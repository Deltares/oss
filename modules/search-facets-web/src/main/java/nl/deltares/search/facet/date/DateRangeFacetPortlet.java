package nl.deltares.search.facet.date;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {


        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        String startDate = FacetUtils.getRequestParameter("startDate", renderRequest);
        String endDate = FacetUtils.getRequestParameter("endDate", renderRequest);
        DateRangeFacetConfiguration _configuration = null;
        try {
            _configuration = _configurationProvider.getPortletInstanceConfiguration(DateRangeFacetConfiguration.class, themeDisplay);
            if (startDate == null) {
                if (!_configuration.startDate().isEmpty()) {
                    startDate = _configuration.startDate();
                } else if (Boolean.parseBoolean(_configuration.setStartNow())) {
                    startDate = DATE_TIME_FORMATTER.format(LocalDate.now());
                }
            }
            if (endDate == null) {
                if (!_configuration.endDate().isEmpty()) {
                    endDate = _configuration.endDate();
                }
            }
        } catch (ConfigurationException e) {
            //
        }
        if (startDate != null) {
            renderRequest.setAttribute("startDate", startDate);
        }
        if (endDate != null) {
            renderRequest.setAttribute("endDate", endDate);
        }
        super.render(renderRequest, renderResponse);
    }


    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }
}