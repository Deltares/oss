package nl.deltares.search.facet.date;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.search.constans.FacetPortletKeys;
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
        configurationPid = "nl.deltares.search.facet.date.DateRangeFacetConfiguration",
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + FacetPortletKeys.DATE_RANGE_FACET_PORTLET
        },
        service = ConfigurationAction.class
)
public class DateRangeFacetConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                DateRangeFacetConfiguration.class.getName(),
                _configuration);
        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        String startDate = ParamUtil.getString(actionRequest, "startDate");
        setPreference(actionRequest, "startDate", startDate);

        String endDate = ParamUtil.getString(actionRequest, "endDate");
        setPreference(actionRequest, "endDate", endDate);

        String setStartNow = ParamUtil.getString(actionRequest, "setStartNow");
        setPreference(actionRequest, "setStartNow", setStartNow);
        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    /**
     *
     * (1)If a method is annoted with @Activate then the method will be called at the time of activation of the component
     *  so that we can perform initialization task
     *
     * (2) This class is annoted with @Component where we have used configurationPid with id com.proliferay.configuration.DemoConfiguration
     * So if we modify any configuration then this method will be called.
     */
    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                DateRangeFacetConfiguration.class, properties);
    }

    private volatile DateRangeFacetConfiguration _configuration;

}