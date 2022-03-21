package nl.deltares.services.rest.download;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Map;

import static nl.deltares.portal.utils.HttpClientUtils.getConnection;

public class DownloadRestService {

    private final DownloadUtils downloadUtils;

    public DownloadRestService(DownloadUtils downloadUtils) {
        this.downloadUtils = downloadUtils;
    }

    @POST
    @Path("direct")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response directDownload(@Context HttpServletRequest request, String json) {

        final String remoteUser = request.getRemoteUser();
        try {
            final User user = UserLocalServiceUtil.getUser(Long.parseLong(remoteUser));
            if (!user.isActive() || user.isDefaultUser()) {
                return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity("User must be logged in, in order to use this service!").build();
            }
        } catch (PortalException e) {
            return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
        final String fileId;
        String fileName;
        try {
            final Map<String, String> jsonToMap = JsonContentUtils.parseJsonToMap(json);
            fileId = jsonToMap.get("fileId");
            fileName = jsonToMap.get("fileName");
            if (fileId == null || fileId.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity("Missing parameter 'fileId'").build();
            }
            if (fileName == null) {
                fileName = fileId;
            }
        } catch (Exception e) {
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Failed to parse request parameter: " + e.getMessage()).build();
        }

        final String directDownloadLink;
        try {
            directDownloadLink = downloadUtils.getDirectDownloadLink(Long.parseLong(fileId));
            if (directDownloadLink == null || directDownloadLink.isEmpty()) {
                return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Empty download link returned for file: " + fileId).build();
            }
        } catch (Exception e) {
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Failed to get downloadLink url: " + e.getMessage()).build();
        }

        final HttpURLConnection connection;
        try {
            connection = getConnection(directDownloadLink, "GET", Collections.emptyMap());

            StreamingOutput stream = os -> HttpClientUtils.stream(connection.getInputStream(), os);
            return Response.ok(stream, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, connection.getContentLength())
                    .build();

        } catch (IOException e) {
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity(String.format("Failed to download file for download link %s : %s",
                    directDownloadLink, e.getMessage())).build();
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
            } else {
                downloadUtils.sendShareLink(filePath, user.getEmailAddress());
            }
        } catch (JSONException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error sending share link: " + e.getMessage()).build();
        }
        return Response.ok().entity(String.format("Share link for '%s' created and sent to '%s'", filePath, user.getEmailAddress())).build();

    }
}
