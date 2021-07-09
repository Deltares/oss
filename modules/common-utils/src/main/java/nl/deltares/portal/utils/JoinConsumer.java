package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.util.Map;

public interface JoinConsumer {
    String getJoinLink(User user, String joinKey, Map<String, String> properties);
}
