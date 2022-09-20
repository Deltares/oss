package nl.deltares.search.results;


import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.results.SearchResultsPortletConfiguration"
)
public interface SearchResultsPortletConfiguration {

    @Meta.AD(required = false, deflt = "" , description = "Configure the display template for presenting results")
    String displayTemplate();


    @Meta.AD(required = false, deflt = "dsd", description = "Display option type")
    String displayType();
}
