package nl.deltares.search.facet.program.builder;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;

import java.util.List;

public class UserProgramFacetBuilder {

    public UserProgramFacetBuilder(UserProgramFacetFactory userProgramFacetFactory) {
        this.userProgramFacetFactory = userProgramFacetFactory;
    }

    public Facet build() {
        Facet facet = userProgramFacetFactory.newInstance(searchContext);

        if (ids != null && !ids.isEmpty()) {
            facet.select(ids.toArray(new String[0]));
        }

        return facet;
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public void setArticlesIds(List<String> ids) {
        this.ids = ids;
    }

    private final UserProgramFacetFactory userProgramFacetFactory;
    private SearchContext searchContext;
    private List<String> ids;
}
