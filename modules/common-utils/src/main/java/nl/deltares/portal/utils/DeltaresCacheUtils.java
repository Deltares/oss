package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.model.impl.AbsDsdArticle;

import java.util.Map;

public interface DeltaresCacheUtils {
    AbsDsdArticle findArticle(JournalArticle article);

    String getPortletConfigCacheId(ThemeDisplay themeDisplay);
    Map<String, Object> findPortletConfig(String portletId);

    long getCacheSize();

    void putArticle(JournalArticle journalArticle, AbsDsdArticle article);

    void putPortletConfig(String portletId, Map<String, Object> config);
    void clearCache();
}
