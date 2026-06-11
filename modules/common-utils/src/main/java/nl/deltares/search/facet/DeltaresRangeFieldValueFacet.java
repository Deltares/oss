package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Search for articles with an exact ddmfieldvalue. Ddmfield value is passed using the fieldvaluekeywordvalue
 * Use te fieldnamevalues to search over multiple structure types; session, dinner, bustransfer
 */
public class DeltaresRangeFieldValueFacet extends BaseFacet {

    @SuppressWarnings("FieldCanBeLocal")
    private final DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //20250630100528

    private final String _upperValue;
    private final String _lowerValue;

    public DeltaresRangeFieldValueFacet(String fieldName, String upperValue, String lowerValue, SearchContext searchContext) {
        super(searchContext);
        setFieldName(fieldName);
        inputDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));

        DateFormat outputDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        outputDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        if (upperValue == null || upperValue.isEmpty()) {
            _upperValue = null;
        } else {
            Date upperDate = parseDate(upperValue);
            if (upperDate != null) {
                _upperValue = outputDateFormat.format(upperDate);
            } else {
                _upperValue = upperValue;
            }
        }
        if (lowerValue == null || lowerValue.isEmpty()) {
            _lowerValue = null;
        } else {
            Date lowerDate = parseDate(lowerValue);
            _lowerValue = outputDateFormat.format(lowerDate);
        }
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
        return new BooleanClauseImpl<>( new RangeTermFilter(getFieldName(), true, true, _lowerValue, _upperValue) , BooleanClauseOccur.MUST);
    }


}
