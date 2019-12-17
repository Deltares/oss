package nl.deltares.common;

import com.liferay.portal.kernel.util.PropsUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyDeltaresUtil {

    public static final String KEYCLOAK_BASEURL_KEY = "keycloak.baseurl";
    public static final String KEYCLOAK_ACCOUNT_PATH = "account";
    public static final String KEYCLOAK_MAILING_PATH = "user-mailings/mailings-page";
    public static final String KEYCLOAK_AVATAR_PATH = "avatar-provider";
    public static final String KEYCLOAK_ADMIN_AVATAR_PATH = "avatar-provider/admin";
    public static final String KEYCLOAK_OPENID_TOKEN_PATH = "protocol/openid-connect/token";
    public static final String KEYCLOAK_CLIENTID_KEY = "keycloak.clientid";
    public static final String KEYCLOAK_CLIENTSECRET_KEY = "keycloak.clientsecret";

    public static final String SESSION_TOKEN_KEY = "keycloak.token";
    public static final String SESSION_EXPIRY_KEY = "keycloak.expirytime";

    public static String getTokenPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_OPENID_TOKEN_PATH;
    }

    public static String getAccountPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_ACCOUNT_PATH;
    }

    public static String getMailingPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_MAILING_PATH;
    }

    public static String getAvatarPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_AVATAR_PATH;
    }

    public static String getAdminAvatarPath(){
        String basePath = getKeycloakBasePath();
        return basePath + KEYCLOAK_ADMIN_AVATAR_PATH;
    }

    public static String getOpenIdClientId(){
        if (!PropsUtil.contains(KEYCLOAK_CLIENTID_KEY)) {
            throw new IllegalStateException(String.format("Missing property %s in portal-ext.properties file", KEYCLOAK_CLIENTID_KEY));
        }
        return PropsUtil.get(KEYCLOAK_CLIENTID_KEY);
    }

    public static String getOpenIdClientSecret(){
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

    public static String getAccessTokenJson(String tokenUrl, String clientId, String clientSec) throws IOException {

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

    public static String readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString("UTF-8");
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toByteArray();
    }

}
