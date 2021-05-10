package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.tasks.DataRequest;
import nl.deltares.portal.tasks.DataRequestManager;
import nl.deltares.portal.tasks.impl.DeleteBannedUsersRequest;
import nl.deltares.portal.tasks.impl.DummyRequest;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=OSS Dummy Form",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/dummy_admin.jsp",
                "javax.portlet.name=" + OssConstants.OSS_DUMMY_FORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=administrator"
        },
        service = Portlet.class
)
public class DummyFormPortlet extends MVCPortlet {


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
        long siteId = themeDisplay.getScopeGroupId();

        String id = DeleteBannedUsersRequest.class.getName() + siteId + themeDisplay.getUserId();
        if ("deleteBannedUsers".equals(action)) {
            deleteBannedUsersAction(id, resourceResponse, themeDisplay, siteId);
        } else if ("updateStatus".equals(action)){
            updateStatusAction(id, resourceResponse);
        } else if ("downloadLog".equals(action)){
            downloadLogAction(id, resourceResponse);
        } else if ("deleteDirect".equals(action)) {
            DummyRequest dummyRequest = new DummyRequest("dummy");
            dummyRequest.startNoThread(resourceResponse.getWriter());
        }

    }

    private void downloadLogAction(String dataRequestId, ResourceResponse resourceResponse) throws IOException {
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null){
            dataRequestNotExistsError(dataRequestId, resourceResponse);
            return;
        }
        DataRequest.STATUS status = dataRequest.getStatus();
        if (status == DataRequest.STATUS.available && dataRequest.getDataFile().exists()){

            resourceResponse.setStatus(HttpServletResponse.SC_OK);
            resourceResponse.setContentType("text/plain");

            try (OutputStream out = resourceResponse.getPortletOutputStream()) {
                Path path = dataRequest.getDataFile().toPath();
                resourceResponse.setContentLength(Long.valueOf(Files.copy(path, out)).intValue());
                out.flush();
            } catch (IOException e) {
                // handle exception
            } finally {
                instance.removeDataRequest(dataRequest);
            }

        } else {
            resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resourceResponse.setContentType("text/plain");
            PrintWriter writer = resourceResponse.getWriter();
            if (status != DataRequest.STATUS.available) {
                writer.printf("Requested log file does not available yet: %s", dataRequestId);
            } else if (!dataRequest.getDataFile().exists()){
                writer.printf("Requested log file does not exist: %s", dataRequest.getDataFile().getAbsolutePath());
                instance.removeDataRequest(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated){
                writer.printf("Requested log file task has been terminated");
                instance.removeDataRequest(dataRequest);
            }
        }
    }

    private void dataRequestNotExistsError(String dataRequestId, ResourceResponse resourceResponse) throws IOException {
        resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resourceResponse.setContentType("text/plain");
        PrintWriter writer = resourceResponse.getWriter();
        writer.printf("Data request for id does not exist: %s", dataRequestId);
    }

    private void updateStatusAction(String dataRequestId, ResourceResponse resourceResponse) throws IOException {
        resourceResponse.setContentType("application/json");

        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null){
            dataRequestNotExistsError(dataRequestId, resourceResponse);
        } else {
            resourceResponse.setStatus(HttpServletResponse.SC_OK);
            String statusMessage = dataRequest.getStatusMessage();
            resourceResponse.setContentLength(statusMessage.length());
            PrintWriter writer = resourceResponse.getWriter();
            writer.print(statusMessage);
        }
    }

    private void deleteBannedUsersAction(String dataRequestId, ResourceResponse resourceResponse, ThemeDisplay themeDisplay, long siteId) throws IOException {
        resourceResponse.setContentType("application/json");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);

        if (dataRequest == null){
            dataRequest = new DummyRequest(dataRequestId);
            instance.addToQueue(dataRequest);
        }
        resourceResponse.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        resourceResponse.setContentLength(statusMessage.length());
        PrintWriter writer = resourceResponse.getWriter();
        writer.print(statusMessage);
    }

}