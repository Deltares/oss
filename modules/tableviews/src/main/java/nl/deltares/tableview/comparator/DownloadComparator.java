package nl.deltares.tableview.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.tableview.model.DisplayDownload;
import com.liferay.portal.kernel.util.NumericalStringComparator;

public class DownloadComparator extends OrderByComparator<DisplayDownload> {

    private final boolean _ascending;
    private final String _orderByField;

    final NumericalStringComparator comparator = new NumericalStringComparator();
    public DownloadComparator(String field, boolean ascending) {
        this._ascending = ascending;
        this._orderByField = field;
    }

    @Override
    public int compare(DisplayDownload o1, DisplayDownload o2) {

        String fieldValue1;
        String fieldValue2;
        switch (_orderByField) {
            case "fileName":
                fieldValue1 = o1.getFileName();
                fieldValue2 = o2.getFileName();
                break;
            case "email":
                fieldValue1 = o1.getEmail();
                fieldValue2 = o2.getEmail();
                break;
            case "organization":
                fieldValue1 = o1.getOrganization();
                fieldValue2 = o2.getOrganization();
                break;
            case "city":
                fieldValue1 = o1.getCity();
                fieldValue2 = o2.getCity();
                break;
            case "countryCode":
                fieldValue1 = o1.getCountryCode();
                fieldValue2 = o2.getCountryCode();
                break;
            case "downloadId":
                fieldValue1 = String.valueOf(o1.getDownloadId());
                fieldValue2 = String.valueOf(o2.getDownloadId());
                break;
            case "expirationDate":
                fieldValue1 = String.valueOf(o1.getExpirationDate().getTime());
                fieldValue2 = String.valueOf(o2.getExpirationDate().getTime());
                break;
            default:
                fieldValue1 = String.valueOf(o1.getModifiedDate().getTime());
                fieldValue2 = String.valueOf(o2.getModifiedDate().getTime());
        }

        if (isAscending()) {
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
