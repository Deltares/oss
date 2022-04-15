package nl.deltares.tableview.portlet.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.tableview.portlet.constants.DownloadTablePortletKeys;
import nl.deltares.tableview.tasks.impl.DeletedSelectedDownloadsRequest;
import nl.deltares.tableview.tasks.impl.ExportTableRequest;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS-table",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/tableview.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=DownloadTable",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/downloadsTable.jsp",
                "javax.portlet.name=" + DownloadTablePortletKeys.DOWNLOADTABLE,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DownloadTablePortlet extends MVCPortlet {

    final static String datePattern = "yyy-MM-dd";
    final static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final int cur = ParamUtil.getInteger(renderRequest, "cur", 0);
        final int deltas = ParamUtil.getInteger(renderRequest, "delta", 50);
        final String filterId = ParamUtil.getString(renderRequest, "filterId", "none");

        doFilterValues(filterId, cur, deltas, renderRequest);

        super.render(renderRequest, renderResponse);
    }

    private void doFilterValues(String filterId, int cur, int deltas, RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        final long siteGroupId = themeDisplay.getSiteGroupId();
        final List<Download> downloads;
        final int downloadsCount;
        final int end = cur + deltas;
        try {
            switch (filterId) {
                case "pendingpayment":
                    downloads = DownloadLocalServiceUtil.findDownloadsByShareId(siteGroupId, -1, cur, end);
                    downloadsCount = DownloadLocalServiceUtil.countDownloadsByShareId(siteGroupId, -1);
                    break;
                case "processing":
                    downloads = DownloadLocalServiceUtil.findDownloadsByShareId(siteGroupId, -9, cur, end);
                    downloadsCount = DownloadLocalServiceUtil.countDownloadsByShareId(siteGroupId, -9);
                    break;
                case "direct":
                    downloads = DownloadLocalServiceUtil.findDirectDownloads(siteGroupId, cur, end);
                    downloadsCount = DownloadLocalServiceUtil.countDirectDownloads(siteGroupId);
                    break;
                default:
                    downloads = DownloadLocalServiceUtil.getDownloads(cur, end);
                    downloadsCount = DownloadLocalServiceUtil.getDownloadsCount();
            }
            renderRequest.setAttribute("records", downloads);
            renderRequest.setAttribute("total", downloadsCount);
        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
        }
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    public void filter(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String filter = ParamUtil.getString(actionRequest, "filterSelection", "none");
        actionResponse.getRenderParameters().setValue("filterId", filter);
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
        String filterId = ParamUtil.getString(request, "filterId", "none");

        if ("export".equals(action)) {
            if (id == null) {
                id = DownloadTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            exportTable(id, filterId, response, themeDisplay);
        } else if ("delete-selected".equals(action)) {
            if (id == null) {
                id = DownloadTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            deletedSelected(id, request, response, themeDisplay);
        } else if ("updateStatus".equals(action)) {
            DataRequestManager.getInstance().updateStatus(id, response);
        } else if ("downloadLog".equals(action)) {
            DataRequestManager.getInstance().downloadDataFile(id, response);
        } else {
            DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, response);
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

    private void exportTable(String dataRequestId, String filterId, ResourceResponse response, ThemeDisplay themeDisplay) throws IOException {
        response.setContentType("text/csv");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new ExportTableRequest(dataRequestId, filterId, themeDisplay.getUserId(), themeDisplay.getSiteGroup());
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