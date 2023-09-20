package nl.deltares.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.model.impl.Subscription;
import nl.deltares.portal.model.impl.Terms;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DownloadRequest {

    private final List<Download> downloads = new ArrayList<>();
    private final String baseUrl;
    private final String siteUrl;
    private final HashMap<String, String> userAttributes = new HashMap<>();
    private final long groupId;
    private String bannerUrl = null;
    private BillingInfo billingInfo;
    final private List<SubscriptionSelection> subscriptionSelections = new ArrayList<>();
    final private Map<String, Map<String, String>> shareInfo = new HashMap<>();
    private LicenseInfo licenseInfo;

    public DownloadRequest(ThemeDisplay themeDisplay) throws PortalException {
        siteUrl = PortalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay, themeDisplay.getSiteDefaultLocale());
        baseUrl = themeDisplay.getCDNBaseURL();
        groupId = themeDisplay.getScopeGroupId();
    }

    public void setBannerUrl(String bannerUrl) {
        if (bannerUrl == null || bannerUrl.isBlank()) return;
        this.bannerUrl = bannerUrl;
    }

    public long getGroupId() {
        return groupId;
    }

    public void addDownload(Download download) {
        downloads.add(download);
        final List<Subscription> subs = download.getSubscriptions();

        subs.forEach(sub -> {
            final SubscriptionSelection subSelection = new SubscriptionSelection(sub.getId(), sub.getName());
            subSelection.setSelected(false);
            if (!subscriptionSelections.contains(subSelection)) subscriptionSelections.add(subSelection);
        });
    }

    public List<Download> getDownloads() {
        return Collections.unmodifiableList(downloads);
    }

    public void setBillingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public List<SubscriptionSelection> getSubscriptionSelections() {
        return subscriptionSelections;
    }

    public URL getBannerURL() throws MalformedURLException {
        if (bannerUrl == null) return null;
        return new URL(baseUrl + bannerUrl);
    }

    public String getSiteURL() {
        return siteUrl;
    }

    public boolean isUserInfoRequired(){
        for (Download download : downloads) {
            if (download.isUserInfoRequired()) return true;
        }
        return false;
    }

    public boolean isShowSubscription(){
        for (Download download : downloads) {
            if (download.isShowSubscription()) return true;
        }
        return false;
    }

    public void setUserAttributes(Map<String, String> userAttributes) {
        this.userAttributes.putAll(userAttributes);
    }

    public HashMap<String, String> getUserAttributes() {
        return userAttributes;
    }

    public void registerShareInfo(String articleId, Map<String, String> shareInfo) {
        this.shareInfo.put(articleId, shareInfo);
     }

    public String getShareLink(String articleId) {
        final Map<String, String> info = shareInfo.get(articleId);
        if (info != null) return info.get("url");
        return null;
    }

    public String getSharePassword(String articleId){
        final Map<String, String> info = shareInfo.get(articleId);
        if (info != null) return info.get("password");
        return null;
    }

    public String getLicenseDownloadLink(String articleId){
        final Map<String, String> info = shareInfo.get(articleId);
        if (info != null) return info.get("licUrl");
        return null;
    }

    public void setLicenseInfo(LicenseInfo licenseInfo) {
        this.licenseInfo = licenseInfo;
    }

    public LicenseInfo getLicenseInfo() {
        return licenseInfo;
    }

    public List<Terms> getTerms() {
        ArrayList<Terms> terms = new ArrayList<>();
        for (Download download : downloads) {
            final Terms downloadTerms = download.getTerms();
            if (downloadTerms != null && !terms.contains(downloadTerms)) terms.add(downloadTerms);
        }
        return terms;
    }
}
