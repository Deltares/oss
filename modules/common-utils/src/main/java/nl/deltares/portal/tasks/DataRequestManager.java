package nl.deltares.portal.tasks;

import java.util.*;

public class DataRequestManager {

    private final Map<String, DataRequest> dataRequests = Collections.synchronizedMap(new HashMap<>());
    private static DataRequestManager requestManager;
    private static final Queue<String> pendingRequestsIds = new LinkedList<>();

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
        if (dataRequest.getStatus() != DataRequest.STATUS.pending)
            throw new IllegalStateException(String.format("Data request %s has invalid state %s! State must be 'pending' to add to queue.", dataRequest.getId(), dataRequest.getStatus()));

        DataRequest existingRequest = dataRequests.get(dataRequest.getId());
        if (existingRequest != null && existingRequest.getStatus() == DataRequest.STATUS.running){
            throw new IllegalStateException(String.format("Data request %s is still running! Either terminate this request or wait for it to complete.", dataRequest.getId()));
        }
        dataRequests.put(dataRequest.getId(), dataRequest);
        pendingRequestsIds.add(dataRequest.getId());
        if (existingRequest != null) existingRequest.dispose();
        if (!hasRunningRequests()) {
            startNextRequest();
        }
    }

    private boolean hasRunningRequests() {
        for (DataRequest request : dataRequests.values()) {
            if (request.getStatus() == DataRequest.STATUS.running) return true;
        }
        return false;
    }

    public void fireStateChanged(DataRequest changedRequest) {
        DataRequest.STATUS status = changedRequest.getStatus();
        if (status == DataRequest.STATUS.available
                || status == DataRequest.STATUS.terminated ){
            changedRequest.setDataRequestManager(null); //stop listening
            startNextRequest();
        }
    }

    private void startNextRequest() {

        while (pendingRequestsIds.size() > 0) {
            String nextId = pendingRequestsIds.remove();
            DataRequest request = dataRequests.get(nextId);
            if (request == null) continue;
            request.setDataRequestManager(this);
            request.start();
            break;
        }
    }

}
