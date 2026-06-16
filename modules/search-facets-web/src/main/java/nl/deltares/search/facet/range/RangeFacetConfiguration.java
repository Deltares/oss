package nl.deltares.search.facet.range;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.range.RangeFacetConfiguration"
)
public interface RangeFacetConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Term field name of the articles to retrieve")
    String termFieldName();

    @Meta.AD(required = false, deflt = "false", description = "Is Term field part of a DDM Field array ")
    String isDdmField();

    @Meta.AD(required = false, deflt = "", description = "Lower value of range. If ommitted then no lower range applied. Dates are expected in format yyyy-MM-dd")
    String lowerValue();

    @Meta.AD(required = false, deflt = "", description = "Upper value of range. If ommitted then no upper range applied. Dates are expected in format yyyy-MM-dd")
    String upperValue();

}
