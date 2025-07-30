package nl.deltares.portal.utils;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.AccountInfo;
import nl.deltares.portal.model.AddressInfo;

import java.util.List;

public interface AccountUtils {

    List<AccountEntry> getAccountsByDomain(String domain, long companyId);

    boolean userAccountExists(User user);

    AccountEntry getPersonalAccount(User user);

    AccountEntry createPersonAccountEntry(User user) throws PortalException;

    @SuppressWarnings("UnusedReturnValue")
    AccountEntry createBusinessAccountEntry(AccountInfo accountInfo, long companyId) throws PortalException;

    Address createAddress(AddressInfo addressInfo, long companyId) throws PortalException;
}
