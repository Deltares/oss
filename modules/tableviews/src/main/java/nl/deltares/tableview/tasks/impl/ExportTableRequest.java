package nl.deltares.tableview.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static nl.deltares.tasks.DataRequest.STATUS.*;
import static nl.deltares.tasks.DataRequest.STATUS.terminated;

public class ExportTableRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(ExportTableRequest.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private final Group group;
    public ExportTableRequest(String id, long currentUserId, Group siteGroup) throws IOException {
        super(id, currentUserId);
        this.group = siteGroup;
    }

    @Override
    public STATUS call() throws Exception {
        if (getStatus() == available) return status;
        status = running;
        statusMessage = "starting exporting...";
        init();
        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                exportAllRecords(writer);
                status = available;
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = terminated;
            }
            if (status == available){
                this.dataFile = new File(getExportDir(), id + ".csv");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
        fireStateChanged();

        return status;
    }

    private void exportAllRecords(PrintWriter writer) {
        writer.println("downloadId,modifiedDate,shareId,filePath,directDownloadUrl,email,organization,city,country");

        int start = 0;
        int end = 100;
        totalCount = DownloadLocalServiceUtil.getDownloadsCount();

        for (int i = start; i < totalCount; i++) {
            final List<Download> downloads = DownloadLocalServiceUtil.getDownloads(start, end);
            if (downloads.size() == 0) {
                processedCount = totalCount;
                return;
            }
            downloads.forEach(download -> {
                processedCount++;
                if (group != null && group.getGroupId() != download.getGroupId()) return;
                final User user = UserLocalServiceUtil.fetchUser(download.getUserId());
                String email = "";
                if (user != null){
                    email = user.getEmailAddress();
                }
                writer.println(String.format("%d,%s,%d,%s,%s,%s,%s,%s,%s",
                        download.getDownloadId(), dateFormat.format(download.getModifiedDate()), download.getShareId(),
                        download.getFilePath(), download.getDirectDownloadUrl(), email, download.getOrganization(),
                        download.getCity(), download.getCountryCode()));

            });
            start = end;
            end += 100;
        }

    }

}
