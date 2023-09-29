package nl.deltares.useraccount.model;

import nl.deltares.oss.download.model.Download;

import java.util.Date;

public class DisplayDownload {


    final private Download download;

    private String fileName;

    final private String licenseDownloadUrl;

    public DisplayDownload(Download download) {
        this.download = download;

        this.fileName = download.getFileName();

        final String licenseDownloadPath = download.getLicenseDownloadUrl();
        this.licenseDownloadUrl = licenseDownloadPath != null ? licenseDownloadPath : "";
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getLicenseDownloadUrl() {
        return licenseDownloadUrl;
    }

    public String getLicenseDownloadUrlHTML() {
        return String.format("<a href=\"%s\" target=\"_blank\">%s</a>", licenseDownloadUrl, licenseDownloadUrl);
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
