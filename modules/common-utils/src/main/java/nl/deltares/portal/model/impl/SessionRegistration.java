package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.util.Arrays;

public class SessionRegistration extends Registration {

    private Room room;
    private Expert presenter;

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
            String[] presenters = XmlContentParserUtils.getNodeValues(document, "presenters");
            presenter = parsePresenterData(presenters[0]);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Room parseRoomRegistration(String roomJson) throws PortalException {
        AbsDsdArticle dsdArticle = parseJsonReference(roomJson);
        if (dsdArticle instanceof Room) return (Room) dsdArticle;
        throw new PortalException("Unsupported registration type! Expected Room but found: " + dsdArticle.getClass().getName());
    }

    private Expert parsePresenterData(String jsonData) throws PortalException {
        AbsDsdArticle dsdArticle = parseJsonReference(jsonData);
        if (!(dsdArticle instanceof Expert)) {
            throw new PortalException("Unsupported registration type! Expected Expert but found: " + dsdArticle.getClass().getName());
        }
        return (Expert) dsdArticle;
    }

    public Room getRoom(){
        return room;
    }

    public Expert getPresenter() {
        return presenter;
    }

    @Override
    public boolean storeInParentSite() {
        return false;
    }
}
