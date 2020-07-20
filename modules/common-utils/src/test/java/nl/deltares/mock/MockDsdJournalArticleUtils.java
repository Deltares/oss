package nl.deltares.mock;

import com.liferay.journal.model.JournalArticle;
import nl.deltares.portal.utils.DsdJournalArticleUtils;

import java.util.ArrayList;
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
    public JournalArticle getJournalArticle(long resourceId) {
        for (JournalArticle journalArticle : articleList) {
            if (journalArticle.getResourcePrimKey() == resourceId) return journalArticle;
        }
        return null;
    }
}
