package nl.deltares.portal.utils.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import nl.deltares.portal.utils.AccountUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(
        immediate = true,
        service = AccountUtils.class
)
public class AccountUtilsImpl implements AccountUtils {
    @Override
    public List<AccountEntry> getAccountsByDomain(String domain, long companyId) {

        final DynamicQuery dynamicQuery = _accountEntryLocalService.dynamicQuery();
        dynamicQuery.add(RestrictionsFactoryUtil.like("domains", '%' + domain + '%'));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 0));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));
        return _accountEntryLocalService.dynamicQuery(dynamicQuery);
    }

    @Override
    public boolean userAccountExists(User user) {
        return null != getPersonalAccount(user);
    }

    @Override
    public AccountEntry getPersonalAccount(User user) {
        return _accountEntryLocalService.fetchPersonAccountEntry(user.getUserId());
    }

    @Override
    public AccountEntry createPersonAccountEntry(User user) throws PortalException {

        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setCompanyId(user.getCompanyId());
        serviceContext.setScopeGroupId(user.getGroupId());
        //we can only create personal accounts through the code
        final AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
                user.getScreenName(),
                user.getUserId(),
                0, user.getScreenName(), user.getFullName(), null,
                user.getEmailAddress(), new byte[0],
                null, "person", 0, serviceContext);

        _accountEntryUserRelLocalService.addAccountEntryUserRel(accountEntry.getAccountEntryId(), user.getUserId());

        return accountEntry;
    }

    @Reference
    private AccountEntryLocalService _accountEntryLocalService;

    @Reference
    private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

}

