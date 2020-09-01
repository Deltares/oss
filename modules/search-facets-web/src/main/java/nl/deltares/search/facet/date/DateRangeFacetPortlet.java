package nl.deltares.search.facet.date;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import nl.deltares.search.constans.FacetPortletKeys;
import nl.deltares.search.util.DateFacetUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author allan
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.css-class-wrapper=portlet-date-range-facet",
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=DateRangeFacet",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/facet/date/view.jsp",
                "javax.portlet.name=" + FacetPortletKeys.DATE_RANGE_FACET_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DateRangeFacetPortlet extends MVCPortlet {

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        PortletSharedSearchResponse portletSharedSearchResponse = portletSharedSearchRequest.search(renderRequest);
        Optional<String> startDateOptional = portletSharedSearchResponse.getParameter("startDate", renderRequest);
        Optional<String> endDateOptional = portletSharedSearchResponse.getParameter("endDate", renderRequest);

        LocalDate startDate;
        if (startDateOptional.isPresent()) {
            startDate = DateFacetUtil.parseDate(startDateOptional.get());
        } else {
            startDate = DateFacetUtil.getDefaultStartDate();
        }
        renderRequest.setAttribute("startDate", startDate);

        LocalDate endDate;
        if (endDateOptional.isPresent()) {
            endDate = DateFacetUtil.parseDate(endDateOptional.get());
        } else {
            endDate = DateFacetUtil.getDefaultEndDate();
        }
        renderRequest.setAttribute("endDate", endDate);

        super.render(renderRequest, renderResponse);
    }

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;
}