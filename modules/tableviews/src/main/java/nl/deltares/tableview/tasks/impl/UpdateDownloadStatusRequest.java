package nl.deltares.tableview.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.IOException;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class UpdateDownloadStatusRequest extends AbstractDataRequest {
    private static final Log LOG = LogFactoryUtil.getLog(UpdateDownloadStatusRequest.class);
    private final User user;
    private final DownloadUtils downloadUtils;
    private final long groupId;

    public UpdateDownloadStatusRequest(String id, User user, long groupId, long currentUserId, DownloadUtils downloadUtils) throws IOException {
        super(id, currentUserId);
        this.user = user;
        this.groupId = groupId;
        this.downloadUtils = downloadUtils;

    }

    @Override
    public long getTimeoutMillis() {
        return 30000;
    }

    @Override
    public STATUS call() {
        if (getStatus() == available) return status;
        status = running;
        String userInfo = user != null? " user " + user.getEmailAddress() : "ALL users";
        statusMessage = String.format("Start activity %s for %s", id, userInfo);
        LOG.info(status);
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
                errorMessage = String.format("Error updating pending shares for %s: %s", userInfo, e.getMessage());
                throw e;
            }
            try {
                //this value should contain the origin of the login request
                downloadUtils.updateProcessingShares(user, groupId);
            } catch (Exception e) {
                errorMessage = String.format("Error updating processing shares for %s: %s", userInfo, e.getMessage());
                throw e;
            }

            status = available;
            statusMessage = String.format("Finished activity %s for %s", id, userInfo);
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
