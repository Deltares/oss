package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.Registration;

import java.util.Date;
import java.util.List;

public interface DsdTransferUtils {

    /**
     * Register user for single date of this Registration
     * @param user User to register
     * @param registration Registration for which user must be registered
     * @param registeredBy If registering for someone else add user making the registration
     */
    void registerUser(User user, Registration registration, User registeredBy) throws PortalException;

    /**
     * Unregister user for all dates of this Registration
     * @param user User to unregister
     * @param registration Registration for which user must be unregistered
     */
    void unRegisterUser(User user, Registration registration);

    /**
     * Return number of current registrations for Registration.
     * @param registration Registration Article
     * @return number or registrations
     */
    int getRegistrationCount(Registration registration);

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

}
