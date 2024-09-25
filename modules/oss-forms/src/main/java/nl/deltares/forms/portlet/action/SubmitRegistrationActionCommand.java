package nl.deltares.forms.portlet.action;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.*;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.*;
import nl.deltares.emails.DsdEmail;
import nl.deltares.forms.portlet.DsdRegistrationFormConfiguration;
import nl.deltares.model.RegistrationRequest;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.constants.BillingConstants;
import nl.deltares.portal.constants.LicenseConstants;
import nl.deltares.portal.constants.OrganizationConstants;
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
import java.util.concurrent.atomic.AtomicBoolean;
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

    @Reference
    private URLUtils urlUtils;

    @Reference
    private CommerceUtils commerceUtils;
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
        if (action.isEmpty()){
            action = actionRequest.getPreferences().getValue("action", "");
        }
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

        DSDSiteConfiguration configuration = _configurationProvider
                .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

        RegistrationRequest registrationRequest = getRegistrationRequest(actionRequest, themeDisplay, action, configuration);
        if (registrationRequest == null) {
            if (!redirect.isEmpty()) {
                sendRedirect(actionRequest, actionResponse, redirect);
            }
            return;
        }

        boolean success = true;
        switch (action){
            case "update":
                success = removeCurrentRegistration(actionRequest, themeDisplay.getUser(), registrationRequest);
//                break; //skip break and continue with registering
            case "register":
                final Map<User, List<Registration>> userRegistrations = new HashMap<>() ;
                final Map<Registration, List<User>> registrationUsers = new HashMap<>() ;
                //Get a list of registrations sorted by user
                for (Registration registration : registrationRequest.getRegistrations()) {
                    List<User> registeredUsers = getOrCreateUsersForRegistration(actionRequest, registration, themeDisplay);
                    registrationUsers.put(registration, registeredUsers);
                    for (User registeredUser : registeredUsers) {
                        final ArrayList<Registration> userRegistrationsList = new ArrayList<>();
                        List<Registration> registrations = userRegistrations.putIfAbsent(registeredUser, userRegistrationsList);
                        if (registrations == null) registrations = userRegistrationsList;
                        registrations.add(registration);
                    }
                }
                if (!validateSelection(actionRequest, userRegistrations, registrationRequest)) {
                    success = false;
                    break;
                }
                Map<Long, Long> registrationOrdersMap = new HashMap<>();
                if (registrationRequest.isPaymentRequired()){
                    AccountEntry accountEntry = getAccountEntry(actionRequest, registrationRequest, themeDisplay.getUser());
                    registrationRequest.setAccountEntry(accountEntry);
                    //todo: we will create orders in a later stage.

                }

                setUserSubscriptions(actionRequest, themeDisplay.getUser(), registrationRequest);

                if(success) {
                    success = registerUsers(actionRequest, registrationUsers, registrationRequest, themeDisplay.getUser(), registrationOrdersMap);
                }
                if (success) {
                    success = updateUserAttributes(actionRequest, userRegistrations.keySet(), registrationRequest);
                }
                if (success){
                    success = sendEmail(actionRequest, themeDisplay.getUser(), registrationRequest, themeDisplay, action, configuration);
                }
                break;
            case "unregister":
                User user = null;
                String userId = ParamUtil.getString(actionRequest, "userId");
                if (userId != null && !userId.isEmpty()) {
                    user = UserLocalServiceUtil.fetchUser(Long.parseLong(userId));
                }
                if (user == null) user = themeDisplay.getUser();
                success = removeCurrentRegistration(actionRequest, user, registrationRequest);
                if (success){
                    success = sendEmail(actionRequest, user, registrationRequest, themeDisplay, action, configuration);
                }
                break;
            default:
                SessionErrors.add(actionRequest, "registration-failed", "Unsupported action " + action);
                success = false;
        }
        if (success){
            SessionMessages.add(actionRequest, "registration-success", new String[]{action, registrationRequest.getTitle()});
            redirect = getRedirectURL(themeDisplay, action + "_success");
            sendRedirect(actionRequest, actionResponse, redirect);
        } else {
            redirect = getRedirectURL(themeDisplay, action + "_fail");
            final String namespace = actionResponse.getNamespace();
            redirect = urlUtils.setUrlParameter(redirect, namespace, "action", ParamUtil.getString(actionRequest, "action"));
            redirect = urlUtils.setUrlParameter(redirect, namespace, "ids", ParamUtil.getString(actionRequest, "ids"));
            redirect = urlUtils.setUrlParameter(redirect, "", "p_p_id", themeDisplay.getPortletDisplay().getId());
            sendRedirect(actionRequest, actionResponse, redirect);
        }

    }

    private AccountEntry createPersonAccount(RegistrationRequest registrationRequest, User registrationUser) throws PortalException {

        User billingUser;
        String billingEmail = null;
        try {
            billingEmail = registrationRequest.getRequestParameter(BillingConstants.EMAIL);
            billingUser = adminUtils.getOrCreateRegistrationUser(registrationUser.getCompanyId(), registrationUser,
                    billingEmail,
                    registrationRequest.getRequestParameter(BillingConstants.FIRST_NAME),
                    registrationRequest.getRequestParameter(BillingConstants.LAST_NAME), null, registrationRequest.getEvent().getLocale());
        } catch (Exception e) {
            LOG.warn(String.format("Error creating billing user for email %s. Defaulting to logged in user %s: %s",
                    billingEmail, registrationUser.getEmailAddress(), e.getMessage()));
            billingUser = registrationUser;
        }

        AccountEntry accountEntry = AccountEntryLocalServiceUtil.fetchPersonAccountEntry(billingUser.getUserId());
        if (accountEntry == null) {
            accountEntry = commerceUtils.createPersonAccountEntry(billingUser, registrationRequest.getRequestParameters());
        }
        return accountEntry;
    }


    private boolean validateSelection(ActionRequest actionRequest, Map<User, List<Registration>> userRegistrations, RegistrationRequest registrationRequest){
        boolean success = true;
        try {
            for (User user : userRegistrations.keySet()) {
                final List<Registration> registrations = userRegistrations.get(user);
                dsdSessionUtils.validateRegistrations(user, registrations, getChildRegistrations(registrations, registrationRequest));
            }
        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
            success = false;
        }
        return success;
    }
    private boolean registerUsers(ActionRequest actionRequest, Map<Registration, List<User>> registrationUsers,
                                  RegistrationRequest registrationRequest, User registrationUser, Map<Long, Long> registrationOrdersMap) {

        boolean success = true;
        final Map<String, String> registrationAttributes = registrationRequest.getRequestParameters();
        for (Registration registration : registrationUsers.keySet()) {

            final Long orderId = registrationOrdersMap.get(Long.parseLong(registration.getArticleId()));
            if (orderId != null) registrationAttributes.put("orderId", String.valueOf(orderId));

            final List<User> users = registrationUsers.get(registration);
            for (User user : users) {
                try {
                    dsdSessionUtils.registerUser(user, registrationAttributes, registration, registrationUser);
                } catch (PortalException e) {
                    SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
                    success = false;
                }
                for (Registration childRegistration : registrationRequest.getChildRegistrations(registration)) {
                    try {
                        dsdSessionUtils.registerUser(user, registrationAttributes, childRegistration, registrationUser);
                    } catch (PortalException e) {
                        SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
                        success = false;
                    }
                }

            }
        }
        return success;
    }

    private List<User> getOrCreateUsersForRegistration(ActionRequest actionRequest, Registration registration, ThemeDisplay themeDisplay) throws Exception {

        final int userCount = ParamUtil.getInteger(actionRequest, "count_parent_registration_" + registration.getArticleId());
        List<User> registrationUsers = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            String postfix = i == 0? "": String.valueOf(i);
            String jobTitles = ParamUtil.getString(actionRequest, "jobTitles_" + registration.getArticleId() + postfix);
            String firstName = ParamUtil.getString(actionRequest, "firstName_" + registration.getArticleId() + postfix);
            String lastName = ParamUtil.getString(actionRequest, "lastName_" + registration.getArticleId() + postfix);
            String email = ParamUtil.getString(actionRequest, "email_" + registration.getArticleId() + postfix);
            registrationUsers.add(adminUtils.getOrCreateRegistrationUser(themeDisplay.getCompanyId(), themeDisplay.getUser(),
                    email, firstName, lastName, jobTitles, themeDisplay.getLocale()));

        }
        return registrationUsers;
    }

    private String getRedirectURL(ThemeDisplay themeDisplay, String key) {

        String friendlyUrl = null;
        try {
            String configuredRedirect;
            final DsdRegistrationFormConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(DsdRegistrationFormConfiguration.class, themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId());
            switch (key){
                case "register_success":
                    configuredRedirect =  configuration.registerSuccessURL();
                    break;
                case "unregister_success":
                    configuredRedirect =  configuration.unregisterSuccessURL();
                    break;
                case "update_success":
                    configuredRedirect =  configuration.updateSuccessURL();
                    break;
                default:
                    //todo: specify failure types
                    configuredRedirect =  configuration.failURL();
            }

            if (configuredRedirect == null || configuredRedirect.isEmpty()) {
                friendlyUrl = PortalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay, themeDisplay.getLocale());
            } else {
                friendlyUrl = PortalUtil.getAbsoluteURL(themeDisplay.getRequest(), configuredRedirect);
            }
            LOG.info("Redirecting registration request to " + friendlyUrl);
        } catch (PortalException e) {
            LOG.warn("Failed to get configuredRedirect URL: " + e.getMessage());
        }
        return friendlyUrl;
    }

    private boolean removeCurrentRegistration(ActionRequest actionRequest, User user, RegistrationRequest registrationRequest) {

            List<Registration> registrations = registrationRequest.getRegistrations();
            boolean result = true;
            for (Registration registration : registrations) {
                try {
                    dsdSessionUtils.unRegisterUser(user, registration);
                } catch (PortalException e) {
                    //Continue anyway.
                    SessionErrors.add(actionRequest, "unregister-failed",  e.getMessage());
                    result = false;
                }
            }
            return result;
    }

    private void setUserSubscriptions(ActionRequest actionRequest, User registrationUser, RegistrationRequest registrationRequest){
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

            if (!subscribeIds.isEmpty()) {
                try {
                    subscriptionUtils.subscribeAll(registrationUser, subscribeIds);
                } catch (Exception e) {
                    LOG.warn(String.format("Failed to subscribe user %s for mailing %s: %s", registrationUser.getEmailAddress(), subscribeIds, e.getMessage()));
                }
            }
            if (!unsubscribeIds.isEmpty()) {
                try {
                    subscriptionUtils.unsubscribeAll(registrationUser.getEmailAddress(), unsubscribeIds);
                } catch (Exception e) {
                    LOG.warn(String.format("Failed to unsubscribe user %s for mailing %s: %s", registrationUser.getEmailAddress(), unsubscribeIds, e.getMessage()));
                }
            }
        }
    }

    private List<Registration> getChildRegistrations(List<Registration> registrations, RegistrationRequest registrationRequest) {
        final ArrayList<Registration> children = new ArrayList<>();
        for (Registration registration : registrations) {
            children.addAll(registrationRequest.getChildRegistrations(registration));
        }
        return children;
    }

    private RegistrationRequest getRegistrationRequest(ActionRequest actionRequest, ThemeDisplay themeDisplay, String action, DSDSiteConfiguration configuration) {
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

            Event event = dsdParserUtils.getEvent(siteId, String.valueOf(configuration.eventId()), themeDisplay.getLocale());
            RegistrationRequest registrationRequest = new RegistrationRequest(themeDisplay);
            registrationRequest.setEvent(event);
            loadRegistrationParameters(actionRequest, registrationRequest);
            if (!configuration.mailingIds().isEmpty()) {
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

    private AccountEntry getAccountEntry(ActionRequest actionRequest, RegistrationRequest registrationRequest, User registrationUser){
        final long selectedAccountEntryId = ParamUtil.getLong(actionRequest, "select_organization");
        AccountEntry accountEntry = null;
        if (selectedAccountEntryId > 0){
            accountEntry = AccountEntryLocalServiceUtil.fetchAccountEntry(selectedAccountEntryId);
        }
        if (accountEntry == null){
            try {
                accountEntry = createPersonAccount(registrationRequest, registrationUser);
            } catch (Exception e) {
                SessionErrors.add(actionRequest, "registration-failed",  "Error creating AccountEntry: " + e.getMessage());
                return null;
            }
        }
        try {
            if ("person".equals(accountEntry.getType())) {
                //Only personal accounts can be updated
                commerceUtils.updateAccountEntry(accountEntry, registrationRequest.getRequestParameters());
            }
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "registration-failed",  "Error updating AccountEntry: " + e.getMessage());
        }
        return accountEntry;
    }

    private void loadRegistrationParameters(ActionRequest actionRequest, RegistrationRequest registrationRequest) {

        //noinspection DuplicatedCode
        for (String key : OrganizationConstants.ORG_KEYS) {
            registrationRequest.setRequestParameter(key, ParamUtil.getString(actionRequest, key));
        }
        for (String key : BillingConstants.ORG_KEYS) {
            registrationRequest.setRequestParameter(key, ParamUtil.getString(actionRequest, key));
        }

        for (String key : BillingConstants.BILLING_KEY) {
            registrationRequest.setRequestParameter(key, ParamUtil.getString(actionRequest, key));
        }

        for (String key : LicenseConstants.KEYS) {
            registrationRequest.setRequestParameter(key, ParamUtil.getString(actionRequest, key));
        }
        registrationRequest.setRequestParameter("remarks", ParamUtil.getString(actionRequest, "remarks"));

    }

    private boolean updateUserAttributes(ActionRequest actionRequest, Set<User> users, RegistrationRequest registrationRequest) {

        boolean success = true;
        final AccountEntry accountEntry = registrationRequest.getAccountEntry();
        if (accountEntry != null) return true; //We no longer write organization info to Keycloak if user is linked to an AccountEntry.
        HashMap<String, String> attributes = new HashMap<>();
        for (String key : OrganizationConstants.ORG_KEYS) {
            attributes.put(key, registrationRequest.getRequestParameter(key));
        }
        for (User user : users) {
            try {
                return keycloakUtils.updateUserAttributes(user.getEmailAddress(), attributes) < 300;
            } catch (Exception e) {
                SessionErrors.add(actionRequest, "update-attributes-failed",  new String[]{user.getEmailAddress(), e.getMessage()});
                success = false;
            }
        }
        return success;
    }

    private boolean sendEmail(ActionRequest actionRequest, User user, RegistrationRequest registrationRequest,
                              ThemeDisplay themeDisplay, String action, DSDSiteConfiguration configuration) {
        try {

            if (!configuration.enableEmails()) return true;

            ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());
            DsdEmail email = new DsdEmail(user, resourceBundle, registrationRequest);
            email.setReplyToEmail(configuration.replyToEmail());
            String bccToEmail = configuration.bccToEmail();
            if (user.getUserId() != themeDisplay.getUserId()){
                //someone else is registering for this user
                bccToEmail = bccToEmail + ';' + themeDisplay.getUser().getEmailAddress();
            }

            AtomicBoolean isCancellationPeriodExceeded = new AtomicBoolean(false);
            registrationRequest.getRegistrations().forEach(registration -> {
                if (registration.isCancellationPeriodExceeded()){
                    isCancellationPeriodExceeded.set(true);
                }
            });
            if (isCancellationPeriodExceeded.get() && !configuration.cancellationReplyToEmail().isEmpty()) {
                bccToEmail += ';' + configuration.cancellationReplyToEmail();
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

    @Reference
    private EmailSubscriptionUtils subscriptionUtils;

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
