package nl.deltares.services.rest.download;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.services.utils.Helper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DownloadRestService {

    private static final Log LOG = LogFactoryUtil.getLog(DownloadRestService.class);
    private final DownloadUtils downloadUtils;
    private final GeoIpUtils geoIpUtils;
    private final KeycloakUtils keycloakUtils;

    public DownloadRestService(DownloadUtils downloadUtils, GeoIpUtils geoIpUtils, KeycloakUtils keycloakUtils) {
        this.downloadUtils = downloadUtils;
        this.geoIpUtils = geoIpUtils;
        this.keycloakUtils = keycloakUtils;
    }

    @POST
    @Path("createShareLink")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createShareLink(@Context HttpServletRequest request, String json) {
        final User user;
        try {
            user = Helper.getRemoteUser(request);
        } catch (Exception e) {
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(Helper.getErrorResponseMessage("Error extracting user for 'createShareLink': ", e)).build();
        }

        final String countryCode;
        final String filePath;
        final String fileName;
        final long downloadId;
        final long groupId;
        try {
            final Map<String,String> map = JsonContentUtils.parseJsonToMap(json);
            countryCode =  map.get("countryCode");
            filePath = map.get("filePath");
            fileName = map.get("fileName");
            downloadId = Long.parseLong(map.get("downloadId"));
            groupId = Long.parseLong(map.get("groupId"));
        } catch (JSONException e) {
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(Helper.getErrorResponseMessage("Error parsing POST data for 'createShareLink': ", e)).build();
        }
        final Map<String, String> shareInfo;
        try {
            if (downloadUtils.hasMultipleDownloadUrls()) {
                shareInfo = downloadUtils.createShareLink(countryCode, filePath, user.getEmailAddress(), false);
            } else {
                shareInfo = downloadUtils.createShareLink(filePath, user.getEmailAddress(), false);
            }
        } catch (Exception e) {
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(Helper.getErrorResponseMessage("Error creating share link: ", e)).build();
        }
        Map<String, String> userAttributes;
        try {
            userAttributes = getUserAttributes(user, request);
        } catch (Exception e) {
            LOG.warn("Error retrieving user attributes: " + e.getMessage());
            userAttributes = Collections.emptyMap();
        }
        try {
            downloadUtils.registerDownload(user, groupId, downloadId, fileName, shareInfo, userAttributes);
        } catch (Exception e) {
            LOG.warn("Error registering download: " + e.getMessage());
        }

        return Response.ok().type(MediaType.APPLICATION_JSON).entity(JsonContentUtils.formatMapToJson(shareInfo)).build();

    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerDownload(@Context HttpServletRequest request, String json) {

        final User user;
        try {
            user = Helper.getRemoteUser(request);
        } catch (Exception e) {
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(Helper.getErrorResponseMessage("Error extracting user for 'register': ", e)).build();
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
            if (downloadUtils == null) {
                LOG.warn("DownloadUtils is not configured!");
            } else {
                final Map<String, String> userAttributes = getUserAttributes(user, request);
                downloadUtils.registerDownload(user, groupId, downloadId, fileName, fileShare, userAttributes);
            }
            return Response.ok().entity(String.format("Download registered for file '%s':  '%s'", fileName, fileShare)).build();
        } catch (PortalException e) {
            LOG.warn("Error registering direct download url: " + e.getMessage());
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Failed register download: " + e.getMessage()).build();
        }

    }

    private Map<String, String> getUserAttributes(User user, HttpServletRequest request) {

        final Map<String, String> attributes = new HashMap<>(parseGeoLocationFromIp(request));

        if (user == null || user.isDefaultUser()) return attributes;

        try {
            attributes.putAll(keycloakUtils.getUserAttributes(user.getEmailAddress()));
        } catch (Exception e) {
            LOG.warn(String.format("Error getting user attributes for user %s: %s", user.getEmailAddress(), e.getMessage()));
        }
        return attributes;
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
