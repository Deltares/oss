package nl.deltares.search.facet.selection;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.search.facet.FacetFactory;

@ProviderType
public interface SelectionFacetFactory extends FacetFactory {
    void setField(String field);
}
