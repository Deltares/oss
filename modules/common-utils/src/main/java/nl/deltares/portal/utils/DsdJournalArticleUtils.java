package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface DsdJournalArticleUtils {
    JournalArticle getLatestArticle(long classPK) throws PortalException;

    List<JournalArticle> getEvents(long groupId, Locale locale) throws PortalException;

    JournalArticle getJournalArticle(long groupId, String articleId) throws PortalException;

    List<JournalArticle> getRegistrations(long companyId, long groupId, String[] structureKeys, Locale locale) throws PortalException;

    List<JournalArticle> getRegistrationsForEvent(long companyId, long groupId, String eventArticleId, Locale locale) throws PortalException;

    List<JournalArticle> getRegistrationsForPeriod(long companyId, long groupId, Date startTime, Date endTime, Locale locale) throws PortalException;

    void contributeDsdRegistrations(long groupId, String[] structureKeys, SearchContext searchContext, Locale locale);

    void contributeDsdEventRegistrations(long groupId, String eventId, SearchContext searchContext, Locale locale);

    void contributeDsdDateRangeRegistrations(long groupId, Date startDate, Date endDate,  SearchContext searchContext, Locale locale);
}
