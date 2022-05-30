package nl.deltares.search.facet.program;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.program.UserProgramFacetConfiguration"
)
public interface UserProgramFacetConfiguration {

    @Meta.AD(required = false, deflt = "false", description = "Show my registrations made for other users.")
    String showRegistrationsMadeForOthers();

    @Meta.AD(required = false, deflt = "", description = "Link to page showing registrations made for other users.")
    String linkToRegistrationsPageForOthers();



}
