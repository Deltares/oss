package nl.deltares.tableview.model;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.oss.download.model.Download;

import java.util.Date;


public class DisplayDownload {

    final private Download download;
    final private String email;
    final private String organization;
    private String city = "";
    private String countryCode = "";
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

        final String organization = download.getOrganization();
        this.organization = organization != null ? organization : "";
        final String filePath = download.getFileName();
        this.fileName = filePath != null ? filePath : "";
        final String directDownloadPath = download.getFileShareUrl();
        this.fileShareUrl = directDownloadPath != null ? directDownloadPath : "";
        final String licenseDownloadPath = download.getLicenseDownloadUrl();
        this.licenseDownloadUrl = licenseDownloadPath != null ? licenseDownloadPath : "";
    }


    public long getId() {
        return download.getId();
    }

    public long getDownloadId() {
        return download.getDownloadId();
    }

    public Date getModifiedDate() {
        return download.getModifiedDate();
    }

    public Date getExpirationDate() {
        return download.getExpiryDate();
    }

    public String getEmail() {
        return email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setCity(String city) {
        if (city == null) return;
        this.city = city;
    }

    public void setCountryCode(String countryCode) {
        if (countryCode == null) return;
        this.countryCode = countryCode;
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

}
