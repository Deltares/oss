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
import nl.deltares.portal.utils.*;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.DownloadEventRegistrationsRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
	immediate = true,
	property = {
			"javax.portlet.version=3.0",
			"com.liferay.portlet.display-category=OSS",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.header-portlet-javascript=/lib/dsd-admin.js",
			"com.liferay.portlet.header-portlet-javascript=/lib/common.js",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=DSD Admin Form",
			"javax.portlet.init-param.config-template=/admin/configuration/dsd_configuration.jsp",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/admin/dsd_admin.jsp",
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

	@Reference
	WebinarUtilsFactory webinarUtilsFactory;

	private final String[] downloadActions = new String[] {"download", "downloadRepro", "downloadLight"};
	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			request.setAttribute("events", getEvents(themeDisplay));
			request.setAttribute("years", getYears());
		} catch (PortalException e) {
			request.setAttribute("events", Collections.emptyList());
		}
		super.render(request, response);
	}

	private List<Integer> getYears() {
		final ArrayList<Integer> years = new ArrayList<>();
		final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = -5; i < 2; i++) {
			years.add(currentYear + i);
		}
		return years;
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

		if (!PortletPermissionUtils.isUserSiteAdministrator(themeDisplay.getUserId(), themeDisplay.getSiteGroupId())) {
			resourceResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resourceResponse.getWriter().println("Unauthorized request: User must be site administrator!");
			return;
		}
		String action = ParamUtil.getString(resourceRequest, "action");
		boolean removeMissing = ParamUtil.getBoolean(resourceRequest, "removeMissing");
		String id = ParamUtil.getString(resourceRequest, "id", null);

		boolean delete = action != null && action.startsWith("delete");
		final int downloadAction = getDownloadActionIndex(action);
		if (downloadAction > -1 || delete) {
			String articleId = ParamUtil.getString(resourceRequest, "articleId", null);
			int year = ParamUtil.getInteger(resourceRequest, "year", -1);

			if (articleId != null) {
				if (id == null) {
					id = DownloadEventRegistrationsRequest.class.getName() + articleId + themeDisplay.getUserId();
				}
			} else if (year != -1) {
				if (id == null) {
					id = DownloadEventRegistrationsRequest.class.getName() + year + themeDisplay.getUserId();
				}
			} else {
				DataRequestManager.getInstance().writeError("No articleId or year selected!", resourceResponse);
				return;
			}

			downloadEventRegistrations(id, resourceResponse, themeDisplay, articleId, year, "deletePrimKey".equals(action),
					delete, removeMissing, downloadAction);
		} else if ("updateStatus".equals(action)){
			DataRequestManager.getInstance().updateStatus(id, resourceResponse);
		} else if ("downloadLog".equals(action)){
			DataRequestManager.getInstance().downloadDataFile(id, resourceResponse);
		} else {
			DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, resourceResponse);
		}
	}

	private int getDownloadActionIndex(String action) {
		for (int i = 0; i < downloadActions.length; i++) {
			if (downloadActions[i].equals(action)) return i;
		}
		return -1;
	}

	private void downloadEventRegistrations(String dataRequestId, ResourceResponse resourceResponse,
											ThemeDisplay themeDisplay, String articleId, Integer year, boolean primKey, boolean delete,
											boolean deleteMissing, int downloadAction) throws IOException {
		resourceResponse.setContentType("text/csv");
		DataRequestManager instance = DataRequestManager.getInstance();
		DataRequest dataRequest = instance.getDataRequest(dataRequestId);
		if (dataRequest == null) {
			if (articleId != null) {
				dataRequest = new DownloadEventRegistrationsRequest(dataRequestId, themeDisplay.getUserId(), articleId, themeDisplay.getSiteGroup(),
						dsdParserUtils, dsdSessionUtils, dsdJournalArticleUtils,
						webinarUtilsFactory, primKey, delete, deleteMissing, downloadAction);
			} else if (year != null) {
				dataRequest = new DownloadEventRegistrationsRequest(dataRequestId, themeDisplay.getUserId(), year, themeDisplay.getSiteGroup(),
						dsdParserUtils, dsdSessionUtils, dsdJournalArticleUtils,
						webinarUtilsFactory, primKey, delete, deleteMissing, downloadAction);
			}
			instance.addToQueue(dataRequest);
		} else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata){
			instance.removeDataRequest(dataRequest);
		}
		resourceResponse.setStatus(HttpServletResponse.SC_OK);
        assert dataRequest != null;
        String statusMessage = dataRequest.getStatusMessage();
		resourceResponse.setContentLength(statusMessage.length());
		PrintWriter writer = resourceResponse.getWriter();
		writer.println(statusMessage);
		writer.flush();

	}

}