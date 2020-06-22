package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class EventLocation extends Location {
    private static final Log LOG = LogFactoryUtil.getLog(EventLocation.class);
    private final List<Room> rooms = new ArrayList<>();
    private final List<Building> buildings = new ArrayList<>();
    public EventLocation(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init(){
        Document document = getDocument();
        String[] buildings = XmlContentParserUtils.getDynamicContentsByName(document, "buildings");
        if (buildings.length > 0){
            this.buildings.addAll(parseBuildings(buildings));
        }
        String[] rooms = XmlContentParserUtils.getDynamicContentsByName(document, "rooms");
        if (rooms.length > 0){
            this.rooms.addAll(Building.parseRooms(rooms));
        }

    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Eventlocation.name();
    }

    public List<Room> getRooms(){
        return new ArrayList<>(rooms);
    }

    public List<Building> getBuildings(){
        return new ArrayList<>(buildings);
    }

    private List<Building> parseBuildings(String[] buildingReferences) {

        ArrayList<Building> buildings = new ArrayList<>();
        for (String json : buildingReferences) {
            try {
                buildings.add(JsonContentParserUtils.parseBuildingJson(json));
            } catch (PortalException e) {
                LOG.error(String.format("Error getting article for building: %s: %s", json, e.getMessage()));
            }
        }
        return buildings;
    }

}
