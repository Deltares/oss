package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.WebinarUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MSTeamsUtils extends HttpClientUtils implements WebinarUtils {
    private static final Log LOG = LogFactoryUtil.getLog(MSTeamsUtils.class);

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public int registerUser(User user, String webinarKey, String callerId, Map<String, String> registrationProperties) throws Exception {
        return 0;
    }

    @Override
    public boolean isUserRegistered(User user, String webinarKey, Map<String, String> registrationProperties) throws Exception {
        return false;
    }

    @Override
    public int unregisterUser(User user, String webinarKey, Map<String, String> registrationProperties) throws Exception {
        return 0;
    }
}
