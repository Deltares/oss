package nl.deltares.tableview.portlet.portlet;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
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
import nl.deltares.tableview.model.DisplayRegistration;
import nl.deltares.tableview.portlet.constants.TablePortletKeys;
import nl.deltares.tableview.tasks.impl.DeletedSelectedRegistrationsRequest;
import nl.deltares.tableview.tasks.impl.ExportSelectedRegistrationsTableRequest;
import nl.deltares.tableview.utils.RegistrationUtils;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
                "com.liferay.portlet.header-portlet-javascript=/lib/tableview.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
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
        final String filterEmailValue = ParamUtil.getString(renderRequest, "filterEmailValue", "");
        final long filterEventValue = Long.parseLong(ParamUtil.getString(renderRequest, "filterEventValue", "0"));
        final long filterRegistrationValue = Long.parseLong(ParamUtil.getString(renderRequest, "filterRegistrationValue", "0"));
        final String path = ParamUtil.getString(renderRequest, "mvcPath", null);

        if (path != null && path.endsWith("editRegistration.jsp")) {
            doLoadRegistration(renderRequest);
        } else {
            doFilterValues(filterEmailValue, filterEventValue, filterRegistrationValue, curPage, deltas, renderRequest);
        }

        if (filterEmailValue.isEmpty()) {
            renderRequest.setAttribute("filterEmailValue", "");
            renderRequest.setAttribute("filterRegistrationValue", String.valueOf(filterRegistrationValue));
            renderRequest.setAttribute("filterEventValue", String.valueOf(filterEventValue));
        } else {
            renderRequest.setAttribute("filterEmailValue", filterEmailValue);
            renderRequest.setAttribute("filterRegistrationValue", "0");
            renderRequest.setAttribute("filterEventValue", "0");
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

        long eventResourceId = registration.getEventResourcePrimaryKey();
        String eventName = RegistrationUtils.getArticleTitleByResourcePrimaryKey(
                registration.getEventResourcePrimaryKey(), new HashMap<>(), dsdJournalArticleUtils, String.valueOf(eventResourceId));

        String sessionName = RegistrationUtils.getArticleTitleByResourcePrimaryKey(
                registration.getResourcePrimaryKey(), new HashMap<>(), dsdJournalArticleUtils, String.valueOf(registration.getResourcePrimaryKey()));

        final String email = ParamUtil.getString(renderRequest, "editEmailValue", null);
        renderRequest.setAttribute("record",
                new DisplayRegistration(id, registration.getResourcePrimaryKey(), eventResourceId, email, eventName, sessionName,
                        RegistrationUtils.formatJson(registration.getUserPreferences()),
                        registration.getStartTime(), registration.getEndTime()));
    }

    private void doFilterValues(String filterEmailValue, long filterEventValue, long filterRegistrationValue, int curPage, int deltas, RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        Map<Long, JournalArticle> articleCache = new HashMap<>();
        Map<Long, String> eventTitles = RegistrationUtils.doLoadEventTitles(themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId(),
                articleCache, dsdJournalArticleUtils);
        renderRequest.setAttribute("eventTitles", eventTitles);

        long userId = 0;
        if (filterEmailValue != null && !filterEmailValue.trim().isEmpty()) {
            User user = UserLocalServiceUtil.fetchUserByEmailAddress(themeDisplay.getCompanyId(), filterEmailValue);
            userId = user != null ? user.getUserId() : 0;
        }

        Map<Long, String> registrationTitles = RegistrationUtils.doLoadRegistrationTitles(themeDisplay.getCompanyId(),
                themeDisplay.getSiteGroupId(), filterEventValue, userId, articleCache, dsdJournalArticleUtils);

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
            List<DisplayRegistration> displays = RegistrationUtils.convertToDisplayValues(registrations, articleCache
                    , dsdJournalArticleUtils);

            String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
            String orderByType = ParamUtil.getString(renderRequest, "orderByType");
            RegistrationUtils.sortDownloads(displays, orderByCol, orderByType);

            renderRequest.setAttribute("records", displays);
            renderRequest.setAttribute("total", recordCount);

        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
        }

    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        if (!themeDisplay.isSignedIn() || !request.isUserInRole("administrator")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized request!");
            return;
        }
        String action = ParamUtil.getString(request, "action");
        String id = ParamUtil.getString(request, "id", null);
        final String filterEmailValue = ParamUtil.getString(request, "filterEmailValue", "");
        final long filterEventValue = Long.parseLong(ParamUtil.getString(request, "filterEventValue", "0"));
        final long filterRegistrationValue = Long.parseLong(ParamUtil.getString(request, "filterRegistrationValue", "0"));

        if ("export".equals(action)) {
            if (id == null) {
                id = RegistrationTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            exportTable(id, filterEmailValue, filterEventValue, filterRegistrationValue, response, themeDisplay);
        } else if ("delete-selected".equals(action)) {
            if (id == null) {
                id = RegistrationTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            deletedSelected(id, request, response, themeDisplay);

        } else if ("updateStatus".equals(action)) {
            DataRequestManager.getInstance().updateStatus(id, response);
        } else if ("downloadLog".equals(action)) {
            DataRequestManager.getInstance().downloadDataFile(id, response);
        } else {
            DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, response);
        }
        super.serveResource(request, response);

    }

    private void deletedSelected(String dataRequestId, ResourceRequest request, ResourceResponse response, ThemeDisplay themeDisplay) throws IOException {

        final HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
        final String[] selectedIds = httpReq.getParameterValues("selection");

        if (selectedIds.length == 0) {
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setContentType("text/csv");
            DataRequestManager instance = DataRequestManager.getInstance();
            DataRequest dataRequest = instance.getDataRequest(dataRequestId);
            if (dataRequest == null) {
                dataRequest = new DeletedSelectedRegistrationsRequest(dataRequestId, Arrays.asList(selectedIds), themeDisplay.getUserId(), dsdJournalArticleUtils);
                instance.addToQueue(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.TERMINATED || dataRequest.getStatus() == DataRequest.STATUS.NODATA) {
                instance.removeDataRequest(dataRequest);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            String statusMessage = dataRequest.getStatusMessage();
            response.setContentLength(statusMessage.length());
            PrintWriter writer = response.getWriter();
            writer.println(statusMessage);

        }
    }


    private void exportTable(String dataRequestId, String filterEmailValue, long filterEventValue, long filterRegistrationValue,
                             ResourceResponse response, ThemeDisplay themeDisplay) throws IOException {
        response.setContentType("text/csv");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new ExportSelectedRegistrationsTableRequest(dataRequestId, filterEmailValue, filterEventValue,
                    filterRegistrationValue, themeDisplay, dsdJournalArticleUtils);
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.TERMINATED || dataRequest.getStatus() == DataRequest.STATUS.NODATA) {
            instance.removeDataRequest(dataRequest);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        response.setContentLength(statusMessage.length());
        PrintWriter writer = response.getWriter();
        writer.println(statusMessage);

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

        if (!selectedEmail.isEmpty()) {
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

        final String selectedEventValue = ParamUtil.getString(actionRequest, "filterEventValue", "");
        final String selectedRegistrationValue = ParamUtil.getString(actionRequest, "filterRegistrationValue", "");

        if (!selectedEventValue.isEmpty()) {
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


}