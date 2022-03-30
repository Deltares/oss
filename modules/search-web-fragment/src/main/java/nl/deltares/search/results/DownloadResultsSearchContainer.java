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
import nl.deltares.portal.display.context.DownloadDisplayContext;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.DsdParserUtils;

import java.util.ArrayList;
import java.util.List;

public class DownloadResultsSearchContainer extends SearchContainer<DownloadDisplayContext> {

    private static final Log LOG = LogFactoryUtil.getLog(DownloadResultsSearchContainer.class);

    private final DsdParserUtils dsdParserUtils;
    private final ThemeDisplay themeDisplay;
    private final SearchContainer<Document> searchContainer;

    public DownloadResultsSearchContainer(SearchContainer<Document> searchContainer, ThemeDisplay themeDisplay, DsdParserUtils dsdParserUtils) {
        super(searchContainer.getPortletRequest(), searchContainer.getDisplayTerms(), searchContainer.getSearchTerms(),
                searchContainer.getCurParam(), searchContainer.getCur(), searchContainer.getDelta(), searchContainer.getIteratorURL(),
                searchContainer.getHeaderNames(), searchContainer.getEmptyResultsMessage(), searchContainer.getClassName());

        this.searchContainer = searchContainer;
        this.themeDisplay = themeDisplay;
        this.dsdParserUtils = dsdParserUtils;
        loadResultsFromSearchContainer();

    }

    @Override
    public List<DownloadDisplayContext> getResults() {

        final List<DownloadDisplayContext> results = super.getResults();
        super.setDelta(searchContainer.getDelta());
        super.setTotal(searchContainer.getTotal());
        return results;
    }

    private void loadResultsFromSearchContainer() {
        final List<Document> results = searchContainer.getResults();
        List<DownloadDisplayContext> displayContexts = new ArrayList<>(results.size() + 20);
        final List<Download> downloads = loadRegistrations(results);
        downloads.forEach(download -> displayContexts.add(new DownloadDisplayContext(download, themeDisplay)));
        super.setResults(displayContexts);
    }


    private List<Download> loadRegistrations(List<Document> results) {
        final ArrayList<Download> registrations = new ArrayList<>();
        for (Document result : results) {
            if (!result.getFields().containsKey("entryClassPK")) continue;
            final Field classPK = result.getField("entryClassPK");
            JournalArticle registrationArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(Long.parseLong(classPK.getValue()));
            if(registrationArticle == null){
                LOG.warn(String.format("Failed to fetch download for classPK %s", classPK.getValue()));
                continue;
            }
            try {
                final AbsDsdArticle absDsdArticle = dsdParserUtils.toDsdArticle(registrationArticle);
                if (!(absDsdArticle instanceof Download)) continue;
                registrations.add((Download) absDsdArticle);
            } catch (PortalException e) {
                //
            }
        }
        return registrations;
    }
}
