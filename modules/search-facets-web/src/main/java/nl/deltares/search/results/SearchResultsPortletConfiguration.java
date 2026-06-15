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

    @Meta.AD(required = false, deflt = "false", description = "Reverse sort order")
    String reverseOrder();

    @Meta.AD(required = false, deflt = "20", description = "Maximum number of items to display")
    String numberOfResults();

}
