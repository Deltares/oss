package nl.deltares.search.facet.event.topic.builder.impl;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.FacetFactory;
import com.liferay.portal.search.filter.FilterBuilders;
import nl.deltares.search.facet.event.EventFacet;
import nl.deltares.search.facet.event.EventFacetFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        service = {
                FacetFactory.class,
                EventFacetFactory.class
        }
)
public class SessionTopicFacetFactoryImpl implements EventFacetFactory {

    private String field;

    @Override
    public String getFacetClassName() {
        return this.field;
    }

    @Override
    public Facet newInstance(SearchContext searchContext) {
        return new EventFacet(this.field, searchContext, this.filterBuilders);
    }

    @Override
    public void setField(String field) {
        this.field = field;
    }

    @Reference
    protected FilterBuilders filterBuilders;
}
