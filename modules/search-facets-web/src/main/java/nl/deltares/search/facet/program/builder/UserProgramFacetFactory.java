package nl.deltares.search.facet.program.builder;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.search.facet.FacetFactory;

@ProviderType
public interface UserProgramFacetFactory extends FacetFactory {
    void setField(String field);
}
