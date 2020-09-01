package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class AbsDsdArticle implements DsdArticle {

    private static final HashMap<String, Event> cache = new HashMap<>();
    private static final long MAX_CACHE_TIME = TimeUnit.MINUTES.toMillis(5);
    private final JournalArticle article;
    final long instantiationTime;
    private Document document;


    public static boolean isDsdArticle(JournalArticle article) {
        try {
            getDsdStructureKey(parseStructureKey(article));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void validate() throws PortalException {
        //
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Generic.name();
    }

    @Override
    public boolean storeInParentSite() {
        return false;
    }

    @Override
    public long getResourceId() {
        if (article == null) return 0;
        return article.getResourcePrimKey();
    }

    @Override
    public String getArticleId() {
        if (article == null) return "0";
        return article.getArticleId();
    }

    @Override
    public String getTitle() {
        if (article == null) return "";
        return article.getTitle();
    }

    @Override
    public long getGroupId(){
        if (article == null) return 0;
        return article.getGroupId();
    }

    @Override
    public long getCompanyId(){
        if (article == null) return 0;
        return article.getCompanyId();
    }

    @Override
    public Document getDocument(){
        return document;
    }

    AbsDsdArticle(JournalArticle article) throws PortalException {
        this.article = article;
        this.instantiationTime = System.currentTimeMillis();
        init();
    }

    private void init() throws PortalException {
        this.document = XmlContentUtils.parseContent(article);
    }

    public static AbsDsdArticle getInstance(JournalArticle journalArticle) throws PortalException {

        String parseStructureKey = parseStructureKey(journalArticle);
        DSD_STRUCTURE_KEYS dsd_structure_key = getDsdStructureKey(parseStructureKey);

        AbsDsdArticle article;
        switch (dsd_structure_key){
            case Session:
                article = new SessionRegistration(journalArticle);
                break;
            case Bustransfer:
                article = new BusTransfer(journalArticle);
                break;
            case Dinner:
                article = new DinnerRegistration(journalArticle);
                break;
            case Location:
                article = new Location(journalArticle);
                break;
            case Eventlocation:
                article = new EventLocation(journalArticle);
                break;
            case Building:
                article = new Building(journalArticle);
                break;
            case Room:
                article = new Room(journalArticle);
                break;
            case Expert:
                article = new Expert(journalArticle);
                break;
            case Event:
                article = getCachedEventArticle(journalArticle.getArticleId());
                if (article != null) return article;
                article = new Event(journalArticle);
                cache.put(article.getArticleId(), (Event) article);
                break;
            case Busroute:
                article = new BusRoute(journalArticle);
                break;
            default:
                article = new GenericArticle(journalArticle, parseStructureKey);
        }

        return article;
    }

    private static Event getCachedEventArticle(String article) {
        Event dsdArticle = cache.get(article);
        if (dsdArticle != null &&
                (System.currentTimeMillis() - dsdArticle.instantiationTime) <  MAX_CACHE_TIME){
            return dsdArticle;
        }
        return null;
    }

    private static DSD_STRUCTURE_KEYS getDsdStructureKey(String structureKey) {
        DSD_STRUCTURE_KEYS dsd_structure_key;
        try {
            dsd_structure_key = DSD_STRUCTURE_KEYS.valueOf(structureKey);
        } catch (IllegalArgumentException e) {
            return DSD_STRUCTURE_KEYS.Generic;
        }
        return dsd_structure_key;
    }

    private static String parseStructureKey(JournalArticle dsdArticle) {
        String structureKey = dsdArticle.getDDMStructureKey();

        if (structureKey.matches("([A-Z])+-(\\d+\\.)(\\d+\\.)(\\d+)")) {
            structureKey = structureKey.substring(0, 1).toUpperCase()
                    + structureKey.substring(1, structureKey.lastIndexOf("-")).toLowerCase();
        }
        return structureKey;
    }

    public String getSmallImageURL(ThemeDisplay themeDisplay) {
        if (article == null) return "";
        String url = article.getSmallImageURL();
        if (Validator.isNull(url)) {
            url = article.getArticleImageURL(themeDisplay);
        }
        if (url == null) return "";
        return url;
    }

    public JournalArticle getJournalArticle(){
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbsDsdArticle that = (AbsDsdArticle) o;
        return article.getPrimaryKey() == that.article.getPrimaryKey();
    }

    @Override
    public int hashCode() {
        return Objects.hash(article.getPrimaryKey());
    }
}
