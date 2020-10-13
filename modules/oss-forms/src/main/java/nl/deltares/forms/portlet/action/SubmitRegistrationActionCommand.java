package nl.deltares.forms.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.*;
import nl.deltares.dsd.model.BillingInfo;
import nl.deltares.dsd.model.RegistrationRequest;
import nl.deltares.emails.DsdEmail;
import nl.deltares.forms.constants.OssFormPortletKeys;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.util.*;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + OssFormPortletKeys.DSD_REGISTRATIONFORM,
                "mvc.command.name=/submit/register/form"
        },
        service = MVCActionCommand.class
)
public class SubmitRegistrationActionCommand extends BaseMVCActionCommand {

    private static final String PARENT_PREFIX = "parent_registration_";
    private static final String CHILD_PREFIX = "child_registration_";

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        String redirect = ParamUtil.getString(actionRequest, "redirect");
        String action = ParamUtil.getString(actionRequest, "action");

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();

        RegistrationRequest registrationRequest = getRegistrationRequest(actionRequest, themeDisplay, action);
        if (registrationRequest == null){
            if (!redirect.isEmpty()) {
                sendRedirect(actionRequest, actionResponse, redirect);
            }
            return;
        } else {
            redirect = registrationRequest.getSiteURL();
        }

        LOG.info(redirect);

        boolean success = true;
        switch (action){
            case "update":
                success = removeCurrentRegistration(actionRequest, user, registrationRequest);
//                break; //skip break and continue with registering
            case "register":
                if (success) {
                    success = updateUserAttributes(actionRequest, user.getEmailAddress());
                }
                if (success){
                    success = registerUser(actionRequest, user, registrationRequest);
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
            SessionMessages.add(actionRequest, "registration-success", new String[]{action, user.getEmailAddress(), registrationRequest.getEvent().getTitle()});
            if (!redirect.isEmpty()) {
                sendRedirect(actionRequest, actionResponse, redirect);
            }
        }

    }

    private boolean removeCurrentRegistration(ActionRequest actionRequest, User user, RegistrationRequest registrationRequest) {

            boolean success = true;
            List<Registration> registrations = registrationRequest.getRegistrations();
            for (Registration registration : registrations) {
                try {
                    dsdSessionUtils.unRegisterUser(user, registration);
                } catch (PortalException e) {
                    SessionErrors.add(actionRequest, "unregister-failed",  e.getMessage());
                    success = false;
                }
            }
            return success;
    }

    private boolean registerUser(ActionRequest actionRequest, User user, RegistrationRequest registrationRequest) {

        Map<String, String> billingInfo = getBillingInfo(registrationRequest);
        List<Registration> registrations = registrationRequest.getRegistrations();
        boolean success = true;

        try {
            dsdSessionUtils.validateRegistrations(user, registrations);
        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
            return false;
        }
        for (Registration registration : registrations) {
            try {
                dsdSessionUtils.registerUser(user, registration, registration.getPrice() > 0 ? billingInfo : Collections.emptyMap());
            } catch (PortalException e) {
                SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
                success = false;
            }
            for (Registration childRegistration : registrationRequest.getChildRegistrations(registration)) {

                try {
                    dsdSessionUtils.registerUser(user, childRegistration, childRegistration.getPrice() > 0 ? billingInfo : Collections.emptyMap());
                } catch (PortalException e) {
                    SessionErrors.add(actionRequest, "registration-failed",  e.getMessage());
                    success = false;
                }
            }
        }
        return success;
    }

    private Map<String, String> getBillingInfo(RegistrationRequest registrationRequest) {
        HashMap<String, String> propertyMap = new HashMap<>();

        BillingInfo billingInfo = registrationRequest.getBillingInfo();
        if (billingInfo.isUseOrganization()){
            propertyMap.put(KeycloakUtils.BILLING_ATTRIBUTES.billing_address.name(), billingInfo.getAddress());
            propertyMap.put(KeycloakUtils.BILLING_ATTRIBUTES.billing_city.name(), billingInfo.getCity());
            propertyMap.put(KeycloakUtils.BILLING_ATTRIBUTES.billing_country.name(), billingInfo.getCountry());
            propertyMap.put(KeycloakUtils.BILLING_ATTRIBUTES.billing_email.name(), billingInfo.getEmail());
            propertyMap.put(KeycloakUtils.BILLING_ATTRIBUTES.billing_name.name(), billingInfo.getName());
            propertyMap.put(KeycloakUtils.BILLING_ATTRIBUTES.billing_postal.name(), billingInfo.getPostal());
            propertyMap.put(KeycloakUtils.BILLING_ATTRIBUTES.billing_vat.name(), billingInfo.getVat());
            propertyMap.put(KeycloakUtils.BILLING_ATTRIBUTES.billing_reference.name(), billingInfo.getReference());

        }
        return propertyMap;
    }

    private RegistrationRequest getRegistrationRequest(ActionRequest actionRequest, ThemeDisplay themeDisplay, String action) {
        List<String> articleIds;
        if (action.equals("unregister")){
            articleIds = Arrays.asList(actionRequest.getParameter("articleId"));
        } else {
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

            Event event = dsdParserUtils.getEvent(siteId, String.valueOf(configuration.eventId()));
            BillingInfo billingInfo = getBillingInfo(actionRequest);

            RegistrationRequest registrationRequest = new RegistrationRequest(themeDisplay);
            registrationRequest.setEvent(event);
            registrationRequest.setBillingInfo(billingInfo);

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

            return registrationRequest;

        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "send-email-failed", "Could not retrieve registration for actionId: " + Arrays.toString(articleIds.toArray()));
            LOG.debug("Could not retrieve registration for actionId: " + Arrays.toString(articleIds.toArray()));
        }
        return null;
    }

    private BillingInfo getBillingInfo(ActionRequest actionRequest) {

        BillingInfo billingInfo = new BillingInfo();
        for (KeycloakUtils.BILLING_ATTRIBUTES key : KeycloakUtils.BILLING_ATTRIBUTES.values()) {
            String value = ParamUtil.getString(actionRequest, key.name());
            if (Validator.isNotNull(value)) {
                billingInfo.setAttribute(key, value);
            }
        }
        return billingInfo;
    }

    private boolean updateUserAttributes(ActionRequest actionRequest, String emailAddress) {
        try {
            return keycloakUtils.updateUserAttributes(emailAddress, getKeycloakAttributes(actionRequest)) < 300;
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "update-attributes-failed", e.getMessage());
            LOG.debug("Could not update keycloak attributes for user [" + emailAddress + "]", e);
        }
        return false;
    }

    private Map<String, String> getKeycloakAttributes(ActionRequest actionRequest) {
        Map<String, String> attributes = new HashMap<>();

        for (KeycloakUtils.ATTRIBUTES key : KeycloakUtils.ATTRIBUTES.values()) {
            String value = ParamUtil.getString(actionRequest, key.name());
            if (Validator.isNotNull(value)) {
                attributes.put(key.name(), value);
            }
        }
        String billingRef = ParamUtil.getString(actionRequest, KeycloakUtils.BILLING_ATTRIBUTES.billing_reference.name());
        String billingVat = ParamUtil.getString(actionRequest, KeycloakUtils.BILLING_ATTRIBUTES.billing_vat.name());
        if (Validator.isNotNull(billingRef)) attributes.put(KeycloakUtils.ATTRIBUTES.pay_reference.name(), billingRef);
        if (Validator.isNotNull(billingVat)) attributes.put(KeycloakUtils.ATTRIBUTES.org_vat.name(), billingVat);

        return attributes;
    }

    private boolean sendEmail(ActionRequest actionRequest, User user, RegistrationRequest registrationRequest,
                              ThemeDisplay themeDisplay, String action) {
        try {

            DSDSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

            ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());
            DsdEmail email = new DsdEmail(user, resourceBundle, registrationRequest);
            email.setReplyToEmail(configuration.replyToEmail());
            email.setSendFromEmail(configuration.sendFromEmail());
            switch (action){
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
            LOG.debug("Could not send " + action + " email for user [" + user.getEmailAddress() + "]", e);
            return false;
        }
    }

    @Reference
    private KeycloakUtils keycloakUtils;

    @Reference
    private DsdParserUtils dsdParserUtils;

    @Reference
    private DsdSessionUtils dsdSessionUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(SubmitRegistrationActionCommand.class);
}
