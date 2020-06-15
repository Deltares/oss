package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

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
            String roomJson = XmlContentParserUtils.getDynamicContentByName(document, "room", false);
            room = parseRoomRegistration(roomJson);
            String[] presenters = XmlContentParserUtils.getDynamicContentsByName(document, "presenters");
            if (presenters.length > 0) {
                //todo: what to do with multiple presenters.
                presenter = parsePresenterData(presenters[0]);
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Room parseRoomRegistration(String roomJson) throws PortalException {
        if (roomJson == null){
            throw new NullPointerException("roomJson");
        }
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

}
