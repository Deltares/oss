package nl.deltares.forms.portlet;

import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.service.MBBanLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
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
import nl.deltares.tasks.impl.DeleteUsersRequest;
import nl.deltares.tasks.impl.DownloadInvalidUsersRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/user-admin.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=User management Admin Form",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/admin/user_admin.jsp",
                "javax.portlet.name=" + OssConstants.OSS_ADMIN_FORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=administrator"
        },
        service = Portlet.class
)
public class UserManagmentAdminFormPortlet extends MVCPortlet {

    @Reference
    AdminUtils adminUtils;

    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException {
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        if (!PortletPermissionUtils.isUserAdministrator(themeDisplay.getUserId())) {
            resourceResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resourceResponse.getWriter().println("Unauthorized request!");
            return;
        }
        String action = ParamUtil.getString(resourceRequest, "action");
        String id = DataRequest.class.getName().concat("_").concat(String.valueOf(themeDisplay.getCompanyId()))
                .concat("_").concat(String.valueOf(themeDisplay.getSiteGroupId())).concat(String.valueOf(themeDisplay.getUserId()));
        if ("deleteBannedUsers".equals(action)) {
            deleteBannedUsersAction(id, resourceResponse, themeDisplay);
        } else if ("updateStatus".equals(action)) {
            DataRequestManager.getInstance().updateStatus(id, resourceResponse);
        } else if ("downloadLog".equals(action)) {
            DataRequestManager.getInstance().downloadDataFile(id, resourceResponse);
        } else if ("downloadInvalidUsers".equals(action)) {
            downloadInvalidUsersAction(id, resourceResponse, themeDisplay);
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
                writer.print(String.format("{\"status\"' : \"error\", \"message\" : \"Failed to copy tempFile %s: %s\"}", tempFile.getName(), e.getMessage()));
                return;
            }

            resourceResponse.setContentType("application/json");
            DataRequestManager instance = DataRequestManager.getInstance();
            DataRequest dataRequest = instance.getDataRequest(dataRequestId);
            if (dataRequest == null) {
                dataRequest = new DeleteUsersRequest(dataRequestId, themeDisplay.getUserId(), usersFilePath, adminUtils);
                instance.addToQueue(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
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
        if (dest.exists()) {
            Files.delete(dest.toPath());
        }
        final Path usersFile = Files.copy(tempFile.toPath(), dest.toPath());
        return usersFile.toFile().getAbsolutePath();
    }

    private void downloadInvalidUsersAction(String dataRequestId, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) throws IOException {

        resourceResponse.setContentType("application/json");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new DownloadInvalidUsersRequest(dataRequestId, themeDisplay.getUserId(), adminUtils);
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
            instance.removeDataRequest(dataRequest);
        }
        resourceResponse.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        resourceResponse.setContentLength(statusMessage.length());
        PrintWriter writer = resourceResponse.getWriter();
        writer.println(statusMessage);
    }

    private void deleteBannedUsersAction(String dataRequestId, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) throws IOException {
        List<MBBan> bannedUsers = getBannedUsersAllSites();
        if (bannedUsers.size() == 0) {
            PrintWriter writer = resourceResponse.getWriter();
            resourceResponse.setContentType("text/plain");
            resourceResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            writer.println("No banned users found for any site.");
            return;
        }
        resourceResponse.setContentType("application/json");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new DeleteBannedUsersRequest(dataRequestId, themeDisplay.getUserId(), bannedUsers, adminUtils);
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
            instance.removeDataRequest(dataRequest);
        }
        resourceResponse.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        resourceResponse.setContentLength(statusMessage.length());
        PrintWriter writer = resourceResponse.getWriter();
        writer.println(statusMessage);
    }

    private List<MBBan> getBannedUsersAllSites() {

        List<MBBan> bans = new ArrayList<>();
        final List<VirtualHost> virtualHosts = VirtualHostLocalServiceUtil.getVirtualHosts(0, 10);
        for (VirtualHost virtualHost : virtualHosts) {
            final long companyId = virtualHost.getCompanyId();
            for (Group companyGroup : GroupLocalServiceUtil.getCompanyGroups(companyId, 0, 50)) {
                if (!companyGroup.isSite()) continue;
                bans.addAll(MBBanLocalServiceUtil.getBans(companyGroup.getGroupId(), 0, 100));
            }
        }
        return bans;
    }
}