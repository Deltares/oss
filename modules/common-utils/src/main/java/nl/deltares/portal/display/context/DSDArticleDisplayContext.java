package nl.deltares.portal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.facet.FacetSelection;

import java.net.URI;

@SuppressWarnings("unused")
public class DSDArticleDisplayContext {

    public DSDArticleDisplayContext(DsdArticle article, ThemeDisplay themeDisplay, FacetSelection facetSelection) {
        this._themeDisplay = themeDisplay;
        this._article = article;
        this._facetSelection = facetSelection;
        ConfigurationProvider configurationProvider = ConfigurationProviderUtil.getConfigurationProvider();
        if (configurationProvider != null && _article != null) {
            try {
                _dsdSiteConfiguration = configurationProvider
                        .getGroupConfiguration(DSDSiteConfiguration.class, this._article.getGroupId());
            } catch (ConfigurationException e) {
                LOG.error("Error retrieving DsdSiteConfiguration: ", e);
            }
        }
    }

    public String getSmallImageURL() {
        String url = "";
        DsdArticle registration = getDsdArticle();
        if (registration != null) {
            url = registration.getSmallImageURL(_themeDisplay);
        }
        if (_facetSelection != null && url != null && !url.toLowerCase().startsWith("http")) {
            //Relative path
            String portalUrl = getPortalUrl();
            portalUrl += url;
            return portalUrl;
        } else {
            return url;
        }

    }

    public DsdArticle getDsdArticle() {
        return _article;
    }

    public String getTitle() {
        return _article.getTitle();
    }

    public String getSummary() {
        String summary = "";
        if (_article == null) return summary;
        try {
            AssetEntry assetEntry = AssetEntryLocalServiceUtil
                    .getEntry(JournalArticle.class.getName(), _article.getJournalArticle().getResourcePrimKey());
            summary = StringUtil.shorten(HtmlUtil.stripHtml(assetEntry.getSummary(_themeDisplay.getLocale())), 150);
        } catch (Exception e) {
            LOG.debug("Could not get the AssetEntry for article [" + _article.getArticleId() + "]");
        }
        return summary;
    }

    public String getContactEmail() {
        if (_dsdSiteConfiguration != null) {
            return _dsdSiteConfiguration.replyToEmail();
        }
        return "mydeltares@deltares.nl";
    }

    public String getCourseConditionsUrl() {
        if (_dsdSiteConfiguration != null) {
            return _dsdSiteConfiguration.conditionsURL();
        }
        return "";
    }

    public String getViewURL(DsdArticle article) {
        return getViewURL(article, true);
    }

    public String getViewURL(DsdArticle article, boolean redirect) {

        String redirectURL = _themeDisplay.getURLPortal() + _themeDisplay.getURLCurrent();

        String portalURL = getPortalUrl();
        portalURL += "/-/" + article.getJournalArticle().getUrlTitle();

        if (redirect) {
            portalURL += "?redirect=" + redirectURL;
        }
        return portalURL;
    }

    protected String getPortalUrl() {
        final long companyId;
        final long groupId;
        if (_facetSelection == null) {
            companyId = _article.getCompanyId();
            groupId = _article.getGroupId();
        } else {
            companyId = _facetSelection.getCompanyId();
            groupId = _facetSelection.getSiteGroupId();
        }
        Company company = CompanyLocalServiceUtil.fetchCompany(companyId);
        Group group = GroupLocalServiceUtil.fetchGroup(groupId);
        try {
            int port = _themeDisplay.getServerPort();
            String portalURL = company.getPortalURL(groupId);
            String friendlyURL = group.getFriendlyURL();
            String siteUrl = portalURL.concat("/web").concat(friendlyURL);
            URI uri = URI.create(siteUrl);
            if (uri.getPort() == port) {
                return siteUrl;
            }
            return siteUrl.replace(":" + uri.getPort(), ":" + port);
        } catch (PortalException e) {
            LOG.warn("Could not get the PortalURL for company [" + companyId + "]");
            return "";
        }

    }

    public String getConfiguredRegistrationFormId() {
        return _themeDisplay.getThemeSetting("registration-form-id");
    }

    public long getScopeUserId() {
        if (_facetSelection == null) {
            return _themeDisplay.getUserId();
        }
        return _facetSelection.getUserId();
    }

    public long getSiteGroupId() {
        if (_facetSelection == null) {
            return _themeDisplay.getSiteGroupId();
        }
        return _facetSelection.getSiteGroupId();
    }

    protected DSDSiteConfiguration _dsdSiteConfiguration = null;
    protected final ThemeDisplay _themeDisplay;
    protected final DsdArticle _article;
    protected final FacetSelection _facetSelection;

    private static final Log LOG = LogFactoryUtil.getLog(DSDArticleDisplayContext.class);

}
