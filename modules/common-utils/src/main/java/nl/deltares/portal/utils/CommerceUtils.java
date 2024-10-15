package nl.deltares.portal.utils;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.DeltaresProduct;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface CommerceUtils {

    List<AccountEntry> getAccountsByDomain(String domain, long companyId);

    List<DeltaresProduct> commerceOrderItemsToDeltaresProducts(List<CommerceOrderItem> commerceOrderItems, CommerceContext context, Locale locale) throws PortalException;

    List<DeltaresProduct> cpCategoryEntriesToDeltaresProducts(List<CPCatalogEntry> cpCatalogEntries, CommerceContext context, Locale locale) throws PortalException;

    DeltaresProduct toDeltaresProduct(CPCatalogEntry entry, CommerceContext context, Locale locale) throws PortalException;

    List<DeltaresProduct> getRelatedChildren(DeltaresProduct deltaresProduct, CommerceContext context, Locale locale) throws PortalException;

    List<DeltaresProduct> getRelatedParents(DeltaresProduct deltaresProduct, CommerceContext context, Locale locale) throws PortalException;

    List<DeltaresProduct> sortProductsByStartTime(List<DeltaresProduct> products);

    Address createAddress(Map<String, String> addressInfo, AccountEntry accountEntry) throws PortalException;

    AccountEntry createPersonAccountEntry(User billingUser, Map<String, String> requestParameters) throws PortalException;

    AccountEntry getPersonalAccount(User user);

    void updateAccountEntry(AccountEntry accountEntry, Map<String, String> requestParameters) throws PortalException;
}
