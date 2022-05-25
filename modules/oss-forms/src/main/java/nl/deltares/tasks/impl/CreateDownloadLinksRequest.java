package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.model.DownloadRequest;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.*;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.IOException;
import java.util.*;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class CreateDownloadLinksRequest extends AbstractDataRequest {

    private static final Log LOG = LogFactoryUtil.getLog(CreateDownloadLinksRequest.class);
    private final DownloadRequest downloadRequest;
    private final User user;
    private final DownloadUtils downloadUtils;


    public CreateDownloadLinksRequest(String id, User user, DownloadRequest downloadRequest, DownloadUtils downloadUtils) throws IOException {
        super(id, user.getUserId());
        this.downloadRequest = downloadRequest;
        this.downloadUtils = downloadUtils;
        this.user = user;
    }

    @Override
    public STATUS call() {

        if (getStatus() == available) return status;
        status = running;
        statusMessage = "Start creating share links";
        init();

        final String emailAddress = user.getEmailAddress();
        final List<Download> downloads = downloadRequest.getDownloads();
        totalCount = downloads.size();

        try {
            downloads.forEach(download -> setStatusToProcessing(user, Long.parseLong(download.getArticleId()), download.getFilePath()));
            for (Download download : downloads) {

                Map<String, Object> shareInfo;
                if (download.isBillingRequired()) {
                    LOG.info(String.format("Creation of share link for user '%s' on file '%s' is pending payment.", emailAddress, download.getFileName()));
                    shareInfo = new HashMap<>();
                    shareInfo.put("id", -1);
                } else {

                    try {
                        shareInfo = downloadUtils.shareLinkExists(download.getFilePath(), emailAddress);
                        if (shareInfo.isEmpty()) {
                            shareInfo = downloadUtils.sendShareLink(download.getFilePath(), emailAddress);
                        } else {
                            shareInfo = downloadUtils.resendShareLink((Integer) shareInfo.get("id"));
                        }
                    } catch (Exception e) {
                        errorMessage = String.format("Failed to send link for file %s : %s ", download.getFileName(), e.getMessage());
                        LOG.warn(errorMessage);
                        continue;
                    } finally {
                        unsetStatusFromProcessing(user, Long.parseLong(download.getArticleId()));
                    }
                }

                try {
                    downloadUtils.registerDownload(user, downloadRequest.getGroupId() , Long.parseLong(download.getArticleId()),
                            download.getFilePath(), shareInfo, downloadRequest.getUserAttributes());
                } catch (PortalException e) {
                    errorMessage = String.format("Failed to register link for file %s : %s ", download.getFileName(), e.getMessage());
                    LOG.warn(errorMessage);
                }
                incrementProcessCount(1);

                if (Thread.interrupted()) {
                    status = terminated;
                    errorMessage = String.format("Thread 'CreateDownloadLinksRequest' with id %s is interrupted!", id);
                    break;
                }
            }
            status = available;
            statusMessage = String.format("%d share links have been created for user %s", getProcessedCount(), user.getEmailAddress());
            LOG.info(statusMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
        } finally {
            fireStateChanged();
        }

        return status;
    }

    private void unsetStatusFromProcessing(User user, long downloadId) {
        try {
            final Map<String, Object> shareInfo = new HashMap<>();
            shareInfo.put("id", -8);
            downloadUtils.registerDownload(user, downloadRequest.getGroupId(), downloadId, null, shareInfo, Collections.emptyMap());
        } catch (PortalException e) {
            LOG.warn("Error unsetting direct download url: " + e.getMessage());
        }
    }

    private void setStatusToProcessing(User user, long downloadId, String filePath) {
        try {
            final Map<String, Object> shareInfo = new HashMap<>();
            shareInfo.put("id", -9);
            downloadUtils.registerDownload(user, downloadRequest.getGroupId(), downloadId, filePath, shareInfo, Collections.emptyMap());
        } catch (PortalException e) {
            LOG.warn("Error registering direct download url: " + e.getMessage());
        }
    }

}
