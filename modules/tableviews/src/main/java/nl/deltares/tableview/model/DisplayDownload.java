package nl.deltares.tableview.model;

import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.service.GeoLocationLocalServiceUtil;

import java.util.Date;


public class DisplayDownload {

    final private Download download;
    final private String email;
    final private String organization;
    final private String city;
    final private String countryCode;
    final private String fileName;
    final private String fileShareUrl;
    final private String licenseDownloadUrl;

    public DisplayDownload(Download download) {
        this.download = download;

        final User user = UserLocalServiceUtil.fetchUser(download.getUserId());
        if (user == null) {
            email = "";
        } else {
            email = user.getEmailAddress();
        }
        final long geoLocationId = download.getGeoLocationId();
        final GeoLocation geoLocation = GeoLocationLocalServiceUtil.fetchGeoLocation(geoLocationId);

        final Country country;
        if (geoLocation != null){
             country = CountryServiceUtil.fetchCountry(geoLocation.getCountryId());
        } else {
            country = null;
        }
        final String organization = download.getOrganization();
        this.organization = organization != null ? organization : "";
        this.city =  geoLocation != null ? geoLocation.getCityName() : "";
        this.countryCode = country != null ? country.getA2() : "";
        final String filePath = download.getFileName();
        this.fileName = filePath != null ? filePath : "";
        final String directDownloadPath = download.getFileShareUrl();
        this.fileShareUrl = directDownloadPath != null ? directDownloadPath : "";
        final String licenseDownloadPath = download.getLicenseDownloadUrl();
        this.licenseDownloadUrl = licenseDownloadPath != null ? licenseDownloadPath : "";
    }

    public long getId(){
        return download.getId();
    }

    public long getDownloadId() {
        return download.getDownloadId();
    }

    public Date getModifiedDate() {
        return download.getModifiedDate();
    }

    public Date getExpirationDate() {return download.getExpiryDate(); }

    public String getEmail() {
        return email;
    }

    public String getOrganization() {
        return  organization;
    }

    public String getCity() {
        return city;
    }

    public String getCountryCode() {
        return countryCode;
    }


    public String getFileName() {
        return fileName;
    }

    public String getFileShareUrl() {
        return fileShareUrl;
    }

    public String getLicenseDownloadUrl() {
        return licenseDownloadUrl;
    }

    public int compareDesc(DisplayDownload o){
        final int compare = o.getModifiedDate().compareTo(getModifiedDate());
        if (compare == 0) {
            return Long.compare(o.getDownloadId(), getDownloadId());
        } else {
            return compare;
        }

    }
}
