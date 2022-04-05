package nl.deltares.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import nl.deltares.portal.model.impl.Download;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DownloadRequest {

    private final List<Download> downloads = new ArrayList<>();
    private final String baseUrl;
    private final String siteUrl;
    private final HashMap<String, String> userAttributes = new HashMap<>();
    private String bannerUrl = null;
    private BillingInfo billingInfo;
    private List<String> mailingIds = Collections.emptyList();

    private boolean subscribe;

    public DownloadRequest(ThemeDisplay themeDisplay) throws PortalException {
        siteUrl = PortalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay);
        baseUrl = themeDisplay.getCDNBaseURL();
    }

    public void setBannerUrl(String bannerUrl) {
        if (bannerUrl == null || bannerUrl.isEmpty()) return;
        this.bannerUrl = bannerUrl;
    }

    public void addDownload(Download download) {
        downloads.add(download);
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

    public void setSubscribableMailingIds(String mailingIds) {
        if (mailingIds == null || mailingIds.length() == 0) return;
        this.mailingIds = new ArrayList<>();
        String[] split = mailingIds.split(";");
        Arrays.stream(split).forEach(id -> {
            if (id.trim().length() > 0) this.mailingIds.add(id);
        });
    }

    public List<String> getSubscribableMailingIds() {
        return mailingIds;
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
