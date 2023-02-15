package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.IOException;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class PostLoginUpdateUserInfo extends AbstractDataRequest {
    private static final Log LOG = LogFactoryUtil.getLog(PostLoginUpdateUserInfo.class);
    private final User user;
    private final KeycloakUtils keycloakUtils;
    private final String siteId;

    public PostLoginUpdateUserInfo(String id, User user, String siteId, KeycloakUtils keycloakUtils) throws IOException {
        super(id, user.getUserId());
        this.user = user;
        this.siteId = siteId;
        this.keycloakUtils = keycloakUtils;
    }

    @Override
    public long getTimeoutMillis() {
        return 5000;
    }

    @Override
    public STATUS call() throws Exception {
        if (getStatus() == available) return status;
        status = running;
        statusMessage = "Start post login user update";
        init();

        if (keycloakUtils == null || !keycloakUtils.isActive()) {
            status = terminated;
            return status;
        }

        try {

            try {
                byte[] bytes = keycloakUtils.getUserAvatar(user.getEmailAddress());
                if (bytes != null && bytes.length > 0) {
                    LOG.info("Updating avatar for user " + user.getFullName());
                    UserLocalServiceUtil.updatePortrait(user.getUserId(), bytes);
                } else {
                    LOG.info("Deleting avatar for user " + user.getFullName());
                    UserLocalServiceUtil.deletePortrait(user.getUserId());
                }
            } catch (Exception e) {
                errorMessage = String.format("Error updating portrait %d for user %s", user.getPortraitId(), user.getScreenName());
                throw e;
            }
            try {
                //this value should contain the origin of the login request
                if (siteId != null) {
                    LOG.info(String.format("Register user '%s' login to site '%s'", user.getFullName(), siteId));
                    keycloakUtils.registerUserLogin(user.getEmailAddress(), siteId);
                }
            } catch (Exception e) {
                errorMessage = String.format("Error registering user %s to site: %s", user.getEmailAddress(), e.getMessage());
                throw e;
            }

            status = available;
            statusMessage = String.format("Post login activity completed for user %s", user.getEmailAddress());
            LOG.info(statusMessage);
        } catch (Exception e) {
            LOG.warn(errorMessage, e);
            status = terminated;
        } finally {
            fireStateChanged();
        }

        return status;
    }
}
