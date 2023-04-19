package nl.deltares.forms.portlet;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.json.JSONException;
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
import org.osgi.service.component.annotations.ReferenceCardinality;

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
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/download.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=Download Form",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/download/download.jsp",
                "javax.portlet.name=" + OssConstants.DOWNLOADFORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=user"
        },
        service = Portlet.class
)
public class DownloadFormPortlet extends MVCPortlet {


    //TODO: prepare for migration to sendinblue
    private EmailSubscriptionUtils subscriptionUtils;
    private KeycloakUtils keycloakUtils;
    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.MANDATORY
    )
    protected void setKeycloakUtils(KeycloakUtils keycloakUtils) {

        if (keycloakUtils.isActive()){
            this.keycloakUtils = keycloakUtils;
            this.subscriptionUtils = (EmailSubscriptionUtils) keycloakUtils;
        }
    }

    @Reference
    private DsdParserUtils dsdParserUtils;

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(DownloadFormPortlet.class);

    @Override
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
                SessionErrors.add(request, "retrieve-attributes-failed", "Error reading user attributes: " + e.getMessage());
                request.setAttribute("attributes", new HashMap<>());
            }
            try {
                final String language = themeDisplay.getLocale().getLanguage();
                DSDSiteConfiguration dsdConfig = _configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
                request.setAttribute("privacyURL", getLocalizedValue(dsdConfig.privacyURL(), language));
                request.setAttribute("contactURL", getLocalizedValue(dsdConfig.contactURL(), language));
            } catch (Exception e) {
                LOG.warn("Error getting configuration: " + e.getMessage());
            }
        }

        String action = ParamUtil.getString(request, "action");
        List<String> downloads = new ArrayList<>();
        String ids;

        if ("download".equals(action)) {
            ids = ParamUtil.getString(request, "ids");
            LOG.info(Arrays.toString(ids.split(",", -1)));
            downloads = new ArrayList<>(Arrays.asList(ids.split(",", -1)));
        } else {
            ids = ParamUtil.getString(request, "articleId");
            downloads.add(ids);
        }

        Optional<DDMTemplate> ddmTemplateOptional = _ddmStructureUtil
                .getDDMTemplateByName(themeDisplay.getScopeGroupId(), "DOWNLOAD-FORM", themeDisplay.getLocale());

        ddmTemplateOptional.ifPresent(ddmTemplate ->
                request.setAttribute("ddmTemplateKey", ddmTemplate.getTemplateKey()));

        request.setAttribute("dsdParserUtils", dsdParserUtils);
        request.setAttribute("subscriptionUtils", subscriptionUtils);
        request.setAttribute("downloadList", downloads);
        request.setAttribute("ids", ids);

        request.setAttribute(ConfigurationProvider.class.getName(), _configurationProvider);
        super.render(request, response);
    }
}