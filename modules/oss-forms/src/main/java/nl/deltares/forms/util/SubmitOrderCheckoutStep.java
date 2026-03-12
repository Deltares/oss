package nl.deltares.forms.util;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
                _dsdParserUtils, _dsdSessionUtils, _dsdJournalArticleUtils, _webinarUtilsFactory, _adminUtils, _userLocalService,
                _taxCalculator);

        List<Exception> exceptions = _submitOrderDisplayContext.storeUserInformation();
        if (!exceptions.isEmpty()) {
            httpServletRequest.setAttribute("action", "register-error");
            //Pass errors on to the next form
            httpServletRequest.getSession().setAttribute("registration-errors", exceptions);
            return;
        }
        ThemeDisplay themeDisplay = new CPRequestHelper(httpServletRequest).getThemeDisplay();
        try {
            _submitOrderDisplayContext.sendRegistrationEmails(themeDisplay);
        } catch (Exception e) {
            httpServletRequest.setAttribute("action", "register-error");
            //Pass errors on to the next form
            httpServletRequest.getSession().setAttribute("registration-errors", Collections.singletonList(new RegistrationFormException("Error sending registration emails: " + e.getMessage())));
        }
        httpServletRequest.setAttribute("action", "register-success");
    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        SubmitOrderDisplayContext _submitOrderDisplayContext = new SubmitOrderDisplayContext(httpServletRequest, _configurationProvider,
                _dsdParserUtils, _dsdSessionUtils, _dsdJournalArticleUtils, _webinarUtilsFactory, _adminUtils, _userLocalService,
                _taxCalculator);
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
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

    @Reference
    private WebinarUtilsFactory _webinarUtilsFactory;

    @Reference
    private UserLocalService _userLocalService;

    @Reference
    private AdminUtils _adminUtils;

    @Reference
    private TaxCalculator _taxCalculator;


}
