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

import static nl.deltares.services.utils.Helper.getRemoteUser;
import static nl.deltares.services.utils.Helper.toResponse;

/**
 * @author rooij_e
 */
@Path("/registration")
public class UserInformationService {

    private static final Log LOG = LogFactoryUtil.getLog(UserInformationService.class);
    private final KeycloakUtils keycloakUtils;

    public UserInformationService(KeycloakUtils keycloakUtils) {
        this.keycloakUtils = keycloakUtils;
    }

    @GET
    @Path("/user")
    @Produces("application/json")
    public Response getUserAttributes(@Context HttpServletRequest request) throws Exception {

        User user = getRemoteUser(request);
        UserDetails userDetails = new UserDetails();
        userDetails.setScreenName(user.getScreenName());
        userDetails.setEmail(user.getEmailAddress());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        userDetails.setId(user.getUserId());

        loadUserAttributes(user, userDetails);

        return toResponse(userDetails);
    }

    @POST
    @Path("/")
    @Consumes("application/json")
    public void setUserAttributes(@Context HttpServletRequest request, UserDetails userDetails) throws Exception {
        User user = getRemoteUser(request);
        updateUserAttributes(userDetails, user);
    }

    private void updateUserAttributes(UserDetails userDetails, User user) throws LiferayRestException {
        if (!keycloakUtils.isActive()) return;
        try {
            keycloakUtils.updateUserAttributes(user.getEmailAddress(), userDetails.getAttributes());
        } catch (IOException e) {
            String msg = String.format("Error updating user attributes for user %s: %s", user.getEmailAddresses(), e.getMessage());
            LOG.warn(msg);
            throw new LiferayRestException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), msg);
        }
    }

    private void loadUserAttributes(User user, UserDetails userDetails) throws LiferayRestException {
        if (!keycloakUtils.isActive()) return;
        try {
            userDetails.getAttributes().putAll(keycloakUtils.getUserAttributes(user.getEmailAddress()));
        } catch (IOException e) {
            String msg = String.format("Error retrieving user attributes for user %s: %s", user.getEmailAddress(), e.getMessage());
            LOG.warn(msg);
            throw new LiferayRestException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), msg);
        }
    }

//    public String getUserRegistrations(@Context HttpServletRequest request) throws Exception {
//        User user = getRemoteUser(request);
//        // Get permission checker to validate if a user can view the retrieved
//        // articles
//        PermissionChecker checker = getPermissionCheck(user);
//
//        ObjectMapper mapper = new ObjectMapper();
//        ArrayNode articleArray = mapper.createArrayNode();

    // Get the articles created using the structure key
//        List<JournalArticle> articles = journalArticleLocalService.getArticles(groupId,
//                "SESSIONS", -1, -1, null);
//
//        for (JournalArticle article : articles) {
//            // Validates if the user has permission to view the article.
//            if (checker.hasPermission(article.getGroupId(), JournalArticle.class.getName(),
//                    article.getResourcePrimKey(), ActionKeys.VIEW)) {
//                articleArray.add(getContentFromArticle(article));
//            }
//        }

    // Generate the JSON representation
//        return mapper.writeValueAsString(articleArray);
//    }

//    /**
//     * Returns the Jackson object for the article, if any error ocurres then
//     * returns an empty object
//     *
//     * @param article
//     *            The article information
//     * @return The Jackson object
//     * @throws DocumentException
//     */
//    private ObjectNode getContentFromArticle(JournalArticle article) throws DocumentException {
//        Document document;
//        String root = "/root/dynamic-element";
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode contentNode = mapper.createObjectNode();
//
//        // Get the document (XML format)
//        document = SAXReaderUtil.read(article.getContent());
//
//        // Extract the article data
//        String strContent = document.valueOf(root + "[@name='content']").trim();
//
//        // Creates the Jackson JSON structure
//        contentNode.put("content", strContent);
//
//        return contentNode;
//    }

}