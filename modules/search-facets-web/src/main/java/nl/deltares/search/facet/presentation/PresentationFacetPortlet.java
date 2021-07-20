package nl.deltares.search.facet.presentation;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import nl.deltares.search.constans.FacetPortletKeys;
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
import java.util.Optional;

/**
 * @author allan
 */
@Component(
        configurationPid = "nl.deltares.search.facet.presentation.PresentationFacetConfiguration",
        immediate = true,
        property = {
                "com.liferay.portlet.css-class-wrapper=portlet-presentation-facet",
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Presentation Facet",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.config-template=/facet/presentation/configuration.jsp",
                "javax.portlet.init-param.view-template=/facet/presentation/view.jsp",
                "javax.portlet.name=" + FacetPortletKeys.PRESENTATION_FACET_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class PresentationFacetPortlet extends MVCPortlet {

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        renderRequest.setAttribute(
                PresentationFacetConfiguration.class.getName(),
                _configuration);
        super.doView(renderRequest, renderResponse);
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        PortletSharedSearchResponse portletSharedSearchResponse = portletSharedSearchRequest.search(renderRequest);
        Optional<String> hasPresentationsOptional = portletSharedSearchResponse.getParameter("hasPresentations", renderRequest);

        String hasPresentations;
        if (hasPresentationsOptional.isPresent()) {
            hasPresentations = hasPresentationsOptional.get();
            renderRequest.setAttribute("hasPresentations", hasPresentations);
        }
        super.render(renderRequest, renderResponse);
    }

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                PresentationFacetConfiguration.class, properties);
    }

    private volatile PresentationFacetConfiguration _configuration;
}