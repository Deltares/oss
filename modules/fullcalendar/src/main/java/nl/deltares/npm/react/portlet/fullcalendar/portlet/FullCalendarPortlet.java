package nl.deltares.npm.react.portlet.fullcalendar.portlet;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.npm.react.portlet.fullcalendar.constants.FullCalendarPortletKeys;
import nl.deltares.portal.utils.DsdRegistrationUtils;
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
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-js=/lib/index.es.js",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=FullCalendar Portlet",
                "javax.portlet.init-param.config-template=/configuration.jsp",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + FullCalendarPortletKeys.FullCalendar,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class

)
public class FullCalendarPortlet extends MVCPortlet {

    @Override
    public void doView(
            RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        JSPackage jsPackage = _npmResolver.getJSPackage();

        renderRequest.setAttribute(
                FullCalendarPortletKeys.BOOTSTRAP_REQUIRE,
                jsPackage.getResolvedId() + " as bootstrapRequire");

        renderRequest.setAttribute(
                FullCalendarConfiguration.class.getName(),
                _configuration);

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
        renderRequest.setAttribute(
                "mainRequire",
                _npmResolver.resolveModuleName("fullcalendar") + " as main");

        renderRequest.setAttribute(DsdRegistrationUtils.class.getName(), dsdRegistrationUtils);
        renderRequest.setAttribute("hasEditPermission", permissionChecker.isGroupAdmin(themeDisplay.getSiteGroupId()));
        super.doView(renderRequest, renderResponse);
    }

    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                FullCalendarConfiguration.class, properties);
    }

    @Reference
    private NPMResolver _npmResolver;

    @Reference
    private DsdRegistrationUtils dsdRegistrationUtils;

    private volatile FullCalendarConfiguration _configuration;
}