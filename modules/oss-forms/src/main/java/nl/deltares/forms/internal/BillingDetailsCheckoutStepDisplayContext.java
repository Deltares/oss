package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.forms.constants.BillingConstants;
import nl.deltares.forms.constants.OrganizationConstants;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.model.AccountInfo;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.AccountUtils;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BillingDetailsCheckoutStepDisplayContext extends AccountSelectionCheckoutStepDisplayContext {

    private final DsdParserUtils _dsdParserUtils;
    private final AccountEntry _selectedAccountEntry;
    private final BillingInfo _billingInfo;

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
                                                    ConfigurationProvider configurationProvider, DsdParserUtils dsdParserUtils) throws Exception {

        super(request, accountEntryLocalService, addressLocalService, countryLocalService, phoneLocalService, userLocalService,
                commerceUtils, configurationProvider);

        _dsdParserUtils = dsdParserUtils;
        AccountInfo accountInfo = getAccountInfo();
        _selectedAccountEntry = accountInfo.getSelectedAccountEntry();

        Object billingInfo = request.getSession().getAttribute("billing-info");
        if (billingInfo == null){
            _billingInfo = new BillingInfo();
            User _user = getCurrentUser();
            _billingInfo.setEmail(_user.getEmailAddress());
            _billingInfo.setFirstName(_user.getFirstName());
            _billingInfo.setLastName(_user.getLastName());
            request.getSession().setAttribute("billing-info", _billingInfo);
        } else {
            _billingInfo = (BillingInfo) billingInfo;
        }
        loadRegistrations(request, _billingInfo);

        if (_selectedAccountEntry != null){
            _billingInfo.setVat(_selectedAccountEntry.getTaxIdNumber());
        } else {
            _billingInfo.setVat(null);
        }
    }

    public boolean canAddAddress(){
        boolean hasEditableAddress = false;
        List<Address> billingAddresses = getBillingAddresses();
        for (Address billingAddress : billingAddresses) {
            if (canEditAddress(billingAddress.getAddressId())){
                hasEditableAddress = true;
            }
        }
        return !hasEditableAddress;
    }

    public boolean canEditAccount() {
        return _selectedAccountEntry != null && _selectedAccountEntry.isPersonalAccount();
    }

    public boolean canEditAddress(long addressId) {
        if (_selectedAccountEntry == null) return false;
        if (_selectedAccountEntry.isPersonalAccount()) return true;
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

    public void storeBillingInformation(HttpServletRequest httpServletRequest) throws Exception {

        if (_selectedAccountEntry == null){
            throw new IllegalStateException("No account entry selected");
        }
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
        Address billingAddress;
        if (canEditAddress(selectedBillingAddressId)){
            billingAddress = addOrUpdateBillingAddress(httpServletRequest, _selectedAccountEntry, selectedBillingAddressId);
        } else {
            billingAddress = _addressLocalService.fetchAddress(selectedBillingAddressId);
        }
        //Set address values
        _billingInfo.setBillingAddressId(billingAddress.getAddressId());
        _billingInfo.setDefaultBillingAddress(_billingInfo.getBillingAddressId() == _selectedAccountEntry.getDefaultBillingAddressId());
        _billingInfo.setAddress(billingAddress.getStreet1());
        _billingInfo.setCity(billingAddress.getCity());
        _billingInfo.setPostal(billingAddress.getZip());
        _billingInfo.setCountry(billingAddress.getCountry().getName());
        //Set company values
        _billingInfo.setVat(_selectedAccountEntry.getTaxIdNumber());
        _billingInfo.setCompanyName(_selectedAccountEntry.getName());
        //Set user values
        _billingInfo.setEmail(ParamUtil.getString(httpServletRequest, BillingConstants.EMAIL));
        _billingInfo.setFirstName(ParamUtil.getString(httpServletRequest, BillingConstants.FIRST_NAME));
        _billingInfo.setLastName(ParamUtil.getString(httpServletRequest, BillingConstants.LAST_NAME));
        _billingInfo.setPhoneNumber(ParamUtil.getString(httpServletRequest, BillingConstants.ORG_PHONE));
        _billingInfo.setPaymentyPreference(ParamUtil.getString(httpServletRequest, BillingConstants.PAYMENT_METHOD));
        _billingInfo.setPaymentReference(ParamUtil.getString(httpServletRequest, BillingConstants.PAYMENT_REFERENCE));

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
        if (countryId == 0) {
            long selectedBillingAddressId = ParamUtil.getLong(httpServletRequest, getParamName());
            Address address = _addressLocalService.fetchAddress(selectedBillingAddressId);
            if (address != null) {
                countryId = address.getCountryId();
            }
        }
        Country country = _countryLocalService.fetchCountry(countryId);
        if (country != null && !country.isBillingAllowed()) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(
                    new RegistrationFormException(String.format(
                            LanguageUtil.get(httpServletRequest, "sanctioned-country-notification"), country.getName()))));
            return;
        }

        String vatId = ParamUtil.getString(httpServletRequest, OrganizationConstants.ORG_VAT);
        if (_selectedAccountEntry.isPersonalAccount() && vatId.isEmpty()){
            List<RegistrationInfo> registrationInfos = (List<RegistrationInfo>) httpServletRequest.getSession().getAttribute("registrationInfos");
            if (registrationInfos != null && !registrationInfos.isEmpty()){
                Optional<RegistrationInfo> payedEvent = registrationInfos.stream().filter(registrationInfo -> registrationInfo.getPrice() > 0).findFirst();
                if (payedEvent.isPresent()){
                    SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(
                            new RegistrationFormException(LanguageUtil.get(httpServletRequest,
                                    "vat-number-required-for-paid-registrations"))));

                }
            }
        }
    }

    public boolean isPaymentRequired() {
        return _billingInfo.isPaymentRequired();
    }

    private void loadRegistrations(HttpServletRequest httpServletRequest, BillingInfo billingInfo) {
        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        ThemeDisplay _themeDisplay = cpRequestHelper.getThemeDisplay();

        String ids = ParamUtil.getString(httpServletRequest, "ids");
        String[] registrationIds = ids.split(",", -1);

        List<Registration> newRegistrations = new ArrayList<>();
        for (String registrationId : registrationIds) {
            if (registrationId == null || registrationId.isEmpty()) continue;
            Registration registration = billingInfo.getRegistration(registrationId);

            if (registration == null) {
                try {
                    registration = _dsdParserUtils.getRegistration(
                            _themeDisplay.getScopeGroupId(), registrationId);
                } catch (PortalException e) {
                    continue;
                }
            }
            if (registration != null){
                newRegistrations.add(registration);
            }
        }
        billingInfo.setRegistrations(newRegistrations);
    }

}
