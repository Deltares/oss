package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DuplicateCheck;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventLocation extends Location {

    private static final Log LOG = LogFactoryUtil.getLog(EventLocation.class);
    private List<Room> rooms = null;
    private List<Building> buildings = null;

    public EventLocation(JournalArticle article, DsdParserUtils dsdParserUtils) throws PortalException {
        super(article, dsdParserUtils);
    }

    @Override
    public void validate() throws PortalException {
        parseRooms();
        parseBuildings();
        super.validate();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Eventlocation.name();
    }

    public List<Room> getRooms() {
        loadRooms();
        return Collections.unmodifiableList(rooms);
    }

    private void loadRooms(){
        if (rooms != null) return;
        try {
            parseRooms();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing rooms for EventLocation %s: %s", getTitle(), e.getMessage()));
        }
    }

    private void parseRooms() throws PortalException {
        this.rooms = new ArrayList<>();
        String[] rooms = XmlContentUtils.getDynamicContentsByName(getDocument(), "rooms");
        if (rooms.length > 0){
            this.rooms.addAll(parseRooms(rooms));
        }
    }

    public List<Building> getBuildings() {
        loadBuildings();
        return Collections.unmodifiableList(buildings);
    }

    private void loadBuildings() {
        if (buildings != null) return;
        try {
            parseBuildings();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing buildings for EventLocation %s: %s", getTitle(), e.getMessage()));
        }
    }
    private void parseBuildings() throws PortalException {
        this.buildings = new ArrayList<>();
        String[] buildings = XmlContentUtils.getDynamicContentsByName(getDocument(), "buildings");
        if (buildings.length > 0){
            this.buildings.addAll(parseBuildings(buildings));
        }
    }

    private List<Building> parseBuildings(String[] buildingReferences) throws PortalException {

        DuplicateCheck check = new DuplicateCheck();
        ArrayList<Building> buildings = new ArrayList<>();
        for (String json : buildingReferences) {
            JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(json);
            Building building = (Building) dsdParserUtils.toDsdArticle(article);
            if (check.checkDuplicates(building)) buildings.add(building);
        }
        return buildings;
    }

}
