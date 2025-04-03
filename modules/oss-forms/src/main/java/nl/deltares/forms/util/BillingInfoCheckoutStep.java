package nl.deltares.forms.util;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.BillingDetailsCheckoutStepDisplayContext;
import nl.deltares.model.BillingInfo;
import nl.deltares.portal.utils.CommerceUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

@Component(
        property = {
                "checkout.step.name=" + BillingInfoCheckoutStep.NAME,
                "checkout.step.order:Integer=15"
        },
        service = DeltaresCheckoutStep.class
)
public class BillingInfoCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "billing-info";
    private BillingDetailsCheckoutStepDisplayContext _displayContext;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);

        _displayContext = new BillingDetailsCheckoutStepDisplayContext(
                httpServletRequest, _addressLocalService, _accountEntryLocalService,
                _countryLocalService, _phoneLocalService, _userLocalService, _commerceUtils);
        _displayContext.validateRequestData(httpServletRequest);

        try {
            BillingInfo billingInfo = _displayContext.storeBillingInformation(httpServletRequest);
            httpServletRequest.getSession().setAttribute("billingInfo", billingInfo);
        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class,
                    Collections.singletonList(new RegistrationFormException(e.getMessage())));
        }

    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        _displayContext = new BillingDetailsCheckoutStepDisplayContext(
                httpServletRequest, _addressLocalService, _accountEntryLocalService,
                _countryLocalService, _phoneLocalService, _userLocalService, _commerceUtils);
        HttpSession session = httpServletRequest.getSession();
        Object billingInfo = session.getAttribute("billingInfo");
        if (billingInfo != null) {
            httpServletRequest.setAttribute("billingInfo", billingInfo);
        } else {
            httpServletRequest.setAttribute("billingInfo", _displayContext.getBillingInfo());
        }

        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, _displayContext);

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/billing-info.jsp");
    }

    @Override
    public boolean isActive(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            _displayContext = new BillingDetailsCheckoutStepDisplayContext(
                    httpServletRequest, _addressLocalService, _accountEntryLocalService,
                    _countryLocalService, _phoneLocalService, _userLocalService, _commerceUtils);
        } catch (Exception e) {
            return false;
        }
        return _displayContext.isPaymentRequired(httpServletRequest, _dsdParserUtils);
    }

    @Reference
    private Portal _portal;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private AddressLocalService _addressLocalService;

    @Reference
    private AccountEntryLocalService _accountEntryLocalService;

    @Reference
    private CountryLocalService _countryLocalService;

    @Reference
    private PhoneLocalService _phoneLocalService;

    @Reference
    private CommerceUtils _commerceUtils;

    @Reference
    private UserLocalService _userLocalService;

    @Reference
    private DsdParserUtils _dsdParserUtils;

}
