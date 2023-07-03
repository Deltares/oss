package nl.deltares.services.rest.download;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.portal.utils.JsonContentUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;

public class DownloadRestService {

    private static final Log LOG = LogFactoryUtil.getLog(DownloadRestService.class);
    private final DownloadUtils downloadUtils;
    private final GeoIpUtils geoIpUtils;

    public DownloadRestService(DownloadUtils downloadUtils, GeoIpUtils geoIpUtils) {
        this.downloadUtils = downloadUtils;
        this.geoIpUtils = geoIpUtils;
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerDownload(@Context HttpServletRequest request, String json) {

        final String remoteUser = request.getRemoteUser();
        final User user;
        if (remoteUser != null && !remoteUser.isEmpty()) {
            try {
                user = UserLocalServiceUtil.getUser(Long.parseLong(remoteUser));
            } catch (PortalException e) {
                return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
            }
        } else {
            user = null;
        }

        String fileShare;
        String fileName;
        long downloadId;
        long groupId;
        try {
            final Map<String, String> jsonToMap = JsonContentUtils.parseJsonToMap(json);
            fileName = jsonToMap.get("fileName");
            fileShare = jsonToMap.get("fileShare");
            downloadId = Long.parseLong(jsonToMap.get("downloadId"));
            groupId = Long.parseLong(jsonToMap.get("groupId"));
        } catch (Exception e) {
            LOG.warn(String.format("Error parsing json request '%s': %s", json, e.getMessage()));
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Failed to parse request parameter: " + e.getMessage()).build();
        }

        try {
            downloadUtils.registerDownload(user, groupId, downloadId, fileName, fileShare, parseGeoLocationFromIp(request));
            return Response.ok().entity(String.format("Download registered for file '%s':  '%s'", fileName, fileShare)).build();
        } catch (PortalException e) {
            LOG.warn("Error registering direct download url: " + e.getMessage());
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Failed register download: " + e.getMessage()).build();
        }

    }

    private Map<String, String> parseGeoLocationFromIp(HttpServletRequest request) {
        if (geoIpUtils == null || !geoIpUtils.isActive()) return Collections.emptyMap();
        final String remoteAddr = request.getRemoteAddr();
        try {
            return geoIpUtils.getClientIpInfo(remoteAddr);
        } catch (Exception e) {
            LOG.warn("Error getting country info: " + e.getMessage());
        }
        return Collections.emptyMap();

    }

}
