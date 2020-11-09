package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WebinarUtils {

    boolean isActive();

    int registerUser(User user, String webinarKey, String callerId, Map<String, String> registrationProperties) throws Exception;

    boolean isUserRegistered(User user, String webinarKey, Map<String, String> registrationProperties) throws Exception;

    int unregisterUser(User user, String webinarKey, Map<String, String> registrationProperties) throws Exception;

}
