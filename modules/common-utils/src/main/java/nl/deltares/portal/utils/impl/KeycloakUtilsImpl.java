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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Component(
        immediate = true,
        service = KeycloakUtils.class
)
public class KeycloakUtilsImpl implements KeycloakUtils {

    private static final Log LOG = LogFactoryUtil.getLog(KeycloakUtilsImpl.class);

    private static final String KEYCLOAK_BASEURL_KEY = "keycloak.baseurl";
    private static final String KEYCLOAK_ACCOUNT_PATH = "account";
    private static final String KEYCLOAK_MAILING_PATH = "user-mailings/mailings-page";
    private static final String KEYCLOAK_AVATAR_PATH = "avatar-provider";
    private static final String KEYCLOAK_ADMIN_AVATAR_PATH = "avatar-provider/admin";
    private static final String KEYCLOAK_OPENID_TOKEN_PATH = "protocol/openid-connect/token";
    private static final String KEYCLOAK_CLIENTID_KEY = "keycloak.clientid";
    private static final String KEYCLOAK_CLIENTSECRET_KEY = "keycloak.clientsecret";

    private static final String CACHED_TOKEN_KEY = "keycloak.token";
    private static final String CACHED_EXPIRY_KEY = "keycloak.expirytime";

    public String getAccountPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_ACCOUNT_PATH;
    }

    public String getMailingPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_MAILING_PATH;
    }

    public String getAvatarPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_AVATAR_PATH;
    }

    public String getAdminAvatarPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_ADMIN_AVATAR_PATH;
    }

    @Override
    public byte[] getUserAvatar(String email) {
        URL url;
        try {
            url = new URL(getAdminAvatarPath() + "?email=" + email);
        } catch (MalformedURLException e) {
            LOG.warn(String.format("Invalid path %s: %s", getAdminAvatarPath(), e.getMessage()));
            return null;
        }

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization" , "Bearer " + getAccessToken());
            return readAllBytes(urlConnection.getInputStream());
        } catch (IOException e) {
            LOG.warn(String.format("Error getting user avatar for %s: %s", email, e.getMessage()));
        }
        return null;
    }

    private String getTokenPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_OPENID_TOKEN_PATH;
    }

    private String getAccessToken() {

        String cachedToken = getCachedToken();
        if (cachedToken != null) return cachedToken;

        try {
            String jsonResponse = getAccessTokenJson(
                    getTokenPath(),
                    getOpenIdClientId(),
                    getOpenIdClientSecret());
            return parseJson(jsonResponse);

        } catch (IOException | JSONException e){
            LOG.warn("Failed to get access token: " + e.getMessage(), e);
        }
        return null;
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
            throw new IllegalStateException(String.format("Missing property %s in portal-ext.properties file", KEYCLOAK_CLIENTID_KEY));
        }
        return PropsUtil.get(KEYCLOAK_CLIENTID_KEY);
    }

    private static String getOpenIdClientSecret(){
        if (!PropsUtil.contains(KEYCLOAK_CLIENTSECRET_KEY)) {
            throw new IllegalStateException(String.format("Missing property %s in portal-ext.properties file", KEYCLOAK_CLIENTSECRET_KEY));
        }
        return PropsUtil.get(KEYCLOAK_CLIENTSECRET_KEY);
    }

    private static String getKeycloakBasePath() {
        if (!PropsUtil.contains(KEYCLOAK_BASEURL_KEY)) {
            throw new IllegalStateException(String.format("Missing property %s in portal-ext.properties file", KEYCLOAK_BASEURL_KEY));
        }
        String basePath =  PropsUtil.get(KEYCLOAK_BASEURL_KEY);

        if(basePath.endsWith("/")){
            return basePath;
        }
        return basePath + '/';
    }

    private static String getAccessTokenJson(String tokenUrl, String clientId, String clientSec) throws IOException {

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", clientId);
        params.put("client_secret", clientSec);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
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
        if (responseCode != 200){
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

    private String parseJson(String jsonResponse) throws JSONException {
        // parsing file "JSONExample.json"
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonResponse);
        long expMillis = jsonObject.getInt("expires_in") * 1000;
        long expTimeMillis = expMillis + System.currentTimeMillis();

        String accessToken = jsonObject.getString("access_token");
        setCachedToken(accessToken, expTimeMillis);
        return accessToken;
    }


}
