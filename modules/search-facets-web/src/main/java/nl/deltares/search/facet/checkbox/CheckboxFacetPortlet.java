package nl.deltares.search.facet.checkbox;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import jakarta.portlet.*;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;

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

    @SuppressWarnings("unused")
    public void submitForm(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        CheckboxFacetConfiguration configuration = getConfiguration(themeDisplay);
        String name = configuration.structureName() + '-' + configuration.fieldName();
        String selection = ParamUtil.getString(actionRequest, "checkbox-facet-" + name);
        if ("undefined".equals(selection)) {
            FacetUtils.removeFromSession(themeDisplay.getPortletDisplay().getId(), name, actionRequest);
        } else {
            FacetUtils.storeInSession(themeDisplay.getPortletDisplay().getId(), name, selection, actionRequest);
        }
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        CheckboxFacetConfiguration configuration = getConfiguration(themeDisplay);

        final boolean visible = Boolean.parseBoolean(configuration.visible());
        if (!visible) {
            renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, false);
        }

        renderRequest.setAttribute("title", configuration.title());

        String name = configuration.structureName() + '-' + configuration.fieldName();
        renderRequest.setAttribute("name", name);
        String selection = FacetUtils.getRequestParameter(name, renderRequest);
        if (selection == null) {
            selection = (String) FacetUtils.getFromSession(themeDisplay.getPortletDisplay().getId(), name, renderRequest);
        }

        if (selection == null) {
            if (!visible){
                boolean defaultValue = Boolean.parseBoolean(configuration.defaultValue());
                selection = FacetUtils.serializeYesNo(defaultValue);

            } else {
                FacetUtils.removeFromSession(themeDisplay.getPortletDisplay().getId(), name, renderRequest);
            }
        }
        renderRequest.setAttribute("selection", selection);
        super.render(renderRequest, renderResponse);
    }

    private CheckboxFacetConfiguration getConfiguration(ThemeDisplay themeDisplay) throws PortletException {

        final String portletId = themeDisplay.getPortletDisplay().getId();
        try {
            return _configurationProvider.getPortletInstanceConfiguration(
                    CheckboxFacetConfiguration.class, themeDisplay.getLayout(), portletId);
        } catch (ConfigurationException e) {
            throw new PortletException(String.format("Could not get configuration for portlet '%s': %s", portletId, e.getMessage()), e);
        }
    }

    @Reference
    private ConfigurationProvider _configurationProvider;
}