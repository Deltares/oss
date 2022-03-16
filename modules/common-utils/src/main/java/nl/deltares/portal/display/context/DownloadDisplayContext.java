package nl.deltares.portal.display.context;

import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.model.impl.Download;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

public class DownloadDisplayContext {

    private static final Log LOG = LogFactoryUtil.getLog(DownloadDisplayContext.class);

    private final ThemeDisplay themeDisplay;
    private final Download download;

    public DownloadDisplayContext(Download download, ThemeDisplay themeDisplay) {
        this.themeDisplay = themeDisplay;
        this.download = download;
    }

    public Download getDownload() {
        return download;
    }

    public static JournalArticleDisplay getArticleDisplay(PortletRequest portletRequest, PortletResponse portletResponse,
                                                          String ddmTemplateKey, String articleId, ThemeDisplay themeDisplay) {
        JournalArticleDisplay articleDisplay = null;
        try {
            articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
                    themeDisplay.getScopeGroupId(), articleId, ddmTemplateKey, "VIEW",
                    themeDisplay.getLanguageId(), 1, new PortletRequestModel(portletRequest, portletResponse),
                    themeDisplay);
        } catch (Exception e) {
            String message = String.format("Error getting article display object for article [%s] with template ID [%s]",
                    articleId, ddmTemplateKey);
            LOG.debug(message, e);
        }
        return articleDisplay;
    }

    public String getDirectDownloadRequest(PortletRequest portletRequest) {

        try {
            Layout downloadPage = LayoutLocalServiceUtil
                    .fetchLayoutByFriendlyURL(themeDisplay.getSiteGroupId(), false, "/download");
            if (downloadPage != null) {

                final LiferayPortletURL resourceUrl = PortletURLFactoryUtil.create(
                        portletRequest,
                        "downloadForm",
                        downloadPage.getPlid(),
                        PortletRequest.RENDER_PHASE);
                resourceUrl.setWindowState(LiferayWindowState.NORMAL);
                resourceUrl.setPortletMode(PortletMode.VIEW);
                resourceUrl.setParameter("javax.portlet.action", "/submit/download/form");
                resourceUrl.setParameter("fileId", String.valueOf(download.getFileId()));
                resourceUrl.setParameter("action", "directdownload");
                return resourceUrl.toString();
            }
        } catch (Exception e) {
            LOG.error("Error creating portlet url", e);
        }

        return "";
    }

}
