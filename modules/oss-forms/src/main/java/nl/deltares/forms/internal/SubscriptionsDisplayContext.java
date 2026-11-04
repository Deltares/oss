package nl.deltares.forms.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.EmailSubscriptionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    public List<SubscriptionSelection> getSubscriptions() {

        String emailAddress = _user.getEmailAddress();
        List<SubscriptionSelection> subscriptionSelections;
        try {
            subscriptionSelections = _subscriptionUtil.getSubscriptions(emailAddress);
        } catch (Exception e) {
            LOG.warn("Error retrieving subscriptions: " + e.getMessage());
            return Collections.emptyList();
        }
        List<SubscriptionSelection> subscriptions = new ArrayList<>();
        for (SubscriptionSelection subscription : subscriptionSelections) {
            if (_subscriptionIds.contains(subscription.getId())) {
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }

    public void storeSubscriptionInfo(HttpServletRequest httpServletRequest) {

        List<SubscriptionSelection> subscriptions = new ArrayList<>();
        try {
            subscriptions.addAll(_subscriptionUtil.getSubscriptions(_user.getEmailAddress()));
        } catch (Exception e) {
            LOG.warn("Error retrieving subscriptions: " + e.getMessage());
        }
        subscriptions.forEach(subscription -> {
            String mailingId = subscription.getId();
            final String selected = ParamUtil.getString(httpServletRequest, "subscription-" + mailingId, "false");
            boolean bSelected = Boolean.parseBoolean(selected);
            subscription.setSelected(bSelected);
        });

        try {
            _subscriptionUtil.updateSubscriptions(_user, subscriptions);
        } catch (Exception e) {
            LOG.warn(String.format("Failed to update subscriptions for user %s: %s", _user.getEmailAddress(), e.getMessage()));
        }

    }
}
