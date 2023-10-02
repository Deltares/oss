package nl.deltares.tableview.model;

public class DisplayDownloadCount {

    final private int count;
    final private String fileName;
    final private String fileTopic;
    private final long id;

    public DisplayDownloadCount(long id, String fileName, String fileTopic, int count) {
        this.id = id;
        this.count = count;
        this.fileName = fileName;
        this.fileTopic = fileTopic;
    }

    public long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileTopic() {
        return fileTopic;
    }

}
