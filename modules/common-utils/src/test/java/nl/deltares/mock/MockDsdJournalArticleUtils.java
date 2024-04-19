package nl.deltares.mock;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.SearchContext;
import nl.deltares.portal.utils.DsdJournalArticleUtils;

import java.util.*;

public class MockDsdJournalArticleUtils implements DsdJournalArticleUtils {

    private static final List<JournalArticle> articleList = new ArrayList<>();

    public void addTestResource(JournalArticle article) {
        articleList.add(article);
    }

    public JournalArticle getLatestArticle(long resourcePrimKey) {

        for (JournalArticle journalArticle : articleList) {
            if (journalArticle.getResourcePrimKey() == resourcePrimKey) return journalArticle;
        }
        return null;
    }

    @Override
    public List<JournalArticle> getEvents(long groupId, Locale locale) {
        ArrayList<JournalArticle> events = new ArrayList<>();
        for (JournalArticle journalArticle : articleList) {
            if (journalArticle.getGroupId() != groupId) continue;
            if (!journalArticle.getDDMStructure().getStructureKey().toLowerCase().startsWith("event")) continue;
            events.add(journalArticle);
        }
        return events;
    }

    @Override
    public JournalArticle getJournalArticle(long groupId, String articleId) {
        for (JournalArticle journalArticle : articleList) {
            if (journalArticle.getGroupId() != groupId) continue;
            if (journalArticle.getArticleId().equals(articleId)) return journalArticle;
        }
        return null;
    }

    @Override
    public List<JournalArticle> getRegistrations(long companyId, long groupId, String[] structureKeys, Locale locale) throws PortalException {
        return null;
    }

    @Override
    public List<JournalArticle> getRegistrationsForEvent(long companyId, long groupId, String eventArticleId, String[] structureKeys, Locale locale) throws PortalException {
        return null;
    }


    @Override
    public List<JournalArticle> getRegistrationsForPeriod(long companyId, long groupId, Date startTime, Date endTime, String[] structureKeys, String dateFieldName, Locale locale) throws PortalException {
        return null;
    }

    @Override
    public void queryMultipleFieldValues(long groupId, String[] structureKeys, SearchContext searchContext, Locale locale) {

    }

    @Override
    public void queryDdmFieldValue(long groupId, String ddmFieldName, String ddmFieldValue, String[] structureKeys, SearchContext searchContext, Locale locale) {

    }

    @Override
    public void queryDdmFieldValues(long groupId, String ddmFieldName, String[] ddmFieldValues, String[] structureKeys, SearchContext searchContext, Locale locale) {

    }

    @Override
    public void queryExcludeDdmFieldValue(long groupId, String ddmFieldName, String ddmFieldValue, String[] structureKeys, SearchContext searchContext, Locale locale) {

    }

    @Override
    public void queryDateRange(long groupId, Date startDate, Date endDate, String[] structureKeys, String dateFieldName, SearchContext searchContext, Locale locale) {

    }


    @Override
    public Map<String, String> getStructureFieldOptions(long groupId, String structureName, String optionsField, Locale locale) throws PortalException {
        return null;
    }

}
