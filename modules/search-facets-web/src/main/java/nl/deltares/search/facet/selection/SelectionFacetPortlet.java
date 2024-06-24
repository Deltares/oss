package nl.deltares.search.facet.selection;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import nl.deltares.portal.utils.DeltaresCacheUtils;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author allan
 */
@Component(
        configurationPid = "nl.deltares.search.facet.selection.SelectionFacetConfiguration",
  immediate = true,
  property = {
    "com.liferay.portlet.css-class-wrapper=portlet-selection-facet",
    "com.liferay.portlet.display-category=OSS-search",
    "com.liferay.portlet.header-portlet-css=/css/main.css",
    "com.liferay.portlet.header-portlet-javascript=/js/facet_util.js",
    "com.liferay.portlet.instanceable=true",
    "javax.portlet.display-name=SelectionFacet",
    "javax.portlet.expiration-cache=0",
    "javax.portlet.init-param.template-path=/",
    "javax.portlet.init-param.config-template=/facet/selection/configuration.jsp",
    "javax.portlet.init-param.view-template=/facet/selection/view.jsp",
    "javax.portlet.name=" + SearchModuleKeys.SELECTION_FACET_PORTLET,
    "javax.portlet.resource-bundle=content.Language",
    "javax.portlet.security-role-ref=power-user,user",
          "javax.portlet.version=3.0"
  },
  service = Portlet.class
)
public class SelectionFacetPortlet extends MVCPortlet {

    @Reference
    private DeltaresCacheUtils deltaresCacheUtils;


    private Map<String, Object> getConfiguration(RenderRequest renderRequest) throws PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        final String id = themeDisplay.getPortletDisplay().getId();
        Map<String, Object> portletConfig = deltaresCacheUtils.findPortletConfig(id);
        if (portletConfig != null) {
            return portletConfig;
        }

        SelectionFacetConfiguration configuration;
        try {
            configuration = _configurationProvider.getPortletInstanceConfiguration(
                    SelectionFacetConfiguration.class, themeDisplay.getLayout(), id);
        } catch (ConfigurationException e) {
            throw new PortletException(String.format("Could not get configuration for portlet '%s': %s", id, e.getMessage()), e);
        }

        String structureName = configuration.structureName().toLowerCase();
        String fieldName = configuration.fieldName();
        final String name = structureName + '-' + fieldName;
        portletConfig = new HashMap<>();
        portletConfig.put("name", name); //important to use '-' because this translates to JSP id
        portletConfig.put("fieldName", fieldName);
        portletConfig.put("structureName", structureName);
        portletConfig.put("title", FacetUtils.retrieveLanguageFieldValue(configuration.titleMap(), themeDisplay.getLanguageId()));
        portletConfig.put("titleMap", configuration.titleMap());

        final Group scopeGroup = themeDisplay.getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        portletConfig.put("groupId", groupId);
        portletConfig.put("siteDefaultLocale", LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId()));

        try {
            portletConfig.put("selectionMap", dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
                    structureName,
                    fieldName, themeDisplay.getLocale()));
        } catch (PortalException e) {
            throw new PortletException(String.format("Could not get options for field '%s' in structure %s: %s", fieldName, structureName, e.getMessage()), e);
        }
        deltaresCacheUtils.putPortletConfig(id, portletConfig);

        return portletConfig;
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final Map<String, Object> configuration = getConfiguration(renderRequest);
        final String name = (String) configuration.get("name");
        renderRequest.setAttribute("name", name);
        renderRequest.setAttribute("titleMap", configuration.get("titleMap"));
        renderRequest.setAttribute("title", configuration.get("title"));
        @SuppressWarnings("unchecked") final Map<String, String> selectionMap = (Map<String, String>) configuration.get("selectionMap");
        if (selectionMap != null && !selectionMap.isEmpty()) renderRequest.setAttribute("selectionMap", selectionMap);

        final String selection = FacetUtils.getRequestParameter(name, renderRequest);
        if (selection != null) {
            renderRequest.setAttribute("selection", selection);
        }

        super.render(renderRequest, renderResponse);
    }

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;
}