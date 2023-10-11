package nl.deltares.tableview.comparator;

import com.liferay.portal.kernel.util.NumericalStringComparator;
import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.tableview.model.DisplayDownloadCount;

public class DownloadCountComparator extends OrderByComparator<DisplayDownloadCount> {

    private final boolean _ascending;
    private final String _orderByField;

    final NumericalStringComparator comparator = new NumericalStringComparator();
    public DownloadCountComparator(String field, boolean ascending) {
        this._ascending = ascending;
        this._orderByField = field;
    }

    @Override
    public int compare(DisplayDownloadCount o1, DisplayDownloadCount o2) {

        String fieldValue1;
        String fieldValue2;
        switch (_orderByField){
            case "fileTopic":
                fieldValue1 = o1.getFileTopic();
                fieldValue2 = o2.getFileTopic();
                break;
            case "fileName":
                fieldValue1 = o1.getFileName();
                fieldValue2 = o2.getFileName();
                break;
            case "count":

                fieldValue1 = String.valueOf(o1.getCount());
                fieldValue2 = String.valueOf(o2.getCount());

                break;
            default:
                fieldValue1 = "";
                fieldValue2 = "";
        }

        if (isAscending()){
            return comparator.compare(fieldValue1, fieldValue2);
        } else {
            return comparator.compare(fieldValue2, fieldValue1);
        }

    }

    @Override
    public boolean isAscending() {
        return _ascending;
    }

    @Override
    public String getOrderBy() {
        return _orderByField;
    }
}
