package nl.deltares.forms.util;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.CartOverviewDisplayContext;
import nl.deltares.forms.internal.UserRegistrationValidationContext;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@Component(
        property = {
                "checkout.step.name=" + CartOverviewCheckoutStep.NAME,
                "checkout.step.order:Integer=5"
        },
        service = DeltaresCheckoutStep.class
)
public class CartOverviewCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "cart-content";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
        UserRegistrationValidationContext validationContext = new UserRegistrationValidationContext(
                httpServletRequest, _dsdSessionUtils, _dsdParserUtils, _userLocalService);
        validationContext.validateRequestData(httpServletRequest);
        httpServletRequest.getSession().setAttribute("registrationInfos", validationContext.getRegistrationInformation());
        if (SessionErrors.contains(httpServletRequest, RegistrationFormException.class)){
            return;
        }
        storeUserInformation(validationContext.getRegistrationInformation());

    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        try {
            CartOverviewDisplayContext displayContext = new CartOverviewDisplayContext(httpServletRequest,
                    _dsdParserUtils, _ddmStructureUtil);

            httpServletRequest.setAttribute(
                    CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT,
                    displayContext);
        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(new RegistrationFormException(e.getMessage(), e)));
        }

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/cart-overview.jsp");
    }

    private void storeUserInformation(List<RegistrationInfo> registrationInformation) {
        //todo
    }

    @Reference
    private Portal _portal;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private DsdParserUtils _dsdParserUtils;

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    @Reference
    private DsdSessionUtils _dsdSessionUtils;

    @Reference
    private UserLocalService _userLocalService;

}
