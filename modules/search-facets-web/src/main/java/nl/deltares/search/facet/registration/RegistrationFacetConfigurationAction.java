package nl.deltares.search.facet.registration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.JsonContentUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component(
        configurationPid = "nl.deltares.search.facet.registration.RegistrationFacetConfiguration",
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + FacetPortletKeys.REGISTRATION_FACET_PORTLET
        },
        service = ConfigurationAction.class
)
public class RegistrationFacetConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                RegistrationFacetConfiguration.class.getName(),
                _configuration);
        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        List<String> structureList = convertStructuresToList(actionRequest);
        setPreference(actionRequest, "structureList", JsonContentUtils.formatListToJson(structureList));
        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    private List<String> convertStructuresToList(ActionRequest actionRequest) {
        ArrayList<String> structures = new ArrayList<>();
        for (DsdArticle.DSD_REGISTRATION_STRUCTURE_KEYS structure_keys : DsdArticle.DSD_REGISTRATION_STRUCTURE_KEYS.values()) {
            String structureKey = structure_keys.name();
            String value = ParamUtil.getString(actionRequest, structureKey);
            if (value.isEmpty() || !Boolean.parseBoolean(value)) continue;
            structures.add(structureKey.toUpperCase());
        }
        return structures;
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
                RegistrationFacetConfiguration.class, properties);
    }

    private volatile RegistrationFacetConfiguration _configuration;

}