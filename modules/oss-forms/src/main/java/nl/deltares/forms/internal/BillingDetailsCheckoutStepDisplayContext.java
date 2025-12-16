package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.forms.constants.BillingConstants;
import nl.deltares.forms.constants.OrganizationConstants;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.AccountUtils;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BillingDetailsCheckoutStepDisplayContext extends AccountSelectionCheckoutStepDisplayContext {

    private BillingInfo _billingInfo;
    private AccountEntry _selectedAccountEntry = null;
    private Boolean _paymentRequired = null;

    public String getTitle() {
        return "billing-info";
    }

    public String getParamName() {
        return "billingAddressId";
    }

    public BillingDetailsCheckoutStepDisplayContext(HttpServletRequest request, AddressLocalService addressLocalService,
                                                    AccountEntryLocalService accountEntryLocalService,
                                                    CountryLocalService countryLocalService, PhoneLocalService phoneLocalService,
                                                    UserLocalService userLocalService, AccountUtils commerceUtils,
                                                    ConfigurationProvider configurationProvider) throws Exception {

        super(request, accountEntryLocalService, addressLocalService, countryLocalService, phoneLocalService, userLocalService,
                commerceUtils, configurationProvider);

        _billingInfo = (BillingInfo) request.getSession().getAttribute("billingInfo");
        if (_billingInfo == null) {
            _billingInfo = new BillingInfo();
            _billingInfo.setEmail(_user.getEmailAddress());
            _billingInfo.setFirstName(_user.getFirstName());
            _billingInfo.setLastName(_user.getLastName());
        }
        Object selectedAccountEntryId = request.getSession().getAttribute("selectedAccountEntryId");
        if (selectedAccountEntryId != null) {
            _selectedAccountEntry = getAccountEntry((Long) selectedAccountEntryId);
        }
        if (_selectedAccountEntry != null) {
            _billingInfo.setVat(_selectedAccountEntry.getTaxIdNumber());
            Address billingAddress = getAccountAddress(_selectedAccountEntry);
            if (billingAddress != null) {
                _billingInfo.setBillingAddressId(billingAddress.getAddressId());
            }
        }
    }

    public boolean canEditAccount() {
        return _selectedAccountEntry != null && _selectedAccountEntry.isPersonalAccount();
    }

    public boolean canEditAddress(long addressId) {
        if (_selectedAccountEntry == null) return false;
        Address defaultBillingAddress = _selectedAccountEntry.getDefaultBillingAddress();
        if (defaultBillingAddress == null) return true;
        return defaultBillingAddress.getAddressId() != addressId;
    }

    public List<Address> getBillingAddresses() {

        if (_selectedAccountEntry == null) return Collections.emptyList();

        List<Address> addresses = _addressLocalService.getAddresses(getCompanyId(), AccountEntry.class.getName(),
                _selectedAccountEntry.getAccountEntryId());

        if (addresses.isEmpty()) {
            return Collections.singletonList(_selectedAccountEntry.getDefaultBillingAddress());
        }
        return addresses;
    }

    public BillingInfo storeBillingInformation(HttpServletRequest httpServletRequest) throws Exception {

        if (_selectedAccountEntry.isPersonalAccount()) {
            boolean updated = false;
            String taxIdNumber = ParamUtil.getString(httpServletRequest, OrganizationConstants.ORG_VAT);
            if (taxIdNumber != null && !taxIdNumber.equals(_selectedAccountEntry.getTaxIdNumber())){
                updated = true;
                _selectedAccountEntry.setTaxIdNumber(taxIdNumber);
            }
            if (updated) {
                _accountEntryLocalService.updateAccountEntry(_selectedAccountEntry);
            }
        }
        long selectedBillingAddressId = ParamUtil.getLong(httpServletRequest, getParamName());
        if (canEditAccount() || selectedBillingAddressId == 0){
            Address billingAddress = addOrUpdateBillingAddress(httpServletRequest, _selectedAccountEntry, selectedBillingAddressId);
            _billingInfo.setDefaultBillingAddress(billingAddress.getAddressId() == _selectedAccountEntry.getDefaultBillingAddressId());
            _billingInfo.setBillingAddressId(billingAddress.getAddressId());
            _billingInfo.setAddress(billingAddress.getStreet1());
            _billingInfo.setCity(billingAddress.getCity());
            _billingInfo.setPostal(billingAddress.getZip());
            _billingInfo.setCountry(billingAddress.getCountry().getName());
        }

        _billingInfo.setVat(_selectedAccountEntry.getTaxIdNumber());
        _billingInfo.setCompanyName(_selectedAccountEntry.getName());

        _billingInfo.setEmail(ParamUtil.getString(httpServletRequest, BillingConstants.EMAIL));
        _billingInfo.setFirstName(ParamUtil.getString(httpServletRequest, BillingConstants.FIRST_NAME));
        _billingInfo.setLastName(ParamUtil.getString(httpServletRequest, BillingConstants.LAST_NAME));
        _billingInfo.setPhoneNumber(ParamUtil.getString(httpServletRequest, BillingConstants.ORG_PHONE));
        _billingInfo.setPaymentyPreference(ParamUtil.getString(httpServletRequest, BillingConstants.PAYMENT_METHOD));
        _billingInfo.setPaymentReference(ParamUtil.getString(httpServletRequest, BillingConstants.PAYMENT_REFERENCE));


        return _billingInfo;
    }

    public BillingInfo getBillingInfo() {
        return _billingInfo;
    }

    public void validateRequestData(HttpServletRequest httpServletRequest) {

        if (_selectedAccountEntry == null) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(
                    new RegistrationFormException("Account selection is missing!")));
            return;
        }

        long countryId = ParamUtil.getLong(httpServletRequest, OrganizationConstants.ORG_COUNTRY_ID);
        Country country = _countryLocalService.fetchCountry(countryId);
        if (country != null && !country.isBillingAllowed()) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(
                    new RegistrationFormException(String.format("It is not allowed to do business with country '%s'", country.getName()))));
            return;
        }

        String vatId = ParamUtil.getString(httpServletRequest, OrganizationConstants.ORG_VAT);
        if (_selectedAccountEntry.isPersonalAccount() && vatId.isEmpty()){
            List<RegistrationInfo> registrationInfos = (List<RegistrationInfo>) httpServletRequest.getSession().getAttribute("registrationInfos");
            if (registrationInfos != null && !registrationInfos.isEmpty()){
                Optional<RegistrationInfo> payedEvent = registrationInfos.stream().filter(registrationInfo -> registrationInfo.getPrice() > 0).findFirst();
                if (payedEvent.isPresent()){
                    SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(
                            new RegistrationFormException("Tax identification number required for payed events! If not applicable please enter 'n/a'.")));

                }
            }

        }

    }

    public boolean isPaymentRequired(HttpServletRequest httpServletRequest, DsdParserUtils dsdParserUtils) {

        if (_paymentRequired != null) return _paymentRequired;
        _paymentRequired = false;
        String ids = ParamUtil.getString(httpServletRequest, "ids");
        String[] registrationIds = ids.split(",", -1);
        for (String registrationId : registrationIds) {
            if (registrationId == null || registrationId.isEmpty()) continue;
            Registration registration;
            try {
                registration = dsdParserUtils.getRegistration(
                        _themeDisplay.getScopeGroupId(), registrationId);
            } catch (PortalException e) {
                continue;
            }
            if (registration.getPrice() > 0) {
                _paymentRequired = true;
                break;
            }
        }
        return _paymentRequired;
    }

}
