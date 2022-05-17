package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.Subscription;

import java.util.List;

public interface EmailSubscriptionUtils {

    boolean isActive();
    boolean isSubscribed(User user, List<Subscription> subscriptions) throws Exception;
    boolean isSubscribed(User user, Subscription subscription) throws Exception;
    void subscribe(User user, Subscription subscription) throws Exception;
    void unsubscribe(User user, Subscription subscription) throws Exception;
}
