package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UserRegistrationContext {

    private final DsdSessionUtils _sessionUtils;
    private final ThemeDisplay _themeDisplay;
    private final DsdParserUtils _dsdParserUtils;
    private final HttpServletRequest _httpServletRequest;
    private final WebinarUtilsFactory _webinarUtilsFactory;
    private final UserLocalService _userLocalService;
    private final KeycloakUtils _keycloakUtils;

    public UserRegistrationContext(HttpServletRequest httpServletRequest, DsdSessionUtils sessionUtils,
                                   DsdParserUtils parserUtils, WebinarUtilsFactory webinarUtilsFactory,
                                   UserLocalService userLocalService, KeycloakUtils keycloakUtils) throws Exception {
        _sessionUtils = sessionUtils;
        _dsdParserUtils = parserUtils;
        _webinarUtilsFactory = webinarUtilsFactory;
        _userLocalService = userLocalService;
        _keycloakUtils = keycloakUtils;

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _httpServletRequest = httpServletRequest;
    }

    public void storeUserInformation(List<RegistrationInfo> registrationInformation, BillingInfo billingInfo, AccountEntry accountEntry) {

        boolean addIfExceptions = false;
        List<RegistrationFormException> exceptions;
        if (SessionErrors.contains(_httpServletRequest, RegistrationFormException.class)) {
            exceptions = (List<RegistrationFormException>) SessionErrors.get(_httpServletRequest, RegistrationFormException.class);
        } else {
            addIfExceptions = true;
            exceptions = new ArrayList<>();
        }

        for (RegistrationInfo registrationInfo : registrationInformation) {
            Registration registration;
            try {
                registration = _dsdParserUtils.getRegistration(_themeDisplay.getSiteGroupId(), registrationInfo.getRegistrationId());
            } catch (PortalException e) {
                exceptions.add(new RegistrationFormException(e.getMessage()));
                continue;
            }

            User registrationUser;
            try {
                registrationUser = getOrCreateRegistrationUser(_themeDisplay.getCompanyId(), _themeDisplay.getUser(),
                        registrationInfo.getEmail(), registrationInfo.getFirstName(), registrationInfo.getLastName(),
                        _themeDisplay.getLocale());
            } catch (Exception e) {
                exceptions.add(new RegistrationFormException(e.getMessage()));
                continue;
            }
//            THis should not be necessary as check was already performed.
//            if (_sessionUtils.isUserRegisteredFor(registrationUser, registration)){
//                exceptions.add(new RegistrationFormException(String.format("User '%s' is already registered for session '%s' !",
//                        registrationUser.getEmailAddress(), registration.getTitle())));
//                continue;
//            }

            Map<String, String> registrationAttributes = new HashMap<>();
            String remarks = registrationInfo.getRemarks();
            if (remarks != null) registrationAttributes.put("remarks", remarks);

            if (billingInfo != null) {
                addBillingAttributes(billingInfo, registrationAttributes);
                long billingAddressId = billingInfo.getBillingAddressId();
                if (!billingInfo.isDefaultBillingAddress()) {
                    registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_addressid.name(), String.valueOf(billingAddressId));
                }
            }
            if (_webinarUtilsFactory.isWebinarSupported(registration)) {
                try {
                    registrationAttributes.putAll(registerWebinar(registrationUser, (SessionRegistration) registration, accountEntry));
                } catch (Exception e) {
                    exceptions.add(new RegistrationFormException(e.getMessage()));
                    continue;
                }
            }

            try {
                _sessionUtils.registerUser(registrationUser, registration, registrationAttributes, _themeDisplay.getUser());
            } catch (PortalException e) {
                exceptions.add(new RegistrationFormException(e.getMessage()));
            }
        }

        if (addIfExceptions && !exceptions.isEmpty()) {
            SessionErrors.add(_httpServletRequest, RegistrationFormException.class, exceptions);
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
        String preference = billingInfo.getPreference();
        if (preference != null && !preference.isEmpty()) {
            registrationAttributes.put(BillingInfo.ATTRIBUTES.billing_preference.name(), preference);
        }
    }

    private Map<String, String> registerWebinar(User registrationUser, SessionRegistration registration, AccountEntry accountEntry) throws Exception {

        try {
            WebinarUtils webinarUtils = _webinarUtilsFactory.newInstance(registration);
            if (webinarUtils.isActive()) {
                HashMap<String, String> webinarAttributes = new HashMap<>();
                HashMap<String, String> orgAttributes = new HashMap<>();

                Address billingAddress = accountEntry.getDefaultBillingAddress();
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_address.name(), billingAddress.getStreet1());
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_postal.name(), billingAddress.getZip());
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_city.name(), billingAddress.getCity());
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_country.name(), billingAddress.getCountry().getName());
                orgAttributes.put(KeycloakUtils.ATTRIBUTES.org_name.name(), accountEntry.getName());

                String source = GroupServiceUtil.getGroup(registration.getGroupId()).getName(Locale.US);
                webinarUtils.registerUser(registrationUser, orgAttributes, registration.getWebinarKey(), source, webinarAttributes);

                return webinarAttributes;
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error registering for webinar %s: %s", registration.getTitle(), e.getMessage()));
        }

        return Collections.emptyMap();
    }

    private User getOrCreateRegistrationUser(long companyId, User loggedInUser, String registrationEmail,
                                             String firstName, String lastName, Locale locale) throws Exception {

        if (registrationEmail == null || registrationEmail.isEmpty()) {
            throw new IllegalArgumentException("Registration email missing");
        }
        final User registrationUser = _userLocalService.fetchUserByEmailAddress(companyId, registrationEmail);
        if (registrationUser != null) return registrationUser; //user already exists.

        final Map<String, String> keycloakUser = _keycloakUtils.getUserInfo(registrationEmail);
        String userName = null;
        if (keycloakUser.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                String testUserName = KeycloakUtils.extractUsernameFromEmail(registrationEmail, i);
                if (!_keycloakUtils.isExistingUsername(testUserName)) {
                    //do not create user, instead check if username is taken.
                    userName = testUserName;
                    break;
                }
            }
        } else {
            userName = keycloakUser.get("username");
        }
        long id = CounterLocalServiceUtil.increment(User.class.getName());
        if (userName == null) {
            userName = String.valueOf(id); //let's assume that id is unieque
        }
        User newUser = _userLocalService.createUser(CounterLocalServiceUtil.increment(UserLocalService.class.getName()));
        newUser.setCompanyId(companyId);
        newUser.setScreenName(userName);
        newUser.setEmailAddress(registrationEmail);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        _userLocalService.updateUser(newUser);

        return newUser;

    }

}
