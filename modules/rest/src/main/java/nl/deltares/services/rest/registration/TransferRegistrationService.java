package nl.deltares.services.rest.registration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdTransferUtils;
import nl.deltares.portal.utils.JsonContentUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static nl.deltares.services.utils.Helper.getRemoteUser;

/**
 * @author rooij_e
 */
@Path("/transfer")
public class TransferRegistrationService {

    private static final Log LOG = LogFactoryUtil.getLog(TransferRegistrationService.class);
    private final DsdTransferUtils registrationUtils;
    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final DsdParserUtils dsdParserUtils;

    public TransferRegistrationService(DsdTransferUtils dsdTransferUtils, DsdParserUtils dsdParserUtils) {
        this.registrationUtils = dsdTransferUtils;
        this.dsdParserUtils = dsdParserUtils;
    }

    @GET
    @Path("/register/{siteId}/{articleId}")
    @Consumes("application/json")
    public Response registrations(@Context HttpServletRequest request,
                               @PathParam("siteId") long siteId, @PathParam("articleId") long articleId) {

        User user;
        try {
            user = getRemoteUser(request);
        } catch (Exception e) {
            String msg = JsonContentUtils.formatTextToJson("message", "Error getting user: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }
        List<Date> registeredDays;
        try {
            Registration registration = dsdParserUtils.getRegistration(siteId, String.valueOf(articleId));
            registeredDays = registrationUtils.getRegisteredDays(user, registration);
        } catch (PortalException e) {
            String msg = JsonContentUtils.formatTextToJson("message",
                    String.format("Error retrieving registration for site %d and article %d: %s", siteId, articleId, e.getMessage()));
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }
        ArrayList<String> days = new ArrayList<>();
        registeredDays.forEach(date -> days.add(dayFormat.format(date)));
        return Response.ok(days).type(MediaType.APPLICATION_JSON).build();

    }

    /**
     * Registers user for transportation service. Any earlier registrations will be removed
     */
    @POST
    @Path("/register/{siteId}/{articleId}")
    @Consumes("application/json")
    public Response register(@Context HttpServletRequest request,
                         @PathParam("siteId") long siteId, @PathParam("articleId") long articleId,
                             List<String> transferDates)  {

        User user;
        try {
            user = getRemoteUser(request);
        } catch (Exception e) {
            String msg = JsonContentUtils.formatTextToJson("message", "Error getting user: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }
        Registration registration ;
        try {
            registration = dsdParserUtils.getRegistration(siteId, String.valueOf(articleId));
        } catch (PortalException e) {
            String msg = JsonContentUtils.formatTextToJson("message",
                    String.format("Error retrieving registration for site %d and article %d: %s", siteId, articleId, e.getMessage()));
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }

        //Un register user for any earlier registration
        registrationUtils.unRegisterUser(user, registration);

        try {
            for (String transferDate : transferDates) {
                registrationUtils.registerUser(user, registration, dayFormat.parse(transferDate));
            }
        } catch (Exception e) {
            String msg = JsonContentUtils.formatTextToJson("message", "Error registering user: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.accepted(
                JsonContentUtils.formatTextToJson("message",
                "User registered for bus transfer " + registration.getTitle() )).type(MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/register/{siteId}/{articleId}")
    @Consumes("application/json")
    public Response unRegister(@Context HttpServletRequest request,
                             @PathParam("siteId") long siteId, @PathParam("articleId") long articleId)  {
        User user;
        try {
            user = getRemoteUser(request);
        } catch (Exception e) {
            String msg = JsonContentUtils.formatTextToJson("message", "Error getting user: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }
        Registration registration;
        try {
            registration = dsdParserUtils.getRegistration(siteId, String.valueOf(articleId));
        } catch (PortalException e) {
            String msg = JsonContentUtils.formatTextToJson("message","Error getting registration: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }
        registrationUtils.unRegisterUser(user, registration);
        return Response.accepted(
                JsonContentUtils.formatTextToJson("message","User un-registered for " + registration.getTitle())).type(MediaType.APPLICATION_JSON).build();
    }

}