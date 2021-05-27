package nl.deltares.search.facet.selection;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;

public class SelectionFacetBuilder {

    public SelectionFacetBuilder(SelectionFacetFactory facetFactory) {
        this.selectionFacetFactory = facetFactory;
    }

    public Facet build() {
        Facet facet = selectionFacetFactory.newInstance(searchContext);

        String typeString = this.getSelectionString(facet);

        if (!Validator.isBlank(typeString)) {
            facet.select(typeString);
        }

        return facet;
    }

    private String getSelectionString(Facet facet) {
        String selectionString = null;

        if (!Validator.isBlank(selection)) {
            selectionString = selection;
            this.searchContext.setAttribute(facet.getFieldId(), selection);
        }

        return selectionString;
    }

    public void setSelection(String selection) {
        if ("undefined".equals(selection)){
            this.selection = null;
        } else {
            this.selection = selection;
        }
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    private String selection;
    private final SelectionFacetFactory selectionFacetFactory;
    private SearchContext searchContext;
}
