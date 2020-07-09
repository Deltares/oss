package nl.deltares.services.rest.registration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.services.rest.registration.modules.RegistrationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static nl.deltares.services.utils.Helper.getRemoteUser;

/**
 * @author rooij_e
 */
@Path("/session")
public class SessionRegistrationService {

    private static final Log LOG = LogFactoryUtil.getLog(SessionRegistrationService.class);
    private final DsdSessionUtils registrationUtils;
    private final DsdParserUtils dsdParserUtils;

    public SessionRegistrationService(DsdSessionUtils registrationUtils, DsdParserUtils dsdParserUtils) {
        this.registrationUtils = registrationUtils;
        this.dsdParserUtils = dsdParserUtils;
    }

    @GET
    @Path("/register/{siteId}/{eventId}")
    @Consumes("application/json")
    public Response registrations(@Context HttpServletRequest request,
                                  @PathParam("siteId") long siteId,
                                  @PathParam("eventId") long eventId) throws Exception {

        User user = getRemoteUser(request);
        Event event = dsdParserUtils.getEvent(siteId, String.valueOf(eventId));
        List<Map<String, Object>> registrationRecords = registrationUtils.getUserRegistrations(user, event);
        List<RegistrationDetails> details = new ArrayList<>();
        registrationRecords.stream().map(stringObjectMap ->
                getRegistrationDetails(stringObjectMap, event))
                .filter(Objects::nonNull).forEach(details::add);
        return Response.ok(details).type(MediaType.APPLICATION_JSON).build();

    }


    @POST
    @Path("/register/{siteId}/{articleId}")
    @Consumes("application/json")
    public Response register(@Context HttpServletRequest request,
                         @PathParam("siteId") long siteId, @PathParam("articleId") long articleId, String jsonProperties) throws Exception {

        User user = getRemoteUser(request);
        Registration registration = dsdParserUtils.getRegistration(siteId, String.valueOf(articleId));

        try {
            registrationUtils.registerUser(user, registration, jsonProperties);
        } catch (ValidationException e) {
            String msg = JsonContentParserUtils.formatTextToJson("message","Validation Exception: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.accepted(JsonContentParserUtils.formatTextToJson("message","User registered for " + registration.getTitle())).type(MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/register/{siteId}/{articleId}")
    @Consumes("application/json")
    public Response unRegister(@Context HttpServletRequest request,
                             @PathParam("siteId") long siteId, @PathParam("articleId") long articleId) throws Exception {
        User user = getRemoteUser(request);

        Registration registration = dsdParserUtils.getRegistration(siteId, String.valueOf(articleId));
        registrationUtils.unRegisterUser(user, registration);
        return Response.accepted(JsonContentParserUtils.formatTextToJson("message","User un-registered for " + registration.getTitle())).type(MediaType.APPLICATION_JSON).build();
    }

    private RegistrationDetails getRegistrationDetails(Map<String, Object> record, Event event) {

        Long registrationId = (Long) record.get("resourcePrimaryKey");
        Registration registration = event.getRegistration(registrationId);
        if (registration == null){
            return null;
        }
        RegistrationDetails details = new RegistrationDetails();
        details.setEventArticleId(event.getArticleId());
        details.setArticleId(registration.getArticleId());
        details.setGroupId(registration.getGroupId());
        details.setTitle(registration.getTitle());
        details.setUserId((Long)record.get("userId"));
        details.setPreferences((String)record.get("userPreferences"));
        return details;
    }

}