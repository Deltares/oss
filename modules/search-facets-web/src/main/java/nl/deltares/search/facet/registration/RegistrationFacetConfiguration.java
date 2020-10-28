package nl.deltares.search.facet.registration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.registration.RegistrationFacetConfiguration"
)
public interface RegistrationFacetConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Configure the structures to filter for.")
    String structureList();
}
