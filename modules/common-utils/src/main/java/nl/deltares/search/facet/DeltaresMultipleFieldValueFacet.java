package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * Search for any article with a non-nested field value that matches one of the provided values.
 *
 */
public class DeltaresMultipleFieldValueFacet extends BaseFacet {

    private String[] _fieldValues = {};

    /**
     *
     * @param fieldName Name of field to search
     * @param searchContext {@link SearchContext}
     */
    public DeltaresMultipleFieldValueFacet(String fieldName, SearchContext searchContext) {
        super(searchContext);
        setFieldName(fieldName);
    }

    /**
     * Values to look for
     * @param fieldValues List of search values
     */
    public void setFieldValues(String... fieldValues){
        if (fieldValues != null) {
            _fieldValues = fieldValues;
        }
        else {
            _fieldValues = new String[0];
        }
    }

    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
        //Nothing to filter
        if (_fieldValues == null || ArrayUtil.isEmpty(_fieldValues)) {
            return null;
        }

        BooleanFilter booleanFilter = new BooleanFilter();

        final String fieldName = getFieldName();
        if (Validator.isNotNull(fieldName)) {
            TermsFilter _StructureFieldNamesFilter = new TermsFilter(fieldName);
            _StructureFieldNamesFilter.addValues(_fieldValues);
            booleanFilter.add(_StructureFieldNamesFilter, BooleanClauseOccur.MUST);
        }
        BooleanQuery booleanQuery = new BooleanQueryImpl();
        booleanQuery.setPreBooleanFilter(booleanFilter);
        QueryFilter queryFilter = new QueryFilter(booleanQuery);
        return new BooleanClauseImpl<>(queryFilter, BooleanClauseOccur.MUST);
    }

}
