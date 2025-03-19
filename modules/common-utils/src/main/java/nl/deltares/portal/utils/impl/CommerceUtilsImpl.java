package nl.deltares.portal.utils.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import nl.deltares.portal.utils.CommerceUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        service = CommerceUtils.class
)
public class CommerceUtilsImpl implements CommerceUtils {
    @Override
    public List<AccountEntry> getAccountsByDomain(String domain, long companyId) {

        final DynamicQuery dynamicQuery = _accountEntryLocalService.dynamicQuery();
        dynamicQuery.add(RestrictionsFactoryUtil.like("domains", '%' + domain + '%'));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 0));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));
        return _accountEntryLocalService.dynamicQuery(dynamicQuery);
    }

    @Override
    public boolean userAccountExists(User user, long companyId) {
        return null != _accountEntryLocalService.fetchUserAccountEntry(user.getUserId(), companyId);
    }

    @Override
    public AccountEntry getPersonalAccount(User user, long companyId) {
        AccountEntry accountEntry = _accountEntryLocalService.fetchUserAccountEntry(user.getUserId(), companyId);
        if (accountEntry == null) {
            accountEntry = _accountEntryLocalService.fetchPersonAccountEntry(user.getUserId());
            accountEntry.setExternalReferenceCode(user.getScreenName());
            accountEntry.setCompanyId(companyId);
            _accountEntryLocalService.updateAccountEntry(accountEntry);
        }
        return accountEntry;
    }

    @Override
    public AccountEntry createPersonAccountEntry(User billingUser, long companyId) throws PortalException {

        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setCompanyId(companyId);
        //we can only create personal accounts through the code
        final AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
                billingUser.getUserId(),
                0, billingUser.getScreenName(), billingUser.getFullName(), null,
                billingUser.getEmailAddress(), new byte[0],
                null, "person", 0, serviceContext);

        accountEntry.setExternalReferenceCode(billingUser.getScreenName());
        _accountEntryUserRelLocalService.addAccountEntryUserRel(accountEntry.getAccountEntryId(), billingUser.getUserId());

        return accountEntry;
    }

    @Reference
    private AccountEntryLocalService _accountEntryLocalService;

    @Reference
    private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

    @Reference
    private AddressLocalService _addressLocalService;
}

