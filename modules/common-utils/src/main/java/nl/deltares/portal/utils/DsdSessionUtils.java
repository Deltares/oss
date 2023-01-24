package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;

import java.util.List;

import java.util.Map;

public interface DsdSessionUtils {

    void deleteRegistrationRecord(long registrationId) throws PortalException;

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
    List<Map<String, Object>> getRegistrations(int start, int end);

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
    String getUserJoinLink(User user, Registration registration) throws Exception;

    /**
     * Register user for Registration
     * @param user User to register
     * @param userAttributes Additional user information required by registration process.
     * @param registration Registration for which user must be registered
     * @param registrationProperties Optional additional properties linked to this registration.
     * @param registeredBy If registering for someone else add user making the registration
     */
    void registerUser(User user, Map<String, String> userAttributes, Registration registration, Map<String, String> registrationProperties, User registeredBy) throws PortalException;

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

    /** Return list of registrations that reference this registration a parent.
     *
     * @param registration Parent registration
     * @return List of child registartions
     */
    List<Registration> getChildRegistrations(Registration registration) throws PortalException;

    /** Return map containing registration preferences
     *
     */
    Map<String, String> getUserPreferences(User user, Registration registration) throws PortalException;

    /**
     * Check if user is already registerd for this registration.
     * @param user User to check for registration
     * @param registration Registration to check
     * @return TRUE if user is registered else false
     */
    boolean isUserRegisteredFor(User user, Registration registration);

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
    void deleteRegistrationsFor(Registration registration);

    /**
     * Delete all registrations linked to this registration article
     * @param groupId Registration groupId
     * @param resourceId Registration resourcePrimaryKey
     */
    void deleteRegistrationsFor(long groupId, long resourceId) throws PortalException;

    /**
     * Get user's registrations records for given event.
     * @param user User Id
     * @param event Event for which to retrieve registrations
     * @return List of user registration records
     */
    List<Map<String, Object>> getUserRegistrations(User user, Event event);

    /**
     * Find all user registrations that this user made for other user for given event .
     * @param user User Id of user that made registration
     * @param event Event for which to retrieve registrations
     * @return List of user registration records
     */
    List<Map<String, Object>> getUserRegistrationsMadeForOthers(User user, Event event);

    /**
     * Has this user made registrations for others for this event .
     * @param user User Id of user that made registration
     * @param groupId Site Id,
     * @param eventArticleId Configured active Event article ID
     * @return true or false
     */
    boolean hasUserRegistrationsMadeForOthers(User user, long groupId, long eventArticleId);

    /**
     * Get all registrations records for given event.
     * @param event Event for which to retrieve registrations
     * @return List of user registration records
     */
    List<Map<String, Object>> getRegistrations(Event event);

    /**
     * Get all registrations by registration resourceId.
     * @param groupId Registration groupid
     * @param resourceId Registration resourceid
     * @return List of user registration records
     */
    List<Map<String, Object>> getRegistrations(long groupId, long resourceId);

    /**
     * Get all registrations by event resourceId.
     * @param groupId Registration groupid
     * @param eventResourceId Event resourceid
     * @return List of user registration records
     */
    List<Map<String, Object>> getEventRegistrations(long groupId, long eventResourceId);

    void deleteEventRegistrations(long groupId, long resourceId);
}
