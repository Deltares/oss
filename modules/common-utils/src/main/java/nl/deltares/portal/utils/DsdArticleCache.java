package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import nl.deltares.portal.model.impl.AbsDsdArticle;

public interface DsdArticleCache {
    AbsDsdArticle findArticle(JournalArticle article);

    long getCacheSize();

    void putArticle(JournalArticle journalArticle, AbsDsdArticle article);

    void clearCache();
}
