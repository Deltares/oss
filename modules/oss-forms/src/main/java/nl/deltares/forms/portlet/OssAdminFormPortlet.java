package nl.deltares.forms.portlet;

import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.service.MBBanLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.utils.AdminUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=OSS Admin Form",
                "javax.portlet.init-param.config-template=/admin/configuration/oss_configuration.jsp",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/oss_admin.jsp",
                "javax.portlet.name=" + OssConstants.OSS_ADMIN_FORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=administrator"
        },
        service = Portlet.class
)
public class OssAdminFormPortlet extends MVCPortlet {

    @Reference
    AdminUtils adminUtils;

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        renderRequest.setAttribute(ConfigurationProvider.class.getName(), _configurationProvider);

        super.render(renderRequest, renderResponse);
    }

    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException {
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        if (!themeDisplay.isSignedIn() || !resourceRequest.isUserInRole("administrator")) {
            resourceResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resourceResponse.getWriter().println("Unauthorized request!");
            return;
        }
        String action = ParamUtil.getString(resourceRequest, "action");
        long siteId = ParamUtil.getLong(resourceRequest, "siteId");
        if (siteId == 0) siteId = themeDisplay.getScopeGroupId();
        if ("deleteBannedUsers".equals(action)) {

            List<com.liferay.message.boards.model.MBBan> bannedUsers = MBBanLocalServiceUtil.getBans(siteId, 0, 100);

            resourceResponse.setContentType("text/csv");
            PrintWriter writer = resourceResponse.getWriter();

            if (bannedUsers.size() == 0) {
                try {
                    Group group = GroupLocalServiceUtil.getGroup(siteId);
                    writer.printf("No banned users found for site %s (%d)", group.getName(), siteId);
                } catch (PortalException e) {
                    writer.printf("Error getting scope group for siteId %d: %s", siteId, e.getMessage());
                    return;
                }
            }
            for (MBBan bannedUser : bannedUsers) {
                if (bannedUser.getBanUserId() == themeDisplay.getUserId()) {
                    writer.printf("Error: Not allowed to delete yourself %s", bannedUser.getUserName());
                    continue;
                }

                adminUtils.deleteUserAndRelatedContent(siteId, bannedUser.getBanUserId(), writer);
                //start flushing
                writer.flush();
            }
        }

    }


    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

}