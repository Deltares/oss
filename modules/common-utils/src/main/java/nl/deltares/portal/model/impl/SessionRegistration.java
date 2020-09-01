package nl.deltares.portal.model.impl;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

public class SessionRegistration extends Registration {
    private static final Log LOG = LogFactoryUtil.getLog(SessionRegistration.class);
    private Room room = null;
    private Expert presenter = null;
    private String imageUrl = "";
    private String webinarKey;

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
            String jsonImage = XmlContentUtils.getDynamicContentByName(document, "eventImage", true);
            if (jsonImage != null) {
                imageUrl = parseImage(jsonImage);
            }
            webinarKey = XmlContentUtils.getDynamicContentByName(document, "webinarKey", true);
            //todo: Add provider currently only GOTO
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
        AbsDsdArticle dsdArticle = parseJsonReference(roomJson);
        if (!(dsdArticle instanceof Room)){
            throw new PortalException("Unsupported registration type! Expected Room but found: " + dsdArticle.getClass().getName());
        }
        room = (Room) dsdArticle;
    }

    private void parsePresenter() throws PortalException {

        String[] presenters = XmlContentUtils.getDynamicContentsByName(getDocument(), "presenters");
        if (presenters.length > 0) {
            //todo: what to do with multiple presenters.
            AbsDsdArticle dsdArticle = parseJsonReference(presenters[0]);
            if (!(dsdArticle instanceof Expert)) {
                throw new PortalException("Unsupported registration type! Expected Expert but found: " + dsdArticle.getClass().getName());
            }
            presenter = (Expert) dsdArticle;
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

    public Expert getPresenter() {
        loadPresenter();
        return presenter;
    }

    private void loadPresenter() {
        if (presenter != null) return;
        try {
            parsePresenter();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing presenter for session %s: %s", getTitle(), e.getMessage()));
        }
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getWebinarKey(){ return  webinarKey; }
    
    private String parseImage(String jsonData) throws PortalException {
        JSONObject jsonObject = JsonContentUtils.parseContent(jsonData);
        FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(jsonObject.getLong("fileEntryId"));
        if (fileEntry == null) return "";
        return DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), null, "", false, true);
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
