package nl.deltares.tasks.impl;

import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.AbstractDataRequest;
import nl.deltares.tasks.DataRequestManager;

import java.io.File;
import java.io.IOException;

import static nl.deltares.tasks.DataRequest.STATUS.available;
import static nl.deltares.tasks.DataRequest.STATUS.terminated;

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

        try {
            currentRequest = new DownloadDisabledUsersRequest(id, disableTimeAfter, currentUserId, adminUtils);
            currentRequest.setDataRequestManager(super.manager);
            final STATUS call = currentRequest.call();
            fireStateChanged();
            if (call == available) {
                currentRequest = new DeleteDisabledUsersRequest(id, currentUserId, companyId,
                        currentRequest.getDataFile().getAbsolutePath(), adminUtils);
                currentRequest.setDataRequestManager(super.manager);
                currentRequest.call();
                fireStateChanged();
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
            currentRequest = this;
        }
        return status;

    }

    @Override
    public int getProcessedCount() {
        return currentRequest.getProcessedCount();
    }

    @Override
    public int getTotalCount() {
        return currentRequest.getTotalCount();
    }

    @Override
    public STATUS getStatus() {
        return currentRequest.getStatus();
    }

    @Override
    public File getDataFile() {
        return currentRequest.getDataFile();
    }

    @Override
    public String getErrorMessage() {
        return currentRequest.getErrorMessage();
    }

    @Override
    public String getStatusMessage() {
        return currentRequest.getStatusMessage();
    }

    @Override
    public void setDataRequestManager(DataRequestManager manager) {
        super.setDataRequestManager(manager);
        this.manager = manager;
    }
}
