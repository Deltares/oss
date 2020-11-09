package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.WebinarUtils;

import java.util.Map;

public class ANewSpringUtils extends HttpClientUtils implements WebinarUtils {
    private static final Log LOG = LogFactoryUtil.getLog(ANewSpringUtils.class);

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public Map<String, String> registerUser(User user, String webinarKey, String callerId) throws Exception {
        return null;
    }

    @Override
    public Map<String, String> getRegistration(User user, String webinarKey) throws Exception {
        return null;
    }

    @Override
    public int unregisterUser(String registrantsKey, String webinarKey) throws Exception {
        return 0;
    }

    @Override
    public boolean isMeetingSupported(Registration registration) {
        return false;
    }
}
