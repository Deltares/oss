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
    final private List<Subscription> subscriptions = new ArrayList<>();

    private boolean subscribe;

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
            if (!subscriptions.contains(sub)){
                subscriptions.add(sub);
            }
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

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public boolean isSubscribe() {
        return subscribe;
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
}
