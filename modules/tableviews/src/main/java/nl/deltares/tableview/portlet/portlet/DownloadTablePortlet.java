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
import nl.deltares.tableview.model.DisplayDownload;
import nl.deltares.tableview.portlet.constants.TablePortletKeys;
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
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
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

    final static String datePattern = "yyy-MM-dd";
    final static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final int curPage = ParamUtil.getInteger(renderRequest, "cur", 1);
        final int deltas = ParamUtil.getInteger(renderRequest, "delta", 25);
        final String filterValue = ParamUtil.getString(renderRequest, "filterValue", null);
        final String filterSelection = ParamUtil.getString(renderRequest, "filterSelection", null);

        doFilterValues(filterValue, filterSelection, curPage, deltas, renderRequest);

        super.render(renderRequest, renderResponse);
    }

    private void doFilterValues(String filterValue, String filterSelection, int curPage, int deltas, RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        final long siteGroupId = themeDisplay.getSiteGroupId();
        List<Download> downloads = null;
        int downloadsCount = 0;
        final int start = (curPage - 1) * deltas;
        final int end = curPage * deltas;
        try {
            if (filterValue != null && filterValue.trim().length() > 0) {
                switch (filterSelection) {
                    case "email":
                        User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), filterValue);
                        downloads = DownloadLocalServiceUtil.findDownloadsByUserId(siteGroupId, user.getUserId(), start, end);
                        downloadsCount = DownloadLocalServiceUtil.countDownloadsByUserId(siteGroupId, user.getUserId());
                        break;
                    case "articleid":
                        final long downloadId = Long.parseLong(filterValue);
                        downloads = DownloadLocalServiceUtil.findDownloadsByArticleId(siteGroupId, downloadId, start, end);
                        downloadsCount = DownloadLocalServiceUtil.countDownloadsByArticleId(siteGroupId, downloadId);
                        break;
                }
            }
            if (downloads == null) {
                downloads = DownloadLocalServiceUtil.findDownloads(siteGroupId, start, end);
                downloadsCount = DownloadLocalServiceUtil.countDownloads(siteGroupId);
            }

            renderRequest.setAttribute("records", convertToDisplayDownloads(downloads));
            renderRequest.setAttribute("total", downloadsCount);
            renderRequest.setAttribute("filterValue", filterValue);
            renderRequest.setAttribute("filterSelection", filterSelection);
        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
            renderRequest.setAttribute("records", Collections.emptyList());
            renderRequest.setAttribute("total", 0);
        }
    }

    private List<DisplayDownload> convertToDisplayDownloads(List<Download> downloads) {
        final ArrayList<DisplayDownload> displays = new ArrayList<>(downloads.size());
        downloads.forEach(download -> displays.add(new DisplayDownload(download)));

        displays.sort(DisplayDownload::compareDesc);
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

        final String filter = ParamUtil.getString(actionRequest, "filterValue", "none");
        actionResponse.getRenderParameters().setValue("filterValue", filter);
        final String filterSelection = ParamUtil.getString(actionRequest, "filterSelection", "none");
        actionResponse.getRenderParameters().setValue("filterSelection", filterSelection);
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
        String filterValue = ParamUtil.getString(request, "filterValue", null);
        String filterSelection = ParamUtil.getString(request, "filterSelection", null);

        if ("export".equals(action)) {
            if (id == null) {
                id = DownloadTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            exportTable(id, filterValue, filterSelection, response, themeDisplay);
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


    private void exportTable(String dataRequestId, String filterValue, String filterSelection, ResourceResponse response, ThemeDisplay themeDisplay) throws IOException {
        response.setContentType("text/csv");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new ExportTableRequest(dataRequestId, filterValue, filterSelection, themeDisplay.getUserId(), themeDisplay.getSiteGroup());
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