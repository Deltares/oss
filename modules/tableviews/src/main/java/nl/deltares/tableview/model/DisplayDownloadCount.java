package nl.deltares.tableview.model;

public class DisplayDownloadCount {

    final private long downloadId;
    final private int count;
    final private String fileName;
    final private String siteName;

    public DisplayDownloadCount(long downloadId, int count, String fileName, String siteName) {
        this.downloadId = downloadId;
        this.count = count;
        this.fileName = fileName;
        this.siteName = siteName;
    }

    public long getDownloadId() {
        return downloadId;
    }

    public int getCount() {
        return count;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSiteName() {
        return siteName;
    }
}
