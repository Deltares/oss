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
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.tableview.comparator.RegistrationComparator;
import nl.deltares.tableview.model.DisplayRegistration;
import nl.deltares.tableview.portlet.constants.TablePortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS-table",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=RegistrationTable",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/registrationTable.jsp",
                "javax.portlet.name=" + TablePortletKeys.REGISTRATIONTABLE,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
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
        final String filterValue = ParamUtil.getString(renderRequest, "filterValue", null);
        final String filterSelection = ParamUtil.getString(renderRequest, "filterSelection", null);

        final String path = ParamUtil.getString(renderRequest, "mvcPath", null);
        if (path != null && path.endsWith("editRegistration.jsp")) {
            doLoadRegistration(renderRequest);
        } else {
            doFilterValues(filterValue, filterSelection, curPage, deltas, renderRequest);
        }
        super.render(renderRequest, renderResponse);
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
        final String email = ParamUtil.getString(renderRequest, "filterEmail", null);

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

    private void doFilterValues(String filterValue, String filterSelection, int curPage, int deltas, RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        final long siteGroupId = themeDisplay.getSiteGroupId();


        List<Registration> registrations = null;
        int recordCount = 0;
        final int start = (curPage - 1) * deltas;
        final int end = curPage * deltas;

        try {
            if (filterValue != null && !filterValue.trim().isEmpty()) {
                switch (filterSelection) {
                    case "email":
                        User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), filterValue);
                        registrations = RegistrationLocalServiceUtil.getUserRegistrations(siteGroupId, user.getUserId(), start, end);
                        recordCount = RegistrationLocalServiceUtil.getUserRegistrationsCount(siteGroupId, user.getUserId());
                        break;
                    case "resourceid": {
                        final long articleResourceId = Long.parseLong(filterValue);
                        registrations = RegistrationLocalServiceUtil.getArticleRegistrations(siteGroupId, articleResourceId, start, end);
                        recordCount = RegistrationLocalServiceUtil.getRegistrationsCount(siteGroupId, articleResourceId);
                        break;
                    }
                    case "eventid": {
                        final long eventResourceId = Long.parseLong(filterValue);
                        registrations = RegistrationLocalServiceUtil.getEventRegistrations(siteGroupId, eventResourceId, start, end);
                        recordCount = RegistrationLocalServiceUtil.getEventRegistrationsCount(siteGroupId, eventResourceId);
                        break;
                    }
                }
            }
            if (registrations == null) {
                registrations = RegistrationLocalServiceUtil.getRegistrations(start, end);
                recordCount = RegistrationLocalServiceUtil.getRegistrationsCount();
            }
            String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
            String orderByType = ParamUtil.getString(renderRequest, "orderByType");
            final List<DisplayRegistration> displays = convertToDisplayRegistrations(registrations);
            sortDownloads(displays, orderByCol, orderByType);
            renderRequest.setAttribute("records", displays);
            renderRequest.setAttribute("total", recordCount);
            renderRequest.setAttribute("filterValue", filterValue);
            renderRequest.setAttribute("filterSelection", filterSelection);

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

    private List<DisplayRegistration> convertToDisplayRegistrations(List<Registration> registrations) {

        final ArrayList<DisplayRegistration> displays = new ArrayList<>(registrations.size());
        Map<Long, JournalArticle> articleCache = new HashMap<>();
        registrations.forEach(registration -> {
            String registrationTitle;
            String eventTitle;

            final long registrationPrimaryKey = registration.getResourcePrimaryKey();
            JournalArticle registrationArticle = getArticleByResourcePrimaryKey(registrationPrimaryKey, articleCache);
            registrationTitle = registrationArticle != null ? registrationArticle.getTitle() : "";
            final long eventResourcePrimaryKey = registration.getEventResourcePrimaryKey();
            JournalArticle eventArticle = getArticleByResourcePrimaryKey(eventResourcePrimaryKey, articleCache);
            eventTitle = eventArticle != null ? eventArticle.getTitle() : "";
            final User user = UserLocalServiceUtil.fetchUser(registration.getUserId());
            final String email = user != null ? user.getEmailAddress() : "";
            displays.add(new DisplayRegistration(registration.getRegistrationId(), registrationPrimaryKey, eventResourcePrimaryKey,
                    email, eventTitle, registrationTitle, null, registration.getStartTime(), registration.getEndTime()));
        });

        displays.sort(DisplayRegistration::compareTo);
        return displays;
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void filter(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String selectedEmail = ParamUtil.getString(actionRequest, "filterEmail", null);
        actionResponse.getRenderParameters().setValue("filterEmail", selectedEmail);
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
            RegistrationLocalServiceUtil.deleteRegistration(Long.parseLong(recordId));
            SessionMessages.add(actionRequest, "action-success", String.format("Delete registration %s", recordId));
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "action-failed", String.format("Failed to delete registration: %s", e.getMessage()));
        }

        final String selectedEmail = ParamUtil.getString(actionRequest, "filterEmail", null);
        actionResponse.getRenderParameters().setValue("filterEmail", selectedEmail);
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