package nl.deltares.model;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountInfo implements Serializable {

    AccountEntry selectedAccount;
    User currentUser;

    List<AccountEntry> accounts = new ArrayList<>();
    private Long companyId;

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addAccounts(List<AccountEntry> accountEntries) {
        accounts.addAll(accountEntries);
    }

    public void addAccount(AccountEntry accountEntry) {
        accounts.add(accountEntry);
    }

    public AccountEntry getSelectedAccountEntry() {
        return selectedAccount;
    }

    public boolean hasPersonalAccount() {
        if (accounts.isEmpty()) return false;

        for (AccountEntry account : accounts) {
            //Already a personal account created, cannot create new
            if (account.isPersonalAccount()) return true;
        }
        return false;
    }

    public List<AccountEntry> getAccountEntries() {
        return accounts;
    }

    public AccountEntry getAccountEntry(long accountEntryId) {
        for (AccountEntry account : accounts) {
            if (account.getAccountEntryId() != accountEntryId) continue;
            return account;
        }
        return null;
    }

    public void setSelectedAccount(AccountEntry accountEntry) {
        selectedAccount = accountEntry;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId == 0 && currentUser != null ? currentUser.getCompanyId() : companyId;
    }
}
