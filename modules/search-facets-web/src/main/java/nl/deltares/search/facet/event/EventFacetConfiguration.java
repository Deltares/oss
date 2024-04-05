package nl.deltares.search.facet.event;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.registration.EventFacetConfiguration"
)
public interface EventFacetConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Overrule DSD configured event list for filtering. (space separated)")
    String eventsList();
}
