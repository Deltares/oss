package nl.deltares.search.facet.terms;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.terms.TermsFacetConfiguration"
)
public interface TermsFacetConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Company Ids of companies from which to retrieve articles. Space separated")
    String companyIds();

    @Meta.AD(required = false, deflt = "", description = "Term field name of the articles to retrieve")
    String termFieldName();

    @Meta.AD(required = false, deflt = "", description = "Term value of the articles to retrieve")
    String termValue();

    @Meta.AD(required = false, deflt = "false", description = "Does the Term value contain wildcard")
    String useWildcard();

    @Meta.AD(required = false, deflt = "", description = "Article Ids of articles to retrieve, separated by space. If specified, only these articles will be retrieved, otherwise all articles matching the companyId and ddmStructureKey will be retrieved.")
    String articleIds();

    @Meta.AD(required = false, deflt = "", description = "Group Ids of all sites to search, separated by space. If specified, articles will only be retrieved from given sites.")
    String groupIds();
}
