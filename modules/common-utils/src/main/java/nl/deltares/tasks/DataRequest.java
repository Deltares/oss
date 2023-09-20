package nl.deltares.tasks;

import java.io.File;
import java.util.concurrent.Callable;

public interface DataRequest extends Callable<DataRequest.STATUS> {

    enum STATUS {pending, running, terminated, expired, available, nodata}

    String getId();

    void dispose();

    STATUS getStatus();

    File getDataFile();

    String getErrorMessage();

    String getStatusMessage();

    void setDataRequestManager(DataRequestManager manager);

    long getTimeoutMillis();

    long getTimeoutMillis();

}
