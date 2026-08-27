package nl.deltares.portal.utils;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.AccountInfo;
import nl.deltares.portal.model.AddressInfo;

import java.util.List;

public interface AccountUtils {

    List<AccountEntry> searchAccountsByName(String filterValue, long companyId, int start, int end);

    long searchAccountsByNameCount(String filterValue, long companyId);

    List<AccountEntry> getAccountsByDomain(String domain, long companyId);

    String[] getAccountsDomains(long accountEntryId);

    AccountEntry getPersonalAccount(User user, long accountCompanyId);

    AccountEntry createPersonAccountEntry(User user, long companyId) throws PortalException;

    @SuppressWarnings("UnusedReturnValue")
    AccountEntry createOrUpdateBusinessAccountEntry(AccountInfo accountInfo, long companyId, long currentUserId) throws PortalException;

    Address createOrUpdateAddress(AddressInfo addressInfo, long companyId, long currentUserId, AccountEntry accountEntry) throws PortalException;

    static String[] getSplitDomains(String domains) {

        if (domains.contains(",")) {
            return domains.split(",");
        } else if (domains.contains(";")) {
            return domains.split(";");
        } else {
            return new String[]{domains};
        }
    }

    int deleteUserPersonalAccount(String  screenName);
}
