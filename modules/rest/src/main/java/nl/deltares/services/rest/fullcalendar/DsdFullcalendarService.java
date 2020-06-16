package nl.deltares.services.rest.fullcalendar;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.services.rest.fullcalendar.models.Event;
import nl.deltares.services.rest.fullcalendar.models.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static nl.deltares.services.utils.Helper.toResponse;

/**
 * @author rooij_e
 */
@Path("/calendar")
public class DsdFullcalendarService {
    private static final Log LOG = LogFactoryUtil.getLog(DsdFullcalendarService.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final ConfigurationProvider configurationProvider;

    public DsdFullcalendarService(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    @GET
    @Path("/events/{siteId}/{eventId}")
    @Produces("application/json")
    public Response events(@Context HttpServletRequest request,
                           @PathParam("siteId") long siteId, @PathParam("eventId") long eventId,
                           @QueryParam("portletId") String portletId, @QueryParam("layoutUuid") String layoutUuid,
                           @QueryParam("start") String start, @QueryParam("end") String end, @QueryParam("timeZone") String timeZone) {

        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date startSearch;
        try {
            startSearch = dateFormat.parse(start);
        } catch (ParseException e) {
            return Response.serverError().entity(String.format("Error parsing start (%s): %s", start, e.getMessage())).build();
        }
        Date endSearch;
        try {
             endSearch = dateFormat.parse(end);
        } catch (ParseException e) {
            return Response.serverError().entity(String.format("Error parsing end (%s): %s", start, e.getMessage())).build();
        }
        DsdEvent dsdEvent;
        try {
            dsdEvent = getDsdEvent(siteId, eventId);
        } catch (PortalException e) {
            return  Response.serverError().entity(e.getMessage()).build();
        }

        Map<String, String> colorMap = getColorMap(layoutUuid, siteId, portletId);

        List<AbsDsdArticle> sortedLocations = getSortedLocations(dsdEvent);
        List<Registration> eventSessions = dsdEvent.getEventSessions();
        List<Event> events = new ArrayList<>();
        for (Registration eventSession : eventSessions) {
            if (!isInSearchPeriod(eventSession, startSearch, endSearch)) continue;
            Event event = new Event();
            event.setTitle(eventSession.getTitle());
            event.setId(String.valueOf(eventSession.getResourceId()));
            event.setResourceId(String.valueOf(sortedLocations.indexOf(getLocation(eventSession))));
            event.setStart(eventSession.getStartTime().getTime());
            event.setEnd(eventSession.getEndTime().getTime());
            event.setUrl(dsdEvent.getTitle());
            event.setColor(colorMap.get(eventSession.getType()));
            events.add(event);
        }

        return toResponse(events);

    }

    private Map<String, String> getColorMap(String layoutUuid, long siteId, String portletId) {

        Layout layout = null;
        try {
            layout = LayoutServiceUtil.getLayoutByUuidAndGroupId(layoutUuid, siteId, false);
        } catch (PortalException e) {
            LOG.error(String.format("Error retrieving FullCalendar portlet layout for uuid '%s': %s", layoutUuid, e.getMessage()));
            return Collections.emptyMap();
        }
        FullCalendarConfiguration groupConfiguration = null;
        try {
            groupConfiguration = configurationProvider.getPortletInstanceConfiguration(FullCalendarConfiguration.class, layout , portletId);
        } catch (ConfigurationException e) {
            LOG.error(String.format("Error retrieving FullCalendarConfiguration for siteId '%s': %s", portletId, e.getMessage()));
            return Collections.emptyMap();
        }

        String jsonColorMap = groupConfiguration.sessionColorMap();
        try {
            return JsonContentParserUtils.parseJsonToMap(jsonColorMap);
        } catch (JSONException e) {
            LOG.error(String.format("Error parsing color map configuration for siteId '%s': %s", portletId, e.getMessage()));
            return Collections.emptyMap();
        }


    }

    private boolean isInSearchPeriod(Registration eventSession, Date startSearch, Date endSearch) {
        if (eventSession.getStartTime().after(endSearch)) return false;
        return !eventSession.getEndTime().before(startSearch);
    }

    @GET
    @Path("/resources/{siteId}/{eventId}")
    @Produces("application/json")
    public Response resources( @Context HttpServletRequest request,
                               @PathParam("siteId") long siteId, @PathParam("eventId") long eventId) {

        DsdEvent dsdEvent;
        try {
            dsdEvent = getDsdEvent(siteId, eventId);
        } catch (PortalException e) {
            return  Response.serverError().entity(e.getMessage()).build();
        }

        List<AbsDsdArticle> sortedLocations = getSortedLocations(dsdEvent);
        List<Resource> resources = new ArrayList<>();
        for (int i = 0; i < sortedLocations.size(); i++) {
            AbsDsdArticle dsdArticle = sortedLocations.get(i);
            Resource resource = new Resource();
            resource.setId(String.valueOf(i));
            resource.setTitle(dsdArticle.getTitle());
            resources.add(resource);
        }
        return toResponse(resources);
    }

    private List<AbsDsdArticle> getSortedLocations(DsdEvent dsdEvent) {

        List<Registration> eventSessions = dsdEvent.getEventSessions();
        List<Long> mapped = new ArrayList<>();
        List<AbsDsdArticle> rooms = new ArrayList<>();
        List<AbsDsdArticle> locations = new ArrayList<>();
        for (Registration eventSession : eventSessions) {
            if (mapped.contains(eventSession.getResourceId())) continue;
            mapped.add(eventSession.getResourceId());
            AbsDsdArticle absDsdArticle = getLocation(eventSession);
            if (absDsdArticle instanceof Room) {
                rooms.add(absDsdArticle);
            } else if (absDsdArticle instanceof Location) {
                locations.add(absDsdArticle);
            }
        }
        rooms.sort(Comparator.comparing(AbsDsdArticle::getTitle));
        locations.sort(Comparator.comparing(AbsDsdArticle::getTitle));
        rooms.addAll(locations);
        return rooms;
    }

    private AbsDsdArticle getLocation(Registration eventSession) {

        if (eventSession instanceof SessionRegistration){
            return ((SessionRegistration) eventSession).getRoom();
        }
        if (eventSession instanceof DinnerRegistration){
            return  ((DinnerRegistration) eventSession).getRestaurant();
        }
        throw new UnsupportedOperationException("Unsupported Registration type for " + eventSession.getTitle());
    }

    private DsdEvent getDsdEvent( long siteId, long eventId) throws PortalException {
        JournalArticle eventResource = JournalArticleLocalServiceUtil.getLatestArticle(siteId, String.valueOf(eventId));
        AbsDsdArticle eventArticle = AbsDsdArticle.getInstance(eventResource);
        if (! (eventArticle instanceof DsdEvent) ){
            throw new PortalException(String.format("EventId %d is not the articleId of a valid DSD Event", eventId));
        }
        return (DsdEvent) eventArticle;
    }


}