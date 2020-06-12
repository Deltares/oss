package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.*;

public class DsdEvent extends AbsDsdArticle {

    private static final Log LOG = LogFactoryUtil.getLog(DsdEvent.class);
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private final List<Registration> registrations = new ArrayList<>();
    private EventLocation eventLocation;
    private Date startDay;
    private Date endDay;
    private final HashMap<String, String> colorMap = new HashMap<>();

    public DsdEvent(JournalArticle journalArticle) throws PortalException {
        super(journalArticle);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String[] eventSessions = XmlContentParserUtils.getNodeValues(document, "eventSession");
            registrations.addAll( parseRegistrationsData(eventSessions));
            Object eventLocation = XmlContentParserUtils.getNodeValue(document, "eventLocation", false);
            this.eventLocation = parseEventLocation((String) eventLocation);
            this.startDay = parseDate("startDay");
            this.endDay = parseDate("endDay");
            parseColorMap();
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private void parseColorMap() throws PortalException {
        try {
//            Document document = getDocument();
//            NodeList mappings = null;
//            if (mappings == null) return;
//            for (int i = 0; i < mappings.getLength(); i++) {
//                Node item = mappings.item(i);
//                String[] nodeValues = XmlContentParserUtils.getNodeValues(item.getChildNodes());
//                if (nodeValues.length == 2){
//                    colorMap.put(nodeValues[0], nodeValues[1]);
//                }
//            }

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing Color mappings for event %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Date parseDate(String dateField) throws PortalException {
        try {
            Document document = getDocument();
            String dateValue = (String) XmlContentParserUtils.getNodeValue(document, dateField, false);
            return dateFormatter.parse(dateValue);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing date field %s for article %s: %s!", dateField, getTitle(), e.getMessage()), e);
        }
    }

    private EventLocation parseEventLocation(String eventLocation) throws PortalException {
        Location location = JsonContentParserUtils.parseLocationJson(eventLocation);
        if (! (location instanceof EventLocation)){
            throw new PortalException("Location not instance of EventLocation: " + location.getTitle());
        }
        return (EventLocation) location;
    }

    private Collection<? extends Registration> parseRegistrationsData(String[] eventSessions) {

        ArrayList<Registration> registrations = new ArrayList<>();
        for (String json : eventSessions) {
            try {
                registrations.add(JsonContentParserUtils.parseRegistrationJson(json));
            } catch (PortalException e) {
                LOG.error(String.format("Error getting article for reference: %s: %s", json, e.getMessage()));
            }
        }
        return registrations;
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Dsdevent.name();
    }

    public List<Registration> getEventSessions() {
        return new ArrayList<>(registrations);
    }

    public EventLocation getEventLocation() {
        return eventLocation;
    }

    public Date getStartDay() {
        return startDay;
    }

    public Date getEndDay() {
        return endDay;
    }
}
