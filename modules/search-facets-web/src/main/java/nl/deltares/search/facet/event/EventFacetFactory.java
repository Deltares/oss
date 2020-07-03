package nl.deltares.search.facet.event;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.search.facet.FacetFactory;

@ProviderType
public interface EventFacetFactory extends FacetFactory {
    void setField(String field);
}
