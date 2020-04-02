package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component(
        immediate = true,
        service = KeycloakUtils.class
)
public class KeycloakUtilsImpl implements KeycloakUtils {

    private static final Log LOG = LogFactoryUtil.getLog(KeycloakUtilsImpl.class);

    private static final String CACHED_TOKEN_KEY = "keycloak.token";
    private static final String CACHED_EXPIRY_KEY = "keycloak.expirytime";

    private static final String KEYCLOAK_USER_MAILING_PATH = "user-mailings";
    private static final String KEYCLOAK_ACCOUNT_PATH = "account";
    private static final String KEYCLOAK_BASEURL_KEY = "keycloak.baseurl";
    private static final String KEYCLOAK_BASEAPIURL_KEY = "keycloak.baseapiurl";
    private static final String KEYCLOAK_AVATAR_PATH = "avatar-provider";
    private static final String KEYCLOAK_USER_ATTRIBUTES_PATH = "user-attributes";
    private static final String KEYCLOAK_ADMIN_AVATAR_PATH = KEYCLOAK_AVATAR_PATH + "/admin";

    private static final String KEYCLOAK_OPENID_TOKEN_PATH = "protocol/openid-connect/token";
    private static final String KEYCLOAK_CLIENTID_KEY = "keycloak.clientid";
    private static final String KEYCLOAK_CLIENTSECRET_KEY = "keycloak.clientsecret";


    @Override
    public boolean isActive() {
        return PropsUtil.contains(KEYCLOAK_BASEURL_KEY);
    }

    @Override
    public String getAdminAvatarPath() {
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_ADMIN_AVATAR_PATH;
    }

    @Override
    public String getUserMailingPath() {
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_USER_MAILING_PATH;
    }

    @Override
    public String getAccountPath() {
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_ACCOUNT_PATH;
    }

    @Override
    public byte[] getUserAvatar(String email) throws IOException {
        HttpURLConnection urlConnection = getConnection(getAdminAvatarPath() + "?email=" + email, "GET", getAccessToken(), null);
        checkResponse(urlConnection);
        return readAllBytes(urlConnection.getInputStream());
    }

    @Override
    public Map<String, Object> getUserAttributes(String email) throws IOException {
        HttpURLConnection urlConnection = getConnection(getAdminUserAttributesPath() + "?email=" + email, "GET", getAccessToken(), null);
        checkResponse(urlConnection);
        String response = readAll(urlConnection.getInputStream());
        JSONObject jsonAttributes;
        try {
            jsonAttributes = JSONFactoryUtil.createJSONObject(response);
        } catch (JSONException e) {
            throw new IOException("Error parsing json response: " + e.getMessage());
        }
        HashMap<String, Object> attributesMap = new HashMap<>();
        for (Iterator<String> it = jsonAttributes.keys(); it.hasNext(); ) {
            String key = it.next();
            attributesMap.put(key, jsonAttributes.get(key));
        }
        return attributesMap;
    }

    @Override
    public int updateUserAttributes(String email, Map<String, Object> attributes) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        HttpURLConnection urlConnection = getConnection(getAdminUserAttributesPath(), "PUT", getAccessToken(), map);

        JSONObject jsonBody = JSONFactoryUtil.createJSONObject();
        jsonBody.put("email", email);
        JSONObject jsonAtts = JSONFactoryUtil.createJSONObject();
        jsonBody.put("attributes", jsonAtts);
        for (Map.Entry<String, Object> keyValue : attributes.entrySet()) {
            jsonAtts.put(keyValue.getKey(), keyValue.getValue());
        }
        try (OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream())) {
            jsonBody.write(writer);
        } catch (JSONException e) {
            LOG.error(String.format("Error updating user attributes for user %s: %s", email, e.getMessage()), e);
        }
        return checkResponse(urlConnection);
    }

    private String getAdminUserAttributesPath() {
        String basePath = getKeycloakBaseApiPath();
        return basePath + KEYCLOAK_USER_ATTRIBUTES_PATH;
    }

    private static String getKeycloakBasePath() {
        if (!PropsUtil.contains(KEYCLOAK_BASEURL_KEY)) {
            LOG.info(String.format("Missing property %s in portal-ext.properties file", KEYCLOAK_BASEURL_KEY));
            return null;
        }
        String basePath =  PropsUtil.get(KEYCLOAK_BASEURL_KEY);

        if(basePath.endsWith("/")){
            return basePath;
        }
        return basePath + '/';
    }

    private String getKeycloakBaseApiPath() {
        String basePath =PropsUtil.get(KEYCLOAK_BASEAPIURL_KEY);

        if (basePath.endsWith("/")) {
            return basePath;
        }
        return basePath + '/';
    }

    private String getTokenPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_OPENID_TOKEN_PATH;
    }

    private String getAccessToken(){

        String cachedToken = getCachedToken();
        if (cachedToken != null) return cachedToken;

        try {
            String jsonResponse = getAccessTokenJson(
                    getTokenPath(),
                    getOpenIdClientId(),
                    getOpenIdClientSecret());
            return parseTokenJson(jsonResponse);

        } catch (JSONException | IOException e) {
            LOG.warn("Failed to get access token: " + e.getMessage());
        }
        return null;
    }

    private String parseTokenJson(String jsonResponse) throws JSONException {
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonResponse);
        long expMillis = jsonObject.getInt("expires_in") * 1000;
        long expTimeMillis = expMillis + System.currentTimeMillis();

        String accessToken = jsonObject.getString("access_token");
        setCachedToken(accessToken, expTimeMillis);
        return accessToken;
    }

    private String getCachedToken() {
        PortalCache<String, Serializable> keycloakCache = MultiVMPoolUtil.getPortalCache("keycloak", true);
        String token = (String) keycloakCache.get(CACHED_TOKEN_KEY);
        if (token != null) {
            Long expiryTime = (Long) keycloakCache.get(CACHED_EXPIRY_KEY);
            if (expiryTime != null && expiryTime > System.currentTimeMillis()){
                return token;
            }
        }
        return null;
    }

    private void setCachedToken(String token, long expiryTimeMillis){
        PortalCache<String, Serializable> keycloakCache = MultiVMPoolUtil.getPortalCache("keycloak", true);
        keycloakCache.put(CACHED_TOKEN_KEY, token);
        keycloakCache.put(CACHED_EXPIRY_KEY, expiryTimeMillis);
    }

    private static String getOpenIdClientId(){
        if (!PropsUtil.contains(KEYCLOAK_CLIENTID_KEY)) {
            LOG.info(String.format("Missing property %s in portal-ext.properties file", KEYCLOAK_CLIENTID_KEY));
            return null;
        }
        return PropsUtil.get(KEYCLOAK_CLIENTID_KEY);
    }

    private static String getOpenIdClientSecret(){
        if (!PropsUtil.contains(KEYCLOAK_CLIENTSECRET_KEY)) {
            LOG.info(String.format("Missing property %s in portal-ext.properties file", KEYCLOAK_CLIENTSECRET_KEY));
            return null;
        }
        return PropsUtil.get(KEYCLOAK_CLIENTSECRET_KEY);
    }

    private static String getAccessTokenJson(String tokenUrl, String clientId, String clientSec) throws IOException {

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", clientId);
        params.put("client_secret", clientSec);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.name()));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8.name()));
        }
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

        URL url = new URL(tokenUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);

        urlConnection.getOutputStream().write(postDataBytes);
        int responseCode = urlConnection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Error " + responseCode + ": " + readAll(urlConnection.getErrorStream()));
        }
        return readAll(urlConnection.getInputStream());

    }

    private static String readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString("UTF-8");
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toByteArray();
    }

    private HttpURLConnection getConnection(String path, String method, String accessToken, Map<String, String> props) throws IOException {
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new IOException(String.format("Invalid path %s: %s", path, e.getMessage()));
        }

        HttpURLConnection httpConnection = getHttpConnection(url, method, props);
        httpConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
        return httpConnection;
    }

    private HttpURLConnection getHttpConnection(URL url, String method, Map<String, String> props) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(!method.equals("GET"));
        urlConnection.setRequestMethod(method);
        urlConnection.setConnectTimeout(1000);

        if (props != null) {
            Set<String> keys = props.keySet();
            for (String key : keys) {
                urlConnection.setRequestProperty(key, props.get(key));
            }
        }
        return urlConnection;
    }

    private int checkResponse(HttpURLConnection urlConnection) throws IOException {
        int responseCode = urlConnection.getResponseCode();
        if (responseCode > 299) {
            InputStream errorStream = urlConnection.getErrorStream();
            if (errorStream != null) {
                throw new IOException("Error " + responseCode + ": " + readAll(errorStream));
            } else {
                throw new IOException("Error " + responseCode + ": no message");
            }
        }
        return responseCode;
    }

}
