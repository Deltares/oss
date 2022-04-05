package nl.deltares.services.rest.download;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

    private static final Log LOG = LogFactoryUtil.getLog(DownloadRestService.class);
    private final DownloadUtils downloadUtils;

    public DownloadRestService(DownloadUtils downloadUtils) {
        this.downloadUtils = downloadUtils;
    }

    @POST
    @Path("direct")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response directDownload(@Context HttpServletRequest request, String json) {

        final String remoteUser = request.getRemoteUser();
        final User user;
        try {
            user = UserLocalServiceUtil.getUser(Long.parseLong(remoteUser));
            if (!user.isActive() || user.isDefaultUser()) {
                return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity("User must be logged in, in order to use this service!").build();
            }
        } catch (PortalException e) {
            return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
        final String fileId;
        String fileName;
        long downloadId;
        try {
            final Map<String, String> jsonToMap = JsonContentUtils.parseJsonToMap(json);
            fileId = jsonToMap.get("fileId");
            fileName = jsonToMap.get("fileName");
            downloadId = Long.parseLong(jsonToMap.get("downloadId"));
            if (fileId == null || fileId.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity("Missing parameter 'fileId'").build();
            }
            if (fileName == null) {
                fileName = fileId;
            }
        } catch (Exception e) {
            LOG.warn(String.format("Error parsing json request '%s': %s", json, e.getMessage()));
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Failed to parse request parameter: " + e.getMessage()).build();
        }

        String directDownloadLink;
        try {
            directDownloadLink = getDirectDownloadLink(Long.parseLong(fileId), downloadId, user.getUserId());
        } catch (IOException e) {
            LOG.warn(e.getMessage());
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }

        try {
            downloadUtils.registerDownload(user, downloadId, directDownloadLink, Collections.emptyMap());
        } catch (PortalException e) {
            LOG.warn("Error registering direct download url: " + e.getMessage());
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

            final String msg = String.format("Failed to download file for download link %s : %s",
                    directDownloadLink, e.getMessage());
            LOG.warn(msg);
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity(msg).build();
        }

    }

    private String getDirectDownloadLink(long fileId, long downloadId, long userId) throws IOException{

        String directDownloadLink;
        try {
            directDownloadLink = downloadUtils.directDownloadExists(downloadId, userId);
            if (directDownloadLink != null) return directDownloadLink;
        } catch (Exception e) {
            LOG.warn("Error checking for existing download link: " + e.getMessage());
        }

        try {
            directDownloadLink = downloadUtils.getDirectDownloadLink(fileId);
            if (directDownloadLink == null || directDownloadLink.isEmpty()) {
                throw new IOException( "Empty download link returned for file: " + fileId);
            }
        } catch (Exception e) {
            throw new IOException( "Failed to get downloadLink url: " + e.getMessage());
        }
        return directDownloadLink;
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
        final long downloadId;
        final Map<String, String> jsonToMap;
        try {
             jsonToMap = JsonContentUtils.parseJsonToMap(json);
        } catch (JSONException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        filePath = jsonToMap.get("filePath");
        downloadId = Long.parseLong(jsonToMap.get("downloadId"));
        final boolean resendLink = Boolean.parseBoolean(jsonToMap.get("resendLink"));
        if (filePath == null || filePath.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing parameter 'filePath'").build();
        }
        final Map<String, Object> shareInfo;
        try {
             shareInfo = downloadUtils.shareLinkExists(filePath, user.getEmailAddress());
        } catch (Exception e) {
            final String msg = "Error checking if share link exists: " + e.getMessage();
            LOG.warn(msg);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build();
        }
        try {
            if (!shareInfo.isEmpty()) {
                if (resendLink) {
                    downloadUtils.resendShareLink((Integer) shareInfo.get("id"));
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Share link has already been sent to user " + user.getEmailAddress()).build();
                }
            } else {
                downloadUtils.sendShareLink(filePath, user.getEmailAddress());
            }

        } catch (Exception e) {
            final String msg = "Error sending share link: " + e.getMessage();
            LOG.warn(msg);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build();
        }

        try {
            downloadUtils.registerDownload(user, downloadId, shareInfo, Collections.emptyMap());
        } catch (PortalException e) {
            LOG.warn("Error registering share link: " + e.getMessage());
        }
        return Response.ok().entity(String.format("Share link for '%s' created and sent to '%s'", filePath, user.getEmailAddress())).build();

    }
}
