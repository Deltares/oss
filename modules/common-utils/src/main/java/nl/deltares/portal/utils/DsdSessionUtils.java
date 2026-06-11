package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.database.RegistrationData;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;

import java.util.Date;
import java.util.List;

import java.util.Map;

public interface DsdSessionUtils {

    void deleteRegistration(long registrationId) throws PortalException;

    /**
     * Get count for all records in registrations table
     * @return count
     */
    int getRegistrationCount();

    /**
     * Return registrations for indices
     * @param start start index
     * @param end end index
     * @return list of record values
     */
    List<RegistrationData> getRegistrations(int start, int end);

    /**
     * Retrieves the webinar join link for user.
     *
     * @param user User to Registration
     * @param registration Webinar registration
     * @param isRegistered If user is registered
     * @return Join link for given user if user. Pass user registration information for evaluation.
     */
    String getUserJoinLink(User user, Registration registration, boolean isRegistered) throws Exception;

    /**
     * Retrieves the webinar join link for user.
     *
     * @param user User to Registration
     * @param registration Webinar registration
     * @return Join link for given user if user. Checks if User is registered before returning link.
     */
    @SuppressWarnings("unused")
    String getUserJoinLink(User user, Registration registration) throws Exception;

    /**
     * Register user for Registration
     * @param user User to register
     * @param userAttributes Additional user information required by registration process.
     * @param registration Registration for which user must be registered
     * @param registrationProperties Optional additional properties linked to this registration.
     * @param registeredBy If registering for someone else add user making the registration
     */
    void registerUser(User user, Map<String, String> userAttributes, Registration registration, Map<String, String> registrationProperties, User registeredBy, Event event) throws PortalException;

    void registerUser(User user, Registration registration, Map<String, String> registrationProperties, User registrationUser, Event event) throws PortalException;

    /**
     * Unregister user for Registration
     * @param user User to unregister
     * @param registration Registration for which user must be unregistered
     */
    void unRegisterUser(User user, Registration registration) throws PortalException;

    /**
     * Return number of current registrations for Registration.
     * @param registration Registration Article
     * @return number or registrations
     */
    int getRegistrationCount(Registration registration);

    /**
     * Return map of site groupIds grouped tegether by their company groupId.
     * @return map of siteId grouped by company groupId
     */
    Map<Long, List<Long>> getRegistrationSiteIds(Long[] includeGroupIds, Long[] excludeGroupIds);

    /** Return list of registrations that reference this registration a parent.
     *
     * @param registration Parent registration
     * @return List of child registartions
     */
    List<Registration> getChildRegistrations(Registration registration, List<Registration> eventRegistrations) throws PortalException;

    /** Return map containing registration preferences
     *
     */
    Map<String, String> getUserPreferences(User user, Registration registration) throws PortalException;

    List<String> getOverlappingRegistrationTitles(long groupId, long userId, long resourceId, List<Period> periods, long parentResourceId);

    /**
     * Check if user is already registerd for this registration.
     * @param user User to check for registration
     * @param registration Registration to check
     * @return TRUE if user is registered else false
     */
    boolean isUserRegisteredFor(User user, Registration registration);

    boolean isUserRegisteredFor(long groupId, long userId, long resourceId);
    /**
     * Validates if user can register for all registrations in list.
     * @param user User wanting to register
     * @param registrations List of registrations that need to be registered
     * @param childRegistrations List of child registration belonging to the registrations
     * @throws ValidationException Thrown if registration is not valid.

     */
    void validateRegistrations(User user, List<Registration> registrations, List<Registration> childRegistrations) throws PortalException;

    /**
     * Delete all registrations linked to this registration article
     * @param registration Registration Journal Article being deleted
     */
    void deleteRegistrations(Registration registration);

    /**
     * Delete all registrations linked to this registration article
     * @param groupId Registration groupId
     * @param resourceId Registration resourcePrimaryKey
     */
    void deleteRegistrations(long groupId, long resourceId) throws PortalException;

    /**
     * Get user's registrations records for given event.
     * @param userId User Id
     * @param groupId Site id for which to retrieve registrations
     * @return List of user registration records
     */
    List<Long> getResourceIdsByUserAndGroup(long userId, long groupId);

    /**
     * Find all user registrations that this user made for other user .
     * @param userId User Id of user that made registration
     * @return List of user registration records
     */
    List<Long> getResourceIdsByAuthorAndGroup(long userId, long groupId);

    /**
     * Get registration data by resourceId and author of that registration.
     * @param authorId    User Id of user that made registration
     * @param resourceId  Registration resourceId
     * @return List of records
     */
    @SuppressWarnings("unused")
    List<RegistrationData> getRegistrationDataByAuthorAndResourceId(long authorId, long resourceId);

    /**
     * Get registration data by resourceId and user of that registration.
     * @param userId    User Id of user that made registration
     * @param resourceId  Registration resourceId
     * @return List of records
     */
    @SuppressWarnings("unused")
    List<RegistrationData> getRegistrationDataByUserAndResourceId(long userId, long resourceId);
    /**
     * Get all registrations records for given event.
     * @param event Event for which to retrieve registrations
     * @return List of user registration records
     */
    List<RegistrationData> getRegistrations(Event event);

    /**
     * Get all registrations by registration resourceId.
     * @param groupId Registration groupid
     * @param resourceId Registration resourceid
     * @return List of user registration records
     */
    List<RegistrationData> getRegistrations(long groupId, long resourceId);

    List<RegistrationData> getRegistrations(long groupId, Date startDate, Date endDate);

    /**
     * Get all registrations by event resourceId.
     * @param groupId Registration groupid
     * @param eventResourceId Event resourceid
     * @return List of user registration records
     */
    List<RegistrationData> getEventRegistrations(long groupId, long eventResourceId);

    void deleteEventRegistrations(long groupId, long resourceId);
}
