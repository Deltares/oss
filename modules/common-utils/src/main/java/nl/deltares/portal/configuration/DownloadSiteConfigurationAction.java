package nl.deltares.portal.configuration;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
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
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static nl.deltares.portal.utils.LocalizationUtils.convertToLocalizedMap;
import static nl.deltares.portal.utils.LocalizationUtils.getAvailableLanguageIds;

@Component(
        configurationPid = OssConstants.Download_SITE_CONFIGURATIONS_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.DOWNLOAD_ADMIN_FORM,
        },
        service = ConfigurationAction.class
)

public class DownloadSiteConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                ConfigurationProvider.class.getName(),
                _configurationProvider);

        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
            httpServletRequest.setAttribute("contactURL", getParsedJsonParameter(themeDisplay, _configurationProvider, "contactURL"));
            httpServletRequest.setAttribute("privacyURL", getParsedJsonParameter(themeDisplay, _configurationProvider, "privacyURL"));
            httpServletRequest.setAttribute("languageIds", getAvailableLanguageIds(httpServletRequest));

        } catch (PortalException e) {
            throw new PortletException("Could not get configuration for DownloadSiteConfiguration: " + e.getMessage(), e);
        }

        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

        String downloadURL = ParamUtil.getString(actionRequest, "downloadURL");
        Map<String,String> privacyURL = convertToLocalizedMap(actionRequest, "privacyURL");
        Map<String,String> contactURL = convertToLocalizedMap(actionRequest, "contactURL");
        String sendFromEmail = ParamUtil.getString(actionRequest, "sendFromEmail");
        String replyToEmail = ParamUtil.getString(actionRequest, "replyToEmail");
        String bccToEmail = ParamUtil.getString(actionRequest, "bccToEmail");
        String bannerURL = ParamUtil.getString(actionRequest, "bannerURL");
        boolean isSendEmails = ParamUtil.getBoolean(actionRequest, "enableEmails");

        Settings settings = FallbackKeysSettingsUtil.getSettings(
                new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), DownloadSiteConfiguration.class.getName()));

        ModifiableSettings modifiableSettings =
                settings.getModifiableSettings();

        modifiableSettings.setValue("downloadURL", downloadURL);
        modifiableSettings.setValue("privacyURL", JsonContentUtils.formatMapToJson(privacyURL));
        modifiableSettings.setValue("contactURL", JsonContentUtils.formatMapToJson(contactURL));
        modifiableSettings.setValue("bannerURL", bannerURL);
        modifiableSettings.setValue("sendFromEmail", sendFromEmail);
        modifiableSettings.setValue("replyToEmail", replyToEmail);
        modifiableSettings.setValue("bccToEmail", bccToEmail);
        modifiableSettings.setValue("enableEmails", String.valueOf(isSendEmails));
        modifiableSettings.store();

        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    public static Map<String, String> getParsedJsonParameter(ThemeDisplay themeDisplay, ConfigurationProvider configurationProvider, String parameterId) throws PortalException {

        DownloadSiteConfiguration siteConfiguration;
        try {
            siteConfiguration = configurationProvider
                    .getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getSiteGroupId());

        } catch (ConfigurationException e) {
            throw new PortalException(String.format("Error getting DSD siteConfiguration: %s", e.getMessage()));
        }
        String json;
        switch (parameterId){
            case "contactURL":
                json = siteConfiguration.contactURL();
                break;
            case "privacyURL":
                json = siteConfiguration.privacyURL();
                break;
            default:
                json = null ;
        }
        try {
            return JsonContentUtils.parseJsonToMap(json);
        } catch (Exception e){
            return Collections.emptyMap();
        }

    }
}
