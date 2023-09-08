package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.service.GeoLocationLocalServiceUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DownloadActivityMapRequest extends AbstractDataRequest {


    private static final Log LOG = LogFactoryUtil.getLog(DownloadActivityMapRequest.class);
    private final DsdParserUtils dsdParserUtils;
    private final long siteGroupId;

    public DownloadActivityMapRequest(String id, long currentUserId, long groupId, DsdParserUtils dsdParserUtils) throws IOException {
        super(id, currentUserId);

        this.dsdParserUtils = dsdParserUtils;
        this.siteGroupId = groupId;
    }

    @Override
    public STATUS call() {
        if (getStatus() == available) return status;
        status = running;
        statusMessage = "Start DownloadActivityMapRequest";
        init();

        try {
            JSONArray downloadLocations = JSONFactoryUtil.createJSONArray();
            final List<GeoLocation> geoLocations = GeoLocationLocalServiceUtil.getGeoLocations(0, GeoLocationLocalServiceUtil.getGeoLocationsCount());

            totalCount = geoLocations.size();

            for (GeoLocation geoLocation : geoLocations) {

                final JSONObject downloadLocation = JSONFactoryUtil.createJSONObject();
                downloadLocation.put("city", geoLocation.getCityName());
                final JSONObject position = JSONFactoryUtil.createJSONObject();
                position.put("lat", geoLocation.getLatitude());
                position.put("lng", geoLocation.getLongitude());
                downloadLocation.put("position", position);

                final JSONArray products = JSONFactoryUtil.createJSONArray();
                final List<Long> downloadsByGeoLocations = DownloadLocalServiceUtil.findDownloadIdsByGeoLocation(geoLocation.getLocationId());
                Map<Long, Integer> distinctCounts = convertToDistinctCounts(downloadsByGeoLocations);
                final int[] totalDownloadCount = {0};
                distinctCounts.forEach((downloadId, count) -> {

                    JSONObject jsonProduct = JSONFactoryUtil.createJSONObject();
                    jsonProduct.put("downloadId", downloadId);
                    jsonProduct.put("downloadCount", count);
                    totalDownloadCount[0] += count;
                    nl.deltares.portal.model.impl.Download dsdDownload = getDownloadArticle(siteGroupId, downloadId);
                    if (dsdDownload != null) {
                        jsonProduct.put("software", dsdDownload.getFileTopic());
                        jsonProduct.put("downloadName", dsdDownload.getFileName());
                    }
                    products.put(jsonProduct);

                });
                if (products.length() > 0) {
                    downloadLocation.put("products", products);
                    downloadLocation.put("totalDownloadCount", totalDownloadCount[0]);
                    downloadLocations.put(downloadLocation);
                }

                incrementProcessCount(1);

                if (Thread.interrupted()) {
                    status = terminated;
                    errorMessage = String.format("Thread 'DownloadActivityMapRequest' with id %s is interrupted!", id);
                    break;
                }
            }

            if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
            writeResultsToFile(downloadLocations, dataFile);

            status = available;

            statusMessage = String.format("%d download locations have been processed.", getProcessedCount());
            LOG.info(statusMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
        } finally {
            fireStateChanged();
        }
        return status;
    }

    private void writeResultsToFile(JSONArray downloadLocations, File tempFile) throws IOException, JSONException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
            downloadLocations.write(writer);
            writer.flush();
        }
    }

    private Map<Long, Integer> convertToDistinctCounts(List<Long> downloadsByGeoLocations) {
        final HashMap<Long, Integer> distinctCounts = new HashMap<>();

        for (Long downloadsByGeoLocation : downloadsByGeoLocations) {
            Integer orDefault = distinctCounts.getOrDefault(downloadsByGeoLocation, 0);
            distinctCounts.put(downloadsByGeoLocation, ++orDefault);
        }
        return distinctCounts;
    }

    private nl.deltares.portal.model.impl.Download getDownloadArticle(long siteGroupId, long downloadId) {
        try {
            return (nl.deltares.portal.model.impl.Download) dsdParserUtils.toDsdArticle(siteGroupId, String.valueOf(downloadId));
        } catch (PortalException e) {
            LOG.warn(String.format("Error parsing article %d : %s", downloadId, e.getMessage()));
            return null;
        }
    }
}
