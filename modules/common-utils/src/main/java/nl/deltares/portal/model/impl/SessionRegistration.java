package nl.deltares.portal.model.impl;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public class SessionRegistration extends Registration {

    private Room room;
    private Expert presenter;
    private String imageUrl;

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
            String jsonImage = XmlContentParserUtils.getDynamicContentByName(document, "eventImage", true);
            if (jsonImage != null) {
                imageUrl = parseImage(jsonImage);
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

    public String getImageUrl(){
        return imageUrl;
    }

    private String parseImage(String jsonData) throws PortalException {
        JSONObject jsonObject = JsonContentParserUtils.parseContent(jsonData);
        FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(jsonObject.getLong("fileEntryId"));
        if (fileEntry == null) return null;
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
