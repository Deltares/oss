package nl.deltares.forms.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.*;
import nl.deltares.emails.DsdEmail;
import nl.deltares.model.BadgeInfo;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.RegistrationRequest;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

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
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    static {
        final TimeZone gmt = TimeZone.getTimeZone("GMT");
        dateTimeFormatter.setTimeZone(gmt);
        dateFormat.setTimeZone(gmt);
    }
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
                User registrationUser = null;
                if (isRegisterSomeoneElse(actionRequest)){
                    try {
                        registrationUser = themeDisplay.getUser();
                        final String email = ParamUtil.getString(actionRequest, KeycloakUtils.ATTRIBUTES.email.name());
                        final String firstName = ParamUtil.getString(actionRequest,  KeycloakUtils.ATTRIBUTES.first_name.name());
                        final String lastName = ParamUtil.getString(actionRequest,  KeycloakUtils.ATTRIBUTES.last_name.name());
                        user = adminUtils.getOrCreateRegistrationUser(themeDisplay.getCompanyId(), registrationUser,
                                email, firstName, lastName, themeDisplay.getLocale());
                    } catch (Exception e) {
                        success = false;
                        SessionErrors.add(actionRequest, "registration-failed", e.getMessage() );
                    }
                }
                Map<String, String> userAttributes = getUserAttributes(actionRequest);
                if (success) {
                    registerAcceptedTerms(userAttributes);
                    success = updateUserAttributes(actionRequest, user, userAttributes);
                }
                if (success){
                    success = registerUser(actionRequest, user, userAttributes, registrationRequest, registrationUser);
                }
                if (success){
                    success = sendEmail(actionRequest, user, registrationRequest, themeDisplay, action);
                }
                break;
            case "unregister":
                String userId = ParamUtil.getString(actionRequest, "userId");
                if (userId != null && !userId.isEmpty()) {
                    user = UserLocalServiceUtil.fetchUser(Long.parseLong(userId));
                }
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

    private void registerAcceptedTerms(Map<String, String> userAttributes) {
        final long now = System.currentTimeMillis();
        final String timeStamp = dateFormat.format(new Date(now));
        userAttributes.put("terms.general-course-conditions-deltares", timeStamp);
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

    private boolean registerUser(ActionRequest actionRequest, User user, Map<String, String> userAttributes,
                                 RegistrationRequest registrationRequest, User registrationUser) {

        boolean success = true;
        try {
            dsdSessionUtils.validateRegistrations(user, registrationRequest.getRegistrations(), getChildRegistrations(registrationRequest));
        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
            return false;
        }

        List<String> subscribableMailingIds = registrationRequest.getSubscribableMailingIds();
        if (subscribableMailingIds != null) {
            List<String> subscribeIds = new ArrayList<>();
            List<String> unsubscribeIds = new ArrayList<>();
            subscribableMailingIds.forEach(mailingId -> {
                final String selected = ParamUtil.getString(actionRequest, "subscription-" + mailingId);
                if (Boolean.parseBoolean(selected)) {
                    subscribeIds.add(mailingId);
                } else {
                    unsubscribeIds.add(mailingId);
                }
            });

            if (subscribeIds.size() > 0) {
                try {
                    subscriptionUtils.subscribeAll(user, subscribeIds);
                } catch (Exception e) {
                    LOG.warn(String.format("Failed to subscribe user %s for mailing %s: %s", user.getEmailAddress(), subscribeIds, e.getMessage()));
                }
            }
            if (unsubscribeIds.size() > 0) {
                try {
                    subscriptionUtils.unsubscribeAll(user.getEmailAddress(), unsubscribeIds);
                } catch (Exception e) {
                    LOG.warn(String.format("Failed to unsubscribe user %s for mailing %s: %s", user.getEmailAddress(), unsubscribeIds, e.getMessage()));
                }
            }
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
        for (Registration registration : registrationRequest.getRegistrations()) {
            preferences = new HashMap<>(userPreferences);
// Always add billing info
//            if (registration.getPrice() > 0) {
                preferences.putAll(billing);
                preferences.putAll(badge);
                preferences.put("registration_time", dateTimeFormatter.format(new Date()));
                preferences.put(KeycloakUtils.ATTRIBUTES.org_name.name(), userAttributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()));
//            }
            try {
                dsdSessionUtils.registerUser(user, userAttributes, registration, preferences, registrationUser);
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
                    dsdSessionUtils.registerUser(user, userAttributes, childRegistration, new HashMap<>(), registrationUser);
                } catch (PortalException e) {
                    SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
                    success = false;
                }
            }
        }
        return success;
    }

    private List<Registration> getChildRegistrations(RegistrationRequest registrationRequest) {
        final ArrayList<Registration> children = new ArrayList<>();
        for (Registration registration : registrationRequest.getRegistrations()) {
            children.addAll(registrationRequest.getChildRegistrations(registration));
        }
        return children;
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

                List<Registration> childRegistrations = dsdSessionUtils.getChildRegistrations(parentRegistration);
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
                        structureKey, dsdRegistrationTypeField, themeDisplay.getLocale()));
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
    private AdminUtils adminUtils;

    @Reference
    private KeycloakUtils keycloakUtils;

    private EmailSubscriptionUtils subscriptionUtils;
    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.AT_LEAST_ONE
    )
    protected void setSubscriptionUtilsUtils(EmailSubscriptionUtils subscriptionUtils) {
        if (!subscriptionUtils.isActive()) return;
        if (this.subscriptionUtils == null){
            this.subscriptionUtils = subscriptionUtils;
        } else if (subscriptionUtils.isDefault()){
            this.subscriptionUtils = subscriptionUtils;
        }
    }

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
