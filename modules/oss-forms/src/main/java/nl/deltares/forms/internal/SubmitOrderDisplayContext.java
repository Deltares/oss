package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.emails.RegistrationEmail;
import nl.deltares.emails.serializer.RegisterEmailSerializer;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.model.*;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SubmitOrderDisplayContext {

    private final DSDSiteConfiguration _configuration;
    private final User _loggedInUser;
    private final RegistrationFormContext _context;
    private final WebinarUtilsFactory _webinarUtilsFactory;
    private final DsdSessionUtils _dsdSessionUtils;
    private final DsdJournalArticleUtils _dsdJournalArticleUtils;
    private final AdminUtils _adminUtils;
    private final UserLocalService _userLocalService;
    private final DsdParserUtils _dsdParserUtils;
    private final SimpleDateFormat dateTimeFormatter;
    public SubmitOrderDisplayContext(HttpServletRequest httpServletRequest, ConfigurationProvider configurationProvider,
                                     DsdParserUtils dsdParserUtils, DsdSessionUtils dsdSessionUtils, DsdJournalArticleUtils dsdJournalArticleUtils,
                                     WebinarUtilsFactory webinarUtilsFactory,
                                     AdminUtils adminUtils, UserLocalService userLocalService) throws Exception {

        _dsdSessionUtils = dsdSessionUtils;
        _dsdParserUtils = dsdParserUtils;
        _dsdJournalArticleUtils = dsdJournalArticleUtils;
        _webinarUtilsFactory = webinarUtilsFactory;
        _adminUtils = adminUtils;
        _userLocalService = userLocalService;

        dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        ThemeDisplay themeDisplay = new CPRequestHelper(httpServletRequest).getThemeDisplay();
        _configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class,
                themeDisplay.getScopeGroupId());
        _loggedInUser = themeDisplay.getUser();

        _context = (RegistrationFormContext) httpServletRequest.getSession().getAttribute("registration-context");
        if (_context == null || _context.getRegistrationsInfo() == null || _context.getRegistrationsInfo().getAllUserRegistrations().isEmpty()) {
            throw new RegistrationFormException("No registrations in session!");
        }
    }

    public boolean showTerms() {
        return _context.getRegistrationsInfo().isPaymentRequired();
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

    public long getEventId() {
        return _configuration.eventId();
    }

    public List<Exception> storeUserInformation() {

        List<Exception> exceptions = new ArrayList<>();
        BillingInfo billingInfo = _context.getBillingInfo();
        AccountInfo accountInfo = _context.getAccountInfo();
        RegistrationsInfo registrationsInfo = _context.getRegistrationsInfo();
        for (RegistrationInfo userRegistration : registrationsInfo.getAllUserRegistrations()) {

            try {
                Registration registration = registrationsInfo.getRegistration(userRegistration.getArticleId());
                Event event = registrationsInfo.getEvent(String.valueOf(registration.getEventId()));
                storeUserInformation(userRegistration, accountInfo, billingInfo, registration, event);
            } catch (Exception e) {
                exceptions.add(new RegistrationFormException(e.getMessage()));
            }
        }
        return exceptions;
    }

    private void storeUserInformation(RegistrationInfo userRegistration, AccountInfo accountInfo, BillingInfo billingInfo,
                                      Registration registration, Event event) throws Exception {

        User registrationUser = _adminUtils.getOrCreateRegistrationUser(_loggedInUser.getCompanyId(), _loggedInUser,
                userRegistration.getEmail(), userRegistration.getFirstName(), userRegistration.getLastName(),
                userRegistration.getSalutation(), _loggedInUser.getLocale());

        storeBadgeSettings(registrationUser, _loggedInUser);

        Map<String, String> registrationAttributes = new HashMap<>();
        if (!userRegistration.getRemarks().isEmpty()) {
            registrationAttributes.put("remarks", "\"" + userRegistration.getRemarks() + "\"");
        }
        registrationAttributes.put("registration_time", dateTimeFormatter.format(new Date()));

        if (billingInfo != null) {
            addBillingAttributes(billingInfo, registrationAttributes);
        }
        AccountEntry accountEntry = accountInfo.getSelectedAccountEntry();
        if (accountEntry != null) {
            registrationAttributes.put("accountEntryId", String.valueOf(accountEntry.getAccountEntryId()));
        }

        if (_webinarUtilsFactory.isWebinarSupported(registration)) {
            registerWebinar(registrationUser, (SessionRegistration) registration, accountEntry, registrationAttributes);
        }
        _dsdSessionUtils.registerUser(registrationUser, registration, registrationAttributes, _loggedInUser, event);
    }

    private static void storeBadgeSettings(User registrationUser, User loggedInUser) throws PortalException {

        if (registrationUser.equals(loggedInUser)) {return;}

        ExpandoBridge expandoBridge = loggedInUser.getExpandoBridge();
        String nameSettingKey = BadgeInfo.badge_name_setting;
        if (expandoBridge.hasAttribute(nameSettingKey)) {
            Serializable nameSetting = expandoBridge.getAttribute(nameSettingKey, false);
            if (!registrationUser.getExpandoBridge().hasAttribute(nameSettingKey)){
                registrationUser.getExpandoBridge().addAttribute(nameSettingKey);
            }
            registrationUser.getExpandoBridge().setAttribute(nameSettingKey, nameSetting, false);
        }
        String titleSettingKey = BadgeInfo.badge_title_setting;
        if (expandoBridge.hasAttribute(titleSettingKey)){
            Serializable titleSetting = expandoBridge.getAttribute(titleSettingKey, false);
            if (!registrationUser.getExpandoBridge().hasAttribute(titleSettingKey)){
                registrationUser.getExpandoBridge().addAttribute(titleSettingKey);
            }
            registrationUser.getExpandoBridge().setAttribute(nameSettingKey, titleSetting, false);
        }

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
        String remarks = billingInfo.getRemarks();
        if (remarks != null && !remarks.isEmpty()) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_reference.name(), "\"" + remarks + "\"");
        }
        String preference = billingInfo.getPreference();
        if (preference != null && !preference.isEmpty()) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_preference.name(), preference);
        }
        long billingAddressId = billingInfo.getBillingAddressId();
        if (billingAddressId > 0) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_addressid.name(), String.valueOf(billingAddressId));
        }
    }

    private void registerWebinar(User registrationUser, SessionRegistration registration,
                                 AccountEntry accountEntry, Map<String, String> registrationAttributes) throws Exception {

        try {
            WebinarUtils webinarUtils = _webinarUtilsFactory.newInstance(registration);
            if (webinarUtils.isActive()) {
                HashMap<String, String> orgAttributes = new HashMap<>();

                if (accountEntry != null) {
                    Address billingAddress = accountEntry.getDefaultBillingAddress();
                    if (billingAddress != null) {
                        orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_address.name(), billingAddress.getStreet1());
                        orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_postal.name(), billingAddress.getZip());
                        orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_city.name(), billingAddress.getCity());
                        orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_country.name(), billingAddress.getCountry().getName());
                    }
                    orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_name.name(), accountEntry.getName());
                }
                String source = GroupServiceUtil.getGroup(registration.getGroupId()).getName(Locale.US);
                webinarUtils.registerUser(registrationUser, orgAttributes, registration.getWebinarKey(), source, registrationAttributes);
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error registering for webinar %s: %s", registration.getTitle(), e.getMessage()));
        }

    }

    public void sendRegistrationEmails(ThemeDisplay themeDisplay) throws Exception {
        if (!_configuration.enableEmails()) return;

        ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());

        RegistrationEmail registrationEmail = new RegistrationEmail(resourceBundle);
        registrationEmail.setReplyToEmail(_configuration.replyToEmail());
        registrationEmail.setSendFromEmail(_configuration.sendFromEmail());
        String bccToEmails = _configuration.bccToEmail();
        String[] emails = bccToEmails.split(";");
        for (String email : emails) {
            if (email.isEmpty()) continue;
            registrationEmail.addBCCEmail(email);
        }

        String siteUrl = PortalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay, themeDisplay.getLocale());
        registrationEmail.setSiteUrl(siteUrl);

        RegistrationsInfo registrationsInfo = _context.getRegistrationsInfo();
        Event event = registrationsInfo.getEvent(String.valueOf(_configuration.eventId()));
        if (event != null) {
            String subject = LanguageUtil.format(resourceBundle, "dsd.register.subject", event.getTitle());
            registrationEmail.setSubject(subject);
            registrationEmail.setEmailBanner(event.getEmailBannerURL(), event.getEmailBannerFileEntryId());
            registrationEmail.setEmailFooter(event.getEmailFooterURL(), event.getEmailFooterFileEntryId());
        }

        String[] structureKeys = getStructureKeys(_configuration);
        String dsdRegistrationTypeField = _configuration.dsdRegistrationTypeField();
        Map<String, String> typeTranslations = new HashMap<>();
        for (String structureKey : structureKeys) {
            typeTranslations.putAll(_dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
                    structureKey, dsdRegistrationTypeField, themeDisplay.getLocale()));
        }
        registrationEmail.setTypeTranslations(typeTranslations);

        registrationEmail.addCCEmail(_loggedInUser.getEmailAddress());

        RegisterEmailSerializer serializer = new RegisterEmailSerializer();

        List<RegistrationInfo> allUserRegistrations = registrationsInfo.getAllUserRegistrations();
        Map<User, List<Registration>> mappedInfos = mapRegistrationsByUser(allUserRegistrations, themeDisplay);
        for (Map.Entry<User, List<Registration>> entry : mappedInfos.entrySet()) {
            registrationEmail.sendRegisterEmail(serializer, entry.getKey(), entry.getValue(),
                    getRegistrationInfosForUser(allUserRegistrations, entry.getKey()));
        }
    }

    private List<RegistrationInfo> getRegistrationInfosForUser(List<RegistrationInfo> registrationInfos, User user) {
        return registrationInfos.stream().filter(info -> info.getEmail().equals(user.getEmailAddress())).collect(Collectors.toList());
    }

    private String[] getStructureKeys(DSDSiteConfiguration configuration) {
        if (configuration == null) return new String[0];
        String structureList = configuration.dsdRegistrationStructures();
        if (structureList != null && !structureList.isEmpty()){
            return StringUtil.split(structureList, ' ');
        }
        return new String[0];
    }

    private Map<User, List<Registration>> mapRegistrationsByUser(List<RegistrationInfo> registrationInfos, ThemeDisplay themeDisplay) throws PortalException {
        HashMap<User, List<Registration>> map = new HashMap<>();
        for (RegistrationInfo registrationInfo : registrationInfos) {
            User user = _userLocalService.fetchUserByEmailAddress(themeDisplay.getCompanyId(), registrationInfo.getEmail());
            if (user == null) continue;
            Registration registration = _dsdParserUtils.getRegistration(themeDisplay.getSiteGroupId(), registrationInfo.getArticleId());
            List<Registration> list = map.computeIfAbsent(user, k -> new ArrayList<>());
            list.add(registration);
        }
        return map;
    }

}
