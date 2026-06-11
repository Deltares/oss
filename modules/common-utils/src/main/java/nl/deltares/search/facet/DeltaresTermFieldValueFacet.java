package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;

/**
 * Search for articles with an exact ddmfieldvalue. Ddmfield value is passed using the fieldvaluekeywordvalue
 * Use te fieldnamevalues to search over multiple structure types; session, dinner, bustransfer
 */
public class DeltaresTermFieldValueFacet extends BaseFacet {

    @SuppressWarnings("FieldCanBeLocal")
    private final String _termFieldValue;
    private final boolean _useWildCard;
    private boolean exclude = false;

    public DeltaresTermFieldValueFacet(String fieldName, String fieldValue, SearchContext searchContext) {
        this(fieldName, fieldValue, false, searchContext);
    }
    public DeltaresTermFieldValueFacet(String fieldName, String fieldValue, boolean userWildcard, SearchContext searchContext) {
        super(searchContext);
        setFieldName(fieldName);
        _termFieldValue = fieldValue;
        _useWildCard = userWildcard;
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

        Filter filter;
        if  (_useWildCard) {
            WildcardQuery wildcardQuery = new WildcardQueryImpl(getFieldName(), _termFieldValue);
            filter = new QueryFilter(wildcardQuery);
        } else {
            filter = new TermFilter(getFieldName(), _termFieldValue);
        }

        if (exclude) {
            return new BooleanClauseImpl<>(filter, BooleanClauseOccur.MUST_NOT);
        } else {
            return new BooleanClauseImpl<>(filter, BooleanClauseOccur.MUST);
        }

    }


}
