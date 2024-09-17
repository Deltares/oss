package nl.deltares.model;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import nl.deltares.portal.model.impl.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class RegistrationRequest {

    private final Map<String, List<Registration>> childRegistrations = new HashMap<>();
    private final List<Registration> registrations = new ArrayList<>();
    private final String baseUrl;
    private final String siteUrl;
    private BillingInfo billingInfo;
    private BadgeInfo badgeInfo;
    private Event event;
    private boolean enableBusInfo;
    private String busTransferUrl;
    private Map<String, String> typeTranslations = new HashMap<>();
    private String remarks;
    private List<String> mailingIds = Collections.emptyList();
    private boolean subscribe;
    private AccountEntry accountEntry;
    private OrganizationInfo organizationInfo;

    public RegistrationRequest(ThemeDisplay themeDisplay) throws PortalException {
        siteUrl = PortalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay, themeDisplay.getLocale());
        baseUrl = themeDisplay.getCDNBaseURL();
    }

    public void addRegistration(Registration registration){
        registrations.add(registration);
    }

    public void addChildRegistration(Registration parent, Registration child){

        ArrayList<Registration> newList = new ArrayList<>();
        List<Registration> children = childRegistrations.putIfAbsent(parent.getArticleId(), newList);
        if (children == null) {
            newList.add(child);
        } else {
            children.add(child);
        }
    }

    public List<Registration> getChildRegistrations(Registration parent){
        return Collections.unmodifiableList(childRegistrations.getOrDefault(parent.getArticleId(), new ArrayList<>()));
    }

    public List<Registration> getRegistrations() {
        return Collections.unmodifiableList(registrations);
    }

    public String getLocation(Registration registration) {
        if (registration instanceof SessionRegistration){
            Room room = ((SessionRegistration) registration).getRoom();
            return room.getTitle();
        } else if (registration instanceof DinnerRegistration){
            Location location = ((DinnerRegistration) registration).getRestaurant();
            return location.getTitle();
        }
        return null;
    }

    public void setBillingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public void setBadgeInfo(BadgeInfo badgeInfo) {
        this.badgeInfo = badgeInfo;
    }

    public void setRemarks(String remarks){
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public BadgeInfo getBadgeInfo() {
        return badgeInfo;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public String getTitle() {
        return event != null ? event.getTitle() : "";
    }

    public String getSiteURL() {
        return siteUrl;
    }

    public URL getBannerURL() throws MalformedURLException {
        final String emailBannerURL = event.getEmailBannerURL();
        if (emailBannerURL == null || emailBannerURL.isBlank()) return null;
        return new URL(baseUrl + emailBannerURL);
    }

    public URL getFooterURL() throws MalformedURLException {
        final String emailFooterURL = event.getEmailFooterURL();
        if (emailFooterURL == null || emailFooterURL.isBlank()) return  null;
        return new URL(baseUrl + emailFooterURL);
    }

    public void setBusInfo(boolean enableBusInfo) {
        this.enableBusInfo = enableBusInfo;
    }

    public boolean isBusInfo() {
        return enableBusInfo;
    }

    public void setBusTransferUrl(String pageUrl) {
        this.busTransferUrl = pageUrl;
    }

    public String getBusTransferUrl() {
        if (busTransferUrl == null) return null;
        return StringBundler.concat(siteUrl, busTransferUrl);
    }

    public String translateRegistrationType(String type){
        return typeTranslations.getOrDefault(type, type);
    }

    public void setTypeTranslations(Map<String, String> typeTranslations) {
        this.typeTranslations = typeTranslations;
    }

    public void setSubscribableMailingIds(String mailingIds) {
        if (mailingIds == null || mailingIds.isEmpty()) return;
        this.mailingIds = new ArrayList<>();
        String[] split = mailingIds.split(";");
        Arrays.stream(split).forEach(id -> {if (!id.trim().isEmpty()) this.mailingIds.add(id); });
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

    public void setAccountEntry(AccountEntry accountEntry) {
        this.accountEntry = accountEntry;
    }

    public AccountEntry getAccountEntry() {
        return accountEntry;
    }

    public void setOrganizationInfo(OrganizationInfo organizationInfo) {
        this.organizationInfo = organizationInfo;
    }

    public OrganizationInfo getOrganizationInfo() {
        return organizationInfo;
    }
}
