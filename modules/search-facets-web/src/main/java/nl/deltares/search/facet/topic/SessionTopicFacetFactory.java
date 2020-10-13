package nl.deltares.search.facet.topic;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.search.facet.FacetFactory;

@ProviderType
public interface SessionTopicFacetFactory extends FacetFactory {
    void setField(String field);
}
