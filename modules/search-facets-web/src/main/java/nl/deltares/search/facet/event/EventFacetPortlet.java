package nl.deltares.search.facet.event;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;
import jakarta.portlet.Portlet;
import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;
import nl.deltares.search.constans.SearchModuleKeys;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import java.io.IOException;
import java.util.Map;

/**
 * @author allan
 */
@Component(
        configurationPid = "nl.deltares.search.facet.registration.EventFacetConfiguration",
        immediate = true,
        property = {
                "com.liferay.portlet.css-class-wrapper=portlet-event-facet",
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "jakarta.portlet.display-name=EventFacet",
                "jakarta.portlet.expiration-cache=0",
                "jakarta.portlet.init-param.template-path=/",
                "jakarta.portlet.init-param.config-template=/facet/event/configuration.jsp",
                "jakarta.portlet.init-param.view-template=/facet/event/view.jsp",
                "jakarta.portlet.name=" + SearchModuleKeys.EVENT_FACET_PORTLET,
                "jakarta.portlet.resource-bundle=content.Language",
                "jakarta.portlet.security-role-ref=power-user,user",
                "jakarta.portlet.version=4.0"
        },
        service = Portlet.class
)
public class EventFacetPortlet extends MVCPortlet {

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        renderRequest.setAttribute(
                EventFacetConfiguration.class.getName(),
                _configuration);
        super.doView(renderRequest, renderResponse);
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, false);
        super.render(renderRequest, renderResponse);
    }


    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                EventFacetConfiguration.class, properties);
    }

    private volatile EventFacetConfiguration _configuration;
}