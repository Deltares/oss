package nl.deltares.search.facet.checkbox;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
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
        configurationPid = "nl.deltares.search.facet.checkbox.CheckboxFacetConfiguration",
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name=" + SearchModuleKeys.CHECKBOX_FACET_PORTLET
        },
        service = ConfigurationAction.class
)
public class CheckboxFacetConfigurationAction extends DefaultConfigurationAction {

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(
                CheckboxFacetConfiguration.class.getName(),
                _configuration);
        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        setPreference(actionRequest, "structureName", ParamUtil.getString(actionRequest, "structureName"));
        setPreference(actionRequest, "fieldName", ParamUtil.getString(actionRequest, "fieldName"));
        Map<String, String> titleMap = FacetUtils.getLanguageFieldValueMap(actionRequest, "title");
        setPreference(actionRequest, "titleMap", JsonContentUtils.formatMapToJson(titleMap));
        setPreference(actionRequest, "visible", ParamUtil.getString(actionRequest, "visible"));
        setPreference(actionRequest, "defaultValue", ParamUtil.getString(actionRequest, "defaultValue"));
        setPreference(actionRequest, "explicitSearch", ParamUtil.getString(actionRequest, "explicitSearch"));
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
                CheckboxFacetConfiguration.class, properties);
    }

    private volatile CheckboxFacetConfiguration _configuration;

}