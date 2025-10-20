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
    public AccountEntry createOrUpdateBusinessAccountEntry(AccountInfo accountInfo, long companyId) throws PortalException {

        //todo: make this field required
        String companyIdentifier = accountInfo.getCompanyIdentifier();
        AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(companyIdentifier, companyId);
        if (accountEntry == null) {
            accountEntry = _accountEntryLocalService.createAccountEntry(CounterLocalServiceUtil.increment(AccountEntry.class.getName()));
            accountEntry.setCompanyId(companyId);
            accountEntry.setExternalReferenceCode(companyIdentifier);
            accountEntry.setType(accountInfo.getType());
        }
        accountEntry.setName(accountInfo.getCompanyName());
        accountEntry.setTaxIdNumber(accountInfo.getVat());
        accountEntry.setDomains(accountInfo.getEmailDomain());
        String website = accountInfo.getWebsite();
        if(website != null) {
            if (!accountEntry.getExpandoBridge().hasAttribute("website")) {
                accountEntry.getExpandoBridge().addAttribute("website");
            }
            accountEntry.getExpandoBridge().setAttribute("website", website, false);
        }

        AddressInfo addressInfo = accountInfo.getAddressInfo();
        Address address = createOrUpdateAddress(addressInfo, companyId);
        if (address != null) {

            if (addressInfo.isDefaultBillingAddress()) {
                accountEntry.setDefaultBillingAddressId(address.getAddressId());
            }
            address.setClassPK(accountEntry.getAccountEntryId());
            _addressLocalService.updateAddress(address);
        }

        _accountEntryLocalService.updateAccountEntry(accountEntry);

        return accountEntry;
    }

    @Override
    public Address createOrUpdateAddress(AddressInfo addressInfo, long companyId) throws PortalException {

        if (addressInfo == null) {return null;}

        Address address = _addressLocalService.fetchAddressByExternalReferenceCode(addressInfo.getAddressIdentifier(), companyId);
        if (address == null) {
            //we are not going to update existing entries
            address = _addressLocalService.createAddress(CounterLocalServiceUtil.increment(Address.class.getName()));
            address.setCompanyId(companyId);
            address.setExternalReferenceCode(addressInfo.getAddressIdentifier());
        }
        address.setName(addressInfo.getAddressName());
        address.setStreet1(addressInfo.getStreet());
        address.setZip(addressInfo.getPostal());
        address.setCity(addressInfo.getCity());
        String countryA2Code = addressInfo.getCountryA2Code();
        Country companyCountry = _countryLocalService.getCountryByA2(companyId, countryA2Code);
        address.setCountryId(companyCountry.getCountryId());
        _addressLocalService.updateAddress(address);

        String phoneNumber = addressInfo.getPhone();
        if (phoneNumber != null) {

            List<Phone> phones = _phoneLocalService.getPhones(companyId, "com.liferay.portal.kernel.model.Address", address.getAddressId());
            Phone phone;
            if (phones.isEmpty() ) {
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

