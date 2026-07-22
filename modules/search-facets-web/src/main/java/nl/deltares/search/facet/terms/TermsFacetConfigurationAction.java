package nl.deltares.search.facet.terms;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.deltares.search.constans.SearchModuleKeys;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import java.util.Map;

@Component(
        configurationPid = "nl.deltares.search.facet.terms.TermsFacetConfiguration",
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "jakarta.portlet.name=" + SearchModuleKeys.TERMS_FACET_PORTLET
        },
        service = ConfigurationAction.class
)
public class TermsFacetConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                TermsFacetConfiguration.class.getName(),
                _configuration);
        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {
        setPreference(actionRequest, "multipleTermValues", ParamUtil.getString(actionRequest, "multipleTermValues"));
        setPreference(actionRequest, "termFieldName", ParamUtil.getString(actionRequest, "termFieldName"));
        setPreference(actionRequest, "termValue", ParamUtil.getString(actionRequest, "termValue"));
        setPreference(actionRequest, "isDdmField", ParamUtil.getString(actionRequest, "isDdmField"));
        setPreference(actionRequest, "useWildcard",String.valueOf(ParamUtil.getBoolean(actionRequest, "useWildcard")));
        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    /**
     *
     * (1)If a method is annoted with @Activate then the method will be called at the time of activation of the component
     *  so that we can perform initialization task
     * <p>
     * (2) This class is annoted with @Component where we have used configurationPid with id com.proliferay.configuration.DemoConfiguration
     * So if we modify any configuration then this method will be called.
     */
    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                TermsFacetConfiguration.class, properties);
    }

    private volatile TermsFacetConfiguration _configuration;

}