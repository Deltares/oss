package nl.deltares.tableview.model;

import com.liferay.portal.kernel.util.StringComparator;


public class DisplayDownloadCount implements Comparable<DisplayDownloadCount> {

    final static StringComparator comparator = new StringComparator(true, true);
    final private int count;
    final private String fileName;
    final private String fileTopic;

    public DisplayDownloadCount(String fileName, String fileTopic, int count) {
        this.count = count;
        this.fileName = fileName;
        this.fileTopic = fileTopic;
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

    @Override
    public int compareTo(DisplayDownloadCount o) {
        final int compare = comparator.compare(fileTopic, o.fileTopic);
        if (compare == 0){
            return comparator.compare(fileName, o.fileName);
        } else {
            return compare;
        }
    }
}
