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
import java.util.*;

import static nl.deltares.portal.utils.LocalizationUtils.convertToLocalizedMap;
import static nl.deltares.portal.utils.LocalizationUtils.getAvailableLanguageIds;

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
            httpServletRequest.setAttribute("templateMap", getParsedJsonParameter(themeDisplay, _configurationProvider, "templateMap"));
            httpServletRequest.setAttribute("conditionsURL", getParsedJsonParameter(themeDisplay, _configurationProvider, "conditionsURL"));
            httpServletRequest.setAttribute("contactURL", getParsedJsonParameter(themeDisplay, _configurationProvider, "contactURL"));
            httpServletRequest.setAttribute("privacyURL", getParsedJsonParameter(themeDisplay, _configurationProvider, "privacyURL"));
            httpServletRequest.setAttribute("languageIds", getAvailableLanguageIds(httpServletRequest));
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
        Map<String,String> conditionsURL = convertToLocalizedMap(actionRequest, "conditionsURL");
        Map<String,String> privacyURL = convertToLocalizedMap(actionRequest, "privacyURL");
        Map<String,String> contactURL = convertToLocalizedMap(actionRequest, "contactURL");
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

        Map<String, String> templateMap = convertTemplatesToMap(actionRequest);

        Settings settings = SettingsFactoryUtil.getSettings(
                new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), DSDSiteConfiguration.class.getName()));

        ModifiableSettings modifiableSettings =
                settings.getModifiableSettings();

        modifiableSettings.setValue("eventId", String.valueOf(eventId));
        modifiableSettings.setValue("registrationURL", registrationURL);
        modifiableSettings.setValue("busTransferURL", busTransferURL);
        modifiableSettings.setValue("travelStayURL", travelStayURL);
        modifiableSettings.setValue("conditionsURL", JsonContentUtils.formatMapToJson(conditionsURL));
        modifiableSettings.setValue("privacyURL", JsonContentUtils.formatMapToJson(privacyURL));
        modifiableSettings.setValue("contactURL", JsonContentUtils.formatMapToJson(contactURL));
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
        modifiableSettings.setValue("templateMap", JsonContentUtils.formatMapToJson(templateMap));

        modifiableSettings.store();

        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }


    private Map<String, String> convertTemplatesToMap(ActionRequest actionRequest) {

        HashMap<String, String> map = new HashMap<>();
        int row = 0;
        String portletId;
        while(!(portletId = ParamUtil.getString(actionRequest, "portletId-" + row)).isEmpty()){
            if (!portletId.startsWith("enter")) {
                final String type = ParamUtil.getString(actionRequest, "templateId-" + row);
                map.put(portletId, type);
            }
            row++;
        }
        return map;
    }

    public static Map<String, String> getParsedJsonParameter(ThemeDisplay themeDisplay, ConfigurationProvider configurationProvider, String parameterId) throws PortalException {

        DSDSiteConfiguration siteConfiguration;
        try {
            siteConfiguration = configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getSiteGroupId());

        } catch (ConfigurationException e) {
            throw new PortalException(String.format("Error getting DSD siteConfiguration: %s", e.getMessage()));
        }
        String json;
        switch (parameterId){
            case "templateMap":
                json = siteConfiguration.templateMap();
                break;
            case "conditionsURL":
                json = siteConfiguration.conditionsURL();
                break;
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
