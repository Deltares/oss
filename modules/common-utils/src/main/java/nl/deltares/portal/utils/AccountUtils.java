package nl.deltares.portal.utils;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.List;

public interface AccountUtils {

    List<AccountEntry> getAccountsByDomain(String domain, long companyId);

    boolean userAccountExists(User user);

    AccountEntry getPersonalAccount(User user);

    AccountEntry createPersonAccountEntry(User user) throws PortalException;
}
