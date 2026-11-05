package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;

import java.util.List;

public interface EmailSubscriptionUtils {

    boolean isActive();

    List<SubscriptionSelection> getSubscriptions(String emailAddress) throws Exception;

    void updateSubscriptions(User user, List<SubscriptionSelection> subscriptions) throws Exception;

    List<SubscriptionSelection> getConsentGroups(String emailAddress) throws Exception;

    void updateConsentGroups(User user, List<SubscriptionSelection> consentGroups) throws Exception;
}
