package nl.deltares.npm.react.portlet.fullcalendar.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.npm.react.portlet.fullcalendar.constants.FullCalendarPortletKeys;
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
        configurationPid = "nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration",
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + FullCalendarPortletKeys.FullCalendar
        },
        service = ConfigurationAction.class
)

/*
 *
 * (1) configurationPolicy optional means that the component is created regardless of whether or not the configuration was set
 * (2) The property javax.portlet.name indicates that this configuration is for com_proliferay_portlet_DemoPortlet
 * (3 )This component should be registered as a configuration action class so it should specify service = ConfigurationAction.class
 *  in the @Component annotation.
 *
 */
public class FullCalendarConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(FullCalendarConfiguration.class.getName(), _configuration);

        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        String eventId = ParamUtil.getString(actionRequest, "eventId");
        String baseUrl = ParamUtil.getString(actionRequest, "baseUrl");
//        String authUser = ParamUtil.getString(actionRequest, "authUser");
//        String authPassword = ParamUtil.getString(actionRequest, "authPassword");

        setPreference(actionRequest, "eventId", eventId);
        setPreference(actionRequest, "baseUrl", baseUrl);
//        setPreference(actionRequest, "authUser", authUser);
//        setPreference(actionRequest, "authPassword", authPassword);
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
        _configuration = ConfigurableUtil.createConfigurable(FullCalendarConfiguration.class, properties);
    }
    private volatile FullCalendarConfiguration _configuration;

}
