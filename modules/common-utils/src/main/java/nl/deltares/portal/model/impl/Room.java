package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public class Room extends AbsDsdArticle {
    private int capacity;
    private boolean storeInParentSite;
    private String calendarColor;

    public Room(JournalArticle dsdArticle) throws PortalException {
        super(dsdArticle);
        init();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Room.name();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            Object capacity = XmlContentParserUtils.getNodeValue(document, "capacity", false);
            this.capacity =  capacity == null ? Integer.MAX_VALUE : (int) capacity;
            Object storeInParentSite = XmlContentParserUtils.getNodeValue(document, "storeInParentSite", true);
            this.storeInParentSite = storeInParentSite != null && (boolean) storeInParentSite;
            Object calendarColor = XmlContentParserUtils.getNodeValue(document, "calendarColor", true);
            this.calendarColor = calendarColor == null ? null : (String) calendarColor;

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean storeInParentSite() {
        return storeInParentSite;
    }

    /**
     * Returns calender color of room as HEX string
     * @return HEX string value
     */
    public String getCalendarColor() {
        return calendarColor;
    }
}
