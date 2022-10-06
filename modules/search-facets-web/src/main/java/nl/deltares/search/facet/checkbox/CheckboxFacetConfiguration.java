package nl.deltares.search.facet.checkbox;


import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.checkbox.CheckboxFacetConfiguration"
)
public interface CheckboxFacetConfiguration {

    @Meta.AD(required = false, deflt = "" , description = "Configure the structure name that contains the checkbox field")
    String structureName();

    @Meta.AD(required = false, deflt = "", description = "Configure the field name of the checkbox")
    String fieldName();

    @Meta.AD(required = false, deflt = "{}" , description = "Configure the title of facet per language")
    String titleMap();

    @Meta.AD(required = false, deflt = "", description = "Toggle visibility of facet portlet.")
    String visible();

    @Meta.AD(required = false, deflt = "", description = "Set the default value for the facet portlet.")
    String defaultValue();

    @Meta.AD(required = false, deflt = "", description = "If selected only return items with matching field. If not selected also include that do not contain field.")
    String explicitSearch();

}
