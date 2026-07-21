package nl.deltares.forms.util;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.internal.UserInputValidationContext;
import nl.deltares.forms.internal.UserRegistrationDisplayContext;
import nl.deltares.portal.utils.AccountUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component(
        property = {
                "checkout.step.name=" + UserRegistrationCheckoutStep.NAME,
                "checkout.step.order:Integer=10"
        },
        service = DeltaresCheckoutStep.class
)
public class UserRegistrationCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "registrations-info";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
        UserInputValidationContext _registrationContext = new UserInputValidationContext(
                httpServletRequest, _dsdSessionUtils, _dsdParserUtils, _userLocalService, _accountUtils);

        _registrationContext.storeUserRegistrationInfos(httpServletRequest);
        _registrationContext.validateRequestData(httpServletRequest);
    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        UserRegistrationDisplayContext displayContext = new UserRegistrationDisplayContext(httpServletRequest,
                _dsdParserUtils);
        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, displayContext);

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/user-registration.jsp");
    }

    @Reference
    private Portal _portal;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private DsdParserUtils _dsdParserUtils;

    @Reference
    private DsdSessionUtils _dsdSessionUtils;

    @Reference
    private UserLocalService _userLocalService;

    @Reference
    private AccountUtils _accountUtils;

}
