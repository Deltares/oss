package nl.deltares.portal.configuration;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletConfig;
import jakarta.portlet.PortletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

@Component(
        configurationPid = OssConstants.Download_SITE_CONFIGURATIONS_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "jakarta.portlet.name=" + OssConstants.DOWNLOAD_ADMIN_FORM,
        },
        service = ConfigurationAction.class
)

public class DownloadSiteConfigurationAction extends DefaultConfigurationAction {

    private static final String CONTACT_URL = "contactURL";
    private static final String PRIVACY_URL = "privacyURL";

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                ConfigurationProvider.class.getName(),
                _configurationProvider);

        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
            httpServletRequest.setAttribute(CONTACT_URL, getParameter(themeDisplay, _configurationProvider, CONTACT_URL));
            httpServletRequest.setAttribute(PRIVACY_URL, getParameter(themeDisplay, _configurationProvider, PRIVACY_URL));

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
        String privacyURL = ParamUtil.getString(actionRequest, PRIVACY_URL);
        String contactURL = ParamUtil.getString(actionRequest, CONTACT_URL);
        String sendFromEmail = ParamUtil.getString(actionRequest, "sendFromEmail");
        String replyToEmail = ParamUtil.getString(actionRequest, "replyToEmail");
        String bccToEmail = ParamUtil.getString(actionRequest, "bccToEmail");
        String bannerURL = ParamUtil.getString(actionRequest, "bannerURL");
        boolean isSendEmails = ParamUtil.getBoolean(actionRequest, "enableEmails");

        GroupServiceSettingsLocator groupServiceSettingsLocator = new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), DownloadSiteConfiguration.class.getName());
        Settings settings = groupServiceSettingsLocator.getSettings();

        ModifiableSettings modifiableSettings =
                settings.getModifiableSettings();

        modifiableSettings.setValue("downloadURL", downloadURL);
        modifiableSettings.setValue(PRIVACY_URL, privacyURL);
        modifiableSettings.setValue(CONTACT_URL, contactURL);
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

    public static String getParameter(ThemeDisplay themeDisplay, ConfigurationProvider configurationProvider, String parameterId) throws PortalException {

        DownloadSiteConfiguration siteConfiguration;
        try {
            siteConfiguration = configurationProvider
                    .getGroupConfiguration(DownloadSiteConfiguration.class,themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId());

        } catch (ConfigurationException e) {
            throw new PortalException(String.format("Error getting DSD siteConfiguration: %s", e.getMessage()));
        }
        return switch (parameterId) {
            case CONTACT_URL -> siteConfiguration.contactURL();
            case PRIVACY_URL -> siteConfiguration.privacyURL();
            default -> null;
        };

    }
}
