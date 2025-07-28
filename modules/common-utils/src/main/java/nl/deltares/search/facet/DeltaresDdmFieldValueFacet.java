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
 * Use te fieldnamevalues to search over multiple structure types; session, dinner, bustransfer
 */
public class DeltaresDdmFieldValueFacet extends BaseFacet {

    private String _path = "ddmFieldArray" ;
    @SuppressWarnings("FieldCanBeLocal")
    private final String _ddmFieldName = "ddmFieldName";
    private String[] _ddmFieldNameValues = {};
    private final String languageString;

    private String _ddmFieldValueKeywordName = "ddmFieldValueKeyword";
//    private String _ddmFieldValueKeywordValue = null;

    private String[] _ddmFieldValueKeywordValues = null;
    private boolean exclude = false;

    public DeltaresDdmFieldValueFacet(String name, String languageString, SearchContext searchContext) {
        super(searchContext);
        setFieldName(name);
        this.languageString = languageString;
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
        _ddmFieldValueKeywordValues = new String[]{fieldValue};
    }
    /**
     * Search values contained by the field set in the method {@link #setFieldValueKeywordName(String)}
     * @param fieldValues search value
     */
    public void setFieldValueKeywordValues(String[] fieldValues){

        _ddmFieldValueKeywordValues = fieldValues;

    }
    public void setPath(String path) {
        _path = path;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
        //Nothing to filter
        if (_ddmFieldValueKeywordValues == null || _ddmFieldValueKeywordValues.length == 0 || ArrayUtil.isEmpty(_ddmFieldNameValues)) {
            return null;
        }

        BooleanFilter booleanFilter = new BooleanFilter();

        if (Validator.isNotNull(_ddmFieldName)) {
            TermsFilter _StructureFieldNamesFilter = new TermsFilter(_path + '.' + _ddmFieldName);
            _StructureFieldNamesFilter.addValues(_ddmFieldNameValues);
            booleanFilter.add(_StructureFieldNamesFilter, BooleanClauseOccur.MUST);
        }
        boolean hasLocalizedFieldNameValues = false;
        boolean hasNoneLocalizedFieldNameValues = false;
        for (String ddmFieldNameValue : _ddmFieldNameValues) {
            if (ddmFieldNameValue.endsWith(languageString)) {
                hasLocalizedFieldNameValues = true;
            } else {
                hasNoneLocalizedFieldNameValues = true;
            }
        }
        BooleanClauseOccur booleanClauseOccur;
        BooleanFilter valueKeywordBooleanFilter ;
        // If both localized and non-localized field names are present, we need to add two sub-filters each with a SHOULD clause.
        if (hasNoneLocalizedFieldNameValues && hasLocalizedFieldNameValues) {
            booleanClauseOccur = BooleanClauseOccur.SHOULD;
            valueKeywordBooleanFilter = new BooleanFilter();
            //on of the two sub-filters MUST be true
            booleanFilter.add(valueKeywordBooleanFilter, BooleanClauseOccur.MUST);
        } else {
            //no sub-filters required
            booleanClauseOccur = BooleanClauseOccur.MUST;
            valueKeywordBooleanFilter = booleanFilter;
        }
        if (hasLocalizedFieldNameValues){
            valueKeywordBooleanFilter.add(
                    getTermsFilter(_ddmFieldValueKeywordName + '_' + languageString, _ddmFieldValueKeywordValues),
                    booleanClauseOccur);
        }
        if (hasNoneLocalizedFieldNameValues){
            valueKeywordBooleanFilter.add(
                    getTermsFilter(_ddmFieldValueKeywordName, _ddmFieldValueKeywordValues),
                    booleanClauseOccur);
        }

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

    private BaseFilter getTermsFilter(String fieldValueKeywordName, String[] fieldValueKeywordValues) {
        if (fieldValueKeywordValues.length == 1) {
            return new TermFilter(_path + '.' + fieldValueKeywordName, fieldValueKeywordValues[0]);
        } else {
            TermsFilter searchFieldValue = new TermsFilter(_path + '.' + fieldValueKeywordName);
            searchFieldValue.addValues(fieldValueKeywordValues);
            return searchFieldValue;
        }
    }


}
