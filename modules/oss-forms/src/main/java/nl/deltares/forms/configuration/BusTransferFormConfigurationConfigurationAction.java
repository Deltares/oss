package nl.deltares.forms.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.forms.constants.OssFormPortletKeys;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import java.util.Map;

@Component(
        configurationPid = OssFormPortletKeys.BUS_TRANSFER_FORM_CONFIGURATIONS_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL,
        property = {
                "javax.portlet.name=" + OssFormPortletKeys.DSD_BUS_REGISTRATION_FORM
        },
        service = ConfigurationAction.class
)
public class BusTransferFormConfigurationConfigurationAction extends DefaultConfigurationAction {

    @Activate
    @Modified
    protected void activate(Map<String, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                BusTransferFormConfiguration.class, properties);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        long eventId = ParamUtil.getLong(actionRequest, "eventId");
        setPreference(actionRequest, "eventId", String.valueOf(eventId));
        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    private volatile BusTransferFormConfiguration _configuration;
}
