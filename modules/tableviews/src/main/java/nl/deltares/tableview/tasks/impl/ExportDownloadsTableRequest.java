package nl.deltares.tableview.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.service.GeoLocationLocalServiceUtil;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static nl.deltares.portal.utils.KeycloakUtils.ATTRIBUTES.org_city;
import static nl.deltares.portal.utils.KeycloakUtils.ATTRIBUTES.org_country;
import static nl.deltares.tasks.DataRequest.STATUS.*;
import static nl.deltares.tasks.DataRequest.STATUS.terminated;

public class ExportDownloadsTableRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(ExportDownloadsTableRequest.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private final Group group;
    private final String filterValue;
    private final String filterSelection;
    private final boolean findByUser;
    private final boolean findByArticleId;
    protected KeycloakUtils keycloakUtils;

    public ExportDownloadsTableRequest(String id, String filterValue, String filterSelection, long currentUserId, Group siteGroup, KeycloakUtils keycloakUtils) throws IOException {
        super(id, currentUserId);
        this.group = siteGroup;
        this.filterValue = filterValue;
        this.filterSelection = filterSelection;

        this.findByUser = "email".equals(filterSelection);
        this.findByArticleId = "articleid".equals(filterSelection);

        this.keycloakUtils = keycloakUtils;
    }

    @Override
    public STATUS call()  {
        if (getStatus() == available) return status;
        String filter = filterSelection == null ? "none": filterSelection + " = " + filterValue;
        statusMessage = "starting exporting for filter " + filter;
        init();
        status = running;
        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                exportAllRecords(writer);
                if (status != terminated) {
                    status = available;
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = terminated;
            }
            if (status == available){
                this.dataFile = new File(getExportDir(), id + ".csv");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
        } finally {
            if (status == running || status == pending){
                status = terminated;
            }
        }
        fireStateChanged();

        return status;
    }

    private void exportAllRecords(PrintWriter writer) {
        writer.println("downloadId,modifiedDate,expirationDate,fileName,email,organization,city,country,shareUrl,licenseUrl");

        int start = 0;
        int end = 100;

        final User filterUser;
        final Long filterArticleId;
        if (findByUser) {
            filterArticleId = null;
            filterUser = UserLocalServiceUtil.fetchUserByEmailAddress(group.getCompanyId(), filterValue);
            if (filterUser != null) totalCount = DownloadLocalServiceUtil.countDownloadsByUserId(group.getGroupId(), filterUser.getUserId());
        } else if (findByArticleId) {
            filterUser = null;
            filterArticleId = Long.parseLong(filterValue);
            totalCount = DownloadLocalServiceUtil.countDownloadsByArticleId(group.getGroupId(), filterArticleId);
        } else
        {
            filterArticleId = null;
            filterUser = null;
            totalCount = DownloadLocalServiceUtil.countDownloads(group.getGroupId());
        }

        for (int i = 0; i < totalCount; ) {
            if (status == terminated) return;
            final List<Download> downloads;
            if (filterUser != null) {
                downloads = DownloadLocalServiceUtil.findDownloadsByUserId(group.getGroupId(), filterUser.getUserId(), start, end);
            } else if (filterArticleId != null ) {
                downloads = DownloadLocalServiceUtil.findDownloadsByArticleId(group.getGroupId(), filterArticleId, start, end);
            } else
            {
                downloads = DownloadLocalServiceUtil.findDownloads(group.getGroupId(), start, end);
            }
            if (downloads.isEmpty()) {
                setProcessCount(totalCount);
                return;
            }

            HashMap<Long, Map<String, String>> userAttributesCache = new HashMap<>();
            downloads.forEach(download -> {
                if (status == terminated) return;
                incrementProcessCount(1);
                if (group.getGroupId() != download.getGroupId()) return;
                String email = "";
                if (filterUser != null){
                    email = filterValue;
                } else {
                    final User user = UserLocalServiceUtil.fetchUser(download.getUserId());
                    if (user != null) {
                        email = user.getEmailAddress();
                    }
                }
                String city;
                String countryCode;
                try {
                    if (download.getGeoLocationId() > 0) {
                        final GeoLocation geoLocation = GeoLocationLocalServiceUtil.getGeoLocation(download.getGeoLocationId());
                        city = geoLocation.getCityName();
                        countryCode = CountryServiceUtil.getCountry(geoLocation.getCountryId()).getA2();
                    } else {
                        Map<String, String> attributes = userAttributesCache.get(download.getUserId());
                        if (attributes == null){
                            attributes = keycloakUtils.getUserAttributes(email);
                            userAttributesCache.put(download.getUserId(), attributes);
                        }
                        city = attributes.get(org_city.name());
                        countryCode = attributes.get(org_country.name());
                    }
                } catch (Exception e) {
                    city  = "";
                    countryCode = "";
                }
                final String modifiedDate;
                if (download.getModifiedDate() != null) {
                    modifiedDate = dateFormat.format(download.getModifiedDate());
                } else {
                    modifiedDate = "";
                }
                final String expiryDate;
                if (download.getExpiryDate() != null) {
                    expiryDate = dateFormat.format(download.getExpiryDate());
                } else {
                    expiryDate = "";
                }
                writer.println(String.format("%d,%s,%s,%s,%s,\"%s\",%s,%s,%s,%s",
                        download.getDownloadId(), modifiedDate, expiryDate,
                        download.getFileName(), email, download.getOrganization(),
                        city, countryCode, download.getFileShareUrl(), download.getLicenseDownloadUrl()));

                if (Thread.interrupted()) {
                    status = terminated;
                    errorMessage = String.format("Thread 'DeletedSelectedDownloadsRequest' with id %s is interrupted!", id);
                }
            });
            i += downloads.size();
            start = end;
            end += 100;
        }

    }

}
