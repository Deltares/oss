package nl.deltares.services.rest.registration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.services.rest.exception.LiferayRestException;
import nl.deltares.services.rest.registration.modules.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
    public Response getUserAttributes(@Context HttpServletRequest request) throws Exception {

        User user = getRemoteUser(request);
        UserDetails userDetails = getUserDetails(user);

        return toResponse(userDetails);
    }

    @POST
    @Path("/info")
    @Consumes("application/json")
    public void setUserAttributes(@Context HttpServletRequest request, UserDetails userDetails) throws Exception {
        User user = getRemoteUser(request);
        updateUserAttributes(userDetails, user);
    }

    private UserDetails getUserDetails(User user) throws LiferayRestException {
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
        } catch (IOException e) {
            String msg = String.format("Error updating user attributes for user %s: %s", user.getEmailAddresses(), e.getMessage());
            LOG.warn(msg);
            throw new LiferayRestException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), msg);
        }
    }

    private Map<String, String> loadUserAttributes(User user) throws LiferayRestException {
        if (!keycloakUtils.isActive()) return Collections.emptyMap();
        try {
            return keycloakUtils.getUserAttributes(user.getEmailAddress());
        } catch (IOException e) {
            String msg = String.format("Error retrieving user attributes for user %s: %s", user.getEmailAddress(), e.getMessage());
            LOG.warn(msg);
            throw new LiferayRestException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), msg);
        }
    }

}