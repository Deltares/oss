package nl.deltares.search.facet.checkbox;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import nl.deltares.portal.utils.DeltaresCacheUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author allan
 */
@Component(
        configurationPid = "nl.deltares.search.facet.checkbox.CheckboxFacetConfiguration",
        immediate = true,
        property = {
                "com.liferay.portlet.css-class-wrapper=portlet-checkbox-facet",
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/js/facet_util.js",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=CheckboxFacet",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.config-template=/facet/checkbox/configuration.jsp",
                "javax.portlet.init-param.view-template=/facet/checkbox/view.jsp",
                "javax.portlet.name=" + SearchModuleKeys.CHECKBOX_FACET_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user",
                "javax.portlet.version=3.0"
        },
        service = Portlet.class
)
public class CheckboxFacetPortlet extends MVCPortlet {

    @Reference
    private DeltaresCacheUtils deltaresCacheUtils;

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        final Map<String, Object> configuration = getConfiguration(themeDisplay);

        final Boolean visible = (Boolean) configuration.getOrDefault("visible", true);
        renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, visible);
        final String name = (String) configuration.get("name");
        renderRequest.setAttribute("name", name);
        @SuppressWarnings("unchecked") final Map<String, String> titleMap = (Map<String, String>) configuration.get("titleMap");
        if (titleMap != null) {
            renderRequest.setAttribute("title", titleMap.getOrDefault(themeDisplay.getLanguageId(), "Checkbox Title"));
        }
        final String selection = FacetUtils.getRequestParameter(name, renderRequest);
        if (selection != null) {
            renderRequest.setAttribute("selection", selection);
        }

        super.render(renderRequest, renderResponse);
    }

    private Map<String, Object> getConfiguration(ThemeDisplay themeDisplay) throws PortletException {

        final String id = themeDisplay.getPortletDisplay().getId();
        Map<String, Object> portletConfig = deltaresCacheUtils.findPortletConfig(id);
        if (portletConfig != null) {
            return portletConfig;
        }

        CheckboxFacetConfiguration _configuration;
        try {
            _configuration = _configurationProvider.getPortletInstanceConfiguration(
                    CheckboxFacetConfiguration.class, themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId());
        } catch (ConfigurationException e) {
            throw new PortletException(String.format("Could not get configuration for portlet '%s': %s", themeDisplay.getPortletDisplay().getId(), e.getMessage()), e);
        }

        String structureName = _configuration.structureName().toLowerCase();
        String fieldName = _configuration.fieldName();
        String name = structureName + '-' + fieldName; //important to use '-' because this translates to JSP id
        portletConfig = new HashMap<>();
        portletConfig.put("name", name); //important to use '-' because this translates to JSP id
        portletConfig.put("fieldName", fieldName);
        portletConfig.put("structureName", structureName);
        try {
            portletConfig.put("titleMap", JsonContentUtils.parseJsonToMap(_configuration.titleMap()));
        } catch (JSONException e) {
            //ignore
        }
        portletConfig.put("visible", Boolean.parseBoolean(_configuration.visible()));
        portletConfig.put("explicitSearch", Boolean.parseBoolean(_configuration.explicitSearch()));
        portletConfig.put("defaultValue", Boolean.parseBoolean(_configuration.defaultValue()));

        final Group scopeGroup = themeDisplay.getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        portletConfig.put("groupId", groupId);
        portletConfig.put("siteDefaultLocale", LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId()));

        deltaresCacheUtils.putPortletConfig(id, portletConfig);
        return portletConfig;
    }

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }
}