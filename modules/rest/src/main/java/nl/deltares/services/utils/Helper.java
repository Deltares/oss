package nl.deltares.services.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.services.rest.exception.LiferayRestException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Helper {

    private static final Log _log = LogFactoryUtil.getLog(Helper.class.getName());
    private static Properties APP_MESSAGES;
    /*
     * Load a resource file when the module is running in a bundled environment
     */
    private static Properties loadResourceBundled(String path) {
        final InputStream stream = Helper.class.getClassLoader().getResourceAsStream(path);

        Properties properties = new Properties();

        try {
            properties.load( stream );
        } catch (IOException e1) {
            _log.error("An error occurred when getting the properties file");
        }

        return properties;
    }

    public static User getRemoteUser(@Context HttpServletRequest request) throws LiferayRestException, PortalException {

        String userid = request.getRemoteUser();

        // By default, if the user has not been authenticated in the portal, the
        // Guest user is retrieved.
        // However, it is important to validate the case if it is null
        User user = null;
        if (userid != null) {
            user = UserLocalServiceUtil.getUser(Long.parseLong(userid));
        }
        if (user == null) {
            throw new LiferayRestException(Response.Status.UNAUTHORIZED.getStatusCode(),
                    (String) getProperties().get("userNotFound"));
        }
        return user;
    }

    public static PermissionChecker getPermissionCheck(User user) throws Exception {

        // Get permission checker to validate if a user can view the retrieved
        // articles
        PermissionChecker checker;
        checker = PermissionCheckerFactoryUtil.create(user);
        if (checker == null) {
            throw new LiferayRestException(Response.Status.UNAUTHORIZED.getStatusCode(),
                    (String) getProperties().get("userPermissionValidation"));
        }
        return checker;
    }

    private static Properties getProperties() {
        if (APP_MESSAGES == null) {
            APP_MESSAGES = loadResourceBundled("/configuration/LiferayRest.properties");
        }
        return APP_MESSAGES;
    }

    public static Response toResponse(List pojos) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        for (Object pojo : pojos) {
            arrayNode.addPOJO(pojo);
        }

        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, pojos);
            return Response.ok().entity(writer.toString()).build();
        } catch (IOException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public static Response toResponse(Object pojo) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.putPOJO("user", pojo);

        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, pojo);
            return Response.ok().entity(writer.toString()).build();
        } catch (IOException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public static Object getErrorResponseMessage(String prefix, Exception e) {
        final HashMap<String, String> responseMap = new HashMap<>();

        final String errorMessage;
        if(prefix != null){
            errorMessage = String.format("%s: %s", prefix, e.getMessage());
        } else {
            errorMessage = e.getMessage();
        }
        responseMap.put("errorMessage", errorMessage);
        return JsonContentUtils.formatMapToJson(responseMap);
    }
}
