package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;

import java.util.*;

public class Building extends AbsDsdArticle {

    private static final Log LOG = LogFactoryUtil.getLog(Building.class);
    private List<Room> rooms = null;
    private double longitude;
    private double latitude;

    public Building(JournalArticle dsdArticle, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(dsdArticle, dsdParserUtils, locale);
        init();
    }

    private void init() throws PortalException {
        try {
            Map<String, String> coords = JsonContentUtils.parseJsonToMap(getFormFieldValue("location", false));
            this.longitude = Double.parseDouble(coords.getOrDefault("longitude", coords.get("lng")));
            this.latitude =  Double.parseDouble(coords.getOrDefault("latitude", coords.get("lat")));
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
        List<String> rooms = getFormFieldValues("rooms", true);
        if (rooms.size() > 0){
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
