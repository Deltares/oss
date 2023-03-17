package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Map;

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
            int keycloakProcessStatus = 0;
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile, false))) {
                logger.info("Calling Keycloak 'download-invalid-users'");
                keycloakProcessStatus = adminUtils.getKeycloakUtils().downloadInvalidUsers(null, writer);
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn(String.format("Error calling 'download-invalid-users': %s", e.getMessage()), e);
                status = terminated;
            }
            if (keycloakProcessStatus == 102) {
                try {
                    waitForProcessToComplete(tempFile);
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    logger.warn(String.format("Error calling 'download-invalid-users': %s", errorMessage), e);
                    status = terminated;
                }
            }

            incrementProcessCount(100);
            if (status != terminated) {
                this.dataFile = new File(getExportDir(), id + ".data");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
                logger.info("Finished calling Keycloak 'check-users-exist' for file: " + dataFile);
                status = available;
            }
        } catch (IOException e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
        fireStateChanged();

        return status;

    }

    private void waitForProcessToComplete(File tempFile) throws Exception {
        int keycloakStatus = 102;
        while (keycloakStatus == 102) {

            String response = new String(Files.readAllBytes(tempFile.toPath()));
            final Map<String, String> jsonToMap = JsonContentUtils.parseJsonToMap(response);
            final String id = jsonToMap.get("id");
            final String processed = jsonToMap.get("processed");
            setProcessCount(Integer.parseInt(processed));

            Thread.sleep(1000);

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile, false))) {
                keycloakStatus = adminUtils.getKeycloakUtils().downloadInvalidUsers(id, writer);
            }

        }
    }

}
