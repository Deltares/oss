package nl.deltares.search.facet.selection;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

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


  @Override
  public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

    ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
    SelectionFacetConfiguration configuration;
    try {
      configuration = _configurationProvider.getPortletInstanceConfiguration(
              SelectionFacetConfiguration.class, themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId());
    } catch (ConfigurationException e) {
      throw new PortletException(String.format("Could not get configuration for portlet '%s': %s", themeDisplay.getPortletDisplay().getId(), e.getMessage()), e);
    }

    String structureName = configuration.structureName().toLowerCase();
    String fieldName = configuration.fieldName();
    String name = structureName + '-' + fieldName; //important to use '-' because this translates to JSP id

    renderRequest.setAttribute("name", name);
    renderRequest.setAttribute("title", configuration.title());

    PortletSharedSearchResponse portletSharedSearchResponse = portletSharedSearchRequest.search(renderRequest);
    Optional<String> facetSelection = portletSharedSearchResponse.getParameter(name, renderRequest);

    facetSelection.ifPresentOrElse(s -> renderRequest.setAttribute("selection", s), () ->
            {
              //check for parameter is in namespace of searchResultsPortlet
              final String selection = FacetUtils.getIteratorParameter(name, renderRequest);
              if (selection != null) renderRequest.setAttribute("selection", selection);
            }
    );

    try {
      Map<String, String> selectionMap = dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
              structureName,
              fieldName, themeDisplay.getLocale());
      renderRequest.setAttribute("selectionMap", selectionMap);
    } catch (PortalException e) {
      throw new PortletException(String.format("Could not get options for field '%s' in structure %s: %s", fieldName, structureName, e.getMessage()), e);
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