package nl.deltares.dsd.registration.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.util.Date;

public abstract class AbstractRegistration {

    private int capacity = -1;

    public abstract boolean hasParentRegistration();

    public abstract long getParentRegistrationId();

    public boolean isOpen(){
        return true;
    }

    public long getArticleId(){
        return Long.parseLong(article.getArticleId());
    }

    public long getGroupId(){
        return article.getGroupId();
    }

    public int getCapacity() throws PortalException {
        if (this.capacity != -1) return this.capacity;
        Object capacity = XmlContentParserUtils.getNodeValue(content, "capacity");
        this.capacity =  capacity == null ? Integer.MAX_VALUE : (int) capacity;
        return this.capacity;
    }

    public Date getStartTime(){
        return new Date();
    }

    public Date getEndTime(){
        return new Date();
    }

    private enum REGISTRATION_STRUCTURE_KEY {Bustransfer, Dinner, Session}

    private final JournalArticle article;
    private final Document content;

    AbstractRegistration(JournalArticle article) throws PortalException {
        this.article = article;
        try {
            this.content = XmlContentParserUtils.parseContent(article.getContent());
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", article.getArticleId(), e.getMessage()), e);
        }
    }

    private static REGISTRATION_STRUCTURE_KEY getStructureType(String ddmStructureKey) throws PortalException {

        String structureKey;
        if (ddmStructureKey.matches("([A-Z])+-(\\d+\\.)(\\d+\\.)(\\d+)")) {
            structureKey = ddmStructureKey.substring(0, 1).toUpperCase()
                    + ddmStructureKey.substring(1, ddmStructureKey.lastIndexOf("-")).toLowerCase();
        } else {
            structureKey = ddmStructureKey;
        }

        try {
            return REGISTRATION_STRUCTURE_KEY.valueOf(structureKey);
        } catch (IllegalArgumentException e) {
            throw new PortalException(String.format("Unsupported structure type %s registrations!", structureKey));
        }

    }

    public static AbstractRegistration getInstance(JournalArticle article) throws PortalException {

        REGISTRATION_STRUCTURE_KEY structureType = getStructureType(article.getDDMStructureKey());
        switch (structureType){
            case Dinner: return new DinnerRegistration(article);
            case Session: return new SessionRegistration(article);
            case Bustransfer: return new BusTransferRegistration(article);
            default:
                throw new UnsupportedOperationException("Unsupported structure type " + article.getDDMStructureKey());
        }
    }
}
