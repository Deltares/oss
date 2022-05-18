package nl.deltares.tableview.portlet.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
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
import java.util.*;

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

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        Map<String, String> topicMap;
        try {
            topicMap = dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
                    "download", "Topic", themeDisplay.getLocale());
        } catch (PortalException e) {
            topicMap = Collections.emptyMap();
        }
        doFilterValues(cur, deltas, renderRequest, topicMap);

        super.render(renderRequest, renderResponse);
    }

    private void doFilterValues(int cur, int deltas, RenderRequest renderRequest, Map<String, String> topicMap) {

        final List<DisplayDownloadCount> displayCounts = new ArrayList<>();
        final int end = cur + deltas;
        try {
            final List<DownloadCount> downloadCounts = DownloadCountLocalServiceUtil.getDownloadCounts(cur, end);
            downloadCounts.forEach(downloadCount -> {

                final long downloadId = downloadCount.getDownloadId();
                final long groupId = downloadCount.getGroupId();
                try {
                    final String downloadIdString = String.valueOf(downloadId);
                    final AbsDsdArticle dsdArticle = dsdParserUtils.toDsdArticle(groupId, downloadIdString);
                    String fileName;
                    String topic;
                    if (dsdArticle != null) {
                        final Download download = (Download) dsdArticle;
                        fileName = download.getFileName();
                        String topicKey = download.getFileTopic();
                        topic = topicMap.getOrDefault(topicKey, topicKey);
                    } else {
                        fileName = downloadIdString;
                        topic = "";
                    }

                    displayCounts.add(new DisplayDownloadCount(fileName, topic, downloadCount.getCount()));
                } catch (PortalException e) {
                    SessionErrors.add(renderRequest, "action-failed", "Error getting download for id " + downloadId);
                }
            });
            displayCounts.sort(DisplayDownloadCount::compareTo);
            renderRequest.setAttribute("records", displayCounts);
            renderRequest.setAttribute("total", displayCounts.size());
        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
        }
    }
}