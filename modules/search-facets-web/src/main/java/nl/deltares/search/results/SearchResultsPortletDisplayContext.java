package nl.deltares.search.results;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.facet.FacetSelection;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.Period;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResultsPortletDisplayContext implements Serializable {

    private static final Log LOG = LogFactoryUtil.getLog(SearchResultsPortletDisplayContext.class);
    private final DsdParserUtils dsdParserUtils;
    private final ThemeDisplay themeDisplay;
    private boolean _renderNothing;
    private String _keywords;
    private int delta;
    private int totalHits = 0;
    private final List<RegistrationDisplayContext> registrations = new ArrayList<>();
    private final List<DsdArticle> dsdArticles = new ArrayList<>();
    private FacetSelection facetSelection;

    public SearchResultsPortletDisplayContext(DsdParserUtils dsdParserUtils, ThemeDisplay themeDisplay) {
        this.dsdParserUtils = dsdParserUtils;
        this.themeDisplay = themeDisplay;
    }

    public boolean isRenderNothing() {
        return _renderNothing;
    }

    public void setRenderNothing(boolean renderNothing) {
        _renderNothing = renderNothing;
    }

    public void setResultsDocuments(List<Document> documents, String type){

        if ("dsd".equals(type)){
            loadRegistrations(documents);
        } else {
            loadDownloads(documents);
        }
    }

    private void loadDownloads(List<Document> documents) {
        loadDsdArticles(documents);

    }
    private void loadRegistrations(List<Document> documents) {
        registrations.clear();
        loadDsdArticles(documents);
        splitMultiDayRegistrations(dsdArticles, registrations);
    }

    public String getKeywords() {
        return _keywords;
    }

    public void setKeywords(String _keywords) {
        this._keywords = _keywords;
    }


    public int getDelta() {
        return delta;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public List<DsdArticle> getDsdArticleResults() {
        return dsdArticles;
    }

    public List<RegistrationDisplayContext> getRegistrationResults() {
        return registrations;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    private void loadDsdArticles(List<Document> results) {
        dsdArticles.clear();
        for (Document result : results) {
            if (!result.getFields().containsKey("entryClassPK")) continue;
            final Field classPK = result.getField("entryClassPK");
            JournalArticle registrationArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(Long.parseLong(classPK.getValue()));
            if (registrationArticle == null) {
                continue;
            }
            try {
                final AbsDsdArticle absDsdArticle = dsdParserUtils.toDsdArticle(registrationArticle);
                dsdArticles.add(absDsdArticle);
            } catch (PortalException e) {
                LOG.warn("Error parsing DSD article " + registrationArticle.getTitle());
            }
        }
    }

    private void splitMultiDayRegistrations(List<DsdArticle> registrations, List<RegistrationDisplayContext> registrationDisplayContexts) {

        for (DsdArticle dsdArticle : registrations) {
            if (!(dsdArticle instanceof Registration registration)) continue;
            if (registration.isMultiDayEvent() && !registration.isShowMultipleDaysAsSingleDate()) {
                final List<Period> startAndEndTimesPerDay = registration.getStartAndEndTimesPerDay();
                for (int i = 0; i < startAndEndTimesPerDay.size(); i++) {
                    final RegistrationDisplayContext displayContext = new RegistrationDisplayContext(registration, i,
                            themeDisplay, facetSelection);
                    registrationDisplayContexts.add(displayContext);
                }
            } else {
                final RegistrationDisplayContext displayContext = new RegistrationDisplayContext(registration, 0,
                        themeDisplay, facetSelection);
                registrationDisplayContexts.add(displayContext);
            }
        }
    }

    public void setFacetSelection(FacetSelection facetSelection) {
        this.facetSelection = facetSelection;
    }

    public FacetSelection getFacetSelection() {
        return facetSelection;
    }
}
