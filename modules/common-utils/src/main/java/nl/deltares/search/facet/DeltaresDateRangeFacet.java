package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.*;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * Search for articles with a ddmfieldvalue date within a given date range.
 * It is possible to provide only an upper or lower date value.
 * <p>
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
        _ddmFieldNameValues = Objects.requireNonNullElseGet(fieldValues, () -> new String[0]);
    }

    /**
     * Field name of containing the search values. Defaults to 'ddmFieldValueKeyword'
     * <p>
     * If field supports localization, it is necessary to provided localized field name
     * @param fieldName name of field
     */
    @SuppressWarnings("unused")
    public void setFieldValueKeywordName(String fieldName) {
        _ddmFieldValueKeywordName = fieldName;
    }

    /**
     * Lowest date boundary of search period.
     * <p>
     * Expected date format; YYYY-MM-dd
     * <p>
     * If omitted the lower boundary is not included in the query
     *
     * @param formattedDate date string
     */
    public void setStartSearchDate(String formattedDate) {
        _ddmFieldValueKeywordStartValue = formattedDate;
    }

    /**
     * Upper date boundary of search period.
     * <p>
     * Expected date format; YYYY-MM-dd
     * <p>
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
        BooleanQuery booleanQuery = new BooleanQuery() {
            {
                this.setPreBooleanFilter(booleanFilter);
            }
        };

        NestedQuery nestedQuery = new NestedQuery(_path, booleanQuery);

        QueryFilter queryFilter = new QueryFilter(nestedQuery);
        return new BooleanClause<>(queryFilter, BooleanClauseOccur.MUST);
    }


}
