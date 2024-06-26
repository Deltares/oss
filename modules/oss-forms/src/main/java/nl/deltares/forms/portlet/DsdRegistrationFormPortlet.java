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
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.*;

import static nl.deltares.portal.utils.LocalizationUtils.getLocalizedValue;

/**
 * @author rooij_e
 */
@Component(
		configurationPid = OssConstants.DSD_REGISTRATIONFORM_CONFIGURATIONS_PID,
	immediate = true,
	property = {
			"javax.portlet.version=3.0",
			"com.liferay.portlet.display-category=OSS",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.header-portlet-javascript=/lib/dsd-registration.js",
			"com.liferay.portlet.header-portlet-javascript=/lib/common.js",
			"com.liferay.portlet.instanceable=false",
			"javax.portlet.display-name=DsdRegistrationForm",
			"javax.portlet.init-param.config-template=/registration/configuration.jsp",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/registration/dsd_register.jsp",
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
	private EmailSubscriptionUtils subscriptionUtils;

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
				final Map<String, String> userAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
				request.setAttribute("attributes", userAttributes);
				//translate org vat code
				final String org_vat = userAttributes.get(KeycloakUtils.ATTRIBUTES.org_vat.name());
				if (org_vat != null) userAttributes.put("billing_vat", org_vat);
            } catch (Exception e) {
				SessionErrors.add(request, "update-attributes-failed", "Error reading user attributes: " + e.getMessage());
				request.setAttribute("attributes", new HashMap<>());
			}
			final String language = themeDisplay.getLocale().getLanguage();
			try {
				DSDSiteConfiguration dsdConfig = _configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
				request.setAttribute("conditionsURL", getLocalizedValue(dsdConfig.conditionsURL(), language));
				request.setAttribute("privacyURL", getLocalizedValue(dsdConfig.privacyURL(), language));
				request.setAttribute("contactURL", getLocalizedValue(dsdConfig.contactURL(), language));
				request.setAttribute("eventId", dsdConfig.eventId());
				List<String> mailingIdsList = Arrays.asList(dsdConfig.mailingIds().split(";"));
				request.setAttribute("subscriptionSelection", getSubscriptionSelection(user.getEmailAddress(), mailingIdsList));
				request.setAttribute("subscribed", subscriptionUtils.isSubscribed(user.getEmailAddress(), mailingIdsList));
			} catch (Exception e) {
				LOG.warn("Error getting DSDSiteConfiguration: " + e.getMessage());
				request.setAttribute("subscribed", false);
			}
			try {
				DsdRegistrationFormConfiguration dsdConfig = _configurationProvider.getGroupConfiguration(DsdRegistrationFormConfiguration.class, themeDisplay.getScopeGroupId());
				request.setAttribute("childHeaderText", getLocalizedValue(dsdConfig.childHeaderText(), language));
			} catch (Exception e) {
				LOG.warn("Error getting DsdRegistrationFormConfiguration: " + e.getMessage());
				request.setAttribute("childHeaderText", null);
			}
		}
		String action = ParamUtil.getString(request, "action");
		String ids = ParamUtil.getString(request, "ids");
		List<String> registrations = getRegistrations(action, ids, ParamUtil.getString(request, "articleId"));

		Optional<DDMTemplate> ddmTemplateOptional = _ddmStructureUtil
				.getDDMTemplateByName(themeDisplay.getScopeGroupId(), "REGISTRATION", themeDisplay.getLocale());

		ddmTemplateOptional.ifPresent(ddmTemplate ->
				request.setAttribute("ddmTemplateKey", ddmTemplate.getTemplateKey()));

		request.setAttribute("dsdParserUtils", dsdParserUtils);
		request.setAttribute("dsdSessionUtils", dsdSessionUtils);
		request.setAttribute("registrationList", registrations);
		request.setAttribute("ids", ids);
		request.setAttribute("callerAction", action);

		request.setAttribute(ConfigurationProvider.class.getName(), _configurationProvider);
		super.render(request, response);
	}

	private List<String> getRegistrations(String action, String ids, String articleId){
		if ("register".equals(action) && ids != null) {
			LOG.info(Arrays.toString(ids.split(",", -1)));
			return new ArrayList<>(Arrays.asList(ids.split(",", -1)));
		} else if (!articleId.isEmpty()) {
			return Collections.singletonList(articleId);
		} else {
			return Collections.emptyList();
		}
	}

	private List<SubscriptionSelection> getSubscriptionSelection(String email, List<String> configuredSubscriptionIds) {

		try {
			final List<SubscriptionSelection> subset = new ArrayList<>();
			final List<SubscriptionSelection> allSubscriptionSelections = subscriptionUtils.getSubscriptions(email);
			allSubscriptionSelections.forEach(s -> {
				if (configuredSubscriptionIds.contains(s.getId())) subset.add(s);
			});
			return subset;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private ConfigurationProvider _configurationProvider;

	@Reference
	protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
		_configurationProvider = configurationProvider;
	}

	private static final Log LOG = LogFactoryUtil.getLog(DsdRegistrationFormPortlet.class);
}