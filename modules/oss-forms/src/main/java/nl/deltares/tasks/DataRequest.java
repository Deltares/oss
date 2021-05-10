package nl.deltares.tasks;

import java.io.File;

public interface DataRequest {

    enum STATUS {pending, running, terminated, expired, available, nodata}

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
