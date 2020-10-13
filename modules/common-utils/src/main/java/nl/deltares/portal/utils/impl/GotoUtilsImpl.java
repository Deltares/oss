package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.GotoUtils;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component(
        immediate = true,
        service = GotoUtils.class
)
public class GotoUtilsImpl extends HttpClientUtils implements GotoUtils {
    private static final Log LOG = LogFactoryUtil.getLog(GotoUtilsImpl.class);

    private final String CACHED_REFRESH_EXPIRY_KEY = "goto.refresh.expirytime";
    private final String CACHED_REFRESH_TOKEN_KEY = "goto.refresh.token";
    private final String BASEURL_KEY =  "goto.baseurl";
    private String basePath;
    private static final String CACHED_ORGANIZER_KEY = "goto.organizer";
    private static final String GOTO_REGISTRATION_PATH = "G2W/rest/v2/organizers/%s/webinars/%s/registrants";
    private String organizer_key;

    @Override
    public boolean isActive() {
        return PropsUtil.contains(BASEURL_KEY);
    }

    @Override
    public boolean isGotoMeeting(Registration registration) {
        return (registration instanceof SessionRegistration) && (((SessionRegistration) registration).getWebinarKey() != null);
    }

    @Override
    public Map<String, String> getRegistration(User user, String webinarKey) throws Exception {
        String rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH;
        String accessToken = getAccessToken(); //calling this method loads organization key
        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(), webinarKey);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);

        //open connection
        HttpURLConnection connection = getConnection(registrationPath, "GET", headers);

        //get response
        checkResponse(connection);
        String jsonResponse = readAll(connection.getInputStream());
        List<Map<String, String>> mapsList = JsonContentUtils.parseJsonArrayToMap(jsonResponse);
        for (Map<String, String> registrant : mapsList) {
            String email = registrant.get("email");
            if (email != null && email.equals(user.getEmailAddress())) return registrant;
        }
        return null;
    }

    @Override
    public Map<String, String> registerUser(User user, String webinarKey, String callerId) throws Exception {

        String rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH;
        String accessToken = getAccessToken(); //calling this method loads organization key
        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(), webinarKey);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);

        //open connection
        HttpURLConnection connection = getConnection(registrationPath, "POST", headers);

        //write user information
        writePostData(connection, user, callerId);

        //get response
        checkResponse(connection);
        String jsonResponse = readAll(connection.getInputStream());
        return JsonContentUtils.parseJsonToMap(jsonResponse);

    }

    private String getOrganizerKey() {
        if (organizer_key != null) return organizer_key;
        return getCachedToken(CACHED_ORGANIZER_KEY, null);
    }

    @Override
    public int unregisterUser(String registrantKey, String webinarKey) throws Exception{

        String rawRegistrationPath = getBasePath() + GOTO_REGISTRATION_PATH;
        String accessToken = getAccessToken(); //calling this method loads organization key
        String registrationPath = String.format(rawRegistrationPath, getOrganizerKey(), webinarKey);
        String unregisterPath = registrationPath + '/' + registrantKey;

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        //open connection
        HttpURLConnection connection = getConnection(unregisterPath, "DELETE", headers);

        //get response
        return checkResponse(connection);
    }

    private void writePostData(HttpURLConnection connection, User user, String callerId) throws IOException {
        connection.setDoOutput(true);

        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("firstName", user.getFirstName());
        parameterMap.put("lastName", user.getLastName());
        parameterMap.put("email", user.getEmailAddress());
        parameterMap.put("source", callerId);

        String postData = JsonContentUtils.formatMapToJson(parameterMap);
        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            w.append(postData);
        }
    }

    private String getAccessToken() {
        String CACHED_TOKEN_KEY = "goto.token";
        String CACHED_EXPIRY_KEY = "goto.expirytime";
        String token = getCachedToken(CACHED_TOKEN_KEY, CACHED_EXPIRY_KEY);
        if (token != null) return token;

        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic " + getBasicAuthorization(getClientId(), getClientSecret()));

        try {
            HttpURLConnection connection = getConnection(getTokenPath(), "POST", headers);
            writeOauthPostParameters(connection, getOAuthParameters());
            String jsonResponse = readAll(connection.getInputStream());
            Map<String, String> parsedToken = JsonContentUtils.parseJsonToMap(jsonResponse);

            String organizer_key = parsedToken.get("organizer_key");
            if (!setCachedToken(CACHED_ORGANIZER_KEY, null, organizer_key, 0)){
                this.organizer_key = organizer_key; //cache is disabled
            }
            cacheAccessToken(CACHED_TOKEN_KEY, CACHED_EXPIRY_KEY, parsedToken);

            String refresh_token = parsedToken.get("refresh_token");
            if (refresh_token != null) {
                long refreshExpTimeMillis = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(20);
                setCachedToken(CACHED_REFRESH_TOKEN_KEY, CACHED_REFRESH_EXPIRY_KEY, refresh_token, refreshExpTimeMillis);
            }

            return parsedToken.get("access_token");
        } catch (IOException | JSONException e){
            LOG.error("Failed to get access token: " + e.getMessage());
        }

        return null;
    }

    private Map<String, String> getOAuthParameters() {
        Map<String,String> pathParameters = new HashMap<>();
        String cachedToken = getCachedToken(CACHED_REFRESH_TOKEN_KEY, CACHED_REFRESH_EXPIRY_KEY);
        if (cachedToken != null){
            pathParameters.put("grant_type", "refresh_token");
            pathParameters.put("refresh_token", cachedToken);
        } else {
            pathParameters.put("grant_type", "password");
            pathParameters.put("username", getUserName());
            pathParameters.put("password", getUserPassword());
        } return pathParameters;
    }

    protected String getBasePath() {
        if (basePath != null) return basePath;
        basePath =  getProperty(BASEURL_KEY);
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

    protected String getUserName(){
        return getProperty("goto.username");
    }

    protected String getUserPassword(){
        return getProperty("goto.password");
    }

    protected String getClientId(){
        return getProperty("goto.clientid");
    }

    protected String getClientSecret(){
        return getProperty("goto.clientsecret");
    }
}
