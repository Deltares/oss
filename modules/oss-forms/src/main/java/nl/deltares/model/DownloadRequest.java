package nl.deltares.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.model.impl.Subscription;

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
    final private Map<Subscription, Boolean> subscriptions = new HashMap<>();
    final private Map<Download, Map<String, Object>> shareInfo = new HashMap<>();
    private LicenseInfo licenseInfo;

    public DownloadRequest(ThemeDisplay themeDisplay) throws PortalException {
        siteUrl = PortalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay);
        baseUrl = themeDisplay.getCDNBaseURL();
        groupId = themeDisplay.getScopeGroupId();
    }

    public void setBannerUrl(String bannerUrl) {
        if (bannerUrl == null || bannerUrl.isEmpty()) return;
        this.bannerUrl = bannerUrl;
    }

    public long getGroupId() {
        return groupId;
    }

    public void addDownload(Download download) {
        downloads.add(download);
        final List<Subscription> subs = download.getSubscriptions();
        subs.forEach(sub -> {
            subscriptions.putIfAbsent(sub, false);
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

    public Set<Subscription> getSubscriptions() {
        return subscriptions.keySet();
    }

    public void setSubscribe(Subscription subscription, boolean subscribe) {
        this.subscriptions.put(subscription, subscribe);
    }

    public boolean isSubscribe(Subscription subscription) {
        final Boolean subscribe = subscriptions.get(subscription);
        return Boolean.TRUE.equals(subscribe);
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

    public boolean isBillingRequired(){
        for (Download download : downloads) {
            if (download.isBillingRequired()) return true;
        }
        return false;
    }

    public void setUserAttributes(Map<String, String> userAttributes) {
        this.userAttributes.putAll(userAttributes);
    }

    public HashMap<String, String> getUserAttributes() {
        return userAttributes;
    }

    public void registerShareInfo(Download download, Map<String, Object> shareInfo) {
        this.shareInfo.put(download, shareInfo);
     }

    public String getShareLink(Download download) {
        final Map<String, Object> info = shareInfo.get(download);
        if (info != null) return (String) info.get("url");
        return null;
    }

    public String getSharePassword(Download download){
        final Map<String, Object> info = shareInfo.get(download);
        if (info != null) return (String) info.get("password");
        return null;
    }

    public void setLicenseInfo(LicenseInfo licenseInfo) {
        this.licenseInfo = licenseInfo;
    }

    public LicenseInfo getLicenseInfo() {
        return licenseInfo;
    }
}
