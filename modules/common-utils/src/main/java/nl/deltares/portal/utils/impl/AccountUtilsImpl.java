package nl.deltares.portal.utils.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.*;
import nl.deltares.portal.model.AccountInfo;
import nl.deltares.portal.model.AddressInfo;
import nl.deltares.portal.utils.AccountUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component(
        immediate = true,
        service = AccountUtils.class
)
public class AccountUtilsImpl implements AccountUtils {

    private static final Log LOG = LogFactoryUtil.getLog(AccountUtilsImpl.class);

    final static String PERSONAL_ACCOUNT_PREFIX = "Personal_account_";
    final static String CUSTOM_FIELD_DOMAIN = "Email domain";

    @Override
    public List<AccountEntry> searchAccountsByName(String filterValue, long companyId, int start, int end) {

        final DynamicQuery dynamicQuery = getDynamicQuery(filterValue, companyId);
        dynamicQuery.setLimit(start, end);
        dynamicQuery.addOrder(OrderFactoryUtil.asc("name"));
        return _accountEntryLocalService.dynamicQuery(dynamicQuery);
    }

    @Override
    public long searchAccountsByNameCount(String filterValue, long companyId) {

        final DynamicQuery dynamicQuery = getDynamicQuery(filterValue, companyId);
        return _accountEntryLocalService.dynamicQueryCount(dynamicQuery);
    }

    private DynamicQuery getDynamicQuery(String filterValue, long companyId) {
        final DynamicQuery dynamicQuery = _accountEntryLocalService.dynamicQuery();
        dynamicQuery.add(RestrictionsFactoryUtil.or(
                RestrictionsFactoryUtil.like("name", '%' + filterValue + '%'),
                RestrictionsFactoryUtil.eq("emailAddress", filterValue)
        ));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 0));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));
        return dynamicQuery;
    }

    @Override
    public List<AccountEntry> getAccountsByDomain(String domain, long companyId) {

        List<ExpandoValue> domainValues = _expandoValueLocalService.getColumnValues(companyId, AccountEntry.class.getName(), "CUSTOM_FIELDS", CUSTOM_FIELD_DOMAIN, domain, 0, 1);
        if (domainValues.isEmpty()) {
            final DynamicQuery dynamicQuery = _accountEntryLocalService.dynamicQuery();
            dynamicQuery.add(RestrictionsFactoryUtil.like("domains", '%' + domain + '%'));
            dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 0));
            dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));
            return _accountEntryLocalService.dynamicQuery(dynamicQuery);

        } else {
            Optional<ExpandoValue> first = domainValues.stream().findFirst();
            ExpandoValue expandoValue = first.get();
            long accountEntryId = expandoValue.getClassPK();
            return Collections.singletonList(_accountEntryLocalService.fetchAccountEntry(accountEntryId));
        }
    }

    @Override
    public String[] getAccountsDomains(long accountEntryId) {

        AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(accountEntryId);
        if (accountEntry == null) {
            LOG.warn("No account entry found with id " + accountEntryId + " while trying to retrieve account domains");
            return new String[0];
        }

        String domains = accountEntry.getDomains();
        if (domains != null && !domains.isEmpty()) {
            return AccountUtils.getSplitDomains(domains);
        }

        try {
            domains = _expandoValueLocalService.getData(accountEntry.getCompanyId(), AccountEntry.class.getName(), "CUSTOM_FIELDS", CUSTOM_FIELD_DOMAIN, accountEntryId).toString();
            if (domains != null && !domains.isEmpty()) {
                return AccountUtils.getSplitDomains(domains);
            }
        } catch (PortalException e) {
            LOG.warn("Error getting domain values from Expando for  accountEntryId " + accountEntryId + ": " + e.getMessage());
            return new String[0];
        }
        return new String[0];
    }

    @Override
    public AccountEntry getPersonalAccount(User user, long accountCompanyId) {

        String externalReferenceCode = PERSONAL_ACCOUNT_PREFIX + user.getScreenName();
        AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(externalReferenceCode, accountCompanyId);
        if (accountEntry != null && accountEntry.isPersonalAccount()) return accountEntry;
        return null;
    }

    @Override
    public AccountEntry createPersonAccountEntry(User localUser, long accountCompanyId) throws PortalException {

        User centralLiferayUser = _userLocalService.fetchUserByScreenName(accountCompanyId, "liferay");
        if (centralLiferayUser == null) {
            throw new PortalException("Liferay user does not exist for company with ID " + accountCompanyId);
        }
        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setCompanyId(accountCompanyId);
        serviceContext.setScopeGroupId(centralLiferayUser.getGroupId());
        serviceContext.setUserId(centralLiferayUser.getUserId());
        String externalReferenceCode = PERSONAL_ACCOUNT_PREFIX + localUser.getScreenName();

        //we can only create personal accounts through the code
        final AccountEntry accountEntry = _accountEntryLocalService.addOrUpdateAccountEntry(
                externalReferenceCode, centralLiferayUser.getUserId(),
                0, localUser.getScreenName(), "Personal account for user '" + localUser.getFullName() + "' with email '" + localUser.getEmailAddress() + "'",
                null, localUser.getEmailAddress(), new byte[0],
                null, "person", 0, serviceContext);

        _accountEntryUserRelLocalService.addAccountEntryUserRel(accountEntry.getAccountEntryId(), centralLiferayUser.getUserId());

        return accountEntry;
    }

    @Override
    public AccountEntry createOrUpdateBusinessAccountEntry(AccountInfo accountInfo, long companyId, long currentUserId) throws PortalException {

        //todo: make this field required
        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setCompanyId(companyId);
        serviceContext.setUserId(currentUserId);

        AccountEntry accountEntry = _accountEntryLocalService.addOrUpdateAccountEntry(
                accountInfo.getCompanyIdentifier(),
                currentUserId, 0, accountInfo.getCompanyName(), null,
                accountInfo.getEmailDomains(), null, null, accountInfo.getVat(),
                accountInfo.getType(), 0, serviceContext
        );

        String website = accountInfo.getWebsite();
        if (website != null) {
            if (!accountEntry.getExpandoBridge().hasAttribute("website")) {
                accountEntry.getExpandoBridge().addAttribute("website");
            }
            accountEntry.getExpandoBridge().setAttribute("website", website, false);
        }

        AddressInfo addressInfo = accountInfo.getAddressInfo();
        Address address = createOrUpdateAddress(addressInfo, companyId, currentUserId, accountEntry);

        //Set address as default if no address yet exists.
        if (address != null && accountEntry.getDefaultBillingAddress() == null) {
            accountEntry.setDefaultBillingAddressId(address.getAddressId());
            _accountEntryLocalService.updateAccountEntry(accountEntry);
        }

        return accountEntry;
    }

    @Override
    public Address createOrUpdateAddress(AddressInfo addressInfo, long companyId, long currentUserId, AccountEntry accountEntry) throws PortalException {

        if (addressInfo == null) {
            return null;
        }

        String countryA2Code = addressInfo.getCountryA2Code();
        Country companyCountry = _countryLocalService.getCountryByA2(companyId, countryA2Code);
        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setCompanyId(companyId);
        serviceContext.setUserId(currentUserId);

        Address address = _addressLocalService.fetchAddressByExternalReferenceCode(addressInfo.getAddressIdentifier(), companyId);
        if (address == null) {
            ListType type = ListTypeLocalServiceUtil.getListType("billing", "com.liferay.account.model.AccountEntry.address");
            address = _addressLocalService.addAddress(addressInfo.getAddressIdentifier(), currentUserId,
                    "com.liferay.account.model.AccountEntry", accountEntry.getAccountEntryId(),
                    "Billing address", null, addressInfo.getStreet(), null, null,
                    addressInfo.getCity(), addressInfo.getPostal(), 0, companyCountry.getCountryId(), type.getListTypeId(),
                    false, false, addressInfo.getPhone(), serviceContext);
        } else {
            address.setStreet1(addressInfo.getStreet());
            address.setCity(addressInfo.getCity());
            address.setZip(addressInfo.getPostal());
            address.setCountryId(companyCountry.getCountryId());
            _addressLocalService.updateAddress(address);

            String phoneNumber = addressInfo.getPhone();
            if (phoneNumber != null) {

                List<Phone> phones = _phoneLocalService.getPhones(companyId, "com.liferay.portal.kernel.model.Address", address.getAddressId());
                Phone phone;
                if (phones.isEmpty()) {
                    ListType _phoneType = ListTypeLocalServiceUtil.getListType(
                            "phone-number", "com.liferay.portal.kernel.model.Address.phone");
                    phone = _phoneLocalService.createPhone(CounterLocalServiceUtil.increment(PhoneLocalService.class.getName()));
                    phone.setCompanyId(companyId);
                    phone.setClassName("com.liferay.portal.kernel.model.Address");
                    phone.setClassPK(address.getAddressId());
                    phone.setListTypeId(_phoneType.getListTypeId());
                    phone.setPrimary(true);
                } else {
                    phone = phones.get(0);
                }
                phone.setNumber(phoneNumber);
                _phoneLocalService.updatePhone(phone);
            }

        }
        return address;
    }

    @Reference
    private AccountEntryLocalService _accountEntryLocalService;

    @Reference
    private ExpandoValueLocalService _expandoValueLocalService;

    @Reference
    private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

    @Reference
    private AddressLocalService _addressLocalService;

    @Reference
    private CountryLocalService _countryLocalService;

    @Reference
    private PhoneLocalService _phoneLocalService;

    @Reference
    private UserLocalService _userLocalService;
}

