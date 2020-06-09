package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.DsdRegistrationUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        service = DsdRegistrationUtils.class
)
public class DsdRegistrationUtilsImpl implements DsdRegistrationUtils{

    @Reference
    KeycloakUtils keycloakUtils;

    @Override
    public void deleteRegistrationsFor(Registration registration) {
        RegistrationLocalServiceUtil.deleteAllRegistrationsAndChildRegistrations(registration.getGroupId(), registration.getArticleId());
    }

    @Override
    public void registerUser(User user, Registration registration, String userProperties) throws PortalException {

        validateRegistration(user, registration);

        long parentId = registration.getParentRegistration() == null ? 0 : registration.getParentRegistration().getArticleId();
        RegistrationLocalServiceUtil.addUserRegistration(
                registration.getCompanyId(), registration.getGroupId(), registration.getArticleId(),
                parentId, user.getUserId(),
                registration.getStartTime(), registration.getEndTime(), userProperties);
    }

    @Override
    public void unRegisterUser(User user, Registration registration) {

        RegistrationLocalServiceUtil.deleteUserRegistrationAndChildRegistrations(
                registration.getGroupId(), registration.getArticleId(), user.getUserId());
    }

    @Override
    public void validateRegistration(User user, Registration registration) throws PortalException {

        if (!registration.isOpen()){
            throw new ValidationException(String.format("Registration %s is not open!", registration.getTitle()));
        }

        if (isUserRegisteredFor(user, registration)){
            throw new ValidationException(String.format("User already registered for %s !", registration.getTitle()));
        }

        if (registration.getCapacity() != Integer.MAX_VALUE && getRegistrationCount(registration) >= registration.getCapacity()){
            throw new ValidationException(String.format("Registration %s is full!", registration.getTitle()));
        }

        if (getOverlappingRegistrationIds(user, registration).length > 0){
            throw new ValidationException(String.format("Registration period for %s overlaps with other existing registrations!", registration.getTitle()));
        }

        List<String> missingInfo = getMissingUserInformation(user, registration);
        if (missingInfo.size() > 0){
            throw new ValidationException("Missing user data for following fields: " + Arrays.toString(missingInfo.toArray()));
        }

        if (registration.getParentRegistration() != null && !isUserRegisteredFor(user, registration.getParentRegistration())){
            throw new ValidationException("User not registered for required parent registration: " + registration.getParentRegistration().getTitle() );
        }

    }

    @Override
    public int getRegistrationCount(Registration registration) {
        return RegistrationLocalServiceUtil.getRegistrationsCount(registration.getGroupId(), registration.getArticleId());
    }

    private long[] getOverlappingRegistrationIds(User user, Registration registration){
        long[] registrationsWithOverlappingPeriod = RegistrationLocalServiceUtil.getRegistrationsWithOverlappingPeriod(registration.getGroupId(), user.getUserId(),
                registration.getStartTime(), registration.getEndTime());

        /*
         * Some parallel sessions can overlap with their parent session. These need to be removed.
         */
        if (registrationsWithOverlappingPeriod.length == 0 || registration.getParentRegistration() == null) {
            return registrationsWithOverlappingPeriod;
        }
        long parentId = registration.getParentRegistration().getArticleId();
        boolean overlapWithParent = registration.isOverlapWithParent();

        for (int i = 0; i < registrationsWithOverlappingPeriod.length; i++) {
            if (registrationsWithOverlappingPeriod[i] == parentId && overlapWithParent){
                registrationsWithOverlappingPeriod[i] = 0;
            }
        }
        return ArrayUtil.remove(registrationsWithOverlappingPeriod, 0);
    }

    @Override
    public List<Registration> getOverlappingRegistrations(User user, Registration registration) throws PortalException {
        long[] overlappingArticleId = RegistrationLocalServiceUtil.getRegistrationsWithOverlappingPeriod(registration.getGroupId(), user.getUserId(),
                registration.getStartTime(), registration.getEndTime());

        ArrayList<Registration> overlapping = new ArrayList<>();
        for (long articleId : overlappingArticleId) {
            JournalArticle overlappingArticle = JournalArticleLocalServiceUtil.getLatestArticle(registration.getGroupId(), String.valueOf(articleId));
            overlapping.add((Registration) AbsDsdArticle.getInstance(overlappingArticle));
        }
        return overlapping;
    }

    @Override
    public void validateRoomCapacity(SessionRegistration session) throws PortalException {

        int sessionCapacity = session.getCapacity();
        int roomCapacity = session.getRoom().getCapacity();
        if (roomCapacity < sessionCapacity){
            throw new ValidationException(String.format("Room capacity %d is smaller than session capacity %s !", roomCapacity, sessionCapacity));
        }
    }

    @Override
    public List<String> getMissingUserInformation(User user, Registration registration) throws PortalException {

        ArrayList<String> missingInfo = new ArrayList<>();
        if (user.getFirstName() == null){
            missingInfo.add("First name");
        }
        if (user.getLastName() == null){
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
        int registrationsCount = RegistrationLocalServiceUtil.getRegistrationsCount(registration.getGroupId(), user.getUserId(), registration.getArticleId());
        return registrationsCount > 0;
    }
}
