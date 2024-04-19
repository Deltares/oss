package nl.deltares.search.facet.checkbox;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Optional;

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


    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        CheckboxFacetConfiguration _configuration;
        try {
            _configuration = _configurationProvider.getPortletInstanceConfiguration(
                    CheckboxFacetConfiguration.class, themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId());
        } catch (ConfigurationException e) {
            throw new PortletException(String.format("Could not get configuration for portlet '%s': %s", themeDisplay.getPortletDisplay().getId(), e.getMessage()), e);
        }
        final boolean visible = Boolean.parseBoolean(_configuration.visible());
        if (!visible) renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, false);

        String structureName = _configuration.structureName().toLowerCase();
        String fieldName = _configuration.fieldName();
        String name = structureName + '-' + fieldName; //important to use '-' because this translates to JSP id

        renderRequest.setAttribute("name", name);
        renderRequest.setAttribute("title", FacetUtils.retrieveLanguageFieldValue(_configuration.titleMap(), themeDisplay.getLanguageId()));

        PortletSharedSearchResponse portletSharedSearchResponse = portletSharedSearchRequest.search(renderRequest);
        Optional<String> facetSelection = Optional.of(portletSharedSearchResponse.getParameter(name, renderRequest));
        if (facetSelection.isPresent()) {
            renderRequest.setAttribute("selection", facetSelection.get());
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
}