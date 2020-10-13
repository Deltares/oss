package nl.deltares.search.facet.type.builder;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;
import nl.deltares.search.facet.topic.SessionTopicFacetFactory;

public class SessionTypeFacetBuilder {

    public SessionTypeFacetBuilder(SessionTopicFacetFactory facetFactory) {
        this.typeFacetFactory = facetFactory;
    }

    public Facet build() {
        Facet facet = typeFacetFactory.newInstance(searchContext);

        String typeString = this.getSelectedTypeString(facet);

        if (!Validator.isBlank(typeString)) {
            facet.select(typeString);
        }

        return facet;
    }

    private String getSelectedTypeString(Facet facet) {
        String typeString = null;

        if (!Validator.isBlank(type)) {
            typeString = type;
            this.searchContext.setAttribute(facet.getFieldId(), type);
        }

        return typeString;
    }

    public void setSessionType(String type) {
        if (type != null && "undefined".equals(type)){
            this.type = null;
        } else {
            this.type = type;
        }
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    private String type;
    private final SessionTopicFacetFactory typeFacetFactory;
    private SearchContext searchContext;
}
