package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Search for articles with an exact ddmfieldvalue. Ddmfield value is passed using the fieldvaluekeywordvalue
 * Use te fieldnamevalues to search over multiple structure types; session, dinner, bustransfer
 */
public class DeltaresRangeFieldValueFacet extends BaseFacet {

    @SuppressWarnings("FieldCanBeLocal")
    private final DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //20250630100528
    private static final List<String> SORTABLE_DATE_FIELDS = Arrays.asList("createDate", "modified", "publishedDate");
    private final String _upperValue;
    private final String _lowerValue;

    public DeltaresRangeFieldValueFacet(String fieldName, String lowerValue, String upperValue, SearchContext searchContext) {
        super(searchContext);
        setFieldName(getFieldName(fieldName));
        inputDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));

        DateFormat outputDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        outputDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        if (upperValue == null || upperValue.isEmpty()) {
            _upperValue = null;
        } else {
            Date upperDate = parseDate(upperValue);
            if (upperDate != null) {
                _upperValue = getDateValue(outputDateFormat, upperDate, isSortableDateField(fieldName));
            } else {
                _upperValue = upperValue;
            }
        }
        if (lowerValue == null || lowerValue.isEmpty()) {
            _lowerValue = null;
        } else {
            Date lowerDate = parseDate(lowerValue);
            _lowerValue = getDateValue(outputDateFormat, lowerDate, isSortableDateField(fieldName));
        }
    }

    private static boolean isSortableDateField(String fieldName) {
        if (SORTABLE_DATE_FIELDS.contains(fieldName)) {return true;}
        return fieldName.endsWith("_sortable");

    }
    private static String getFieldName(String fieldName) {

        if (fieldName == null || fieldName.isEmpty()) {return null;}
        if(SORTABLE_DATE_FIELDS.contains(fieldName)){
            return fieldName + "_sortable";
        }
        return fieldName;
    }

    private String getDateValue(DateFormat outputDateFormat, Date inputDate, boolean sortable) {

        if (sortable) {return inputDate.getTime() + "";}
        final String _upperValue;
        _upperValue = outputDateFormat.format(inputDate);
        return _upperValue;
    }

    private Date parseDate(String testValue) {
        try {
            return inputDateFormat.parse(testValue);
        } catch (ParseException e) {
            return null;
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {


        return new BooleanClause<>( new RangeTermFilter(getFieldName(), true, true, _lowerValue, _upperValue) , BooleanClauseOccur.MUST);
    }


}
