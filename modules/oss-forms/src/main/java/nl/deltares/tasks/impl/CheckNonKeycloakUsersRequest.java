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

public class CheckNonKeycloakUsersRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(CheckNonKeycloakUsersRequest.class);
    private final String usersFilePath;
    private final AdminUtils adminUtils;

    public CheckNonKeycloakUsersRequest(String id, long currentUserId, String usersFile, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);
        this.usersFilePath = usersFile;
        this.adminUtils = adminUtils;
    }

    @Override
    public STATUS call() {

        final File usersFile = new File(usersFilePath);
        if (!usersFile.exists()) {
            errorMessage = "File containing users does not exist: " + usersFilePath;
            status = terminated;
            fireStateChanged();
            return status;
        }

        if (status == available || status == nodata) {
            return status;
        }

        init();
        status = running;

        //dummy set something to show in progress bar
        totalCount = 100;

        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            //Download results to file
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                logger.info("Calling Keycloak 'check-users-exist' for file: " + usersFilePath);
                adminUtils.getKeycloakUtils().callCheckUsersExist(usersFile, writer);
                incrementProcessCount(100);
                logger.info("Finished calling Keycloak 'check-users-exist' for file: " + usersFilePath);
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn(String.format("Error calling 'check-users-exist': %s", errorMessage), e);
                status = terminated;
            }
            if (status != terminated) {
                this.dataFile = new File(getExportDir(), id + ".data");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
                status = available;
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
