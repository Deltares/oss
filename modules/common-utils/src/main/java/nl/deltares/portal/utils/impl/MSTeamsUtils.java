package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.WebinarUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MSTeamsUtils extends HttpClientUtils implements WebinarUtils {

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public int registerUser(User user, Map<String, String> userAttributes, String webinarKey, String callerId, Map<String, String> registrationProperties) throws Exception {
        return 0;
    }

    @Override
    public int unregisterUser(User user, String webinarKey, Map<String, String> registrationProperties) throws Exception {
        return 0;
    }

    @Override
    public List<String> getAllCourseRegistrations(String webinarKey) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUserInCourseRegistrationsList(List<String> courseRegistrations, User user) {
        return false;
    }
}
