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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import java.text.SimpleDateFormat;

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

    public String getSmallImageURL() {
        String url = "";
        if (getRegistration() != null) {
            url = getRegistration().getSmallImageURL(themeDisplay);
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

    public String getStartTime() {
        String time = "";
        if (getRegistration() != null) {
            time = timeFormat.format(getRegistration().getStartTime());
        }
        return time;
    }

    public String getEndTime() {
        String time = "";
        if (getRegistration() != null) {
            time = timeFormat.format(getRegistration().getEndTime());
        }
        return time;
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

    public String getUnregisterURL(PortletRequest portletRequest) {
        return "";
    }

    public String getRegisterURL(PortletRequest portletRequest) {
        String url = "";

        long groupId = themeDisplay.getScopeGroupId();

        if (configurationProvider != null) {
            try {
                DSDSiteConfiguration urlsConfiguration = configurationProvider
                        .getGroupConfiguration(DSDSiteConfiguration.class, groupId);

                Layout registrationPage = LayoutLocalServiceUtil
                        .fetchLayoutByFriendlyURL(groupId, false, urlsConfiguration.registrationURL());

                if (registrationPage != null) {
                    PortletURL portletURL = PortletURLFactoryUtil
                            .create(portletRequest, "dsd_RegistrationFormPortlet", registrationPage.getPlid(), PortletRequest.RENDER_PHASE);
                    portletURL.setWindowState(LiferayWindowState.NORMAL);
                    portletURL.setPortletMode(LiferayPortletMode.VIEW);
                    portletURL.setParameter("articleId", getRegistration().getArticleId());

                    url = portletURL.toString();
                }
            } catch (Exception e) {
                LOG.debug("Error creating portlet url", e);
            }
        }

        return url;
    }

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
    private final ThemeDisplay themeDisplay;
    private ConfigurationProvider configurationProvider;
    private Registration registration;

    private static final Log LOG = LogFactoryUtil.getLog(RegistrationDisplayContext.class);
}
