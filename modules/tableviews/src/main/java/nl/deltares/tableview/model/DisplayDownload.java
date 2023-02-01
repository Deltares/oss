package nl.deltares.tableview.model;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.oss.download.model.Download;

import java.util.Date;


public class DisplayDownload implements Comparable<DisplayDownload> {

    final private Download download;
    final private String email;
    final private String organization;
    final private String city;
    final private String countryCode;
    final private String filePath;
    final private String directDownloadUrl;

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
        final String city = download.getCity();
        this.city =  city != null ? city : "";
        final String countryCode = download.getCountryCode();
        this.countryCode = countryCode != null ? countryCode : "";
        final String filePath = download.getFilePath();
        this.filePath = filePath != null ? filePath : "";
        final String directDownloadPath = download.getDirectDownloadUrl();
        this.directDownloadUrl = directDownloadPath != null ? directDownloadPath : "";
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

    public int getShareId() {
        return download.getShareId();
    }

    public String getShareIdStatus() {
        final int shareId = download.getShareId();
        switch (shareId){
            case -9 : return "processing";
            case -1 : return "pending payment";
            default: return String.valueOf(shareId);
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDirectDownloadUrl() {
        return directDownloadUrl;
    }

    @Override
    public int compareTo(DisplayDownload o) {

        final int compare = getModifiedDate().compareTo(o.getModifiedDate());
        if (compare == 0) {
            return Long.compare(getDownloadId(), o.getDownloadId());
        } else {
            return compare;
        }
    }
}
