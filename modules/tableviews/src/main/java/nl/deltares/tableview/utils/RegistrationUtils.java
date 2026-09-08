package nl.deltares.tableview.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.tableview.comparator.RegistrationComparator;
import nl.deltares.tableview.model.DisplayRegistration;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class RegistrationUtils {

    public static String getEmail(long userId) {
        final User user = UserLocalServiceUtil.fetchUser(userId);
        if (user == null){
            return String.valueOf(userId);
        } else {
            return user.getEmailAddress();
        }
    }

    public static List<Registration> getRegistrationsForFilterSelection(long groupId, long filterUserId, long filterEventId,
                                                                        long filterRegistrationId, int start, int end) {
        if (filterUserId > 0) {
            return RegistrationLocalServiceUtil.getUserRegistrations(groupId, filterUserId, start, end);
        } else if (filterRegistrationId > 0) {
            return RegistrationLocalServiceUtil.getArticleRegistrations(groupId, filterRegistrationId, start, end);
        } else if (filterEventId > 0) {
            return RegistrationLocalServiceUtil.getEventRegistrations(groupId, filterEventId, start, end);
        } else {
            return Collections.emptyList();
        }
    }

    public static List<Download> getDownloadsForFilterSelection(long groupId, String filterValue, long filterUserId,
                                                                boolean filterEmptyFileName, int start, int end) {
        if (filterUserId > 0) {
            return DownloadLocalServiceUtil.findDownloadsByUserId(groupId, filterUserId, start, end, "createDate", "asc");
        } else if(filterEmptyFileName) {
            return DownloadLocalServiceUtil.findDownloadsWithEmptyFileName(groupId, start, end, "createDate", "asc");
        } else {
            return DownloadLocalServiceUtil.findDownloadsByFileName(groupId, filterValue, start, end, "createDate", "asc");
        }
    }

    public static int getTotalRegistrationsCountForFilterSelection(Group group, long filterUserId, long filterEventId,
                                                                   long filterRegistrationId) {
        if (filterUserId > 0) {
            return RegistrationLocalServiceUtil.getUserRegistrationsCount(group.getGroupId(), filterUserId);
        } else if(filterRegistrationId > 0) {
            return RegistrationLocalServiceUtil.getRegistrationsCount(group.getGroupId(), filterRegistrationId);
        } else if (filterEventId > 0) {
            return RegistrationLocalServiceUtil.getEventRegistrationsCount(group.getGroupId(), filterEventId);
        }
        return 0;
    }


    public static int getTotalDownloadsCountForFilterSelection(Group group, String filterValue, boolean filterEmptyFileName, long filterUserId) {
        if (filterUserId > 0) {
            return DownloadLocalServiceUtil.countDownloadsByUserId(group.getGroupId(), filterUserId);
        } else if (filterEmptyFileName) {
            return DownloadLocalServiceUtil.countDownloadsWithEmptyFileName(group.getGroupId());
        } else {
            if (filterValue != null) {
                return DownloadLocalServiceUtil.countDownloadsByFileName(group.getGroupId(), filterValue);
            }
        }
        return 0;
    }

    public static boolean isUnAuthorizied(PortletRequest request, PortletResponse response) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);

        boolean administrator = themeDisplay.isSignedIn() && request.isUserInRole("administrator");
        if (!administrator && response instanceof ResourceResponse) {
            ((ResourceResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                ((ResourceResponse)response).getWriter().println("Unauthorized request!");
            } catch (IOException e) {
                //ignore
            }
        }
        return !administrator;
    }

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

    public static String getTaskId(ResourceRequest request, ThemeDisplay themeDisplay, Class clazz) {
        String id = ParamUtil.getString(request, "id", null);
        if (id == null) {
            id = clazz.getName() + themeDisplay.getUserId();
        }
        return id;
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
