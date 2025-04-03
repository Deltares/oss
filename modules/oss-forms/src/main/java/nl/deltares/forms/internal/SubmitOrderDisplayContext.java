package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import nl.deltares.emails.RegistrationEmail;
import nl.deltares.emails.serializer.RegisterEmailSerializer;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class SubmitOrderDisplayContext {

    private final DSDSiteConfiguration _configuration;
    private final boolean _showTerms;
    private final List<RegistrationInfo> _registrationInfos;
    private final ThemeDisplay _themeDisplay;
    private final DsdParserUtils _dsdParserUtils;
    private final HttpServletRequest _httpServletRequest;
    private final WebinarUtilsFactory _webinarUtilsFactory;
    private final DsdSessionUtils _dsdSessionUtils;
    private final AdminUtils _adminUtils;
    private final Event _event;
    private final UserLocalService _userLocalService;

    public SubmitOrderDisplayContext(HttpServletRequest httpServletRequest, ConfigurationProvider configurationProvider,
                                     DsdParserUtils dsdParserUtils, DsdSessionUtils dsdSessionUtils,
                                     WebinarUtilsFactory webinarUtilsFactory, AdminUtils adminUtils, UserLocalService userLocalService) throws Exception {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, _themeDisplay.getScopeGroupId());
        _httpServletRequest = httpServletRequest;
        _dsdParserUtils = dsdParserUtils;
        _dsdSessionUtils = dsdSessionUtils;
        _webinarUtilsFactory = webinarUtilsFactory;
        _adminUtils = adminUtils;
        _userLocalService = userLocalService;

        _registrationInfos = (List<RegistrationInfo>) httpServletRequest.getSession().getAttribute("registrationInfos");
        if (_registrationInfos == null || _registrationInfos.isEmpty()) {
            throw new RegistrationFormException("No registrations in session!");
        }
        Optional<RegistrationInfo> payedInstance = _registrationInfos.stream()
                .filter(registrationInfo -> registrationInfo.getPrice() > 0).findFirst();
        _showTerms = payedInstance.isPresent();

        _event = _dsdParserUtils.getEvent(_themeDisplay.getSiteGroupId(), String.valueOf(_configuration.eventId()), _themeDisplay.getLocale());
    }

    public long getCompanyId() {
        return 10131;
    }

    public boolean showTerms() {
        return _showTerms;
    }

    public String getTermsURL() {
        return _configuration.conditionsURL();
    }

    public String getPrivacyURL() {
        return _configuration.privacyURL();
    }

    public String getContactURL() {
        return _configuration.contactURL();
    }

    public List<Exception> storeUserInformation() {

        List<Exception> exceptions = new ArrayList<>();
        BillingInfo billingInfo = (BillingInfo) _httpServletRequest.getSession().getAttribute("billingInfo");
        for (RegistrationInfo registrationInfo : _registrationInfos) {
            try {
                storeUserInformation(registrationInfo, billingInfo);
            } catch (Exception e) {
                exceptions.add(new RegistrationFormException(e.getMessage()));
            }
        }
        return exceptions;
    }

    private void storeUserInformation(RegistrationInfo registrationInfo, BillingInfo billingInfo) throws Exception {

        Registration registration = _dsdParserUtils.getRegistration(_themeDisplay.getSiteGroupId(), registrationInfo.getArticleId());

        User registrationUser = _adminUtils.getOrCreateRegistrationUser(_themeDisplay.getCompanyId(), _themeDisplay.getUser(),
                registrationInfo.getEmail(), registrationInfo.getFirstName(), registrationInfo.getLastName(), registrationInfo.getSalutation(),
                _themeDisplay.getLocale());

        Map<String, String> registrationAttributes = new HashMap<>();
        if (!registrationInfo.getRemarks().isEmpty()) {
            registrationAttributes.put("remarks", registrationInfo.getRemarks());
        }

        if (billingInfo != null) {
            addBillingAttributes(billingInfo, registrationAttributes);
        }

        if (_webinarUtilsFactory.isWebinarSupported(registration)) {
            registerWebinar(registrationUser, (SessionRegistration) registration, billingInfo, registrationAttributes);
        }

        _dsdSessionUtils.registerUser(registrationUser, registration, registrationAttributes, _themeDisplay.getUser());
    }

    private void addBillingAttributes(BillingInfo billingInfo, Map<String, String> registrationAttributes) {

        String email = billingInfo.getEmail();
        if (email != null && !email.isEmpty()) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_email.name(), email);
        }
        String firstName = billingInfo.getFirstName();
        if (firstName != null && !firstName.isEmpty()) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_firstname.name(), firstName);
        }
        String lastName = billingInfo.getLastName();
        if (lastName != null && !lastName.isEmpty()) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_lastname.name(), lastName);
        }
        String reference = billingInfo.getReference();
        if (reference != null && !reference.isEmpty()) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_reference.name(), reference);
        }
        String preference = billingInfo.getPreference();
        if (preference != null && !preference.isEmpty()) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_preference.name(), preference);
        }
        if (!billingInfo.isDefaultBillingAddress()) {
            long billingAddressId = billingInfo.getBillingAddressId();
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_addressid.name(), String.valueOf(billingAddressId));
        }
    }

    private void registerWebinar(User registrationUser, SessionRegistration registration,
                                 BillingInfo billingInfo, Map<String, String> registrationAttributes) throws Exception {

        try {
            WebinarUtils webinarUtils = _webinarUtilsFactory.newInstance(registration);
            if (webinarUtils.isActive()) {
                HashMap<String, String> orgAttributes = new HashMap<>();

                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_address.name(), billingInfo.getAddress());
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_postal.name(), billingInfo.getPostal());
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_city.name(), billingInfo.getCity());
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_country.name(), billingInfo.getCountry());
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_name.name(), billingInfo.getCompanyName());

                String source = GroupServiceUtil.getGroup(registration.getGroupId()).getName(Locale.US);
                webinarUtils.registerUser(registrationUser, orgAttributes, registration.getWebinarKey(), source, registrationAttributes);
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error registering for webinar %s: %s", registration.getTitle(), e.getMessage()));
        }

    }

    public void sendRegistrationEmails() throws Exception {
        if (!_configuration.enableEmails()) return;

        ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", _themeDisplay.getLocale(), getClass());

        RegistrationEmail registrationEmail = new RegistrationEmail(resourceBundle);
        registrationEmail.setReplyToEmail(_configuration.replyToEmail());
        registrationEmail.setSendFromEmail(_configuration.sendFromEmail());

        String subject = LanguageUtil.format(resourceBundle, "dsd.register.subject", _event.getTitle());
        registrationEmail.setSubject(subject);
        registrationEmail.setEmailBanner(_event.getEmailBannerURL(), _event.getEmailBannerFileEntryId());
        registrationEmail.setEmailFooter(_event.getEmailFooterURL(), _event.getEmailFooterFileEntryId());
        registrationEmail.addCCEmail(_themeDisplay.getUser().getEmailAddress());

        RegisterEmailSerializer serializer = new RegisterEmailSerializer();

        Map<User, List<Registration>> mappedInfos = mapRegistrationsByUser(_registrationInfos);
        for (Map.Entry<User, List<Registration>> entry : mappedInfos.entrySet()) {
            registrationEmail.sendRegisterEmail(serializer, entry.getKey(), entry.getValue(), getRegistrationInfosForUser(_registrationInfos, entry.getKey()));
        }
    }

    private List<RegistrationInfo> getRegistrationInfosForUser(List<RegistrationInfo> registrationInfos, User user) {
        return registrationInfos.stream().filter(info -> info.getEmail().equals(user.getEmailAddress())).collect(Collectors.toList());
    }

    private Map<User, List<Registration>> mapRegistrationsByUser(List<RegistrationInfo> registrationInfos) throws PortalException {
        HashMap<User, List<Registration>> map = new HashMap<>();
        for (RegistrationInfo registrationInfo : registrationInfos) {
            User user = _userLocalService.fetchUserByEmailAddress(_themeDisplay.getCompanyId(), registrationInfo.getEmail());
            if (user == null) continue;
            Registration registration = _dsdParserUtils.getRegistration(_themeDisplay.getSiteGroupId(), registrationInfo.getArticleId());
            List<Registration> list = map.computeIfAbsent(user, k -> new ArrayList<>());
            list.add(registration);
        }
        return map;
    }
}
