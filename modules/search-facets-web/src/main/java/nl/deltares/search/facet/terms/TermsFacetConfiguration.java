package nl.deltares.search.facet.terms;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.terms.TermsFacetConfiguration"
)
public interface TermsFacetConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Term field name of the articles to retrieve")
    String termFieldName();

    @Meta.AD(required = false, deflt = "", description = "Term value of the articles to retrieve")
    String termValue();

    @Meta.AD(required = false, deflt = "false", description = "Is Term field part of a DDM Field array ")
    String isDdmField();

    @Meta.AD(required = false, deflt = "false", description = "Does the Term value contain wildcard")
    String useWildcard();

    @Meta.AD(required = false, deflt = "false", description = "Does the Term value contain multiple values seperated by a space?")
    String multipleTermValues();

}
