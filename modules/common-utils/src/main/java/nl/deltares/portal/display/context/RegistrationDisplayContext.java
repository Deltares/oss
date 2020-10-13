package nl.deltares.portal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.DinnerRegistration;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

public class RegistrationDisplayContext {

    public RegistrationDisplayContext(long classPk, ThemeDisplay themeDisplay) {
        this.themeDisplay = themeDisplay;
        JournalArticle registrationArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(classPk);
        if (registrationArticle != null) {
            setRegistration(registrationArticle);
        }
    }

    public RegistrationDisplayContext(String articleId, ThemeDisplay themeDisplay) {
        this.themeDisplay = themeDisplay;

        JournalArticle registrationArticle = JournalArticleLocalServiceUtil
                .fetchArticle(themeDisplay.getScopeGroupId(), articleId);

        setRegistration(registrationArticle);
    }

    public RegistrationDisplayContext(String articleId, ThemeDisplay themeDisplay, ConfigurationProvider configurationProvider) {
        this.themeDisplay = themeDisplay;
        this.configurationProvider = configurationProvider;

        JournalArticle registrationArticle = JournalArticleLocalServiceUtil
                .fetchArticle(themeDisplay.getScopeGroupId(), articleId);

        setRegistration(registrationArticle);
    }

    private void setRegistration(JournalArticle registrationArticle) {
        try {
            AbsDsdArticle parentInstance = AbsDsdArticle.getInstance(registrationArticle);
            if (parentInstance instanceof Registration) {
                registration = (Registration) parentInstance;
            }
        } catch (Exception e) {
            LOG.debug("Error getting Registration instance [" + registrationArticle.getArticleId() + "]", e);
        }
    }

    public double getPrice(){
        return registration.getPrice();
    }

    public String getCurrency(){
        return registration.getCurrency();
    }

    public String getSmallImageURL() {
        String url = "";
        Registration registration = getRegistration();
        if (registration != null) {
            url = registration.getSmallImageURL(themeDisplay);

            if ((url == null || url.isEmpty()) && registration instanceof DinnerRegistration){
                url = ((DinnerRegistration)registration).getRestaurant().getSmallImageURL(themeDisplay);
            }
        }
        return url;
    }

    public String getPresenterSmallImageURL() {
        String url = "";
        if (getSession() != null && getSession().getPresenter() != null) {
            url = getSession().getPresenter().getSmallImageURL(themeDisplay);
        }
        return url;
    }

    public String getPresenterName() {
        String name = "";
        if (getSession() != null && getSession().getPresenter() != null) {
            name = getSession().getPresenter().getTitle();
        }
        return name;
    }

    private Registration getRegistration() {
        return registration;
    }

    private SessionRegistration getSession() {
        SessionRegistration sessionRegistration = null;

        if (registration instanceof SessionRegistration) {
            sessionRegistration = (SessionRegistration) registration;
        }

        return sessionRegistration;
    }

    public String getStartDate(){
        if (getRegistration() != null) {
            return DateUtil.getDate(getRegistration().getStartTime(), "dd MMMM yyyy", themeDisplay.getLocale());
        }
        return "";

    }

    public String getEndDate(){
        if (getRegistration() != null) {
            return DateUtil.getDate(getRegistration().getEndTime(), "dd MMMM yyyy", themeDisplay.getLocale());
        }
        return "";

    }

    public boolean isPastEvent(){
        if (getRegistration() != null) {
            return getRegistration().getStartTime().getTime() < System.currentTimeMillis();
        }
        return true;

    }

    public String getStartTime() {
        if (getRegistration() != null) {
            return DateUtil.getDate(getRegistration().getStartTime(), "HH:mm", themeDisplay.getLocale());
        }
        return "";
    }

    public String getEndTime() {
        if (getRegistration() != null) {
            return DateUtil.getDate(getRegistration().getEndTime(), "HH:mm", themeDisplay.getLocale());
        }
        return "";
    }

    public String getSummary() {
        String summary = "";
        if (registration == null) return summary;
        try {
            AssetEntry assetEntry = AssetEntryLocalServiceUtil
                    .getEntry(JournalArticle.class.getName(), registration.getJournalArticle().getResourcePrimKey());
            summary = StringUtil.shorten(HtmlUtil.stripHtml(assetEntry.getSummary(themeDisplay.getLocale())), 150);
        } catch (Exception e) {
            LOG.debug("Could not get the AssetEntry for article [" + registration.getArticleId() + "]");
        }
        return summary;
    }

    public String getUnregisterURL(PortletRequest portletRequest){
        return getPortletRequest(portletRequest, "unregister");
    }

    public String getUpdateURL(PortletRequest portletRequest){
        return getPortletRequest(portletRequest, "update");
    }

    public String getRegisterURL(PortletRequest portletRequest) {
        return getPortletRequest(portletRequest, "register");
    }

    public String getPortletRequest(PortletRequest portletRequest, String action){

        if (configurationProvider != null) {
            long groupId = themeDisplay.getScopeGroupId();

            try {
                DSDSiteConfiguration urlsConfiguration = configurationProvider
                        .getGroupConfiguration(DSDSiteConfiguration.class, groupId);

                Layout registrationPage = LayoutLocalServiceUtil
                        .fetchLayoutByFriendlyURL(groupId, false, urlsConfiguration.registrationURL());

                if (registrationPage != null) {
                    PortletURL portletURL = PortletURLFactoryUtil
                            .create(portletRequest,
                                    "dsd_RegistrationFormPortlet",
                                    registrationPage.getPlid(),
                                    action.equals("unregister") ? PortletRequest.ACTION_PHASE : PortletRequest.RENDER_PHASE);
                    portletURL.setWindowState(LiferayWindowState.NORMAL);
                    portletURL.setPortletMode(LiferayPortletMode.VIEW);
                    portletURL.setParameter("javax.portlet.action", "/submit/register/form");
                    portletURL.setParameter("articleId", getRegistration().getArticleId());
                    portletURL.setParameter("action", action);
                    return portletURL.toString();
                }
            } catch (Exception e) {
                LOG.error("Error creating portlet url", e);
            }
        }
        return "";
    }

    private final ThemeDisplay themeDisplay;
    private ConfigurationProvider configurationProvider;
    private Registration registration;

    private static final Log LOG = LogFactoryUtil.getLog(RegistrationDisplayContext.class);
}
