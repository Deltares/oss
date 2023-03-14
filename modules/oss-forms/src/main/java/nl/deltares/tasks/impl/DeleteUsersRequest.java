package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DeleteUsersRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DeleteUsersRequest.class);
    private final String usersFilePath;
    private final AdminUtils adminUtils;

    public DeleteUsersRequest(String id, long currentUserId, String dataFile, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);

        this.usersFilePath = dataFile;
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

        try {
            totalCount = (int) Files.size(usersFile.toPath());
        } catch (IOException e) {
            errorMessage = "Error getting file size: " + e.getMessage();
            status = terminated;
            fireStateChanged();
            return status;
        }

        final User currentUser;
        try {
            currentUser = UserLocalServiceUtil.getUser(currentUserId);
        } catch (PortalException e) {
            errorMessage = "Error getting currentUser for userId " + currentUserId + ": " + e.getMessage();
            status = terminated;
            fireStateChanged();
            return status;
        }

        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            final List<VirtualHost> virtualHosts = VirtualHostLocalServiceUtil.getVirtualHosts(0, 10);
            User user;
            //Download results to file
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                String message = String.format("Start deleting users from file: %s", usersFilePath);
                logger.info(message);
                writer.println(message);
                String line;
                int processedUsers = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
                    while ((line = reader.readLine()) != null) {
                        incrementProcessCount(line.length());
                        final String[] split = line.split(";");
                        if (split.length == 0) continue;
                        String email;
                        String keycloakId = null;
                        if (split.length == 1){
                            email = split[0];
                        } else {
                             keycloakId = split[0];
                             email = split[1];
                        }
                        processedUsers++;
                        if (email.endsWith("@liferay.com") && currentUser.getEmailAddress().equals(email)){
                            writer.println("Skipping admin user : " + email);
                            continue;
                        } else {
                            writer.println("Processing user " + email);
                        }

                        for (VirtualHost virtualHost : virtualHosts) {
                            final long companyId = virtualHost.getCompanyId();
                            user = UserLocalServiceUtil.fetchUserByEmailAddress(companyId, email);
                            if (user != null) adminUtils.deleteLiferayUser(user, writer);
                        }
                        try {
                            if (keycloakId != null){
                                adminUtils.getKeycloakUtils().deleteUserWithId(keycloakId);
                            } else {
                                adminUtils.getKeycloakUtils().deleteUserWithEmail(email);
                            }
                            writer.printf("Deleted user %s in keycloak\n", email);
                        } catch (Exception e) {
                            writer.printf("Failed to delete user in Keycloak: %s\n", e.getMessage());
                        }

                        if (Thread.interrupted()) {
                            status = terminated;
                            errorMessage = String.format("Thread 'DeleteUsersRequest' with id %s is interrupted!", id);
                            logger.warn(errorMessage);
                            break;
                        }
                    }
                }
                if (processedUsers == 0) {
                    writer.println("no users found.");
                    status = nodata;
                } else if (status != terminated) {
                    status = available;
                }
                logger.info(String.format("Finished deleting %d users from file: %s", processedUsers,  usersFilePath));
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

}
