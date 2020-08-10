package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Locale;

public interface DsdJournalArticleUtils {
    JournalArticle getLatestArticle(long classPK) throws PortalException;

    List<JournalArticle> getEvents(long groupId, Locale locale) throws PortalException;

    JournalArticle getJournalArticle(long groupId, String articleId) throws PortalException;

    JournalArticle getJournalArticle(long resourceId) throws PortalException;
}
