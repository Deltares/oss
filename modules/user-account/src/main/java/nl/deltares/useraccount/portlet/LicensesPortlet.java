package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.portal.configuration.SiteMapConfiguration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.useraccount.comparator.DownloadFiledComparator;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;
import nl.deltares.useraccount.model.DisplayDownload;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS-account",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Licenses",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/userlicenses.jsp",
                "javax.portlet.name=" + UserProfilePortletKeys.LICENSES,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class LicensesPortlet extends MVCPortlet {
    private static final Log logger = LogFactoryUtil.getLog(LicensesPortlet.class);

    @Reference
    private DsdParserUtils dsdParserUtils;

    final static String datePattern = "yyy-MM-dd";

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }
    final static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);
        String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
        String orderByType = ParamUtil.getString(renderRequest, "orderByType");
        final long siteGroupId = getDownloadSiteGroup(themeDisplay);
        final User loggedInUser = getDownloadSiteUser(themeDisplay, siteGroupId);
        List<Download> downloads;
        try {
            downloads = DownloadLocalServiceUtil.findDownloadsByUserId(siteGroupId, loggedInUser.getUserId());
            final List<DisplayDownload> list = convertToDisplayDownloads(downloads);
            sortDownloads(list, orderByCol, orderByType);
            renderRequest.setAttribute("records", list);

        } catch (Exception e) {
            renderRequest.setAttribute("records", Collections.emptyList());
        }
        super.render(renderRequest, renderResponse);
    }

    private void sortDownloads(List<DisplayDownload> displays, String orderByCol, String orderByType) {

        final DownloadFiledComparator comparator = new DownloadFiledComparator(orderByCol, orderByType.equals("asc"));
        displays.sort(comparator);

    }

    private User getDownloadSiteUser(ThemeDisplay themeDisplay, long siteGroupId) {

        final User loggedInUser = themeDisplay.getUser();
        final Group downloadGroup;
        try {
            downloadGroup = GroupLocalServiceUtil.getGroup(siteGroupId);
        } catch (PortalException e) {
            logger.warn(String.format("Error retrieving download group %d: %s\nReturning logged in user.", siteGroupId, e.getMessage()));
            return loggedInUser;
        }

        final User downloadSiteUser = UserLocalServiceUtil.fetchUserByEmailAddress(downloadGroup.getCompanyId(), loggedInUser.getEmailAddress());
        if (downloadSiteUser != null) return downloadSiteUser;

        logger.warn(String.format("Cannot find download site user: %s! Returning logged in user.", loggedInUser.getEmailAddress()));
        return loggedInUser;
    }

    private List<DisplayDownload> convertToDisplayDownloads(List<Download> downloads) {
        final ArrayList<DisplayDownload> displays = new ArrayList<>(downloads.size());

        downloads.forEach(download -> {
            if (download.getLicenseDownloadUrl() == null) return;

            final DisplayDownload displayDownload = new DisplayDownload(download);
            if (displayDownload.getFileName() == null) {
                try {
                    final Download dsdArticle = (Download) dsdParserUtils.toDsdArticle(download.getGroupId(), String.valueOf(download.getDownloadId()));
                    displayDownload.setFileName(dsdArticle.getFileName());
                } catch (PortalException e) {
                    logger.warn(String.format("Error parsing download %d: %s", download.getDownloadId(), e.getMessage()));
                }

            }
            displays.add(displayDownload);
        });

        return displays;
    }

    private long getDownloadSiteGroup(ThemeDisplay themeDisplay){

        try {
            SiteMapConfiguration configuration = _configurationProvider.getSystemConfiguration(
                    SiteMapConfiguration.class);
            return  Long.parseLong(configuration.downloadPortalSiteId());
        } catch (Exception e) {
            logger.error("Error retrieving ID for download portal from SiteMapConfiguration: " + e.getMessage());
            return themeDisplay.getSiteGroupId();
        }
    }

}