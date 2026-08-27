package nl.deltares.tableview.portlet.portlet;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import jakarta.portlet.*;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.tableview.comparator.RegistrationComparator;
import nl.deltares.tableview.model.DisplayRegistration;
import nl.deltares.tableview.portlet.constants.TablePortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
        property = {
                "com.liferay.portlet.display-category=OSS-table",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "jakarta.portlet.display-name=RegistrationTable",
                "jakarta.portlet.init-param.template-path=/",
                "jakarta.portlet.init-param.view-template=/registrationTable.jsp",
                "jakarta.portlet.name=" + TablePortletKeys.REGISTRATIONTABLE,
                "jakarta.portlet.resource-bundle=content.Language",
                "jakarta.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class RegistrationTablePortlet extends MVCPortlet {

    @Reference
    private DsdJournalArticleUtils dsdJournalArticleUtils;


    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final int curPage = ParamUtil.getInteger(renderRequest, "cur", 1);
        final int deltas = ParamUtil.getInteger(renderRequest, "delta", 25);
        final String filterEmailValue = ParamUtil.getString(renderRequest, "filterEmailValue", null);
        final long filterEventValue = Long.parseLong(ParamUtil.getString(renderRequest, "filterEventValue", "0"));
        final long filterRegistrationValue = Long.parseLong(ParamUtil.getString(renderRequest, "filterRegistrationValue", "0"));
        final String path = ParamUtil.getString(renderRequest, "mvcPath", null);
        if (path != null && path.endsWith("editRegistration.jsp")) {
            doLoadRegistration(renderRequest);
        } else {
            doFilterValues(filterEmailValue, filterEventValue, filterRegistrationValue, curPage, deltas, renderRequest);
        }

        if (filterEmailValue != null) renderRequest.setAttribute("filterEmailValue", filterEmailValue);
        if (filterRegistrationValue > 0) renderRequest.setAttribute("filterRegistrationValue", String.valueOf(filterRegistrationValue));
        if (filterEventValue > 0) renderRequest.setAttribute("filterEventValue", String.valueOf(filterEventValue));

        super.render(renderRequest, renderResponse);
    }

    private Map<Long, String> doLoadEventTitles(ThemeDisplay themeDisplay) {

        Map<Long, String> titles = new HashMap<>();

        List<Long> resourceIds = RegistrationLocalServiceUtil.getDistinctEventResourceIds(
                themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId());
        doLoadResourceTitle(resourceIds, titles);
        return titles;
    }

    private Map<Long, String> doLoadRegistrationTitles(ThemeDisplay themeDisplay, Long selectedEventResourceId, Long selectedUserId) {

        if (selectedEventResourceId == null) {return Collections.emptyMap();}

        Map<Long, String> titles = new HashMap<>();
        List<Long> resourceIds = RegistrationLocalServiceUtil.getDistinctRegistrationResourceIds(
                themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId(), selectedEventResourceId, selectedUserId);
        doLoadResourceTitle(resourceIds, titles);
        return titles;
    }

    private void doLoadResourceTitle(List<Long> resourcePrimaryKeys, Map<Long, String> titles) {
        for (Long resourceId : resourcePrimaryKeys) {
            if (titles.containsKey(resourceId)) {continue;}
            try {
                JournalArticle journalArticle = dsdJournalArticleUtils.getLatestArticle(resourceId);
                if (journalArticle != null)  titles.put(resourceId, journalArticle.getTitle());
            } catch (PortalException e) {

                //Article no longer exists;
                titles.put(resourceId, String.valueOf(resourceId));
            }
        }
    }

    private void doLoadRegistration(RenderRequest renderRequest) {

        final String recordId = ParamUtil.getString(renderRequest, "recordId", null);
        if (recordId == null) {
            SessionErrors.add(renderRequest, "action-failed", "No registration Id provided.");
            return;
        }

        Registration registration;
        final long id = Long.parseLong(recordId);
        try {
            registration = RegistrationLocalServiceUtil.getRegistration(id);
        } catch (PortalException e) {
            SessionErrors.add(renderRequest, "action-failed", String.format("Error getting registration %s: %s", recordId, e.getMessage()));
            return;
        }

        String eventName;
        long eventResourceId;
        final JournalArticle eventArticle = getArticleByResourcePrimaryKey(registration.getEventResourcePrimaryKey(), new HashMap<>());
        if (eventArticle != null) {
            eventName = eventArticle.getTitle();
            eventResourceId = registration.getEventResourcePrimaryKey();
        } else {
            eventName = "";
            eventResourceId = registration.getEventResourcePrimaryKey();
        }
        String sessionName;
        try {
            final JournalArticle registrationArticle = dsdJournalArticleUtils.getLatestArticle(registration.getResourcePrimaryKey());
            sessionName = registrationArticle.getTitle();
        } catch (PortalException e) {
            sessionName = String.valueOf(registration.getResourcePrimaryKey());
        }
        final String email = ParamUtil.getString(renderRequest, "editEmailValue", null);
        renderRequest.setAttribute("record",
                new DisplayRegistration(id, registration.getResourcePrimaryKey(), eventResourceId, email, eventName, sessionName, formatJson(registration.getUserPreferences()),
                        registration.getStartTime(), registration.getEndTime()));
    }

    private String formatJson(String json) {

        try {
            final JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);
            return jsonObject.toString(4);
        } catch (JSONException e) {
            return json;
        }

    }

    private void doFilterValues(String filterEmailValue, long filterEventValue, long filterRegistrationValue, int curPage, int deltas, RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        Map<Long, String> eventTitles = doLoadEventTitles(themeDisplay);
        renderRequest.setAttribute("eventTitles", eventTitles);

        long userId = 0;
        if (filterEmailValue != null && !filterEmailValue.trim().isEmpty()) {
            User user = UserLocalServiceUtil.fetchUserByEmailAddress(themeDisplay.getCompanyId(), filterEmailValue);
            userId = user != null ? user.getUserId() : 0;
        }

        Map<Long, String> registrationTitles = doLoadRegistrationTitles(themeDisplay, filterEventValue, userId);
        renderRequest.setAttribute("registrationTitles", registrationTitles);

        final long siteGroupId = themeDisplay.getSiteGroupId();

        List<Registration> registrations;
        int recordCount;
        final int start = (curPage - 1) * deltas;
        final int end = curPage * deltas;

        try {
            if (userId > 0) {
                registrations = RegistrationLocalServiceUtil.getUserRegistrations(siteGroupId, userId, start, end);
                recordCount = RegistrationLocalServiceUtil.getUserRegistrationsCount(siteGroupId, userId);
            } else if (filterRegistrationValue > 0) {
                registrations = RegistrationLocalServiceUtil.getArticleRegistrations(siteGroupId, filterRegistrationValue, start, end);
                recordCount = RegistrationLocalServiceUtil.getRegistrationsCount(siteGroupId, filterRegistrationValue);
            } else if (filterEventValue > 0) {
                registrations = RegistrationLocalServiceUtil.getEventRegistrations(siteGroupId, filterEventValue, start, end);
                recordCount = RegistrationLocalServiceUtil.getEventRegistrationsCount(siteGroupId, filterEventValue);
            } else {
                registrations = Collections.emptyList();
                recordCount = 0;
            }
            List<DisplayRegistration> displays  = convertToDisplayValues(registrations, eventTitles, registrationTitles);

            String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
            String orderByType = ParamUtil.getString(renderRequest, "orderByType");
            sortDownloads(displays, orderByCol, orderByType);

            renderRequest.setAttribute("records", displays);
            renderRequest.setAttribute("total", recordCount);

        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
        }

    }

    private JournalArticle getArticleByResourcePrimaryKey(long resourceId, Map<Long, JournalArticle> cache) {

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

    private List<DisplayRegistration> convertToDisplayValues(List<Registration> registrations,
                                                             Map<Long, String> eventTitles, Map<Long, String> registrationTitles) {

        final ArrayList<DisplayRegistration> displays = new ArrayList<>(registrations.size());
        Map<Long, JournalArticle> articleCache = new HashMap<>();
        registrations.forEach(registration -> {
            final long registrationPrimaryKey = registration.getResourcePrimaryKey();

            String registrationTitle = registrationTitles.get(registrationPrimaryKey);
            if (registrationTitle == null) {
                JournalArticle registrationArticle = getArticleByResourcePrimaryKey(registrationPrimaryKey, articleCache);
                if (registrationArticle != null) {
                    registrationTitle = registrationArticle.getTitle();
                    registrationTitles.put(registrationPrimaryKey, registrationTitle);
                } else {
                    registrationTitle = String.valueOf(registrationPrimaryKey);
                }
            }

            final long eventResourcePrimaryKey = registration.getEventResourcePrimaryKey();
            String eventTitle = eventTitles.get(eventResourcePrimaryKey);
            if (eventTitle == null) {
                JournalArticle eventArticle = getArticleByResourcePrimaryKey(eventResourcePrimaryKey, articleCache);
                if (eventArticle != null) {
                    eventTitle = eventArticle.getTitle();
                    eventTitles.put(eventResourcePrimaryKey, eventTitle);
                } else {
                    eventTitle = String.valueOf(eventResourcePrimaryKey);
                }
            }
            final User user = UserLocalServiceUtil.fetchUser(registration.getUserId());
            final String email = user != null ? user.getEmailAddress() : String.valueOf(registration.getUserId());
            displays.add(new DisplayRegistration(registration.getRegistrationId(), registrationPrimaryKey, eventResourcePrimaryKey,
                    email, eventTitle, registrationTitle, null, registration.getStartTime(), registration.getEndTime()));
        });
        return displays;
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void filterEmail(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String selectedEmail = ParamUtil.getString(actionRequest, "filterEmailValue", "");

        if ( !selectedEmail.isEmpty() ) {
            RenderURL redirectURL = actionResponse.createRedirectURL(MimeResponse.Copy.ALL);
            redirectURL.getRenderParameters().setValue("filterEmailValue", selectedEmail);
            redirectURL.getRenderParameters().setValue("filterEventValue", "0");
            redirectURL.getRenderParameters().setValue("filterRegistrationValue", "0");

            try {
                actionResponse.sendRedirect(redirectURL.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void filterSelections(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String selectedEmail = ParamUtil.getString(actionRequest, "filterEmailValue", null);
        final String filterEventValue = ParamUtil.getString(actionRequest, "filterEventValue", null);
        final String filterRegistrationValue = ParamUtil.getString(actionRequest, "filterRegistrationValue", null);

        actionResponse.getRenderParameters().setValue("filterEmailValue", selectedEmail);
        actionResponse.getRenderParameters().setValue("filterEventValue", selectedEmail);
        actionResponse.getRenderParameters().setValue("filterRegistrationValue", selectedEmail);
        final String selectedEventValue = ParamUtil.getString(actionRequest, "filterEventValue", "");
        final String selectedRegistrationValue = ParamUtil.getString(actionRequest, "filterRegistrationValue", "");

        if ( !selectedEventValue.isEmpty() ) {
            RenderURL redirectURL = actionResponse.createRedirectURL(MimeResponse.Copy.ALL);
            redirectURL.getRenderParameters().setValue("filterEmailValue", "");
            redirectURL.getRenderParameters().setValue("filterEventValue", selectedEventValue);
            redirectURL.getRenderParameters().setValue("filterRegistrationValue", selectedRegistrationValue);

            try {
                actionResponse.sendRedirect(redirectURL.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Delete selected registration
     *
     * @param actionRequest  Delete action
     * @param actionResponse Delete response
     */
    @SuppressWarnings("unused")
    public void delete(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String recordId = ParamUtil.getString(actionRequest, "recordId", null);
        try {
            Registration registration = RegistrationLocalServiceUtil.deleteRegistration(Long.parseLong(recordId));
            SessionMessages.add(actionRequest, "action-success", String.format("Delete registration %s", recordId));
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "action-failed", String.format("Failed to delete registration: %s", e.getMessage()));
        }

        final String selectedEmail = ParamUtil.getString(actionRequest, "filterEmailValue", null);
        final String filterEventValue = ParamUtil.getString(actionRequest, "filterEventValue", null);
        final String filterRegistrationValue = ParamUtil.getString(actionRequest, "filterRegistrationValue", null);

        actionResponse.getRenderParameters().setValue("filterEmailValue", selectedEmail);
        actionResponse.getRenderParameters().setValue("filterRegistrationValue", selectedEmail);
        actionResponse.getRenderParameters().setValue("filterEmailValue", selectedEmail);
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void save(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String userPreferences = ParamUtil.getString(actionRequest, "preferences", null);
        final String recordId = ParamUtil.getString(actionRequest, "recordId", null);

        try {
            validate(userPreferences);
        } catch (JSONException e) {
            SessionErrors.add(actionRequest, "action-failed", "Invalid JSON content: " + e.getMessage());
            PortalUtil.copyRequestParameters(actionRequest, actionResponse);
            actionResponse.getRenderParameters().setValue("mvcPath", "/editRegistration.jsp");
            return;
        }
        try {
            final Registration registration = RegistrationLocalServiceUtil.getRegistration(Long.parseLong(recordId));
            registration.setUserPreferences(userPreferences);
            RegistrationLocalServiceUtil.updateRegistration(registration);
            SessionMessages.add(actionRequest, "action-success", String.format("Updated registration %s", recordId));
        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "action-failed", String.format("Failed to update registration %s: %s", recordId, e.getMessage()));
        }


        final String filterEmail = ParamUtil.getString(actionRequest, "filterEmail", null);
        actionResponse.getRenderParameters().setValue("filterEmail", filterEmail);

    }

    private void validate(String preferences) throws JSONException {
        if (preferences == null) return;
        JsonContentUtils.parseContent(preferences);

    }

    private void sortDownloads(List<DisplayRegistration> displays, String orderByCol, String orderByType) {

        final RegistrationComparator comparator = new RegistrationComparator(orderByCol, orderByType.equals("asc"));
        displays.sort(comparator);

    }
}