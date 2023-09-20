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

public class DownloadInvalidUsersRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DownloadInvalidUsersRequest.class);
    private final AdminUtils adminUtils;

    public DownloadInvalidUsersRequest(String id, long currentUserId, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);
        this.adminUtils = adminUtils;
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
                adminUtils.getKeycloakUtils().downloadInvalidUsers(writer);
                incrementProcessCount(100);
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
    public String getStatusMessage() {
        //dummy something to show in progress bar.
        if (status == running){
            int processedCount = super.getProcessedCount();
            processedCount++;
            if (processedCount == totalCount) super.setProcessCount(0);
            else setProcessCount(processedCount);
        }
        return super.getStatusMessage();
    }

}
