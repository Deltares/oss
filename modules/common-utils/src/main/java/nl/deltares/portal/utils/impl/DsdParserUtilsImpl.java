package nl.deltares.portal.utils.impl;

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
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.LayoutUtils;
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
    LayoutUtils layoutUtils;

    private Map<Long, Map<String, String>> structureKeyMap = new HashMap<>();

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

    @Override
    public void clearConfigCache(Long groupId, String configKey) {
        structureKeyMap.clear();
    }

    public AbsDsdArticle toDsdArticle(JournalArticle journalArticle, Locale locale) throws  PortalException{

        if (locale == null){
            locale = LocaleUtil.getDefault();
        }

        String parseStructureKey = getParseStructureKey(journalArticle);
        DsdArticle.DSD_STRUCTURE_KEYS dsd_structure_key = DsdParserUtils.getDsdStructureKey(parseStructureKey);

        AbsDsdArticle article;
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
        Map<String, String> groupMap = structureKeyMap.get(groupId);
        if (groupMap == null) {
            //Now structure keys are LONG identifiers and need to be mapped.
            StructureKeyMapConfiguration configuration = _configurationProvider.getGroupConfiguration(
                    StructureKeyMapConfiguration.class, groupId);
            try {
                groupMap = JsonContentUtils.parseJsonToMap(configuration.structureKeyMap());
            } catch (JSONException e) {
                LOG.warn("Error parsing the configured StructureKey map: " + e.getMessage());
                groupMap = Collections.emptyMap();
            }
            structureKeyMap.put(groupId, groupMap);
        }
        return groupMap.get(String.valueOf(ddmStructure.getStructureId()));
    }

}
