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
import nl.deltares.portal.utils.DsdTransferUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static nl.deltares.services.utils.Helper.getRemoteUser;

/**
 * @author rooij_e
 */
@Path("/transfer")
public class TransferRegistrationService {

    private static final Log LOG = LogFactoryUtil.getLog(TransferRegistrationService.class);
    private final DsdTransferUtils registrationUtils;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TransferRegistrationService(DsdTransferUtils dsdTransferUtils) {
        this.registrationUtils = dsdTransferUtils;
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
        Registration registration = getRegistrationArticle(siteId, articleId);

        //Un register user for any earlier registration
        registrationUtils.unRegisterUser(user, registration);

        try {
            for (String transferDate : transferDates) {
                registrationUtils.registerUser(user, registration, dateFormat.parse(transferDate));
            }
        } catch (ValidationException e) {
            String msg = "Validation Exception: " + e.getMessage();
            LOG.warn(msg);
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        } catch (ParseException e){
            String msg = "Error parsing transfer date: " + e.getMessage();
            return Response.serverError().entity(msg).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.accepted("User registered for bus transfer " + registration.getTitle()).type(MediaType.APPLICATION_JSON).build();
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

}