package nl.deltares.search.facet.program;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
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
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        renderRequest.setAttribute(
                UserProgramFacetConfiguration.class.getName(),
                _configuration);
        super.doView(renderRequest, renderResponse);
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

        try {
            DSDSiteConfiguration configuration = _configurationProvider.
                    getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getSiteGroupId());
            if (configuration.eventId() > 0) {

                UserProgramFacetConfiguration portletConfiguration;
                try {
                    portletConfiguration = _configurationProvider.getPortletInstanceConfiguration(
                            UserProgramFacetConfiguration.class, themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId());
                } catch (ConfigurationException e) {
                    throw new PortletException(String.format("Could not get configuration for portlet '%s': %s", themeDisplay.getPortletDisplay().getId(), e.getMessage()), e);
                }

                final boolean hasMadeRegistrationsForOthers = dsdSessionUtils.hasUserRegistrationsMadeForOthers(themeDisplay.getUser(),
                        themeDisplay.getSiteGroupId(), configuration.eventId());
                final boolean hasLink = !portletConfiguration.linkToRegistrationsPageForOthers().isEmpty();
                if (!hasMadeRegistrationsForOthers || !hasLink) {
                    //only add when false otherwise it is always invisible
                    renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, hasMadeRegistrationsForOthers);
                }
                renderRequest.setAttribute("hasMadeRegistrationsForOthers", hasMadeRegistrationsForOthers);
            }

        } catch (ConfigurationException e) {
            renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, false);
            LOG.debug("Could not get event configuration", e);
        }
        super.render(renderRequest, renderResponse);
    }

    @Reference
    private DsdSessionUtils dsdSessionUtils;


    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                UserProgramFacetConfiguration.class, properties);
    }

    private volatile UserProgramFacetConfiguration _configuration;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(UserProgramFacetPortlet.class);
}
