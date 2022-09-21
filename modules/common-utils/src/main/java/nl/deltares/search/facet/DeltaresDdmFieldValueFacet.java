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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * Search for articles with an exact ddmfieldvalue. Ddmfield value is passed using the fieldvaluekeywordvalue
 *
 * Use te fieldnamevalues to search over multiple structure types; session, dinner, bustransfer
 */
public class DeltaresDdmFieldValueFacet extends BaseFacet {

    private String _path = "ddmFieldArray" ;
    @SuppressWarnings("FieldCanBeLocal")
    private final String _ddmFieldName = "ddmFieldName";
    private String[] _ddmFieldNameValues = {};

    private String _ddmFieldValueKeywordName = "ddmFieldValueKeyword";
    private String _ddmFieldValueKeywordValue = null;
    private boolean exclude = false;

    public DeltaresDdmFieldValueFacet(String name, SearchContext searchContext) {
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
        }
        else {
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
    public void setFieldValueKeywordName(String fieldName){
        _ddmFieldValueKeywordName = fieldName;
    }


    /**
     * Exclude wil result in a negative search of this field value. So if an item contains the field it will not
     * be added to the results
     */
    public void setExclude(boolean exclude){
        this.exclude = exclude;
    }
    /**
     * Search value contained by the field set in the method {@link #setFieldValueKeywordName(String)}
     * @param fieldValue search value
     */
    public void setFieldValueKeywordValue(String fieldValue){
        _ddmFieldValueKeywordValue = fieldValue;
    }

    public void setPath(String path) {
        _path = path;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
        //Nothing to filter
        if (_ddmFieldValueKeywordValue == null || ArrayUtil.isEmpty(_ddmFieldNameValues)) {
            return null;
        }

        BooleanFilter booleanFilter = new BooleanFilter();

        if (Validator.isNotNull(_ddmFieldName)) {
            TermsFilter _StructureFieldNamesFilter = new TermsFilter(_path + '.' + _ddmFieldName);
            _StructureFieldNamesFilter.addValues(_ddmFieldNameValues);
            booleanFilter.add(_StructureFieldNamesFilter, BooleanClauseOccur.MUST);
        }

        TermFilter searchFieldValue = new TermFilter(_path + '.' + _ddmFieldValueKeywordName, _ddmFieldValueKeywordValue);
        booleanFilter.add(
                searchFieldValue, BooleanClauseOccur.MUST);

        BooleanQuery booleanQuery = new BooleanQueryImpl();

        booleanQuery.setPreBooleanFilter(booleanFilter);

        NestedQuery nestedQuery = new NestedQuery(_path, booleanQuery);

        QueryFilter queryFilter = new QueryFilter(nestedQuery);

        if (exclude){
            return new BooleanClauseImpl<>(queryFilter, BooleanClauseOccur.MUST_NOT);
        } else {
            return new BooleanClauseImpl<>(queryFilter, BooleanClauseOccur.MUST);
        }

    }



}
