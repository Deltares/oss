package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.dsd.registration.exception.NoSuchRegistrationException;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.Registration;

import java.util.Date;
import java.util.List;

public interface DsdTransferUtils {

    /**
     * Return the days for which the user is registered.
     */
    List<Date> getRegisteredDays(User user, Registration registration) throws PortalException;

    /**
     * Register user for single date of this Registration
     * @param user User to register
     * @param registration Registration for which user must be registered
     * @param transferDate Date of transfer for which user is registering.
     */
    void registerUser(User user, Registration registration, Date transferDate) throws PortalException;

    /**
     * Unregister user for single date of this Registration
     * @param user User to unregister
     * @param registration Registration for which user must be unregistered
     * @param transferDate Day for which user wants to unregister
     */
    void unRegisterUser(User user, Registration registration, Date transferDate) throws NoSuchRegistrationException;


    /**
     * Unregister user for all dates of this Registration
     * @param user User to unregister
     * @param registration Registration for which user must be unregistered
     */
    void unRegisterUser(User user, Registration registration);

    /**
     * Return number of current registrations for Registration.
     * @param registration Registration Article
     * @param transferDate Day for which to retrieve count
     * @return number or registrations
     */
    int getRegistrationCount(Registration registration, Date transferDate);

    /**
     * Check if user is already registerd for this registration.
     * @param user User to check for registration
     * @param registration Registration to check
     * @param transferDate Day for which to check if user is registered
     * @return TRUE if user is registered else false
     */
    boolean isUserRegisteredFor(User user, Registration registration, Date transferDate);

    /**
     * Validates if user can register for current Registration.
     * @param user User wanting to register
     * @param registration Registration user wants to register for
     * @param transferDate Day for which to validate
     * @throws ValidationException Thrown if registration is not valid.
     */
    void validateRegistration(User user, Registration registration, Date transferDate) throws PortalException;

    /**
     * Delete all registrations linked to this registration article
     * @param registration Registration Journal Article being deleted
     */
    void deleteRegistrationsFor(Registration registration);

}
