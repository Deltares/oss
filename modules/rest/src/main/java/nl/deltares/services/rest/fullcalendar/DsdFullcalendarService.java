package nl.deltares.services.rest.fullcalendar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.Period;
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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final ConfigurationProvider configurationProvider;
    private final DsdParserUtils parserUtils;

    public DsdFullcalendarService(ConfigurationProvider configurationProvider, DsdParserUtils parserUtils) {
        this.configurationProvider = configurationProvider;
        this.parserUtils = parserUtils;
    }

    @GET
    @Path("/events/{siteId}")
    @Produces("application/json")
    public Response events(@Context HttpServletRequest request,
                           @PathParam("siteId") String siteId, @QueryParam("eventIds") String eventIds,
                           @QueryParam("start") String start, @QueryParam("end") String end, @QueryParam("timeZone") String timeZone) {


        if (timeZone != null && !timeZone.isEmpty()) {
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        Date startSearch;
        if (start != null && !start.isEmpty()) {
            try {
                startSearch = dateFormat.parse(start);
            } catch (ParseException e) {
                return Response.serverError().entity(String.format("Error parsing start (%s): %s", start, e.getMessage())).build();
            }
        } else {
            startSearch = null;
        }

        Date endSearch;
        if (end != null && !end.isEmpty()) {
            try {
                endSearch = dateFormat.parse(end);
            } catch (ParseException e) {
                return Response.serverError().entity(String.format("Error parsing end (%s): %s", end, e.getMessage())).build();
            }
        } else {
            endSearch = null;
        }

        String[] ids;
        if (eventIds == null || eventIds.isEmpty() || eventIds.equals("0")) {
            ids = new String[0];
        } else {
            ids = eventIds.split(",");
        }

        DSDSiteConfiguration siteConfiguration;
        try {
            siteConfiguration = getSiteConfiguration(siteId);
        } catch (ConfigurationException e) {
            return Response.serverError().entity(String.format("Error getting DSD siteConfiguration: %s", e.getMessage())).build();
        }

        try {
            Group group = GroupLocalServiceUtil.getGroup(Long.parseLong(siteId));
            Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

            List<Registration> registrations = new ArrayList<>();
            if (ids.length == 0){
                registrations.addAll(parserUtils.getRegistrations(group.getCompanyId(), Long.parseLong(siteId), startSearch, endSearch,
                        getStructureKeys(siteConfiguration), siteConfiguration.dsdRegistrationDateField(), locale));
            } else {
                for (String id : ids) {
                    try {
                        nl.deltares.portal.model.impl.Event event = parserUtils.getEvent(Long.parseLong(siteId), id, locale);
                        registrations.addAll(event.getRegistrations(locale));
                    } catch (PortalException e) {
                        LOG.warn(e.getMessage());
                    }

                }
            }

            return toResponse(getEvents(registrations, startSearch, endSearch, siteConfiguration));
        } catch (PortalException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("/resources/{siteId}")
    @Produces("application/json")
    public Response resources(@Context HttpServletRequest request,
                              @PathParam("siteId") String siteId, @QueryParam("eventIds") String eventIds, @QueryParam("locale") String localeStr) {

        if (eventIds == null || eventIds.isEmpty() || eventIds.equals("0")) {
            DSDSiteConfiguration siteConfiguration;
            try {
                siteConfiguration = getSiteConfiguration(siteId);
            } catch (ConfigurationException e) {
                return Response.serverError().entity(String.format("Error getting DSD siteConfiguration: %s", e.getMessage())).build();
            }
            eventIds = String.valueOf(siteConfiguration.eventId());
        }

        Locale locale = LocaleUtil.fromLanguageId(localeStr);
        String[] ids = eventIds.split(",");
        List<Resource> allResources = new ArrayList<>();
        for (String id : ids) {
            nl.deltares.portal.model.impl.Event dsdEvent;
            try {
                 dsdEvent = parserUtils.getEvent(Long.parseLong(siteId), id, locale);
                List<Resource> resources = getResources(dsdEvent, locale);
                resources.forEach(resource -> {
                    if (!allResources.contains(resource)){
                        allResources.add(resource);
                    }
                });
            } catch (PortalException e) {
                LOG.warn(e.getMessage());
            }

        }
        return toResponse(allResources);

    }

    private String[] getStructureKeys(DSDSiteConfiguration configuration) {
        if (configuration == null) return new String[0];
        String structureList = configuration.dsdRegistrationStructures();
        if (structureList != null && !structureList.isEmpty()) {
            return StringUtil.split(structureList, ' ');
        }
        return new String[0];
    }

    private List<Event> getEvents(List<Registration> registrations, Date startSearch, Date endSearch, DSDSiteConfiguration siteConfiguration) {

        List<Event> events = new ArrayList<>(registrations.size());
        for (Registration registration : registrations) {
            if (registration.getJournalArticle().isInTrash()) continue;
            if (startSearch != null) {
                Date endTime = registration.getEndTime();
                if (endTime.before(startSearch)) continue;
            }
            if (endSearch != null) {
                Date startTime = registration.getStartTime();
                if (startTime.after(endSearch)) continue;
            }

            int dayCounter = 0;
            List<Period> periodsPerDay = registration.getStartAndEndTimesPerDay();
            for (Period period : periodsPerDay) {
                events.add(createCalendarEvent(registration, dayCounter++, period.getStartTime(), period.getEndTime(), siteConfiguration));
            }
        }

        return events;
    }

    private Event createCalendarEvent(Registration registration, int dayCount, long startDay, long endDay, DSDSiteConfiguration siteConfiguration) {
        Event event = new Event();
        event.setId(registration.getArticleId() + '_' + dayCount);
        if (registration instanceof SessionRegistration) {
            event.setResourceId(String.valueOf(((SessionRegistration) registration).getRoom().getResourceId()));
            event.setUrl("-/" + registration.getJournalArticle().getUrlTitle());
        } else if (registration instanceof DinnerRegistration) {
            event.setResourceId(String.valueOf(((DinnerRegistration) registration).getRestaurant().getResourceId()));
            if (siteConfiguration != null && siteConfiguration.travelStayURL() != null && !siteConfiguration.travelStayURL().isEmpty()) {
                event.setUrl(getPageUrl(registration.getGroupId(), siteConfiguration.travelStayURL()));
            } else {
                event.setUrl("-/" + registration.getJournalArticle().getUrlTitle());
            }
        } else if (registration instanceof BusTransfer) {
            event.setResourceId("bustransfer");
            if (siteConfiguration != null && siteConfiguration.busTransferURL() != null) {
                event.setUrl(getPageUrl(registration.getGroupId(), siteConfiguration.busTransferURL()));
            }
        }
        event.setStart(startDay);
        event.setEnd(endDay);
        event.setType(registration.getType());
        event.setTitle(registration.getTitle());

        return event;
    }

    private String getPageUrl(long groupId, String relativeUrl) {
        try {
            String friendlyURL = GroupServiceUtil.getGroup(groupId).getFriendlyURL();
            return StringBundler.concat("/web", friendlyURL, relativeUrl);
        } catch (PortalException e) {
            return "";
        }
    }

    private List<Resource> getResources(nl.deltares.portal.model.impl.Event dsdEvent, Locale locale) {

        EventLocation dsdLocation = dsdEvent.getEventLocation();
        List<Resource> resources = new ArrayList<>();
        if (dsdLocation != null) {
            resources.addAll(getBuildingResources(dsdLocation.getBuildings()));
            resources.addAll(getRoomResources(dsdLocation.getRooms()));
        }
        resources.addAll(getExternalResources(dsdEvent.getRegistrations(locale)));
        return resources;
    }

    private List<Resource> getExternalResources(List<Registration> registrations) {

        ArrayList<Resource> externals = new ArrayList<>();
        for (Registration registration : registrations) {
            if (registration instanceof DinnerRegistration) {
                Location restaurant = ((DinnerRegistration) registration).getRestaurant();
                Resource resource = new Resource();
                resource.setBuilding("Restaurant");
                resource.setId(String.valueOf(restaurant.getResourceId()));
                resource.setTitle(restaurant.getTitle());
                externals.add(resource);
            } else if (registration instanceof BusTransfer) {
                Resource resource = new Resource();
                resource.setBuilding("Bus Transfer");
                resource.setId("bustransfer");
                resource.setTitle("Bus Transfer");
                externals.add(resource);
            }

        }
        return externals;
    }

    private List<Resource> getBuildingResources(List<Building> buildings) {
        List<Resource> resources = new ArrayList<>();
        for (Building building : buildings) {

            List<Resource> roomResources = getRoomResources(building.getRooms());
            for (Resource roomResource : roomResources) {
                roomResource.setBuilding(building.getTitle());
            }
            resources.addAll(roomResources);
        }
        return resources;
    }

    private List<Resource> getRoomResources(List<Room> rooms) {
        List<Resource> resources = new ArrayList<>(rooms.size());
        for (Room room : rooms) {
            Resource resource = new Resource();
            resource.setId(String.valueOf(room.getResourceId()));
            resource.setTitle(room.getTitle());
            resource.setCapacity(room.getCapacity());
            resource.setBuilding("Other");
            resources.add(resource);
        }
        return resources;
    }

    private DSDSiteConfiguration getSiteConfiguration(String siteId) throws ConfigurationException {
        DSDSiteConfiguration siteConfiguration;
        siteConfiguration = configurationProvider
                .getGroupConfiguration(DSDSiteConfiguration.class, Long.parseLong(siteId));
        return siteConfiguration;
    }
}