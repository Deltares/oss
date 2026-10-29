package nl.deltares.csvviewer.portlet.action;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.csvviewer.configuration.CsvViewerConfiguration;
import nl.deltares.csvviewer.constants.CsvViewerPortletKeys;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component(
        configurationPid = CsvViewerPortletKeys.CSV_VIEWER_CONFIGUTATION_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + CsvViewerPortletKeys.CSV_VIEWER
        },
        service = ConfigurationAction.class
)
public class CsvViewerConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletRequest.setAttribute(
                CsvViewerConfiguration.class.getName(),
                _configuration);
        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        setPreference(actionRequest, "csvFileUrl", ParamUtil.getString(actionRequest, "csvFileUrl"));
        setPreference(actionRequest, "username", ParamUtil.getString(actionRequest, "username"));
        setPreference(actionRequest, "password", ParamUtil.getString(actionRequest, "password"));
        setPreference(actionRequest, "displayColumns", ParamUtil.getString(actionRequest, "displayColumns"));
        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                CsvViewerConfiguration.class, properties);
    }

    private volatile CsvViewerConfiguration _configuration;
}

