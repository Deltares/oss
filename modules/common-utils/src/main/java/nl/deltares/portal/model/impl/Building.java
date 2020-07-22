package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DuplicateCheck;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Building extends AbsDsdArticle {

    private static final Log LOG = LogFactoryUtil.getLog(Building.class);
    private boolean storeInParentSite;
    private List<Room> rooms = null;
    private double longitude;
    private double latitude;

    public Building(JournalArticle dsdArticle) throws PortalException {
        super(dsdArticle);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String storeInParentSite = XmlContentParserUtils.getDynamicContentByName(document, "storeInParentSite", true);
            this.storeInParentSite = Boolean.parseBoolean(storeInParentSite);

            String geoLocation = XmlContentParserUtils.getDynamicContentByName(document, "location", false);
            Map<String, String> coords = JsonContentParserUtils.parseJsonToMap(geoLocation);
            this.longitude = Double.parseDouble(coords.get("longitude"));
            this.latitude =  Double.parseDouble(coords.get("latitude"));
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public void validate() throws PortalException {
        parseRooms();
        super.validate();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Building.name();
    }

    @Override
    public boolean storeInParentSite() {
        return storeInParentSite;
    }

    static List<Room> parseRooms(String[] roomReferences) throws PortalException {

        DuplicateCheck check = new DuplicateCheck();
        ArrayList<Room> rooms = new ArrayList<>();
        for (String json : roomReferences) {
            Room room = JsonContentParserUtils.parseRoomJson(json);
            if (check.checkDuplicates(room)) rooms.add(room);
        }
        return rooms;
    }

    public List<Room> getRooms() {
        if (rooms == null) {
            try {
                parseRooms();
            } catch (PortalException e) {
                LOG.error(String.format("Error parsing rooms for building %s: %s", getTitle(), e.getMessage()));
            }
        }
        return Collections.unmodifiableList(this.rooms);
    }

    private void parseRooms() throws PortalException {
        this.rooms = new ArrayList<>();
        String[] rooms = XmlContentParserUtils.getDynamicContentsByName(getDocument(), "rooms");
        if (rooms.length > 0){
            this.rooms = parseRooms(rooms);
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
