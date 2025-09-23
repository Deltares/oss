package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.service.GeoLocationLocalServiceUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DownloadActivityMapRequest extends AbstractDataRequest {

    public static final String QUERY_PARAMETER_STARTDATE = "startdate";
    public static final String QUERY_PARAMETER_ENDDATE = "enddate";
    public static final String QUERY_PARAMETER_MAXRECORDS = "maxrecords";


    private static final Log LOG = LogFactoryUtil.getLog(DownloadActivityMapRequest.class);
    private final DsdParserUtils dsdParserUtils;
    private final long siteGroupId;
    private final Map<String, Object> queryProperties;

    public DownloadActivityMapRequest(String id, long currentUserId, long groupId, DsdParserUtils dsdParserUtils, Map<String, Object> queryProperties) throws IOException {
        super(id, currentUserId);

        this.dsdParserUtils = dsdParserUtils;
        this.siteGroupId = groupId;
        this.queryProperties = queryProperties;
    }

    @Override
    public STATUS call() {
        if (getStatus() == available) return status;
        status = running;
        statusMessage = "Start DownloadActivityMapRequest";
        init();
        if (dataFile.exists()) {
            try {
                Files.deleteIfExists(dataFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFile))) {
            DynamicQuery query = DownloadLocalServiceUtil.dynamicQuery();
            Object startDate = queryProperties.get(QUERY_PARAMETER_STARTDATE);
            Date start;
            if (startDate instanceof Date) {
                start = (Date) startDate;
            } else {
                start = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(100));
            }
            Object endDate = queryProperties.get(QUERY_PARAMETER_ENDDATE);
            Date end;
            if (endDate instanceof Date) {
                end = (Date) endDate;
            } else {
                end = new Date();
            }

            Object maxRecords = queryProperties.get(QUERY_PARAMETER_MAXRECORDS);
            if (maxRecords instanceof Integer) {
                totalCount = (int) maxRecords;
            } else {
                totalCount = 100;
            }
            query.add(RestrictionsFactoryUtil.between("createDate", start, end));
            query.addOrder(OrderFactoryUtil.desc("createDate"));


            int iterations = (totalCount / 100);
            writer.append("[");
            boolean first = true;
            for (int i = 0; i < iterations; i++) {
                int startIndex = i * 100;
                int endIndex = startIndex + 100;
                List<Download> downloads = DownloadLocalServiceUtil.dynamicQuery(query, startIndex, endIndex);
                HashMap<Long, JSONObject> downloadLocationsMap = new HashMap<>();
                for (Download download : downloads) {
                    long geoLocationId = download.getGeoLocationId();
                    JSONObject downloadLocation = downloadLocationsMap.get(geoLocationId);
                    if (downloadLocation == null) {
                        GeoLocation geoLocation = GeoLocationLocalServiceUtil.fetchGeoLocation(geoLocationId);
                        if (geoLocation == null) {
                            geoLocation = getDummyGeoLocation(geoLocationId);
                        }
                        downloadLocation = JSONFactoryUtil.createJSONObject();
                        downloadLocation.put("city", geoLocation.getCityName());
                        final JSONObject position = JSONFactoryUtil.createJSONObject();
                        position.put("lat", geoLocation.getLatitude());
                        position.put("lng", geoLocation.getLongitude());
                        downloadLocation.put("position", position);
                        final JSONArray products = JSONFactoryUtil.createJSONArray();
                        downloadLocation.put("products", products);
                        downloadLocation.put("totalDownloadCount", 0);
                        downloadLocationsMap.put(geoLocationId, downloadLocation);
                    }
                    JSONArray products = downloadLocation.getJSONArray("products");
                    downloadLocation.put("products", addToProductsArray(download, products));
                    downloadLocation.put("totalDownloadCount", downloadLocation.getInt("totalDownloadCount") + 1);

                    if (first){
                        first = false;
                    } else {
                        writer.append(",");
                    }
                    writer.append(downloadLocation.toJSONString());

                    incrementProcessCount(1);

                    if (Thread.interrupted()) {
                        status = terminated;
                        errorMessage = String.format("Thread 'DownloadActivityMapRequest' with id %s is interrupted!", id);
                        break;
                    }
                }
            }
            writer.append("]");
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

    private GeoLocation getDummyGeoLocation(long geoLocationId) {

        GeoLocation geoLocation = GeoLocationLocalServiceUtil.createGeoLocation(geoLocationId);
        geoLocation.setCityName("Unknown");
        geoLocation.setLatitude(0);
        geoLocation.setLongitude(0);
        return geoLocation;
    }

    private JSONArray addToProductsArray(Download download, JSONArray products) {

        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
        JSONObject existing = null;
        for (Object product : products) {
            JSONObject jsonProduct = (JSONObject) product;
            if (jsonProduct.getInt("downloadId") == download.getDownloadId()) {
                existing = jsonProduct;
            }
            jsonArray.put(jsonProduct);
        }
        if (existing == null){
            JSONObject jsonProduct = JSONFactoryUtil.createJSONObject();
            jsonProduct.put("downloadId", download.getDownloadId());
            jsonProduct.put("downloadCount", 1);
            nl.deltares.portal.model.impl.Download dsdDownload = getDownloadArticle(siteGroupId, download.getDownloadId());
            if (dsdDownload != null) {
                jsonProduct.put("software", dsdDownload.getFileTopic());
                jsonProduct.put("downloadName", dsdDownload.getFileName());
            }
            jsonArray.put(jsonProduct);
        } else {
            existing.put("downloadCount", existing.getInt("downloadCount") + 1);
        }
        return jsonArray;
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
