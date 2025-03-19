package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RegionCodeException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.forms.constants.OrganizationConstants;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.utils.CommerceUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountSelectionCheckoutStepDisplayContext {

    private boolean _accountsLoaded = false;
    private final List<AccountEntry> accounts = new ArrayList<>();;
    private final AccountEntryLocalService _accountEntryLocalService;
    private final AddressLocalService _addressLocalService;
    private final CountryLocalService _countryLocalService;
    private final CommerceUtils _commerceUtils;
    private final ThemeDisplay _themeDisplay;
    private final User _user;

    public AccountSelectionCheckoutStepDisplayContext(HttpServletRequest request, AccountEntryLocalService accountEntryLocalService,
                                                      CommerceUtils commerceUtils,
                                                      AddressLocalService addressLocalService, CountryLocalService countryLocalService) {
        _accountEntryLocalService = accountEntryLocalService;
        _addressLocalService = addressLocalService;
        _countryLocalService = countryLocalService;
        _commerceUtils = commerceUtils;

        _themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        _user = _themeDisplay.getUser();
    }

    public void loadAccounts() {

        if (_accountsLoaded){
            throw new IllegalStateException("Accounts already loaded!");
        }
        if (!_user.isGuestUser()){
            String domain = _user.getEmailAddress().split("@")[1];
            accounts.addAll(_commerceUtils.getAccountsByDomain(domain, getCompanyId()));
            if (_commerceUtils.userAccountExists(_user, getCompanyId())) {
                accounts.add(_commerceUtils.getPersonalAccount(_user, getCompanyId()));
            }
            _accountsLoaded = true;
        }

    }

    public boolean canCreateNewAccount() {
        if (!_accountsLoaded){
            loadAccounts();
        }
        if (accounts.isEmpty()) return true;

        for (AccountEntry account : accounts) {
            //Already a personal account created, cannot create new
            if (account.isPersonalAccount()) return false;
        }
        return true;
    }

    public String getTitle() {
        return "account-info";
    }

    public String getParamName() {
        return "accountEntryId";
    }

    public long getCompanyId(){
        return 10131;
    }
    public boolean canEditAccount(User user, AccountEntry accountEntry) {
        return accountEntry.getUserId() == user.getUserId() && accountEntry.isPersonalAccount();
    }

    public List<AccountEntry> getAccountEntries() {
        if (!_accountsLoaded){
            loadAccounts();
        }
        return accounts;
    }

    public String getAccountWebsite(long accountEntryId) {
        AccountEntry accountEntry = getAccountEntry(accountEntryId);
        if (accountEntry != null){
            return (String) accountEntry.getExpandoBridge().getAttribute("website", false);
        }
        return "";
    }

    public AccountEntry getAccountEntry(long accountEntryId) {
        if (!_accountsLoaded){
            loadAccounts();
        }
        for (AccountEntry account : accounts) {
            if (account.getAccountEntryId() != accountEntryId) continue;
            return account;
        }
        return null;
    }

    public long storeAccountSelection(HttpServletRequest request) throws Exception {

        long accountEntryId = ParamUtil.getLong(request, getParamName());

        if (accountEntryId == 0 && !canCreateNewAccount()) return accountEntryId;

        AccountEntry accountEntry;
        if (accountEntryId == 0) {
             accountEntry = addCommerceAccount(request);
        } else {
            accountEntry = _accountEntryLocalService.fetchUserAccountEntry(accountEntryId, getCompanyId());
        }

        if (canEditAccount(_user, accountEntry)) {
            Address address = addOrUpdateCommerceAddress(request, accountEntry);
            accountEntry.setDefaultBillingAddressId(address.getAddressId());
            _accountEntryLocalService.updateAccountEntry(accountEntry);
        }

    }

    private AccountEntry addCommerceAccount(HttpServletRequest request) throws PortalException {
        AccountEntry accountEntry = _commerceUtils.createPersonAccountEntry(_user, getCompanyId());

        String name = ParamUtil.getString(request, OrganizationConstants.ORG_NAME);
        String website = ParamUtil.getString(request, OrganizationConstants.ORG_WEBSITE);
        String externalReferenceCode = ParamUtil.getString(request, OrganizationConstants.ORG_EXTERNAL_REF);

        if (name == null || name.isEmpty()){
            throw new RegistrationFormException("Account name field is required!");
        }
        accountEntry.setName(name);
        if (externalReferenceCode != null && !externalReferenceCode.isEmpty()) {
            accountEntry.setExternalReferenceCode(externalReferenceCode);
        }
        if (website != null && !website.isEmpty()) {
            //Update account entry
            accountEntry.getExpandoBridge().setAttribute("website", website, false);
        }
        return accountEntry;
    }

    private Address addOrUpdateCommerceAddress(HttpServletRequest request, AccountEntry accountEntry) throws PortalException {
        long countryId = ParamUtil.getLong(request, OrganizationConstants.ORG_COUNTRY_ID);

        Country country = _countryLocalService.fetchCountry(countryId);
        if (country == null){
            throw new RegistrationFormException(String.format("Country with ID '%d' does not exist!", countryId));
        }
        if (!country.isBillingAllowed()) {
            throw new RegionCodeException(String.format("It is not allowed to do business with country '%s'", country.getName()));
        }

        String name = ParamUtil.getString(request, OrganizationConstants.ORG_NAME);
        String street1 = ParamUtil.getString(request, OrganizationConstants.ORG_STREET);
        String city = ParamUtil.getString(request, OrganizationConstants.ORG_CITY);
        String zip = ParamUtil.getString(request, OrganizationConstants.ORG_POSTAL);
        long regionId = ParamUtil.getLong(request, OrganizationConstants.ORG_REGION);
        String phoneNumber = ParamUtil.getString(request, OrganizationConstants.ORG_PHONE);

        long defaultBillingAddressId = accountEntry.getDefaultBillingAddressId();
        Address billingAddress = _addressLocalService.fetchAddress(defaultBillingAddressId);
        if (billingAddress == null) {
            Address address = _addressLocalService.createAddress(0);
            address.setStreet1(street1);
            address.setZip(zip);
            address.setCity(city);
            address.setCountryId(countryId);
            address.setRegionId(regionId);
            address.setCompanyId(getCompanyId());

            return _addressLocalService.addAddress(
                    AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
                    name, null, street1, null, null, city, zip, regionId,
                    countryId, phoneNumber, CommerceAddressConstants.ADDRESS_TYPE_BILLING, serviceContext);
        } else {
            return _commerceAddressService.updateCommerceAddress(billingAddress.getCommerceAddressId(),
                    name,null, street1, null, null, city, zip, regionId,
                    countryId, phoneNumber, CommerceAddressConstants.ADDRESS_TYPE_BILLING, serviceContext
            );
        }

    }


}
