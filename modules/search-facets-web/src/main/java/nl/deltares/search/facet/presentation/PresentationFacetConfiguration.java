package nl.deltares.search.facet.presentation;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.presentation.PresentationFacetConfiguration"
)
public interface PresentationFacetConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Toggle visibility of facet portlet.")
    String visible();

    @Meta.AD(required = false, deflt = "", description = "Set the default value for the facet portlet.")
    String defaultValue();


}
