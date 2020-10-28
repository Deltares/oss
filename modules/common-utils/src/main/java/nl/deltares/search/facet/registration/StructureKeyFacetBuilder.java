package nl.deltares.search.facet.registration;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;

import java.util.ArrayList;
import java.util.Collections;

public class StructureKeyFacetBuilder {

    public StructureKeyFacetBuilder(StructureKeyFacetFactory facetFactory) {
        this.facetFactory = facetFactory;
    }

    public Facet build() {
        Facet facet = facetFactory.newInstance(searchContext);

        String[] registrationsKeys = this.getSelectedStructureKeys();

        if (registrationsKeys.length > 0) {
            facet.select(registrationsKeys);
        }

        return facet;
    }

    private String[] getSelectedStructureKeys() {
        return structuresKeys.toArray(new String[0]);
    }

    public void setStructureKeys(String... selection) {
        if(selection != null){
            Collections.addAll(structuresKeys, selection);
        }
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    private ArrayList<String> structuresKeys = new ArrayList<>();
    private final StructureKeyFacetFactory facetFactory;
    private SearchContext searchContext;
}
