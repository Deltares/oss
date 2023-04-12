package nl.deltares.forms.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.emails.DownloadEmail;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.DownloadRequest;
import nl.deltares.model.LicenseInfo;
import nl.deltares.portal.configuration.DownloadSiteConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.model.impl.Subscription;
import nl.deltares.portal.model.impl.Terms;
import nl.deltares.portal.utils.*;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.CreateDownloadLinksRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.DOWNLOADFORM,
                "mvc.command.name=/submit/download/form"
        },
        service = MVCActionCommand.class
)
public class SubmitDownloadActionCommand extends BaseMVCActionCommand {

    private static final String DOWNLOAD_PREFIX = "download_";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        String redirect = ParamUtil.getString(actionRequest, "redirect");
        String action = ParamUtil.getString(actionRequest, "action");

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();

        DownloadRequest downloadRequest = getDownloadRequest(actionRequest, themeDisplay);
        if (downloadRequest == null) {
            if (!redirect.isEmpty()) {
                sendRedirect(actionRequest, actionResponse, redirect);
            }
            return;
        } else if (redirect.isEmpty()) {
            redirect = downloadRequest.getSiteURL();
        }

        LOG.info(redirect);

        boolean success = true;
        if ("download".equals(action)) {

            User registrationUser;
            //noinspection DuplicatedCode
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

            if (success) {
                Map<String, String> userAttributes = new HashMap<>();
                if (downloadRequest.isUserInfoRequired()) {
                    userAttributes.putAll(getUserAttributes(actionRequest));
                } else {
                    userAttributes.putAll(getMinimumAttributes(actionRequest));
                }
                registerAcceptedTerms(downloadRequest, userAttributes);
                downloadRequest.setUserAttributes(userAttributes);
                success = updateUserAttributes(actionRequest, user, userAttributes);

            }
            if (success && downloadRequest.isShowSubscription()) {
                success = updateSubscriptions(downloadRequest, user);
            }

            if (success) {
                DownloadEmail loadedEmail = prepareDownloadEmail(actionRequest, user, downloadRequest, themeDisplay, "download");
                success = createShareLinks(actionRequest, downloadRequest, user, loadedEmail);
            }

        } else {
            SessionErrors.add(actionRequest, "sendlink-failed", "Unsupported action " + action);
        }
        if (success) {
            SessionMessages.add(actionRequest, "sendlink-success", new String[]{action, user.getEmailAddress()});
            if (!redirect.isEmpty()) {
                sendRedirect(actionRequest, actionResponse, redirect);
            }
        }

    }

    private boolean createShareLinks(ActionRequest actionRequest, DownloadRequest downloadRequest, User user, DownloadEmail loadedEmail) {
        String id = SubmitDownloadActionCommand.class.getName() + user.getUserId();

        final DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(id);
        if (dataRequest != null) {
            if (dataRequest.getStatus() == DataRequest.STATUS.running || dataRequest.getStatus() == DataRequest.STATUS.pending) {
                SessionMessages.add(actionRequest, String.format("Download process is still running. %s", dataRequest.getStatusMessage()));
                return false;
            }
            //currently we do not do anything with progress
            instance.removeDataRequest(dataRequest);
        }
        try {
            dataRequest = new CreateDownloadLinksRequest(id, user, downloadRequest, downloadUtils, loadedEmail, licenseManagerUtils);
        } catch (IOException e) {
            SessionErrors.add(actionRequest, String.format("Failed to create downloadLinks request for user %s : %s",
                    user.getEmailAddress(), e.getMessage()));
            return false;
        }
        instance.addToQueue(dataRequest);
        return true;
    }

    private void registerAcceptedTerms(DownloadRequest downloadRequest, Map<String, String> userAttributes) {

        List<Terms> terms = downloadRequest.getTerms();

        final long now = System.currentTimeMillis();
        final String timeStamp = dateFormat.format(new Date(now));

        for (Terms term : terms) {
            userAttributes.put("terms." + term.getTitle(), timeStamp);
        }

    }

    private boolean updateSubscriptions(DownloadRequest downloadRequest, User user) {
        Set<Subscription> subscriptions = downloadRequest.getSubscriptions();
        if (subscriptions != null) {
            subscriptions.forEach(subscription -> {
                if (downloadRequest.isSubscribe(subscription)) {
                    try {
                        subscriptionUtils.subscribe(user, subscription.getId());
                    } catch (Exception e) {
                        LOG.warn(String.format("Failed to subscribe user %s for mailing %s: %s", user.getEmailAddress(), subscription.getName(), e.getMessage()));
                    }
                } else {
                    try {
                        subscriptionUtils.unsubscribe(user.getEmailAddress(), subscription.getId());
                    } catch (Exception e) {
                        LOG.warn(String.format("Failed to unsubscribe user %s for mailing %s: %s", user.getEmailAddress(), subscription.getName(), e.getMessage()));
                    }
                }
            });
        }
        return true;
    }

    private DownloadRequest getDownloadRequest(ActionRequest actionRequest, ThemeDisplay themeDisplay) {
        List<String> articleIds;

        //noinspection deprecation
        articleIds = actionRequest.getParameterMap()
                .keySet()
                .stream()
                .filter(strings -> strings.startsWith(DOWNLOAD_PREFIX))
                .filter(key -> ParamUtil.getBoolean(actionRequest, key))
                .map(key -> key.substring(DOWNLOAD_PREFIX.length()))
                .peek(LOG::info)
                .collect(Collectors.toList());

        try {
            long siteId = themeDisplay.getSiteGroupId();

            DownloadSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());

            BillingInfo billingInfo = getBillingInfo(actionRequest, themeDisplay.getUser());

            DownloadRequest downloadRequest = new DownloadRequest(themeDisplay);
            downloadRequest.setBillingInfo(billingInfo);
            downloadRequest.setLicenseInfo(getLicenseInfo(actionRequest));
            for (String articleId : articleIds) {
                Download downloadArticle = (Download) dsdParserUtils.toDsdArticle(siteId, articleId);
                downloadRequest.addDownload(downloadArticle);
            }
            setSubscriptionSelection(actionRequest, downloadRequest);
            downloadRequest.setBannerUrl(configuration.bannerURL());
            return downloadRequest;

        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "send-email-failed", "Could not retrieve download for actionId: " + Arrays.toString(articleIds.toArray()));
            LOG.debug("Could not retrieve download for actionId: " + Arrays.toString(articleIds.toArray()));
        }
        return null;
    }

    private LicenseInfo getLicenseInfo(ActionRequest actionRequest) {
        LicenseInfo licenseInfo = new LicenseInfo();

        if (Boolean.parseBoolean(ParamUtil.getString(actionRequest, LicenseInfo.LICENSETYPES.network.name()))) {
            licenseInfo.setLicenseType(LicenseInfo.LICENSETYPES.network);
        } else if (Boolean.parseBoolean(ParamUtil.getString(actionRequest, LicenseInfo.LICENSETYPES.standalone.name()))) {
            licenseInfo.setLicenseType(LicenseInfo.LICENSETYPES.standalone);
        }

        if (Boolean.parseBoolean(ParamUtil.getString(actionRequest, LicenseInfo.LOCKTYPES.new_usb_dongle.name()))) {
            licenseInfo.setLockType(LicenseInfo.LOCKTYPES.new_usb_dongle);
        } else if (Boolean.parseBoolean(ParamUtil.getString(actionRequest, LicenseInfo.LOCKTYPES.existing_usb_dongle.name()))) {
            licenseInfo.setLockType(LicenseInfo.LOCKTYPES.existing_usb_dongle);
            licenseInfo.setDongleNumber(ParamUtil.getString(actionRequest, LicenseInfo.ATTRIBUTES.lock_address.name()));
        } else if (Boolean.parseBoolean(ParamUtil.getString(actionRequest, LicenseInfo.LOCKTYPES.mac_address.name()))) {
            licenseInfo.setLockType(LicenseInfo.LOCKTYPES.mac_address);
        }
        return licenseInfo;
    }

    /**
     * Get selection for subscriptions from request
     */
    private void setSubscriptionSelection(ActionRequest actionRequest, DownloadRequest downloadRequest) {

        final Set<Subscription> subscriptions = downloadRequest.getSubscriptions();
        for (Subscription subscription : subscriptions) {
            final String selected = ParamUtil.getString(actionRequest, "subscription-" + subscription.getId());
            downloadRequest.setSubscribe(subscription, Boolean.parseBoolean(selected));
        }
    }

    private BillingInfo getBillingInfo(ActionRequest actionRequest, User user) {

        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setEmail(user.getEmailAddress()); //Email does not get sent when billing info is empty

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

    private Map<String, String> getMinimumAttributes(ActionRequest actionRequest) {
        Map<String, String> attributes = new HashMap<>();
        final String remoteAddr = ((LiferayPortletRequest) actionRequest).getHttpServletRequest().getRemoteAddr();
        try {
            final Map<String, String> clientIpInfo = geoIpUtils.getClientIpInfo(remoteAddr);
            if (clientIpInfo.isEmpty()) return Collections.emptyMap();
            final Country country = CountryServiceUtil.getCountryByA2(geoIpUtils.getCountryIso2Code(clientIpInfo));
            if (country != null ) attributes.put(KeycloakUtils.ATTRIBUTES.org_country.name(), country.getName());
            return attributes;
        } catch (PortalException e) {
            LOG.warn("Error getting country info: " + e.getMessage());
        }
        return attributes;

    }

    private DownloadEmail prepareDownloadEmail(ActionRequest actionRequest, User user, DownloadRequest registrationRequest,
                                               ThemeDisplay themeDisplay, @SuppressWarnings("SameParameterValue") String action) {
        try {
            DownloadSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());

            if (!configuration.enableEmails()) return null;

            ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());
            DownloadEmail email = new DownloadEmail(user, resourceBundle, registrationRequest);
            email.setReplyToEmail(configuration.replyToEmail());
            email.setBCCToEmail(configuration.bccToEmail());
            email.setSendFromEmail(configuration.sendFromEmail());
            if ("download".equals(action)) {
                return email;
            }
            return null;

        } catch (Exception e) {
            SessionErrors.add(actionRequest, "send-email-failed", "Could not send " + action + " email for user [" + user.getEmailAddress() + "] : " + e.getMessage());
            LOG.error("Could not send " + action + " email for user [" + user.getEmailAddress() + "]", e);
            return null;
        }
    }

//    private boolean sendConfirmationEmail(ActionRequest actionRequest, User user, DownloadRequest registrationRequest,
//                                          ThemeDisplay themeDisplay, @SuppressWarnings("SameParameterValue") String action) {
//        try {
//
//            DownloadSiteConfiguration configuration = _configurationProvider
//                    .getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());
//
//            if (!configuration.enableEmails()) return true;
//
//            ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());
//            DownloadEmail email = new DownloadEmail(user, resourceBundle, registrationRequest);
//            email.setReplyToEmail(configuration.replyToEmail());
//            email.setBCCToEmail(configuration.bccToEmail());
//            email.setSendFromEmail(configuration.sendFromEmail());
//            if ("download".equals(action)) {
//                email.sendDownloadsEmail();
//                return true;
//            }
//            return false;
//
//        } catch (Exception e) {
//            SessionErrors.add(actionRequest, "send-email-failed", "Could not send " + action + " email for user [" + user.getEmailAddress() + "] : " + e.getMessage());
//            LOG.error("Could not send " + action + " email for user [" + user.getEmailAddress() + "]", e);
//            return false;
//        }
//    }

    //TODO: prepare for migration to sendinblue
    private EmailSubscriptionUtils subscriptionUtils;
    private KeycloakUtils keycloakUtils;

    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.MANDATORY
    )
    protected void setKeycloakUtils(KeycloakUtils keycloakUtils) {

        if (keycloakUtils.isActive()) {
            this.keycloakUtils = keycloakUtils;
            this.subscriptionUtils = (EmailSubscriptionUtils) keycloakUtils;
        }
    }

    private boolean isRegisterSomeoneElse(ActionRequest actionRequest) {
        return Boolean.parseBoolean(ParamUtil.getString(actionRequest, "registration_other"));
    }

    @Reference
    private AdminUtils adminUtils;

    @Reference
    private DsdParserUtils dsdParserUtils;

    @Reference
    private DownloadUtils downloadUtils;

    @Reference
    protected LicenseManagerUtils licenseManagerUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(SubmitDownloadActionCommand.class);

    private GeoIpUtils geoIpUtils;

    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.AT_LEAST_ONE
    )
    protected void setGeoIpUtils(GeoIpUtils geoIpUtils) {

        //todo: add check for preferred instance
        if (geoIpUtils.isActive()) {
            this.geoIpUtils = geoIpUtils;
        }
    }
}
