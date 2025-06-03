package nl.deltares.forms.util;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.BadgeConfigCheckoutStepDisplayContext;
import nl.deltares.forms.portlet.RegistrationFormConfiguration;
import nl.deltares.model.BadgeInfo;
import nl.deltares.portal.utils.DsdParserUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Component(
        property = {
                "checkout.step.name=" + BadgeConfigCheckoutStep.NAME,
                "checkout.step.order:Integer=12"
        },
        service = DeltaresCheckoutStep.class
)
public class BadgeConfigCheckoutStep extends BaseCheckoutStep {

    private static final Log LOG = LogFactoryUtil.getLog(BadgeConfigCheckoutStep.class);

    public static final String NAME = "badge-info";
    private BadgeConfigCheckoutStepDisplayContext _displayContext;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);

        _displayContext = new BadgeConfigCheckoutStepDisplayContext(
                httpServletRequest, _configurationProvider, _dsdParserUtils);

        try {
            BadgeInfo badgeInfo = _displayContext.storeBadgeSettings(httpServletRequest);
            httpServletRequest.getSession().setAttribute("badgeInfo", badgeInfo);
        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class,
                    Collections.singletonList(new RegistrationFormException(e.getMessage())));
        }

    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        _displayContext = new BadgeConfigCheckoutStepDisplayContext(
                httpServletRequest, _configurationProvider, _dsdParserUtils);
        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, _displayContext);
        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/badge-info.jsp");
    }

    @Override
    public boolean isActive(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
            return _configurationProvider.getPortletInstanceConfiguration(RegistrationFormConfiguration.class,
                    cpRequestHelper.getThemeDisplay()).showBadgeInfo();
        } catch (ConfigurationException e) {
            LOG.error("Error checking if BadgeConfigCheckoutStep is active.", e);
            return false;
        }
    }

    @Reference
    private Portal _portal;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private DsdParserUtils _dsdParserUtils;

    @Reference
    private ConfigurationProvider _configurationProvider;

}
