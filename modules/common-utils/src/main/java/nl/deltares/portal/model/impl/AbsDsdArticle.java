package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public abstract class AbsDsdArticle implements DsdArticle {

    private final JournalArticle article;
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
    public String getTitle() {
        return article.getTitle();
    }

    @Override
    public long getArticleId(){
        String articleId = article.getArticleId();
        if (articleId.startsWith("/")){
            return Long.parseLong(articleId.substring(1));
        }
        return Long.parseLong(articleId);
    }

    @Override
    public long getGroupId(){
        return article.getGroupId();
    }

    @Override
    public long getCompanyId(){
        return article.getCompanyId();
    }

    @Override
    public Document getDocument(){
        return document;
    }

    AbsDsdArticle(JournalArticle article) throws PortalException {
        this.article = article;
        init();
    }

    private void init() throws PortalException {
        try {
            this.document = XmlContentParserUtils.parseContent(article);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", article.getArticleId(), e.getMessage()), e);
        }
    }


    public static AbsDsdArticle getInstance(JournalArticle journalArticle) throws PortalException {

        String parseStructureKey = parseStructureKey(journalArticle);
        DSD_STRUCTURE_KEYS dsd_structure_key = getDsdStructureKey(parseStructureKey);

        switch (dsd_structure_key){
            case Session: return new SessionRegistration(journalArticle);
            case Bustransfer: return new BusTransferRegistration(journalArticle);
            case Dinner: return new DinnerRegistration(journalArticle);
            case Location: return new Location(journalArticle);
            case Eventlocation: return new EventLocation(journalArticle);
            case Building: return new Building(journalArticle);
            case Room: return new Room(journalArticle);
            case Expert: return new Expert(journalArticle);
            default:
                return new GenericArticle(journalArticle, parseStructureKey);
        }

    }

    private static DSD_STRUCTURE_KEYS getDsdStructureKey(String structureKey) {
        DSD_STRUCTURE_KEYS dsd_structure_key;
        try {
            dsd_structure_key = DSD_STRUCTURE_KEYS.valueOf(structureKey);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException("Unsupported DSD structure: " + structureKey);
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
}
