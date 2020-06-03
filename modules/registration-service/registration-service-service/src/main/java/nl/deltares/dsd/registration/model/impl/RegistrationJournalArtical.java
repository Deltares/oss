package nl.deltares.dsd.registration.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.dsd.registration.exception.NoSuchRegistrationException;
import nl.deltares.dsd.registration.service.impl.JournalArticleUtils;
import org.w3c.dom.Document;

import java.util.Date;

public class RegistrationJournalArtical {

    public boolean isOpen(){
        return true;
    }

    public long getArticleId(){
        return Long.parseLong(article.getArticleId());
    }

    public long getGroupId(){
        return article.getGroupId();
    }

    public int getCapacity(){
        return 0;
    }

    public Date getStartTime(){
        return new Date();
    }

    public Date getEndTime(){
        return new Date();
    }

    public enum REGISTRATION_STRUCTURE_KEY {BUSTRANSFER, DINNER, SESSION}

    final JournalArticle article;
    final Document content;

    RegistrationJournalArtical(JournalArticle article) throws PortalException {
        this.article = article;
        try {
            this.content = JournalArticleUtils.parseContent(article.getContent());
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", article.getArticleId(), e.getMessage()), e);
        }
    }

    private static REGISTRATION_STRUCTURE_KEY getStructureType(String ddmStructureKey) {
        for (REGISTRATION_STRUCTURE_KEY value : REGISTRATION_STRUCTURE_KEY.values()) {
            if (ddmStructureKey.startsWith(value.name())) return value;
        }
        return null;
    }

    public static RegistrationJournalArtical getInstance(JournalArticle article) throws PortalException {
        REGISTRATION_STRUCTURE_KEY structureType = getStructureType(article.getDDMStructureKey());
        if (structureType == null){
            throw new NoSuchRegistrationException(String.format("Article %s is not of a valid registration structure type:  %s!", article.getArticleId(), article.getDDMStructureKey()));
        }
        return new RegistrationJournalArtical(article);


    }
}
