package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.EmailSubscriptionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class SubscriptionsDisplayContext {

    private static final Log LOG = LogFactoryUtil.getLog(SubscriptionsDisplayContext.class);

    private final List<String> _subscriptionIds = new ArrayList<>();
    private final List<SubscriptionSelection> _subscriptions = new ArrayList<>();
    private boolean loaded = false;
    private final EmailSubscriptionUtils _subscriptionUtil;
    private final User _user;

    public SubscriptionsDisplayContext(HttpServletRequest httpServletRequest, ConfigurationProvider configurationProvider,
                                       EmailSubscriptionUtils subscriptionUtil) throws Exception {

        _subscriptionUtil = subscriptionUtil;
        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        ThemeDisplay _themeDisplay = cpRequestHelper.getThemeDisplay();
        DSDSiteConfiguration _configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, _themeDisplay.getScopeGroupId());
        _subscriptionIds.addAll(Arrays.asList(_configuration.mailingIds().split(";")));
        _user = _themeDisplay.getUser();

    }

    public boolean hasSubscriptions() {
        return !_subscriptionIds.isEmpty();
    }

    public List<SubscriptionSelection> getSubscriptions() {

        if (!loaded) {
            String emailAddress = _user.getEmailAddress();
            List<SubscriptionSelection> subscriptions;
            try {
                subscriptions = _subscriptionUtil.getSubscriptions(emailAddress);
            } catch (Exception e) {
                LOG.warn("Error retrieving subscriptions: " + e.getMessage());
                return Collections.emptyList();
            }
            for (SubscriptionSelection subscription : subscriptions) {
                if (_subscriptionIds.contains(subscription.getId())) {
                    _subscriptions.add(subscription);
                }
            }
            loaded = true;
        }
        return _subscriptions;
    }

    public void storeSubscriptionInfo(HttpServletRequest httpServletRequest) {

        List<String> subscribeIds = new ArrayList<>();
        List<String> unsubscribeIds = new ArrayList<>();
        _subscriptions.forEach(subscription -> {
            String mailingId = subscription.getId();
            final String selected = ParamUtil.getString(httpServletRequest, "subscription-" + mailingId);
            boolean bSelected = Boolean.parseBoolean(selected);
            if (bSelected) {
                subscribeIds.add(mailingId);
            } else {
                unsubscribeIds.add(mailingId);
            }
            subscription.setSelected(bSelected);
        });

        if (!subscribeIds.isEmpty()) {
            try {
                _subscriptionUtil.subscribeAll(_user, subscribeIds);
            } catch (Exception e) {
                LOG.warn(String.format("Failed to subscribe user %s for mailing %s: %s", _user.getEmailAddress(), subscribeIds, e.getMessage()));
            }
        }
        if (!unsubscribeIds.isEmpty()) {
            try {
                _subscriptionUtil.unsubscribeAll(_user.getEmailAddress(), unsubscribeIds);
            } catch (Exception e) {
                LOG.warn(String.format("Failed to unsubscribe user %s for mailing %s: %s", _user.getEmailAddress(), unsubscribeIds, e.getMessage()));
            }
        }
    }
}
