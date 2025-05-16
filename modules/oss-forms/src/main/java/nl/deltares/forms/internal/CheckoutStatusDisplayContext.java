package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.configuration.DSDSiteConfiguration;

import javax.servlet.http.HttpServletRequest;

import static nl.deltares.portal.utils.LocalizationUtils.getLocalizedValue;

public class CheckoutStatusDisplayContext {


    private final String _action;
    private final HttpServletRequest _httpServletRequest;
    private final DSDSiteConfiguration _configuration;
    private final ThemeDisplay _themeDisplay;

    public CheckoutStatusDisplayContext(HttpServletRequest httpServletRequest, String action, ConfigurationProvider configurationProvider) throws ConfigurationException {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _httpServletRequest = httpServletRequest;
        _configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, _themeDisplay.getScopeGroupId());
        this._action = action;
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
        switch (_action) {
            case "unregister-success":
                return "registrationform.unregister.success";
            case "unregister-error":
                return "registrationform.unregister.error";
            case "register-success":
                return "registrationform.register.success";
            case "register-error":
                return "registrationform.register.error";
            default:
                return "";
        }

    }

    public String getEmailMessageKey() {
        switch (_action) {
            case "unregister-success":
                return "registrationform.unregister.email";
            case "register-success":
                return "registrationform.register.email";
            default:
                return "";
        }
    }

    public String getPaymentMessageKey() {
        switch (_action) {
            case "unregister-success":
                return "registrationform.unregister.payment";
            case "register-success":
                return "registrationform.register.payment";
            default:
                return "";
        }
    }

    public String[] getPaymentMessageArguments() {
        switch (_action) {
            case "unregister-success":
                final String language = _themeDisplay.getLocale().getLanguage();
                return new String[] {getLocalizedValue(_configuration.conditionsURL(), language)};
            default:
                return new String[0];
        }
    }
}
