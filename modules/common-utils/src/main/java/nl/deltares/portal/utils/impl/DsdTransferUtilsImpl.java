package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.dsd.registration.service.RegistrationLocalService;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdTransferUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        service = DsdTransferUtils.class
)
public class DsdTransferUtilsImpl implements DsdTransferUtils {

    private RegistrationLocalService registrationLocalService;

    private DsdParserUtils dsdParserUtils;

    @Reference(unbind = "-")
    private void setRepositoryLogLocalService(RegistrationLocalService registrationLocalService) {

        this.registrationLocalService = registrationLocalService;
    }

    @Reference(unbind = "-")
    private void setDsdParserUtils(DsdParserUtils dsdParserUtils) {
        this.dsdParserUtils = dsdParserUtils;
    }

    @Override
    public void registerUser(User user, Registration registration, User registrationUser) throws PortalException {

        if (isUserRegisteredFor(user, registration)) return;

        validateRegistration(user, registration);
        Event event = dsdParserUtils.getEvent(registration.getGroupId(), String.valueOf(registration.getEventId()), registration.getLocale());

        Registration parentRegistration = registration.getParentRegistration();

        long registeredByUserId = 0;
        if (registrationUser != null && registrationUser != user) {
            registeredByUserId = registrationUser.getUserId();
        }
        registrationLocalService.addUserRegistration(
                registration.getCompanyId(), registration.getGroupId(), registration.getResourceId(),
                event == null ? 0 : event.getResourceId(),
                parentRegistration == null ? 0 : parentRegistration.getResourceId(), user.getUserId(),
                registration.getStartTime(), registration.getEndTime(), null, registeredByUserId);
    }

    @Override
    public void unRegisterUser(User user, Registration registration) {
        registrationLocalService.deleteUserRegistrationAndChildRegistrations(
                registration.getGroupId(), registration.getResourceId(), user.getUserId());
    }

    @Override
    public void validateRegistration(User user, Registration registration) throws PortalException {
        if (user.isDefaultUser()) return;
        if (!registration.isOpen()) {
            throw new ValidationException(String.format("Bus transfer %s is not open!", registration.getTitle()));
        }

        if (registration.getCapacity() != Integer.MAX_VALUE && getRegistrationCount(registration) >= registration.getCapacity()) {
            throw new ValidationException(String.format("Bus transfer %s is full!", registration.getTitle()));
        }

    }

    @Override
    public int getRegistrationCount(Registration registration) {
        return registrationLocalService.getRegistrationsCount(registration.getGroupId(), registration.getResourceId());
    }

    @Override
    public boolean isUserRegisteredFor(User user, Registration registration) {
        if (user.isDefaultUser()) return false;

        int registrationsCount = registrationLocalService.getRegistrationsCount(registration.getGroupId(), user.getUserId(), registration.getResourceId());
        return registrationsCount > 0;
    }

}
