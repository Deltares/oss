package nl.deltares.tasks;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public abstract class AbstractDataRequest implements DataRequest {

    private static final Log logger = LogFactoryUtil.getLog(AbstractDataRequest.class);

    protected File dataFile;
    protected String errorMessage;
    protected String statusMessage = "";

    protected final String id;
    protected final long currentUserId;
    protected STATUS status = pending;
    protected int processedCount = 0;
    protected int totalCount = 0;
    protected File tempDir;
    private final PermissionChecker permissionChecker;


    protected DataRequestManager manager;

    public AbstractDataRequest(String id, long currentUserId) throws IOException {
        this.currentUserId = currentUserId;
        this.id = id;
        this.dataFile = new File(getExportDir(), id + ".data");
        permissionChecker = PermissionThreadLocal.getPermissionChecker();
    }

    /**
     * Run this method at the start of the {{@link #call()}} method.
     */
    protected void init(){
        //Set the permissionChecker in the background thread
        PermissionThreadLocal.setPermissionChecker(permissionChecker);
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void dispose() {

        status = terminated;
        if (dataFile != null && dataFile.exists()) {
            try {
                Files.deleteIfExists(dataFile.toPath());
            } catch (IOException e) {
                logger.warn(String.format("Cannot delete file %s: %s", dataFile.getAbsolutePath(), e.getMessage()));
            }
        }
        if (manager != null) manager.fireStateChanged(this);

    }

    public int getProcessedCount() {
        return processedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public STATUS getStatus() {
        return status;
    }

    @Override
    public File getDataFile() {
        if (status != available) throw new IllegalStateException("Data not available! Check if state is 'available'!");
        return dataFile;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getStatusMessage() {
        JSONObject statusJson = JSONFactoryUtil.createJSONObject();
        statusJson.put("id", id);
        statusJson.put("request", this.getClass().getSimpleName());
        statusJson.put("status", status.toString());
        statusJson.put("progress", processedCount);
        statusJson.put("total", totalCount);
        statusJson.put("message", statusMessage);
        return statusJson.toJSONString();
    }

    @Override
    public void setDataRequestManager(DataRequestManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean isCached(){
        return false;
    };

    protected File getExportDir() throws IOException {
        if (tempDir != null) return tempDir;
        String property = System.getProperty("java.io.tmpdir");
        if (property == null){
            throw new IOException("Missing system property: jboss.server.temp.dir");
        }
        tempDir = new File(property, Long.toString(currentUserId));
        if (!tempDir.exists()) Files.createDirectory(tempDir.toPath());
        return tempDir;
    }

    protected void fireStateChanged(){
        if (manager != null) manager.fireStateChanged(this);
    }
}
