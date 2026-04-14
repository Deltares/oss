package nl.deltares.search.facet.program;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.*;

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
            renderRequest.setAttribute("selection", String.valueOf(themeDisplay.getCompanyId()));
        } else {
            //First retrieve from request URL then try from session.
            String selection = FacetUtils.getRequestParameter("user-program-facet-selection", renderRequest);
            if (selection == null) {
                selection = FacetUtils.getFromSession(themeDisplay.getPortletDisplay().getId(), "user-program-facet-selection", renderRequest);
            }
            if (selection != null) {
                renderRequest.setAttribute("selection", selection);
            }
        }

        renderRequest.setAttribute("selectionMap", convertToOptions(configuration.companyIds(), themeDisplay.getCompany()));
        renderRequest.setAttribute("showRegistrationsMadeForOthers", configuration.showRegistrationsMadeForOthers());
        super.render(renderRequest, renderResponse);
    }

    private Map<String, String> convertToOptions(String companyIds, Company currentCompany) {
        if (companyIds == null || companyIds.isEmpty()) {
            return Collections.singletonMap(String.valueOf(currentCompany.getCompanyId()), currentCompany.getName());
        }

        Map<String, String> options = new HashMap<>();
        String[] ids = companyIds.split(" ");
        for (String id : ids) {
            Company company = CompanyLocalServiceUtil.fetchCompany(Long.parseLong(id));
            options.put(String.valueOf(company.getCompanyId()), company.getName());
        }
        return options;

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
        String selection = ParamUtil.getString(actionRequest, "user-program-facet-selection");

        if ("undefined".equals(selection)) {
            FacetUtils.removeFromSession(themeDisplay.getPortletDisplay().getId(), "user-program-facet-selection", actionRequest);
        } else {
            FacetUtils.storeInSession(themeDisplay.getPortletDisplay().getId(), "user-program-facet-selection", selection, actionRequest);
        }
    }
    @Reference
    private ConfigurationProvider _configurationProvider;

}
