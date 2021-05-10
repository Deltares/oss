package nl.deltares.portal.tasks;

import java.io.File;

public interface DataRequest {

    enum STATUS {pending, running, terminated, expired, available}

    String getId();

    void dispose();

    void start();

    STATUS getStatus();

    File getDataFile();

    String getErrorMessage();

    String getStatusMessage();

    void setDataRequestManager(DataRequestManager manager);

    boolean isCached();

}
