package nl.deltares.services.rest.registration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.services.rest.exception.LiferayRestException;
import nl.deltares.services.rest.registration.modules.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;

import static nl.deltares.services.utils.Helper.getRemoteUser;
import static nl.deltares.services.utils.Helper.toResponse;

/**
 * @author rooij_e
 */
@Path("/user")
public class UserInfoService {

    private static final Log LOG = LogFactoryUtil.getLog(UserInfoService.class);
    private final KeycloakUtils keycloakUtils;

    public UserInfoService(KeycloakUtils keycloakUtils) {
        this.keycloakUtils = keycloakUtils;
    }

    @GET
    @Path("/info")
    @Produces("application/json")
    public Response getUserAttributes(@Context HttpServletRequest request)  {

        User user;
        try {
            user = getRemoteUser(request);
        } catch (Exception e) {
            String msg = JsonContentParserUtils.formatTextToJson("message", "Error getting user: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }
        UserDetails userDetails = null;
        try {
            userDetails = getUserDetails(user);
        } catch (Exception e) {
            String msg = JsonContentParserUtils.formatTextToJson("message", "Error getting user details: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }

        return toResponse(userDetails);
    }

    @POST
    @Path("/info")
    @Consumes("application/json")
    public Response setUserAttributes(@Context HttpServletRequest request, UserDetails userDetails)  {
        User user;
        try {
            user = getRemoteUser(request);
        } catch (Exception e) {
            String msg = JsonContentParserUtils.formatTextToJson("message", "Error getting user: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }
        try {
            updateUserAttributes(userDetails, user);
        } catch (LiferayRestException e) {
            String msg = JsonContentParserUtils.formatTextToJson("message", "Error updating user attributes: " + e.getMessage());
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }
        return Response.ok().build();
    }

    private UserDetails getUserDetails(User user) throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setScreenName(user.getScreenName());
        userDetails.setEmail(user.getEmailAddress());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        userDetails.setId(user.getUserId());
        userDetails.getAttributes().putAll(loadUserAttributes(user));
        return userDetails;
    }

    private void updateUserAttributes(UserDetails userDetails, User user) throws LiferayRestException {
        if (!keycloakUtils.isActive()) {
            LOG.warn("Keycloak not active. Cannot update user attributes!");
            return;
        }
        try {
            keycloakUtils.updateUserAttributes(user.getEmailAddress(), userDetails.getAttributes());
        } catch (Exception e) {
            String msg = String.format("Error updating user attributes for user %s: %s", user.getEmailAddresses(), e.getMessage());
            LOG.warn(msg);
            throw new LiferayRestException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), msg);
        }
    }

    private Map<String, String> loadUserAttributes(User user) throws Exception {
        if (!keycloakUtils.isActive()) return Collections.emptyMap();
        return keycloakUtils.getUserAttributes(user.getEmailAddress());
    }

}