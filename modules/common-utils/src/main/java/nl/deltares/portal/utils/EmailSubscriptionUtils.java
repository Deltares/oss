package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;

import java.util.List;

public interface EmailSubscriptionUtils {

    boolean isActive();

    boolean isDefault();

    boolean isSubscribed(String email, List<String> subscriptionIds) throws Exception;

    boolean isSubscribed(String email, String subscriptionId) throws Exception;

    void subscribe(User user, String subscriptionId) throws Exception;

    void subscribeAll(User user, List<String> subscriptionIds) throws Exception;

    void unsubscribe(String email, String subscriptionId) throws Exception;

    void unsubscribeAll(String email, List<String> subscriptionIds) throws Exception;

    void deleteUser(String email) throws Exception;

    List<SubscriptionSelection> getSubscriptions(String emailAddress) throws Exception;


}
