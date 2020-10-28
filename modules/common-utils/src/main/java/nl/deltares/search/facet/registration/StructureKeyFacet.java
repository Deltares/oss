package nl.deltares.search.facet.registration;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.filter.FilterBuilders;

public class StructureKeyFacet extends BaseFacet implements Facet {

    public StructureKeyFacet(String fieldName, SearchContext searchContext, FilterBuilders filterBuilders) {
        super(searchContext);
        setFieldName(fieldName);
        _filterBuilders = filterBuilders;
    }

    @Override
    public String getAggregationName() {
        String aggregationName;
        if (_aggregationName != null) {
            aggregationName = _aggregationName;
        } else {
            aggregationName = getFieldName();
        }
        return aggregationName;
    }

    @Override
    public String[] getSelections() {
        return _selections;
    }

    @Override
    public void select(String... selections) {
        _selections = selections;
    }

    @Override
    public void setAggregationName(String aggregationName) {
        _aggregationName = aggregationName;
    }


    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
        if (ArrayUtil.isEmpty(_selections)) {
            return null;
        }
        SearchContext searchContext = getSearchContext();
        TermsFilter termsFilter = new TermsFilter(getFieldName());
        termsFilter.addValues(_selections);
        return BooleanClauseFactoryUtil.createFilter(
                searchContext, termsFilter, BooleanClauseOccur.MUST);
    }

    private String _aggregationName;
    private final FilterBuilders _filterBuilders;
    private String[] _selections = {};
}
