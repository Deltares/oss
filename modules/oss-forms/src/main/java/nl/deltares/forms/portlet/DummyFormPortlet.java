package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.DeleteBannedUsersRequest;
import nl.deltares.tasks.impl.DummyRequest;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
            deleteBannedUsersAction(id, resourceResponse, themeDisplay);
        } else if ("updateStatus".equals(action)){
            DataRequestManager.getInstance().updateStatus(id, resourceResponse);
        } else if ("downloadLog".equals(action)){
            DataRequestManager.getInstance().downloadDataFile(id, resourceResponse);
        } else if ("deleteDirect".equals(action)) {
            DummyRequest dummyRequest = new DummyRequest("dummy", themeDisplay.getUserId());
            dummyRequest.startNoThread(resourceResponse.getWriter());
        }

    }

    private void deleteBannedUsersAction(String dataRequestId, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) throws IOException {
        resourceResponse.setContentType("application/json");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);

        if (dataRequest == null){
            dataRequest = new DummyRequest(dataRequestId, themeDisplay.getUserId());
            instance.addToQueue(dataRequest);
        }
        resourceResponse.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        resourceResponse.setContentLength(statusMessage.length());
        PrintWriter writer = resourceResponse.getWriter();
        writer.print(statusMessage);
    }

}