package nl.deltares.search.facet.terms;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
        id = "nl.deltares.search.facet.terms.TermsFacetConfiguration"
)
public interface TermsFacetConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Company Id to retrieve articles from.")
    String companyId();

    @Meta.AD(required = false, deflt = "", description = "DDMStructureKey of the articles to retrieve")
    String ddmStructureKey();

    @Meta.AD(required = false, deflt = "", description = "Article Ids of articles to retrieve, separated by space. If specified, only these articles will be retrieved, otherwise all articles matching the companyId and ddmStructureKey will be retrieved.")
    String articleIds();

    @Meta.AD(required = false, deflt = "", description = "Group Ids of all sites to search, separated by space. If specified, articles will only be retrieved from given sites.")
    String groupIds();
}
