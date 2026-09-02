package nl.deltares.tableview.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.tableview.comparator.RegistrationComparator;
import nl.deltares.tableview.model.DisplayRegistration;

import java.util.*;

public class RegistrationUtils {

    public static List<DisplayRegistration> convertToDisplayValues(List<Registration> registrations, Map<Long, JournalArticle> articleCache,
                                                                   DsdJournalArticleUtils dsdJournalArticleUtils) {

        final ArrayList<DisplayRegistration> displays = new ArrayList<>(registrations.size());
        registrations.forEach(registration -> {
            final long registrationPrimaryKey = registration.getResourcePrimaryKey();

            String registrationTitle = getArticleTitleByResourcePrimaryKey(registrationPrimaryKey, articleCache, dsdJournalArticleUtils, String.valueOf(registrationPrimaryKey));

            final long eventResourcePrimaryKey = registration.getEventResourcePrimaryKey();
            String eventTitle =  getArticleTitleByResourcePrimaryKey(eventResourcePrimaryKey, articleCache, dsdJournalArticleUtils, String.valueOf(eventResourcePrimaryKey));

            final User user = UserLocalServiceUtil.fetchUser(registration.getUserId());
            final String email = user != null ? user.getEmailAddress() : String.valueOf(registration.getUserId());
            displays.add(new DisplayRegistration(registration.getRegistrationId(), registrationPrimaryKey, eventResourcePrimaryKey,
                    email, eventTitle, registrationTitle, null, registration.getStartTime(), registration.getEndTime()));
        });
        return displays;
    }

    public static String getArticleTitleByResourcePrimaryKey(long resourceId, Map<Long, JournalArticle> cache, DsdJournalArticleUtils dsdJournalArticleUtils, String defaultValue) {

        JournalArticle article = getArticleByResourcePrimaryKey(resourceId, cache, dsdJournalArticleUtils);
        if (article != null) {return article.getTitle();}
        return defaultValue;

    }
    public static JournalArticle getArticleByResourcePrimaryKey(long resourceId, Map<Long, JournalArticle> cache, DsdJournalArticleUtils dsdJournalArticleUtils) {

        JournalArticle journalArticle = cache.get(resourceId);
        if (journalArticle != null) return journalArticle;
        try {
            journalArticle = dsdJournalArticleUtils.getLatestArticle(resourceId);
            if (journalArticle != null) cache.put(resourceId, journalArticle);
            return journalArticle;
        } catch (PortalException e) {
            return null;
        }
    }

    public static Map<Long, String> doLoadEventTitles(long companyId, long siteGroupId, Map<Long, JournalArticle> cache, DsdJournalArticleUtils dsdJournalArticleUtils) {

        Map<Long, String> titles = new HashMap<>();

        List<Long> resourceIds = RegistrationLocalServiceUtil.getDistinctEventResourceIds(
                companyId, siteGroupId);
        for (Long resourceId : resourceIds) {
            String title = getArticleTitleByResourcePrimaryKey(resourceId, cache, dsdJournalArticleUtils, String.valueOf(resourceId));
            if (title != null) titles.put(resourceId, title);
        }
        return titles;
    }

    public static Map<Long, String> doLoadRegistrationTitles(long companyId, long groupId, long selectedEventResourceId,
                                                             long selectedUserId, Map<Long, JournalArticle> cache, DsdJournalArticleUtils dsdJournalArticleUtils) {

        if (selectedEventResourceId == 0) {
            return Collections.emptyMap();
        }

        Map<Long, String> titles = new HashMap<>();
        List<Long> resourceIds = RegistrationLocalServiceUtil.getDistinctRegistrationResourceIds(
                companyId, groupId, selectedEventResourceId, selectedUserId);
        for (Long resourceId : resourceIds) {
            String title = getArticleTitleByResourcePrimaryKey(resourceId, cache, dsdJournalArticleUtils, String.valueOf(resourceId));
            if (title != null) titles.put(resourceId, title);
        }
        return titles;
    }

    public static String formatJson(String json) {

        try {
            final JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);
            return jsonObject.toString(4);
        } catch (JSONException e) {
            return json;
        }

    }

    public static void sortDownloads(List<DisplayRegistration> displays, String orderByCol, String orderByType) {

        final RegistrationComparator comparator = new RegistrationComparator(orderByCol, orderByType.equals("asc"));
        displays.sort(comparator);

    }
}
