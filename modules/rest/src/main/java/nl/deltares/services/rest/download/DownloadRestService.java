package nl.deltares.services.rest.download;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.JsonContentUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class DownloadRestService {

    private static final Log LOG = LogFactoryUtil.getLog(DownloadRestService.class);
    private final DownloadUtils downloadUtils;

    public DownloadRestService(DownloadUtils downloadUtils) {
        this.downloadUtils = downloadUtils;
    }

    @GET
    @Path("direct/{fileId}")
    @Produces("text/plain")
    public Response directDownload(@Context HttpServletRequest request, @PathParam("fileId") String fileId) {
        final String remoteUser = request.getRemoteUser();

        try {
            final User user = UserLocalServiceUtil.getUser(Long.parseLong(remoteUser));
            if (!user.isActive() || user.isDefaultUser()) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User must be logged in, in order to use this service!").build();
            }
        } catch (PortalException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
        try {
            return Response.ok().entity(downloadUtils.getDirectDownloadLink(Long.parseLong(fileId))).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/sendlink")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendShareLink(@Context HttpServletRequest request, String json) {

        final String remoteUser = request.getRemoteUser();
        final User user;
        try {
            user = UserLocalServiceUtil.getUser(Long.parseLong(remoteUser));
            if (!user.isActive() || user.isDefaultUser()) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User must be logged in, in order to use this service!").build();
            }
        } catch (PortalException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }

        final String filePath;
        try {
            final Map<String, String> jsonToMap = JsonContentUtils.parseJsonToMap(json);
            filePath = jsonToMap.get("filePath");
            final boolean resendLink = Boolean.parseBoolean(jsonToMap.get("resendLink"));
            if (filePath == null || filePath.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Missing parameter 'filePath'").build();
            }
            final int existingLink = downloadUtils.shareLinkExists(filePath, user.getEmailAddress());
            if (existingLink != -1) {

                if (resendLink) {
                    downloadUtils.resendShareLink(existingLink);
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Share link has already been sent to user " + user.getEmailAddress()).build();
                }
            }

            downloadUtils.sendShareLink(filePath, user.getEmailAddress());
        } catch (JSONException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error sending share link: " + e.getMessage()).build();
        }
        return Response.ok().entity(String.format("Share link for '%s' created and sent to '%s'", filePath, user.getEmailAddress())).build();

    }
}
