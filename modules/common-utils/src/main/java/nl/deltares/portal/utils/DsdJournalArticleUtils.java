package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface DsdJournalArticleUtils {

    List<JournalArticle> getRelatedArticles(long groupId, String[] articleIds) throws PortalException;

    JournalArticle getLatestArticle(long classPK) throws PortalException;

    List<JournalArticle> getEvents(long groupId, Locale locale) throws PortalException;

    JournalArticle getJournalArticle(long groupId, String articleId) throws PortalException;

    List<JournalArticle> getRegistrations(long companyId, long groupId, String[] structureKeys, Locale locale) throws PortalException;

    List<JournalArticle> getRegistrationsForEvent(long companyId, long groupId, String eventArticleId, String[] structureKeys, Locale locale) throws PortalException;

    List<JournalArticle> getRegistrationsForPeriod(long companyId, long groupId, Date startTime, Date endTime,
                                                   String[] structureKeys, String dateFieldName, Locale locale) throws PortalException;

    void addDefaultFacets(PortletSharedSearchSettings portletSharedSearchSettings);

    void addTermFacet(PortletSharedSearchSettings portletSharedSearchSettings, String termName, String termValue,
                      boolean exclude, boolean wildCard) throws PortalException;

    void addTermsFacet(PortletSharedSearchSettings portletSharedSearchSettings, String termName, String[] termValues, boolean exclude) throws PortalException;

    void addCompanyIndexers(PortletSharedSearchSettings portletSharedSearchSettings, String[] companyIds);

    void queryMultipleFieldValues(long groupId, String[] structureKeys, SearchContext searchContext, Locale locale);

    void queryDdmFieldValue(long groupId, String ddmFieldName, String ddmFieldValue, String[] structureKeys, SearchContext searchContext, Locale locale);

    void queryDdmFieldValues(long groupId, String ddmFieldName, String[] ddmFieldValues, String[] structureKeys, SearchContext searchContext, Locale locale);

    void queryExcludeDdmFieldValue(long groupId, String ddmFieldName, String ddmFieldValue, String[] structureKeys, SearchContext searchContext, Locale locale);

    void queryDateRange(long groupId, Date startDate, Date endDate, String[] structureKeys, String dateFieldName, SearchContext searchContext, Locale locale);

    Map<String, String> getPicklistFieldOptions(long groupId, String picklistExternalIdentifier, Locale locale, User user) throws PortalException;

    Map<String, String> getStructureFieldOptions(long groupId, String structureName, String optionsField, Locale locale) throws PortalException;

    void addSortTerm(PortletSharedSearchSettings portletSharedSearchSettings, String sortField, SortOrder sortOrder);

    void addDDMFieldSortTerm(PortletSharedSearchSettings portletSharedSearchSettings, String path, String sortByFieldValue, Locale locale, SortOrder sortOrder);

}
