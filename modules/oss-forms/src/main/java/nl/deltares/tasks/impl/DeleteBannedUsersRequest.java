package nl.deltares.tasks.impl;

import com.liferay.message.boards.model.MBBan;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DeleteBannedUsersRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DeleteBannedUsersRequest.class);
    private final AdminUtils adminUtils;
    private final long siteId;
    private final List<MBBan> bannedUsers;

    public DeleteBannedUsersRequest(String id, long siteId, long currentUserId,  List<MBBan> bannedUsers, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);
        this.siteId = siteId;
        this.bannedUsers = bannedUsers;
        this.adminUtils = adminUtils;
        totalCount = bannedUsers.size();
        if (totalCount == 0) status = nodata;
    }

    @Override
    public void start() {

        if (status == available || status == nodata) return;

        if (thread != null) throw new IllegalStateException("Thread already started!");
        status = running;

        thread = new Thread(() -> {

            try {
                File tempFile = new File(getExportDir(), id + ".tmp");
                if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

                //Create local session because the servlet session will close after call to endpoint is completed
                try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))){

                    for (MBBan bannedUser : bannedUsers) {
                        if (bannedUser.getBanUserId() == currentUserId) {
                            writer.printf("Error: Not allowed to delete yourself %s", bannedUser.getUserName());
                            continue;
                        }
                        adminUtils.deleteUserAndRelatedContent(siteId, bannedUser.getBanUserId(), writer);
                        //start flushing
                        writer.flush();
                        processedCount++;
                    }

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

        }, id);

        thread.start();

    }

}
