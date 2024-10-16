package nl.deltares.search.facet.date;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
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

/**
 * @author allan
 */
@Component(
        configurationPid = "nl.deltares.search.facet.date.DateRangeFacetConfiguration",
        immediate = true,
        property = {
                "com.liferay.portlet.css-class-wrapper=portlet-date-range-facet",
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=DateRangeFacet",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.config-template=/facet/date/configuration.jsp",
                "javax.portlet.init-param.view-template=/facet/date/view.jsp",
                "javax.portlet.name=" + SearchModuleKeys.DATE_RANGE_FACET_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user",
                "javax.portlet.version=3.0"
        },
        service = Portlet.class
)
public class DateRangeFacetPortlet extends MVCPortlet {

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        renderRequest.setAttribute(
                DateRangeFacetConfiguration.class.getName(),
                _configuration);
        super.doView(renderRequest, renderResponse);
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final String startDate = FacetUtils.getRequestParameter("startDate", renderRequest);
        if (startDate != null) renderRequest.setAttribute("startDate", FacetUtils.parseDate(startDate));

        final String endDate = FacetUtils.getRequestParameter("endDate", renderRequest);
        if (endDate != null) renderRequest.setAttribute("endDate", FacetUtils.parseDate(endDate));

        super.render(renderRequest, renderResponse);
    }

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                DateRangeFacetConfiguration.class, properties);
    }

    private volatile DateRangeFacetConfiguration _configuration;
}