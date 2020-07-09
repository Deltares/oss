package nl.deltares.mock;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.JournalArticleServiceUtils;

import java.util.ArrayList;
import java.util.List;

public class MockJournalArticleLocalServiceUtil implements JournalArticleServiceUtils {

    private static List<JournalArticle> articleList = new ArrayList<>();

    public void addTestResource(JournalArticle article){
        articleList.add(article);
    }

    public JournalArticle getLatestArticle(long resourcePrimKey) throws PortalException {

        for (JournalArticle journalArticle : articleList) {
            if (journalArticle.getResourcePrimKey() == resourcePrimKey) return journalArticle;
        }
        return null;
    }
}
