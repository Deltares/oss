package nl.deltares.search.facet.registration;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.search.facet.FacetFactory;

@ProviderType
public interface StructureKeyFacetFactory extends FacetFactory {
    void setField(String field);
}
