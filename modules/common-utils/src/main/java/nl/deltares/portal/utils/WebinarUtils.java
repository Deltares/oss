package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.util.Map;

public interface WebinarUtils {

    boolean isActive();

    int registerUser(User user, String webinarKey, String callerId, Map<String, String> registrationProperties) throws Exception;

    Map<String, String> getRegistration(User user, String webinarKey) throws Exception;

    int unregisterUser(User user, String webinarKey, Map<String, String> registrationProperties) throws Exception;

}
