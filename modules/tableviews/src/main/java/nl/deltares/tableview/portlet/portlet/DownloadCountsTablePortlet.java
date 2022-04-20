package nl.deltares.tableview.portlet.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.tableview.model.DisplayDownloadCount;
import nl.deltares.tableview.portlet.constants.DownloadTablePortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=DownloadCountsTable",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/downloadCountsTable.jsp",
                "javax.portlet.name=" + DownloadTablePortletKeys.DOWNLOADCOUNTTABLE,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DownloadCountsTablePortlet extends MVCPortlet {

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

        doFilterValues(cur, deltas, renderRequest);

        super.render(renderRequest, renderResponse);
    }

    private void doFilterValues(int cur, int deltas, RenderRequest renderRequest) {

        final List<DisplayDownloadCount> displayCounts = new ArrayList<>();
        final int end = cur + deltas;
        try {
            final List<DownloadCount> downloadCounts = DownloadCountLocalServiceUtil.getDownloadCounts(cur, end);
            downloadCounts.forEach(downloadCount -> {

                final long downloadId = downloadCount.getDownloadId();
                final long groupId = downloadCount.getGroupId();
                try {
                    final String downloadIdString = String.valueOf(downloadId);
                    final AbsDsdArticle download = dsdParserUtils.toDsdArticle(groupId, downloadIdString);
                    String fileName = download != null ? ((Download) download).getFileName() : downloadIdString;
                    final Group group = GroupLocalServiceUtil.getGroup(groupId);
                    String groupName = group != null ? group.getName() : String.valueOf(groupId);
                    displayCounts.add(new DisplayDownloadCount(downloadId, downloadCount.getCount(), fileName, groupName));
                } catch (PortalException e) {
                    SessionErrors.add(renderRequest, "action-failed", "Error getting download for id " + downloadId);
                }
            });

            renderRequest.setAttribute("records", displayCounts);
            renderRequest.setAttribute("total", displayCounts.size());
        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
        }
    }
}