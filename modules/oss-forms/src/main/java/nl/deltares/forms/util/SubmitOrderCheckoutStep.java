package nl.deltares.forms.util;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.SubmitOrderDisplayContext;
import nl.deltares.portal.utils.*;
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
                "checkout.step.name=" + SubmitOrderCheckoutStep.NAME,
                "checkout.step.order:Integer=" + (Integer.MAX_VALUE - 100)
        },
        service = DeltaresCheckoutStep.class
)
public class SubmitOrderCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "submit-order";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
        SubmitOrderDisplayContext _submitOrderDisplayContext = new SubmitOrderDisplayContext(httpServletRequest, _configurationProvider,
                _dsdParserUtils, _dsdSessionUtils, _webinarUtilsFactory, _adminUtils, _userLocalService);

        List<Exception> exceptions = _submitOrderDisplayContext.storeUserInformation();
        if (!exceptions.isEmpty()) {
            httpServletRequest.setAttribute("action", "register-error");
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, exceptions);
            return;
        }
        try {
            _submitOrderDisplayContext.sendRegistrationEmails();
        } catch (Exception e) {
            httpServletRequest.setAttribute("action", "register-error");
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(
                    new RegistrationFormException(e.getMessage())
            ));
        }

        if (SessionErrors.isEmpty(httpServletRequest)) {
            httpServletRequest.setAttribute("action", "register-success");
        } else {
            httpServletRequest.setAttribute("action", "register-error");
        }
    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        SubmitOrderDisplayContext _submitOrderDisplayContext = new SubmitOrderDisplayContext(httpServletRequest, _configurationProvider,
                _dsdParserUtils, _dsdSessionUtils, _webinarUtilsFactory, _adminUtils, _userLocalService);
        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, _submitOrderDisplayContext);

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/submit-order.jsp");
    }

    @Reference
    private Portal _portal;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private ConfigurationProvider _configurationProvider;

    @Reference
    private DsdParserUtils _dsdParserUtils;

    @Reference
    private DsdSessionUtils _dsdSessionUtils;

    @Reference
    private WebinarUtilsFactory _webinarUtilsFactory;

    @Reference
    private UserLocalService _userLocalService;

    @Reference
    private AdminUtils _adminUtils;


}
