package nl.deltares.search.facet.date.builder;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.search.facet.FacetFactory;

@ProviderType
public interface DateRangeFacetFactory extends FacetFactory {
    void setField(String field);
}
