package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.utils.JsonContentUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

import static nl.deltares.portal.utils.LocalizationUtils.convertToLocalizedMap;
import static nl.deltares.portal.utils.LocalizationUtils.getAvailableLanguageIds;

@Component(
        configurationPid = OssConstants.DSD_REGISTRATIONFORM_CONFIGURATIONS_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.DSD_REGISTRATIONFORM
        },
        service = ConfigurationAction.class
)
public class DsdRegistrationFormConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                ConfigurationProvider.class.getName(),
                _configurationProvider);

        httpServletRequest.setAttribute("languageIds", getAvailableLanguageIds(httpServletRequest));
        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
            DsdRegistrationFormConfiguration siteConfiguration = _configurationProvider
                    .getGroupConfiguration(DsdRegistrationFormConfiguration.class, themeDisplay.getSiteGroupId());

            httpServletRequest.setAttribute("childHeaderText", getParsedJsonParameter(siteConfiguration, "childHeaderText"));
            httpServletRequest.setAttribute("registerSuccessURL", siteConfiguration.registerSuccessURL());
            httpServletRequest.setAttribute("unregisterSuccessURL", siteConfiguration.unregisterSuccessURL());
            httpServletRequest.setAttribute("updateSuccessURL", siteConfiguration.updateSuccessURL());
            httpServletRequest.setAttribute("failURL", siteConfiguration.failURL());
        } catch (PortalException e) {
            throw new PortalException("Could not get options for field 'registrationType' in structure SESSIONS: " + e.getMessage(), e);
        }
        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        Map<String, String> childHeaderText = convertToLocalizedMap(actionRequest, "childHeaderText");

        Settings settings = FallbackKeysSettingsUtil.getSettings(
                new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), DsdRegistrationFormConfiguration.class.getName()));

        ModifiableSettings modifiableSettings =
                settings.getModifiableSettings();
        modifiableSettings.setValue("registerSuccessURL", ParamUtil.getString(actionRequest, "registerSuccessURL"));
        modifiableSettings.setValue("unregisterSuccessURL", ParamUtil.getString(actionRequest, "unregisterSuccessURL"));
        modifiableSettings.setValue("updateSuccessURL", ParamUtil.getString(actionRequest, "updateSuccessURL"));
        modifiableSettings.setValue("failURL", ParamUtil.getString(actionRequest, "failURL"));
        modifiableSettings.setValue("childHeaderText", JsonContentUtils.formatMapToJson(childHeaderText));
        modifiableSettings.store();
        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }


    public static Map<String, String> getParsedJsonParameter(DsdRegistrationFormConfiguration configuration, String parameterId) throws PortalException {

        String json;
        if (parameterId.equals("childHeaderText")) {
            json = configuration.childHeaderText();
        } else {
            json = null;
        }
        try {
            return JsonContentUtils.parseJsonToMap(json);
        } catch (Exception e) {
            return Collections.emptyMap();
        }

    }
}
