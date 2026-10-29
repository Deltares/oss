package nl.deltares.csvviewer.portlet;

import com.liferay.portal.kernel.json.JSONObject;
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

        final int curPage = ParamUtil.getInteger(renderRequest, "cur", 1);
        final int deltas = ParamUtil.getInteger(renderRequest, "delta", 25);
        final String filterColumn = ParamUtil.getString(renderRequest, "filterColumn", "");
        final String filterValue = ParamUtil.getString(renderRequest, "filterValue", "");

        final String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
        final String orderByType = ParamUtil.getString(renderRequest, "orderByType");

        renderRequest.setAttribute("filterColumn", filterColumn);
        renderRequest.setAttribute("filterValue", filterValue);

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
        final String portletId = portletDisplay.getId();
        CsvViewerConfiguration configuration = null;
        try {
            configuration = _configurationProvider.getPortletInstanceConfiguration(
                    CsvViewerConfiguration.class, themeDisplay.getLayout(), portletId);
            renderRequest.setAttribute(CsvViewerConfiguration.class.getName(), configuration);
        } catch (ConfigurationException e) {
            SessionErrors.add(renderRequest, "config-error", e.getMessage());
        }
        final String path = ParamUtil.getString(renderRequest, "mvcPath", null);
        final int rowNumber = ParamUtil.getInteger(renderRequest, "rowNumber", -1);
        if (configuration != null && configuration.csvFileUrl() != null && !configuration.csvFileUrl().isEmpty()) {
            try {
                JSONObject jsonCsv;
                if (path != null && path.endsWith("viewEntry.jsp") && rowNumber >= 0) {
                    jsonCsv = CsvDataService.loadCsvAsJson(
                            configuration.csvFileUrl(),
                            configuration.username(),
                            configuration.password(),
                            rowNumber
                    );
                } else {
                    jsonCsv = CsvDataService.loadCsvAsJson(
                            configuration.csvFileUrl(),
                            configuration.username(),
                            configuration.password(),
                            configuration.displayColumns(),
                            (curPage-1)*deltas, deltas,
                            filterColumn, filterValue,
                            orderByCol, orderByType
                    );
                }
                renderRequest.setAttribute("jsonCsv", jsonCsv);
            } catch (IOException e) {
                SessionErrors.add(renderRequest, "csv-error", e.getMessage());
            }
        }

        super.render(renderRequest, renderResponse);
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void filter(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String selectedColumn = ParamUtil.getString(actionRequest, "filterColumn", "");
        final String selectedValue = ParamUtil.getString(actionRequest, "filterValue", "");

        if (!selectedValue.isEmpty()) {
            RenderURL redirectURL = actionResponse.createRedirectURL(MimeResponse.Copy.ALL);
            redirectURL.getRenderParameters().setValue("filterColumn", selectedColumn);
            redirectURL.getRenderParameters().setValue("filterValue", selectedValue);
            try {
                actionResponse.sendRedirect(redirectURL.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }


}
