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
import nl.deltares.model.BillingInfo;
import nl.deltares.model.DownloadRequest;
import nl.deltares.emails.DownloadEmail;
import nl.deltares.portal.configuration.DownloadSiteConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
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

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        String redirect = ParamUtil.getString(actionRequest, "redirect");
        String action = ParamUtil.getString(actionRequest, "action");

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();

        DownloadRequest downloadRequest = getDownloadRequest(actionRequest, themeDisplay, action);
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
            if (downloadRequest.isUserInfoRequired()) {
                Map<String, String> userAttributes = getUserAttributes(actionRequest);
                downloadRequest.setUserAttributes(userAttributes);
                success = updateUserAttributes(actionRequest, user, userAttributes);

            }
            if (success && downloadRequest.isShowSubscription()) {
                success = updateSubscriptions(downloadRequest, user);
            }
            if (success && downloadRequest.isBillingRequired()) {
                //todo: process billing request.
            }
            if (success){
                success = createShareLinks(actionRequest, downloadRequest, user);
            }

            if (success){
                success = sendConfirmationEmail(actionRequest, user, downloadRequest, themeDisplay, "download");
            }
        } else {
            SessionErrors.add(actionRequest, "sendlink-failed", "Unsupported action " + action);
        }
        if (success){
            SessionMessages.add(actionRequest, "sendlink-success", new String[]{action, user.getEmailAddress()});
            if (!redirect.isEmpty()) {
                sendRedirect(actionRequest, actionResponse, redirect);
            }
        }

    }

    private boolean createShareLinks(ActionRequest actionRequest, DownloadRequest downloadRequest, User user) {

        boolean success = true;
        final String emailAddress = user.getEmailAddress();
        final List<Download> downloads = downloadRequest.getDownloads();
        for (Download download : downloads) {

            Map<String, Object> shareInfo = null;
            if (download.isBillingRequired()) {
                LOG.info(String.format("Creation of share link for user '%s' on file '%s' is pending payment.", emailAddress, download.getFileName()));
                shareInfo = Collections.emptyMap();
            } else {

                try {
                     shareInfo = downloadUtils.shareLinkExists(download.getFilePath(), emailAddress);
                    if (shareInfo.isEmpty()) {
                        shareInfo = downloadUtils.sendShareLink(download.getFilePath(), emailAddress);
                    } else {
                        shareInfo = downloadUtils.resendShareLink((Integer) shareInfo.get("id"));
                    }

                } catch (Exception e) {
                    SessionErrors.add(actionRequest, "sendlink-failed",
                            String.format("Failed to send link for file %s : %s ", download.getFileName(), e.getMessage()));
                    success = false;
                    continue;
                }
            }

            try {
                downloadUtils.registerDownload(user, Long.parseLong(download.getArticleId()), shareInfo, downloadRequest.getUserAttributes());
            } catch (PortalException e) {
                SessionErrors.add(actionRequest, "registerlink-failed",
                        String.format("Failed to register link for file %s : %s ", download.getFileName(), e.getMessage()));
            }


        }
        return success;
    }

    private boolean updateSubscriptions(DownloadRequest downloadRequest, User user) {
        List<String> subscribableMailingIds = downloadRequest.getSubscribableMailingIds();
        if (subscribableMailingIds != null) {
            subscribableMailingIds.forEach(mailingId -> {
                if (downloadRequest.isSubscribe()) {
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
        return true;
    }

    private DownloadRequest getDownloadRequest(ActionRequest actionRequest, ThemeDisplay themeDisplay, String action) {
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
            if (configuration.mailingIds().length() > 0) {
                downloadRequest.setSubscribableMailingIds(configuration.mailingIds());
            }
            downloadRequest.setSubscribe(ParamUtil.getBoolean(actionRequest, "subscribe_newsletter", false));
            for (String articleId : articleIds) {
                Download downloadArticle = (Download) dsdParserUtils.toDsdArticle(siteId, articleId);
                downloadRequest.addDownload(downloadArticle);
            }
            downloadRequest.setBannerUrl(configuration.bannerURL());
            return downloadRequest;

        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "send-email-failed", "Could not retrieve download for actionId: " + Arrays.toString(articleIds.toArray()));
            LOG.debug("Could not retrieve download for actionId: " + Arrays.toString(articleIds.toArray()));
        }
        return null;
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

    private boolean sendConfirmationEmail(ActionRequest actionRequest, User user, DownloadRequest registrationRequest,
                                          ThemeDisplay themeDisplay, @SuppressWarnings("SameParameterValue") String action) {
        try {

            DownloadSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());

            ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());
            DownloadEmail email = new DownloadEmail(user, resourceBundle, registrationRequest);
            email.setReplyToEmail(configuration.replyToEmail());
            email.setBCCToEmail(configuration.bccToEmail());
            email.setSendFromEmail(configuration.sendFromEmail());
            if ("download".equals(action)) {
                email.sendDownloadsEmail();
                return true;
            }
            return false;

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
    private DownloadUtils downloadUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(SubmitDownloadActionCommand.class);
}
