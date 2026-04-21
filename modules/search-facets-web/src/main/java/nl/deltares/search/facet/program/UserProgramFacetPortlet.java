package nl.deltares.search.facet.program;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(
        configurationPid = "nl.deltares.search.facet.program.UserProgramFacetConfiguration",
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=UserProgramFacet",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.config-template=/facet/program/configuration.jsp",
                "javax.portlet.init-param.view-template=/facet/program/view.jsp",
                "javax.portlet.name=" + SearchModuleKeys.USER_PROGRAM_FACET_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user",
                "javax.portlet.version=3.0"
        },
        service = Portlet.class
)
public class UserProgramFacetPortlet extends MVCPortlet {

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        UserProgramFacetConfiguration configuration = getConfiguration(themeDisplay);

        final boolean visible = Boolean.parseBoolean(configuration.visible());
        if (!visible) {
            renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, false);
            renderRequest.setAttribute("site-selection", String.valueOf(themeDisplay.getSiteGroupId()));
        } else {

            String siteSelection = ParamUtil.getString(renderRequest, "user-program-site-selection", String.valueOf(themeDisplay.getSiteGroupId()));
            renderRequest.setAttribute("site-selection", siteSelection);

            Map<Long, List<Long>> registrationSiteIds = _dsdSessionUtils.getRegistrationSiteIds();
            renderRequest.setAttribute("site-selectionMap", convertToOptions(registrationSiteIds, themeDisplay.getSiteGroup()));
        }
        renderRequest.setAttribute("showRegistrationsMadeForOthers", configuration.showRegistrationsMadeForOthers());
        super.render(renderRequest, renderResponse);
    }

    private Map<String, String> convertToOptions(Map<Long, List<Long>> sitesMap, Group currentSite) throws PortletException {
        if (sitesMap == null || sitesMap.isEmpty()) {
            try {
                return Collections.singletonMap(String.valueOf(currentSite.getGroupId()), currentSite.getDescriptiveName());
            } catch (PortalException e) {
                throw new PortletException(e);
            }
        }

        Map<String, String> siteNames = new HashMap<>();
        for (Long siteGroupId : sitesMap.keySet()) {
            List<Long> siteChildIds = sitesMap.get(siteGroupId);
            for (Long siteChildId : siteChildIds) {
                Group group = GroupLocalServiceUtil.fetchGroup(siteChildId);
                if (group != null) {
                    try {
                        siteNames.put(String.valueOf(siteChildId), group.getDescriptiveName());
                    } catch (PortalException e) {
                        throw new PortletException(e);
                    }
                }
            }
        }
        return siteNames;

    }

    private UserProgramFacetConfiguration getConfiguration(ThemeDisplay themeDisplay) throws PortletException {

        final String portletId = themeDisplay.getPortletDisplay().getId();
        try {
            return _configurationProvider.getPortletInstanceConfiguration(
                    UserProgramFacetConfiguration.class, themeDisplay.getLayout(), portletId);
        } catch (ConfigurationException e) {
            throw new PortletException(String.format("Could not get configuration for portlet '%s': %s", portletId, e.getMessage()), e);
        }
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void submitForm(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        final UserProgramFacetConfiguration configuration = getConfiguration(themeDisplay);
        String selection = ParamUtil.getString(actionRequest, "user-program-site-selection");
        if (selection == null || selection.isEmpty()) return;
        actionRequest.setAttribute("site-selection", String.valueOf(themeDisplay.getSiteGroupId()));
    }
    @Reference
    private ConfigurationProvider _configurationProvider;

    @Reference
    private DsdSessionUtils _dsdSessionUtils;
}
