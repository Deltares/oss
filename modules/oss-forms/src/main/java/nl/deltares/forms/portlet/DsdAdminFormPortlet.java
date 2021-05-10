package nl.deltares.forms.portlet;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.kernel.util.comparator.JournalArticleComparator;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.DownloadEventRegistrationsRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
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
			"javax.portlet.display-name=DSD Admin Form",
			"javax.portlet.init-param.config-template=/admin/configuration/dsd_configuration.jsp",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/dsd_admin.jsp",
			"javax.portlet.name=" + OssConstants.DSD_ADMIN_FORM,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DsdAdminFormPortlet extends MVCPortlet {

	@Reference
	DsdParserUtils dsdParserUtils;

	@Reference
	DsdSessionUtils dsdSessionUtils;

	@Reference
	KeycloakUtils keycloakUtils;

	@Reference
	DsdJournalArticleUtils dsdJournalArticleUtils;

	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			request.setAttribute("events", getEvents(themeDisplay));
		} catch (PortalException e) {
			request.setAttribute("events", Collections.emptyList());
		}
		super.render(request, response);
	}

	private List<JournalArticle> getEvents(ThemeDisplay themeDisplay) throws PortalException {
		List<JournalArticle> events = dsdJournalArticleUtils.getEvents(themeDisplay.getSiteGroupId(), themeDisplay.getLocale());
		List<Group> children = themeDisplay.getSiteGroup().getChildren(true);
		for (Group child : children) {
			events.addAll(dsdJournalArticleUtils.getEvents(child.getGroupId(), themeDisplay.getLocale()));
		}

		JournalArticleComparator comparator = new JournalArticleComparator(dsdParserUtils);
		events.sort(comparator.reversed());
		return events;
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
		String eventId = ParamUtil.getString(resourceRequest, "eventId");
		if (eventId == null || eventId.isEmpty()){
			DataRequestManager.getInstance().writeError("No eventId", resourceResponse);
			return;
		}

		String id = DownloadEventRegistrationsRequest.class.getName() + eventId + themeDisplay.getUserId();
		boolean delete = "delete".equals(action);
		if ("download".equals(action) || delete) {
			downloadEventRegistrations(id, resourceResponse, themeDisplay, eventId, delete);
		} else if ("updateStatus".equals(action)){
			DataRequestManager.getInstance().updateStatus(id, resourceResponse);
		} else if ("downloadLog".equals(action)){
			DataRequestManager.getInstance().downloadDataFile(id, resourceResponse);
		} else {
			DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, resourceResponse);
		}
	}

	private void downloadEventRegistrations(String dataRequestId, ResourceResponse resourceResponse,
											ThemeDisplay themeDisplay, String eventId, boolean delete) throws IOException {
		resourceResponse.setContentType("text/csv");
		DataRequestManager instance = DataRequestManager.getInstance();
		DataRequest dataRequest = instance.getDataRequest(dataRequestId);
		if (dataRequest == null) {
			dataRequest = new DownloadEventRegistrationsRequest(dataRequestId, themeDisplay.getUserId(), eventId, themeDisplay.getSiteGroup(),
					themeDisplay.getLocale(), dsdParserUtils, dsdSessionUtils, keycloakUtils, dsdJournalArticleUtils, delete);
			instance.addToQueue(dataRequest);
		} else if (dataRequest.getStatus() == DataRequest.STATUS.terminated){
			instance.removeDataRequest(dataRequest);
		}
		resourceResponse.setStatus(HttpServletResponse.SC_OK);
		String statusMessage = dataRequest.getStatusMessage();
		resourceResponse.setContentLength(statusMessage.length());
		PrintWriter writer = resourceResponse.getWriter();
		writer.println(statusMessage);

	}

}