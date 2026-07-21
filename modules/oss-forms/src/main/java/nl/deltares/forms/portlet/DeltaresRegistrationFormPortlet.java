package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import jakarta.portlet.Portlet;
import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;
import jakarta.servlet.http.HttpServletRequest;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.internal.CheckoutDisplayContext;
import nl.deltares.forms.util.DeltaresCheckoutStepRegistry;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;

/**
 * @author rooij_e
 */
@Component(
        configurationPid = OssConstants.REGISTRATIONFORM_CONFIGURATIONS_PID,
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/registration.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=Deltares Registration Form",
                "javax.portlet.init-param.config-template=/registration2.0/configuration.jsp",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/registration2.0/view.jsp",
                "javax.portlet.name=" + OssConstants.REGISTRATIONFORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.supported-locale=en",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DeltaresRegistrationFormPortlet extends MVCPortlet {

    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        if (!user.isGuestUser()) {

            String ids = ParamUtil.getString(request, "ids");
            request.setAttribute("ids", ids);
            String callerURL = ParamUtil.getString(request, "callerURL");
            if (callerURL != null && !callerURL.isEmpty()) {
                HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(request);
                httpServletRequest.getSession().setAttribute("callerURL", callerURL);
            }

            CheckoutDisplayContext checkoutDisplayContext = new CheckoutDisplayContext(_checkoutStepRegistry,
                    _portal.getLiferayPortletRequest(request),
                    _portal.getLiferayPortletResponse(response), _portal);
            request.setAttribute(CheckoutWebKeys.PORTLET_DISPLAY_CONTEXT, checkoutDisplayContext);
        }
        super.render(request, response);
    }

    @Reference
    private Portal _portal;

    @Reference
    private DeltaresCheckoutStepRegistry _checkoutStepRegistry;

}