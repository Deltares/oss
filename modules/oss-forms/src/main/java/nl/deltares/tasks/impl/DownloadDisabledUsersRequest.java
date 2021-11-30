package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DownloadDisabledUsersRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DownloadDisabledUsersRequest.class);
    private final AdminUtils adminUtils;
    private final long disableTimeAfter;

    public DownloadDisabledUsersRequest(String id, long disableTimeAfter, long currentUserId, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);
        this.adminUtils = adminUtils;
        this.disableTimeAfter = disableTimeAfter;
    }

    @Override
    public STATUS call() {

        if (status == available || status == nodata) return status;

        init();
        status = running;

        //dummy set something to show in progress bar
        totalCount = 100;

        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            //Download results to file
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                adminUtils.downloadDisabledUsers(disableTimeAfter, writer);
                processedCount = 100;
                status = available;
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = terminated;
            }
            if (status == available) {
                this.dataFile = new File(getExportDir(), id + ".data");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }
        } catch (IOException e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
        fireStateChanged();

        return status;

    }

    @Override
    public int getProcessedCount() {
        //dummy something to show in progress bar.
        if (status == running){
            processedCount++;
            if (processedCount == totalCount) processedCount = 0;
        }
        return processedCount;
    }

}
