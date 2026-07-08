package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.configuration.WebinarSiteConfiguration;
import nl.deltares.portal.utils.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GotoUtils extends HttpClientUtils implements WebinarUtils, JoinConsumer {
    private static final Log LOG = LogFactoryUtil.getLog(GotoUtils.class);

    private final String CACHED_REFRESH_EXPIRY_KEY;
    private final String CACHED_REFRESH_TOKEN_KEY;
    private final String CACHED_ORGANIZER_KEY;
    private final String CACHED_TOKEN_PREFIX;
    private final String CACHED_EXPIRY_KEY;
    private final String CACHED_TOKEN_KEY;
    private final boolean CACHE_TOKEN;
    private final boolean CACHE_RESPONSES;


    private final WebinarSiteConfiguration configuration;
    private String basePath;
    private static final String GOTO_REGISTRANTS_PATH = "G2W/rest/v2/organizers/%s/webinars/%s/registrants";
    private static final String GOTO_COORGANIZERS_PATH = "G2W/rest/v2/organizers/%s/webinars/%s/coorganizers";
    private static final String GOTO_PANELISTS_PATH = "G2W/rest/v2/organizers/%s/webinars/%s/panelists";

    public GotoUtils(WebinarSiteConfiguration siteConfiguration) throws IOException {
        if (siteConfiguration == null) throw new NullPointerException("siteConfiguration == null");
        this.configuration = siteConfiguration;

        String clientId = configuration.gotoClientId();
        if (clientId == null || clientId.isEmpty()){
            throw new IOException("No GOTO clientId configured! Please check the WebinarConfigurations options.");
        }
        CACHED_TOKEN_PREFIX = clientId;
        CACHED_REFRESH_TOKEN_KEY = CACHED_TOKEN_PREFIX + ".refresh.token";
        CACHED_REFRESH_EXPIRY_KEY = CACHED_TOKEN_PREFIX + ".refresh.expirytime";
        CACHED_ORGANIZER_KEY = CACHED_TOKEN_PREFIX + ".organizer";
        CACHED_TOKEN_KEY = CACHED_TOKEN_PREFIX + ".token";
        CACHED_EXPIRY_KEY = CACHED_TOKEN_PREFIX + ".expirytime";
        CACHE_TOKEN = configuration.gotoCacheToken();
        CACHE_RESPONSES = configuration.gotoCacheResponse();
    }

    @Override
    public boolean isActive() {
        return !configuration.gotoClientId().isEmpty();
    }

    @Override
    public int registerUser(User user, Map<String, String> userAttributes,  String webinarKey, String callerId, Map<String, String> registrationProperties) throws Exception {

        String rawRegistrationPath = getBasePath() + GOTO_REGISTRANTS_PATH;
        String accessToken = getAccessToken(); //calling this method loads organization key
        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(accessToken), webinarKey);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);

        //open connection
        HttpURLConnection connection = getConnection(registrationPath, "POST", headers);

        //write user information
        writePostData(connection, user, userAttributes, callerId);
        Map<String, String> parseJsonToMap;
        //get response
        int responseCode = checkResponse(connection);
        if (responseCode == 409){
            parseJsonToMap = getUserInfo(user.getEmailAddress(), webinarKey, getBasePath() + GOTO_REGISTRANTS_PATH, false);
        } else {
            String jsonResponse = readAll(connection);
            parseJsonToMap = JsonContentUtils.parseJsonToMap(jsonResponse);
        }
        if (parseJsonToMap.containsKey("registrantKey")) registrationProperties.put("registrantKey", parseJsonToMap.get("registrantKey"));
        if (parseJsonToMap.containsKey("joinUrl")) registrationProperties.put("joinUrl", parseJsonToMap.get("joinUrl"));
        return 0;
    }

    @Override
    public int unregisterUser(User user, String webinarKey, Map<String, String> properties) throws Exception{

        String registrantKey = properties.get("registrantKey");
        if (registrantKey == null && user != null){
            final Map<String, String> userInfo = getUserInfo(user.getEmailAddress(), webinarKey, getBasePath() + GOTO_REGISTRANTS_PATH, false);
            registrantKey = userInfo.get("registrantKey");
        }
        if (registrantKey == null) {
            return 0; //user not found.
        }

        String rawRegistrationPath = getBasePath() + GOTO_REGISTRANTS_PATH;
        String accessToken = getAccessToken(); //calling this method loads organization key
        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(accessToken), webinarKey);
        String unregisterPath = registrationPath + '/' + registrantKey;

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        //open connection
        HttpURLConnection connection = getConnection(unregisterPath, "DELETE", headers);

        //get response
        try {
            return checkResponse(connection);
        } finally {
            connection.disconnect();
        }
    }

    @Override
    public String getJoinLink(User user, String webinarKey, Map<String, String> properties) {
        if (user == null) return null;

        /*
          It is possible that Co-organizers and panelist members have been added from within GOTO.
          Check and return those links if present.
          */
        final String coOrganizerJoinLink = getCoOrganizerJoinLink(user, webinarKey);
        if (coOrganizerJoinLink != null) return coOrganizerJoinLink;

        final String panelistJoinLink = getPanelistJoinLink(user, webinarKey);
        if (panelistJoinLink != null) return panelistJoinLink;

        final String joinUrl = properties.get("joinUrl");
        if (joinUrl != null) return joinUrl;

        String rawRegistrationPath = getBasePath() + GOTO_REGISTRANTS_PATH;
        String registrantKey = properties.get("registrantKey");
        if (registrantKey != null) {
            rawRegistrationPath +=  "/" + registrantKey;
        }
        final Map<String, String> userInfo = getUserInfo(user.getEmailAddress(), webinarKey, rawRegistrationPath, false);
        return userInfo.get("joinUrl");
    }

    public String getCoOrganizerJoinLink(User user, String webinarKey) {
        if (user == null) return null;

        String apiPathTemplate = getBasePath() + GOTO_COORGANIZERS_PATH;
        final Map<String, String> userInfo = getUserInfo(user.getEmailAddress(), webinarKey, apiPathTemplate, CACHE_RESPONSES);
        return userInfo.get("joinLink");
    }

    public String getPanelistJoinLink(User user, String webinarKey) {
        if (user == null) return null;

        String apiPathTemplate = getBasePath() + GOTO_PANELISTS_PATH;
        final Map<String, String> userInfo = getUserInfo(user.getEmailAddress(), webinarKey, apiPathTemplate, CACHE_RESPONSES);
        return userInfo.get("joinLink");
    }

    private Map<String, String> getUserInfo(String email, String webinarKey, String apiPathTemplate, boolean cacheResponse) {
        try {
            String jsonResponse = callGotoApi(webinarKey, apiPathTemplate, cacheResponse);
            final List<Map<String, String>> mapsList = JsonContentUtils.parseJsonArrayToMap(jsonResponse);
            for (Map<String, String> registrant : mapsList) {
                if (email.equalsIgnoreCase(registrant.get("email"))) {
                    return registrant;
                }
            }
        } catch (Exception e){
            //
        }
        return Collections.emptyMap();
    }

    private String callGotoApi(String webinarKey, String apiPathTemplate, boolean cacheResponse) throws Exception {
        //open connection
        String accessToken = getAccessToken(); //calling this method loads organization key
        String apiPath = String.format(apiPathTemplate, getOrganizerKey(accessToken), webinarKey);
        String apiPathExpiry = apiPath + ".expirytime";
        if (cacheResponse) {
            final String cachedResponse = getCachedToken(apiPath, apiPathExpiry);
            if (cachedResponse != null) return cachedResponse;
        }
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);
        HttpURLConnection connection = getConnection(apiPath, "GET", headers);
        //get response
        if (connection.getResponseCode() == 404) {
            connection.disconnect();
            return null; //user not found for registrantKey
        }
        final String response = readAll(connection);
        if (cacheResponse && !JsonContentUtils.isEmpty(response)){
            setCachedToken(apiPath, apiPathExpiry, response, System.currentTimeMillis() + 300000);
        }
        return response;
    }

    @Override
    public List<String> getAllCourseRegistrations(String webinarKey) throws Exception {

        String jsonResponse = callGotoApi(webinarKey, getBasePath() + GOTO_REGISTRANTS_PATH, false);

        List<Map<String, String>> mapsList = JsonContentUtils.parseJsonArrayToMap(jsonResponse);
        ArrayList<String> emails = new ArrayList<>();
        for (Map<String, String> registrant : mapsList) {
            String email = registrant.get("email");
            if (email != null && !email.isEmpty()) {
                emails.add(email.toLowerCase());
            }
        }
        return emails;
    }

    @Override
    public boolean isUserInCourseRegistrationsList(List<String> courseRegistrations, User user) {
        if (user == null) return false;
        return courseRegistrations.contains(user.getEmailAddress().toLowerCase());
    }

    private String getOrganizerKey(String accessToken) {
        String organizer_key = CACHE_TOKEN ? getCachedToken(CACHED_ORGANIZER_KEY, null) : null;
        if (organizer_key != null) return organizer_key;

        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Bearer " + accessToken);

        try {
            HttpURLConnection connection = getConnection(getOrganizerPath(), "GET", headers);
            String jsonResponse = readAll(connection);
            Map<String, String> parsedResponse = JsonContentUtils.parseJsonToMap(jsonResponse);

            organizer_key = parsedResponse.get("id");
            if (CACHE_TOKEN){
                setCachedToken(CACHED_ORGANIZER_KEY, null, organizer_key, 0);
            }
            return organizer_key;
        } catch (IOException | JSONException e){
            clearAccessTokens(CACHED_TOKEN_PREFIX);
            LOG.error("Failed to get organizer key: " + e.getMessage());
        }

        return null;
    }

    private void writePostData(HttpURLConnection connection, User user, Map<String, String> userAttributes, String callerId) throws IOException {
        connection.setDoOutput(true);

        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("firstName", user.getFirstName());
        parameterMap.put("lastName", user.getLastName());
        parameterMap.put("email", user.getEmailAddress());
        parameterMap.put("registrantKey", user.getScreenName());
        parameterMap.put("zipCode", userAttributes.get(KeycloakUtils.ATTRIBUTES.org_postal.name()));
        parameterMap.put("country", userAttributes.get(KeycloakUtils.ATTRIBUTES.org_country.name()));
        parameterMap.put("address", userAttributes.get(KeycloakUtils.ATTRIBUTES.org_address.name()));
        parameterMap.put("city", userAttributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()));
        parameterMap.put("organization", userAttributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()));
        parameterMap.put("source", callerId);

        String postData = JsonContentUtils.formatMapToJson(parameterMap);
        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            w.append(postData);
        }
    }

    private String getAccessToken() {

        String token = CACHE_TOKEN ? getCachedToken(CACHED_TOKEN_KEY, CACHED_EXPIRY_KEY) : null;
        if (token != null) return token;

        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic " + getBasicAuthorization(configuration.gotoClientId(), configuration.gotoClientSecret()));

        try {
            HttpURLConnection connection = getConnection(getTokenPath(), "POST", headers);
            writePostParameters(connection, getOAuthParameters());
            String jsonResponse = readAll(connection);
            Map<String, String> parsedToken = JsonContentUtils.parseJsonToMap(jsonResponse);

            if (CACHE_TOKEN){
                cacheAccessTokens(CACHED_TOKEN_PREFIX, parsedToken);
            }
            return parsedToken.get("access_token");
        } catch (IOException | JSONException e){
            clearAccessTokens(CACHED_TOKEN_PREFIX);
            LOG.error("Failed to get access token: " + e.getMessage());
        }

        return null;
    }

    private Map<String, String> getOAuthParameters() {
        Map<String,String> pathParameters = new HashMap<>();
        String cachedToken = CACHE_TOKEN ? getCachedToken(CACHED_REFRESH_TOKEN_KEY, CACHED_REFRESH_EXPIRY_KEY) : null;
        if (cachedToken != null) {
            pathParameters.put("grant_type", "refresh_token");
            pathParameters.put("refresh_token", cachedToken);
        } else if(configuration.gotoPAT() != null && !configuration.gotoPAT().isEmpty()) {
            pathParameters.put("grant_type", "personal_access_token");
            pathParameters.put("pat", configuration.gotoPAT());
            pathParameters.put("scope", configuration.gotoPATScope());
        } else {
            pathParameters.put("grant_type", "password");
            pathParameters.put("username", configuration.gotoUserName());
            pathParameters.put("password", configuration.gotoUserPassword());
        } return pathParameters;
    }

    protected String getBasePath() {
        if (basePath != null) return basePath;
        basePath =  configuration.gotoURL();
        if (basePath == null) return null;
        if(basePath.endsWith("/")){
            return basePath;
        }
        basePath += '/';
        return basePath;
    }

    protected String getTokenPath(){
        return configuration.gotoTokenURL();
    }
    protected String getOrganizerPath(){
        return getBasePath() + "identity/v1/Users/me";
    }
}
