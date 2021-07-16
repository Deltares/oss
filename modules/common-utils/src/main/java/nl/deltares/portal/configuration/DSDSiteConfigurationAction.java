package nl.deltares.portal.configuration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
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
import java.util.HashMap;
import java.util.Map;

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
            Map<String, String> typeMap = getTypeMap(themeDisplay, _configurationProvider);
            httpServletRequest.setAttribute("typeMap", typeMap);
        } catch (PortalException e) {
            throw new PortletException("Could not get options for field 'registrationType' in structure SESSIONS: " + e.getMessage(), e);
        }

        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

        long eventId = ParamUtil.getLong(actionRequest, "eventId");
        String registrationURL = ParamUtil.getString(actionRequest, "registrationURL");
        String busTransferURL = ParamUtil.getString(actionRequest, "busTransferURL");
        String travelStayURL = ParamUtil.getString(actionRequest, "travelStayURL");
        String conditionsURL = ParamUtil.getString(actionRequest, "conditionsURL");
        String privacyURL = ParamUtil.getString(actionRequest, "privacyURL");
        String contactURL = ParamUtil.getString(actionRequest, "contactURL");
        String sendFromEmail = ParamUtil.getString(actionRequest, "sendFromEmail");
        String replyToEmail = ParamUtil.getString(actionRequest, "replyToEmail");
        String bccToEmail = ParamUtil.getString(actionRequest, "bccToEmail");
        String mailingIds = ParamUtil.getString(actionRequest, "mailingIds");
        boolean isSendEmails = ParamUtil.getBoolean(actionRequest, "enableEmails");
        boolean isBustransfer = ParamUtil.getBoolean(actionRequest, "enableBusInfo");
        boolean isDsdSite = ParamUtil.getBoolean(actionRequest, "dsdSite");
        String dsdRegistrationStructures = ParamUtil.getString(actionRequest, "dsdRegistrationStructures");
        String dsdRegistrationDateField = ParamUtil.getString(actionRequest, "dsdRegistrationDateField");
        String dsdRegistrationTypeField = ParamUtil.getString(actionRequest, "dsdRegistrationTypeField");

        Map<String, String> typeMap = convertTypesToMap(actionRequest);

        Settings settings = SettingsFactoryUtil.getSettings(
                new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), DSDSiteConfiguration.class.getName()));

        ModifiableSettings modifiableSettings =
                settings.getModifiableSettings();

        modifiableSettings.setValue("eventId", String.valueOf(eventId));
        modifiableSettings.setValue("registrationURL", registrationURL);
        modifiableSettings.setValue("busTransferURL", busTransferURL);
        modifiableSettings.setValue("travelStayURL", travelStayURL);
        modifiableSettings.setValue("conditionsURL", conditionsURL);
        modifiableSettings.setValue("privacyURL", privacyURL);
        modifiableSettings.setValue("contactURL", contactURL);
        modifiableSettings.setValue("sendFromEmail", sendFromEmail);
        modifiableSettings.setValue("replyToEmail", replyToEmail);
        modifiableSettings.setValue("bccToEmail", bccToEmail);
        modifiableSettings.setValue("enableEmails", String.valueOf(isSendEmails));
        modifiableSettings.setValue("enableBusInfo", String.valueOf(isBustransfer));
        modifiableSettings.setValue("dsdSite", String.valueOf(isDsdSite));
        modifiableSettings.setValue("mailingIds", mailingIds);
        modifiableSettings.setValue("dsdRegistrationStructures", dsdRegistrationStructures);
        modifiableSettings.setValue("dsdRegistrationDateField", dsdRegistrationDateField);
        modifiableSettings.setValue("dsdRegistrationTypeField", dsdRegistrationTypeField);
        modifiableSettings.setValue( "typeMap", JsonContentUtils.formatMapToJson(typeMap));

        modifiableSettings.store();

        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private Map<String, String> convertTypesToMap(ActionRequest actionRequest) {

        HashMap<String, String> map = new HashMap<>();
        int row = 0;
        String portletId;
        while(!(portletId = ParamUtil.getString(actionRequest, "portletId-" + row)).isEmpty()){
            if (!portletId.startsWith("enter")) {
                final String type = ParamUtil.getString(actionRequest, "type-" + row);
                map.put(portletId, type);
            }
            row++;
        }
        return map;
    }

    public static Map<String, String> getTypeMap(ThemeDisplay themeDisplay, ConfigurationProvider configurationProvider) throws PortalException {

        DSDSiteConfiguration siteConfiguration;
        try {
            siteConfiguration = configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getSiteGroupId());

        } catch (ConfigurationException e) {
            throw new PortalException(String.format("Error getting DSD siteConfiguration: %s", e.getMessage()));
        }
        String typeMapJson = siteConfiguration.typeMap();
        return JsonContentUtils.parseJsonToMap(typeMapJson);
    }
}
