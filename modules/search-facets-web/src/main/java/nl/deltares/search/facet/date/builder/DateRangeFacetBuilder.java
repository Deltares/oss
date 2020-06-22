package nl.deltares.search.facet.date.builder;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;

public class DateRangeFacetBuilder {

    public DateRangeFacetBuilder(DateRangeFacetFactory dateRangeFacetFactory) {
        this.dateRangeFacetFactory = dateRangeFacetFactory;
        this.dateRangeFactory = new DateRangeFactory();
    }

    public Facet build() {
        Facet facet = dateRangeFacetFactory.newInstance(searchContext);

        String rangeString = this.getSelectedRangeString(facet);

        if (!Validator.isBlank(rangeString)) {
            facet.select(rangeString);
        }

        return facet;
    }

    private String getSelectedRangeString(Facet facet) {
        String rangeString = null;

        if (!Validator.isBlank(startDate) &&
                !Validator.isBlank(endDate)) {

            rangeString = this.dateRangeFactory.getRangeString(startDate, endDate);

            this.searchContext.setAttribute(facet.getFieldId(), rangeString);
        }

        return rangeString;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    private String startDate;
    private String endDate;
    private final DateRangeFacetFactory dateRangeFacetFactory;
    private final DateRangeFactory dateRangeFactory;
    private SearchContext searchContext;
}
