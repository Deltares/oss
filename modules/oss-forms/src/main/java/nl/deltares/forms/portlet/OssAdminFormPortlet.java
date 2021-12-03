package nl.deltares.forms.portlet;

import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.service.MBBanLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.DeleteBannedUsersRequest;
import nl.deltares.tasks.impl.DeleteDisabledUsersRequest;
import nl.deltares.tasks.impl.DownloadDisabledUsersRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        long disabledTimeAfter = ParamUtil.getLong(resourceRequest, "disabledTime", System.currentTimeMillis() - TimeUnit.DAYS.toMillis(365));
        if (siteId == 0) siteId = themeDisplay.getScopeGroupId();

        String id = DataRequest.class.getName() + siteId + themeDisplay.getUserId();
        if ("deleteBannedUsers".equals(action)) {
            deleteBannedUsersAction(id, resourceResponse, themeDisplay, siteId);
        } else if ("updateStatus".equals(action)) {
            DataRequestManager.getInstance().updateStatus(id, resourceResponse);
        } else if ("downloadLog".equals(action)) {
            DataRequestManager.getInstance().downloadDataFile(id, resourceResponse);
        } else if ("downloadDisabledUsers".equals(action)) {
            downloadDisabledUsersAction(id, disabledTimeAfter, resourceResponse, themeDisplay);
        } else if ("deleteUsers".equals(action)) {
            deleteUsersAction(id, resourceRequest, resourceResponse, themeDisplay);
        } else {
            DataRequestManager.getInstance().writeError("Unsupported action error: " + action, resourceResponse);
        }
    }

    private void deleteUsersAction(String dataRequestId, ResourceRequest resourceRequest, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) throws IOException {
        UploadRequest uploadRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
        File tempFile = uploadRequest.getFile("userFile");
        if (tempFile != null && tempFile.exists()) {
            String usersFilePath;
            try {
                usersFilePath = copyTempFile(dataRequestId, tempFile, tempFile.getName());
            } catch (IOException e) {
                PrintWriter writer = resourceResponse.getWriter();
                resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resourceResponse.setContentType("application/json");
                writer.print(String.format("{\"tatus\"' : \"error\", \"message\" : \"Failed to copy tempFile %s: %s\"}",tempFile.getName(), e.getMessage()) );
                return;
            }

            resourceResponse.setContentType("application/json");
            DataRequestManager instance = DataRequestManager.getInstance();
            DataRequest dataRequest = instance.getDataRequest(dataRequestId);
            if (dataRequest == null) {
                dataRequest = new DeleteDisabledUsersRequest(dataRequestId, themeDisplay.getUserId(), themeDisplay.getCompanyId(), usersFilePath, adminUtils);
                instance.addToQueue(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated) {
                instance.removeDataRequest(dataRequest);
            }
            resourceResponse.setStatus(HttpServletResponse.SC_OK);
            String statusMessage = dataRequest.getStatusMessage();
            resourceResponse.setContentLength(statusMessage.length());
            PrintWriter writer = resourceResponse.getWriter();
            writer.println(statusMessage);
        } else {
            PrintWriter writer = resourceResponse.getWriter();
            resourceResponse.setContentType("application/json");
            resourceResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print("{\"status\" : \"error\", \"message\": \"Could not find uploaded file!\"}");
        }

    }

    private String copyTempFile(String dataRequestId, File tempFile, String fileName) throws IOException {


        final File dest = new File(tempFile.getParentFile(), dataRequestId + fileName);
        if (dest.exists()){
            Files.delete(dest.toPath());
        }
        final Path usersFile = Files.copy(tempFile.toPath(), dest.toPath());
        return usersFile.toFile().getAbsolutePath();
    }

    private void downloadDisabledUsersAction(String dataRequestId, long disabledTimeAfter, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) throws IOException {

        resourceResponse.setContentType("application/json");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new DownloadDisabledUsersRequest(dataRequestId, disabledTimeAfter, themeDisplay.getUserId(), adminUtils);
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated) {
            instance.removeDataRequest(dataRequest);
        }
        resourceResponse.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        resourceResponse.setContentLength(statusMessage.length());
        PrintWriter writer = resourceResponse.getWriter();
        writer.println(statusMessage);
    }

    private void deleteBannedUsersAction(String dataRequestId, ResourceResponse resourceResponse, ThemeDisplay themeDisplay, long siteId) throws IOException {
        List<MBBan> bannedUsers = MBBanLocalServiceUtil.getBans(siteId, 0, 100);

        if (bannedUsers.size() == 0) {
            PrintWriter writer = resourceResponse.getWriter();
            resourceResponse.setContentType("text/plain");
            try {
                resourceResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
                Group group = GroupLocalServiceUtil.getGroup(siteId);
                writer.printf("No banned users found for site %s (%d)", group.getName(), siteId);
                return;
            } catch (PortalException e) {
                resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.printf("Error getting scope group for siteId %d: %s", siteId, e.getMessage());
                return;
            }
        }
        resourceResponse.setContentType("application/json");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new DeleteBannedUsersRequest(dataRequestId, siteId, themeDisplay.getUserId(), bannedUsers, adminUtils);
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated) {
            instance.removeDataRequest(dataRequest);
        }
        resourceResponse.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        resourceResponse.setContentLength(statusMessage.length());
        PrintWriter writer = resourceResponse.getWriter();
        writer.println(statusMessage);
    }


    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

}