package nl.deltares.portal.utils;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.List;

public interface CommerceUtils {

    List<AccountEntry> getAccountsByDomain(String domain, long companyId);

    boolean userAccountExists(User user, long companyId);

    AccountEntry getPersonalAccount(User user, long companyId);

    AccountEntry createPersonAccountEntry(User billingUser, long companyId) throws PortalException;
}
