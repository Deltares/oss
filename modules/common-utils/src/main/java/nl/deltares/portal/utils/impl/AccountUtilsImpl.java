package nl.deltares.portal.utils.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.*;
import nl.deltares.portal.model.AccountInfo;
import nl.deltares.portal.model.AddressInfo;
import nl.deltares.portal.utils.AccountUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(
        immediate = true,
        service = AccountUtils.class
)
public class AccountUtilsImpl implements AccountUtils {
    @Override
    public List<AccountEntry> getAccountsByDomain(String domain, long companyId) {

        final DynamicQuery dynamicQuery = _accountEntryLocalService.dynamicQuery();
        dynamicQuery.add(RestrictionsFactoryUtil.like("domains", '%' + domain + '%'));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 0));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));
        return _accountEntryLocalService.dynamicQuery(dynamicQuery);
    }

    @Override
    public boolean userAccountExists(User user) {
        return null != getPersonalAccount(user);
    }

    @Override
    public AccountEntry getPersonalAccount(User user) {
        return _accountEntryLocalService.fetchPersonAccountEntry(user.getUserId());
    }

    @Override
    public AccountEntry createPersonAccountEntry(User user) throws PortalException {

        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setCompanyId(user.getCompanyId());
        serviceContext.setScopeGroupId(user.getGroupId());
        //we can only create personal accounts through the code
        final AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
                user.getUserId(),
                0, user.getScreenName(), user.getFullName(), null,
                user.getEmailAddress(), new byte[0],
                null, "person", 0, serviceContext);

        _accountEntryUserRelLocalService.addAccountEntryUserRel(accountEntry.getAccountEntryId(), user.getUserId());

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
        if(website != null) {
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

        if (addressInfo == null) {return null;}

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
    private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

    @Reference
    private AddressLocalService _addressLocalService;

    @Reference
    private CountryLocalService _countryLocalService;

    @Reference
    private PhoneLocalService _phoneLocalService;
}

