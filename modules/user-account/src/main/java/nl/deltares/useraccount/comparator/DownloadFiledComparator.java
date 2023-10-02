package nl.deltares.useraccount.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.useraccount.model.DisplayDownload;

public class DownloadFiledComparator extends OrderByComparator<DisplayDownload> {

    private final boolean _ascending;
    private final String _orderByField;

    public DownloadFiledComparator(String field, boolean ascending) {
        this._ascending = ascending;
        this._orderByField = field;
    }

    @Override
    public int compare(DisplayDownload o1, DisplayDownload o2) {

        String fieldValue1;
        String fieldValue2;
        switch (_orderByField){
            case "fileName":
                fieldValue1 = o1.getFileName();
                fieldValue2 = o2.getFileName();
                break;
            case "expirationDate":
                fieldValue1 = String.valueOf(o1.getExpirationDate().getTime());
                fieldValue2 = String.valueOf(o2.getExpirationDate().getTime());
                break;
            default:
                fieldValue1 = String.valueOf(o1.getModifiedDate().getTime());
                fieldValue2 = String.valueOf(o2.getModifiedDate().getTime());
        }

        if (isAscending()){
            return fieldValue1.compareTo(fieldValue2);
        } else {
            return fieldValue2.compareTo(fieldValue1);
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
