package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.util.List;
import java.util.Map;

public interface WebinarUtils {

    boolean isActive();

    int registerUser(User user, Map<String, String> userAttributes, String webinarKey, String callerId, Map<String, String> registrationProperties) throws Exception;

    int unregisterUser(User user, String webinarKey, Map<String, String> registrationProperties) throws Exception;

    List<String> getAllCourseRegistrations(String webinarKey) throws Exception;

    boolean isUserInCourseRegistrationsList(List<String> courseRegistrations, User user);

}
