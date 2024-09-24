package nl.deltares.portal.utils.impl;


import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeLocalServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import nl.deltares.portal.constants.BillingConstants;
import nl.deltares.portal.constants.OrganizationConstants;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.CommerceUtils;
import org.osgi.service.component.annotations.Component;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        service = {CommerceUtils.class}
)
public class CommerceUtilsImpl implements CommerceUtils {


    final OrderByComparator<CommerceChannelAccountEntryRel> channelAccountEntryRelComparator;
    public CommerceUtilsImpl() {

        channelAccountEntryRelComparator = OrderByComparatorFactoryUtil.create("CChannelAccountEntryRel", new Object[]{"CChannelAccountEntryRelId", true} );
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
    public CProduct createProduct(Registration registration, User registrationUser) {

        final long cProductId = CounterLocalServiceUtil.increment(CProduct.class.getName());
        final long cpDefinitionId = CounterLocalServiceUtil.increment(CPDefinition.class.getName());
        final CProduct cProduct = CProductLocalServiceUtil.createCProduct(cProductId);
        cProduct.setNew(true);
        cProduct.setCompanyId(registration.getCompanyId());
        cProduct.setGroupId(registration.getGroupId());
        cProduct.setExternalReferenceCode(registration.getArticleId());
        cProduct.setUserId(registrationUser.getUserId());
        cProduct.setUserName(registrationUser.getScreenName());
        cProduct.setPublishedCPDefinitionId(cpDefinitionId);
        final Date createDate = new Date();
        cProduct.setCreateDate(createDate);

        final CPDefinition cpDefinition = CPDefinitionLocalServiceUtil.createCPDefinition(cpDefinitionId);
        cpDefinition.setCompanyId(registration.getCompanyId());
        cpDefinition.setGroupId(registration.getGroupId());
        cpDefinition.setUserId(registrationUser.getUserId());
        cpDefinition.setUserName(registrationUser.getScreenName());
        cpDefinition.setCProductId(cProductId);
        cpDefinition.setProductTypeName("virtual");
        cpDefinition.setIgnoreSKUCombinations(true);
        cpDefinition.setShippable(false);

        CProductLocalServiceUtil.addCProduct(cProduct);
        CPDefinitionLocalServiceUtil.addCPDefinition(cpDefinition);

        return cProduct;
    }

    @Override
    public CommerceOrder createCommerceOrder(AccountEntry accountEntry, long siteGroupId, User registrationUser) {

        final long commerceOrderId = CounterLocalServiceUtil.increment(CommerceOrder.class.getName());
        final CommerceOrder commerceOrder = CommerceOrderLocalServiceUtil.createCommerceOrder(commerceOrderId);
        commerceOrder.setGroupId(siteGroupId);
        commerceOrder.setCompanyId(accountEntry.getCompanyId());
        commerceOrder.setUserId(registrationUser.getUserId());
        commerceOrder.setUserName(registrationUser.getScreenName());

        commerceOrder.setCommerceAccountId(accountEntry.getAccountEntryId());
        commerceOrder.setBillingAddressId(accountEntry.getDefaultBillingAddressId());
        commerceOrder.setStatus(CommerceOrderConstants.ORDER_STATUS_OPEN);
        commerceOrder.setPaymentStatus(CommerceOrderPaymentConstants.STATUS_PENDING);
        final CommerceCurrency commerceCurrency = getCommerceCurrency(accountEntry);

        if (commerceCurrency != null) commerceOrder.setCommerceCurrencyId(commerceCurrency.getCommerceCurrencyId());

        CommerceOrderLocalServiceUtil.addCommerceOrder(commerceOrder);

        return commerceOrder;
    }

    @Override
    public Address createAddress(Map<String, String> addressInfo, boolean billing, long companyId) {


        final long addressId = CounterLocalServiceUtil.increment(Address.class.getName());
        final Address address = AddressLocalServiceUtil.createAddress(addressId);
        address.setNew(true);
        address.setName(addressInfo.getOrDefault(billing? BillingConstants.ORG_NAME:OrganizationConstants.ORG_NAME, ""));
        address.setStreet1(addressInfo.getOrDefault(billing? BillingConstants.ORG_STREET:OrganizationConstants.ORG_STREET, ""));
        address.setZip(addressInfo.getOrDefault(billing? BillingConstants.ORG_POSTAL: OrganizationConstants.ORG_POSTAL, ""));
        address.setCity(addressInfo.getOrDefault(billing?BillingConstants.ORG_CITY:OrganizationConstants.ORG_CITY, ""));
        final String countryCode = addressInfo.getOrDefault(billing?BillingConstants.ORG_COUNTRY_CODE:OrganizationConstants.ORG_COUNTRY_CODE, null);
        if (countryCode != null) {
            final Country country = CountryLocalServiceUtil.fetchCountryByA2(companyId, countryCode);
            if (country != null) address.setCountryId(country.getCountryId());
        }
        address.setListTypeId(ListTypeLocalServiceUtil.getListType("billing", "com.liferay.account.model.AccountEntry.address").getListTypeId());

        final String phoneNumber = addressInfo.getOrDefault(billing? BillingConstants.ORG_PHONE:OrganizationConstants.ORG_PHONE, null);
        if (phoneNumber != null){
            final Phone phone = PhoneLocalServiceUtil.createPhone(CounterLocalServiceUtil.increment(Phone.class.getName()));
            phone.setNew(true);
            phone.setCompanyId(address.getCompanyId());
            phone.setNumber(phoneNumber);
            phone.setClassPK(address.getAddressId());
            phone.setListTypeId(ListTypeLocalServiceUtil.getListType("phone-number", "com.liferay.portal.kernel.model.Address.phone").getListTypeId());
            PhoneLocalServiceUtil.addPhone(phone);
        }

        AddressLocalServiceUtil.addAddress(address);

        return address;
    }

    private CommerceCurrency getCommerceCurrency(AccountEntry accountEntry){

        final List<CommerceChannelAccountEntryRel> rels = CommerceChannelAccountEntryRelLocalServiceUtil.getCommerceChannelAccountEntryRels(accountEntry.getAccountEntryId(),
                CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY, 0, 10, channelAccountEntryRelComparator);
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
