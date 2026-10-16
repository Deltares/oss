package nl.deltares.forms.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.model.RegistrationFormContext;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.EmailSubscriptionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubscriptionsDisplayContext {

    private static final Log LOG = LogFactoryUtil.getLog(SubscriptionsDisplayContext.class);

    private final List<String> _subscriptionIds = new ArrayList<>();
    private final EmailSubscriptionUtils _subscriptionUtil;
    private final User _user;

    public SubscriptionsDisplayContext(User user, List<String> subscriptionIds,
                                       EmailSubscriptionUtils subscriptionUtil) {

        _subscriptionUtil = subscriptionUtil;
        _subscriptionIds.addAll(subscriptionIds);
        _user = user;

    }

    public List<SubscriptionSelection> getSubscriptions(HttpServletRequest request) {

        RegistrationFormContext context = (RegistrationFormContext) request.getSession().getAttribute("registration-context");
        List<SubscriptionSelection> subscriptions;
        if (context != null && context.getSubscriptions() != null) {
            return context.getSubscriptions();
        } else {
            subscriptions = new ArrayList<>();
            if (context != null) {
                context.setSubscriptions(subscriptions);
            }
        }

        String emailAddress = _user.getEmailAddress();
        List<SubscriptionSelection> subscriptionSelections;
        try {
            subscriptionSelections = _subscriptionUtil.getSubscriptions(emailAddress);
        } catch (Exception e) {
            LOG.warn("Error retrieving subscriptions: " + e.getMessage());
            return Collections.emptyList();
        }
        for (SubscriptionSelection subscription : subscriptionSelections) {
            if (_subscriptionIds.contains(subscription.getId())) {
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }

    public void storeSubscriptionInfo(HttpServletRequest httpServletRequest) {

        List<String> subscribeIds = new ArrayList<>();
        List<String> unsubscribeIds = new ArrayList<>();
        List<SubscriptionSelection> subscriptions = getSubscriptions(httpServletRequest);
        subscriptions.forEach(subscription -> {
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
