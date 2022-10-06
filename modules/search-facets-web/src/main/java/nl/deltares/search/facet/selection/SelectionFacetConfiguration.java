package nl.deltares.search.facet.selection;


import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.selection.SelectionFacetConfiguration"
)
public interface SelectionFacetConfiguration {

    @Meta.AD(required = false, deflt = "" , description = "Configure the structure name that contains the selection options field")
    String structureName();

    @Meta.AD(required = false, deflt = "", description = "Configure the field name that contains the list of selectable items")
    String fieldName();

    @Meta.AD(required = false, deflt = "{}" , description = "Configure the title of facet per language")
    String titleMap();


}
