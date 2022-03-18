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
import nl.deltares.portal.configuration.DownloadSiteConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=Download Form",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/download.jsp",
                "javax.portlet.name=" + OssConstants.DOWNLOADFORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=user"
        },
        service = Portlet.class
)
public class DownloadFormPortlet extends MVCPortlet {


    @Reference
    private KeycloakUtils keycloakUtils;

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
                DownloadSiteConfiguration dsdConfig = _configurationProvider.getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());
                List<String> mailingIdsList = Arrays.asList(dsdConfig.mailingIds().split(";"));
                request.setAttribute("subscribed", keycloakUtils.isSubscribed(user.getEmailAddress(), mailingIdsList));
            } catch (Exception e) {
                LOG.warn("Error getting user subscriptions: " + e.getMessage());
                request.setAttribute("subscribed", false);
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
        request.setAttribute("downloadList", downloads);
        request.setAttribute("ids", ids);

        request.setAttribute(ConfigurationProvider.class.getName(), _configurationProvider);
        super.render(request, response);
    }
}