package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component(
        immediate = true,
        service = DsdParserUtils.class
)
public class DsdParserUtilsImpl implements DsdParserUtils {

    private static final Log LOG = LogFactoryUtil.getLog(DsdParserUtilsImpl.class);
    private static final long MAX_CACHE_TIME = TimeUnit.MINUTES.toMillis(5);
    private static final HashMap<String, AbsDsdArticle> cache = new HashMap<>();

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;

    @Override
    public Event getEvent(long siteId, String articleId) throws PortalException {
        if ("0".equals(articleId)) {
            return null; //show all events
        }
        AbsDsdArticle cachedDsdArticle = getCachedDsdArticle(articleId);
        if (cachedDsdArticle != null) return (Event) cachedDsdArticle;

        JournalArticle article = dsdJournalArticleUtils.getJournalArticle(siteId, articleId);
        AbsDsdArticle eventArticle = toDsdArticle(article);
        if (!(eventArticle instanceof Event)) {
            throw new PortalException(String.format("Article %s is not a valid DSD Event", article.getTitle()));
        }
        Event event = (Event) eventArticle;

        cacheDsdArticle(event);
        return event;
    }

    @Override
    public List<Registration> getRegistrations(long siteId, Date startTime, Date endTime, Locale locale) throws PortalException {
        long companyId = PortalUtil.getDefaultCompanyId();
        List<JournalArticle> articles = dsdJournalArticleUtils.getRegistrationsForPeriod(companyId, siteId, startTime, endTime, locale);
        return articlesToDsd(articles);
    }

    @Override
    public List<Registration> getRegistrations(long siteId, String eventId, Locale locale) throws PortalException {
        long companyId = PortalUtil.getDefaultCompanyId();
        List<JournalArticle> articles = dsdJournalArticleUtils.getRegistrationsForEvent(companyId, siteId, String.valueOf(eventId), locale);
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
        return new RegistrationDisplayContext(articleId, themeDisplay, _configurationProvider, this);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static void cacheDsdArticle(AbsDsdArticle dsdArticle) {
        if (Boolean.parseBoolean(PropsUtil.get("nocache"))) return;
        cache.put(dsdArticle.getArticleId(), dsdArticle);
    }

    private static AbsDsdArticle getCachedDsdArticle(String articleId) {
        AbsDsdArticle dsdArticle = cache.get(articleId);
        if (dsdArticle != null &&
                (System.currentTimeMillis() - dsdArticle.instantiationTime) <  MAX_CACHE_TIME){
            return dsdArticle;
        }
        return null;
    }

    public AbsDsdArticle toDsdArticle(JournalArticle journalArticle) throws PortalException {

        String parseStructureKey = DsdParserUtils.parseStructureKey(journalArticle);
        DsdArticle.DSD_STRUCTURE_KEYS dsd_structure_key = DsdParserUtils.getDsdStructureKey(parseStructureKey);

        AbsDsdArticle article;
        switch (dsd_structure_key){
            case Session:
                article = new SessionRegistration(journalArticle, this);
                break;
            case Bustransfer:
                article = new BusTransfer(journalArticle, this);
                break;
            case Dinner:
                article = new DinnerRegistration(journalArticle, this);
                break;
            case Location:
                article = new Location(journalArticle, this);
                break;
            case Eventlocation:
                article = new EventLocation(journalArticle, this);
                break;
            case Building:
                article = new Building(journalArticle, this);
                break;
            case Room:
                article = new Room(journalArticle, this);
                break;
            case Expert:
                article = new Expert(journalArticle, this);
                break;
            case Event:
                article = new Event(journalArticle, this);
                break;
            case Busroute:
                article = new BusRoute(journalArticle ,this);
                break;
            default:
                article = new GenericArticle(journalArticle, this);
        }

        return article;
    }

}
