package nl.deltares.portal.utils;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.Registration;

import java.util.List;
import java.util.Map;

public interface CommerceUtils {

    List<AccountEntry> getAccountsByDomain(String domain, long companyId);

    CProduct getProductByRegistration(Registration registration);

    Address createAddress(Map<String, String> addressInfo, AccountEntry accountEntry) throws PortalException;
    AccountEntry createPersonAccountEntry(User billingUser, Map<String, String> requestParameters) throws PortalException;

    AccountEntry getPersonalAccount(User user);

    void updateAccountEntry(AccountEntry accountEntry, Map<String, String> requestParameters) throws PortalException;
}
