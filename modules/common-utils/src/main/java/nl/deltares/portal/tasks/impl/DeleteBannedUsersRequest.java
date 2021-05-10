package nl.deltares.portal.tasks.impl;

import com.liferay.message.boards.model.MBBan;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.tasks.DataRequest;
import nl.deltares.portal.tasks.DataRequestManager;
import nl.deltares.portal.utils.AdminUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import static nl.deltares.portal.tasks.DataRequest.STATUS.*;

public class DeleteBannedUsersRequest implements DataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DeleteBannedUsersRequest.class);
    private final AdminUtils adminUtils;
    private final long currentUserId;
    private DataRequest.STATUS status = pending;
    private final String id;
    private final long siteId;
    private final List<MBBan> bannedUsers;
    private int deletionCount;
    private final File logFile;

    private String errorMessage;

    private Thread thread;
    private File tempDir;

    private DataRequestManager manager;

    public DeleteBannedUsersRequest(String id, long siteId, long currentUserId,  List<MBBan> bannedUsers, AdminUtils adminUtils) throws IOException {
        this.id = id;
        this.siteId = siteId;
        this.currentUserId = currentUserId;
        this.bannedUsers = bannedUsers;
        this.logFile = new File(getExportDir(), id + ".log");
        this.adminUtils = adminUtils;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void dispose() {

        status = terminated;
        if (thread != null){
            thread.interrupt();
            try {
                thread.join(5000);
            } catch (InterruptedException e) {
                //
            }
        }

        if (logFile != null && logFile.exists()) {
            try {
                Files.deleteIfExists(logFile.toPath());
            } catch (IOException e) {
                logger.warn(String.format("Cannot delete file %s: %s", logFile.getAbsolutePath(), e.getMessage()));
            }
        }
        if (manager != null) manager.fireStateChanged(this);

    }

    @Override
    public void start() {

        if (getStatus() == available) return;

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
                        deletionCount++;
                    }

                    status = available;
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    logger.warn("Error serializing csv content: %s", e);
                    status = terminated;
                }
                if (status == available) {
                    if (logFile.exists()) Files.deleteIfExists(logFile.toPath());
                    Files.move(tempFile.toPath(), logFile.toPath());
                }
            } catch (IOException e) {
                errorMessage = e.getMessage();
                status = terminated;
            }
            if (manager != null) manager.fireStateChanged(this);

        }, id);

        thread.start();

    }

    @Override
    public STATUS getStatus() {
        return status;
    }

    @Override
    public File getDataFile() {
        if (status != available) throw new IllegalStateException("Data not available! Check if state is 'available'!");
        return logFile;
    }

    @Override
    public String getErrorMessage() {
        JSONObject statusJson = JSONFactoryUtil.createJSONObject();
        statusJson.put("id", id);
        statusJson.put("request", DeleteBannedUsersRequest.class.getSimpleName());
        statusJson.put("status", status.toString());
        statusJson.put("message", errorMessage);
        return statusJson.toJSONString();
    }

    @Override
    public String getStatusMessage() {
        JSONObject statusJson = JSONFactoryUtil.createJSONObject();
        statusJson.put("id", id);
        statusJson.put("request", DeleteBannedUsersRequest.class.getSimpleName());
        statusJson.put("status", status.toString());
        statusJson.put("progress", deletionCount);
        statusJson.put("message", status.toString());
        if (bannedUsers != null) {
            statusJson.put("total", bannedUsers.size());
        }
        return statusJson.toJSONString();
    }

    @Override
    public void setDataRequestManager(DataRequestManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean isCached() {
        return false;
    }

    private File getExportDir() throws IOException {
        if (tempDir != null) return tempDir;
        String property = System.getProperty("java.io.tmpdir");
        if (property == null){
            throw new IOException("Missing system property: jboss.server.temp.dir");
        }
        tempDir = new File(property, Long.toString(currentUserId));
        if (!tempDir.exists()) Files.createDirectory(tempDir.toPath());
        return tempDir;
    }
}
