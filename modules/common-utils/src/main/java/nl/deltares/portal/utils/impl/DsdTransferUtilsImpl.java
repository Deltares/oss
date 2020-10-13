package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.dsd.registration.service.RegistrationLocalService;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.BusTransfer;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdTransferUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component(
        immediate = true,
        service = DsdTransferUtils.class
)
public class DsdTransferUtilsImpl implements DsdTransferUtils {

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

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
    public List<Date> getRegisteredDays(User user, Registration registration) {
        return registrationLocalService.getRegistrationDates(registration.getGroupId(), user.getUserId(), registration.getResourceId());
    }

    @Override
    public void registerUser(User user, Registration registration, Date transferDate) throws PortalException {

        if (isUserRegisteredFor(user, registration, transferDate)) return;

        validateRegistration(user, registration, transferDate);

        registrationLocalService.addUserRegistration(
                registration.getCompanyId(), registration.getGroupId(), registration.getResourceId(),
                0, user.getUserId(),
                transferDate, transferDate, null);
    }

    @Override
    public void unRegisterUser(User user, Registration registration) {
        registrationLocalService.deleteUserRegistrationAndChildRegistrations(
                registration.getGroupId(), registration.getResourceId(), user.getUserId());
    }

    @Override
    public void unRegisterUser(User user, Registration registration, Date transferDate) throws PortalException {
        registrationLocalService.deleteUserRegistration(
                registration.getGroupId(), registration.getResourceId(), user.getUserId(), transferDate);
    }

    @Override
    public void validateRegistration(User user, Registration registration, Date transferDate) throws PortalException {

        if (!registration.isOpen()) {
            throw new ValidationException(String.format("Bus transfer %s is not open!", registration.getTitle()));
        }

        try {
            if (!isValidDate(registration, transferDate)) {
                throw new ValidationException(String.format("Transfer date %s does not lie with valid period!", dayFormat.format(transferDate)));
            }
        } catch (ParseException e){
            throw new PortalException("Error parsing dates: " + e.getMessage());
        }

        if (registration.getCapacity() != Integer.MAX_VALUE && getRegistrationCount(registration, transferDate) >= registration.getCapacity()) {
            throw new ValidationException(String.format("Bus transfer %s is full!", registration.getTitle()));
        }

    }

    private boolean isValidDate(Registration registration, Date transferDate) throws ParseException {

        if (registration instanceof BusTransfer){
            BusTransfer transfer = (BusTransfer) registration;
            List<Date> transferDates = transfer.getTransferDates();
            return transferDates.contains(transferDate);
        } else {
            //strip time from date
            long startDay = dayFormat.parse(dayFormat.format(new Date(registration.getStartTime().getTime()))).getTime();
            long endDay = dayFormat.parse(dayFormat.format(new Date(registration.getEndTime().getTime()))).getTime();
            long transferDay = transferDate.getTime();
            return startDay >= transferDay || endDay >= transferDay;

        }
    }

    @Override
    public int getRegistrationCount(Registration registration, Date transferDate) {
        return registrationLocalService.getRegistrationsCount(registration.getGroupId(), registration.getResourceId(), transferDate);
    }

    @Override
    public boolean isUserRegisteredFor(User user, Registration registration, Date transferDate) {
        int registrationsCount = registrationLocalService.getRegistrationsCount(registration.getGroupId(), user.getUserId(), registration.getResourceId(), transferDate);
        return registrationsCount > 0;
    }

}
