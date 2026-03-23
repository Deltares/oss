package nl.deltares.useraccount.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.useraccount.model.CustomerContact;
import nl.deltares.useraccount.model.DisplayDownload;

public class CustomerContactComparator extends OrderByComparator<CustomerContact> {

    private final boolean _ascending;
    private final String _orderByField;

    public CustomerContactComparator(String field, boolean ascending) {
        this._ascending = ascending;
        this._orderByField = field;
    }

    @Override
    public int compare(CustomerContact o1, CustomerContact o2) {

        String fieldValue1;
        String fieldValue2;
        if (_orderByField.equals("contactEmail")) {
            fieldValue1 = o1.getContactEmail();
            fieldValue2 = o2.getContactEmail();
        } else {
            fieldValue1 = o1.getContactName();
            fieldValue2 = o2.getContactName();
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
