package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import jakarta.servlet.http.HttpServletRequest;
import nl.deltares.portal.configuration.DSDSiteConfiguration;

public class CheckoutStatusDisplayContext {


    private final String _action;
    private final HttpServletRequest _httpServletRequest;
    private final DSDSiteConfiguration _configuration;
    private final ThemeDisplay _themeDisplay;
    private final String _redirect;

    public CheckoutStatusDisplayContext(HttpServletRequest httpServletRequest, ConfigurationProvider configurationProvider) throws ConfigurationException {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _httpServletRequest = httpServletRequest;
        _configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, _themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId());
        _action = httpServletRequest.getParameter("action");
        _redirect = (String) httpServletRequest.getSession().getAttribute("callerURL");

    }

    public String redirectOneURL() {
        return _themeDisplay.getSiteGroup().getDisplayURL(_themeDisplay) + "/my-registrations";
    }

    public String redirectTwoURL() {
        if (_redirect == null || _redirect.isEmpty()) {
            return _themeDisplay.getSiteGroup().getDisplayURL(_themeDisplay) + "/program";
        } else {
            return _redirect;
        }
    }
    public String getImageURL() {

        String imagePath;
        if (_action != null && _action.endsWith("-error")){
            imagePath = "/images/error.png";{}
        } else {
            imagePath = "/images/success.png";
        }
        return  _httpServletRequest.getContextPath() + imagePath;
    }

    public String getHeaderKey() {
        return switch (_action) {
            case "unregister-success" -> "registrationform.unregister.success";
            case "unregister-error" -> "registrationform.unregister.error";
            case "register-success" -> "registrationform.register.success";
            case "register-error" -> "registrationform.register.error";
            default -> "";
        };

    }

    public String getEmailMessageKey() {
        return switch (_action) {
            case "unregister-success" -> "registrationform.unregister.email";
            case "register-success" -> "registrationform.register.email";
            default -> "";
        };
    }

    public String getPaymentMessageKey() {
        return switch (_action) {
            case "unregister-success" -> "registrationform.unregister.payment";
            case "register-success" -> "registrationform.register.payment";
            default -> "";
        };
    }

    public String[] getPaymentMessageArguments() {
        if (_action.equals("unregister-success")) {
            return new String[]{_configuration.conditionsURL()};
        }
        return new String[0];
    }
}
