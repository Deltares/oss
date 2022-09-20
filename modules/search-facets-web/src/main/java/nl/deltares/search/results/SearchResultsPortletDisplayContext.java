package nl.deltares.search.results;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.kernel.util.comparator.DsdArticleComparator;
import nl.deltares.portal.kernel.util.comparator.RegistrationDisplayContextComparator;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.Period;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultsPortletDisplayContext implements Serializable {

    private final DsdParserUtils dsdParserUtils;
    private final ThemeDisplay themeDisplay;
    private boolean _renderNothing;
    private String _keywords;
    private int delta;
    private int totalHits = 0;
    private int paginationStart;
    private List<RegistrationDisplayContext> registrations = Collections.emptyList();
    private List<DsdArticle> dsdArticles = Collections.emptyList();

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
        } else if ("download".equals(type)){
            loadDownloads(documents);
        }

    }

    private void loadDownloads(List<Document> documents) {
        dsdArticles = loadDsdArticles(documents);
        dsdArticles.sort(new DsdArticleComparator());
        totalHits = dsdArticles.size();

    }
    private void loadRegistrations(List<Document> documents) {
        registrations = new ArrayList<>(documents.size() + 20);
        splitMultiDayRegistrations(loadDsdArticles(documents), registrations);
        registrations.sort(new RegistrationDisplayContextComparator());
        totalHits = registrations.size();
    }

    private int getEndIndex() {
        return Math.min(paginationStart + delta, totalHits);
    }

    private int getStartIndex() {
        return paginationStart >= totalHits ? 0 : paginationStart;
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
        return dsdArticles.subList(getStartIndex(), getEndIndex());
    }

    public List<RegistrationDisplayContext> getRegistrationResults() {
        return registrations.subList(getStartIndex(), getEndIndex());
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setPaginationStart(int paginationStart) {
        this.paginationStart = paginationStart;
    }

    private List<DsdArticle> loadDsdArticles(List<Document> results) {
        final ArrayList<DsdArticle> articles = new ArrayList<>();
        for (Document result : results) {
            if (!result.getFields().containsKey("entryClassPK")) continue;
            final Field classPK = result.getField("entryClassPK");
            JournalArticle registrationArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(Long.parseLong(classPK.getValue()));
            if (registrationArticle == null) {
                continue;
            }
            try {
                final AbsDsdArticle absDsdArticle = dsdParserUtils.toDsdArticle(registrationArticle);
                articles.add(absDsdArticle);
            } catch (PortalException e) {
                //
            }
        }
        return articles;
    }

    private void splitMultiDayRegistrations(List<DsdArticle> registrations, List<RegistrationDisplayContext> registrationDisplayContexts) {

        for (DsdArticle dsdArticle : registrations) {
            if (! (dsdArticle instanceof Registration) ) continue;
            Registration registration = (Registration) dsdArticle;
            if (registration.isMultiDayEvent()) {
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

}
