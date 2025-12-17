package nl.deltares.forms.util;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.internal.CheckoutStatusDisplayContext;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component(
        property = {
                "checkout.step.name=" + CheckoutStatusCheckoutStep.NAME,
                "checkout.step.order:Integer=" + Integer.MAX_VALUE
        },
        service = DeltaresCheckoutStep.class
)
public class CheckoutStatusCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "checkout-status";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        String action = httpServletRequest.getParameter("action");
        Object redirect = httpServletRequest.getSession().getAttribute("callerURL");
        CheckoutStatusDisplayContext displayContext = new CheckoutStatusDisplayContext(httpServletRequest, action, _configurationProvider);
        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, displayContext);
        httpServletRequest.setAttribute(OssConstants.MY_REGISTRATIONS_URL, "/my-registrations");
        if (redirect == null || redirect.toString().isEmpty()) {
            httpServletRequest.setAttribute("redirect", "/program");
        } else {
            httpServletRequest.setAttribute("redirect", redirect);
        }

        _jspRenderer.renderJSP(httpServletRequest, httpServletResponse, "/registration2.0/status.jsp");

    }

    @Override
    public boolean showControls(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return false;
    }

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private ConfigurationProvider _configurationProvider;

}
