package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.database.RegistrationData;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        service = DsdSessionUtils.class
)
public class DsdSessionUtilsImpl implements DsdSessionUtils {

    @Reference
    WebinarUtilsFactory webinarUtilsFactory;

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;

    @Override
    public void deleteRegistrations(Registration registration) {
        RegistrationLocalServiceUtil.deleteAllRegistrationsAndChildRegistrations(registration.getGroupId(), registration.getResourceId());
    }

    @Override
    public void deleteRegistrations(long groupId, long resourceId) {
        RegistrationLocalServiceUtil.deleteAllRegistrationsAndChildRegistrations(groupId, resourceId);
    }

    @Override
    public void deleteRegistration(long registrationId) throws PortalException {
        RegistrationLocalServiceUtil.deleteRegistration(registrationId);
    }

    @Override
    public int getRegistrationCount() {
        return RegistrationLocalServiceUtil.getRegistrationsCount();
    }

    @Override
    public List<RegistrationData> getRegistrations(int start, int end) {
        final List<nl.deltares.dsd.registration.model.Registration> dbRegistrations = RegistrationLocalServiceUtil.getRegistrations(start, end);
        List<RegistrationData> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(getRegistrationData(dbRegistration)));
        return registrations;
    }

    @Override
    public String getUserJoinLink(User user, Registration registration, boolean isRegistered) throws Exception {

        if (user.isGuestUser()) return "";

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
        if (user.isGuestUser()) return "";
        final boolean userRegisteredFor = isUserRegisteredFor(user, registration);
        return getUserJoinLink(user, registration, userRegisteredFor);
    }

    @Override
    public void registerUser(User user, Map<String, String> userAttributes, Registration registration, Map<String, String> registrationProperties, User registrationUser, Event event) throws PortalException {

        if (user.isGuestUser()) return;
        try {
            if (webinarUtilsFactory.isWebinarSupported(registration)) {
                registerWebinarUser(user, userAttributes, (SessionRegistration) registration, registrationProperties);
            }
        } finally {
            long parentId = registration.getParentRegistration() == null ? 0 : registration.getParentRegistration().getResourceId();

            long eventResourcePrimaryKey = 0;
            if (event != null) eventResourcePrimaryKey = event.getResourceId();
            long registeredByUserId = 0;
            if (registrationUser != null && registrationUser != user) {
                registeredByUserId = registrationUser.getUserId();
            }
            RegistrationLocalServiceUtil.addUserRegistration(
                    registration.getCompanyId(), registration.getGroupId(), registration.getResourceId(), eventResourcePrimaryKey,
                    parentId, user.getUserId(),
                    registration.getStartTime(), registration.getEndTime(), JsonContentUtils.formatMapToJson(registrationProperties), registeredByUserId);
        }
    }

    @Override
    public void registerUser(User user, Registration registration, Map<String, String> registrationProperties, User registrationUser, Event event) throws PortalException {

        if (user.isGuestUser()) return;

        long parentId = registration.getParentRegistration() == null ? 0 : registration.getParentRegistration().getResourceId();

        long eventResourcePrimaryKey = 0;
        if (event != null) eventResourcePrimaryKey = event.getResourceId();
        long registeredByUserId = 0;
        if (registrationUser != null && registrationUser != user) {
            registeredByUserId = registrationUser.getUserId();
        }
        RegistrationLocalServiceUtil.addUserRegistration(
                registration.getCompanyId(), registration.getGroupId(), registration.getResourceId(), eventResourcePrimaryKey,
                parentId, user.getUserId(),
                registration.getStartTime(), registration.getEndTime(), JsonContentUtils.formatMapToJson(registrationProperties), registeredByUserId);

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

        if (user.isGuestUser()) return;
        try {
            if (webinarUtilsFactory.isWebinarSupported(registration)) {

                List<nl.deltares.dsd.registration.model.Registration> registrations = RegistrationLocalServiceUtil.getRegistrations(registration.getGroupId(), user.getUserId(), registration.getResourceId());
                if (!registrations.isEmpty()) {
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
            RegistrationLocalServiceUtil.deleteUserRegistrationAndChildRegistrations(
                    registration.getGroupId(), registration.getResourceId(), user.getUserId());
        }
    }

    private Map<String, String> getUserPreferencesMap(nl.deltares.dsd.registration.model.Registration dbRegistration) throws PortalException {
        String userPreferences = dbRegistration.getUserPreferences();
        if (userPreferences == null) {
            return Collections.emptyMap();
        }
        return JsonContentUtils.parseJsonToMap(userPreferences);
    }

    @Override
    public void validateRegistrations(User user, List<Registration> registrations, List<Registration> childRegistrations) throws PortalException {
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
        final ArrayList<Registration> combined = new ArrayList<>(registrations);
        combined.addAll(childRegistrations);
        List<Registration> overlapping = checkIfRegistrationsOverlap(combined);
        if (!overlapping.isEmpty()) {
            StringBuilder titles = new StringBuilder();
            overlapping.forEach(registration -> {
                titles.append(registration.getTitle());
                titles.append(", ");
            });
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

        List<Long> overlappingRegistrationIds = getOverlappingRegistrationIds(user, registration);
        if (!overlappingRegistrationIds.isEmpty()) {
            throw new ValidationException(String.format("Registration period for %s overlaps with other existing registrations: %s",
                    registration.getTitle(), Arrays.toString(getTitles(overlappingRegistrationIds))));
        }

        if (registration.getParentRegistration() != null && !isUserRegisteredFor(user, registration.getParentRegistration())) {
            throw new ValidationException("User not registered for required parent registration: " + registration.getParentRegistration().getTitle());
        }
    }

    private String[] getTitles(List<Long> articleIds) {
        ArrayList<String> titles = new ArrayList<>(articleIds.size());
        for (Long articleId : articleIds) {
            try {
                JournalArticle journalArticle = dsdJournalArticleUtils.getLatestArticle(articleId);
                titles.add(journalArticle == null ? String.valueOf(articleId) : journalArticle.getTitle());
            } catch (PortalException e) {
                titles.add(String.valueOf(articleId));
            }
        }
        return titles.toArray(new String[0]);
    }

    public List<Registration> getChildRegistrations(Registration registration, List<Registration> eventRegistrations) {
        ArrayList<Registration> children = new ArrayList<>();
        for (Registration eventRegistration : eventRegistrations) {
            if (eventRegistration.getParentRegistration() != null && eventRegistration.getParentRegistration().getResourceId() == registration.getResourceId()) {
                children.add(eventRegistration);
            }
        }

        children.sort(Comparator.comparing(Registration::getStartTime).thenComparing(Registration::getTitle));
        return children;
    }

    @Override
    public Map<String, String> getUserPreferences(User user, Registration registration) throws PortalException {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getRegistrations(registration.getGroupId(), user.getUserId(), registration.getResourceId());

        for (nl.deltares.dsd.registration.model.Registration dbRegistration : dbRegistrations) {
            return getUserPreferencesMap(dbRegistration);
        }
        return Collections.emptyMap();
    }

    @Override
    public int getRegistrationCount(Registration registration) {
        return RegistrationLocalServiceUtil.getRegistrationsCount(registration.getGroupId(), registration.getResourceId());
    }

    private List<Registration> checkIfRegistrationsOverlap(List<Registration> registrations) {

        ArrayList<Registration> overlapping = new ArrayList<>();
        Registration[] list = registrations.toArray(new Registration[0]);
        for (Registration reg1 : list) {
            registrations.forEach(registration -> {
                if (registration == reg1) return;
                if (canOverlapWithParent(reg1, registration)) return;
                if (canOverlapWithParent(registration, reg1)) return;
                if (periodsOverlap(reg1, registration)) {
                    if (!overlapping.contains(registration)) overlapping.add(registration);
                    if (!overlapping.contains(reg1)) overlapping.add(reg1);
                }
            });

        }
        return overlapping;
    }

    private boolean canOverlapWithParent(Registration child, Registration parent) {
        if (child.getParentRegistration() == null) return false; //no parent so cannot overlap
        return child.getParentRegistration().getArticleId().equals(parent.getArticleId()) && child.isOverlapWithParent();
    }

    private boolean periodsOverlap(Registration reg1, Registration reg2) {

        List<Period> reg1Periods = reg1.getStartAndEndTimesPerDay();
        List<Period> reg2Periods = reg2.getStartAndEndTimesPerDay();

        final boolean[] overlap = {false};
        for (Period reg1Period : reg1Periods) {
            reg2Periods.forEach(reg2Period -> overlap[0] = reg2Period.isAnyTimeCommon(reg1Period, true));
            if (overlap[0]) return true;
        }
        return false;
    }

    private List<Long> getOverlappingRegistrationIds(User user, Registration registration) {

        /*
         * Some parallel sessions can overlap with their parent session. These need to be removed.
         */
        long parentId = registration.getParentRegistration() == null ? -1 : registration.getParentRegistration().getResourceId();
        boolean overlapWithParent = registration.isOverlapWithParent();

        long searchResourceId = registration.getResourceId();
        List<Long> resourcesWithOverlappingPeriod = new ArrayList<>();
        registration.getStartAndEndTimesPerDay().forEach(startAndEndTimesPerDay -> {
            List<nl.deltares.dsd.registration.model.Registration> overlappingRegistrations = RegistrationLocalServiceUtil.getRegistrationsWithOverlappingPeriod(registration.getGroupId(), user.getUserId(),
                    registration.getStartTime(), registration.getEndTime());
            for (nl.deltares.dsd.registration.model.Registration overlappingRegistration : overlappingRegistrations) {
                long registrationResourceId = overlappingRegistration.getResourcePrimaryKey();
                if (searchResourceId == registrationResourceId) continue;
                if (parentId == registrationResourceId && overlapWithParent) continue;
                if (resourcesWithOverlappingPeriod.contains(registrationResourceId)) continue;
                resourcesWithOverlappingPeriod.add(registrationResourceId);
            }

        });
        return resourcesWithOverlappingPeriod;
    }

    @Override
    public boolean isUserRegisteredFor(User user, Registration registration) {
        int registrationsCount = RegistrationLocalServiceUtil.getRegistrationsCount(registration.getGroupId(), user.getUserId(), registration.getResourceId());
        return registrationsCount > 0;
    }

    @Override
    public boolean isUserRegisteredFor(long groupId, long userId, long resourceId) {
        int registrationsCount = RegistrationLocalServiceUtil.getRegistrationsCount(groupId, userId, resourceId);
        return registrationsCount > 0;
    }

    public List<Long> getResourceIdsByUserAndGroup(User user, long groupId) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getRegistrationDataByUserAndGroupId(user.getUserId(), groupId);
        return dbRegistrations.stream().map(nl.deltares.dsd.registration.model.Registration::getResourcePrimaryKey).collect(Collectors.toList());
    }

    @Override
    public List<Long> getResourceIdsByAuthorAndGroup(User user, long groupId) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getRegistrationDataByAuthorAndGroupId(user.getUserId(), groupId);
        return dbRegistrations.stream()
                .filter(registration -> registration.getUserId() != registration.getRegisteredByUserId())
                .map(nl.deltares.dsd.registration.model.Registration::getResourcePrimaryKey).collect(Collectors.toList());
    }
    @Override
    public List<RegistrationData> getRegistrationDataByAuthorAndResourceId(User author, long resourceId) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getRegistrationDataByAuthorAndResourceId(author.getUserId(), resourceId);
        return dbRegistrations.stream()
                .filter(registration -> registration.getUserId() != registration.getRegisteredByUserId())
                .map(DsdSessionUtilsImpl::getRegistrationData)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistrationData> getRegistrationDataByUserAndResourceId(User user, long resourceId) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getRegistrationDataByUserAndResourceId(user.getUserId(), resourceId);
        return dbRegistrations.stream().map(DsdSessionUtilsImpl::getRegistrationData).collect(Collectors.toList());
    }

    @Override
    public List<RegistrationData> getRegistrations(Event event) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getEventRegistrations(event.getGroupId(), event.getResourceId());
        List<RegistrationData> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration ->
        {
            registrations.add(getRegistrationData(dbRegistration));
        });
        return registrations;
    }

    private static RegistrationData getRegistrationData(nl.deltares.dsd.registration.model.Registration dbRegistration) {
        RegistrationData registrationData = new RegistrationData();
        registrationData.setRegistrationRecordId(dbRegistration.getRegistrationId());
        registrationData.setCompanyId(dbRegistration.getCompanyId());
        registrationData.setGroupId(dbRegistration.getGroupId());
        registrationData.setAuthorId(dbRegistration.getRegisteredByUserId());
        registrationData.setUserId(dbRegistration.getUserId());
        registrationData.setResourceId(dbRegistration.getResourcePrimaryKey());
        registrationData.setEventResourceId(dbRegistration.getEventResourcePrimaryKey());
        registrationData.setParentResourceId(dbRegistration.getParentResourcePrimaryKey());
        String userPreferences = dbRegistration.getUserPreferences();
        if (userPreferences != null) {
            try {
                Map<String, String> attributes = JsonContentUtils.parseJsonToMap(userPreferences);
                attributes.forEach(registrationData::putAttribute);
            } catch (JSONException e) {
                LOG.warn("Error parsing userPreferences JSON: " + userPreferences, e);
            }
        }

        registrationData.addPeriod(new Period(dbRegistration.getStartTime(), dbRegistration.getEndTime()));
        return registrationData;
    }

    @Override
    public List<RegistrationData> getRegistrations(long groupId, long resourceId) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getArticleRegistrations(groupId, resourceId);

        List<RegistrationData> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration ->
                registrations.add(getRegistrationData(dbRegistration)));
        return registrations;

    }

    @Override
    public List<RegistrationData> getRegistrations(long groupId, Date startDate, Date endDate) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getRegistrations(groupId, startDate, endDate);
        List<RegistrationData> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration ->
                registrations.add(getRegistrationData(dbRegistration)));
        return registrations;
    }

    @Override
    public List<RegistrationData> getEventRegistrations(long groupId, long eventResourceId) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                RegistrationLocalServiceUtil.getEventRegistrations(groupId, eventResourceId);
        List<RegistrationData> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration ->
                registrations.add(getRegistrationData(dbRegistration)));
        return registrations;
    }

    @Override
    public void deleteEventRegistrations(long groupId, long resourceId) {
        RegistrationLocalServiceUtil.deleteAllEventRegistrations(groupId, resourceId);
    }

    private static final Log LOG = LogFactoryUtil.getLog(DsdSessionUtilsImpl.class);
}
