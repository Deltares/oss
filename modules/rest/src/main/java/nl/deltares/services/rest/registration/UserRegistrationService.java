package nl.deltares.services.rest.registration;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.services.rest.exception.LiferayRestException;
import nl.deltares.services.rest.registration.modules.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
public class UserRegistrationService {

    private static final Log LOG = LogFactoryUtil.getLog(UserRegistrationService.class);
    private final DsdSessionUtils registrationUtils;
    private final KeycloakUtils keycloakUtils;

    public UserRegistrationService(DsdSessionUtils registrationUtils, KeycloakUtils keycloakUtils) {
        this.registrationUtils = registrationUtils;
        this.keycloakUtils = keycloakUtils;
    }

    @POST
    @Path("/register/{siteId}/{articleId}")
    @Consumes("application/json")
    public Response register(@Context HttpServletRequest request,
                         @PathParam("siteId") long siteId, @PathParam("articleId") long articleId, String jsonProperties) throws Exception {

        User user = getRemoteUser(request);
        Registration registration = getRegistrationArticle(siteId, articleId);

        try {
            registrationUtils.registerUser(user, registration, jsonProperties);
        } catch (ValidationException e) {
            String msg = "Validation Exception: " + e.getMessage();
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.accepted("User registered for " + registration.getTitle()).type(MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/register/{siteId}/{articleId}")
    @Consumes("application/json")
    public Response unRegister(@Context HttpServletRequest request,
                             @PathParam("siteId") long siteId, @PathParam("articleId") long articleId) throws Exception {
        User user = getRemoteUser(request);

        Registration registration = getRegistrationArticle(siteId, articleId);
        registrationUtils.unRegisterUser(user, registration);
        return Response.accepted("User un-registered for " + registration.getTitle()).type(MediaType.APPLICATION_JSON).build();
    }

    private Registration getRegistrationArticle(long siteId, long articleId) throws PortalException {
        JournalArticle article = JournalArticleLocalServiceUtil.getLatestArticle(siteId, String.valueOf(articleId));
        AbsDsdArticle registration = AbsDsdArticle.getInstance(article);
        if (! (registration instanceof Registration)){
            String msg = "Invalid registration type! Expected instance of RegistrationArticle found: " +registration.getClass().getName();
            LOG.warn(msg);
            throw new PortalException(msg);
        }
        return (Registration)registration;
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