package nl.deltares.search.facet.program;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
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
                "javax.portlet.name=" + FacetPortletKeys.USER_PROGRAM_FACET_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
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
        portletSharedSearchRequest.search(renderRequest);
        renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, false);
        super.render(renderRequest, renderResponse);
    }

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                UserProgramFacetConfiguration.class, properties);
    }

    private volatile UserProgramFacetConfiguration _configuration;
}
