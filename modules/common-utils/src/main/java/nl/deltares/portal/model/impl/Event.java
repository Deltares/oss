package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Event extends AbsDsdArticle {

    private final List<Registration> registrationCache = new ArrayList<>();
    private EventLocation eventLocation;
    private Date startTime = null;
    private Date endTime = null;

    public Event(JournalArticle journalArticle) throws PortalException {
        super(journalArticle);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String eventLocation = XmlContentParserUtils.getDynamicContentByName(document, "eventLocation", false);
            this.eventLocation = parseEventLocation(eventLocation);
            startTime = XmlContentParserUtils.parseDateTimeFields(document, "start", "starttime", false);
            endTime = XmlContentParserUtils.parseDateTimeFields(document, "end", "endtime", false);
            loadRegistrations();
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private EventLocation parseEventLocation(String eventLocation) throws PortalException {
        Location location = JsonContentParserUtils.parseLocationJson(eventLocation);
        if (! (location instanceof EventLocation)){
            throw new PortalException("Location not instance of EventLocation: " + location.getTitle());
        }
        return (EventLocation) location;
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Event.name();
    }

    public EventLocation getEventLocation() {
        return eventLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public List<Registration> getRegistrations() {
        return Collections.unmodifiableList(registrationCache);
    }

    public boolean isEventInPast(){
        return System.currentTimeMillis() > startTime.getTime();
    }

    public boolean isMultiDayEvent(){
        long duration = endTime.getTime() - startTime.getTime();
        return TimeUnit.MILLISECONDS.toHours(duration) > TimeUnit.DAYS.toMillis(1);
    }

    private void loadRegistrations() throws PortalException {

        long siteId = getGroupId();
        long eventId = Long.parseLong(getArticleId());

        List<JournalArticle> filteredArticle = getFilteredArticle(siteId, this);
        for (JournalArticle journalArticle : filteredArticle) {
            Registration registration = (Registration) AbsDsdArticle.getInstance(journalArticle);
            if (registration.getEventId() != eventId) continue;
            registrationCache.add(registration);
        }
    }

    /**
     * Get all journalArticles for given site and structure type and with a creation time within the search window of
     * event.
     * @param siteId Site identifier
     * @param eventArticle Event for which to find matching session articles
     * @return List of Session articles <i>possibly</i> belonging to this event
     */
    private List<JournalArticle> getFilteredArticle(long siteId, Event eventArticle) {


        DynamicQuery searchQuery = JournalArticleLocalServiceUtil.dynamicQuery();
        //filter for site
        searchQuery.add(PropertyFactoryUtil.forName("groupId").eq(siteId));

        //filer for registration structures
        String sessionStructureKey = DsdArticle.DSD_STRUCTURE_KEYS.Session.name().toUpperCase();
        String dinnerStructureKey = DsdArticle.DSD_STRUCTURE_KEYS.Dinner.name().toUpperCase();
        Criterion structureCriteria = PropertyFactoryUtil.forName("DDMStructureKey").like(sessionStructureKey + "%");
        structureCriteria = RestrictionsFactoryUtil.or(structureCriteria,PropertyFactoryUtil.forName("DDMStructureKey").like(dinnerStructureKey + "%"));
        searchQuery.add(structureCriteria);

        //filter creation date between ~6months before start up till start
        Date endCreationSearchPeriod = eventArticle.getStartTime();
        //todo make this configurable
        Date startCreationSearchPeriod = new Date(endCreationSearchPeriod.getTime() - TimeUnit.DAYS.toMillis(180));
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


}
