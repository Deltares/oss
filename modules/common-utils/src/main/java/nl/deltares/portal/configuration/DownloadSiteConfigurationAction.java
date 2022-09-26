package nl.deltares.portal.configuration;

import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

        String downloadURL = ParamUtil.getString(actionRequest, "downloadURL");
        String privacyURL = ParamUtil.getString(actionRequest, "privacyURL");
        String contactURL = ParamUtil.getString(actionRequest, "contactURL");
        String sendFromEmail = ParamUtil.getString(actionRequest, "sendFromEmail");
        String replyToEmail = ParamUtil.getString(actionRequest, "replyToEmail");
        String bccToEmail = ParamUtil.getString(actionRequest, "bccToEmail");
        String bannerURL = ParamUtil.getString(actionRequest, "bannerURL");
        boolean isSendEmails = ParamUtil.getBoolean(actionRequest, "enableEmails");

        Settings settings = SettingsFactoryUtil.getSettings(
                new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), DownloadSiteConfiguration.class.getName()));

        ModifiableSettings modifiableSettings =
                settings.getModifiableSettings();

        modifiableSettings.setValue("downloadURL", downloadURL);
        modifiableSettings.setValue("privacyURL", privacyURL);
        modifiableSettings.setValue("contactURL", contactURL);
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
}
