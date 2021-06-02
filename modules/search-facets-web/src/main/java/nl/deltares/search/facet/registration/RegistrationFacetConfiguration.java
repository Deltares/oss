package nl.deltares.search.facet.registration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.registration.RegistrationFacetConfiguration"
)
public interface RegistrationFacetConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Overrule DSD configured structures for filtering. (space separated)")
    String structureList();
}
