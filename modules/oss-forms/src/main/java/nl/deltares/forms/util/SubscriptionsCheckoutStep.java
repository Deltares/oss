package nl.deltares.forms.util;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.SubscriptionsDisplayContext;
import nl.deltares.portal.utils.EmailSubscriptionUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Component(
        property = {
                "checkout.step.name=" + SubscriptionsCheckoutStep.NAME,
                "checkout.step.order:Integer=20"
        },
        service = DeltaresCheckoutStep.class
)
public class SubscriptionsCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "subscription-info";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
        SubscriptionsDisplayContext _subscriptionsDisplayContext = new SubscriptionsDisplayContext(httpServletRequest, _configurationProvider, _subscriptionUtil);
        try {
            _subscriptionsDisplayContext.storeSubscriptionInfo(httpServletRequest);
        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class,
                    Collections.singletonList(new RegistrationFormException(e.getMessage())));
        }

    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        SubscriptionsDisplayContext _subscriptionsDisplayContext = new SubscriptionsDisplayContext(httpServletRequest, _configurationProvider, _subscriptionUtil);
        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, _subscriptionsDisplayContext);

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/subscription-info.jsp");
    }

    @Override
    public boolean isActive(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            SubscriptionsDisplayContext _subscriptionsDisplayContext = new SubscriptionsDisplayContext(httpServletRequest, _configurationProvider, _subscriptionUtil);
            return _subscriptionsDisplayContext.hasSubscriptions();
        } catch (Exception e) {
            return false;
        }

    }

    @Reference
    private Portal _portal;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private ConfigurationProvider _configurationProvider;

    @Reference
    private EmailSubscriptionUtils _subscriptionUtil;

}
