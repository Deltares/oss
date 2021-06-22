package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.util.List;
import java.util.Map;

public interface DsdUserUtils {

    boolean isSubscribed(String emailAddress, List<String> mailingIdsList) throws Exception;

    void subscribe(String emailAddress, String mailingId) throws Exception;

    void unsubscribe(String emailAddress, String mailingId) throws Exception;

    enum ATTRIBUTES {
        badge_name_setting,
        badge_title_setting
    }

    Map<String, String> getUserAttributes(User user) throws Exception;

    String getUserAttribute(User user, DsdUserUtils.ATTRIBUTES attribute) throws Exception;

    void setUserAttribute(User user, DsdUserUtils.ATTRIBUTES attribute, String value) throws Exception;

    void deleteUserAttribute(User user, DsdUserUtils.ATTRIBUTES attribute) throws Exception;

    int updateUserAttributes(User user, Map<String, String> attributes) throws Exception;
}
