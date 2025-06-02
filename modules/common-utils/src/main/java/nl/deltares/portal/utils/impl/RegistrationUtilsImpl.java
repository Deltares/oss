package nl.deltares.portal.utils.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import nl.deltares.data.service.registration.model.RegistrationAttribute;
import nl.deltares.data.service.registration.model.RegistrationModel;
import nl.deltares.data.service.registration.model.RegistrationPeriod;
import nl.deltares.data.service.registration.model.RegistrationResource;
import nl.deltares.data.service.registration.service.*;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.database.RegistrationData;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        service = DsdSessionUtils.class
)
public class RegistrationUtilsImpl implements DsdSessionUtils {

    @Override
    public void deleteRegistrations(Registration registration) {
        deleteRegistrations(registration.getGroupId(), registration.getResourceId());
    }

    @Override
    public void deleteRegistrations(long groupId, long resourceId) {
        List<RegistrationResource> childResources = _registrationResourceLocalService.findByGroupAndParentResource(groupId, resourceId);
        childResources.forEach(childResource ->
                _registrationLocalService.removeByResource(childResource.getRegistrationResourceId())
        );
        _registrationLocalService.removeByResource(resourceId);
    }

    @Override
    public void deleteRegistration(long registrationId) throws PortalException {
        _registrationLocalService.deleteRegistration(registrationId);
    }

    @Override
    public int getRegistrationCount() {
        return _registrationLocalService.getRegistrationsCount();
    }

    @Override
    public List<RegistrationData> getRegistrations(int start, int end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUserJoinLink(User user, Registration registration, boolean isRegistered) throws Exception {

        if (user.isGuestUser()) return "";

        if (!_webinarUtilsFactory.isWebinarSupported(registration)) {
            return "";
        }

        final WebinarUtils webinarUtils = _webinarUtilsFactory.newInstance(registration);
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
    public void registerUser(User user, Map<String, String> userAttributes, Registration registration, Map<String, String> registrationProperties, User author, Event event) throws PortalException {

        if (user.isGuestUser()) return;

        if (_registrationLocalService.countByUserAndResource(user.getUserId(), registration.getResourceId()) > 0){
            throw new ValidationException("User already registered");
        }

        try {
            if (_webinarUtilsFactory.isWebinarSupported(registration)) {
                registerWebinarUser(user, userAttributes, (SessionRegistration) registration, registrationProperties);
            }
        } catch (Exception e) {
            LOG.error(String.format("Error registering user for webinar:  user=%s, webinar=%s, error=%s",
                    user.getEmailAddress(), registration.getResourceId(), e.getMessage() ));
        }

        RegistrationResource registrationResource = _registrationResourceLocalService.fetchRegistrationResource(registration.getResourceId());
        if (registrationResource == null) {
            registrationResource = addRegistrationResource(registration, event);
            if (registrationResource == null) return;
        }

        nl.deltares.data.service.registration.model.Registration userRegistration = addUserRegistration(user, registration, author);
        if (userRegistration != null) {
            addRegistrationAttributes(registrationProperties, userRegistration);
        }

    }

    @Override
    public void registerUser(User user, Registration registration, Map<String, String> registrationProperties, User author, Event event) throws PortalException {

        if (user.isGuestUser()) return;

        if (_registrationLocalService.countByUserAndResource(user.getUserId(), registration.getResourceId()) > 0){
            throw new ValidationException("User already registered");
        }

        RegistrationResource registrationResource = _registrationResourceLocalService.fetchRegistrationResource(registration.getResourceId());
        if (registrationResource == null) {
            registrationResource = addRegistrationResource(registration, event);
            if (registrationResource == null) return;
        }

        nl.deltares.data.service.registration.model.Registration userRegistration = addUserRegistration(user, registration, author);
        if (userRegistration != null) {
            addRegistrationAttributes(registrationProperties, userRegistration);
        }
    }

    @Override
    public void unRegisterUser(User user, Registration registration) {

        if (user.isGuestUser()) return;

        List<nl.deltares.data.service.registration.model.Registration> userRegistrations = _registrationLocalService
                .findByUserAndResource(user.getUserId(), registration.getResourceId());
        if (userRegistrations == null || userRegistrations.isEmpty()) {
            LOG.warn(String.format("User not registered for webinar: userId=%s, webinar=%s", user.getUserId(), registration.getTitle()));
            return;
        }

        userRegistrations.forEach(userRegistration -> {

            if (_webinarUtilsFactory.isWebinarSupported(registration)) {
                List<RegistrationAttribute> attributes = _registrationAttributeLocalService.findByRegistration(userRegistration.getRegistrationId());
                Map<String, String> preferences = new HashMap<>();
                for (RegistrationAttribute attribute : attributes) {
                    preferences.put(attribute.getName(), attribute.getValue());
                }
                try {
                    WebinarUtils webinarUtils = _webinarUtilsFactory.newInstance(registration);
                    if (webinarUtils.isActive()) {
                        webinarUtils.unregisterUser(user, ((SessionRegistration) registration).getWebinarKey(), preferences);
                    }
                } catch (Exception e) {
                    LOG.error(String.format("Failed to unregister user %s for registration %s: %s", user.getEmailAddress(), registration.getTitle(), e.getMessage()));
                }
            }

            _registrationAttributeLocalService.removeByRegistration(userRegistration.getRegistrationId());
            _registrationLocalService.removeByUserAndResource(user.getUserId(), registration.getResourceId());
        });

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

    @Override
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
    public Map<String, String> getUserPreferences(User user, Registration registration)  {
        List<nl.deltares.data.service.registration.model.Registration> dbRegistrations =
                _registrationLocalService.findByUserAndResource(user.getUserId(), registration.getResourceId());

        nl.deltares.data.service.registration.model.Registration uniqueUserRegistration = dbRegistrations.get(0);
        List<RegistrationAttribute> attributes = _registrationAttributeLocalService.findByRegistration(uniqueUserRegistration.getRegistrationId());
        return attributes.stream().collect(Collectors.toMap(RegistrationAttribute::getName, RegistrationAttribute::getValue));
    }

    @Override
    public int getRegistrationCount(Registration registration) {
        return _registrationLocalService.countByResource(registration.getResourceId());
    }

    @Override
    public boolean isUserRegisteredFor(User user, Registration registration) {
        return _registrationLocalService.countByUserAndResource(user.getUserId(), registration.getResourceId()) > 0;
    }

    @Override
    public List<Long> getResourceIdsByUserAndGroup(User user, long groupId) {
        List<nl.deltares.data.service.registration.model.Registration> dbRegistrations =
                _registrationLocalService.findByUserAndGroup(user.getUserId(), groupId);

        return dbRegistrations.stream().map(RegistrationModel::getRegistrationResourceId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getResourceIdsByAuthorAndGroup(User author, long groupId) {
        List<nl.deltares.data.service.registration.model.Registration> dbRegistrations =
                _registrationLocalService.findByAuthorAndGroup(author.getUserId(), groupId);

        return dbRegistrations.stream().map(RegistrationModel::getRegistrationResourceId).collect(Collectors.toList());
    }

    @Override
    public List<RegistrationData> getRegistrationDataByAuthorAndResourceId(User author, long resourceId) {
        List<nl.deltares.data.service.registration.model.Registration> dbRegistrations =
                _registrationLocalService.findByAuthorAndResource(author.getUserId(), resourceId);

        return dbRegistrations.stream().map(registration -> getRegistrationData(author.getCompanyId(), registration)).collect(Collectors.toList());
    }

    @Override
    public List<RegistrationData> getRegistrationDataByUserAndResourceId(User user, long resourceId) {
        List<nl.deltares.data.service.registration.model.Registration> dbRegistrations =
                _registrationLocalService.findByUserAndResource(user.getUserId(), resourceId);

        return dbRegistrations.stream().map(registration -> getRegistrationData(user.getCompanyId(), registration)).collect(Collectors.toList());
    }

    @Override
    public List<RegistrationData> getRegistrations(long groupId, long resourceId) {
        List<RegistrationResource> resources = _registrationResourceLocalService.findByGroupAndResource(groupId, resourceId);

        List<RegistrationData> data = new ArrayList<>();
        resources.forEach(registrationResource -> data.addAll(getRegistrationData(registrationResource)));

        return data;
    }

    @Override
    public List<RegistrationData> getRegistrations(long groupId, Date startDate, Date endDate) {
        List<RegistrationPeriod> periods = _registrationPeriodLocalService.getWithinPeriod(startDate, endDate);

        List<Long> uniqueIds = new ArrayList<>();
        periods.forEach(period -> {
            if (!uniqueIds.contains(period.getRegistrationResourceId())){
                uniqueIds.add(period.getRegistrationResourceId());
            }
        });

        ArrayList<RegistrationData> data = new ArrayList<>();
        uniqueIds.forEach(uniqueId -> {
            List<RegistrationResource> resources = _registrationResourceLocalService.findByGroupAndResource(groupId, uniqueId);
            if (resources != null && !resources.isEmpty()) {
                data.addAll(getRegistrations(groupId, uniqueId));
            }
        });
        return data;
    }


    @Override
    public List<RegistrationData> getRegistrations(Event event) {
        return getEventRegistrations(event.getGroupId(), event.getResourceId());
    }

    @Override
    public List<RegistrationData> getEventRegistrations(long groupId, long eventResourceId) {
        List<RegistrationResource> eventResources = _registrationResourceLocalService.findByGroupAndEventResource(groupId, eventResourceId);

        ArrayList<RegistrationData> data = new ArrayList<>();
        eventResources.forEach(registrationResource -> data.addAll(getRegistrationData(registrationResource)));

        return data;
    }

    @Override
    public void deleteEventRegistrations(long groupId, long eventResourceId) {

        _registrationResourceLocalService.findByGroupAndEventResource(groupId, eventResourceId).forEach(
                registrationResource -> {

            long registrationResourceId = registrationResource.getRegistrationResourceId();
            List<nl.deltares.data.service.registration.model.Registration> registrations = _registrationLocalService
                    .findByResource(registrationResourceId);
            for (nl.deltares.data.service.registration.model.Registration registration : registrations) {
                _registrationAttributeLocalService.removeByRegistration(registration.getRegistrationId());
            }

            _registrationLocalService.removeByResource(registrationResourceId);
            _registrationPeriodLocalService.removeByResource(registrationResourceId);
            _registrationResourceLocalService.deleteRegistrationResource(registrationResource);
        });

    }

    private void registerWebinarUser(User user, Map<String, String> userAttributes, SessionRegistration registration, Map<String, String> userProperties) throws PortalException {

        try {
            WebinarUtils webinarUtils = _webinarUtilsFactory.newInstance(registration);
            if (webinarUtils.isActive()) {
                webinarUtils.registerUser(user, userAttributes, registration.getWebinarKey(), GroupServiceUtil.getGroup(registration.getGroupId()).getName(Locale.US), userProperties);
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error registering for webinar %s: %s", registration.getTitle(), e.getMessage()));
        }
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

    private Long[] getOverlappingRegistrationIds(User user, Registration registration) {

        /*
         * Some parallel sessions can overlap with their parent session. These need to be removed.
         */
        long parentId = registration.getParentRegistration() == null ? -1 : registration.getParentRegistration().getResourceId();
        boolean overlapWithParent = registration.isOverlapWithParent();

        long searchResourceId = registration.getResourceId();

        ArrayList<Long> resourcesWithOverlappingPeriod = new ArrayList<>();
        registration.getStartAndEndTimesPerDay().forEach(startAndEndTimesPerDay -> {
            List<RegistrationPeriod> overlappingPeriods = _registrationPeriodLocalService.getOverlappingPeriods(startAndEndTimesPerDay.getStartDate(), startAndEndTimesPerDay.getEndDate());
            for (RegistrationPeriod overlappingPeriod : overlappingPeriods) {
                long registrationResourceId = overlappingPeriod.getRegistrationResourceId();
                if (searchResourceId == registrationResourceId) continue;
                if (parentId == registrationResourceId && overlapWithParent) continue;
                if (resourcesWithOverlappingPeriod.contains(registrationResourceId)) continue;
                resourcesWithOverlappingPeriod.add(overlappingPeriod.getRegistrationResourceId());
            }
        });

        ArrayList<Long> userRegistrationsWithOverlappingPeriod = new ArrayList<>();
        resourcesWithOverlappingPeriod.forEach(resourceId -> {
            if (_registrationLocalService.countByUserAndResource(user.getUserId(), resourceId) > 0) {
                userRegistrationsWithOverlappingPeriod.add(resourceId);
            }
        });

        return userRegistrationsWithOverlappingPeriod.toArray(new Long[0]);
    }

    private void dbValidationChecks(User user, Registration registration) throws PortalException {
        if (isUserRegisteredFor(user, registration)) {
            throw new ValidationException(String.format("User already registered for %s !", registration.getTitle()));
        }

        if (registration.getCapacity() != Integer.MAX_VALUE && getRegistrationCount(registration) >= registration.getCapacity()) {
            throw new ValidationException(String.format("Registration %s is full!", registration.getTitle()));
        }

        Long[] overlappingRegistrationIds = getOverlappingRegistrationIds(user, registration);
        if (overlappingRegistrationIds.length > 0) {
            throw new ValidationException(String.format("Registration period for %s overlaps with other existing registrations: %s",
                    registration.getTitle(), Arrays.toString(getTitles(overlappingRegistrationIds))));
        }

        if (registration.getParentRegistration() != null && !isUserRegisteredFor(user, registration.getParentRegistration())) {
            throw new ValidationException("User not registered for required parent registration: " + registration.getParentRegistration().getTitle());
        }
    }

    private String[] getTitles(Long[] registrationIds) {

        List<String> titles = new ArrayList<>();
        for (long registrationId : registrationIds) {
            RegistrationResource resource = _registrationResourceLocalService.fetchRegistrationResource(registrationId);
            if (resource == null) continue;
            titles.add(resource.getResourceName());
        }
        return titles.toArray(new String[0]);
    }

    private nl.deltares.data.service.registration.model.Registration addUserRegistration(User user, Registration registration, User author) {
        nl.deltares.data.service.registration.model.Registration userRegistration = _registrationLocalService.createRegistration(
                CounterLocalServiceUtil.increment(nl.deltares.data.service.registration.model.Registration.class.getName())
        );
        userRegistration.setUserId(user.getUserId());
        userRegistration.setGroupId(registration.getGroupId());
        userRegistration.setRegistrationResourceId(registration.getResourceId());
        userRegistration.setRegistrationTime(new Date(System.currentTimeMillis()));
        if (author != null) userRegistration.setAuthorId(author.getUserId());
        try {
            _registrationLocalService.updateRegistration(userRegistration);
        } catch (Exception e) {
            LOG.error(String.format("Error updating User Registration: registrationResourceId=%s, error=%s",
                    registration.getResourceId(), e.getMessage() ));
            return null;
        }
        return userRegistration;
    }

    @SuppressWarnings("UnusedReturnValue")
    private List<RegistrationAttribute> addRegistrationAttributes(Map<String, String> registrationProperties, nl.deltares.data.service.registration.model.Registration userRegistration) {

        List<RegistrationAttribute> attributes = new ArrayList<>();
        registrationProperties.forEach((key, value) -> {
            RegistrationAttribute registrationAttribute = _registrationAttributeLocalService.createRegistrationAttribute(CounterLocalServiceUtil.increment(
                    RegistrationPeriod.class.getName()
            ));
            registrationAttribute.setRegistrationId(userRegistration.getRegistrationId());
            registrationAttribute.setName(key);
            registrationAttribute.setValue(value);
            _registrationAttributeLocalService.updateRegistrationAttribute(registrationAttribute);

            attributes.add(registrationAttribute);
        });
        return attributes;
    }

    private RegistrationResource addRegistrationResource(Registration registration, Event event) {
        RegistrationResource registrationResource = _registrationResourceLocalService.createRegistrationResource(registration.getResourceId());
        registrationResource.setCompanyId(registration.getCompanyId());
        registrationResource.setGroupId(registration.getGroupId());
        Registration parentRegistration = registration.getParentRegistration();
        if (parentRegistration != null) {
            registrationResource.setParentResourceId(parentRegistration.getResourceId());
        }
        registrationResource.setResourceName(registration.getTitle());

        if (event != null) {
            registrationResource.setEventResourceId(event.getResourceId());
            registrationResource.setEventArticleId(Long.parseLong(event.getArticleId()));
            registrationResource.setEventResourceName(event.getTitle());
        }
        try {
            _registrationResourceLocalService.updateRegistrationResource(registrationResource);
        } catch (Exception e) {
            LOG.error(String.format("Error updating RegistrationResource: registrationResourceId=%s, error=%s",
                    registration.getResourceId(), e.getMessage() ));
            return null;
        }

        List<Period> periods = registration.getStartAndEndTimesPerDay();
        for (Period period : periods) {
            RegistrationPeriod registrationPeriod = _registrationPeriodLocalService.createRegistrationPeriod(
                    CounterLocalServiceUtil.increment(RegistrationPeriod.class.getName())
            );
            registrationPeriod.setRegistrationResourceId(registrationResource.getRegistrationResourceId());
            registrationPeriod.setStartTime(period.getStartDate());
            registrationPeriod.setEndTime(period.getEndDate());
            try {
                _registrationPeriodLocalService.updateRegistrationPeriod(registrationPeriod);
            } catch (Exception e) {
                LOG.error(String.format("Error updating RegistrationPeriod: registrationResourceId=%s, error=%s",
                        registration.getResourceId(), e.getMessage() ));
            }
        }
        return registrationResource;
    }

    private static RegistrationData getRegistrationData(long companyId, nl.deltares.data.service.registration.model.Registration registration) {
        RegistrationData registrationData = new RegistrationData();
        registrationData.setRegistrationRecordId(registration.getRegistrationId());
        registrationData.setResourceId(registration.getRegistrationResourceId());
        registrationData.setUserId(registration.getUserId());
        registrationData.setAuthorId(registration.getAuthorId());
        registrationData.setGroupId(registration.getGroupId());
        registrationData.setCompanyId(companyId);
        return registrationData;
    }

    private List<RegistrationData> getRegistrationData(RegistrationResource registrationResource) {

        List<RegistrationData> data = new ArrayList<>();
        List<RegistrationPeriod> resourcePeriods = _registrationPeriodLocalService.findByResource(registrationResource.getRegistrationResourceId());

        List<nl.deltares.data.service.registration.model.Registration> registrations =
                _registrationLocalService.findByResource(registrationResource.getRegistrationResourceId());
        registrations.forEach(registration -> {

            RegistrationData registrationData = new RegistrationData();
            registrationData.setCompanyId(registrationResource.getCompanyId());
            registrationData.setGroupId(registrationResource.getGroupId());
            registrationData.setEventResourceId(registrationData.getResourceId());
            registrationData.setResourceId(registrationResource.getRegistrationResourceId());
            registrationData.setUserId(registration.getUserId());
            registrationData.setRegistrationRecordId(registration.getRegistrationId());
            for (RegistrationPeriod resourcePeriod : resourcePeriods) {
                registrationData.addPeriod(new Period(resourcePeriod.getStartTime(), resourcePeriod.getEndTime()));
            }
            List<RegistrationAttribute> attributes = _registrationAttributeLocalService.findByRegistration(registration.getRegistrationId());
            for (RegistrationAttribute attribute : attributes) {
                registrationData.putAttribute(attribute.getName(), attribute.getValue());
            }
            data.add(registrationData);
        });
        return data;
    }

    @Reference
    private RegistrationResourceLocalService _registrationResourceLocalService;
    @Reference
    private RegistrationPeriodLocalService _registrationPeriodLocalService;
    @Reference
    private RegistrationLocalService _registrationLocalService;
    @Reference
    private RegistrationAttributeLocalService _registrationAttributeLocalService;
    @Reference
    private WebinarUtilsFactory _webinarUtilsFactory;

    private static final Log LOG = LogFactory.getLog(RegistrationUtilsImpl.class);

    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    static {
        dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
