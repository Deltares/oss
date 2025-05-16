package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.*;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.forms.constants.OrganizationConstants;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.portal.utils.AccountUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountSelectionCheckoutStepDisplayContext{

    final AccountEntryLocalService _accountEntryLocalService;
    final AddressLocalService _addressLocalService;
    final CountryLocalService _countryLocalService;
    final PhoneLocalService  _phoneLocalService;
    final UserLocalService _userLocalService;
    final AccountUtils _commerceUtils;

    final ThemeDisplay _themeDisplay;
    final User _user;
    final User _accountUser;

    private boolean _accountsLoaded = false;
    private final List<AccountEntry> accounts = new ArrayList<>();

    public AccountSelectionCheckoutStepDisplayContext(HttpServletRequest request, AccountEntryLocalService accountEntryLocalService,
                                                      AddressLocalService addressLocalService, CountryLocalService countryLocalService,
                                                      PhoneLocalService phoneLocalService, UserLocalService userLocalService,
                                                      AccountUtils commerceUtils) {

        _accountEntryLocalService = accountEntryLocalService;
        _addressLocalService = addressLocalService;
        _countryLocalService = countryLocalService;
        _phoneLocalService = phoneLocalService;
        _userLocalService = userLocalService;
        _commerceUtils = commerceUtils;
        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _user = _themeDisplay.getUser();
        _accountUser = getAccountUser();
    }

    public String getTitle() {
        return "account-info";
    }

    public String getParamName() {
        return "accountEntryId";
    }

    public long getCompanyId() {
        return 10131;
    }

    public void loadAccounts() {

        if (_accountsLoaded) {
            return;
        }
        if (!_user.isGuestUser()) {
            String domain = _user.getEmailAddress().split("@")[1];
            accounts.addAll(_commerceUtils.getAccountsByDomain(domain, getCompanyId()));
            AccountEntry accountEntry = _commerceUtils.getPersonalAccount(_accountUser);
            if (accountEntry != null) {
                accounts.add(accountEntry);
            }
            _accountsLoaded = true;
        }
    }

    public boolean canCreateNewAccount() {
        loadAccounts();
        if (accounts.isEmpty()) return true;

        for (AccountEntry account : accounts) {
            //Already a personal account created, cannot create new
            if (account.isPersonalAccount()) return false;
        }
        return true;
    }

    public List<AccountEntry> getAccountEntries() {
        loadAccounts();
        return accounts;
    }

    public String getCompanyReferenceCode(AccountEntry accountEntry) {
        if (accountEntry != null) {
            return (String) accountEntry.getExpandoBridge().getAttribute(OrganizationConstants.ORG_REGISTRATION_ID, false);
        }
        return "";
    }

    public String getAccountWebsite(AccountEntry accountEntry) {
        if (accountEntry != null) {
            return (String) accountEntry.getExpandoBridge().getAttribute(OrganizationConstants.ORG_WEBSITE, false);
        }
        return "";
    }

    public AccountEntry getAccountEntry(long accountEntryId) {
        if (!_accountsLoaded) {
            loadAccounts();
        }
        for (AccountEntry account : accounts) {
            if (account.getAccountEntryId() != accountEntryId) continue;
            return account;
        }
        return null;
    }

    public Address addOrUpdateBillingAddress(HttpServletRequest request, AccountEntry accountEntry, long selectedAddressId) throws PortalException {

        long countryId = ParamUtil.getLong(request, OrganizationConstants.ORG_COUNTRY_ID);
        Country country = _countryLocalService.fetchCountry(countryId);
        if (country == null) {
            throw new RegistrationFormException(String.format("Country with ID '%d' does not exist!", countryId));
        }
        if (!country.isBillingAllowed()) {
            throw new RegistrationFormException(String.format("It is not allowed to do business with country '%s'", country.getName()));
        }
        Address billingAddress;
        if (selectedAddressId > 0){
            billingAddress = _addressLocalService.fetchAddress(selectedAddressId);
        } else if (accountEntry.isPersonalAccount()) {
            billingAddress = _addressLocalService.fetchAddressByExternalReferenceCode("address_" +
                    _accountUser.getScreenName(), getCompanyId());
        } else {
            billingAddress = null;
        }
        Country companyCountry = _countryLocalService.getCountryByA2(getCompanyId(), country.getA2());
        String name = ParamUtil.getString(request, OrganizationConstants.ORG_ADDRESS_NAME);
        String street = ParamUtil.getString(request, OrganizationConstants.ORG_STREET);
        String city = ParamUtil.getString(request, OrganizationConstants.ORG_CITY);
        String postal = ParamUtil.getString(request, OrganizationConstants.ORG_POSTAL);
        long regionId = ParamUtil.getLong(request, OrganizationConstants.ORG_REGION);
        String phoneNumber = ParamUtil.getString(request, OrganizationConstants.ORG_PHONE);

        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setScopeGroupId(accountEntry.getAccountEntryGroupId());
        serviceContext.setCompanyId(accountEntry.getCompanyId());
        serviceContext.setUserId(accountEntry.getUserId());
        if (billingAddress == null) {
            final ListType accountType = ListTypeLocalServiceUtil.getListType(accountEntry.getCompanyId(),
                    "billing", "com.liferay.account.model.AccountEntry.address");
            billingAddress = _addressLocalService.addAddress("address_" + _accountUser.getScreenName(),
                    _accountUser.getUserId(), AccountEntry.class.getName(),
                    accountEntry.getAccountEntryId(), name, null, street, null, null,
                    city, postal, regionId, companyCountry.getCountryId(), accountType.getListTypeId(), true,
                    true, null, serviceContext);

            billingAddress = _addressLocalService.addAddress(billingAddress);
            accountEntry.setDefaultBillingAddressId(billingAddress.getAddressId());
            _accountEntryLocalService.updateAccountEntry(accountEntry);
        } else {
            billingAddress.setName(name);
            billingAddress.setStreet1(street);
            billingAddress.setZip(postal);
            billingAddress.setCity(city);
            billingAddress.setCountryId(companyCountry.getCountryId());
            billingAddress.setRegionId(regionId);
            billingAddress = _addressLocalService.updateAddress(billingAddress);
        }

        if (phoneNumber != null) {
            List<Phone> phones = _phoneLocalService.getPhones(getCompanyId(), "com.liferay.portal.kernel.model.Address", billingAddress.getAddressId());
            Phone phone;
            if (phones.isEmpty()) {
                final ListType phoneType = ListTypeLocalServiceUtil.getListType(accountEntry.getCompanyId(),
                        "phone-number", "com.liferay.portal.kernel.model.Address.phone");
                _phoneLocalService.addPhone(null,
                        _accountUser.getUserId(), "com.liferay.portal.kernel.model.Address", billingAddress.getAddressId(),
                        phoneNumber, null, phoneType.getListTypeId(), true, serviceContext);
            } else {
                phone = phones.get(0);
                phone.setNumber(phoneNumber);
                _phoneLocalService.updatePhone(phone);
            }
        }
        return billingAddress;
    }

    public AccountEntry storeAccountInfo(HttpServletRequest httpServletRequest) {

        long accountEntryId = ParamUtil.getLong(httpServletRequest, getParamName());

        if (accountEntryId == 0 && !canCreateNewAccount()) return null;

        AccountEntry accountEntry;
        try {
            accountEntry = addOrUpdateAccountEntry(httpServletRequest, accountEntryId);
        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class.getName(),
                    Collections.singletonList(new RegistrationFormException(e.getMessage())));
            return null;
        }

        if (accountEntry != null && accountEntry.isPersonalAccount()) {
            try {
                addOrUpdateBillingAddress(httpServletRequest, accountEntry,
                        accountEntry.getDefaultBillingAddressId());
            } catch (Exception e) {
                SessionErrors.add(httpServletRequest, RegistrationFormException.class.getName(),
                        Collections.singletonList(new RegistrationFormException(e.getMessage())));
                return null;
            }
        }
        return accountEntry;
    }

    AccountEntry addOrUpdateAccountEntry(HttpServletRequest request, long accountEntryId) throws PortalException {

        AccountEntry accountEntry;
        if (accountEntryId == 0){
            accountEntry = _commerceUtils.createPersonAccountEntry(_accountUser);
        } else {
            accountEntry = _accountEntryLocalService.fetchAccountEntry(accountEntryId);
        }

        if (accountEntry != null && accountEntry.isPersonalAccount()) {
            String name = ParamUtil.getString(request, OrganizationConstants.ORG_NAME);

            if (name == null || name.isEmpty()) {
                throw new RegistrationFormException("Account name field is required!");
            }

            String website = ParamUtil.getString(request, OrganizationConstants.ORG_WEBSITE);
            String companyRegistrationId = ParamUtil.getString(request, OrganizationConstants.ORG_REGISTRATION_ID);
            String taxIdNumber = ParamUtil.getString(request, OrganizationConstants.ORG_VAT);

            accountEntry.setTaxIdNumber(taxIdNumber);
            accountEntry.setName(name);
            accountEntry.getExpandoBridge().setAttribute(OrganizationConstants.ORG_REGISTRATION_ID, companyRegistrationId, false);
            accountEntry.getExpandoBridge().setAttribute(OrganizationConstants.ORG_WEBSITE, website, false);
            return _accountEntryLocalService.updateAccountEntry(accountEntry);
        }
        return accountEntry;
    }

    /**
     * Get the equivalent of the logged in user from the central Company where all account data is stored.
     * @return
     */
    private User getAccountUser() {
        long accountCompanyId = getCompanyId();
        if (accountCompanyId != _user.getCompanyId()){
            User user = _userLocalService.fetchUserByEmailAddress(accountCompanyId, _user.getEmailAddress());
            if (user == null) {
                user =_userLocalService.createUser(CounterLocalServiceUtil.increment(User.class.getName()));
                user.setCompanyId(accountCompanyId);
                user.setScreenName(_user.getScreenName());
                user.setEmailAddress(_user.getEmailAddress());
                user.setFirstName(_user.getFirstName());
                user.setLastName(_user.getLastName());
                return _userLocalService.addUser(user);
            } else {
                return user;
            }
        } else {
            return _user;
        }
    }

    public Address getAccountAddress(AccountEntry accountEntry) {
        if (accountEntry == null){
            return null;
        }
        if (accountEntry.getDefaultBillingAddress() != null){
            return accountEntry.getDefaultBillingAddress();
        }
        if (accountEntry.isPersonalAccount()){
            try {
                return _addressLocalService.getAddressByExternalReferenceCode("address_"
                        + _accountUser.getScreenName(), getCompanyId());
            } catch (PortalException e) {
                //
            }
        }
        return null;
    }

}
