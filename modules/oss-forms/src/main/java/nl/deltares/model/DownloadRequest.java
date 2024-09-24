package nl.deltares.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.model.impl.Subscription;
import nl.deltares.portal.model.impl.Terms;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;

import java.util.*;

public class DownloadRequest {

    private final List<Download> downloads = new ArrayList<>();
    private final String siteUrl;
    private final long groupId;
    private Map<String, String> registrationParameters = new HashMap<>();
    final private List<SubscriptionSelection> subscriptionSelections = new ArrayList<>();
    final private Map<String, Map<String, String>> shareInfo = new HashMap<>();
    private String downloadServerCode;

    public DownloadRequest(ThemeDisplay themeDisplay) throws PortalException {
        siteUrl = PortalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay, themeDisplay.getSiteDefaultLocale());
        groupId = themeDisplay.getScopeGroupId();
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

    @SuppressWarnings("UnusedReturnValue")
    public String setRequestParameter(String key, String value){
        return registrationParameters.put(key,value);
    }

    public String getRequestParameterOrDefault(String key, String defaultValue){
        return this.registrationParameters.getOrDefault(key, defaultValue);
    }
    public String getRequestParameter(String key){
        return getRequestParameterOrDefault(key, null);
    }

    public Map<String, String> getRequestParameters(){
        return Collections.unmodifiableMap(registrationParameters);
    }

    public List<SubscriptionSelection> getSubscriptionSelections() {
        return subscriptionSelections;
    }

    @SuppressWarnings({"unused", "Used by Freemarker"})
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

    public List<Terms> getTerms() {
        ArrayList<Terms> terms = new ArrayList<>();
        for (Download download : downloads) {
            final Terms downloadTerms = download.getTerms();
            if (downloadTerms != null && !terms.contains(downloadTerms)) terms.add(downloadTerms);
        }
        return terms;
    }

    public String getDownloadServerCode() {
        return downloadServerCode;
    }

    public void setDownloadServerCode(String downloadServerCode) {
        this.downloadServerCode = downloadServerCode;
    }
}
