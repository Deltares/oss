package nl.deltares.services.rest.download;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.portal.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static nl.deltares.portal.utils.HttpClientUtils.getConnection;

public class DownloadRestService {

    private static final Log LOG = LogFactoryUtil.getLog(DownloadRestService.class);
    private final DownloadUtils downloadUtils;
    private final SanctionCheckUtils sanctionCheckUtils;
    private final GeoIpUtils geoIpUtils;

    public DownloadRestService(DownloadUtils downloadUtils, SanctionCheckUtils sanctionCheckUtils, GeoIpUtils geoIpUtils) {
        this.downloadUtils = downloadUtils;
        this.sanctionCheckUtils = sanctionCheckUtils;
        this.geoIpUtils = geoIpUtils;
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

        try {
            doSanctionCheck(request, user.getEmailAddress());
        } catch (IOException e) {
            return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }

        final String fileId;
        String filePath;
        long downloadId;
        long groupId;
        try {
            final Map<String, String> jsonToMap = JsonContentUtils.parseJsonToMap(json);
            fileId = jsonToMap.get("fileId");
            filePath = jsonToMap.get("filePath");
            downloadId = Long.parseLong(jsonToMap.get("downloadId"));
            groupId = Long.parseLong(jsonToMap.get("groupId"));
            if (fileId == null || fileId.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity("Missing parameter 'fileId'").build();
            }
            if (filePath == null) {
                filePath = fileId;
            }
        } catch (Exception e) {
            LOG.warn(String.format("Error parsing json request '%s': %s", json, e.getMessage()));
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Failed to parse request parameter: " + e.getMessage()).build();
        }

        setStatusToProcessing(user, groupId, downloadId, filePath);

        final HttpURLConnection connection;
        String directDownloadLink = null;
        try {
            directDownloadLink = getDirectDownloadLink(Long.parseLong(fileId), downloadId, user.getUserId(), groupId);
            connection = getConnection(directDownloadLink, "GET", Collections.emptyMap());

            StreamingOutput stream = os -> HttpClientUtils.stream(connection.getInputStream(), os);
            return Response.ok(stream, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filepath=\"" + filePath + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, connection.getContentLength())
                    .build();

        } catch (IOException e) {
            final String msg = String.format("Failed to download file %s : %s",
                    filePath, e.getMessage());
            LOG.warn(msg);
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity(msg).build();
        } finally {
            try {
                downloadUtils.registerDownload(user, groupId, downloadId, filePath, directDownloadLink, Collections.emptyMap());
            } catch (PortalException e) {
                LOG.warn("Error registering direct download url: " + e.getMessage());
            }
        }

    }

    private void doSanctionCheck(HttpServletRequest request, String emailAddress) throws IOException {
        if (geoIpUtils == null){
            LOG.warn("Could not perform sanction check as GeoIpUtils has not be initialized!");
            return;
        }
        final Map<String, String> clientIpInfo = geoIpUtils.getClientIpInfo(request.getRemoteAddr());
        final boolean sanctioned = sanctionCheckUtils.isSanctioned(geoIpUtils.getCountryIso2Code(clientIpInfo));

        if (sanctioned){
            LOG.warn(String.format("%s: Invalid sanction check for user %s and country %s ",
                    DownloadRestService.class.getSimpleName(), emailAddress, clientIpInfo.get("country_name")));
            throw new IOException(String.format(
                    "Users from %s are not sanctioned to download Deltares software.", clientIpInfo.get("country_name")
            ));
        }
        LOG.info(String.format("%s: Valid sanction check for user %s and country %s ",
                DownloadRestService.class.getSimpleName(), emailAddress, clientIpInfo.get("country_name")));
    }

    private void setStatusToProcessing(User user, long groupId, long downloadId, String filePath) {
        try {
            final Map<String, String> shareInfo = new HashMap<>();
            shareInfo.put("id", "-9");
            downloadUtils.registerDownload(user, groupId, downloadId, filePath, shareInfo, Collections.emptyMap());
        } catch (PortalException e) {
            LOG.warn("Error registering direct download url: " + e.getMessage());
        }
    }

    private String getDirectDownloadLink(long fileId, long downloadId, long userId, long groupId) throws IOException{

        String directDownloadLink;
        try {
            directDownloadLink = downloadUtils.directDownloadExists(downloadId, userId, groupId);
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

        try {
            doSanctionCheck(request, user.getEmailAddress());
        } catch (IOException e) {
            return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }

        final String filePath;
        final long downloadId;
        final long groupId;
        final Map<String, String> jsonToMap;
        try {
             jsonToMap = JsonContentUtils.parseJsonToMap(json);
        } catch (JSONException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        filePath = jsonToMap.get("filePath");
        downloadId = Long.parseLong(jsonToMap.get("downloadId"));
        groupId = Long.parseLong(jsonToMap.get("groupId"));
        final boolean resendLink = Boolean.parseBoolean(jsonToMap.get("resendLink"));
        if (filePath == null || filePath.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing parameter 'filePath'").build();
        }

        setStatusToProcessing(user, groupId, downloadId, filePath);

        Map<String, String> shareInfo = null;
        try {
             shareInfo = downloadUtils.shareLinkExists(filePath, user.getEmailAddress());
            if (!shareInfo.isEmpty()) {
                if (resendLink) {
                    shareInfo = downloadUtils.resendShareLink(Integer.parseInt(shareInfo.get("id")));
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Share link has already been sent to user " + user.getEmailAddress()).build();
                }
            } else {
                shareInfo = downloadUtils.sendShareLink(filePath, user.getEmailAddress());
            }
            return Response.ok().entity(String.format("Share link for '%s' created and sent to '%s'", filePath, user.getEmailAddress())).build();
        } catch (Exception e) {
            final String msg = "Error sending share link: " + e.getMessage();
            LOG.warn(msg);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build();
        } finally {
            try {
                downloadUtils.registerDownload(user, groupId, downloadId, filePath,
                        shareInfo == null? Collections.emptyMap() : shareInfo, Collections.emptyMap());
            } catch (PortalException e) {
                LOG.warn("Error registering share link: " + e.getMessage());
            }
        }

    }
}
