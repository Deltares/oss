package nl.deltares.search.results;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.kernel.util.comparator.RegistrationDisplayContextComparator;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.Period;

import java.util.ArrayList;
import java.util.List;

public class DsdResultsSearchContainer extends SearchContainer<RegistrationDisplayContext> {

    private static final Log LOG = LogFactoryUtil.getLog(DsdResultsSearchContainer.class);

    private final DsdParserUtils dsdParserUtils;
    private final ThemeDisplay themeDisplay;
    private final SearchContainer<Document> searchContainer;

    public DsdResultsSearchContainer(SearchContainer<Document> searchContainer, ThemeDisplay themeDisplay, DsdParserUtils dsdParserUtils) {
        super(searchContainer.getPortletRequest(), searchContainer.getDisplayTerms(), searchContainer.getSearchTerms(),
                searchContainer.getCurParam(), searchContainer.getCur(), searchContainer.getDelta(), searchContainer.getIteratorURL(),
                searchContainer.getHeaderNames(), searchContainer.getEmptyResultsMessage(), searchContainer.getClassName());

        this.searchContainer = searchContainer;
        this.themeDisplay = themeDisplay;
        this.dsdParserUtils = dsdParserUtils;
        loadResultsFromSearchContainer();

    }

    @Override
    public List<RegistrationDisplayContext> getResults() {

        loadResultsFromSearchContainer();
        final List<RegistrationDisplayContext> results = super.getResults();
        super.setDelta(searchContainer.getDelta());
        super.setTotal(searchContainer.getTotal());
        return results;
    }

    private void loadResultsFromSearchContainer() {
        final List<Document> results = searchContainer.getResults();
        List<RegistrationDisplayContext> registrationDisplayContexts = new ArrayList<>(results.size() + 20);
        splitMultiDayRegistrations(loadRegistrations(results), registrationDisplayContexts);
        registrationDisplayContexts.sort(new RegistrationDisplayContextComparator());
        super.setResults(registrationDisplayContexts);
    }

    private void splitMultiDayRegistrations(List<Registration> registrations, List<RegistrationDisplayContext> registrationDisplayContexts) {

        for (Registration registration : registrations) {
            if (registration.isMultiDayEvent() && !registration.isShowMultipleDaysAsSingleDate()) {
                final List<Period> startAndEndTimesPerDay = registration.getStartAndEndTimesPerDay();
                for (int i = 0; i < startAndEndTimesPerDay.size(); i++) {
                    final RegistrationDisplayContext displayContext = new RegistrationDisplayContext(registration, i, themeDisplay);
                    registrationDisplayContexts.add(displayContext);
                }
            } else {
                final RegistrationDisplayContext displayContext = new RegistrationDisplayContext(registration, 0, themeDisplay);
                registrationDisplayContexts.add(displayContext);
            }
        }

    }

    private List<Registration> loadRegistrations(List<Document> results) {
        final ArrayList<Registration> registrations = new ArrayList<>();
        for (Document result : results) {
            if (!result.getFields().containsKey("entryClassPK")) continue;
            final Field classPK = result.getField("entryClassPK");
            JournalArticle registrationArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(Long.parseLong(classPK.getValue()));
            if (registrationArticle == null) {
                LOG.warn(String.format("Failed to fetch registration for classPK %s", classPK.getValue()));
                continue;
            }
            try {
                final AbsDsdArticle absDsdArticle = dsdParserUtils.toDsdArticle(registrationArticle);
                if (!(absDsdArticle instanceof Registration)) continue;
                registrations.add((Registration) absDsdArticle);
            } catch (PortalException e) {
                //
            }
        }
        return registrations;
    }
}
