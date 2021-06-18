package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.configuration.WebinarSiteConfiguration;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.portal.utils.WebinarUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GotoUtils extends HttpClientUtils implements WebinarUtils {
    private static final Log LOG = LogFactoryUtil.getLog(GotoUtils.class);

    private final String CACHED_REFRESH_EXPIRY_KEY;
    private final String CACHED_REFRESH_TOKEN_KEY;
    private final String CACHED_ORGANIZER_KEY;
    private final String CACHED_TOKEN_PREFIX;
    private final String CACHED_EXPIRY_KEY;
    private final String CACHED_TOKEN_KEY;
    private final boolean CACHE_TOKEN;


    private final WebinarSiteConfiguration configuration;
    private String basePath;
    private static final String GOTO_REGISTRATION_PATH = "G2W/rest/v2/organizers/%s/webinars/%s/registrants";
    private String organizer_key;

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
    }

    @Override
    public boolean isActive() {
        return !configuration.gotoClientId().isEmpty();
    }

    @Override
    public boolean isUserRegistered(User user, String webinarKey, Map<String, String> registrationProperties) throws Exception {

        String accessToken = getAccessToken(); //calling this method loads organization key
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);
        String rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH;

        String registrantKey = registrationProperties.get("registrantKey");
        if (registrantKey != null) {
            rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH + "/" + registrantKey;
        }
        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(), webinarKey);
        //open connection
        HttpURLConnection connection = getConnection(registrationPath, "GET", headers);
        //get response
        if (connection.getResponseCode() == 404) {
            connection.disconnect();
            return false; //user not found for registrantKey
        }

        String jsonResponse = readAll(connection);

        List<Map<String, String>> mapsList = new ArrayList<>();
        if (registrantKey != null) {
            mapsList.add(JsonContentUtils.parseJsonToMap(jsonResponse));
        } else {
            mapsList.addAll(JsonContentUtils.parseJsonArrayToMap(jsonResponse));
        }
        for (Map<String, String> registrant : mapsList) {
            String email = registrant.get("email");
            if (email != null && email.equals(user.getEmailAddress())) {
                registrationProperties.put("registrantKey", registrant.get("registrantKey"));
                return true;
            }
        }
        return false;
    }

    @Override
    public int registerUser(User user, Map<String, String> userAttributes,  String webinarKey, String callerId, Map<String, String> registrationProperties) throws Exception {

        String rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH;
        String accessToken = getAccessToken(); //calling this method loads organization key
        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(), webinarKey);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);

        //open connection
        HttpURLConnection connection = getConnection(registrationPath, "POST", headers);

        //write user information
        writePostData(connection, user, userAttributes, callerId);

        //get response
        checkResponse(connection);
        String jsonResponse = readAll(connection);
        Map<String, String> parseJsonToMap = JsonContentUtils.parseJsonToMap(jsonResponse);
        if (parseJsonToMap.containsKey("registrantKey")) registrationProperties.put("registrantKey", parseJsonToMap.get("registrantKey"));
        return 0;
    }

    @Override
    public int unregisterUser(User user, String webinarKey, Map<String, String> properties) throws Exception{

        String registrantKey = properties.get("registrantKey");
        if (registrantKey == null){
            registrantKey = getRegistrantKey(user, webinarKey);
            if (registrantKey == null) {
                return 0; //user not found.
            }
        }

        String rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH;
        String accessToken = getAccessToken(); //calling this method loads organization key
        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(), webinarKey);
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

    private String getRegistrantKey(User user, String webinarKey) {
        try {
            String accessToken = getAccessToken(); //calling this method loads organization key
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer " + accessToken);
            String rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH;

            String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(), webinarKey);
            //open connection
            HttpURLConnection connection = getConnection(registrationPath, "GET", headers);
            //get response
            String jsonResponse = readAll(connection);

            List<Map<String, String>> mapsList = new ArrayList<>(JsonContentUtils.parseJsonArrayToMap(jsonResponse));
            for (Map<String, String> registrant : mapsList) {
                String email = registrant.get("email");
                if (user.getEmailAddress().equalsIgnoreCase(email)) {
                    return registrant.get("registrantKey");
                }
            }
        } catch (Exception e){
            //
        }
        return null;
    }

    @Override
    public List<String> getAllCourseRegistrations(String webinarKey) throws Exception {
        String accessToken = getAccessToken(); //calling this method loads organization key
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);
        String rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH;

        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(), webinarKey);
        //open connection
        HttpURLConnection connection = getConnection(registrationPath, "GET", headers);
        //get response
        String jsonResponse = readAll(connection);

        List<Map<String, String>> mapsList = new ArrayList<>(JsonContentUtils.parseJsonArrayToMap(jsonResponse));
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
        return courseRegistrations.contains(user.getEmailAddress().toLowerCase());
    }

    private String getOrganizerKey() {
        if (organizer_key != null) return organizer_key;
        return CACHE_TOKEN ? getCachedToken(CACHED_ORGANIZER_KEY, null) : null;
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

            String organizer_key = parsedToken.get("organizer_key");
            if (CACHE_TOKEN){
                setCachedToken(CACHED_ORGANIZER_KEY, null, organizer_key, 0);
                cacheAccessTokens(CACHED_TOKEN_PREFIX, parsedToken);
            }
            this.organizer_key = organizer_key; //for if cache is disabled

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
        if (cachedToken != null){
            pathParameters.put("grant_type", "refresh_token");
            pathParameters.put("refresh_token", cachedToken);
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
        String basePath = getBasePath();
        return basePath + "oauth/v2/token";
    }

}
