package nl.deltares.portal.utils.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import nl.deltares.portal.configuration.StructureKeyMapConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;

@Component(
        immediate = true,
        service = DsdParserUtils.class
)
public class DsdParserUtilsImpl implements DsdParserUtils{

    private static final Log LOG = LogFactoryUtil.getLog(DsdParserUtilsImpl.class);

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;

    @Reference
    AssetLinkLocalService assetLinkLocalServiceUtil;

    @Reference
    AssetEntryLocalService assetEntryLocalServiceUtil;

    @Reference
    LayoutUtils layoutUtils;

    @Reference
    DeltaresCacheUtils cache;


    @Override
    public Event getEvent(long siteId, String articleId, Locale locale) throws PortalException {
        if ("0".equals(articleId)) {
            return null; //show all events
        }
        JournalArticle article = dsdJournalArticleUtils.getJournalArticle(siteId, articleId);
        if (article == null) return null;
        AbsDsdArticle eventArticle = toDsdArticle(article);
        if (!(eventArticle instanceof Event)) {
            throw new PortalException(String.format("Article %s is not a valid DSD Event", article.getTitle()));
        }
        return (Event) eventArticle;
    }

    @Override
    public List<String> getRelatedArticles(long siteId, String articleId) throws PortalException {

        final JournalArticle article = dsdJournalArticleUtils.getJournalArticle(siteId, articleId);

        final List<String> relatedArticles = new ArrayList<>();

        final long primaryKey = article.getResourcePrimKey();
        final AssetEntry assetEntry = assetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), primaryKey);
        if (assetEntry == null) return Collections.emptyList();
        final long entryId = assetEntry.getEntryId();
        final List<AssetLink> relatedAssets = assetLinkLocalServiceUtil.getDirectLinks(entryId);
        for (AssetLink relatedAsset : relatedAssets) {
            final long relatedEntryId = relatedAsset.getEntryId2();
            final AssetEntry relatedEntry = assetEntryLocalServiceUtil.fetchEntry(relatedEntryId);
            if (relatedEntry == null) continue;
            final long relatedClassPK = relatedEntry.getClassPK();
            final JournalArticle journalArticle = dsdJournalArticleUtils.getLatestArticle(relatedClassPK);
            if (journalArticle == null) continue;
            relatedArticles.add(journalArticle.getArticleId());
        }
        return relatedArticles;
    }

    @Override
    public Event getEvent(long siteId, String articleId) throws PortalException {

        String defaultLanguageId = GroupLocalServiceUtil.getGroup(siteId).getDefaultLanguageId();
        Locale locale = LocaleUtil.fromLanguageId(defaultLanguageId);
        return getEvent(siteId, articleId, locale);
    }

    @Override
    public List<Registration> getRegistrations(long companyId, long siteId, Date startTime, Date endTime,
                                               String[] structureKeys, String dateFieldName, Locale locale) throws PortalException {

        List<JournalArticle> articles = dsdJournalArticleUtils.getRegistrationsForPeriod(companyId, siteId, startTime, endTime,
                structureKeys, dateFieldName, locale);
        return articlesToDsd(articles);
    }

    @Override
    public List<Registration> getRegistrations(long companyId, long siteId, String eventId, String[] registrationStructureKeys, Locale locale) throws PortalException {
        List<JournalArticle> articles = dsdJournalArticleUtils.getRegistrationsForEvent(companyId, siteId, eventId, registrationStructureKeys, locale);
        return articlesToDsd(articles);
    }

    private List<Registration> articlesToDsd(List<JournalArticle> articles) {
        List<Registration> registrations = new ArrayList<>();
        articles.forEach(registrationArticle -> {
            try {
                registrations.add(getRegistration(registrationArticle));
            } catch (PortalException e) {
                LOG.warn(String.format("Error getting registration for %s: %s", registrationArticle.getTitle(), e.getMessage()));
            }
        });
        return registrations;
    }

    @Override
    public Registration getRegistration(long siteId, String articleId) throws PortalException {
        JournalArticle article =  dsdJournalArticleUtils.getJournalArticle(siteId, articleId);
        return getRegistration(article);
    }

    @Override
    public Registration getRegistration(JournalArticle article) throws PortalException {
        AbsDsdArticle dsdArticle = toDsdArticle(article);
        if (!(dsdArticle instanceof Registration)) {
            throw new PortalException(String.format("Article %s is not a valid DSD Registration", article.getTitle()));
        }
        return (Registration) dsdArticle;
    }

    @Override
    public Location getLocation(JournalArticle article) throws PortalException {
        AbsDsdArticle dsdArticle = toDsdArticle(article);
        if (!(dsdArticle instanceof Location)) {
            throw new PortalException(String.format("Article %s is not a valid DSD Location", article.getTitle()));
        }
        return (Location) dsdArticle;
    }


    @Override
    public Expert getExpert(JournalArticle article) throws PortalException {

        AbsDsdArticle dsdArticle = toDsdArticle(article);
        if (!(dsdArticle instanceof Expert)) {
            throw new PortalException(String.format("Article %s is not a valid DSD Expert", article.getTitle()));
        }
        return (Expert) dsdArticle;
    }

    @Override
    public RegistrationDisplayContext getDisplayContextInstance(String articleId, ThemeDisplay themeDisplay) {

        String articleAttrName = "equinox.http.deltares-search-webjavax.portlet.p." + themeDisplay.getPortletDisplay().getId() + "_LAYOUT_" + themeDisplay.getLayout().getPlid() + "?program-list-registration-articleId";
        String dayAttrName = "equinox.http.deltares-search-webjavax.portlet.p." + themeDisplay.getPortletDisplay().getId() + "_LAYOUT_" + themeDisplay.getLayout().getPlid() + "?program-list-registration-day";
        final Object sessionArticleId = themeDisplay.getRequest().getSession().getAttribute(articleAttrName);
        final Object day = themeDisplay.getRequest().getSession().getAttribute(dayAttrName);
        int dayIndex = 0;
        if (articleId.equals(sessionArticleId) && day instanceof Integer){
            dayIndex = (Integer) day;
        }
        return new RegistrationDisplayContext(articleId, dayIndex, themeDisplay, _configurationProvider, this);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    @Override
    public AbsDsdArticle toDsdArticle(long siteId, String articleId) throws PortalException {
        JournalArticle article =  dsdJournalArticleUtils.getJournalArticle(siteId, articleId);
        if (article == null) return null;
        return  toDsdArticle(article);
    }

    public AbsDsdArticle toDsdArticle(JournalArticle journalArticle) throws PortalException {

        String defaultLanguageId = GroupLocalServiceUtil.getGroup(journalArticle.getGroupId()).getDefaultLanguageId();
        Locale locale = LocaleUtil.fromLanguageId(defaultLanguageId);
        return toDsdArticle(journalArticle, locale);
    }

    public AbsDsdArticle toDsdArticle(JournalArticle journalArticle, Locale locale) throws  PortalException{

        AbsDsdArticle article;
        if (cache != null){
            article = cache.findArticle(journalArticle);
            if (article != null) return article;
        }
        if (locale == null){
            locale = LocaleUtil.getDefault();
        }

        String parseStructureKey = getParseStructureKey(journalArticle);
        DsdArticle.DSD_STRUCTURE_KEYS dsd_structure_key;
        try {
            dsd_structure_key = DsdArticle.DSD_STRUCTURE_KEYS.valueOf(parseStructureKey);
        } catch (Exception e){
            dsd_structure_key = DsdArticle.DSD_STRUCTURE_KEYS.Generic;
            LOG.warn(String.format("Article %s has invalid structure key %s", journalArticle.getTitle(), parseStructureKey));
        }

        switch (dsd_structure_key){
            case Session:
                article = new SessionRegistration(journalArticle, this, locale);
                break;
            case Bustransfer:
                article = new BusTransfer(journalArticle, this, locale);
                break;
            case Dinner:
                article = new DinnerRegistration(journalArticle, this, locale);
                break;
            case Licensefile:
                article = new LicenseFile(journalArticle, this, locale);
                break;
            case Location:
                article = new Location(journalArticle, this, locale);
                break;
            case Eventlocation:
                article = new EventLocation(journalArticle, this, locale);
                break;
            case Building:
                article = new Building(journalArticle, this, locale);
                break;
            case Room:
                article = new Room(journalArticle, this, locale);
                break;
            case Expert:
                article = new Expert(journalArticle, this, locale);
                break;
            case Event:
                article = new Event(journalArticle, this, locale);
                break;
            case Presentation:
                article = new Presentation(journalArticle, this, locale);
                return article;
            case Download:
                article = new Download(journalArticle, this, dsdJournalArticleUtils, layoutUtils, locale);
                return article;
            case Downloadgroup:
                article = new DownloadGroup(journalArticle, this, layoutUtils, locale);
                return article;
            case Subscription:
                article = new Subscription(journalArticle, this, locale);
                return article;
            case Terms:
                article = new Terms(journalArticle, this, locale);
                return article;
            default:
                article = new GenericArticle(journalArticle, this, locale);
        }
        if (cache != null){
            cache.putArticle(journalArticle, article);
        }
        return article;
    }

    private String getParseStructureKey(JournalArticle journalArticle) throws ConfigurationException {
        final DDMStructure ddmStructure = journalArticle.getDDMStructure();
        final String ddmStructureKey = ddmStructure.getStructureKey();
        if (ddmStructureKey.matches(DsdParserUtils.STRUCTURE_KEY_REGEX)) {
            //This is the old way structures where named in the system
            return DsdParserUtils.parseStructureKey(journalArticle);
        }
        final long groupId = ddmStructure.getGroupId();
        final String pid = OssConstants.STRUCTURE_KEY_MAP_CONFIGURATIONS_PID.concat(String.valueOf(groupId));
        Map<String, Object> groupMap = cache.findPortletConfig(pid);

        if (groupMap == null || groupMap.isEmpty()) {
            //Now structure keys are LONG identifiers and need to be mapped.
            StructureKeyMapConfiguration configuration = _configurationProvider.getGroupConfiguration(
                    StructureKeyMapConfiguration.class, groupId);
            try {
                groupMap = new HashMap<>(JsonContentUtils.parseJsonToMap(configuration.structureKeyMap()));
            } catch (JSONException e) {
                LOG.warn("Error parsing the configured StructureKey map: " + e.getMessage());
                groupMap = Collections.emptyMap();
            }
            cache.putPortletConfig(pid, groupMap);
        }
        return (String) groupMap.get(String.valueOf(ddmStructure.getStructureId()));
    }

}
