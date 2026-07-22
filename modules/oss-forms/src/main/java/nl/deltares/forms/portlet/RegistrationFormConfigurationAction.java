package nl.deltares.forms.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.*;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletConfig;

import java.util.Map;

@Component(
        configurationPid = OssConstants.REGISTRATIONFORM_CONFIGURATIONS_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "jakarta.portlet.name=" + OssConstants.REGISTRATIONFORM
        },
        service = ConfigurationAction.class
)
public class RegistrationFormConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                RegistrationFormConfiguration.class.getName(),
                _configuration);
        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        setPreference(actionRequest, "showBadgeInfo", String.valueOf(ParamUtil.getBoolean(actionRequest, "showBadgeInfo")));
        setPreference(actionRequest, "alwaysShowRelatedInfo", String.valueOf(ParamUtil.getBoolean(actionRequest, "alwaysShowRelatedInfo")));
        setPreference(actionRequest, "relatedAssetsTemplate", ParamUtil.getString(actionRequest, "relatedAssetsTemplate"));
        setPreference(actionRequest, "selectedAssetsTemplate", ParamUtil.getString(actionRequest, "selectedAssetsTemplate"));
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
                RegistrationFormConfiguration.class, properties);
    }

    private volatile RegistrationFormConfiguration _configuration;

}
