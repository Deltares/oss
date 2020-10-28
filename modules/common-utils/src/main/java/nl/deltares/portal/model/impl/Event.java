package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Event extends AbsDsdArticle {

    private static final Log LOG = LogFactoryUtil.getLog(Event.class);
    private List<Registration> registrations = null;
    private EventLocation eventLocation = null;
    private Date startTime = null;
    private Date endTime = null;
    private String emailBannerURL = null;
    private String emailFooterURL = null;

    public Event(JournalArticle journalArticle, DsdParserUtils dsdParserUtils) throws PortalException {
        super(journalArticle, dsdParserUtils);
        init();
    }

    private void init() throws PortalException {
        Document document = getDocument();
        startTime = XmlContentUtils.parseDateTimeFields(document, "start", "starttime", false);
        endTime = XmlContentUtils.parseDateTimeFields(document, "end", "endtime", false);
        String bannerImageJson = XmlContentUtils.getDynamicContentByName(document, "bannerImage", true);
        if (bannerImageJson != null) {
            emailBannerURL = JsonContentUtils.parseImageJson(bannerImageJson);
        }
        String footerImageJson = XmlContentUtils.getDynamicContentByName(document, "footerImage", true);
        if (footerImageJson != null) {
            emailFooterURL = JsonContentUtils.parseImageJson(footerImageJson);
        }
    }

    @Override
    public void validate() throws PortalException {
        parseEventLocation();
        super.validate();
    }

    private void loadEventLocation(){
        if (eventLocation != null) return;
        try {
            parseEventLocation();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing EventLocation for event %s: %s", getTitle(), e.getMessage()));
        }
    }
    private void parseEventLocation() throws PortalException {
        String eventLocationJson = XmlContentUtils.getDynamicContentByName(getDocument(), "eventLocation", false);
        JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(eventLocationJson);
        AbsDsdArticle location = dsdParserUtils.toDsdArticle(article);
        if (! (location instanceof EventLocation)){
            throw new PortalException("Location not instance of EventLocation: " + location.getTitle());
        }
        this.eventLocation = (EventLocation) location;
    }

    public Building findBuilding(Room room){
        if (room == null) return null;
        EventLocation eventLocation = getEventLocation();
        if (eventLocation ==  null) return null;

        List<Building> buildings = eventLocation.getBuildings();
        for (Building building : buildings) {
            if (building.getRooms().contains(room)) return building;
        }
        return null;
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Event.name();
    }

    public EventLocation getEventLocation(){
        loadEventLocation();
        return eventLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEmailBannerURL() {
        return emailBannerURL;
    }

    public String getEmailFooterURL() {
        return emailFooterURL;
    }

    public List<Registration> getRegistrations()  {
        loadRegistrations();
        return Collections.unmodifiableList(registrations);
    }

    public Registration getRegistration(long resourceId) {
        loadRegistrations();
        for (Registration registration : registrations) {
            if (registration.getResourceId() == resourceId) return registration;
        }
        return null;
    }

    public boolean isEventInPast(){
        return System.currentTimeMillis() > startTime.getTime();
    }

    public boolean isMultiDayEvent(){
        long duration = endTime.getTime() - startTime.getTime();
        return duration > TimeUnit.DAYS.toMillis(1);
    }

    public List<BusTransfer> getBusTransfers() {
        return getRegistrations().stream()
                .filter(registration -> registration instanceof BusTransfer)
                .map(registration -> (BusTransfer) registration)
                .collect(Collectors.toList());
    }

    private synchronized void loadRegistrations(){

        if (registrations != null) {
            return;
        }
        try {
            parseRegistrations();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing registrations for event %s: %s", getTitle(), e.getMessage()));
        }
    }

    private void parseRegistrations() throws PortalException {
        registrations = dsdParserUtils.getRegistrations(getGroupId(), getArticleId(), Locale.getDefault());
    }

}
