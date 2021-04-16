package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SessionRegistration extends Registration {
    private static final Log LOG = LogFactoryUtil.getLog(SessionRegistration.class);
    private Room room = null;
    private final List<Expert> presenters = new ArrayList<>();
    private final List<Presentation> presentations = new ArrayList<>();
    private String imageUrl = "";
    private String webinarKey = "";
    private String provider = "";

    public SessionRegistration(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, locale);
        init();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Session.name();
    }

    private void init() throws PortalException {

        try {
            Document document = getDocument();
            String jsonImage = XmlContentUtils.getDynamicContentByName(document, "eventImage", true);
            if (jsonImage != null) {
                imageUrl = JsonContentUtils.parseImageJson(jsonImage);
            }
            String webinarKey = XmlContentUtils.getDynamicContentByName(document, "webinarKey", true);
            if (webinarKey != null) this.webinarKey = webinarKey;
            String provider = XmlContentUtils.getDynamicContentByName(document, "provider", true);
            if (provider != null) this.provider = provider;
            initDates(document);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public void validate() throws PortalException {
        parseRoom();
        parsePresenter();
        validateRoomCapacity();
        super.validate();
    }

    private void validateRoomCapacity() throws PortalException {
        int sessionCapacity = getCapacity();
        int roomCapacity = getRoom().getCapacity();
        if (roomCapacity < sessionCapacity) {
            throw new ValidationException(String.format("Room capacity %d is smaller than session capacity %s !", roomCapacity, sessionCapacity));
        }
    }

    private void parseRoom() throws PortalException {
        String roomJson = XmlContentUtils.getDynamicContentByName(getDocument(), "room", false);
        JournalArticle journalArticle = JsonContentUtils.jsonReferenceToJournalArticle(roomJson);
        AbsDsdArticle dsdArticle = dsdParserUtils.toDsdArticle(journalArticle, getLocale());
        if (!(dsdArticle instanceof Room)){
            throw new PortalException("Unsupported registration type! Expected Room but found: " + dsdArticle.getClass().getName());
        }
        room = (Room) dsdArticle;
    }

    private void parsePresenter() throws PortalException {

        String[] presenters = XmlContentUtils.getDynamicContentsByName(getDocument(), "presenters");
        for (String presenterJson : presenters) {
            JournalArticle journalArticle = JsonContentUtils.jsonReferenceToJournalArticle(presenterJson);
            this.presenters.add( dsdParserUtils.getExpert(journalArticle) );
        }
    }

    public Room getRoom() {
        loadRoom();
        return room;
    }

    private void loadRoom() {
        if (room != null) return;
        try {
            parseRoom();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing room for session %s: %s", getTitle(), e.getMessage()));
        }
    }

    public List<Expert> getPresenters() {
        loadPresenter();
        return Collections.unmodifiableList(presenters);
    }

    private void loadPresenter() {
        if (presenters.size() > 0) return;
        try {
            parsePresenter();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing presenter for session %s: %s", getTitle(), e.getMessage()));
        }
    }

    public String getWebinarKey(){ return  webinarKey; }

    public String getWebinarProvider() {
        return provider;
    }

    @SuppressWarnings("unused")
    public List<Presentation> getPresentations(){
        loadPresentations();
        return Collections.unmodifiableList(presentations);
    }

    private void loadPresentations() {
        if (presentations.size() > 0) return;
        try {
            parsePresentations();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing presentations for session %s: %s", getTitle(), e.getMessage()));
        }
    }

    private void parsePresentations() throws PortalException {
        String[] presentations = XmlContentUtils.getDynamicContentsByName(getDocument(), "presentation");
        for (String presentationJson : presentations) {
            JournalArticle journalArticle = JsonContentUtils.jsonReferenceToJournalArticle(presentationJson);
            this.presentations.add((Presentation) dsdParserUtils.toDsdArticle(journalArticle, getLocale()));
        }

    }

    @Override
    public String getSmallImageURL(ThemeDisplay themeDisplay) {
        String smallImageURL = super.getSmallImageURL(themeDisplay);
        if (smallImageURL != null && !smallImageURL.trim().isEmpty()) {
            return smallImageURL;
        }
        return imageUrl;
    }
}
