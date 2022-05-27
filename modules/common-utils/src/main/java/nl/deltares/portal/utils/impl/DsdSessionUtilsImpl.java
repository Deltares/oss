package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import nl.deltares.dsd.registration.service.RegistrationLocalService;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;

@Component(
        immediate = true,
        service = DsdSessionUtils.class
)
public class DsdSessionUtilsImpl implements DsdSessionUtils {

    @Reference
    DsdParserUtils parserUtils;

    @Reference
    WebinarUtilsFactory webinarUtilsFactory;

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;

    private RegistrationLocalService registrationLocalService;

    @Reference(unbind = "-")
    private void setRepositoryLogLocalService(RegistrationLocalService registrationLocalService) {

        this.registrationLocalService = registrationLocalService;
    }
    @Override
    public void deleteRegistrationsFor(Registration registration) {
        registrationLocalService.deleteAllRegistrationsAndChildRegistrations(registration.getGroupId(), registration.getResourceId());
    }

    @Override
    public void deleteRegistrationsFor(long groupId, long resourceId) {
        registrationLocalService.deleteAllRegistrationsAndChildRegistrations(groupId, resourceId);
    }

    @Override
    public void deleteRegistrationRecord(long registrationId) throws PortalException {
        registrationLocalService.deleteRegistration(registrationId);
    }

    @Override
    public int getRegistrationCount(){
        return registrationLocalService.getRegistrationsCount();
    }

    @Override
    public List<Map<String, Object>> getRegistrations(int start, int end){
        final List<nl.deltares.dsd.registration.model.Registration> dbRegistrations = registrationLocalService.getRegistrations(start, end);
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }

    @Override
    public String getUserJoinLink(User user, Registration registration, boolean isRegistered) throws Exception {
        if (!webinarUtilsFactory.isWebinarSupported(registration)) {
            return "";
        }

        final WebinarUtils webinarUtils = webinarUtilsFactory.newInstance(registration);
        SessionRegistration sessionRegistration = (SessionRegistration) registration;
        String joinLink = sessionRegistration.getJoinLink();
        if (joinLink != null && !joinLink.isEmpty() && isRegistered) {
            //for static join links a user must have registered
            return joinLink;
        } else if (webinarUtils instanceof JoinConsumer) {
            joinLink = ((JoinConsumer) webinarUtils).getJoinLink(user, sessionRegistration.getWebinarKey(), getUserPreferences(user, registration));
            return joinLink == null ? "" : joinLink;
        } else {
            return "";
        }

    }

    @Override
    public String getUserJoinLink(User user, Registration registration) throws Exception {
        final boolean userRegisteredFor = isUserRegisteredFor(user, registration);
        return getUserJoinLink(user, registration, userRegisteredFor);
    }

    @Override
    public void registerUser(User user, Map<String, String> userAttributes, Registration registration, Map<String,
            String> registrationProperties, User registrationUser) throws PortalException {

        try {
            if (webinarUtilsFactory.isWebinarSupported(registration)) {
                registerWebinarUser(user, userAttributes, (SessionRegistration) registration, registrationProperties);
            }
        } finally {
            long parentId = registration.getParentRegistration() == null ? 0 : registration.getParentRegistration().getResourceId();

            long eventResourcePrimaryKey = 0;
            Event event = parserUtils.getEvent(registration.getGroupId(), String.valueOf(registration.getEventId()), registration.getLocale());
            if (event != null) eventResourcePrimaryKey = event.getResourceId();
            long registeredByUserId = 0;
            if (registrationUser != null && registrationUser != user) {
                registeredByUserId = registrationUser.getUserId();
            }
            registrationLocalService.addUserRegistration(
                    registration.getCompanyId(), registration.getGroupId(), registration.getResourceId(), eventResourcePrimaryKey,
                    parentId, user.getUserId(),
                    registration.getStartTime(), registration.getEndTime(), JsonContentUtils.formatMapToJson(registrationProperties),registeredByUserId);
        }
    }

    private void registerWebinarUser(User user, Map<String, String> userAttributes, SessionRegistration registration, Map<String, String> userProperties) throws PortalException {

        try {
            WebinarUtils webinarUtils = webinarUtilsFactory.newInstance(registration);
            if (webinarUtils.isActive()) {
                webinarUtils.registerUser(user, userAttributes, registration.getWebinarKey(), GroupServiceUtil.getGroup(registration.getGroupId()).getName(Locale.US), userProperties);
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error registering for webinar %s: %s", registration.getTitle(), e.getMessage()));
        }
    }

    @Override
    public void unRegisterUser(User user, Registration registration) throws PortalException {

        try {
            if (webinarUtilsFactory.isWebinarSupported(registration)) {

                List<nl.deltares.dsd.registration.model.Registration> registrations = registrationLocalService.getRegistrations(registration.getGroupId(), user.getUserId(), registration.getResourceId());
                if (registrations.size() > 0) {
                    Map<String, String> preferences = getUserPreferencesMap(registrations.get(0));
                    try {
                        WebinarUtils webinarUtils = webinarUtilsFactory.newInstance(registration);
                        if (webinarUtils.isActive()) {
                            webinarUtils.unregisterUser(user, ((SessionRegistration) registration).getWebinarKey(), preferences);
                        }
                    } catch (Exception e) {
                        throw new PortalException(String.format("Failed to unregister user %s for registration %s: %s", user.getEmailAddress(), registration.getTitle(), e.getMessage()));
                    }
                }
            }
        } finally {
            registrationLocalService.deleteUserRegistrationAndChildRegistrations(
                    registration.getGroupId(), registration.getResourceId(), user.getUserId());
        }
    }

    private Map<String, String> getUserPreferencesMap(nl.deltares.dsd.registration.model.Registration dbRegistration) throws PortalException {
        String userPreferences = dbRegistration.getUserPreferences();
        if (userPreferences == null){
            return Collections.emptyMap();
        }
        return JsonContentUtils.parseJsonToMap(userPreferences);
    }

    @Override
    public void validateRegistrations(User user, List<Registration> registrations) throws PortalException {
        //checks registrations in list
        double maxPrice = 0;
        for (Registration registration : registrations) {
            if (!registration.isOpen()) {
                throw new ValidationException(String.format("Registration %s is not open!", registration.getTitle()));
            }
            if (registration.getPrice() > maxPrice) {
                maxPrice = registration.getPrice();
            }
        }
        List<Registration> overlapping = checkIfRegistrationsOverlap(registrations);
        if (overlapping.size() > 0){
            StringBuilder titles = new StringBuilder();
            overlapping.forEach(registration -> {titles.append(registration.getTitle()); titles.append(", ");});
            throw new ValidationException("Overlapping periods found for registrations: " + titles);
        }

        //check registrations in database
        for (Registration registration : registrations) {
            dbValidationChecks(user, registration);
        }
    }

    private void dbValidationChecks(User user, Registration registration) throws PortalException {
        if (isUserRegisteredFor(user, registration)) {
            throw new ValidationException(String.format("User already registered for %s !", registration.getTitle()));
        }

        if (registration.getCapacity() != Integer.MAX_VALUE && getRegistrationCount(registration) >= registration.getCapacity()) {
            throw new ValidationException(String.format("Registration %s is full!", registration.getTitle()));
        }

        long[] overlappingRegistrationIds = getOverlappingRegistrationIds(user, registration);
        if (overlappingRegistrationIds.length > 0) {
            throw new ValidationException(String.format("Registration period for %s overlaps with other existing registrations: %s",
                    registration.getTitle(), Arrays.toString(getTitles(registration.getGroupId(), overlappingRegistrationIds))));
        }

        if (registration.getParentRegistration() != null && !isUserRegisteredFor(user, registration.getParentRegistration())) {
            throw new ValidationException("User not registered for required parent registration: " + registration.getParentRegistration().getTitle());
        }
    }

    private String[] getTitles(long groupId, long[] articleIds) {
        String[] titles = new String[articleIds.length];
        for (int i = 0; i < articleIds.length; i++) {
            try {
                JournalArticle journalArticle = dsdJournalArticleUtils.getJournalArticle(groupId, String.valueOf(articleIds[i]));
                titles[i] = journalArticle == null ? String.valueOf(articleIds[i]) : journalArticle.getTitle();
            } catch (PortalException e) {
                titles[i] = String.valueOf(articleIds[i]);
            }
        }
        return titles;
    }

    public List<Registration> getChildRegistrations(Registration registration, Locale locale) throws PortalException {
        Event event = parserUtils.getEvent(registration.getGroupId(), String.valueOf(registration.getEventId()), registration.getLocale());
        if (event == null) return Collections.emptyList();
        List<Registration> registrations = event.getRegistrations(locale);
        ArrayList<Registration> children = new ArrayList<>();
        for (Registration eventRegistration : registrations) {
            if (eventRegistration.getParentRegistration() != null && eventRegistration.getParentRegistration().getResourceId() == registration.getResourceId()) {
                children.add(eventRegistration);
            }
        }
        return children;
    }

    @Override
    public Map<String, String> getUserPreferences(User user, Registration registration) throws PortalException {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                registrationLocalService.getRegistrations(registration.getGroupId(), user.getUserId(), registration.getResourceId());

        for (nl.deltares.dsd.registration.model.Registration dbRegistration : dbRegistrations) {
            return getUserPreferencesMap(dbRegistration);
        }
        return Collections.emptyMap();
    }

    @Override
    public int getRegistrationCount(Registration registration) {
        return registrationLocalService.getRegistrationsCount(registration.getGroupId(), registration.getResourceId());
    }

    private List<Registration> checkIfRegistrationsOverlap(List<Registration> registrations){

        ArrayList<Registration> overlapping = new ArrayList<>();
        Registration[] list = registrations.toArray(new Registration[0]);
        for (Registration reg1 : list) {
            registrations.forEach(registration -> {
                if (registration == reg1) return;
                if (registration.getParentRegistration() == reg1 && registration.isOverlapWithParent()) return;
                if (reg1.getParentRegistration() == registration && reg1.isOverlapWithParent()) return;
                if (periodsOverlap(reg1, registration)){
                    if (!overlapping.contains(reg1)) overlapping.add(reg1);
                }
            });

        }
        return overlapping;
    }

    private boolean periodsOverlap(Registration reg1, Registration reg2) {

        List<Period> reg1Periods = reg1.getStartAndEndTimesPerDay();
        List<Period> reg2Periods = reg2.getStartAndEndTimesPerDay();

        final boolean[] overlap = {false};
        for (Period reg1Period : reg1Periods) {
            reg2Periods.forEach(reg2Period -> overlap[0] = reg2Period.isAnyTimeCommon(reg1Period));
            if (overlap[0]) return true;
        }
        return false;
    }

    private long[] getOverlappingRegistrationIds(User user, Registration registration) throws PortalException {
        long[] registrationsWithOverlappingPeriod = registrationLocalService.getRegistrationsWithOverlappingPeriod(registration.getGroupId(), user.getUserId(),
                registration.getStartTime(), registration.getEndTime());

        /*
         * Some parallel sessions can overlap with their parent session. These need to be removed.
         */
        long parentId = registration.getParentRegistration() == null ? -1 : registration.getParentRegistration().getResourceId();
        boolean overlapWithParent = registration.isOverlapWithParent();

        for (int i = 0; i < registrationsWithOverlappingPeriod.length; i++) {
            //skip if child can overlap with parent session
            if (registrationsWithOverlappingPeriod[i] == parentId && overlapWithParent) {
                registrationsWithOverlappingPeriod[i] = 0;
            //skip bus transfers and other registrations that can always overlap
            } else if (canOverlap(registrationsWithOverlappingPeriod[i], registration.getArticleId())) {
                registrationsWithOverlappingPeriod[i] = 0;
            }
        }
        return ArrayUtil.remove(registrationsWithOverlappingPeriod, 0);
    }

    private boolean canOverlap(long overlappingResourcePrimaryKey, String validatingArticleId) throws PortalException {
        JournalArticle overlappingDbRegistration = JournalArticleLocalServiceUtil.fetchLatestArticle(overlappingResourcePrimaryKey);
        if (overlappingDbRegistration == null) {
            LOG.warn(String.format("Registration with resourcePrimaryKey %d no longer exists.", overlappingResourcePrimaryKey));
            return true;
        }
        Registration overlappingRegistration = parserUtils.getRegistration(overlappingDbRegistration);
        return overlappingRegistration.isOverlapWithParent() &&
                (overlappingRegistration.getParentRegistration() == null || overlappingRegistration.getParentRegistration().getArticleId().equals(validatingArticleId));
    }

    @Override
    public boolean isUserRegisteredFor(User user, Registration registration) {
        int registrationsCount = registrationLocalService.getRegistrationsCount(registration.getGroupId(), user.getUserId(), registration.getResourceId());
        return registrationsCount > 0;
    }

    @Override
    public List<Map<String, Object>> getUserRegistrations(User user, Event event) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                registrationLocalService.getUserEventRegistrations(event.getGroupId(), user.getUserId(), event.getResourceId());
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }

    @Override
    public List<Map<String, Object>> getUserRegistrationsMadeForOthers(User user, Event event) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                registrationLocalService.getUserEventRegistrationsMadeForOthers(event.getGroupId(), user.getUserId(), event.getResourceId());
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }

    @Override
    public List<Map<String, Object>> getRegistrations(Event event) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                registrationLocalService.getEventRegistrations(event.getGroupId(), event.getResourceId());
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }

    @Override
    public List<Map<String, Object>> getRegistrations(long groupId, long resourceId) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                registrationLocalService.getArticleRegistrations(groupId, resourceId);
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }

    @Override
    public List<Map<String, Object>> getEventRegistrations(long groupId, long eventResourceId) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                registrationLocalService.getEventRegistrations(groupId, eventResourceId);
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }

    @Override
    public void deleteEventRegistrations(long groupId, long resourceId) {
        registrationLocalService.deleteAllEventRegistrations(groupId, resourceId);
    }

    private static final Log LOG = LogFactoryUtil.getLog(DsdSessionUtilsImpl.class);
}
