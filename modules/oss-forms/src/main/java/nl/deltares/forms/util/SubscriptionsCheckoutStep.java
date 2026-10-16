package nl.deltares.forms.util;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.SubscriptionsDisplayContext;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.EmailSubscriptionUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        ThemeDisplay _themeDisplay = cpRequestHelper.getThemeDisplay();

        SubscriptionsDisplayContext _subscriptionsDisplayContext = new SubscriptionsDisplayContext(
                _themeDisplay.getUser(), getConfiguredSubscriptionIds(_themeDisplay), _subscriptionUtil);
        try {
            _subscriptionsDisplayContext.storeSubscriptionInfo(httpServletRequest);
        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class,
                    Collections.singletonList(new RegistrationFormException(e.getMessage())));
        }
    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        ThemeDisplay _themeDisplay = cpRequestHelper.getThemeDisplay();
        SubscriptionsDisplayContext _subscriptionsDisplayContext = new SubscriptionsDisplayContext(
                _themeDisplay.getUser(), getConfiguredSubscriptionIds(_themeDisplay), _subscriptionUtil);
        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, _subscriptionsDisplayContext);

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/subscription-info.jsp");
    }

    @Override
    public boolean isActive(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
            ThemeDisplay _themeDisplay = cpRequestHelper.getThemeDisplay();
            return !getConfiguredSubscriptionIds(_themeDisplay).isEmpty();
        } catch (Exception e) {
            return false;
        }

    }

    private List<String> getConfiguredSubscriptionIds(ThemeDisplay _themeDisplay) throws ConfigurationException {
        DSDSiteConfiguration _configuration = _configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, _themeDisplay.getScopeGroupId());
        return Arrays.asList(_configuration.mailingIds().split(";"));
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
