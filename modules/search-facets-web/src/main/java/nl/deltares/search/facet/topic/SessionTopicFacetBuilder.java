package nl.deltares.search.facet.topic;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;

public class SessionTopicFacetBuilder {

    public SessionTopicFacetBuilder(SessionTopicFacetFactory facetFactory) {
        this.topicFacetFactory = facetFactory;
    }

    public Facet build() {
        Facet facet = topicFacetFactory.newInstance(searchContext);

        String typeString = this.getSelectedTopicString(facet);

        if (!Validator.isBlank(typeString)) {
            facet.select(typeString);
        }

        return facet;
    }

    private String getSelectedTopicString(Facet facet) {
        String typeString = null;

        if (!Validator.isBlank(topic)) {
            typeString = topic;
            this.searchContext.setAttribute(facet.getFieldId(), topic);
        }

        return typeString;
    }

    public void setSessionTopic(String type) {
        if ("undefined".equals(type)){
            this.topic = null;
        } else {
            this.topic = type;
        }
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    private String topic;
    private final SessionTopicFacetFactory topicFacetFactory;
    private SearchContext searchContext;
}
