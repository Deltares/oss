package nl.deltares.search.facet.date;

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
                "com.liferay.portlet.header-portlet-javascript=/js/facet_util.js",
                "com.liferay.portlet.instanceable=true",
                "jakarta.portlet.display-name=DateRangeFacet",
                "jakarta.portlet.expiration-cache=0",
                "jakarta.portlet.init-param.template-path=/",
                "jakarta.portlet.init-param.config-template=/facet/date/configuration.jsp",
                "jakarta.portlet.init-param.view-template=/facet/date/view.jsp",
                "jakarta.portlet.name=" + SearchModuleKeys.DATE_RANGE_FACET_PORTLET,
                "jakarta.portlet.resource-bundle=content.Language",
                "jakarta.portlet.security-role-ref=power-user,user",
                "jakarta.portlet.version=4.0"
        },
        service = Portlet.class
)
public class DateRangeFacetPortlet extends MVCPortlet {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void submitForm(ActionRequest actionRequest, ActionResponse actionResponse) {
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        String startDate = ParamUtil.getString(actionRequest, "startDate");
        if (startDate == null || startDate.isEmpty()) {
            FacetUtils.removeFromSession(themeDisplay.getPortletDisplay().getId(),"startDate", actionRequest);
        } else {
            FacetUtils.storeInSession(themeDisplay.getPortletDisplay().getId(),"startDate", startDate, actionRequest);
        }

        String endDate = ParamUtil.getString(actionRequest, "endDate");
        if (endDate == null || endDate.isEmpty()) {
            FacetUtils.removeFromSession(themeDisplay.getPortletDisplay().getId(),"endDate", actionRequest);
        } else {
            FacetUtils.storeInSession(themeDisplay.getPortletDisplay().getId(),"endDate", endDate, actionRequest);
        }

    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        // Try to get the start date from the session
        String startDate = FacetUtils.getRequestParameter("startDate", renderRequest);
        if (startDate == null) {
            startDate = (String) FacetUtils.getFromSession(themeDisplay.getPortletDisplay().getId(), "startDate", renderRequest);
        }
        // Try to get the end date from the session
        String endDate = FacetUtils.getRequestParameter("endDate", renderRequest);
        if (endDate == null) {
            endDate = (String) FacetUtils.getFromSession(themeDisplay.getPortletDisplay().getId(), "endDate", renderRequest);
        }
        final String portletId = themeDisplay.getPortletDisplay().getId();

        DateRangeFacetConfiguration _configuration;
        try {
            _configuration = _configurationProvider.getPortletInstanceConfiguration(DateRangeFacetConfiguration.class, themeDisplay.getLayout(), portletId);
            if (startDate == null) {
                if (!_configuration.startDate().isEmpty()) {
                    startDate = _configuration.startDate();
                } else if (Boolean.parseBoolean(_configuration.setStartNow())) {
                    startDate = DATE_TIME_FORMATTER.format(LocalDate.now());
                }
                if (startDate != null){
                    FacetUtils.storeInSession(themeDisplay.getPortletDisplay().getId(),"startDate", startDate, renderRequest);
                }
            }
            if (endDate == null) {
                if (!_configuration.endDate().isEmpty()) {
                    endDate = _configuration.endDate();
                    if (endDate != null){
                        FacetUtils.storeInSession(themeDisplay.getPortletDisplay().getId(),"endDate", endDate, renderRequest);
                    }
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