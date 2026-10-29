package nl.deltares.csvviewer.comparator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Map;

public class CsvJSONObjectComparator extends OrderByComparator<JSONObject>  {

    private final boolean _ascending;
    private final String _orderByField;

    public CsvJSONObjectComparator(boolean _ascending, String _orderByField) {
        this._ascending = _ascending;
        this._orderByField = _orderByField;
    }


    @Override
    public int compare(JSONObject o1, JSONObject o2) {

        String o1FieldValue = "";
        if (o1.has("values")) {
            Map<String, String> values = (Map<String, String>) o1.get("values");
            if (values.containsKey(_orderByField)) {
                o1FieldValue = values.get(_orderByField);
            }
        }
        String o2FieldValue = "";
        if (o2.has("values")) {
            Map<String, String> values = (Map<String, String>) o2.get("values");
            if (values.containsKey(_orderByField)) {
                o2FieldValue = values.get(_orderByField);
            }
        }

        // Handle null field values (email/name)
        if (o1FieldValue == null && o2FieldValue == null) {
            return 0;
        }
        if (o1FieldValue == null) {
            return _ascending ? -1 : 1;
        }
        if (o2FieldValue == null) {
            return _ascending ? 1 : -1;
        }

        if (_ascending){
            return o1FieldValue.compareTo(o2FieldValue);
        } else {
            return o2FieldValue.compareTo(o1FieldValue);
        }

    }
}
