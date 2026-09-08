package nl.deltares.tableview.tasks.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.tableview.utils.RegistrationUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DeletedSelectedDownloadsRequest extends AbstractDataRequest {


    private static final Log logger = LogFactoryUtil.getLog(DeletedSelectedDownloadsRequest.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private final List<String> selectedRecords;
    private final String filterValue;
    private final boolean filterEmptyFileName;
    private final boolean findByUser;
    private final Group group;

    public DeletedSelectedDownloadsRequest(String id, String filterValue, boolean filterEmptyFileName, long currentUserId, Group siteGroup) throws IOException {
        super(id, currentUserId);
        if (filterValue != null && filterValue.trim().isEmpty()){
            this.filterValue = null;
        } else {
            this.filterValue = filterValue;
        }
        this.filterEmptyFileName = filterEmptyFileName;
        this.findByUser = Validator.isEmailAddress(filterValue);
        this.selectedRecords = Collections.emptyList();
        this.group = siteGroup;
    }

    public DeletedSelectedDownloadsRequest(String id, long currentUserId, List<String> recordIds) throws IOException {
        super(id, currentUserId);
        this.selectedRecords = recordIds;
        this.filterValue = null;
        this.filterEmptyFileName = false;
        this.findByUser = false;
        this.group = null;
    }

    @Override
    public STATUS call() {
        if (getStatus() == AVAILABLE) return status;
        status = RUNNING;
        statusMessage = "start deleting...";
        init();
        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                deleteSelectedRecords(writer);
                if (status != TERMINATED) {
                    status = AVAILABLE;
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = TERMINATED;
            }
            if (status == AVAILABLE){
                this.dataFile = new File(getExportDir(), id + ".csv");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = TERMINATED;
        }
        fireStateChanged();

        return status;
    }

    private void deleteSelectedRecords(PrintWriter writer) {
        writer.println("downloadId,modifiedDate,expirationDate,fileName,fileShareUrl,email,organization,geolocationId");

        if (!selectedRecords.isEmpty()) {
            deleteBySelectedIds(writer);
        } else  {
            deleteByFilterSelection(writer);
        }

    }
    private void deleteByFilterSelection(PrintWriter writer) {

        final long[] filterUserId = new long[1];
        if (findByUser) {
            User filterUser = UserLocalServiceUtil.fetchUserByEmailAddress(group.getCompanyId(), filterValue);
            if (filterUser == null) {
                status = NODATA;
                errorMessage = String.format("User not found for email %s", filterValue);
                setProcessCount(totalCount);
                return;
            }
            filterUserId[0] = filterUser.getUserId();
        }
        totalCount = RegistrationUtils.getTotalDownloadsCountForFilterSelection(group, filterValue, filterEmptyFileName, filterUserId[0]);

        for (int i = 0; i < totalCount; ) {
            if (status == TERMINATED) return;
            final List<Download> downloads = RegistrationUtils.getDownloadsForFilterSelection(group.getGroupId(),
                    filterValue, filterUserId[0], filterEmptyFileName, 0, 100);
            if (downloads.isEmpty()) {
                setProcessCount(totalCount);
                return;
            }

            downloads.forEach(download -> {
                try {
                    DownloadLocalServiceUtil.deleteDownload(download);
                    writeToLogMessage(writer, download, filterUserId[0]);
                } finally {
                    incrementProcessCount(1);
                }
                if (Thread.interrupted()) {
                    status = TERMINATED;
                    errorMessage = String.format("Thread 'DeletedSelectedDownloadsRequest' with id %s is interrupted!", id);
                }
            });
            i += downloads.size();

        }
    }

    private static void writeToLogMessage(PrintWriter writer, Download download, long filterUserId) {
        String email = RegistrationUtils.getEmail(filterUserId > 0 ? filterUserId : download.getUserId());
        final Date modifiedDate = download.getModifiedDate();
        final Date expiryDate = download.getExpiryDate();
        writer.println(String.format("%d,%s,%s,%s,%s,%s,%s,%d",
                download.getDownloadId(), dateFormat.format(modifiedDate == null ? new Date(): modifiedDate),
                dateFormat.format(expiryDate == null ? new Date() : expiryDate),
                download.getFileName(), download.getFileShareUrl(), email, download.getOrganization(),
                download.getGeoLocationId()));
    }

    private void deleteBySelectedIds(PrintWriter writer) {

        totalCount = selectedRecords.size();

        selectedRecords.forEach(id -> {
            if (status == TERMINATED) return;
            try {
                final Download download = DownloadLocalServiceUtil.deleteDownload(Long.parseLong(id));
                writeToLogMessage(writer, download, 0);
            } catch (PortalException e) {
                writer.println(String.format("Failed to delete record %s: %s", id, e.getMessage()));
            } finally {
                incrementProcessCount(1);
            }
            if (Thread.interrupted()) {
                status = TERMINATED;
                errorMessage = String.format("Thread 'DeletedSelectedDownloadsRequest' with id %s is interrupted!", id);
            }
        });
    }

}
