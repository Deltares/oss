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
        configurationPid = OssConstants.WEBINAR_SITE_CONFIGURATIONS_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.WEBINAR_ADMIN_FORM,
        },
        service = ConfigurationAction.class
)

public class WebinarSiteConfigurationAction extends DefaultConfigurationAction {

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

        String gotoURL = ParamUtil.getString(actionRequest, "gotoURL");
        String gotoClientId = ParamUtil.getString(actionRequest, "gotoClientId");
        String gotoClientSecret = ParamUtil.getString(actionRequest, "gotoClientSecret");
        String gotoUserName = ParamUtil.getString(actionRequest, "gotoUserName");
        String gotoUserPassword = ParamUtil.getString(actionRequest, "gotoUserPassword");
        boolean gotoCacheToken = ParamUtil.getBoolean(actionRequest, "gotoCacheToken");
        boolean gotoCacheResponse = ParamUtil.getBoolean(actionRequest, "gotoCacheResponse");

        String aNewSpringURL = ParamUtil.getString(actionRequest, "aNewSpringURL");
        String aNewSpringApiKey = ParamUtil.getString(actionRequest, "aNewSpringApiKey");
        boolean aNewSpringCacheToken = ParamUtil.getBoolean(actionRequest, "aNewSpringCacheToken");

        Settings settings = SettingsFactoryUtil.getSettings(
                new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), WebinarSiteConfiguration.class.getName()));

        ModifiableSettings modifiableSettings =
                settings.getModifiableSettings();

        modifiableSettings.setValue("gotoURL", gotoURL);
        modifiableSettings.setValue("gotoClientId", gotoClientId);
        modifiableSettings.setValue("gotoClientSecret", gotoClientSecret);
        modifiableSettings.setValue("gotoUserName", gotoUserName);
        modifiableSettings.setValue("gotoUserPassword", gotoUserPassword);
        modifiableSettings.setValue("gotoCacheToken", String.valueOf(gotoCacheToken));
        modifiableSettings.setValue("gotoCacheResponse", String.valueOf(gotoCacheResponse));

        modifiableSettings.setValue("aNewSpringURL", aNewSpringURL);
        modifiableSettings.setValue("aNewSpringApiKey", aNewSpringApiKey);
        modifiableSettings.setValue("aNewSpringCacheToken", String.valueOf(aNewSpringCacheToken));

        modifiableSettings.store();

        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

}
