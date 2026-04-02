package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.*;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.forms.constants.OrganizationConstants;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.portlet.PortletPermissionUtils;
import nl.deltares.model.AccountInfo;
import nl.deltares.model.RegistrationFormContext;
import nl.deltares.portal.configuration.SiteMapConfiguration;
import nl.deltares.portal.utils.AccountUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class AccountSelectionCheckoutStepDisplayContext{

    final AccountEntryLocalService _accountEntryLocalService;
    final AddressLocalService _addressLocalService;
    final CountryLocalService _countryLocalService;
    final PhoneLocalService  _phoneLocalService;
    final UserLocalService _userLocalService;
    final AccountUtils _commerceUtils;

    private final AccountInfo _accountInfo;

    public AccountSelectionCheckoutStepDisplayContext(HttpServletRequest request, AccountEntryLocalService accountEntryLocalService,
                                                      AddressLocalService addressLocalService, CountryLocalService countryLocalService,
                                                      PhoneLocalService phoneLocalService, UserLocalService userLocalService,
                                                      AccountUtils commerceUtils, ConfigurationProvider configurationProvider) throws ConfigurationException {

        _accountEntryLocalService = accountEntryLocalService;
        _addressLocalService = addressLocalService;
        _countryLocalService = countryLocalService;
        _phoneLocalService = phoneLocalService;
        _userLocalService = userLocalService;
        _commerceUtils = commerceUtils;

        RegistrationFormContext context = (RegistrationFormContext) request.getSession().getAttribute("registration-context");
        if (context == null) {
            context = new RegistrationFormContext();
            request.getSession().setAttribute("registration-context", context);
        }
        AccountInfo accountInfo = context.getAccountInfo();
        if (accountInfo == null) {
            CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
            ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();

            SiteMapConfiguration _configuration = configurationProvider.getSystemConfiguration(SiteMapConfiguration.class);
            _accountInfo = new AccountInfo();
            _accountInfo.setCurrentUser(themeDisplay.getUser());
            _accountInfo.setSiteId(themeDisplay.getSiteGroupId());
            _accountInfo.setCompanyId(_configuration.accountsCompanyId());
            loadAccounts(_accountInfo);
            context.setAccountInfo(_accountInfo);
        } else {
            _accountInfo = accountInfo;
        }
    }

    public AccountEntry getSelectedAccountEntry(){
        return  _accountInfo.getSelectedAccountEntry();
    }
    public String getTitle() {
        return "account-info";
    }

    public String getParamName() {
        return "accountEntryId";
    }

    public long getCompanyId() {
        return _accountInfo.getCompanyId();
    }

    private void loadAccounts(AccountInfo accountInfo) {

        User user = accountInfo.getCurrentUser();
        if (user != null && !user.isGuestUser()) {
            String domain = user.getEmailAddress().split("@")[1];
            accountInfo.addAccounts(_commerceUtils.getAccountsByDomain(domain, getCompanyId()));
            AccountEntry accountEntry = getPersonalAccount();
            if (accountEntry != null) {
                accountInfo.addAccount(accountEntry);
            }
        }
    }

    public boolean canCreateNewAccount() {
        return !_accountInfo.hasPersonalAccount();
    }

    public List<AccountEntry> getAccountEntries() {
        return _accountInfo.getAccountEntries();
    }

    public String getAccountWebsite(AccountEntry accountEntry) {
        if (accountEntry != null) {
            Serializable attribute = accountEntry.getExpandoBridge().getAttribute(OrganizationConstants.ORG_WEBSITE, false);
            return attribute == null ? "" : (String) attribute;
        }
        return "";
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
        } else {
            billingAddress = null;
        }
        Country companyCountry = _countryLocalService.getCountryByA2(getCompanyId(), country.getA2());
        String name = ParamUtil.getString(request, OrganizationConstants.ORG_ADDRESS_NAME);
        if (name.isEmpty()){
            name = accountEntry.getName();
        }

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
            final ListType accountType = ListTypeLocalServiceUtil.getListType(
                    "billing", "com.liferay.account.model.AccountEntry.address");
            billingAddress = _addressLocalService.addAddress(
                    null, accountEntry.getUserId(), AccountEntry.class.getName(),
                    accountEntry.getAccountEntryId(), name, null, street, null, null, city, postal, regionId, companyCountry.getCountryId(),
                    accountType.getListTypeId(), true, true, phoneNumber, serviceContext);

            if (accountEntry.getDefaultBillingAddress() == null) {
                accountEntry.setDefaultBillingAddressId(billingAddress.getAddressId());
                _accountEntryLocalService.updateAccountEntry(accountEntry);
            }

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
                final ListType phoneType = ListTypeLocalServiceUtil.getListType(
                        "phone-number", "com.liferay.portal.kernel.model.Address.phone");
                _phoneLocalService.addPhone(
                        accountEntry.getUserId(), "com.liferay.portal.kernel.model.Address", billingAddress.getAddressId(),
                        phoneNumber, null, phoneType.getListTypeId(), true, serviceContext);
            } else {
                phone = phones.get(0);
                phone.setNumber(phoneNumber);
                _phoneLocalService.updatePhone(phone);
            }
        }
        return billingAddress;
    }

    public void storeAccountInfo(HttpServletRequest httpServletRequest) {

        long accountEntryId = ParamUtil.getLong(httpServletRequest, getParamName());

        if (accountEntryId == 0 && !canCreateNewAccount()) {
            _accountInfo.setSelectedAccount(null);
            return;
        }

        AccountEntry selectedAccountEntry;
        try {
            selectedAccountEntry = addOrUpdateAccountEntry(httpServletRequest, accountEntryId);
            _accountInfo.setSelectedAccount(selectedAccountEntry);
            if (selectedAccountEntry != null && _accountInfo.getAccountEntry(selectedAccountEntry.getAccountEntryId()) == null) {
                _accountInfo.addAccount(selectedAccountEntry);
            }

        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class.getName(),
                    Collections.singletonList(new RegistrationFormException(e.getMessage())));
            return;
        }

        if (selectedAccountEntry != null && selectedAccountEntry.isPersonalAccount()) {
            try {
                addOrUpdateBillingAddress(httpServletRequest, selectedAccountEntry,
                        selectedAccountEntry.getDefaultBillingAddressId());
            } catch (Exception e) {
                SessionErrors.add(httpServletRequest, RegistrationFormException.class.getName(),
                        Collections.singletonList(new RegistrationFormException(e.getMessage())));
            }
        }
    }

    AccountEntry addOrUpdateAccountEntry(HttpServletRequest request, long accountEntryId) throws PortalException {

        AccountEntry accountEntry;
        if (accountEntryId == 0){
            accountEntry = _commerceUtils.createPersonAccountEntry(_accountInfo.getCurrentUser(), getCompanyId());
        } else {
            accountEntry = _accountEntryLocalService.fetchAccountEntry(accountEntryId);
        }

        if (accountEntry != null && accountEntry.isPersonalAccount()) {
            String name = ParamUtil.getString(request, OrganizationConstants.ORG_NAME);

            if (name != null && !name.isEmpty()) {
                accountEntry.setName(name);
            }
            String website = ParamUtil.getString(request, OrganizationConstants.ORG_WEBSITE);
            String taxIdNumber = ParamUtil.getString(request, OrganizationConstants.ORG_VAT);
            accountEntry.setTaxIdNumber(taxIdNumber);

            if (!accountEntry.getExpandoBridge().hasAttribute(OrganizationConstants.ORG_WEBSITE)) {
                accountEntry.getExpandoBridge().addAttribute(OrganizationConstants.ORG_WEBSITE, false);
            }
            if ("".equals(website)) {website = null;}
            accountEntry.getExpandoBridge().setAttribute(OrganizationConstants.ORG_WEBSITE, website, false);

            return _accountEntryLocalService.updateAccountEntry(accountEntry);
        }
        return accountEntry;
    }

    /**
     * Get the equivalent of the logged in user from the central Company where all account data is stored.
     */
    private AccountEntry getPersonalAccount() {
        return _commerceUtils.getPersonalAccount(_accountInfo.getCurrentUser(), getCompanyId());
    }

    public Address getAccountAddress(AccountEntry accountEntry) {
        if (accountEntry == null){
            return null;
        }
        if (accountEntry.getDefaultBillingAddress() != null){
            return accountEntry.getDefaultBillingAddress();
        }
        if (accountEntry.isPersonalAccount()){
            List<Address> accountAddresses = getAccountAddresses(accountEntry);
            if (!accountAddresses.isEmpty()){
                return accountAddresses.get(0);
            }
        }
        return null;
    }

    public List<Address> getAccountAddresses(AccountEntry _selectedAccountEntry) {

        if (_selectedAccountEntry == null) return Collections.emptyList();
        return  _addressLocalService.getAddresses(getCompanyId(), AccountEntry.class.getName(),
                _selectedAccountEntry.getAccountEntryId());

    }

    public User getCurrentUser(){
        return _accountInfo.getCurrentUser();
    }

    public void addAccountEntry(String accountEntryId) throws Exception {

        if (!PortletPermissionUtils.isUserSiteAdministrator(_accountInfo.getCurrentUser().getUserId(), _accountInfo.getSiteId() )) {
            throw new Exception("User does not have permission to access the selected account.");
        }

        if (_accountInfo.containsAccountEntry(Long.parseLong(accountEntryId))){
            return;
        }

        AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(Long.parseLong(accountEntryId));
        if (accountEntry != null){
            _accountInfo.addAccount(accountEntry);
        }

    }
}
