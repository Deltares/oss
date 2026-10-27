package nl.deltares.csvviewer.portlet;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.csvviewer.configuration.CsvViewerConfiguration;
import nl.deltares.csvviewer.constants.CsvViewerPortletKeys;
import nl.deltares.csvviewer.service.CsvDataService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;

@Component(
        configurationPid = CsvViewerPortletKeys.CSV_VIEWER_CONFIGUTATION_PID,
        immediate = true,
        property = {
                "com.liferay.portlet.css-class-wrapper=portlet-checkbox-facet",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.footer-portlet-javascript=/js/main.js",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=CSV-Viewer",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.config-template=/configuration.jsp",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + CsvViewerPortletKeys.CSV_VIEWER,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user",
                "javax.portlet.version=3.0"
        },
        service = Portlet.class
)
public class CsvViewerPortlet extends MVCPortlet {


    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
        final String portletId = portletDisplay.getId();
        CsvViewerConfiguration configuration = null;
        try {
            configuration = _configurationProvider.getPortletInstanceConfiguration(
                    CsvViewerConfiguration.class, themeDisplay.getLayout(), portletId);
            renderRequest.setAttribute(CsvViewerConfiguration.class.getName(), configuration);
        } catch (ConfigurationException e) {
            SessionErrors.add(renderRequest, "get-config-failed", e.getMessage());
        }

        if (configuration != null && configuration.csvFileUrl() != null && !configuration.csvFileUrl().isEmpty()) {
            String jsonCsv = CsvDataService.loadCsvAsJson(
                    configuration.csvFileUrl(),
                    configuration.username(),
                    configuration.password(),
                    configuration.filterableColumns(),
                    configuration.strictValidation()
            );
            renderRequest.setAttribute("jsonCsv", jsonCsv);
        }

        super.render(renderRequest, renderResponse);
    }

    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException {
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        String action = ParamUtil.getString(resourceRequest, "action");

//        if ("loadCsv".equals(action)) {
//            resourceResponse.setContentType("application/json");
//            String json = CsvDataService.loadCsvAsJson(
//                    configuration.csvFileUrl(),
//                    configuration.filterableColumns(),
//                    configuration.strictValidation()
//            );
//            resourceResponse.getWriter().write(json);
//        }
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }


}
