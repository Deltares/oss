package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Component(
        immediate = true,
        service = KeycloakUtils.class
)
public class KeycloakUtilsImpl  extends HttpClientUtils implements KeycloakUtils {

    private static final Log LOG = LogFactoryUtil.getLog(KeycloakUtilsImpl.class);

    private static final String LOGIN_LOGIN_COUNT = "login.login-count";
    private static final String LOGIN_FIRST_LOGIN_DATE = "login.first-login-date";
    private static final String LOGIN_RECENT_LOGIN_DATE = "login.recent-login-date";

    private static final String BASEURL_KEY = "keycloak.baseurl";

    private static String basePath;
    private String baseApiPath;

    @Override
    public boolean isActive() {
        return PropsUtil.contains(BASEURL_KEY);
    }

    @Override
    public String getAdminAvatarPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "avatar-provider/admin";
    }

    @Override
    public String getAvatarPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "avatar-provider";
    }

    @Override
    public String getUserMailingPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "user-mailings/mailings-page";
    }

    @Override
    public String getAccountPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "account";
    }

    @Override
    public byte[] getUserAvatar(String email) throws Exception {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/octet-stream");
        headers.put("Authorization", "Bearer " + getAccessToken());

        //open connection
        HttpURLConnection connection = getConnection(getAdminAvatarPath() + "?email=" + email, "GET", headers);

        //get response
        checkResponse(connection);
        return readAllBytes(connection.getInputStream());
    }

    @Override
    public Map<String, String> getUserAttributes(String email) throws Exception {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + "?email=" + email, "GET", headers);

        checkResponse(connection);
        String jsonResponse = readAll(connection.getInputStream());
        return parseUserAttributes(jsonResponse);
    }

    private JSONObject getJsonObject(String jsonUserArray) throws IOException {
        JSONArray jsonUsers;
        try {
            jsonUsers = JSONFactoryUtil.createJSONArray(jsonUserArray);
        } catch (JSONException e) {
            throw new IOException("Error parsing json response: " + e.getMessage());
        }
        if (jsonUsers.length() == 0) return null;
        return jsonUsers.getJSONObject(0);
    }

    private void fromAttributes(JSONObject jsonUser, Map<String, String> attributes) {
        JSONObject jsonAttributes = jsonUser.getJSONObject("attributes");
        if (jsonAttributes == null) {
            jsonAttributes = JSONFactoryUtil.createJSONObject();
            jsonUser.put("attributes", jsonAttributes);
        }
        for (String key : attributes.keySet()) {
            String value = attributes.get(key);
            jsonAttributes.put(key, JSONFactoryUtil.createJSONArray().put(value));
        }
    }

    private Map<String, String> parseUserAttributes(String jsonResponse) throws JSONException {

        //Keycloak wraps all attributes in a json array. we need to remove this
        List<Map<String, String>> userMapArray = JsonContentUtils.parseJsonArrayToMap(jsonResponse);
        if (userMapArray.size() == 0) return new HashMap<>();
        Map<String, String> userMap = userMapArray.get(0);
        String attributesJson = userMap.get("attributes");
        Map<String, String> attributes = JsonContentUtils.parseJsonToMap(attributesJson);
        HashMap<String, String> filteredAttributes = new HashMap<>();
        for (ATTRIBUTES attributeKey : ATTRIBUTES.values()) {
            String key = attributeKey.name();
            if (!attributes.containsKey(key)) continue;
            filteredAttributes.put(key, JsonContentUtils.parseJsonArrayToValue(attributes.get(key)));
        }
        return filteredAttributes;
    }

    @Override
    public int updateUserAttributes(String email, Map<String, String> attributes) throws Exception {
        //get user representation
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + "?email=" + email, "GET", headers);
        checkResponse(connection);

        String jsonResponse = readAll(connection.getInputStream());
        //Keycloak wraps all attributes in a json array. we need to remove this
        JSONObject userObject = getJsonObject(jsonResponse);
        if (userObject == null) return -1;
        fromAttributes(userObject, attributes);

        //write updated user representation
        connection = getConnection(getKeycloakUsersPath() + '/' + userObject.get("id"), "PUT", headers);
        connection.setDoOutput(true);
        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            userObject.write(w);
        }
        //get response
        return checkResponse(connection);
    }

    public int registerUserLogin(String email, String siteId) throws Exception {
        if ( siteId == null || siteId.isEmpty()) return -1;
        Map<String, String> userAttributes = getUserAttributes(email);

        recordFirstLogin(siteId, userAttributes);
        recordLoginCount(siteId, userAttributes);
        recordRecentLogin(siteId, userAttributes);

        return updateUserAttributes(email, userAttributes);
    }

    private String getKeycloakUsersPath() {
        String basePath = getKeycloakBaseApiPath();
        return basePath + "users";
    }

    private String getKeycloakBasePath() {
        if (basePath != null) return basePath;
        if (!PropsUtil.contains(BASEURL_KEY)) {
            LOG.info(String.format("Missing property %s in portal-ext.properties file", BASEURL_KEY));
            return null;
        }
        basePath =  PropsUtil.get(BASEURL_KEY);

        if(basePath.endsWith("/")){
            return basePath;
        }
        basePath += '/';
        return basePath;
    }

    private String getKeycloakBaseApiPath() {
        if (baseApiPath != null) return baseApiPath;
        String basePath = getKeycloakBasePath();
        if (basePath == null) return null;
        baseApiPath = basePath.replace("auth/realms", "auth/admin/realms");
        return baseApiPath;
    }

    private String getTokenPath(){
        String basePath = getKeycloakBasePath();
        return basePath + "protocol/openid-connect/token";
    }

    private String getAccessToken(){

        String CACHED_TOKEN_KEY = "keycloak.token";
        String CACHED_EXPIRY_KEY = "keycloak.expirytime";
        String token = getCachedToken(CACHED_TOKEN_KEY, CACHED_EXPIRY_KEY);
        if (token != null) return token;

        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        try {
            HttpURLConnection connection = getConnection(getTokenPath(), "POST", headers);
            writeOauthPostParameters(connection, getOAuthParameters());
            String jsonResponse = readAll(connection.getInputStream());
            Map<String, String> parsedToken = JsonContentUtils.parseJsonToMap(jsonResponse);

            cacheAccessToken(CACHED_TOKEN_KEY, CACHED_EXPIRY_KEY, parsedToken);
            return parsedToken.get("access_token");
        } catch (IOException | JSONException e){
            LOG.error("Failed to get access token: " + e.getMessage());
        }

        return null;
    }


    private Map<String, String> getOAuthParameters() {
        Map<String,String> pathParameters = new HashMap<>();
        pathParameters.put("grant_type", "client_credentials");
        pathParameters.put("client_id", getProperty("keycloak.clientid"));
        pathParameters.put("client_secret", getProperty("keycloak.clientsecret"));
        return pathParameters;
    }

    private void recordLoginCount(String referrer, Map<String, String> attributes) {
        String key = referrer == null ? LOGIN_LOGIN_COUNT : LOGIN_LOGIN_COUNT + '.' + referrer;
        String count = attributes.getOrDefault(key, "0");
        int nr = Integer.parseInt(count);
        nr++;
        attributes.put(key, String.valueOf(nr));
    }

    private void recordRecentLogin(String referrer, Map<String, String> attributes) {
        String key = referrer == null ? LOGIN_RECENT_LOGIN_DATE : LOGIN_RECENT_LOGIN_DATE + '.' + referrer;
        attributes.put(key, now(ZoneId.of("GMT")).toString());
    }

    private void recordFirstLogin(String referrer, Map<String, String> attributes) {
        String key = referrer == null ? LOGIN_FIRST_LOGIN_DATE : LOGIN_FIRST_LOGIN_DATE + '.' + referrer;

        if (!attributes.containsKey(key)) {
            attributes.put(key, now(ZoneId.of("GMT")).toString());
        }
    }
}
