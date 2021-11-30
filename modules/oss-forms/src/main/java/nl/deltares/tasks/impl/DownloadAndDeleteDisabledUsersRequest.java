package nl.deltares.tasks.impl;

import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.AbstractDataRequest;
import nl.deltares.tasks.DataRequestManager;

import java.io.File;
import java.io.IOException;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DownloadAndDeleteDisabledUsersRequest extends AbstractDataRequest {

    private final AdminUtils adminUtils;
    private final long disableTimeAfter;
    private final long companyId;

    private AbstractDataRequest currentRequest;

    public DownloadAndDeleteDisabledUsersRequest(String id, long companyId, long disableTimeAfter, long currentUserId, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);

        this.companyId = companyId;
        this.disableTimeAfter = disableTimeAfter;
        this.adminUtils = adminUtils;
    }

    @Override
    public STATUS call() {
        status = running;
        try {
            currentRequest = new DownloadDisabledUsersRequest(id, disableTimeAfter, currentUserId, adminUtils);
            final STATUS call = currentRequest.call();
            fireStateChanged();
            if (call == available) {
                currentRequest = new DeleteDisabledUsersRequest(id, currentUserId, companyId,
                        currentRequest.getDataFile().getAbsolutePath(), adminUtils);
                currentRequest.call();
                fireStateChanged();
            }
            status = available;
        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
            currentRequest = this;
        }
        return status;

    }

    @Override
    public int getProcessedCount() {
        return currentRequest == null ? 0 : currentRequest.getProcessedCount();
    }

    @Override
    public int getTotalCount() {
        return currentRequest == null ? 0: currentRequest.getTotalCount();
    }

    @Override
    public STATUS getStatus() {
        return currentRequest == null ? status : currentRequest.getStatus();
    }

    @Override
    public File getDataFile() {
        return currentRequest == null ? super.dataFile : currentRequest.getDataFile();
    }

    @Override
    public String getErrorMessage() {
        return currentRequest == null ? super.errorMessage : currentRequest.getErrorMessage();
    }

    @Override
    public String getStatusMessage() {
        return currentRequest == null ? super.statusMessage : currentRequest.getStatusMessage();
    }

    @Override
    public void setDataRequestManager(DataRequestManager manager) {
        super.setDataRequestManager(manager);
        this.manager = manager;
    }
}
