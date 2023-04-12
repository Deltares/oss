package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.subscriptions.Subscription;

import java.util.List;

public interface EmailSubscriptionUtils {

    boolean isActive();

    boolean isSubscribed(String email, List<String> subscriptionIds) throws Exception;

    boolean isSubscribed(String email, String subscriptionId) throws Exception;

    void subscribe(User user, String subscriptionId) throws Exception;

    void unsubscribe(String email, String subscriptionId) throws Exception;

    void deleteUser(String email) throws Exception;

    List<Subscription> getSubscriptions(String emailAddress) throws Exception;


}
