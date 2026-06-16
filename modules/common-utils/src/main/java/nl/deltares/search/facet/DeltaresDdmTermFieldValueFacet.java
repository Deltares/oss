package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;

/**
 * Search for articles with an exact ddmfieldvalue. Ddmfield value is passed using the fieldvaluekeywordvalue
 * Use te fieldnamevalues to search over multiple structure types; session, dinner, bustransfer
 */
public class DeltaresDdmTermFieldValueFacet extends BaseFacet {

    @SuppressWarnings("FieldCanBeLocal")
    private final String _termFieldValue;
    private final boolean _useWildCard;
    private boolean exclude = false;

    public DeltaresDdmTermFieldValueFacet(String fieldName, String fieldValue, boolean userWildcard, SearchContext searchContext) {
        super(searchContext);
        setFieldName(fieldName);
        _termFieldValue = fieldValue;
        _useWildCard = userWildcard;
    }

    public boolean isExclude() {
        return exclude;
    }

    /**
     * Exclude wil result in a negative search of this field value. So if an item contains the field it will not
     * be added to the results
     */
    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {

        BooleanFilter booleanFilter = new BooleanFilter();
        QueryFilter queryFilter = new QueryFilter(new WildcardQueryImpl("ddmFieldArray.ddmFieldName", String.format("ddm__keyword__*__%s*", getFieldName())));
        booleanFilter.add(queryFilter, BooleanClauseOccur.MUST);

        if  (_useWildCard) {
            WildcardQuery wildcardQuery = new WildcardQueryImpl("ddmFieldArray.ddmFieldValueKeyword_String_sortable", _termFieldValue);
            booleanFilter.add(new QueryFilter(wildcardQuery), BooleanClauseOccur.MUST);
        } else {
            booleanFilter.add(new TermFilter("ddmFieldArray.ddmFieldValueKeyword_String_sortable", _termFieldValue), BooleanClauseOccur.MUST);
        }

        BooleanQueryImpl booleanQuery = new BooleanQueryImpl();
        booleanQuery.setPreBooleanFilter(booleanFilter);
        NestedQuery nestedQuery = new NestedQuery("ddmFieldArray", booleanQuery);

        if (exclude) {
            return new BooleanClauseImpl<>(new QueryFilter(nestedQuery), BooleanClauseOccur.MUST_NOT);
        } else {
            return new BooleanClauseImpl<>(new QueryFilter(nestedQuery), BooleanClauseOccur.MUST);
        }

    }


}
