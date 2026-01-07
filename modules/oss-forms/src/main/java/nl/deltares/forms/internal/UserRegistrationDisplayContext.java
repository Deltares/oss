package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.model.RegistrationFormContext;
import nl.deltares.model.RegistrationsInfo;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.servlet.http.HttpServletRequest;

public class UserRegistrationDisplayContext {

    private final RegistrationsInfo _registrationsInfo;
    private final String _displayURL;

    public UserRegistrationDisplayContext(HttpServletRequest request, DsdParserUtils dsdParserUtils) {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();
        _displayURL = themeDisplay.getSiteGroup().getDisplayURL(themeDisplay);

        RegistrationFormContext context = (RegistrationFormContext) request.getSession().getAttribute("registration-context");
        if (context == null) {
            context = new RegistrationFormContext();
            request.getSession().setAttribute("registration-context", context);
        }
        RegistrationsInfo registrationsInfo = context.getRegistrationsInfo();
        if (registrationsInfo == null) {
            _registrationsInfo = new RegistrationsInfo();
            context.setRegistrationsInfo(_registrationsInfo);
        } else {
            _registrationsInfo = registrationsInfo;
        }
        RegistrationsInfo.loadRegistrations(request, _registrationsInfo, dsdParserUtils, themeDisplay);
        RegistrationsInfo.loadUserRegistrations(_registrationsInfo, themeDisplay.getUser());

    }

    public String getViewURL(String articleId) {
        Registration registration = _registrationsInfo.getRegistration(articleId);
        if (registration == null) {
            return "";
        }
        return _displayURL + "/-/" + registration.getJournalArticle().getUrlTitle();
    }

    public RegistrationsInfo getRegistrationsInfo() {
        return _registrationsInfo;
    }
}
