package nl.deltares.search.facet.program;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;

public class UserProgramFacet extends BaseFacet implements Facet {

    public UserProgramFacet(String field, SearchContext searchContext) {
        super(searchContext);
        setFieldName(field);
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
            _selections = new String[]{"none"};
        }
        SearchContext searchContext = getSearchContext();
        TermsFilter articleIdsFilter = new TermsFilter(getFieldName());
        articleIdsFilter.addValues(getSelections());
        return new BooleanClause<>(articleIdsFilter, BooleanClauseOccur.MUST);
    }

    private String _aggregationName;
    private String[] _selections;
}
