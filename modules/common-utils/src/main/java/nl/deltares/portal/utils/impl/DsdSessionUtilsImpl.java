package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.*;

@Component(
        immediate = true,
        service = DsdSessionUtils.class,
        property = {
        "javax.portlet.supported-locale=en",
        "javax.portlet.supported-locale=nl",
        "javax.portlet.resource-bundle=content.Language"
}
)
public class DsdSessionUtilsImpl implements DsdSessionUtils {

    @Reference
    KeycloakUtils keycloakUtils;

    @Reference
    GotoUtils gotoUtils;

    @Reference
    DsdParserUtils parserUtils;

    @Override
    public void deleteRegistrationsFor(Registration registration) {
        RegistrationLocalServiceUtil.deleteAllRegistrationsAndChildRegistrations(registration.getGroupId(), registration.getResourceId());
    }

    @Override
    public void registerUser(User user, Registration registration, Map<String, String> userProperties) throws PortalException {

        validateRegistration(user, registration);

        if (gotoUtils.isGotoMeeting(registration)){
            registerGotoUser(user, (SessionRegistration) registration, userProperties);
        }
        long parentId = registration.getParentRegistration() == null ? 0 : registration.getParentRegistration().getResourceId();
        RegistrationLocalServiceUtil.addUserRegistration(
                registration.getCompanyId(), registration.getGroupId(), registration.getResourceId(),
                parentId, user.getUserId(),
                registration.getStartTime(), registration.getEndTime(), JsonContentParserUtils.formatMapToJson(userProperties));
    }

    private void registerGotoUser(User user, SessionRegistration registration, Map<String, String> userProperties) throws PortalException {
        try {
            Map<String, String> responseValues = gotoUtils.registerUser(user, registration.getWebinarKey(), GroupServiceUtil.getGroup(registration.getGroupId()).getName());
            userProperties.put("registrantKey", responseValues.get("registrantKey"));
            userProperties.put("joinUrl", responseValues.get("joinUrl"));
        } catch (Exception e) {
            throw new PortalException(e);
        }
    }

    @Override
    public void unRegisterUser(User user, Registration registration) throws PortalException {

        if (gotoUtils.isGotoMeeting(registration)){
            unRegisterGotUser(user, registration);
        }
        RegistrationLocalServiceUtil.deleteUserRegistrationAndChildRegistrations(
                registration.getGroupId(), registration.getResourceId(), user.getUserId());
    }

    private void unRegisterGotUser(User user, Registration registration) throws PortalException {
        List<nl.deltares.dsd.registration.model.Registration> registrations = RegistrationLocalServiceUtil.getRegistrations(registration.getGroupId(), user.getUserId(), registration.getResourceId());
        if (registrations.size() > 0){
             String userPreferences = registrations.get(0).getUserPreferences();
            if (userPreferences == null){
                throw new PortalException(String.format("No user registrantKey for user %s and registration %s", user.getEmailAddress(), registration.getTitle()));
            }
            Map<String, String> preferences = JsonContentParserUtils.parseJsonToMap(userPreferences);
            String registrantKey = preferences.get("registrantKey");
            if (registrantKey == null){
                throw new PortalException(String.format("No user registrantKey for user %s and registration %s", user.getEmailAddress(), registration.getTitle()));
            }

            try {
                gotoUtils.unregisterUser(registrantKey, ((SessionRegistration) registration).getWebinarKey());
            } catch (Exception e) {
                throw new PortalException(e);
            }
        }
    }

    @Override
    public void validateRegistration(User user, Registration registration) throws PortalException {

        if (!registration.isOpen()) {
            throw new ValidationException(String.format("Registration %s is not open!", registration.getTitle()));
        }

        if (isUserRegisteredFor(user, registration)) {
            throw new ValidationException(String.format("User already registered for %s !", registration.getTitle()));
        }

        if (registration.getCapacity() != Integer.MAX_VALUE && getRegistrationCount(registration) >= registration.getCapacity()) {
            throw new ValidationException(String.format("Registration %s is full!", registration.getTitle()));
        }

        if (getOverlappingRegistrationIds(user, registration).length > 0) {
            throw new ValidationException(String.format("Registration period for %s overlaps with other existing registrations!", registration.getTitle()));
        }

        List<String> missingInfo = getMissingUserInformation(user, registration);
        if (missingInfo.size() > 0) {
            throw new ValidationException("Missing user data for following fields: " + Arrays.toString(missingInfo.toArray()));
        }

        if (registration.getParentRegistration() != null && !isUserRegisteredFor(user, registration.getParentRegistration())) {
            throw new ValidationException("User not registered for required parent registration: " + registration.getParentRegistration().getTitle());
        }

    }


    public List<Registration> getChildRegistrations(Registration registration) throws PortalException {
        Event event = parserUtils.getEvent(registration.getGroupId(), String.valueOf(registration.getEventId()));

        List<Registration> registrations = event.getRegistrations();
        ArrayList<Registration> children = new ArrayList<>();
        registrations.forEach(r -> {
            if (r.getParentRegistration() != null && r.getParentRegistration().getResourceId() == registration.getResourceId()) {
                children.add(r);
            }
        });
        return children;
    }

    @Override
    public int getRegistrationCount(Registration registration) {
        return RegistrationLocalServiceUtil.getRegistrationsCount(registration.getGroupId(), registration.getResourceId());
    }

    private long[] getOverlappingRegistrationIds(User user, Registration registration) {
        long[] registrationsWithOverlappingPeriod = RegistrationLocalServiceUtil.getRegistrationsWithOverlappingPeriod(registration.getGroupId(), user.getUserId(),
                registration.getStartTime(), registration.getEndTime());

        /*
         * Some parallel sessions can overlap with their parent session. These need to be removed.
         */
        if (registrationsWithOverlappingPeriod.length == 0 || registration.getParentRegistration() == null) {
            return registrationsWithOverlappingPeriod;
        }
        long parentId = registration.getParentRegistration().getResourceId();
        boolean overlapWithParent = registration.isOverlapWithParent();

        for (int i = 0; i < registrationsWithOverlappingPeriod.length; i++) {
            if (registrationsWithOverlappingPeriod[i] == parentId && overlapWithParent) {
                registrationsWithOverlappingPeriod[i] = 0;
            }
        }
        return ArrayUtil.remove(registrationsWithOverlappingPeriod, 0);
    }

    @Override
    public List<Registration> getOverlappingRegistrations(User user, Registration registration) throws PortalException {
        long[] overlappingResourceIds = RegistrationLocalServiceUtil.getRegistrationsWithOverlappingPeriod(registration.getGroupId(), user.getUserId(),
                registration.getStartTime(), registration.getEndTime());

        ArrayList<Registration> overlapping = new ArrayList<>();
        for (long resourceId : overlappingResourceIds) {
            JournalArticle overlappingArticle = JournalArticleLocalServiceUtil.getLatestArticle(resourceId);
            overlapping.add((Registration) AbsDsdArticle.getInstance(overlappingArticle));
        }
        return overlapping;
    }

    @Override
    public void validateRoomCapacity(SessionRegistration session) throws PortalException {

        int sessionCapacity = session.getCapacity();
        int roomCapacity = session.getRoom().getCapacity();
        if (roomCapacity < sessionCapacity) {
            throw new ValidationException(String.format("Room capacity %d is smaller than session capacity %s !", roomCapacity, sessionCapacity));
        }
    }

    @Override
    public List<String> getMissingUserInformation(User user, Registration registration) throws PortalException {

        ArrayList<String> missingInfo = new ArrayList<>();
        if (user.getFirstName() == null) {
            missingInfo.add("First name");
        }
        if (user.getLastName() == null) {
            missingInfo.add("Last name");
        }

        Map<String, String> userAttributes;
        try {
            userAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
        } catch (IOException e) {
            throw new PortalException(e);
        }
        for (DsdArticle.DSD_REQUIRED_REGISTRATION_ATTRIBUTES value : DsdArticle.DSD_REQUIRED_REGISTRATION_ATTRIBUTES.values()) {
            if (userAttributes.containsKey(value.name())) continue;
            missingInfo.add(value.name());
        }

        double price = registration.getPrice();
        if (price > 0) {
            for (DsdArticle.DSD_REQUIRED_PAID_REGISTRATION_ATTRIBUTES value : DsdArticle.DSD_REQUIRED_PAID_REGISTRATION_ATTRIBUTES.values()) {
                if (userAttributes.containsKey(value.name())) continue;
                missingInfo.add(value.name());
            }
        }

        return missingInfo;

    }

    @Override
    public boolean isUserRegisteredFor(User user, Registration registration) {
        int registrationsCount = RegistrationLocalServiceUtil.getRegistrationsCount(registration.getGroupId(), user.getUserId(), registration.getResourceId());
        return registrationsCount > 0;
    }

    @Override
    public List<Map<String, Object>> getUserRegistrations(User user, Event event) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations = RegistrationLocalServiceUtil.getUserRegistrations(event.getGroupId(), user.getUserId(), event.getStartTime(), event.getEndTime());
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }
}
