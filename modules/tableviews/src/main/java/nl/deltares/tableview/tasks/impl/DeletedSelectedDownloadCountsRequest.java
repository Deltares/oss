package nl.deltares.tableview.tasks.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.DsdParserUtils;
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

public class DeletedSelectedDownloadCountsRequest extends AbstractDataRequest {


    private static final Log logger = LogFactoryUtil.getLog(DeletedSelectedDownloadCountsRequest.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private final List<String> selectedRecords;
    private final DsdParserUtils dsdParserUtils;

    public DeletedSelectedDownloadCountsRequest(String id, long currentUserId, List<String> recordIds, DsdParserUtils dsdParserUtils) throws IOException {
        super(id, currentUserId);
        this.selectedRecords = recordIds;
        this.dsdParserUtils = dsdParserUtils;
    }

    @Override
    public STATUS call() throws Exception {
        if (getStatus() == available) return status;
        status = running;
        statusMessage = "start deleting...";
        init();
        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                deleteSelectedRecords(writer);
                if (status != terminated) {
                    status = available;
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = terminated;
            }
            if (status == available) {
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

    private void deleteSelectedRecords(PrintWriter writer) {
        writer.println("downloadId,filePath,topic,count");

        totalCount = selectedRecords.size();

        selectedRecords.forEach(id -> {
            if (status == terminated) return;
            try {
                final DownloadCount download = DownloadCountLocalServiceUtil.deleteDownloadCount(Long.parseLong(id));
                final String downloadIdString = String.valueOf(download.getDownloadId());
                final Download dsdDownload = (Download) dsdParserUtils.toDsdArticle(download.getGroupId(), downloadIdString);
                writer.println(String.format("%d,%s,%s,%d",
                        download.getDownloadId(), dsdDownload.getFilePath(), dsdDownload.getFileTopic(), download.getCount()));
            } catch (PortalException e) {
                writer.println(String.format("Failed to delete record %s: %s", id, e.getMessage()));
            } finally {
                incrementProcessCount(1);
            }
            if (Thread.interrupted()) {
                status = terminated;
                errorMessage = String.format("Thread 'DeletedSelectedDownloadCountssRequest' with id %s is interrupted!", id);
            }
        });
    }

}
