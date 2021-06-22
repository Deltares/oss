package nl.deltares.portal.model;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import org.w3c.dom.Document;

public interface DsdArticle {

    enum DSD_LOCATION_KEYS {restaurant, hotel, event}
    enum DSD_REGISTRATION_STRUCTURE_KEYS {Session, Bustransfer, Dinner}
    enum DSD_STRUCTURE_KEYS {Expert, Location, Building, Room, Eventlocation, Registration, Session, Bustransfer, Busroute, Dinner, Event, Presentation, Generic}

    String getTitle();
    long getCompanyId();
    long getGroupId();
    long getResourceId();
    String getArticleId();
    String getStructureKey();
    Document getDocument();
    boolean storeInParentSite();
    String getSmallImageURL(ThemeDisplay themeDisplay);
    JournalArticle getJournalArticle();
    void validate() throws PortalException;
}
