package nl.deltares.tableview.comparator;

import com.liferay.portal.kernel.util.NumericalStringComparator;
import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.tableview.model.DisplayRegistration;

public class RegistrationComparator extends OrderByComparator<DisplayRegistration> {

    private final boolean _ascending;
    private final String _orderByField;

    final NumericalStringComparator comparator = new NumericalStringComparator();
    public RegistrationComparator(String field, boolean ascending) {
        this._ascending = ascending;
        this._orderByField = field;
    }

    @Override
    public int compare(DisplayRegistration o1, DisplayRegistration o2) {

        String fieldValue1;
        String fieldValue2;
        switch (_orderByField) {
            case "eventName":
                fieldValue1 = o1.getEventName();
                fieldValue2 = o2.getEventName();
                break;
            case "registrationName":
                fieldValue1 = o1.getRegistrationName();
                fieldValue2 = o2.getRegistrationName();
                break;
            case "email":
                fieldValue1 = o1.getEmail();
                fieldValue2 = o2.getEmail();
                break;
            case "startTime":
                fieldValue1 = String.valueOf(o1.getStartTime());
                fieldValue2 = String.valueOf(o2.getStartTime());
                break;
            case "endTime":
                fieldValue1 = String.valueOf(o1.getEndTime());
                fieldValue2 = String.valueOf(o2.getEndTime());
                break;
            case "eventResourceId":
                fieldValue1 = String.valueOf(o1.getEventResourceId());
                fieldValue2 = String.valueOf(o2.getEventResourceId());
                break;
            default:
                fieldValue1 = String.valueOf(o1.getResourceId());
                fieldValue2 = String.valueOf(o2.getResourceId());
        }

        if (isAscending()) {
            return comparator.compare(fieldValue1,fieldValue2);
        } else {
            return comparator.compare(fieldValue2,fieldValue1);
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
