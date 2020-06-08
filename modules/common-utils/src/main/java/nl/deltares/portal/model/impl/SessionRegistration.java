package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public class SessionRegistration extends Registration {

    private Room room;
    public SessionRegistration(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Session.name();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            Object roomJson = XmlContentParserUtils.getNodeValue(document, "room", false);
            room = parseRoomRegistration((String) roomJson);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Room parseRoomRegistration(String roomJson) throws PortalException {
        AbsDsdArticle dsdArticle = parseJsonReference(roomJson);
        if (dsdArticle instanceof Room) return (Room) dsdArticle;
        throw new PortalException("Unsupported registration type! Expected Room but found: " + dsdArticle.getClass().getName());
    }

    public Room getRoom(){
        return room;
    }

    @Override
    public boolean storeInParentSite() {
        return false;
    }
}
