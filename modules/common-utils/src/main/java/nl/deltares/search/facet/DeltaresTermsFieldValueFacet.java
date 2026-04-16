package nl.deltares.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;

/**
 * Search for articles with an exact ddmfieldvalue. Ddmfield value is passed using the fieldvaluekeywordvalue
 * Use te fieldnamevalues to search over multiple structure types; session, dinner, bustransfer
 */
public class DeltaresTermsFieldValueFacet extends BaseFacet {

    @SuppressWarnings("FieldCanBeLocal")
    private final String[] _termFieldValues;
    private boolean exclude = false;

    public DeltaresTermsFieldValueFacet(String fieldName, String[] fieldValues, SearchContext searchContext) {
        super(searchContext);
        setFieldName(fieldName);
        _termFieldValues = fieldValues;
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

        TermsFilter termsFilter = new TermsFilter(getFieldName());
        termsFilter.addValues(_termFieldValues);

        if (exclude) {
            return new BooleanClauseImpl<>(termsFilter, BooleanClauseOccur.MUST_NOT);
        } else {
            return new BooleanClauseImpl<>(termsFilter, BooleanClauseOccur.MUST);
        }

    }


}
