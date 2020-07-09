package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

public interface JournalArticleServiceUtils {
    JournalArticle getLatestArticle(long classPK) throws PortalException;
}
