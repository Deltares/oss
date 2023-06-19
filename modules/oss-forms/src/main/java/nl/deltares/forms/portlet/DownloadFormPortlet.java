package nl.deltares.forms.portlet;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.configuration.DownloadSiteConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.model.impl.Terms;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.*;

import static nl.deltares.portal.utils.LocalizationUtils.getLocalizedValue;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/download.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=Download Form",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/download/download.jsp",
                "javax.portlet.name=" + OssConstants.DOWNLOADFORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=user"
        },
        service = Portlet.class
)
public class DownloadFormPortlet extends MVCPortlet {

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
    private KeycloakUtils keycloakUtils;

    @Reference
    private DsdParserUtils dsdParserUtils;

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(DownloadFormPortlet.class);

    @Override
    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        if (!user.isDefaultUser()) {
            try {
                final Map<String, String> userAttributes;
                if (keycloakUtils.isActive()) {
                     userAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
                    request.setAttribute("attributes", userAttributes);
                } else {
                    userAttributes = new HashMap<>();
                }
                //translate org vat code
                final String org_vat = userAttributes.get(KeycloakUtils.ATTRIBUTES.org_vat.name());
                if (org_vat != null) userAttributes.put("billing_vat", org_vat);
            } catch (Exception e) {
                SessionErrors.add(request, "retrieve-attributes-failed", "Error reading user attributes: " + e.getMessage());
                request.setAttribute("attributes", new HashMap<>());
            }
            try {
                final String language = themeDisplay.getLocale().getLanguage();
                DownloadSiteConfiguration dsdConfig = _configurationProvider.getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());
                request.setAttribute("privacyURL", getLocalizedValue(dsdConfig.privacyURL(), language));
                request.setAttribute("contactURL", getLocalizedValue(dsdConfig.contactURL(), language));
            } catch (Exception e) {
                LOG.warn("Error getting configuration: " + e.getMessage());
            }
        }

        String action = ParamUtil.getString(request, "action");
        List<String> downloadIds = new ArrayList<>();
        String ids;

        if ("download".equals(action)) {
            ids = ParamUtil.getString(request, "ids");
            LOG.info(Arrays.toString(ids.split(",", -1)));
            downloadIds = new ArrayList<>(Arrays.asList(ids.split(",", -1)));
        } else {
            ids = ParamUtil.getString(request, "articleId");
            downloadIds.add(ids);
        }

        Optional<DDMTemplate> ddmTemplateOptional = _ddmStructureUtil
                .getDDMTemplateByName(themeDisplay.getScopeGroupId(), "DOWNLOAD-FORM", themeDisplay.getLocale());

        ddmTemplateOptional.ifPresent(ddmTemplate ->
                request.setAttribute("ddmTemplateKey", ddmTemplate.getTemplateKey()));

        request.setAttribute("dsdParserUtils", dsdParserUtils);
        if (downloadIds.size() > 0) {
            final List<Download> downloads = toDownloads(themeDisplay.getScopeGroupId(), downloadIds);
            request.setAttribute("downloads", downloads);
            request.setAttribute("subscriptionSelections", getSubscriptionSelection(user.getEmailAddress(), downloads));
            request.setAttribute("terms", getTerms(downloads));
            final boolean[] requiredTypes = getRequiredTypes(downloads);
            request.setAttribute("showLockTypes", requiredTypes[0]);
            request.setAttribute("showLicenseTypes", requiredTypes[1]);
        }
        super.render(request, response);
    }

    private boolean[] getRequiredTypes(List<Download> downloads) {
        final boolean[] requiredTypes = {false, false};
        downloads.forEach(d -> {
            if (d.isLockTypeRequired()) requiredTypes[0] = true;
            if (d.isLicenseTypeRequired()) requiredTypes[1] = true;
        });
        return requiredTypes;
    }

    private List<Terms> getTerms(List<Download> downloads) {

        final ArrayList<Terms> terms = new ArrayList<>();
        for (Download download : downloads) {
            final Terms downloadTerms = download.getTerms();
            if (downloadTerms != null && !terms.contains(downloadTerms)) {
                terms.add(download.getTerms());
            }
        }
        return terms;
    }

    private List<Download> toDownloads(long scopeGroupId, List<String> downloadIds) {
        final ArrayList<Download> downloads = new ArrayList<>();

        downloadIds.forEach(downloadId -> {
            try {
                downloads.add((Download) dsdParserUtils.toDsdArticle(scopeGroupId, downloadId));
            } catch (PortalException e) {
                LOG.warn(String.format("Error getting download %s: %s", downloadId, e.getMessage()));
            }
        });
        return downloads;
    }

    private List<SubscriptionSelection> getSubscriptionSelection(String email, List<Download> downloads) {

        List<SubscriptionSelection> subscriptionSelection = new ArrayList<>();
        try {
            for (Download download : downloads) {

                final List<nl.deltares.portal.model.impl.Subscription> subscriptions = download.getSubscriptions();
                subscriptions.forEach(subscription -> {
                    final SubscriptionSelection displaySubscription = new SubscriptionSelection(subscription.getId(), subscription.getName());
                    if (subscriptionSelection.contains(displaySubscription)) return; //do not check multiple times
                    try {
                        displaySubscription.setSelected(subscriptionUtils.isSubscribed(email, subscription.getId()));
                    } catch (Exception e) {
                        displaySubscription.setSelected(false);
                    }
                    subscriptionSelection.add(displaySubscription);
                });
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return subscriptionSelection;
    }
}