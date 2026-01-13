package nl.deltares.forms.util;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.SessionErrors;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
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

        CheckoutStatusDisplayContext displayContext = new CheckoutStatusDisplayContext(httpServletRequest, _configurationProvider);
        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, displayContext);
        httpServletRequest.setAttribute(OssConstants.MY_REGISTRATIONS_URL, displayContext.redirectOneURL());
        httpServletRequest.setAttribute("redirect", displayContext.redirectTwoURL());

        Object errors = httpServletRequest.getSession().getAttribute("registration-errors");
        if (errors != null) {
            httpServletRequest.getSession().removeAttribute("registration-errors");
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, errors);
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
