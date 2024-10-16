package nl.deltares.portal.utils.impl;


import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalServiceUtil;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.*;
import nl.deltares.portal.constants.BillingConstants;
import nl.deltares.portal.constants.DeltaresCommerceConstants;
import nl.deltares.portal.constants.OrganizationConstants;
import nl.deltares.portal.model.DeltaresProduct;
import nl.deltares.portal.utils.CommerceUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

@Component(
        immediate = true,
        service = {CommerceUtils.class}
)
public class CommerceUtilsImpl implements CommerceUtils {

    @Reference
    private CPInstanceHelper _cpInstanceHelper;

    @Reference
    private CPDefinitionHelper _cCpDefinitionHelper;

    @Reference
    private ProductHelper _productHelper;

    @Override
    public List<DeltaresProduct> commerceOrderItemsToDeltaresProducts(List<CommerceOrderItem> commerceOrderItems, CommerceContext context, Locale locale) throws PortalException {
        final ArrayList<DeltaresProduct> deltaresProducts = new ArrayList<>(commerceOrderItems.size());
        for (CommerceOrderItem orderItem : commerceOrderItems) {

            try {
                final CPCatalogEntry cpCatalogEntry = _cCpDefinitionHelper.getCPCatalogEntry(orderItem.getCommerceOrder().getCommerceAccountId(),
                        orderItem.getGroupId(), orderItem.getCPDefinitionId(), locale);
                final DeltaresProduct deltaresProduct = toDeltaresProduct(cpCatalogEntry, context, locale);
                deltaresProduct.setOrderItem(orderItem);
                deltaresProducts.add(deltaresProduct);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sortProductsByStartTime(extractChildRelations(deltaresProducts, context, locale));
    }

    private List<DeltaresProduct> extractChildRelations(ArrayList<DeltaresProduct> deltaresProducts, CommerceContext context, Locale locale) throws PortalException {

        final DeltaresProduct[] productsArray = deltaresProducts.toArray(new DeltaresProduct[0]);
        for (DeltaresProduct deltaresProduct : productsArray) {
            final List<DeltaresProduct> children = getRelatedChildren(deltaresProduct, context, locale);
            if (!children.isEmpty()) {
                deltaresProduct.addRelatedChildren(children);
                for (DeltaresProduct child : children) {
                    child.setSelected(deltaresProducts.remove(child));
                }
            }
        }
        return deltaresProducts;
    }

    @Override
    public List<DeltaresProduct> cpCategoryEntriesToDeltaresProducts(List<CPCatalogEntry> cpCatalogEntries, CommerceContext context, Locale locale) throws PortalException {
        final ArrayList<DeltaresProduct> deltaresProducts = new ArrayList<>(cpCatalogEntries.size());

        for (CPCatalogEntry entry : cpCatalogEntries) {
            try {
                final DeltaresProduct deltaresProduct = toDeltaresProduct(entry, context, locale);
                setPrice(deltaresProduct, context, locale);
                deltaresProducts.add(deltaresProduct);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sortProductsByStartTime(extractChildRelations(deltaresProducts, context, locale));
    }

    @Override
    public DeltaresProduct toDeltaresProduct(CPCatalogEntry entry, CommerceContext context, Locale locale) throws PortalException {
        try {

            final DeltaresProduct deltaresProduct = new DeltaresProduct(entry);
            setPrice(deltaresProduct, context, locale);

            return deltaresProduct;
        } catch (Exception e) {
            throw new PortalException(e);
        }
    }

    private List<DeltaresProduct> getRelatedProducts(DeltaresProduct deltaresProduct, CommerceContext context, Locale locale, String relationId) throws PortalException {

        if (context.getCommerceOrder() == null) return Collections.emptyList();
        final List<DeltaresProduct> relatedProducts = new ArrayList<>();
        final List<CPDefinitionLink> relationLinks = CPDefinitionLinkLocalServiceUtil.getReverseCPDefinitionLinks(deltaresProduct.getCProductId(), relationId);
        for (CPDefinitionLink relationLink : relationLinks) {
            final CPCatalogEntry cpCatalogEntry = _cCpDefinitionHelper.getCPCatalogEntry(
                    context.getCommerceOrder().getCommerceAccountId(), relationLink.getGroupId(),
                    relationLink.getCPDefinitionId(), locale);
            relatedProducts.add(new DeltaresProduct(cpCatalogEntry));
        }
        return relatedProducts;
    }

    @Override
    public List<DeltaresProduct> getRelatedChildren(DeltaresProduct deltaresProduct, CommerceContext context, Locale locale) throws PortalException {
        //Lookup relations that have this deltaresProduct as their Parent relation
        return getRelatedProducts(deltaresProduct, context, locale, DeltaresCommerceConstants.RELATION_TYPE_PARENT);
    }

    @Override
    public List<DeltaresProduct> getRelatedParents(DeltaresProduct deltaresProduct, CommerceContext context, Locale locale) throws PortalException {
        //Lookup relations that have this deltaresProduct as their Child relation
        return getRelatedProducts(deltaresProduct, context, locale, DeltaresCommerceConstants.RELATION_TYPE_CHILD);
    }
    private void setPrice(DeltaresProduct deltaresProduct, CommerceContext context, Locale locale) throws PortalException, ParseException {
        final CPInstance defaultCPInstance = _cpInstanceHelper.getDefaultCPInstance(deltaresProduct.getCPDefinitionId());
        final PriceModel priceModel = _productHelper.getPriceModel(
                defaultCPInstance.getCPInstanceId(), "[]", BigDecimal.ZERO,
                StringPool.BLANK, context, locale);

        final String priceString = priceModel.getPrice();
        final String[] split = priceString.split(" ");
        deltaresProduct.setCommerceCurrencyCode(split[0]);

        final NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        if(numberFormat instanceof DecimalFormat){
            ((DecimalFormat)numberFormat).setParseBigDecimal(true);
        }
        final Number parse = numberFormat.parse(priceString.replaceAll("[^\\d.,]", ""));
        deltaresProduct.setUnitPrice(parse.floatValue());

    }

    @Override
    public List<DeltaresProduct> sortProductsByStartTime(List<DeltaresProduct> products) {
        products.sort(Comparator.comparingLong(DeltaresProduct::getStartTime));
        return products;
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
    public void updateAccountEntry(AccountEntry accountEntry, Map<String, String> requestParameters) throws PortalException {
        if (accountEntry.getType().equals("business")) {
            throw new IllegalArgumentException("Business accounts may not be updated");
        }
        final String name = requestParameters.get(BillingConstants.ORG_NAME);
        if (!name.isEmpty()) accountEntry.setName(name);
        final String externalReference = requestParameters.getOrDefault(BillingConstants.ORG_EXTERNAL_REFERENCE_CODE, null);
        if (externalReference != null) accountEntry.setExternalReferenceCode(externalReference);
        accountEntry.setTaxIdNumber(requestParameters.getOrDefault(BillingConstants.ORG_VAT, null));
        if (!accountEntry.getExpandoBridge().hasAttribute(DeltaresCommerceConstants.CUSTOM_FIELD_ACCOUNT_ENTRY_WEBSITE)) {
            accountEntry.getExpandoBridge().addAttribute(DeltaresCommerceConstants.CUSTOM_FIELD_ACCOUNT_ENTRY_WEBSITE, false);
        }
        accountEntry.getExpandoBridge().setAttribute(DeltaresCommerceConstants.CUSTOM_FIELD_ACCOUNT_ENTRY_WEBSITE, requestParameters.getOrDefault(OrganizationConstants.ORG_WEBSITE, null));

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

}
