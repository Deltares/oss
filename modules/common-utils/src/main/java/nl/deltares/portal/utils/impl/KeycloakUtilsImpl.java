package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
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
import java.util.ArrayList;
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
    private final boolean CACHE_TOKEN;

    public KeycloakUtilsImpl() {
        CACHE_TOKEN = Boolean.parseBoolean(PropsUtil.get("keycloak.cache.token"));
    }

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
    public String getAdminUserMailingsPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "user-mailings/admin";
    }

    public String getAdminUsersPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "users-deltares";
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
        return readAllBytes(connection);
    }

    @Override
    public Map<String, String> getUserAttributes(String email) throws Exception {

        Map<String, String> unfiltered = getRawUserAttributes(email);
        HashMap<String, String> filteredAttributes = new HashMap<>();
        for (ATTRIBUTES attributeKey : ATTRIBUTES.values()) {
            String key = attributeKey.name();
            if (!unfiltered.containsKey(key)) continue;
            filteredAttributes.put(key, JsonContentUtils.parseJsonArrayToValue(unfiltered.get(key)));
        }
        return filteredAttributes;
    }

    private Map<String, String> getRawUserAttributes(String email) throws IOException, JSONException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + "?email=" + email, "GET", headers);

        checkResponse(connection);
        String jsonResponse = readAll(connection);
        return parseUserAttributes(jsonResponse);
    }

    private Map<String, String> getUserAttributesFromCacheOrKeycloak(String email, String[] searchKeys) throws Exception{
        Map<String, String> unfiltered = getRawUserAttributes(email);
        HashMap<String, String> filteredAttributes = new HashMap<>();
        for (String key : searchKeys) {
            if (!unfiltered.containsKey(key)) continue;
            filteredAttributes.put(key, JsonContentUtils.parseJsonArrayToValue(unfiltered.get(key)));
        }
        return filteredAttributes;
    }

    private JSONArray getJsonObjects(String jsonUserArray) throws IOException {
        JSONArray jsonUsers;
        try {
            jsonUsers = JSONFactoryUtil.createJSONArray(jsonUserArray);
        } catch (JSONException e) {
            throw new IOException("Error parsing json response: " + e.getMessage());
        }
        return jsonUsers;
    }

    private JSONObject getJsonObject(String jsonUserArray) throws IOException {
        JSONArray jsonUsers = getJsonObjects(jsonUserArray);
        if (jsonUsers.length() == 0) return null;
        return jsonUsers.getJSONObject(0);
    }

    private void fromAttributes(JSONObject jsonUser, Map<String, String> attributes) {
        JSONObject jsonAttributes = jsonUser.getJSONObject("attributes");
        if (jsonAttributes == null) {
            jsonAttributes = JSONFactoryUtil.createJSONObject();
            jsonUser.put("attributes", jsonAttributes);
        }
        for (ATTRIBUTES key : ATTRIBUTES.values()) {
            final String value = attributes.get(key.name());
            if (Validator.isNotNull(value)){
                jsonAttributes.put(key.name(), JSONFactoryUtil.createJSONArray().put(value));
            }
        }
    }

    private Map<String, String> parseUserAttributes(String jsonResponse) throws JSONException {

        //Keycloak wraps all attributes in a json array. we need to remove this
        List<Map<String, String>> userMapArray = JsonContentUtils.parseJsonArrayToMap(jsonResponse);
        if (userMapArray.size() == 0) return new HashMap<>();
        Map<String, String> userMap = userMapArray.get(0);
        String attributesJson = userMap.get("attributes");
        return JsonContentUtils.parseJsonToMap(attributesJson);
    }

    @Override
    public int updateUserAttributes(String email, Map<String, String> attributes) throws Exception {
        //get user representation
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + "?email=" + email, "GET", headers);
        checkResponse(connection);

        String jsonResponse = readAll(connection);
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

    @Override
    public void deleteUser(String email) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + "?email=" + email, "GET", headers);

        checkResponse(connection);
        String jsonResponse = readAll(connection);
        //Keycloak wraps all attributes in a json array. we need to remove this
        JSONObject userObject = getJsonObject(jsonResponse);
        if (userObject == null) return;

        //delete user
        connection = getConnection(getKeycloakUsersPath() + '/' + userObject.get("id"), "DELETE", headers);
        //get response
        checkResponse(connection);

    }

    @Override
    public void subscribe(String email, String mailingId) throws Exception {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getAdminUserMailingsPath() + "/subscriptions/" + mailingId + "?email=" + email, "PUT", headers);
        checkResponse(connection);
    }

    @Override
    public void unsubscribe(String email, String mailingId) throws Exception {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getAdminUserMailingsPath() + "/subscriptions/" + mailingId + "?email=" + email, "DELETE", headers);
        checkResponse(connection);
    }

    @Override
    public boolean isSubscribed(String email, List<String> mailingIds) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getAdminUserMailingsPath() + "/subscriptions?email=" + email, "GET", headers);
        checkResponse(connection);
        String jsonResponse = readAll(connection);
        //Keycloak wraps all attributes in a json array. we need to remove this
        JSONArray subscriptions = getJsonObjects(jsonResponse);

        boolean[] isSubscribed = new boolean[]{false};
        subscriptions.forEach(jsonMailing -> {
            if (mailingIds.contains(((JSONObject)jsonMailing).getString("mailingId"))){
                isSubscribed[0] = true;
            }
        });
        return isSubscribed[0];
    }

    @Override
    public int registerUserLogin(String email, String siteId) throws Exception {
        if (siteId == null || siteId.isEmpty()) return -1;

        String[] searchKeys = {
                LOGIN_LOGIN_COUNT + '.' + siteId,
                LOGIN_FIRST_LOGIN_DATE + '.' + siteId,
                LOGIN_RECENT_LOGIN_DATE + '.' + siteId
        };

        Map<String, String> userAttributes = getUserAttributesFromCacheOrKeycloak(email, searchKeys);

        recordLoginCount(searchKeys[0], userAttributes);
        recordFirstLogin(searchKeys[1], userAttributes);
        recordRecentLogin(searchKeys[2], userAttributes);

        return updateUserAttributes(email, userAttributes);
    }

    @Override
    public List<String> getDisabledUsers(int start, int maxResults, Long after, Long before) throws IOException {

        String queryParams = String.format("?first=%d&max=%d&briefRepresentation=true", start, maxResults);
        if (after != null){
            queryParams += "&disabledTimeAfter=" + after;
        }
        if (before != null){
            queryParams += "&disabledTimeBefore=" + before;
        }
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection urlConnection = getConnection(getAdminUsersPath() + "/disabled" + queryParams, "GET", headers);
        checkResponse(urlConnection);
        String jsonResponse = readAll(urlConnection);
        //Keycloak wraps all attributes in a json array. we need to remove this
        JSONArray jsonUsers = getJsonObjects(jsonResponse);
        final ArrayList<String> emails = new ArrayList<>(jsonUsers.length());
        jsonUsers.forEach(jsonUser -> {
            emails.add(((JSONObject)jsonUser).getString("email"));
        });
        return emails;
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
        String token = CACHE_TOKEN ? getCachedToken(CACHED_TOKEN_KEY, CACHED_EXPIRY_KEY) : null;
        if (token != null) return token;

        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        try {
            HttpURLConnection connection = getConnection(getTokenPath(), "POST", headers);
            writePostParameters(connection, getOAuthParameters());
            checkResponse(connection);
            String jsonResponse = readAll(connection);
            Map<String, String> parsedToken = JsonContentUtils.parseJsonToMap(jsonResponse);

            if (CACHE_TOKEN) {
                cacheAccessTokens("keycloak", parsedToken);
            }
            return parsedToken.get("access_token");
        } catch (IOException | JSONException e){
            clearAccessTokens("keycloak");
            LOG.error("Failed to get access token: " + e.getMessage());
        }

        return null;
    }

    private Map<String, String> getOAuthParameters() {
        Map<String,String> pathParameters = new HashMap<>();
        pathParameters.put("client_id", getProperty("keycloak.clientid"));
        pathParameters.put("client_secret", getProperty("keycloak.clientsecret"));

        final String refreshToken = getCachedToken("keycloak.refresh.token", "keycloak.refresh.expirytime");
        if (refreshToken != null){
            pathParameters.put("grant_type", "refresh_token"); // use refresh token to close previous session
            pathParameters.put("refresh_token", refreshToken); // use refresh token to close previous session
        } else {
            pathParameters.put("grant_type", "client_credentials");
        }
        return pathParameters;
    }

    private void recordLoginCount(String key, Map<String, String> attributes) {
        String count = attributes.getOrDefault(key, "0");
        int nr = Integer.parseInt(count);
        nr++;
        attributes.put(key, String.valueOf(nr));
    }

    private void recordRecentLogin(String key, Map<String, String> attributes) {
        attributes.put(key, now(ZoneId.of("GMT")).toString());
    }

    private void recordFirstLogin(String key, Map<String, String> attributes) {
        if (!attributes.containsKey(key)) {
            attributes.put(key, now(ZoneId.of("GMT")).toString());
        }
    }
}
