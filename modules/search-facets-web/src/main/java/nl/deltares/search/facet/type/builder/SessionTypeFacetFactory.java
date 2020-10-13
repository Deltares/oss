package nl.deltares.search.facet.type.builder;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.search.facet.FacetFactory;

@ProviderType
public interface SessionTypeFacetFactory extends FacetFactory {
    void setField(String field);
}
