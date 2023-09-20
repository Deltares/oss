package nl.deltares.search.results;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.search.constans.SearchModuleKeys;
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
        configurationPid = "nl.deltares.search.results.SearchResultsPortletConfiguration",
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + SearchModuleKeys.SEARCH_RESULTS_PORTLET
        },
        service = ConfigurationAction.class
)
public class SearchResultsPortletConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                SearchResultsPortletConfiguration.class.getName(),
                _configuration);
        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        String displayTemplate = ParamUtil.getString(actionRequest, "displayTemplate");
        setPreference(actionRequest, "displayTemplate", displayTemplate);
        String displayType = ParamUtil.getString(actionRequest, "displayType");
        setPreference(actionRequest, "displayType", displayType);
        Boolean reverseOrder = ParamUtil.getBoolean(actionRequest, "reverseOrder");
        setPreference(actionRequest, "reverseOrder", String.valueOf(reverseOrder));

        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    /**
     * (1)If a method is annoted with @Activate then the method will be called at the time of activation of the component
     * so that we can perform initialization task
     * <p>
     * (2) This class is annoted with @Component where we have used configurationPid with id com.proliferay.configuration.DemoConfiguration
     * So if we modify any configuration then this method will be called.
     */
    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                SearchResultsPortletConfiguration.class, properties);
    }

    private volatile SearchResultsPortletConfiguration _configuration;

}