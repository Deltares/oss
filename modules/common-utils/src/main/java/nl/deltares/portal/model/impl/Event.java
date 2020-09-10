package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DuplicateCheck;
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
    boolean hasEventLocation = true;
    private Date startTime = null;
    private Date endTime = null;
    private String emailBannerURL = null;
    private String emailFooterURL = null;

    public Event(JournalArticle journalArticle) throws PortalException {
        super(journalArticle);
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
        if (!hasEventLocation || eventLocation != null) return;
        try {
            parseEventLocation();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing EventLocation for event %s: %s", getTitle(), e.getMessage()));
        }
    }
    private void parseEventLocation() throws PortalException {
        String eventLocationJson = XmlContentUtils.getDynamicContentByName(getDocument(), "eventLocation", false);
        Location location = JsonContentUtils.parseLocationJson(eventLocationJson);
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

    /**
     * Return all registrations for a given site
     * @param siteId
     * @param date
     * @return
     * @throws PortalException
     */
    public static List<Registration> getRegistrations(long siteId, Date date) throws PortalException {
        List<JournalArticle> filteredArticle = getFilteredArticle(siteId, date);
        List<Registration> registrations = new ArrayList<>();
        for (JournalArticle article : filteredArticle) {
            registrations.add((Registration) AbsDsdArticle.getInstance(article));
        }
        return registrations;
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

    private void loadRegistrations(){

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
        registrations = new ArrayList<>();
        long siteId = getGroupId();
        long eventId = Long.parseLong(getArticleId());
        DuplicateCheck check = new DuplicateCheck();
        List<JournalArticle> filteredArticle = getFilteredArticle(siteId, this.getStartTime());
        for (JournalArticle journalArticle : filteredArticle) {
            Registration registration = (Registration) AbsDsdArticle.getInstance(journalArticle);
            if (registration.getEventId() != eventId) continue;
            if (registration.getJournalArticle().isInTrash()) continue;
            if (check.checkDuplicates(registration)) registrations.add(registration);
        }
    }

    /**
     * Get all journalArticles for given site and a strucutureKey in list; Session, Dinner, Bustransfer, with a creation time within the search window of
     * event.
     * @param siteId Site identifier
     * @param eventStartTime Start time for which to find matching session articles
     * @return List of Session articles <i>possibly</i> belonging to this event
     */
    private static List<JournalArticle> getFilteredArticle(long siteId, Date  eventStartTime) {

        DynamicQuery searchQuery = JournalArticleLocalServiceUtil.dynamicQuery();
        //filter for site
        searchQuery.add(PropertyFactoryUtil.forName("groupId").eq(siteId));

        //filer for registration structures
        String sessionStructureKey = DSD_STRUCTURE_KEYS.Session.name().toUpperCase();
        String dinnerStructureKey = DSD_STRUCTURE_KEYS.Dinner.name().toUpperCase();
        String transferStructureKey = DSD_STRUCTURE_KEYS.Bustransfer.name().toUpperCase();
        Criterion structureCriteria = PropertyFactoryUtil.forName("DDMStructureKey").like(sessionStructureKey + "%");
        structureCriteria = RestrictionsFactoryUtil.or(
                structureCriteria,PropertyFactoryUtil.forName("DDMStructureKey").like(dinnerStructureKey + "%"));
        structureCriteria = RestrictionsFactoryUtil.or(
                structureCriteria,PropertyFactoryUtil.forName("DDMStructureKey").like(transferStructureKey + "%"));
        searchQuery.add(structureCriteria);

        //filter creation date between ~12months before start up till start
        Date endCreationSearchPeriod = eventStartTime;
        //todo make this configurable
        Date startCreationSearchPeriod = new Date(endCreationSearchPeriod.getTime() - TimeUnit.DAYS.toMillis(365));
        Criterion checkCreationPeriod = PropertyFactoryUtil.forName("createDate").between(startCreationSearchPeriod, endCreationSearchPeriod);
        searchQuery.add(checkCreationPeriod);

        List<JournalArticle> results = JournalArticleLocalServiceUtil.dynamicQuery(searchQuery);
        HashMap<String, JournalArticle> resultsByArticleId = new HashMap<>();
        for (JournalArticle article : results) {
            JournalArticle existingArticle = resultsByArticleId.get(article.getArticleId());
            if (existingArticle == null){
                resultsByArticleId.put(article.getArticleId(), article);
            } else if (existingArticle.getVersion() < article.getVersion()){
                resultsByArticleId.put(article.getArticleId(), article);
            }
        }
        return new ArrayList<>(resultsByArticleId.values());
    }

    public List<BusTransfer> getBusTransfers() {
        return getRegistrations().stream()
                .filter(registration -> registration instanceof BusTransfer)
                .map(registration -> (BusTransfer) registration)
                .collect(Collectors.toList());
    }

}
