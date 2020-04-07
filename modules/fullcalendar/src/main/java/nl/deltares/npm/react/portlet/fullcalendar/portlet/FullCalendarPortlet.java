package nl.deltares.npm.react.portlet.fullcalendar.portlet;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.npm.react.portlet.fullcalendar.constants.FullCalendarPortletKeys;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author rooij_e
 */
@Component(
        configurationPid = "nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration",
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/index.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + FullCalendarPortletKeys.FullCalendar,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class

)
public class FullCalendarPortlet extends MVCPortlet {

    private volatile FullCalendarConfiguration _configuration;
    /*
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

    @Override
    public void doView(
            RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        renderRequest.setAttribute(FullCalendarConfiguration.class.getName(), _configuration);
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

//todo
//			Now we user a rest service to get the resources and events. this requires users to loging.
//			to avoid this we can pass csv and then parse the content in fullcallender module.
//			I do not know why but it seems impossible to pass JSON. there are problems with single and double quotes.

        PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
        renderRequest.setAttribute(
                "mainRequire",
                _npmResolver.resolveModuleName("fullcalendar") + " as main");
        renderRequest.setAttribute("hasEditPermission", permissionChecker.isGroupAdmin(themeDisplay.getSiteGroupId()));

        super.doView(renderRequest, renderResponse);
    }

    @Reference
    private NPMResolver _npmResolver;

}