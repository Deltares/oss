package nl.deltares.forms.portlet.action;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.*;
import nl.deltares.model.BadgeInfo;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.RegistrationRequest;
import nl.deltares.emails.DsdEmail;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.DSD_REGISTRATIONFORM,
                "mvc.command.name=/submit/register/form"
        },
        service = MVCActionCommand.class
)
public class SubmitRegistrationActionCommand extends BaseMVCActionCommand {

    private static final String PARENT_PREFIX = "parent_registration_";
    private static final String CHILD_PREFIX = "child_registration_";

    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        String redirect = ParamUtil.getString(actionRequest, "redirect");
        String action = ParamUtil.getString(actionRequest, "action");

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();

        RegistrationRequest registrationRequest = getRegistrationRequest(actionRequest, themeDisplay, action);
        if (registrationRequest == null) {
            if (!redirect.isEmpty()) {
                sendRedirect(actionRequest, actionResponse, redirect);
            }
            return;
        } else if (redirect.isEmpty()) {
            redirect = registrationRequest.getSiteURL();
        }

        LOG.info(redirect);

        boolean success = true;
        switch (action){
            case "update":
                success = removeCurrentRegistration(actionRequest, user, registrationRequest);
//                break; //skip break and continue with registering
            case "register":
                if (isRegisterSomeoneElse(actionRequest)){
                    try {
                        user = getOrCreateRegistrationUser(actionRequest, themeDisplay);
                    } catch (Exception e) {
                        success = false;
                        SessionErrors.add(actionRequest, "registration-failed", e.getMessage() );
                    }
                }
                Map<String, String> userAttributes = getUserAttributes(actionRequest);
                if (success) {
                    success = updateUserAttributes(actionRequest, user, userAttributes);
                }
                if (success){
                    success = registerUser(actionRequest, user, userAttributes, registrationRequest);
                }
                if (success){
                    success = sendEmail(actionRequest, user, registrationRequest, themeDisplay, action);
                }
                break;
            case "unregister":
                success = removeCurrentRegistration(actionRequest, user, registrationRequest);
                if (success){
                    success = sendEmail(actionRequest, user, registrationRequest, themeDisplay, action);
                }
                break;
            default:
                SessionErrors.add(actionRequest, "registration-failed", "Unsupported action " + action);
        }
        if (success){
            SessionMessages.add(actionRequest, "registration-success", new String[]{action, user.getEmailAddress(), registrationRequest.getTitle()});
            if (!redirect.isEmpty()) {
                sendRedirect(actionRequest, actionResponse, redirect);
            }
        }

    }

    private User getOrCreateRegistrationUser(ActionRequest actionRequest, ThemeDisplay themeDisplay) throws Exception {
        final String email = ParamUtil.getString(actionRequest, KeycloakUtils.ATTRIBUTES.email.name());
        final User registrationUser = UserLocalServiceUtil.fetchUserByEmailAddress(themeDisplay.getCompanyId(), email);
        if (registrationUser != null) return registrationUser; //user already exists.

        final Map<String, String> keycloakUser = keycloakUtils.getUserInfo(email);
        String userName = null;
        if (keycloakUser.isEmpty()){
            for (int i = 0; i < 3; i++) {
                String testUserName = KeycloakUtils.extractUsernameFromEmail(email, i);
                try {
                    if (!keycloakUtils.isExistingUsername(testUserName)){
                        //do not create user, instead check if username is taken.
                        userName = testUserName;
                        break;
                    }
                } catch (Exception e) {
                    LOG.warn(String.format("Error creating user for username %s on attempt %d: %s", userName, i, e.getMessage()) );
                }
            }
        } else {
            userName = keycloakUser.get("username");
        }
        String firstName = ParamUtil.getString(actionRequest,  KeycloakUtils.ATTRIBUTES.first_name.name());
        String lastName = ParamUtil.getString(actionRequest,  KeycloakUtils.ATTRIBUTES.last_name.name());
        long id = CounterLocalServiceUtil.increment(User.class.getName());
        if (userName == null) {
            userName = String.valueOf(id); //let's assume that id is unieque
        }
        final User loggedinUser = themeDisplay.getUser();
        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setScopeGroupId(loggedinUser.getGroupId());
        final Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(loggedinUser.getGroupId());
        final User user = UserLocalServiceUtil.addUser(themeDisplay.getUserId(), themeDisplay.getCompanyId(), true,
                null, null, false, userName, email, 0, null,
                themeDisplay.getLocale(), firstName, null, lastName, 0, 0, true,
                1, 1, 1970, null, loggedinUser.getGroupIds(),
                loggedinUser.getOrganizationIds(), new long[]{defaultGroupRole.getRoleId()}, loggedinUser.getUserGroupIds(), false, serviceContext);
        user.setPasswordReset(false);
        UserLocalServiceUtil.updateUser(user);
        return user;

    }

    private boolean isRegisterSomeoneElse(ActionRequest actionRequest) {
        return Boolean.parseBoolean(ParamUtil.getString(actionRequest, "registration_other"));
    }

    private boolean removeCurrentRegistration(ActionRequest actionRequest, User user, RegistrationRequest registrationRequest) {

            List<Registration> registrations = registrationRequest.getRegistrations();
            for (Registration registration : registrations) {
                try {
                    dsdSessionUtils.unRegisterUser(user, registration);
                } catch (PortalException e) {
                    //Continue anyway.
                    SessionErrors.add(actionRequest, "unregister-failed",  e.getMessage());
                }
            }
            return true;
    }

    private boolean registerUser(ActionRequest actionRequest, User user, Map<String, String> userAttributes, RegistrationRequest registrationRequest) {

        List<Registration> registrations = registrationRequest.getRegistrations();
        boolean success = true;

        try {
            dsdSessionUtils.validateRegistrations(user, registrations);
        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
            return false;
        }

        List<String> subscribableMailingIds = registrationRequest.getSubscribableMailingIds();
        if (subscribableMailingIds != null) {
            subscribableMailingIds.forEach(mailingId -> {
                if (registrationRequest.isSubscribe()) {
                    try {
                        keycloakUtils.subscribe(user.getEmailAddress(), mailingId);
                    } catch (Exception e) {
                        LOG.warn(String.format("Failed to subscribe user %s for mailing %s: %s", user.getEmailAddress(), mailingId, e.getMessage()));
                    }
                } else {
                    try {
                        keycloakUtils.unsubscribe(user.getEmailAddress(), mailingId);
                    } catch (Exception e) {
                        LOG.warn(String.format("Failed to unsubscribe user %s for mailing %s: %s", user.getEmailAddress(), mailingId, e.getMessage()));
                    }
                }
            });
        }

        HashMap<String, String> userPreferences = new HashMap<>();
        if (registrationRequest.getRemarks() != null){
            userPreferences.put("remarks" , registrationRequest.getRemarks());
        }
        HashMap<String, String> preferences;
        final BillingInfo billingInfo = registrationRequest.getBillingInfo();
        final Map<String, String> billing = billingInfo.toMap();
        final BadgeInfo badgeInfo = registrationRequest.getBadgeInfo();
        if (badgeInfo.isShowTitle()) badgeInfo.setTitle(userAttributes.get(KeycloakUtils.ATTRIBUTES.academicTitle.name()));
        if (badgeInfo.isShowInitials()) badgeInfo.setInitials(userAttributes.get(KeycloakUtils.ATTRIBUTES.initials.name()));
        final Map<String, String> badge = badgeInfo.toMap();
        for (Registration registration : registrations) {
            preferences = new HashMap<>(userPreferences);
// Always add billing info
//            if (registration.getPrice() > 0) {
                preferences.putAll(billing);
                preferences.putAll(badge);
                preferences.put("registration_time", dateTimeFormatter.format(new Date()));
//            }
            try {
                dsdSessionUtils.registerUser(user, userAttributes, registration, preferences);
            } catch (PortalException e) {
                SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
                success = false;
            }
            for (Registration childRegistration : registrationRequest.getChildRegistrations(registration)) {
                preferences = new HashMap<>(userPreferences);
                if (childRegistration.getPrice() > 0) {
                    preferences.putAll(billing);
                }
                try {
                    dsdSessionUtils.registerUser(user, userAttributes, childRegistration, new HashMap<>());
                } catch (PortalException e) {
                    SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
                    success = false;
                }
            }
        }
        return success;
    }

    private RegistrationRequest getRegistrationRequest(ActionRequest actionRequest, ThemeDisplay themeDisplay, String action) {
        List<String> articleIds;
        if (action.equals("unregister")){
            //noinspection deprecation
            articleIds = Collections.singletonList(actionRequest.getParameter("articleId"));
        } else {
            //noinspection deprecation
            articleIds = actionRequest.getParameterMap()
                    .keySet()
                    .stream()
                    .filter(strings -> strings.startsWith(PARENT_PREFIX))
                    .filter(key -> ParamUtil.getBoolean(actionRequest, key))
                    .map(key -> key.substring(PARENT_PREFIX.length()))
                    .peek(LOG::info)
                    .collect(Collectors.toList());
        }
        try {
            long siteId = themeDisplay.getSiteGroupId();

            DSDSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

            Event event = dsdParserUtils.getEvent(siteId, String.valueOf(configuration.eventId()), themeDisplay.getLocale());
            BillingInfo billingInfo = getBillingInfo(actionRequest);
            BadgeInfo badgeInfo = getBadgeInfo(actionRequest);

            RegistrationRequest registrationRequest = new RegistrationRequest(themeDisplay);
            registrationRequest.setEvent(event);
            registrationRequest.setBillingInfo(billingInfo);
            registrationRequest.setBadgeInfo(badgeInfo);
            registrationRequest.setRemarks(ParamUtil.getString(actionRequest, "remarks_registration", null));
            if (configuration.mailingIds().length() > 0) {
                registrationRequest.setSubscribableMailingIds(configuration.mailingIds());
            }
            registrationRequest.setSubscribe(ParamUtil.getBoolean(actionRequest, "subscribe_newsletter", false));
            registrationRequest.setBusInfo(configuration.enableBusInfo());
            registrationRequest.setBusTransferUrl(configuration.busTransferURL());
            for (String articleId : articleIds) {
                Registration parentRegistration = dsdParserUtils.getRegistration(siteId, articleId);
                registrationRequest.addRegistration(parentRegistration);

                List<Registration> childRegistrations = dsdSessionUtils.getChildRegistrations(parentRegistration, themeDisplay.getLocale());
                for (Registration childRegistration : childRegistrations) {
                    if (ParamUtil.getString(actionRequest, CHILD_PREFIX + childRegistration.getArticleId()).equals("true")) {
                        registrationRequest.addChildRegistration(parentRegistration, childRegistration);
                    } else if ("unregister".equals(action) && dsdSessionUtils.isUserRegisteredFor(themeDisplay.getUser(), childRegistration)){
                        registrationRequest.addChildRegistration(parentRegistration, childRegistration);
                    }
                }
            }

            String[] structureKeys = getStructureKeys(configuration);
            String dsdRegistrationTypeField = configuration.dsdRegistrationTypeField();
            Map<String, String> typeTranslations = new HashMap<>();
            for (String structureKey : structureKeys) {
                typeTranslations.putAll(dsdJournalArticleUtils.getStructureFieldOptions(event.getGroupId(),
                        structureKey, dsdRegistrationTypeField, event.getLocale()));
            }
            registrationRequest.setTypeTranslations(typeTranslations);

            return registrationRequest;

        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "send-email-failed", "Could not retrieve registration for actionId: " + Arrays.toString(articleIds.toArray()));
            LOG.debug("Could not retrieve registration for actionId: " + Arrays.toString(articleIds.toArray()));
        }
        return null;
    }

    private String[] getStructureKeys(DSDSiteConfiguration configuration) {
        if (configuration == null) return new String[0];
        String structureList = configuration.dsdRegistrationStructures();
        if (structureList != null && !structureList.isEmpty()){
            return StringUtil.split(structureList, ' ');
        }
        return new String[0];
    }

    private BadgeInfo getBadgeInfo(ActionRequest actionRequest){

        final BadgeInfo badgeInfo = new BadgeInfo();

        //Get local attributes
        for (BadgeInfo.ATTRIBUTES key : BadgeInfo.ATTRIBUTES.values()) {
            String value = ParamUtil.getString(actionRequest, key.name());
            if (Validator.isNotNull(value) && ! Validator.isBlank(value)) {
                badgeInfo.setAttribute(key, value);
            }
        }

        return badgeInfo;
    }

    private BillingInfo getBillingInfo(ActionRequest actionRequest) {

        BillingInfo billingInfo = new BillingInfo();
        for (BillingInfo.ATTRIBUTES key : BillingInfo.ATTRIBUTES.values()) {
            String value = ParamUtil.getString(actionRequest, key.name());
            if (Validator.isNotNull(value) && !Validator.isBlank(value)) {
                billingInfo.setAttribute(key, value);
            } else {
                //User selected Use Organization values option
                final KeycloakUtils.ATTRIBUTES keycloakKey = BillingInfo.getCorrespondingUserAttributeKey(key);
                if (keycloakKey == null) continue;
                final String keycloakValue = ParamUtil.getString(actionRequest, keycloakKey.name());
                if (Validator.isNull(keycloakValue) || Validator.isBlank(keycloakValue)) continue;
                billingInfo.setAttribute(key, keycloakValue);
            }
        }
        return billingInfo;
    }

    private boolean updateUserAttributes(ActionRequest actionRequest, User user, Map<String, String> attributes) {

        try {
            return keycloakUtils.updateUserAttributes(user.getEmailAddress(), attributes) < 300;
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "update-attributes-failed", e.getMessage());
            LOG.debug("Could not update keycloak attributes for user [" + user.getEmailAddress() + "]", e);
        }
        return false;
    }

    private Map<String, String> getUserAttributes(ActionRequest actionRequest) {
        Map<String, String> attributes = new HashMap<>();

        //Get keycloak attributes
        for (KeycloakUtils.ATTRIBUTES key : KeycloakUtils.ATTRIBUTES.values()) {
            String value = ParamUtil.getString(actionRequest, key.name());
            if (Validator.isNotNull(value)) {
                attributes.put(key.name(), value);
            }
        }
        return attributes;
    }

    private boolean sendEmail(ActionRequest actionRequest, User user, RegistrationRequest registrationRequest,
                              ThemeDisplay themeDisplay, String action) {
        try {

            DSDSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

            if (!configuration.enableEmails()) return true;

            ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());
            DsdEmail email = new DsdEmail(user, resourceBundle, registrationRequest);
            email.setReplyToEmail(configuration.replyToEmail());
            String bccToEmail = configuration.bccToEmail();
            if (user.getUserId() != themeDisplay.getUserId()){
                //someone else is registering for this user
                bccToEmail = bccToEmail + ';' + themeDisplay.getUser().getEmailAddress();
            }
            email.setBCCToEmail(bccToEmail);
            email.setSendFromEmail(configuration.sendFromEmail());
            switch (action) {
                case "register":
                case "update":
                    email.sendRegisterEmail();
                    return true;
                case "unregister":
                    email.sendUnregisterEmail();
                    return true;
                default:
                    return false;
            }

        } catch (Exception e) {
            SessionErrors.add(actionRequest, "send-email-failed", "Could not send " + action + " email for user [" + user.getEmailAddress() + "] : " + e.getMessage());
            LOG.error("Could not send " + action + " email for user [" + user.getEmailAddress() + "]", e);
            return false;
        }
    }

    @Reference
    private KeycloakUtils keycloakUtils;

    @Reference
    private DsdParserUtils dsdParserUtils;

    @Reference
    private DsdSessionUtils dsdSessionUtils;

    @Reference
    private DsdJournalArticleUtils dsdJournalArticleUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(SubmitRegistrationActionCommand.class);
}
