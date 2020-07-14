package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;

import java.util.List;
import java.util.Map;

public interface DsdSessionUtils {

    /**
     * Register user for Registration
     * @param user User to register
     * @param registration Registration for which user must be registered
     * @param userProperties Optional additional properties linked to this registration.
     */
    void registerUser(User user, Registration registration, Map<String, String> userProperties) throws PortalException;
  
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
     * @param registration Parten registration
     * @return List of child registartions
     */
    List<Registration> getChildRegistrations(Registration registration) throws PortalException;

    /**
     * Get all user registration with a period that overlap that of the argument 'registration'.
     * @param user User for which to check overlaps
     * @param registration Registration for which to validate period.
     * @return List of registrations with overlapping period
     */
    List<Registration> getOverlappingRegistrations(User user, Registration registration) throws PortalException;

    /**
     * Get list of missing user information that is required for making the reservation
     * @param user User for which to check field values
     * @param registration Registration for which to validate fields.
     * @return List of missing field names
     */
    List<String> getMissingUserInformation(User user, Registration registration) throws PortalException;

    /**
     * Check if user is already registerd for this registration.
     * @param user User to check for registration
     * @param registration Registration to check
     * @return TRUE if user is registered else false
     */
    boolean isUserRegisteredFor(User user, Registration registration);

    /**
     * Validates if user can register for current Registration.
     * @param user User wanting to register
     * @param registration Registration user wants to register for
     * @throws ValidationException Thrown if registration is not valid.
     */
    void validateRegistration(User user, Registration registration) throws PortalException;

    /**
     * Delete all registrations linked to this registration article
     * @param registration Registration Journal Article being deleted
     */
    void deleteRegistrationsFor(Registration registration);

    /**
     * Check if the capacity of session does not exceed room capacity.
     * @param registration Session registration
     * @throws ValidationException Thrown if room is too small.
     */
    void validateRoomCapacity(SessionRegistration registration) throws PortalException;

    /**
     * Get user's registrations records for given event.
     * @param user User Id
     * @param event Event for which to retrieve registrations
     * @return List of user registration records
     */
    List<Map<String, Object>> getUserRegistrations(User user, Event event);
}
