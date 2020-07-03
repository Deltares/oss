package nl.deltares.search.facet.event;

import aQute.bnd.annotation.metatype.Meta;

//@ExtendedObjectClassDefinition(
//        category = "dynamic-data-mapping"
//)
@Meta.OCD(
        id = "nl.deltares.search.facet.event.EventFacetConfiguration"
)
public interface EventFacetConfiguration {

    @Meta.AD(required = false, deflt = "0")
    long eventId();
}
