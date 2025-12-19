package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import nl.deltares.emails.RegistrationEmail;
import nl.deltares.emails.serializer.UnRegisterEmailSerializer;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.ResourceBundle;

public class UnregisterDisplayContext {

    private static final Log LOG = LogFactoryUtil.getLog(UnregisterDisplayContext.class);

    private final DSDSiteConfiguration _configuration;
    private final ThemeDisplay _themeDisplay;
    private final DsdParserUtils _dsdParserUtils;
    private final DsdSessionUtils _dsdSessionUtils;

    public UnregisterDisplayContext(HttpServletRequest httpServletRequest, ConfigurationProvider configurationProvider,
                                    DsdParserUtils dsdParserUtils, DsdSessionUtils dsdSessionUtils) throws Exception {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, _themeDisplay.getScopeGroupId());
        _dsdParserUtils = dsdParserUtils;
        _dsdSessionUtils = dsdSessionUtils;

    }


    private void sendUnregisterEmail(User user, Registration registration) throws Exception {
        if (!_configuration.enableEmails()) return;

        ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", _themeDisplay.getLocale(), getClass());

        RegistrationEmail registrationEmail = new RegistrationEmail(resourceBundle);
        registrationEmail.setReplyToEmail(_configuration.replyToEmail());
        registrationEmail.setSendFromEmail(_configuration.sendFromEmail());

        String bccToEmail = _configuration.bccToEmail();
        String[] emails = bccToEmail.split(";");
        for (String email : emails) {
            if (email.isEmpty()) continue;
            registrationEmail.addBCCEmail(email);
        }

        Event _event = _dsdParserUtils.getEvent(_themeDisplay.getSiteGroupId(), String.valueOf(registration.getEventId()),
                _themeDisplay.getLocale());

        String subject = LanguageUtil.format(resourceBundle, "dsd.unregister.subject", _event.getTitle());
        registrationEmail.setSubject(subject);
        registrationEmail.setEmailBanner(_event.getEmailBannerURL(), _event.getEmailBannerFileEntryId());
        registrationEmail.setEmailFooter(_event.getEmailFooterURL(), _event.getEmailFooterFileEntryId());
        registrationEmail.addCCEmail(_themeDisplay.getUser().getEmailAddress());

        UnRegisterEmailSerializer serializer = new UnRegisterEmailSerializer();
        registrationEmail.sendUnregisterEmail(serializer, user, Collections.singletonList(registration));
    }

    public void unRegisterUser(String articleId, Long userId) throws Exception {

        if (userId == null) return;

        User user = UserLocalServiceUtil.fetchUser(userId);
        if (user == null) {
            LOG.warn("User with id " + userId + " not found!");
            return;
        }

        Registration registration = _dsdParserUtils.getRegistration(_themeDisplay.getSiteGroupId(), articleId);
        _dsdSessionUtils.unRegisterUser(user, registration);

        sendUnregisterEmail(user, registration);
    }
}
