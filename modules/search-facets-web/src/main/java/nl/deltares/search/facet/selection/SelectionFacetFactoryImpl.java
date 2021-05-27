package nl.deltares.search.facet.selection;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.FacetFactory;
import com.liferay.portal.search.filter.FilterBuilders;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        service = {
                FacetFactory.class,
                SelectionFacetFactory.class
        }
)
public class SelectionFacetFactoryImpl implements SelectionFacetFactory {

    private String field;

    @Override
    public String getFacetClassName() {
        return this.field;
    }

    @Override
    public Facet newInstance(SearchContext searchContext) {
        return new SelectionFacet(this.field, searchContext, this.filterBuilders);
    }

    @Override
    public void setField(String field) {
        this.field = field;
    }

    @Reference
    protected FilterBuilders filterBuilders;
}
