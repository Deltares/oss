package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.DateRangeTermFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;

/**
 * Search for articles with a ddmfieldvalue date within a given date range.
 * It is possible to provide only an upper or lower date value.
 * <p>
 * This Facet uses a Wildcard filter to include different sturcture documents.
 *
 * This Facet does NOT search on localized ddmFields.
 */
public class DeltaresDdmDateRangeFacet extends BaseFacet {

    @SuppressWarnings("FieldCanBeLocal")
    private final String _ddmFieldValueKeywordStartValue;
    private final String _ddmFieldValueKeywordEndValue;

    public DeltaresDdmDateRangeFacet(String fieldName, String lowerValue, String upperValue, SearchContext searchContext) {
        super(searchContext);
        setFieldName(fieldName);
        _ddmFieldValueKeywordStartValue = lowerValue == null || lowerValue.isEmpty() ? null : lowerValue;
        _ddmFieldValueKeywordEndValue = upperValue == null || upperValue.isEmpty() ? null : upperValue;
    }

    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
        //Nothing to filter
        if (_ddmFieldValueKeywordStartValue == null && _ddmFieldValueKeywordEndValue == null) {
            return null;
        }

        boolean includeLower = _ddmFieldValueKeywordStartValue != null;
        boolean includeUpper = _ddmFieldValueKeywordEndValue != null;

        BooleanFilter booleanFilter = new BooleanFilter();
        QueryFilter queryFilter = new QueryFilter(new WildcardQueryImpl("ddmFieldArray.ddmFieldName", String.format("ddm__keyword__*__%s*", getFieldName())));
        booleanFilter.add(queryFilter, BooleanClauseOccur.MUST);

        Filter dateRangeFilter = new DateRangeTermFilter("ddmFieldArray.ddmFieldValueKeyword_String_sortable",
                includeLower, includeUpper, _ddmFieldValueKeywordStartValue, _ddmFieldValueKeywordEndValue);
        booleanFilter.add(dateRangeFilter, BooleanClauseOccur.MUST);

        BooleanQueryImpl booleanQuery = new BooleanQueryImpl();
        booleanQuery.setPreBooleanFilter(booleanFilter);
        NestedQuery nestedQuery = new NestedQuery("ddmFieldArray", booleanQuery);
        return new BooleanClauseImpl<>(new QueryFilter(nestedQuery), BooleanClauseOccur.MUST);
    }


}
