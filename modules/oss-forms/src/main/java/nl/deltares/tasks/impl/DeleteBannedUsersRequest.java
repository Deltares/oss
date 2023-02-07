package nl.deltares.tasks.impl;

import com.liferay.message.boards.model.MBBan;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DeleteBannedUsersRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DeleteBannedUsersRequest.class);
    private final AdminUtils adminUtils;
    private final List<MBBan> bannedUsers;

    public DeleteBannedUsersRequest(String id, long currentUserId,  List<MBBan> bannedUsers, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);
        this.bannedUsers = bannedUsers;
        this.adminUtils = adminUtils;
        totalCount = bannedUsers.size();
        if (totalCount == 0) status = nodata;
    }

    @Override
    public STATUS call() {

        if (status == available || status == nodata) return status;

        init();
        status = running;

        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            //Create local session because the servlet session will close after call to endpoint is completed
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))){
                List<User> deletionUsers = new ArrayList<>();
                for (MBBan bannedUser : bannedUsers) {
                    if (bannedUser.getBanUserId() == currentUserId) {
                        writer.printf("Error: Not allowed to delete yourself %s", bannedUser.getUserName());
                        continue;
                    }
                    User user;
                    try {
                        user = UserLocalServiceUtil.getUser(bannedUser.getBanUserId());
                    } catch (PortalException e) {
                        writer.printf("Could not find user for userId %d\n", bannedUser.getBanUserId());
                        continue;
                    }
                    if (user.isDefaultUser()){
                        writer.println("Skipping Default user : " + user.getEmailAddress());
                        continue;
                    }
                    if (user.getEmailAddress().endsWith("@deltares.nl") || user.getEmailAddress().endsWith("@liferay.com") ){
                        writer.println("Skipping Deltares or admin user : " + user.getEmailAddress());
                        continue;
                    }
                    if (!deletionUsers.contains(user)) deletionUsers.add(user);
                    adminUtils.deleteUserRelatedContent(bannedUser.getGroupId(), user, writer);
                    //start flushing
                    writer.flush();
                    incrementProcessCount(1);
                    if (Thread.interrupted()) {
                        status = terminated;
                        errorMessage = String.format("Thread 'DeleteBannedUsersRequest' with id %s is interrupted!", id);
                        break;
                    }
                }
                if (status != terminated) {
                    for (User deletionUser : deletionUsers) {
                        writer.printf("********** Start deleting user %s (%s) in company %d  ***********\n",
                                deletionUser.getScreenName(), deletionUser.getEmailAddress(), deletionUser.getCompanyId());

                        adminUtils.deleteLiferayUser(deletionUser, writer);
                        try {
                            adminUtils.getKeycloakUtils().deleteUserWithEmail(deletionUser.getEmailAddress());
                            writer.printf("Deleted user in keycloak\n");
                        } catch (Exception e) {
                            writer.printf("Failed to delete user in Keycloak: %s\n", e.getMessage());
                        }

                        writer.printf("********** Finished deleting user %s (%s) in company %d  ***********\n",
                                deletionUser.getScreenName(), deletionUser.getEmailAddress(), deletionUser.getCompanyId());
                    }
                }
                if (status != terminated) {
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
