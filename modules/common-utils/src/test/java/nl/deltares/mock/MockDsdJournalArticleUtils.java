package nl.deltares.mock;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.SearchContext;
import nl.deltares.portal.utils.DsdJournalArticleUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MockDsdJournalArticleUtils implements DsdJournalArticleUtils {

    private static List<JournalArticle> articleList = new ArrayList<>();

    public void addTestResource(JournalArticle article){
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
            if (!journalArticle.getDDMStructureKey().toLowerCase().startsWith("event")) continue;
            events.add(journalArticle);
        }
        return events;
    }

    @Override
    public JournalArticle getJournalArticle(long groupId, String articleId)  {
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
    public List<JournalArticle> getRegistrationsForEvent(long companyId, long groupId, String eventArticleId, Locale locale) throws PortalException {
        return null;
    }

    @Override
    public List<JournalArticle> getRegistrationsForPeriod(long companyId, long groupId, Date startTime, Date endTime, Locale locale) throws PortalException {
        return null;
    }

    @Override
    public void contributeDsdRegistrations(long groupId, String[] structureKeys, SearchContext searchContext, Locale locale) {

    }

    @Override
    public void contributeDsdEventRegistrations(long groupId, String eventId, SearchContext searchContext, Locale locale) {

    }

    @Override
    public void contributeDsdDateRangeRegistrations(long groupId, Date startDate, Date endDate, SearchContext searchContext, Locale locale) {

    }

}
