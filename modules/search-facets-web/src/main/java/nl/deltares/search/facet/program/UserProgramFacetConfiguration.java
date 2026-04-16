package nl.deltares.search.facet.program;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.program.UserProgramFacetConfiguration"
)
public interface UserProgramFacetConfiguration {

    @Meta.AD(required = false, deflt = "false", description = "Toggle visibility of facet portlet.")
    String visible();

    @Meta.AD(required = false, deflt = "false", description = "Show my registrations made for other users.")
    String showRegistrationsMadeForOthers();

}
