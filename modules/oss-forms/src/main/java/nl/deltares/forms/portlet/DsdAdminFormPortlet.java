package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.forms.constants.OssFormPortletKeys;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
		"javax.portlet.init-param.config-template=/admin/configuration/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/dsd_admin.jsp",
		"javax.portlet.name=" + OssFormPortletKeys.REGISTRATION_DOWNLOAD_FORM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DsdAdminFormPortlet extends MVCPortlet {

	private static final Log LOG = LogFactoryUtil.getLog(DsdAdminFormPortlet.class);

	@Reference
	DsdParserUtils dsdParserUtils;

	@Reference
	DsdSessionUtils dsdSessionUtils;

	@Reference
	DsdJournalArticleUtils dsdJournalArticleUtils;

	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			request.setAttribute("events", dsdJournalArticleUtils.getEvents(themeDisplay.getSiteGroupId(), themeDisplay.getLocale()));
		} catch (PortalException e) {
			request.setAttribute("events", Collections.emptyList());
		}
		super.render(request, response);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		String eventId = ParamUtil.getString(resourceRequest, "eventId");
		if (eventId == null || eventId.isEmpty()){
			writeError("No eventId", resourceResponse);
			return;
		}
		Event event;
		try {
			event = dsdParserUtils.getEvent(themeDisplay.getSiteGroupId(), eventId);
		} catch (PortalException e) {
			writeError("Error getting event:" + e.getMessage(), resourceResponse);
			return;
		}
		if (event != null) {
			writeEvents(resourceResponse, event);
		} else {
			writeError("No event found for eventId: " + eventId, resourceResponse);
		}
	}

	private void writeEvents(ResourceResponse resourceResponse, Event event) throws IOException {

		resourceResponse.setContentType("text/csv");

		List<Map<String, Object>> registrationRecords = dsdSessionUtils.getRegistrations(event);

		Map<Long, User> userCache = new HashMap<>();
		PrintWriter writer = resourceResponse.getWriter();
		StringBuilder header = new StringBuilder("title,start date,topic,type,email,firstName,lastName,registrantKey");
		for (KeycloakUtils.BILLING_ATTRIBUTES value : KeycloakUtils.BILLING_ATTRIBUTES.values()) {
			header.append(',');
			header.append(value.name());
		}
		writer.println(header);
		registrationRecords.forEach(recordObjects -> writeRecord(writer, recordObjects, event, userCache, resourceResponse.getLocale()));

	}

	private void writeRecord(PrintWriter writer, Map<String, Object> record, Event event, Map<Long, User> userCache, Locale locale) {

		Long registrationId = (Long) record.get("resourcePrimaryKey");
		Registration registration = event.getRegistration(registrationId);
		if (registration == null){
			LOG.error(String.format("Cannot find registration for registrationId %d", registrationId));
			return;
		}

		Long userId = (Long) record.get("userId");
		User user = userCache.get(userId);
		if (user == null){
			try {
				user = UserLocalServiceUtil.getUser(userId);
			} catch (PortalException e) {
				LOG.error(String.format("Cannot find registered DSD user %d: %s", userId, e.getMessage()));
				return;
			}
			userCache.put(userId, user);
		}
		StringBundler line = new StringBundler();
		line.append(registration.getTitle());
		line.append(',');
		line.append(DateUtil.getDate((Date) record.get("startTime"),"yyyy-MM-dd", locale));
		line.append(',');
		line.append(registration.getTopic());
		line.append(',');
		line.append(registration.getType());
		line.append(',');
		line.append(user.getEmailAddress());
		line.append(',');
		line.append(user.getFirstName());
		line.append(',');
		line.append(user.getLastName());
		line.append(',');
		Map<String, String> userPreferences;
		try {
			userPreferences = JsonContentUtils.parseJsonToMap((String) record.get("userPreferences"));
			String registrantKey = userPreferences.get("registrantKey");
			if (registrantKey != null) line.append(registrantKey);

			//Write billing information.
			for (KeycloakUtils.BILLING_ATTRIBUTES key : KeycloakUtils.BILLING_ATTRIBUTES.values()) {
				line.append(',');
				String value = userPreferences.get(key.name());
				if (value != null) line.append(value);
			}
		} catch (JSONException e) {
			LOG.error(String.format("Invalid userPreferences '%s': %s", record.get("userPreferences"), e.getMessage()));
		}
		writer.println(line);
	}

	private void writeError(String msg, ResourceResponse resourceResponse) throws IOException {
		resourceResponse.setContentType("application/json");
		PrintWriter writer = resourceResponse.getWriter();
		writer.print(JsonContentUtils.formatTextToJson("message", msg));
	}


}