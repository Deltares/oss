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

    @Meta.AD(required = false, deflt = "", description = "Group Ids of all sites to exclude from the search, separated by space. Configure either exclude or include site group ids.")
    String excludedSiteGroupIds();

    @Meta.AD(required = false, deflt = "", description = "Group Ids of all sites to include in the search, separated by space. Configure either exclude or include site group ids.")
    String includedSiteGroupIds();

}
