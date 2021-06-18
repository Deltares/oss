package nl.deltares.forms.portlet;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
	immediate = true,
	property = {
			"com.liferay.portlet.display-category=OSS",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.instanceable=false",
			"javax.portlet.display-name=DsdRegistrationForm",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/dsd_register.jsp",
			"javax.portlet.name=" + OssConstants.DSD_REGISTRATIONFORM,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.supported-locale=en",
			"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DsdRegistrationFormPortlet extends MVCPortlet {

	@Reference
	private KeycloakUtils keycloakUtils;

	@Reference
	private DsdParserUtils dsdParserUtils;

	@Reference
	private DsdSessionUtils dsdSessionUtils;

	@Reference
	private DDMStructureUtil _ddmStructureUtil;

	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();
		if (!user.isDefaultUser()) {
            try {
				Map<String, String> attributes = keycloakUtils.getUserAttributesFromCacheOrKeycloak(user);
				request.setAttribute("attributes", attributes);
            } catch (Exception e) {
				SessionErrors.add(request, "udate-attributes-failed", "Error reading attributes from keycloak! Check if keycloak is initialized: " + keycloakUtils.getAccountPath());
				request.setAttribute("attributes", new HashMap<>());
			}
			try {
				DSDSiteConfiguration dsdConfig = _configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
				List<String> mailingIdsList = Arrays.asList(dsdConfig.mailingIds().split(";"));
				request.setAttribute("subscribed", keycloakUtils.isSubscribed(user.getEmailAddress(), mailingIdsList));
			} catch (Exception e) {
				LOG.warn("Error getting user subscriptions: " + e.getMessage());
				request.setAttribute("subscribed", false);
			}

		}

		String action = ParamUtil.getString(request, "action");
		List<String> registrations = new ArrayList<>();
		String ids;

		if ("register".equals(action)) {
			ids = ParamUtil.getString(request, "ids");
			LOG.info(Arrays.toString(ids.split(",", -1)));
			registrations = new ArrayList<>(Arrays.asList(ids.split(",", -1)));
		} else {
			ids = ParamUtil.getString(request, "articleId");
			registrations.add(ids);
		}

		Optional<DDMTemplate> ddmTemplateOptional = _ddmStructureUtil
				.getDDMTemplateByName(themeDisplay.getScopeGroupId(), "REGISTRATION", themeDisplay.getLocale());

		ddmTemplateOptional.ifPresent(ddmTemplate ->
				request.setAttribute("ddmTemplateKey", ddmTemplate.getTemplateKey()));

		request.setAttribute("dsdParserUtils", dsdParserUtils);
		request.setAttribute("dsdSessionUtils", dsdSessionUtils);
		request.setAttribute("registrationList", registrations);
		request.setAttribute("ids", ids);

		request.setAttribute(ConfigurationProvider.class.getName(), _configurationProvider);
		super.render(request, response);
	}

	private ConfigurationProvider _configurationProvider;

	@Reference
	protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
		_configurationProvider = configurationProvider;
	}

	private static final Log LOG = LogFactoryUtil.getLog(DsdRegistrationFormPortlet.class);
}