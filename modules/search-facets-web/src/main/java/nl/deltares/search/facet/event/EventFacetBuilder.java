package nl.deltares.search.facet.event;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;

public class EventFacetBuilder {

    public EventFacetBuilder(EventFacetFactory facetFactory) {
        this.topicFacetFactory = facetFactory;
    }

    public Facet build() {
        Facet facet = topicFacetFactory.newInstance(searchContext);

        String typeString = this.getSelectedEventString(facet);

        if (!Validator.isBlank(typeString)) {
            facet.select(typeString);
        }

        return facet;
    }

    private String getSelectedEventString(Facet facet) {
        String typeString = null;

        if (eventId> 0) {
            typeString = String.valueOf(eventId);
            this.searchContext.setAttribute(facet.getFieldId(), eventId);
        }

        return typeString;
    }

    public void setEventId(String eventId) {
        if (eventId == null){
            this.eventId = 0;
        }else {
            this.eventId = Long.parseLong(eventId);
        }
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    private long eventId;
    private final EventFacetFactory topicFacetFactory;
    private SearchContext searchContext;
}
