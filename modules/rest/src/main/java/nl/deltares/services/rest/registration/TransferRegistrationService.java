package nl.deltares.services.rest.registration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdTransferUtils;
import nl.deltares.portal.utils.JsonContentParserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
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
                               @PathParam("siteId") long siteId, @PathParam("articleId") long articleId) throws Exception {

        User user = getRemoteUser(request);
        Registration registration = dsdParserUtils.getRegistration(siteId, String.valueOf(articleId));
        List<Date> registeredDays = registrationUtils.getRegisteredDays(user, registration);
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
                             List<String> transferDates) throws Exception {

        User user = getRemoteUser(request);
        Registration registration ;
        try {
            registration = dsdParserUtils.getRegistration(siteId, String.valueOf(articleId));
        } catch (PortalException e) {
            String msg = JsonContentParserUtils.formatTextToJson("message",
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
        } catch (ValidationException e) {
            String msg = JsonContentParserUtils.formatTextToJson("message", "Validation Exception: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        } catch (ParseException e){
            String msg = JsonContentParserUtils.formatTextToJson("message", "Error parsing transfer date: " + e.getMessage());
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.accepted(
                JsonContentParserUtils.formatTextToJson("message",
                "User registered for bus transfer " + registration.getTitle() )).type(MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/register/{siteId}/{articleId}")
    @Consumes("application/json")
    public Response unRegister(@Context HttpServletRequest request,
                             @PathParam("siteId") long siteId, @PathParam("articleId") long articleId) throws Exception {
        User user = getRemoteUser(request);
        Registration registration = dsdParserUtils.getRegistration(siteId, String.valueOf(articleId));
        registrationUtils.unRegisterUser(user, registration);
        return Response.accepted(
                JsonContentParserUtils.formatTextToJson("message","User un-registered for " + registration.getTitle())).type(MediaType.APPLICATION_JSON).build();
    }

}