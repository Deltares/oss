package nl.deltares.tasks;

import jakarta.portlet.ResourceResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

public class DataRequestManager {

    private final Map<String, DataRequest> dataRequests = Collections.synchronizedMap(new HashMap<>());
    private static DataRequestManager requestManager;
    private static final Queue<String> pendingRequestsIds = new LinkedList<>();
    private Future<DataRequest.STATUS> runningRequest;

    public static DataRequestManager getInstance(){
        if (requestManager == null){
            requestManager = new DataRequestManager();
        }
        return requestManager;
    }

    public DataRequest getDataRequest(String id){
        return dataRequests.get(id);
    }

    public void removeDataRequest(DataRequest dataRequest)  {
        if (dataRequest == null) throw new IllegalArgumentException("dataRequest == null");
        dataRequest.dispose();
        dataRequests.remove(dataRequest.getId());
    }

    public void addToQueue(DataRequest dataRequest){
        if (dataRequest == null) throw new IllegalArgumentException("dataRequest == null");
        if (dataRequest.getStatus() != DataRequest.STATUS.PENDING)
            throw new IllegalStateException(String.format("Data request %s has invalid state %s! State must be 'pending' to add to queue.", dataRequest.getId(), dataRequest.getStatus()));

        DataRequest existingRequest = dataRequests.get(dataRequest.getId());
        if (existingRequest != null && existingRequest.getStatus() == DataRequest.STATUS.RUNNING){
            throw new IllegalStateException(String.format("Data request %s is still running! Either terminate this request or wait for it to complete.", dataRequest.getId()));
        }
        dataRequests.put(dataRequest.getId(), dataRequest);
        pendingRequestsIds.add(dataRequest.getId());
        if (existingRequest != null) existingRequest.dispose();
        if (hasNoRunningRequests()) {
            startNextRequest();
        }
    }

    private boolean hasNoRunningRequests() {
        return runningRequest == null || runningRequest.isDone();
    }

    public void fireStateChanged(DataRequest changedRequest) {
        DataRequest.STATUS status = changedRequest.getStatus();
        if (status == DataRequest.STATUS.AVAILABLE
                || status == DataRequest.STATUS.TERMINATED
                || status == DataRequest.STATUS.DISPOSE){
            changedRequest.setDataRequestManager(null); //stop listening
            startNextRequest();
        }

        if (status == DataRequest.STATUS.DISPOSE){
            dataRequests.remove(changedRequest.getId());
        }
    }

    private void startNextRequest() {

        while (!pendingRequestsIds.isEmpty()) {
            String nextId = pendingRequestsIds.remove();
            final DataRequest request = dataRequests.get(nextId);
            if (request == null) continue;
            request.setDataRequestManager(this);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            runningRequest = executor.submit(request);

            ScheduledExecutorService canceller = Executors.newSingleThreadScheduledExecutor();
            canceller.schedule((Callable<Void>) () -> {
                removeDataRequest(request);
                runningRequest.cancel(true);
                return null;
            }, request.getTimeoutMillis(), TimeUnit.MILLISECONDS);

            break;
        }
    }

    public void updateStatus(String dataRequestId, ResourceResponse resourceResponse) throws IOException {
        resourceResponse.setContentType("application/json");

        DataRequest dataRequest = getDataRequest(dataRequestId);
        if (dataRequest == null){
            dataRequestNotExistsError(dataRequestId, resourceResponse);
        } else {
            String statusMessage;
            if (dataRequest.getStatus() == DataRequest.STATUS.NODATA) {
                resourceResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
                statusMessage = "No data found!";
                removeDataRequest(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.TERMINATED){
                resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                statusMessage = dataRequest.getErrorMessage();
                removeDataRequest(dataRequest);
            } else {
                resourceResponse.setStatus(HttpServletResponse.SC_OK);
                statusMessage = dataRequest.getStatusMessage();
            }
            resourceResponse.setContentLength(statusMessage.length());
            PrintWriter writer = resourceResponse.getWriter();
            writer.print(statusMessage);
        }

    }
    public void downloadDataFile(String dataRequestId, ResourceResponse resourceResponse) throws IOException {
        DataRequest dataRequest = getDataRequest(dataRequestId);
        if (dataRequest == null){
            dataRequestNotExistsError(dataRequestId, resourceResponse);
            return;
        }
        DataRequest.STATUS status = dataRequest.getStatus();
        if (status == DataRequest.STATUS.AVAILABLE && dataRequest.getDataFile().exists()){

            resourceResponse.setStatus(HttpServletResponse.SC_OK);
            resourceResponse.setContentType("text/plain");

            try (OutputStream out = resourceResponse.getPortletOutputStream()) {
                Path path = dataRequest.getDataFile().toPath();
                resourceResponse.setContentLength(Long.valueOf(Files.copy(path, out)).intValue());
                out.flush();
            } catch (IOException e) {
                // handle exception
            } finally {
                removeDataRequest(dataRequest);
            }

        } else {
            resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resourceResponse.setContentType("text/plain");
            PrintWriter writer = resourceResponse.getWriter();
            if (status != DataRequest.STATUS.AVAILABLE) {
                writer.printf("Requested log file is not available yet: %s", dataRequestId);
            } else if (!dataRequest.getDataFile().exists()){
                writer.printf("Requested log file does not exist: %s", dataRequest.getDataFile().getAbsolutePath());
                removeDataRequest(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.TERMINATED){
                writer.printf("Requested log file task has been terminated");
                removeDataRequest(dataRequest);
            }
        }
    }

    public void writeError(String msg, ResourceResponse resourceResponse) throws IOException {

        resourceResponse.setContentType("text/plain");
        resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resourceResponse.setContentLength(msg.length());
        PrintWriter writer = resourceResponse.getWriter();
        writer.print(msg);
    }

    public void writeInfo(String msg, ResourceResponse resourceResponse) throws IOException {

        resourceResponse.setContentType("text/plain");
        resourceResponse.setStatus(HttpServletResponse.SC_OK);
        resourceResponse.setContentLength(msg.length());
        PrintWriter writer = resourceResponse.getWriter();
        writer.print(msg);
    }

    public void dataRequestNotExistsError(String dataRequestId, ResourceResponse resourceResponse) throws IOException {
        writeError(String.format("Data request for id does not exist: %s", dataRequestId), resourceResponse);
    }

}
