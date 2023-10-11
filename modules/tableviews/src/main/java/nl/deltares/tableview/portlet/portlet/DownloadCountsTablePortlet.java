package nl.deltares.tableview.portlet.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.tableview.comparator.DownloadCountComparator;
import nl.deltares.tableview.model.DisplayDownloadCount;
import nl.deltares.tableview.portlet.constants.TablePortletKeys;
import nl.deltares.tableview.tasks.impl.DeletedSelectedDownloadCountsRequest;
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
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS-table",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/downloadcountstableview.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=DownloadCountsTable",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/downloadCountsTable.jsp",
                "javax.portlet.name=" + TablePortletKeys.DOWNLOADCOUNTTABLE,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DownloadCountsTablePortlet extends MVCPortlet {

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;

    @Reference
    private DsdParserUtils dsdParserUtils;

    final static String datePattern = "yyy-MM-dd";
    final static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final int cur = ParamUtil.getInteger(renderRequest, "cur", 0);
        final int deltas = ParamUtil.getInteger(renderRequest, "delta", 50);
        String filterId = ParamUtil.getString(renderRequest, "filterId", "none");
        if ("none".equals(filterId)) filterId = null;
        Map<String, String> topicMap = getTopicsFromStructure(renderRequest);
        doFilterValues(filterId, cur, deltas, renderRequest, topicMap);

        super.render(renderRequest, renderResponse);
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

        if ("delete-selected".equals(action)) {
            if (id == null) {
                id = DownloadCountsTablePortlet.class.getName() + themeDisplay.getUserId();
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
                dataRequest = new DeletedSelectedDownloadCountsRequest(dataRequestId, themeDisplay.getUserId(),
                        Arrays.asList(selectedIds), dsdParserUtils);
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

    private Map<String, String> getTopicsFromStructure(RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        Map<String, String> topicMap;
        try {
            topicMap = dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
                    "download", "Topic", themeDisplay.getLocale());
        } catch (PortalException e) {
            topicMap = Collections.emptyMap();
        }
        return topicMap;
    }

    private void doFilterValues(String filterId, int cur, int deltas, RenderRequest renderRequest, Map<String, String> topicMap) {

        String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
        String orderByType = ParamUtil.getString(renderRequest, "orderByType");

        final List<DisplayDownloadCount> displayCounts = new ArrayList<>();
        final int end = cur + deltas;
        try {
            final List<DownloadCount> downloadCounts = DownloadCountLocalServiceUtil.fetchDownloadCounts(cur, end);
            downloadCounts.forEach(downloadCount -> {

                final long downloadId = downloadCount.getDownloadId();
                final long groupId = downloadCount.getGroupId();
                AbsDsdArticle dsdArticle;
                try {
                    dsdArticle = dsdParserUtils.toDsdArticle(groupId, String.valueOf(downloadId));
                } catch (PortalException e) {
                    dsdArticle = null;
                }
                String fileName;
                String topic;
                String topicKey = null;
                if (dsdArticle != null) {
                    final Download download = (Download) dsdArticle;
                    fileName = download.getFileName();
                    topicKey = download.getFileTopic();
                    topic = topicMap.getOrDefault(topicKey, topicKey);
                } else {
                    fileName = String.valueOf(downloadId);
                    topic = "";
                }
                if (filterId != null && !filterId.equals(topicKey)) return;
                displayCounts.add(new DisplayDownloadCount(downloadCount.getId(), fileName, topic, downloadCount.getCount()));
            });
            sortDownloads(displayCounts, orderByCol, orderByType);
        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
        }
        renderRequest.setAttribute("records", displayCounts);
        renderRequest.setAttribute("total", displayCounts.size());
        renderRequest.setAttribute("topics", topicMap);
        renderRequest.setAttribute("filterId", filterId);
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void filter(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String filter = ParamUtil.getString(actionRequest, "filterSelection", "none");
        actionResponse.getRenderParameters().setValue("filterId", filter);
    }

    private void sortDownloads(List<DisplayDownloadCount> displays, String orderByCol, String orderByType) {

        final DownloadCountComparator comparator = new DownloadCountComparator(orderByCol, orderByType.equals("asc"));
        displays.sort(comparator);

    }
}