package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.common.emails.EmailUtils;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.tasks.DataRequestManager;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/download.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Download Admin Form",
                "javax.portlet.init-param.config-template=/admin/configuration/download_configuration.jsp",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/admin/download_admin.jsp",
                "javax.portlet.name=" + OssConstants.DOWNLOAD_ADMIN_FORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DownloadAdminFormPortlet extends MVCPortlet {

    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        if (!PortletPermissionUtils.isUserSiteAdministrator(themeDisplay.getUserId(), themeDisplay.getSiteGroupId())) {
            resourceResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resourceResponse.getWriter().println("Unauthorized request: User must be site administrator!");
            return;
        }
        String action = ParamUtil.getString(resourceRequest, "action");
        if ("testEmail".equals(action)) {
            String email = ParamUtil.getString(resourceRequest, "email", null);
            String virtualHost = themeDisplay.getCompany().getVirtualHostname();
            String site = themeDisplay.getSiteGroup().getNameCurrentValue();

            String source = virtualHost + ':' + site;
            try {
                DataRequestManager.getInstance().writeInfo(EmailUtils.sendTestEmail(themeDisplay.getUser(), email, source), resourceResponse);
            } catch (Exception e) {
                DataRequestManager.getInstance().writeError("Error sending test email: " + e.getMessage(), resourceResponse);
            }
        } else {
            DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, resourceResponse);
        }


        super.serveResource(resourceRequest, resourceResponse);
    }
}