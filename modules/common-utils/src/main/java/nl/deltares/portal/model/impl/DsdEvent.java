package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.util.*;

public class DsdEvent extends AbsDsdArticle {

    private static final Log LOG = LogFactoryUtil.getLog(DsdEvent.class);
    private final List<Registration> registrations = new ArrayList<>();
    private EventLocation eventLocation;
    private Date startDay = null;
    private Date endDay = null;

    public DsdEvent(JournalArticle journalArticle) throws PortalException {
        super(journalArticle);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String[] eventSessions = XmlContentParserUtils.getDynamicContentsByName(document, "eventSession");
            registrations.addAll( parseRegistrationsData(eventSessions));
            String eventLocation = XmlContentParserUtils.getDynamicContentByName(document, "eventLocation", false);
            this.eventLocation = parseEventLocation(eventLocation);
            loadEventPeriod();
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private void loadEventPeriod() {
        if (registrations.size() == 0){
            startDay = new Date();
            endDay = new Date();
        }
        for (Registration registration : registrations) {
            if(startDay == null || registration.getStartTime().before(startDay)){
                startDay = registration.getStartTime();
            }
            if (endDay == null || registration.getEndTime().after(endDay)){
                endDay = registration.getEndTime();
            }
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
