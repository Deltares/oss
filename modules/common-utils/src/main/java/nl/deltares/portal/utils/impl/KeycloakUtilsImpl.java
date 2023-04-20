package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.EmailSubscriptionUtils;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

import static java.time.LocalDateTime.now;

@Component(
        immediate = true,
        service = {KeycloakUtils.class,EmailSubscriptionUtils.class}
)
public class KeycloakUtilsImpl extends HttpClientUtils implements KeycloakUtils, EmailSubscriptionUtils {

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
    public boolean isDefault() {
        return false;
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

    @Override
    public String getAdminMailingsPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "mailing-provider/admin";
    }

    public String getAdminUsersPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "users-deltares";
    }

    @Override
    public int callCheckUsersExist(String requestId, PrintWriter nonExistingUsersOutput) throws Exception{
        HttpURLConnection urlConnection = getConnection(getAdminUsersPath() + "/check-users-exist/" + requestId, "GET", Collections.emptyMap());
        urlConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());

        // Read response from web server, which will trigger the multipart HTTP request to be sent.
        int status = checkResponse(urlConnection);
        writeResponse(nonExistingUsersOutput, urlConnection);
        if (Objects.equals(urlConnection.getContentType(), "application/json")){
            return 102; //running
        }
        return status;
    }

    @Override
    public int callCheckUsersExist(File checkUsersInputFile, PrintWriter nonExistingUsersOutput) throws Exception {
        String boundaryString = "----CheckUsersExist";
        HashMap<String, String> map = new HashMap<>();
        map.put("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        HttpURLConnection urlConnection = getConnection(getAdminUsersPath() + "/check-users-exist", "POST", map);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());

        OutputStream outputStream = urlConnection.getOutputStream();
        BufferedWriter httpRequestBodyWriter =
                new BufferedWriter(new OutputStreamWriter(outputStream));

        // Include the section to describe the file
        httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
        String fileName = checkUsersInputFile.getName();
        httpRequestBodyWriter.write("Content-Disposition: form-data;"
                + "name=\"data\";"
                + "filename=\"" + fileName + "\""
                + "\nContent-Type: application/octet-stream\n\n");
        httpRequestBodyWriter.flush();

        // Write the actual file contents
        FileInputStream inputStreamToLogFile = new FileInputStream(checkUsersInputFile);

        int bytesRead;
        byte[] dataBuffer = new byte[1024];
        while ((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
            outputStream.write(dataBuffer, 0, bytesRead);
        }

        outputStream.flush();

        // Mark the end of the multipart http request
        httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
        httpRequestBodyWriter.flush();

        // Close the streams
        outputStream.close();
        httpRequestBodyWriter.close();

        // Read response from web server, which will trigger the multipart HTTP request to be sent.
        int status = checkResponse(urlConnection);
        writeResponse(nonExistingUsersOutput, urlConnection);
        if (Objects.equals(urlConnection.getContentType(), "application/json")){
            return 102; //running
        }
        return status;
    }

    @Override
    public int downloadInvalidUsers(String requestId, PrintWriter writer) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection urlConnection;
        if (requestId == null) {
            urlConnection = getConnection(getAdminUsersPath() + "/invalid", "GET", headers);
        } else {
            urlConnection = getConnection(getAdminUsersPath() + "/invalid/" + requestId, "GET", headers);
        }
        int status = checkResponse(urlConnection);
        writeResponse(writer, urlConnection);
        if (Objects.equals(urlConnection.getContentType(), "application/json")){
            return 102; //running
        }
        return status;
    }

    private void writeResponse(PrintWriter writer, HttpURLConnection urlConnection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write('\n');
            }
            writer.flush();
        }
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
    public void deleteUserAvatar(String email) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getAccessToken());

        final Map<String, String> userRepresentation = getKeycloakUserRepresentation(email, null);
        //open connection
        HttpURLConnection connection = getConnection(getAdminAvatarPath() + '/' + userRepresentation.get("id"), "DELETE", headers);

        //get response
        checkResponse(connection);
    }

    @Override
    public void updateUserAvatar(String email, byte[] avatar, String fileName) throws Exception {
        String boundaryString = "----UploadAvatar";

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        headers.put("Authorization", "Bearer " + getAccessToken());


        final Map<String, String> userRepresentation = getKeycloakUserRepresentation(email, null);

        HttpURLConnection connection = getConnection(getAdminAvatarPath() + '/' + userRepresentation.get("id"), "POST", headers);
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        BufferedWriter httpRequestBodyWriter =
                new BufferedWriter(new OutputStreamWriter(outputStream));

        // Include the section to describe the file
        httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
        httpRequestBodyWriter.write("Content-Disposition: form-data;"
                + "name=\"image\";"
                + "filename=\"" + fileName + "\""
                + "\nContent-Type: " + URLConnection.guessContentTypeFromName(fileName) + "\n\n");
        httpRequestBodyWriter.flush();

        // Write the actual file contents
        InputStream inputStream = new ByteArrayInputStream(avatar);

        int bytesRead;
        byte[] dataBuffer = new byte[1024];
        while ((bytesRead = inputStream.read(dataBuffer)) != -1) {
            outputStream.write(dataBuffer, 0, bytesRead);
        }

        outputStream.flush();

        // Mark the end of the multipart http request
        httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
        httpRequestBodyWriter.flush();

        // Close the streams
        outputStream.close();
        httpRequestBodyWriter.close();

        //get response
        checkResponse(connection);
    }

    @Override
    public Map<String, String> getUserAttributes(String email) throws Exception {

        final Map<String, String> userRepresentation = getKeycloakUserRepresentation(email, null);
        String attributesJson = userRepresentation.get("attributes");
        Map<String, String> unfiltered = JsonContentUtils.parseJsonToMap(attributesJson);
        HashMap<String, String> filteredAttributes = new HashMap<>();
        for (ATTRIBUTES attributeKey : ATTRIBUTES.values()) {
            String key = attributeKey.name();
            if (!unfiltered.containsKey(key)) continue;
            filteredAttributes.put(key, JsonContentUtils.parseJsonArrayToValue(unfiltered.get(key)));
        }
        return filteredAttributes;
    }

    @Override
    public Map<String, String> getUserInfo(String email) throws Exception {

        Map<String, String> unfiltered = getKeycloakUserRepresentation(email, null);
        if (unfiltered.isEmpty()) return unfiltered;
        HashMap<String, String> filteredInfo = new HashMap<>();
        filteredInfo.put(ATTRIBUTES.first_name.name(), unfiltered.get("firstName"));
        filteredInfo.put(ATTRIBUTES.last_name.name(), unfiltered.get("lastName"));
        filteredInfo.put(ATTRIBUTES.email.name(), unfiltered.get("email"));
        filteredInfo.put("username", unfiltered.get("username"));
        filteredInfo.put("id", unfiltered.get("id"));
        return filteredInfo;
    }


    @Override
    public boolean isExistingUsername(String username) throws Exception {

        Map<String, String> unfiltered = getKeycloakUserRepresentation(null, username);
        return !unfiltered.isEmpty();
    }

    private Map<String, String> getKeycloakUserRepresentation(String email, String username) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        String query;
        if (email != null){
            query = "?email=" + email;
        } else if (username != null){
            query = "?username=" + username;
        } else {
            throw new IOException("Both email and username missing!");
        }
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + query, "GET", headers);

        checkResponse(connection);
        String jsonResponse = readAll(connection);
        List<Map<String, String>> userMapArray = JsonContentUtils.parseJsonArrayToMap(jsonResponse);
        if (userMapArray.size() == 0) return new HashMap<>();
        return userMapArray.get(0);
    }

    private Map<String, String> getUserAttributesFromCacheOrKeycloak(String email, String[] searchKeys) throws Exception{

        final Map<String, String> userRepresentation = getKeycloakUserRepresentation(email, null);
        String attributesJson = userRepresentation.get("attributes");
        Map<String, String> unfiltered = JsonContentUtils.parseJsonToMap(attributesJson);
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

    private void fromAttributes(JSONObject jsonUser, Map<String, String> attributes, boolean includeUserInfo) {
        JSONObject jsonAttributes = jsonUser.getJSONObject("attributes");
        if (jsonAttributes == null) {
            jsonAttributes = JSONFactoryUtil.createJSONObject();
            jsonUser.put("attributes", jsonAttributes);
        }
        for (ATTRIBUTES key : ATTRIBUTES.values()) {
            if (key == ATTRIBUTES.email) continue;
            if (key == ATTRIBUTES.first_name) continue;
            if (key == ATTRIBUTES.last_name) continue;
            final String value = attributes.get(key.name());
            if (Validator.isNotNull(value)){
                jsonAttributes.put(key.name(), JSONFactoryUtil.createJSONArray().put(value));
            }
        }
        //add terms
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (entry.getKey().startsWith("terms.")) {
                jsonAttributes.put(entry.getKey(), entry.getValue());
            }
        }

        if (includeUserInfo){
            final String email = attributes.get(ATTRIBUTES.email.name());
            if (Validator.isEmailAddress(email)) jsonUser.put("email", email);

            final String firstName = attributes.get(ATTRIBUTES.first_name.name());
            if (!Validator.isBlank(firstName)) jsonUser.put("firstName", firstName);

            final String lastName = attributes.get(ATTRIBUTES.last_name.name());
            if (!Validator.isBlank(lastName)) jsonUser.put("lastName", lastName);
        }
    }

    /**
     * Update user information, including name and email
     */
    @Override
    public int updateUserProfile(String email, Map<String, String> attributes) throws Exception {
        return updateKeycloakUser(email, attributes, true);
    }

    /**
     * Update user attributes, do not include name and email
     */
    @Override
    public int updateUserAttributes(String email, Map<String, String> attributes) throws Exception {
        return updateKeycloakUser(email, attributes, false);
    }

    private int updateKeycloakUser(String email, Map<String, String> attributes, boolean includeUserInfo) throws Exception {
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
        fromAttributes(userObject, attributes, includeUserInfo);

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
    public int deleteUserWithEmail(String email) throws Exception {
        //get user representation
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());

        //Keycloak wraps all attributes in a json array. we need to remove this
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + "?briefRepresentation=true&email=" + email, "GET", headers);
        checkResponse(connection);

        String jsonResponse = readAll(connection);
        //Keycloak wraps all attributes in a json array. we need to remove this
        JSONObject userObject = getJsonObject(jsonResponse);
        if (userObject == null) return -1;

        final Object id = userObject.get("id");

        //write updated user representation
        connection = getConnection(getKeycloakUsersPath() + '/' + id, "DELETE", headers);
        //get response
        return checkResponse(connection);

    }

    @Override
    public int deleteUserWithId(String id) throws Exception {

        //get user representation
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());

        //Keycloak wraps all attributes in a json array. we need to remove this
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + "/" + id, "DELETE", headers);
        return checkResponse(connection);
    }

    @Override
    public void subscribe(User user, String mailingId) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getAdminUserMailingsPath() + "/subscriptions/" + mailingId +
                "?email=" + user.getEmailAddress() + "&delivery=e-mail&language=en", "PUT", headers);
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
        for (int i = 0; i < subscriptions.length(); i++) {
            if (mailingIds.contains(subscriptions.getJSONObject(i).getString("mailingId"))){
                isSubscribed[0] = true;
            }
        }

        return isSubscribed[0];
    }

    @Override
    public List<SubscriptionSelection> getSubscriptions(String emailAddress) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getAdminMailingsPath(), "GET", headers);
        checkResponse(connection);
        String jsonResponse = readAll(connection);
        //Keycloak wraps all attributes in a json array. we need to remove this
        JSONArray mailings = getJsonObjects(jsonResponse);

        final ArrayList<SubscriptionSelection> allSubscriptions = new ArrayList<>();
        for (int i = 0; i < mailings.length(); i++) {
            final JSONObject jsonMailing = mailings.getJSONObject(i);
            allSubscriptions.add(
                    new SubscriptionSelection(jsonMailing.getString("id"), jsonMailing.getString("name")));
        }
        if (emailAddress != null) setUserSubscriptionSelection(emailAddress, allSubscriptions);

        return allSubscriptions;
    }


    private void setUserSubscriptionSelection(String email, List<SubscriptionSelection> allSubscriptions) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        HttpURLConnection connection = getConnection(getAdminUserMailingsPath() + "/subscriptions?email=" + email, "GET", headers);
        checkResponse(connection);
        String jsonResponse = readAll(connection);
        //Keycloak wraps all attributes in a json array. we need to remove this
        JSONArray mailings = getJsonObjects(jsonResponse);

        for (int i = 0; i < mailings.length(); i++) {
            final String subscriptionId = mailings.getJSONObject(i).getString("mailingId");
            for (SubscriptionSelection subscription : allSubscriptions) {
                final boolean found = subscription.getId().equals(subscriptionId);
                if (found) {
                    subscription.setSelected(true);
                    break;
                }
            }
        }

    }

    @Override
    public boolean isSubscribed(String email, String subscriptionId) throws Exception {
        return isSubscribed(email, Collections.singletonList(subscriptionId));
    }

    @Override
    public void deleteUser(String email) throws Exception {
        deleteUserWithEmail(email);
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
    public int resetPassword(String username, String currentPassword, String newPassword) throws Exception {
        //get user representation
        final String accessToken;
        try {
            accessToken = getAccessToken(username, currentPassword);
        } catch (Exception e) {
            throw new IOException("Current password invalid for user " + username);
        }

        final Map<String, String> map = parseAccessToken(accessToken);
        String id = map.get("sub");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken()); //Get token with enough privileges

        final JSONObject credentials = JSONFactoryUtil.createJSONObject();
        credentials.put("type", "password");
        credentials.put("temporary", false);
        credentials.put("value", newPassword);

        //write updated user representation
        HttpURLConnection connection = getConnection(getKeycloakUsersPath() + '/' + id + "/reset-password", "PUT", headers);
        connection.setDoOutput(true);
        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            credentials.write(w);
        }
        //get response
        return checkResponse(connection);

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
        basePath = PropsUtil.get(BASEURL_KEY);

        if (basePath.endsWith("/")) {
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

    private String getTokenPath() {
        String basePath = getKeycloakBasePath();
        return basePath + "protocol/openid-connect/token";
    }

    private String getAccessToken() throws Exception {

        String CACHED_TOKEN_KEY = "keycloak.token";
        String CACHED_EXPIRY_KEY = "keycloak.expirytime";
        String token = CACHE_TOKEN ? getCachedToken(CACHED_TOKEN_KEY, CACHED_EXPIRY_KEY) : null;
        if (token != null) return token;

        Map<String, String> headers = new HashMap<>();
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
        } catch (IOException | JSONException e) {
            clearAccessTokens("keycloak");
            LOG.error("Failed to get access token: " + e.getMessage());
            throw e;
        }
    }

    private String getAccessToken(String username, String password) throws Exception {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        try {
            HttpURLConnection connection = getConnection(getTokenPath(), "POST", headers);
            writePostParameters(connection, getOAuthParameters(username, password));
            checkResponse(connection);
            String jsonResponse = readAll(connection);
            Map<String, String> parsedToken = JsonContentUtils.parseJsonToMap(jsonResponse);
            return parsedToken.get("access_token");
        } catch (IOException | JSONException e) {
            throw new Exception(String.format("Failed to get access token for user %s: %s", username, e.getMessage()));
        }
    }

    private Map<String, String> getOAuthParameters(String username, String password) {
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("client_id", getProperty("keycloak.clientid"));
        pathParameters.put("client_secret", getProperty("keycloak.clientsecret"));
        pathParameters.put("grant_type", "password"); // use refresh token to close previous session
        pathParameters.put("username", username); // use refresh token to close previous session
        pathParameters.put("password", password); // use refresh token to close previous session
        return pathParameters;
    }

    private Map<String, String> getOAuthParameters() {
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("client_id", getProperty("keycloak.clientid"));
        pathParameters.put("client_secret", getProperty("keycloak.clientsecret"));

        final String refreshToken = getCachedToken("keycloak.refresh.token", "keycloak.refresh.expirytime");
        if (refreshToken != null) {
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
