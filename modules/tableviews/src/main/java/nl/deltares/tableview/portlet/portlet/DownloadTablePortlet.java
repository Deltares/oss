package nl.deltares.tableview.portlet.portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.tableview.model.DisplayDownload;
import nl.deltares.tableview.portlet.constants.TablePortletKeys;
import nl.deltares.tableview.tasks.impl.DeletedSelectedDownloadsRequest;
import nl.deltares.tableview.tasks.impl.ExportTableRequest;
import nl.deltares.tableview.tasks.impl.PaidSelectedDownloadsRequest;
import nl.deltares.tableview.tasks.impl.UpdateDownloadStatusRequest;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS-table",
                "com.liferay.portlet.header-portlet-javascript=/lib/downloadtableview.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=DownloadTable",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/downloadsTable.jsp",
                "javax.portlet.name=" + TablePortletKeys.DOWNLOADTABLE,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DownloadTablePortlet extends MVCPortlet {

    @Reference
    private DownloadUtils downloadUtils;

    final static String datePattern = "yyy-MM-dd";
    final static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final int cur = ParamUtil.getInteger(renderRequest, "cur", 0);
        final int deltas = ParamUtil.getInteger(renderRequest, "delta", 50);
        final String email = ParamUtil.getString(renderRequest, "filterEmail", null);

        doFilterValues(email, cur, deltas, renderRequest);

        super.render(renderRequest, renderResponse);
    }

    private void doFilterValues(String email, int cur, int deltas, RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        final long siteGroupId = themeDisplay.getSiteGroupId();
        final List<Download> downloads;
        final int downloadsCount;
        final int end = cur + deltas;
        try {
            if (email != null && email.trim().length() > 0) {
                User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), email);
                downloads = DownloadLocalServiceUtil.findDownloadsByUserId(siteGroupId, user.getUserId(), cur, end);
                downloadsCount = DownloadLocalServiceUtil.countDownloadsByUserId(siteGroupId, user.getUserId());
            } else {
                downloads = DownloadLocalServiceUtil.findDownloads(siteGroupId, cur, end);
                downloadsCount = DownloadLocalServiceUtil.countDownloads(siteGroupId);
            }

            renderRequest.setAttribute("records", convertToDisplayDownloads(downloads));
            renderRequest.setAttribute("total", downloadsCount);
        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
            renderRequest.setAttribute("records", Collections.emptyList());
            renderRequest.setAttribute("total", 0);
        }
    }

    private List<DisplayDownload> convertToDisplayDownloads(List<Download> downloads) {
        final ArrayList<DisplayDownload> displays = new ArrayList<>(downloads.size());
        downloads.forEach(download -> displays.add(new DisplayDownload(download)));

        displays.sort(DisplayDownload::compareTo);
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

        final String filter = ParamUtil.getString(actionRequest, "filterEmail", "none");
        actionResponse.getRenderParameters().setValue("filterEmail", filter);
    }

    /**
     * Get latest share information from cloud and update local database
     *
     * @param actionRequest  Update action
     * @param actionResponse Update response
     */
    @SuppressWarnings("unused")
    public void updateShares(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
                .getAttribute(WebKeys.THEME_DISPLAY);
        if (!themeDisplay.isSignedIn() || !actionRequest.isUserInRole("administrator")) {
            SessionErrors.add(actionRequest, "action-failed", "You are not authorized to perform this action.");
            return;
        }
        final long siteGroupId = themeDisplay.getSiteGroupId();
        final String id = String.format("UpdateDownloadStatusRequest_%d_%d", themeDisplay.getCompanyId(), siteGroupId);
        final UpdateDownloadStatusRequest updateRequest =
                new UpdateDownloadStatusRequest(id, null, siteGroupId, themeDisplay.getUser().getUserId(), downloadUtils);
        DataRequestManager.getInstance().addToQueue(updateRequest);

        final String selectedEmail = ParamUtil.getString(actionRequest, "filterEmail", null);
        actionResponse.getRenderParameters().setValue("filterEmail", selectedEmail);
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException {

        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        if (!themeDisplay.isSignedIn() || !request.isUserInRole("administrator")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized request!");
            return;
        }
        String action = ParamUtil.getString(request, "action");
        String id = ParamUtil.getString(request, "id", null);
        String email = ParamUtil.getString(request, "filterEmail", null);

        if ("export".equals(action)) {
            if (id == null) {
                id = DownloadTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            exportTable(id, email, response, themeDisplay);
        } else if ("delete-selected".equals(action)) {
            if (id == null) {
                id = DownloadTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            deletedSelected(id, request, response, themeDisplay);

        } else if ("paid-selected".equals(action)) {
            if (id == null) {
                id = DownloadTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            paidSelected(id, request, response, themeDisplay);
        } else if ("updateStatus".equals(action)) {
            DataRequestManager.getInstance().updateStatus(id, response);
        } else if ("downloadLog".equals(action)) {
            DataRequestManager.getInstance().downloadDataFile(id, response);
        } else {
            DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, response);
        }

    }

    private void paidSelected(String dataRequestId, ResourceRequest request, ResourceResponse response, ThemeDisplay themeDisplay) throws IOException {

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
                dataRequest = new PaidSelectedDownloadsRequest(dataRequestId, themeDisplay.getUserId(), Arrays.asList(selectedIds));
                instance.addToQueue(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
                instance.removeDataRequest(dataRequest);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            String statusMessage = dataRequest.getStatusMessage();
            response.setContentLength(statusMessage.length());
            PrintWriter writer = response.getWriter();
            writer.println(statusMessage);

        }
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
                dataRequest = new DeletedSelectedDownloadsRequest(dataRequestId, themeDisplay.getUserId(), Arrays.asList(selectedIds));
                instance.addToQueue(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
                instance.removeDataRequest(dataRequest);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            String statusMessage = dataRequest.getStatusMessage();
            response.setContentLength(statusMessage.length());
            PrintWriter writer = response.getWriter();
            writer.println(statusMessage);

        }
    }


    private void exportTable(String dataRequestId, String filterEmail, ResourceResponse response, ThemeDisplay themeDisplay) throws IOException {
        response.setContentType("text/csv");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new ExportTableRequest(dataRequestId, filterEmail, themeDisplay.getUserId(), themeDisplay.getSiteGroup());
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
            instance.removeDataRequest(dataRequest);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        response.setContentLength(statusMessage.length());
        PrintWriter writer = response.getWriter();
        writer.println(statusMessage);

    }
}