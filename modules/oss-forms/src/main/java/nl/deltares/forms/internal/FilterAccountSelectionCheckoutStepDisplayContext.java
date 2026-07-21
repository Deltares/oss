package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.ParamUtil;
import jakarta.servlet.http.HttpServletRequest;
import nl.deltares.portal.configuration.SiteMapConfiguration;
import nl.deltares.portal.utils.AccountUtils;

import java.util.List;

public class FilterAccountSelectionCheckoutStepDisplayContext {

    final AccountUtils _commerceUtils;
    private final long _accountCompanyId;
    private final String _filterValue;
    private final int _start;
    private final int _end;

    public FilterAccountSelectionCheckoutStepDisplayContext(HttpServletRequest request, AccountUtils commerceUtils,
                                                            ConfigurationProvider configurationProvider) throws ConfigurationException {

        _filterValue = ParamUtil.getString(request, "filterValue");
        final int curPage = ParamUtil.getInteger(request, "cur", 1);
        final int deltas = ParamUtil.getInteger(request, "delta", 25);
        _start = (curPage - 1) * deltas;
        _end = _start + deltas;
        _commerceUtils = commerceUtils;
        SiteMapConfiguration _configuration = configurationProvider.getSystemConfiguration(SiteMapConfiguration.class);
        _accountCompanyId = _configuration.accountsCompanyId();
    }

    public String getTitle() {
        return "account-selection";
    }

    public long getCompanyId() {
        return _accountCompanyId;
    }

    public String getFilterValue() {
        return _filterValue;
    }

    public List<AccountEntry> getAccountEntries() {
        return _commerceUtils.searchAccountsByName(_filterValue, getCompanyId(), _start, _end);
    }

    public int getTotalCount() {
        return (int) _commerceUtils.searchAccountsByNameCount(_filterValue, getCompanyId());
    }
}
