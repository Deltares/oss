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
import nl.deltares.portal.constants.OssConfigurationConstants;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

@Component(
        configurationPid = OssConstants.DSD_SITE_CONFIGURATIONS_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.DSD_ADMIN_FORM,
        },
        service = ConfigurationAction.class
)

public class DSDSiteConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                ConfigurationProvider.class.getName(),
                _configurationProvider);

        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
            httpServletRequest.setAttribute(OssConfigurationConstants.DSD_SITE_CONFIG_CONDITIONS_URL, getParameter(themeDisplay, _configurationProvider, OssConfigurationConstants.DSD_SITE_CONFIG_CONDITIONS_URL));
            httpServletRequest.setAttribute(OssConfigurationConstants.DSD_SITE_CONFIG_CONTACT_URL, getParameter(themeDisplay, _configurationProvider, OssConfigurationConstants.DSD_SITE_CONFIG_CONTACT_URL));
            httpServletRequest.setAttribute(OssConfigurationConstants.DSD_SITE_CONFIG_PRIVACY_URL, getParameter(themeDisplay, _configurationProvider, OssConfigurationConstants.DSD_SITE_CONFIG_PRIVACY_URL));
        } catch (PortalException e) {
            throw new PortletException("Could not get options for field 'registrationType' in structure SESSIONS: " + e.getMessage(), e);
        }

        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

        long organizationIdForStoringAccounts = ParamUtil.getLong(actionRequest, "organizationIdForStoringAccounts");
        long eventId = ParamUtil.getLong(actionRequest, "eventId");
        String registrationURL = ParamUtil.getString(actionRequest, "registrationURL");
        String busTransferURL = ParamUtil.getString(actionRequest, "busTransferURL");
        String travelStayURL = ParamUtil.getString(actionRequest, "travelStayURL");
        String conditionsURL = ParamUtil.getString(actionRequest, OssConfigurationConstants.DSD_SITE_CONFIG_CONDITIONS_URL);
        String privacyURL = ParamUtil.getString(actionRequest, OssConfigurationConstants.DSD_SITE_CONFIG_PRIVACY_URL);
        String contactURL = ParamUtil.getString(actionRequest, OssConfigurationConstants.DSD_SITE_CONFIG_CONTACT_URL);
        String sendFromEmail = ParamUtil.getString(actionRequest, "sendFromEmail");
        String replyToEmail = ParamUtil.getString(actionRequest, "replyToEmail");
        String bccToEmail = ParamUtil.getString(actionRequest, "bccToEmail");
        String cancellationReplyToEmail = ParamUtil.getString(actionRequest, "cancellationReplyToEmail");
        String mailingIds = ParamUtil.getString(actionRequest, "mailingIds");
        boolean isSendEmails = ParamUtil.getBoolean(actionRequest, "enableEmails");
        boolean isBustransfer = ParamUtil.getBoolean(actionRequest, "enableBusInfo");
        String dsdRegistrationStructures = ParamUtil.getString(actionRequest, "dsdRegistrationStructures");
        String dsdRegistrationDateField = ParamUtil.getString(actionRequest, "dsdRegistrationDateField");
        String dsdRegistrationTypeField = ParamUtil.getString(actionRequest, "dsdRegistrationTypeField");

        GroupServiceSettingsLocator groupServiceSettingsLocator = new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), DSDSiteConfiguration.class.getName());
        Settings settings = groupServiceSettingsLocator.getSettings();

        ModifiableSettings modifiableSettings =
                settings.getModifiableSettings();

        modifiableSettings.setValue("organizationIdForStoringAccounts", String.valueOf(organizationIdForStoringAccounts) );
        modifiableSettings.setValue("eventId", String.valueOf(eventId));
        modifiableSettings.setValue("registrationURL", registrationURL);
        modifiableSettings.setValue("busTransferURL", busTransferURL);
        modifiableSettings.setValue("travelStayURL", travelStayURL);
        modifiableSettings.setValue(OssConfigurationConstants.DSD_SITE_CONFIG_CONDITIONS_URL, conditionsURL);
        modifiableSettings.setValue(OssConfigurationConstants.DSD_SITE_CONFIG_PRIVACY_URL, privacyURL);
        modifiableSettings.setValue(OssConfigurationConstants.DSD_SITE_CONFIG_CONTACT_URL, contactURL);
        modifiableSettings.setValue("sendFromEmail", sendFromEmail);
        modifiableSettings.setValue("replyToEmail", replyToEmail);
        modifiableSettings.setValue("bccToEmail", bccToEmail);
        modifiableSettings.setValue("cancellationReplyToEmail", cancellationReplyToEmail);
        modifiableSettings.setValue("enableEmails", String.valueOf(isSendEmails));
        modifiableSettings.setValue("enableBusInfo", String.valueOf(isBustransfer));
        modifiableSettings.setValue("mailingIds", mailingIds);
        modifiableSettings.setValue("dsdRegistrationStructures", dsdRegistrationStructures);
        modifiableSettings.setValue("dsdRegistrationDateField", dsdRegistrationDateField);
        modifiableSettings.setValue("dsdRegistrationTypeField", dsdRegistrationTypeField);

        modifiableSettings.store();

        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    public static String getParameter(ThemeDisplay themeDisplay, ConfigurationProvider configurationProvider, String parameterId) throws PortalException {

        DSDSiteConfiguration siteConfiguration;
        try {
            siteConfiguration = configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId());

        } catch (ConfigurationException e) {
            throw new PortalException(String.format("Error getting DSD siteConfiguration: %s", e.getMessage()));
        }
        return switch (parameterId) {
            case OssConfigurationConstants.DSD_SITE_CONFIG_CONDITIONS_URL -> siteConfiguration.conditionsURL();
            case OssConfigurationConstants.DSD_SITE_CONFIG_CONTACT_URL -> siteConfiguration.contactURL();
            case OssConfigurationConstants.DSD_SITE_CONFIG_PRIVACY_URL -> siteConfiguration.privacyURL();
            default -> null;
        };

    }

}
