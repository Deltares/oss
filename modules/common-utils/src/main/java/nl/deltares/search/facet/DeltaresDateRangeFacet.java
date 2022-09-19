package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.*;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.util.Validator;

/**
 * Search for articles with a ddmfieldvalue date within a given date range.
 * It is possible to provide only an upper or lower date value.
 *
 * Use te fieldnamevalues to search over multiple structure types; session, dinner, bustransfer
 */
public class DeltaresDateRangeFacet extends BaseFacet {

    private String _path = "ddmFieldArray";
    @SuppressWarnings("FieldCanBeLocal")
    private final String _ddmFieldName = "ddmFieldName";
    private String[] _ddmFieldNameValues = {};

    private String _ddmFieldValueKeywordName = "ddmFieldValueKeyword";
    private String _ddmFieldValueKeywordStartValue = null;
    private String _ddmFieldValueKeywordEndValue = null;

    public DeltaresDateRangeFacet(String name, SearchContext searchContext) {
        super(searchContext);
        setFieldName(name);
    }

    /**
     * Indexed name of ddmfield for the different structures.
     * @param fieldValues indexed name of ddmfield
     */
    public void setFieldNameValues(String... fieldValues) {
        if (fieldValues != null) {
            _ddmFieldNameValues = fieldValues;
        } else {
            _ddmFieldNameValues = new String[0];
        }
    }

    /**
     * Field name of containing the search values. Defaults to 'ddmFieldValueKeyword'
     *
     * If field supports localization, it is necessary to provided localized field name
     * @param fieldName name of field
     */
    @SuppressWarnings("unused")
    public void setFieldValueKeywordName(String fieldName) {
        _ddmFieldValueKeywordName = fieldName;
    }

    /**
     * Lowest date boundary of search period.
     *
     * Expected date format; YYYY-MM-dd
     *
     * If omitted the lower boundary is not included in the query
     *
     * @param formattedDate date string
     */
    public void setStartSearchDate(String formattedDate) {
        _ddmFieldValueKeywordStartValue = formattedDate;
    }

    /**
     * Upper date boundary of search period.
     *
     * Expected date format; YYYY-MM-dd
     *
     * If omitted the upper boundary is not included in the query
     *
     * @param formattedDate date string
     */
    public void setEndSearchDate(String formattedDate) {
        _ddmFieldValueKeywordEndValue = formattedDate;
    }

    public void setPath(String path) {
        _path = path;
    }

    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
        //Nothing to filter
        if (_ddmFieldValueKeywordStartValue == null && _ddmFieldValueKeywordEndValue == null) {
            return null;
        }

        BooleanFilter booleanFilter = new BooleanFilter();

        if (Validator.isNotNull(_ddmFieldName)) {
            TermsFilter _StructureFieldNamesFilter = new TermsFilter(_path + '.' + _ddmFieldName);
            _StructureFieldNamesFilter.addValues(_ddmFieldNameValues);
            booleanFilter.add(_StructureFieldNamesFilter, BooleanClauseOccur.MUST);
        }

        boolean includeLower = _ddmFieldValueKeywordStartValue != null;
        boolean includeUpper = _ddmFieldValueKeywordEndValue != null;

        Filter dateRangeFilter = new DateRangeTermFilter(_path + '.' + _ddmFieldValueKeywordName,
                includeLower, includeUpper, _ddmFieldValueKeywordStartValue, _ddmFieldValueKeywordEndValue);
        booleanFilter.add(dateRangeFilter, BooleanClauseOccur.MUST);
        BooleanQuery booleanQuery = new BooleanQueryImpl();
        booleanQuery.setPreBooleanFilter(booleanFilter);
        NestedQuery nestedQuery = new NestedQuery(_path, booleanQuery);

        QueryFilter queryFilter = new QueryFilter(nestedQuery);

        return new BooleanClauseImpl<>(queryFilter, BooleanClauseOccur.MUST);
    }


}
