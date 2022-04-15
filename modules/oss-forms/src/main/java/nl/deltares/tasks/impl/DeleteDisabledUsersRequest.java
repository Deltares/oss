package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DeleteDisabledUsersRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DeleteDisabledUsersRequest.class);
    private final String disabledUsersPath;
    private final long companyId;
    private final AdminUtils adminUtils;

    public DeleteDisabledUsersRequest(String id, long currentUserId, long companyId, String dataFile, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);

        this.disabledUsersPath = dataFile;
        this.companyId = companyId;
        this.adminUtils = adminUtils;
    }

    @Override
    public STATUS call() {

        final File disabledUsersFile = new File(disabledUsersPath);
        if (!disabledUsersFile.exists()) {
            errorMessage = "Disabled users file does not exist: " + disabledUsersPath;
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
            totalCount = (int) Files.size(disabledUsersFile.toPath());
        } catch (IOException e) {
            errorMessage = "Error getting file size: " + e.getMessage();
            status = terminated;
            fireStateChanged();
            return status;
        }

        final List<Group> siteGroups;
        try {
            siteGroups = GroupLocalServiceUtil.getGroups(companyId, 0, true);
        } catch (Exception e) {
            errorMessage = "Error getting siteGroups: " + e.getMessage();
            status = terminated;
            fireStateChanged();
            return status;
        }

        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            //Download results to file
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

                writer.println("Start deleting downloaded disabled users from file: " + disabledUsersPath);
                String line;
                int processedUsers = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader(disabledUsersFile))) {
                    line = reader.readLine(); //skip header
                    incrementProcessCount(line.length());
                    while ((line = reader.readLine()) != null) {
                        incrementProcessCount(line.length());
                        final String[] split = line.split(";");
                        if (split.length == 0) continue;
                        String email = split[0];
                        processedUsers++;
                        writer.println("Processing disabled user " + email);
                        User user;
                        try {
                            user = UserLocalServiceUtil.getUserByEmailAddress(companyId, email);
                        } catch (PortalException e) {
                            writer.printf("Could not find user for %s in company %d", email, companyId);
                            continue;
                        }
                        for (Group siteGroup : siteGroups) {
                            adminUtils.deleteUserAndRelatedContent(siteGroup.getGroupId(), user, writer, false);
                        }
                    }
                }
                if (processedUsers == 0){
                    writer.println("no disabled users found.");
                    status = nodata;
                } else {
                    status = available;
                }
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
