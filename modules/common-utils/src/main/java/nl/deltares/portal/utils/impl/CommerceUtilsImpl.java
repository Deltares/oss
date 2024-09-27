package nl.deltares.portal.utils.impl;


import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.*;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import nl.deltares.portal.constants.BillingConstants;
import nl.deltares.portal.constants.OrganizationConstants;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.CommerceUtils;
import org.osgi.service.component.annotations.Component;

import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        service = {CommerceUtils.class}
)
public class CommerceUtilsImpl implements CommerceUtils {


    final OrderByComparator<CommerceChannelAccountEntryRel> channelAccountEntryRelComparator;

    public CommerceUtilsImpl() {
        channelAccountEntryRelComparator = OrderByComparatorFactoryUtil.create("CommerceChannelAccountEntryRel", new Object[]{"accountEntryId", true});
    }

    @Override
    public AccountEntry getPersonalAccount(User user) {
        return AccountEntryLocalServiceUtil.fetchPersonAccountEntry(user.getUserId());
    }

    @Override
    public List<AccountEntry> getAccountsByDomain(String domain, long companyId) {
        final DynamicQuery dq = AccountEntryLocalServiceUtil.dynamicQuery();
        dq.add(RestrictionsFactoryUtil.like("domains", '%' + domain + '%'));
        dq.add(RestrictionsFactoryUtil.eq("status", 0));
        dq.add(RestrictionsFactoryUtil.eq("companyId", companyId));
        return AccountEntryLocalServiceUtil.dynamicQuery(dq);
    }

    @Override
    public CProduct getProductByRegistration(Registration registration) {
        return CProductLocalServiceUtil.fetchCProductByExternalReferenceCode(registration.getArticleId(), registration.getCompanyId());
    }

    @Override
    public void updateAccountEntry(AccountEntry accountEntry, Map<String, String> requestParameters) throws PortalException {
        if (accountEntry.getType().equals("business")) {
            throw new IllegalArgumentException("Business accounts may not be updated");
        }
        final String name = requestParameters.get(BillingConstants.ORG_NAME);
        if (!name.isEmpty()) accountEntry.setName(name);
        final String externalReference = requestParameters.getOrDefault(BillingConstants.ORG_EXTERNAL_REFERENCE_CODE, null);
        if (externalReference != null) accountEntry.setExternalReferenceCode(externalReference);
        accountEntry.setTaxIdNumber(requestParameters.getOrDefault(BillingConstants.ORG_VAT, null));
        if (!accountEntry.getExpandoBridge().hasAttribute("website")) {
            accountEntry.getExpandoBridge().addAttribute("website", false);
        }
        accountEntry.getExpandoBridge().setAttribute("website", requestParameters.getOrDefault(OrganizationConstants.ORG_WEBSITE, null));

        Address address = AddressLocalServiceUtil.fetchAddress(accountEntry.getDefaultBillingAddressId());
        if (address == null) {
            address = createAddress(requestParameters, accountEntry);
            accountEntry.setDefaultBillingAddressId(address.getAddressId());
        } else {
            address.setName(requestParameters.getOrDefault(BillingConstants.ORG_NAME, null));
            address.setStreet1(requestParameters.getOrDefault(BillingConstants.ORG_STREET, null));
            address.setZip(requestParameters.getOrDefault(BillingConstants.ORG_POSTAL, null));
            address.setCity(requestParameters.getOrDefault(BillingConstants.ORG_CITY, null));
            final String countryCode = requestParameters.getOrDefault(BillingConstants.ORG_COUNTRY_CODE, "NL");
            final Country country = CountryLocalServiceUtil.fetchCountryByA2(accountEntry.getCompanyId(), countryCode);
            address.setCountryId(country == null ? 0 : country.getCountryId());

            final String phone = requestParameters.getOrDefault(BillingConstants.ORG_PHONE, null);
            if (phone != null) {
                final List<Phone> phones = PhoneLocalServiceUtil.getPhones(address.getCompanyId(), "com.liferay.portal.kernel.model.Address", address.getAddressId());
                if (!phones.isEmpty()) {
                    final Phone phone1 = phones.get(0);
                    phone1.setNumber(phone);
                    PhoneLocalServiceUtil.updatePhone(phone1);
                }
            }
            AddressLocalServiceUtil.updateAddress(address);
        }
        AccountEntryLocalServiceUtil.updateAccountEntry(accountEntry);
    }

    @Override
    public AccountEntry createPersonAccountEntry(User billingUser, Map<String, String> requestParameters) throws PortalException {

        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setScopeGroupId(billingUser.getGroupId());
        serviceContext.setCompanyId(billingUser.getCompanyId());
        //we can only create personal accounts through the code
        final AccountEntry accountEntry = AccountEntryLocalServiceUtil.addAccountEntry(
                billingUser.getUserId(),
                0, requestParameters.get(BillingConstants.ORG_NAME), "", null,
                billingUser.getEmailAddress(), new byte[0],
                requestParameters.get(BillingConstants.ORG_VAT), "person", 0, serviceContext);

        AccountEntryUserRelLocalServiceUtil.addAccountEntryUserRel(accountEntry.getAccountEntryId(), billingUser.getUserId());

        return accountEntry;
    }

    @Override
    public Address createAddress(Map<String, String> addressInfo, AccountEntry accountEntry) throws PortalException {

        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setScopeGroupId(accountEntry.getAccountEntryGroupId());
        serviceContext.setCompanyId(accountEntry.getCompanyId());
        serviceContext.setUserId(accountEntry.getUserId());

        final String street = addressInfo.getOrDefault(BillingConstants.ORG_STREET, null);
        final String postal = addressInfo.getOrDefault(BillingConstants.ORG_POSTAL, null);
        final String city = addressInfo.getOrDefault(BillingConstants.ORG_CITY, null);
        final String phone = addressInfo.getOrDefault(BillingConstants.ORG_PHONE, "");
        final String countryCode = addressInfo.getOrDefault(BillingConstants.ORG_COUNTRY_CODE, "NL");
        long countryId = 0;
        if (countryCode != null) {
            final Country country = CountryLocalServiceUtil.fetchCountryByA2(accountEntry.getCompanyId(), countryCode);
            if (country != null) countryId = country.getCountryId();
        }
        final ListType accountType = ListTypeLocalServiceUtil.getListType(accountEntry.getCompanyId(), "billing", "com.liferay.account.model.AccountEntry.address");
        final ClassName className = ClassNameLocalServiceUtil.getClassName(AccountEntry.class.getName());
        final Address address = AddressLocalServiceUtil.addAddress(null, accountEntry.getUserId(), className.getClassName(),
                accountEntry.getAccountEntryId(), accountEntry.getName(), null, street, null, null,
                city, postal, 0, countryId, accountType.getListTypeId(), false, false, null, serviceContext);
        final ListType phoneType = ListTypeLocalServiceUtil.getListType(accountEntry.getCompanyId(), "phone-number", "com.liferay.portal.kernel.model.Address.phone");
        PhoneLocalServiceUtil.addPhone(accountEntry.getUserId(), "com.liferay.portal.kernel.model.Address",
                address.getAddressId(), phone, null, phoneType.getListTypeId(), true, serviceContext);

        return address;
    }

    private CommerceCurrency getCommerceCurrency(AccountEntry accountEntry) {

        final List<CommerceChannelAccountEntryRel> rels = CommerceChannelAccountEntryRelLocalServiceUtil.getCommerceChannelAccountEntryRels(accountEntry.getAccountEntryId(),
                6, 0, 10, channelAccountEntryRelComparator);
        if (!rels.isEmpty()) {
            final CommerceChannelAccountEntryRel rel = rels.get(0);
            final CommerceCurrency commerceCurrency = CommerceCurrencyLocalServiceUtil.fetchCommerceCurrency(rel.getClassPK());
            if (commerceCurrency != null) return commerceCurrency;
        }

        try {
            return CommerceCurrencyLocalServiceUtil.getCommerceCurrency(accountEntry.getCompanyId(), "EUR");
        } catch (NoSuchCurrencyException e) {
            return null;
        }
    }
}
