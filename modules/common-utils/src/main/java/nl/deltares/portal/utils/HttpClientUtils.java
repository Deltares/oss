package nl.deltares.portal.utils;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

public abstract class HttpClientUtils {

    private static final Log LOG = LogFactoryUtil.getLog(HttpClientUtils.class);

    public static HttpURLConnection getConnection(String path, String method, Map<String, String> headers) throws IOException {

        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new IOException(String.format("Invalid path %s: %s", path, e.getMessage()));
        }

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(false);
        urlConnection.setRequestMethod(method);
        urlConnection.setConnectTimeout(1000);

        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                urlConnection.setRequestProperty(key, headers.get(key));
            }
        }
        return urlConnection;
    }

    public static void writePostParameters(HttpURLConnection connection, Map<String, String> parameters) throws IOException {
        connection.setDoOutput(true);
        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                w.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.name()));
                w.append('=');
                w.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8.name()));
                w.append('&');
            }
        }
    }

    public static int checkResponse(HttpURLConnection urlConnection) throws IOException {
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

    public static String getBasicAuthorization(String username, String password) {
        return  Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());
    }

    public static boolean cacheAccessToken(String cachedTokenKey, String cachedExpiryKey, Map<String, String> parsedToken) {
        String accessToken = parsedToken.get("access_token");
        String expires_in = parsedToken.get("expires_in");
        if (expires_in != null) {
            long expMillis = Long.parseLong(expires_in) * 1000;
            long expTimeMillis = expMillis + System.currentTimeMillis();
            return setCachedToken(cachedTokenKey, cachedExpiryKey, accessToken, expTimeMillis);
        }
        return false;
    }

    public static String getCachedToken(String tokenKey, String expiryKey) {
        if (tokenKey == null) return null;
        if (Boolean.parseBoolean(PropsUtil.get("nocache"))) return null;
        PortalCache<String, Serializable> gotoCache = MultiVMPoolUtil.getPortalCache("deltares", true);
        String token = (String) gotoCache.get(tokenKey);
        if (token != null) {
            if (expiryKey == null) return token;
            Long expiryTime = (Long) gotoCache.get(expiryKey);
            if (expiryTime != null && expiryTime > System.currentTimeMillis()){
                return token;
            }
        }
        return null;
    }

    public static boolean setCachedToken(String tokenKey, String expiryKey, String token, long expiryTimeMillis){
        if (Boolean.parseBoolean(PropsUtil.get("nocache"))) return false;

        PortalCache<String, Serializable> keycloakCache = MultiVMPoolUtil.getPortalCache("deltares", true);
        keycloakCache.put(tokenKey, token);
        if (expiryKey != null) {
            keycloakCache.put(expiryKey, expiryTimeMillis);
        }
        return true;
    }

    public static String getProperty(String propertyKey) {
        if (!PropsUtil.contains(propertyKey)) {
            LOG.warn(String.format("Missing property %s in portal-ext.properties file", propertyKey));
            return null;
        }
        return PropsUtil.get(propertyKey);
    }


}
