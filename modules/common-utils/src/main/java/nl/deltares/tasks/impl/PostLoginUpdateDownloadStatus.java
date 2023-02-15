package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.IOException;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class PostLoginUpdateDownloadStatus extends AbstractDataRequest {
    private static final Log LOG = LogFactoryUtil.getLog(PostLoginUpdateDownloadStatus.class);
    private final User user;
    private final DownloadUtils downloadUtils;
    private final long groupId;

    public PostLoginUpdateDownloadStatus(String id, User user, long groupId, DownloadUtils downloadUtils) throws IOException {
        super(id, user.getUserId());
        this.user = user;
        this.groupId = groupId;
        this.downloadUtils = downloadUtils;
    }

    @Override
    public long getTimeoutMillis() {
        return 5000;
    }

    @Override
    public STATUS call() throws Exception {
        if (getStatus() == available) return status;
        status = running;
        statusMessage = String.format("Start post login activity %s for user %s", id, user.getEmailAddress());
        init();

        if (downloadUtils == null || !downloadUtils.isActive()) {
            status = terminated;
            return status;
        }

        try {

            try {
                //Check if users has any pending shares that need to be updated
                downloadUtils.updatePendingShares(user, groupId);

            } catch (Exception e) {
                errorMessage = String.format("Error updating pending shares for user %s: %s", user.getEmailAddress(), e.getMessage());
                throw e;
            }
            try {
                //this value should contain the origin of the login request
                downloadUtils.updateProcessingShares(user, groupId);
            } catch (Exception e) {
                errorMessage = String.format("Error updating processing shares for user %s: %s", user.getEmailAddress(), e.getMessage());
                throw e;
            }

            status = available;
            statusMessage = String.format("Post login activity %s completed for user %s", id, user.getEmailAddress());
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
