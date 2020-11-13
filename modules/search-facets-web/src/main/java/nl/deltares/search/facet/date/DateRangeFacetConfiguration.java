package nl.deltares.search.facet.date;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.date.DateRangeFacetConfiguration"
)
public interface DateRangeFacetConfiguration {

    @Meta.AD(required = false)
    String startDate();

    @Meta.AD(required = false)
    String endDate();

    @Meta.AD(required = false)
    String showPast();


}
