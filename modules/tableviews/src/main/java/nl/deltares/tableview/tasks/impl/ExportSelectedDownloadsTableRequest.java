package nl.deltares.tableview.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;
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
import java.util.*;

import static nl.deltares.portal.utils.KeycloakUtils.ATTRIBUTES.*;
import static nl.deltares.tasks.DataRequest.STATUS.*;
import static nl.deltares.tasks.DataRequest.STATUS.TERMINATED;

public class ExportSelectedDownloadsTableRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(ExportSelectedDownloadsTableRequest.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    private final Group group;
    private final String filterValue;
    private final boolean findByUser;
    protected KeycloakUtils keycloakUtils;

    public ExportSelectedDownloadsTableRequest(String id, String filterValue, long currentUserId, Group siteGroup, KeycloakUtils keycloakUtils) throws IOException {
        super(id, currentUserId);
        this.group = siteGroup;
        if (filterValue != null && filterValue.trim().isEmpty()){
            this.filterValue = null;
        } else {
            this.filterValue = filterValue;
        }
        this.findByUser = Validator.isEmailAddress(filterValue);
        this.keycloakUtils = keycloakUtils;
    }

    @Override
    public STATUS call()  {
        if (getStatus() == AVAILABLE) return status;

        if (filterValue == null){
            status = NODATA;
            return status;
        }
        statusMessage = "starting exporting for filter " + filterValue;
        init();
        status = RUNNING;
        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                exportAllRecords(writer);
                if (status != TERMINATED) {
                    status = AVAILABLE;
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = TERMINATED;
            }
            if (status == AVAILABLE){
                this.dataFile = new File(getExportDir(), id + ".csv");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = TERMINATED;
        } finally {
            if (status == RUNNING || status == PENDING){
                status = TERMINATED;
            }
        }
        fireStateChanged();

        return status;
    }

    private void exportAllRecords(PrintWriter writer) {
        writer.println("downloadId,modifiedDate,expirationDate,fileName,email,name,organization,city,country,shareUrl,licenseUrl");

        int start = 0;
        int end = 100;

        HashMap<Long, Map<String, String>> userAttributesCache = new HashMap<>();
        User filterUser;
        if (findByUser) {
            filterUser = UserLocalServiceUtil.fetchUserByEmailAddress(group.getCompanyId(), filterValue);
            if (filterUser == null){
                totalCount = 0;
            } else {
                totalCount = DownloadLocalServiceUtil.countDownloadsByUserId(group.getGroupId(), filterUser.getUserId());
            }
        } else {
            filterUser = null;
            if (filterValue != null) {
                totalCount = DownloadLocalServiceUtil.countDownloadsByFileName(group.getGroupId(), filterValue);
            } else {
                totalCount = 0;
            }
        }

        for (int i = 0; i < totalCount; ) {
            if (status == TERMINATED) return;
            final List<Download> downloads;
            if (filterUser != null) {
                downloads = DownloadLocalServiceUtil.findDownloadsByUserId(group.getGroupId(), filterUser.getUserId(), start, end);
            } else {
                downloads = DownloadLocalServiceUtil.findDownloadsByFileName(group.getGroupId(), filterValue, start, end);
            }
            if (downloads.isEmpty()) {
                setProcessCount(totalCount);
                return;
            }

            downloads.forEach(download -> {
                if (status == TERMINATED) return;
                incrementProcessCount(1);
                String city = "";
                String countryCode = "";
                String fullName;
                String email;
                User downloadUser = filterUser != null ? filterUser : UserLocalServiceUtil.fetchUser(download.getUserId());
                try {
                    if (download.getGeoLocationId() > 0) {
                        final GeoLocation geoLocation = GeoLocationLocalServiceUtil.getGeoLocation(download.getGeoLocationId());
                        city = geoLocation.getCityName();
                        countryCode = CountryServiceUtil.getCountry(geoLocation.getCountryId()).getA2();
                    } else if (keycloakUtils.isActive() && downloadUser != null) {
                        Map<String, String> attributes = getUserAttributes(downloadUser, userAttributesCache);
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
                if (downloadUser != null) {
                    fullName = downloadUser.getFullName();
                    email = downloadUser.getEmailAddress();
                } else {
                    fullName = String.valueOf(download.getUserId());
                    email = "";
                }
                final String expiryDate;
                if (download.getExpiryDate() != null) {
                    expiryDate = dateFormat.format(download.getExpiryDate());
                } else {
                    expiryDate = "";
                }
                writer.println(String.format("%d,%s,%s,\"%s\",%s,\"%s\",\"%s\",\"%s\",%s,%s,%s",
                        download.getDownloadId(), modifiedDate, expiryDate,
                        download.getFileName(), email, fullName, download.getOrganization(),
                        city, countryCode, download.getFileShareUrl(), download.getLicenseDownloadUrl()));

                if (Thread.interrupted()) {
                    status = TERMINATED;
                    errorMessage = String.format("Thread 'DeletedSelectedDownloadsRequest' with id %s is interrupted!", id);
                }
            });
            i += downloads.size();
            start = end;
            end += 100;
        }

    }

    private Map<String, String> getUserAttributes(User user, HashMap<Long, Map<String, String>> userAttributeCache) {
        long userId = user.getUserId();
        Map<String, String> attributes = userAttributeCache.get(userId);
        if (attributes == null) {
            try {
                attributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
                userAttributeCache.put(userId, attributes);
            } catch (Exception e) {
                logger.warn(String.format("Error getting user attributes for %s: %s", user.getEmailAddress(), e.getMessage()));
                attributes = Collections.emptyMap();
                userAttributeCache.put(userId, attributes);
            }
        }
        return attributes;
    }

}
