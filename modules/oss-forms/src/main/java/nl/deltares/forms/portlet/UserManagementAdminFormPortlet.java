package nl.deltares.forms.portlet;

import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.service.MBBanLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.CheckNonKeycloakUsersRequest;
import nl.deltares.tasks.impl.DeleteBannedUsersRequest;
import nl.deltares.tasks.impl.DeleteUsersRequest;
import nl.deltares.tasks.impl.DownloadInvalidUsersRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
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
                "javax.portlet.version=3.0",
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
public class UserManagementAdminFormPortlet extends MVCPortlet {

    @Reference
    AdminUtils adminUtils;

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    public void update(ActionRequest actionRequest, ActionResponse actionResponse) {

        if (!actionRequest.isUserInRole("administrator")) {
            SessionErrors.add(actionRequest, "update-failed", "Unauthorized request!");
            return;
        }

        String action = ParamUtil.getString(actionRequest, "action");
        if (action.equals("changeUserEmail")) {
            final String currentEmail = ParamUtil.getString(actionRequest, "currentUserEmail");
            final String newEmail = ParamUtil.getString(actionRequest, "newUserEmail");
            try {
                adminUtils.changeUserEmail(currentEmail, newEmail);
                SessionMessages.add(actionRequest, "update-success", String.format("Changed user email from %s to %s.", currentEmail, newEmail));
            } catch (Exception e) {
                SessionErrors.add(actionRequest, "update-failed", e.getMessage());

                actionResponse.getRenderParameters().setValue("currentUserEmail", currentEmail);
                actionResponse.getRenderParameters().setValue("newUserEmail", newEmail);
            }
        } else {
            SessionErrors.add(actionRequest, "update-failed", "Unsupported action " + action);
        }
    }
    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException {
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        if (!resourceRequest.isUserInRole("administrator")) {
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
        } else if ("checkUsersExist".equals(action)) {
            checkUsersExistAction(id, resourceRequest, resourceResponse, themeDisplay);
        } else if ("deleteUsers".equals(action)) {
            deleteUsersAction(id, resourceRequest, resourceResponse, themeDisplay);
        } else {
            DataRequestManager.getInstance().writeError("Unsupported action error: " + action, resourceResponse);
        }
    }

    private String getUploadedFile(String dataRequestId, String userFile, ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException {
        UploadRequest uploadRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
        File tempFile = uploadRequest.getFile(userFile);
        if (tempFile != null && tempFile.exists()) {
            try {
                return copyTempFile(dataRequestId, tempFile, tempFile.getName());
            } catch (IOException e) {
                PrintWriter writer = resourceResponse.getWriter();
                resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resourceResponse.setContentType("application/json");
                writer.print(String.format("{\"status\"' : \"error\", \"message\" : \"Failed to copy tempFile %s: %s\"}", tempFile.getName(), e.getMessage()));
            }
        } else {
            PrintWriter writer = resourceResponse.getWriter();
            resourceResponse.setContentType("application/json");
            resourceResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print("{\"status\" : \"error\", \"message\": \"Could not find uploaded file!\"}");
        }
        return null;
    }

    private void checkUsersExistAction(String dataRequestId, ResourceRequest resourceRequest, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) throws IOException {

        String usersFilePath = getUploadedFile(dataRequestId, "checkUserFile", resourceRequest, resourceResponse);
        if (usersFilePath != null) {
            resourceResponse.setContentType("application/json");
            DataRequestManager instance = DataRequestManager.getInstance();
            DataRequest dataRequest = instance.getDataRequest(dataRequestId);
            if (dataRequest == null) {
                dataRequest = new CheckNonKeycloakUsersRequest(dataRequestId, themeDisplay.getUserId(), usersFilePath, adminUtils);
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
    }

    private void deleteUsersAction(String dataRequestId, ResourceRequest resourceRequest, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) throws IOException {
        String usersFilePath = getUploadedFile(dataRequestId, "deleteUserFile", resourceRequest, resourceResponse);
        if (usersFilePath != null) {
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
        if (bannedUsers.isEmpty()) {
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
        final int totalBans = MBBanLocalServiceUtil.getMBBansCount();
        for (int i = 0; i < totalBans; i += 50) {
            bans.addAll(MBBanLocalServiceUtil.getMBBans(i, i + 50));
        }
        return bans;
    }
}