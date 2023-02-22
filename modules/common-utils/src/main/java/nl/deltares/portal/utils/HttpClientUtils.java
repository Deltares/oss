package nl.deltares.portal.utils;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class HttpClientUtils {

    private static final Log LOG = LogFactoryUtil.getLog(HttpClientUtils.class);

    /**
     * Copy an input stream to an outputstream
     *
     */
    public static long stream(InputStream input, OutputStream output) throws IOException {
        try (
                ReadableByteChannel inputChannel = Channels.newChannel(input);
                WritableByteChannel outputChannel = Channels.newChannel(output)
        ) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            long size = 0;

            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                size += outputChannel.write(buffer);
                buffer.clear();
            }

            return size;
        }
    }

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
            throw new IOException("Error " + responseCode + ": " + urlConnection.getResponseMessage());
        }
        return responseCode;
    }

    public static String readAll(HttpURLConnection connection) throws IOException {
        try (InputStream is = connection.getInputStream()) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            return result.toString("UTF-8");
        } finally {
            connection.disconnect();
        }
    }

    public static String readError(HttpURLConnection connection) throws IOException {
        try (InputStream is = connection.getErrorStream()) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            return result.toString("UTF-8");
        } finally {
            connection.disconnect();
        }
    }

    public static byte[] readAllBytes(HttpURLConnection connection) throws IOException {
        try (InputStream is = connection.getInputStream()) {

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            return result.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public static String getBasicAuthorization(String username, String password) {
        return Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());
    }

    public static void clearAccessTokens(String prefix) {
        removeCachedToken(prefix + ".token");
        removeCachedToken(prefix + ".expirytime");
        removeCachedToken(prefix + ".refresh.token");
        removeCachedToken(prefix + ".refresh.expirytime");
    }

    public static void cacheAccessTokens(String prefix, Map<String, String> parsedToken) {
        String expires_in = parsedToken.get("expires_in");
        if (expires_in == null) return;
        long expMillis = Long.parseLong(expires_in) * 1000;
        long expTimeMillis = expMillis + System.currentTimeMillis();
        setCachedToken(prefix + ".token", prefix + ".expirytime", parsedToken.get("access_token"), expTimeMillis);

        String refresh_token = parsedToken.get("refresh_token");
        String refresh_expires_in = parsedToken.get("refresh_expires_in");
        if (refresh_expires_in != null) {
            expMillis = Long.parseLong(refresh_expires_in) * 1000;
            expTimeMillis = expMillis + System.currentTimeMillis();
        }
        if (refresh_token != null) {
            setCachedToken(prefix + ".refresh.token", prefix + ".refresh.expirytime", refresh_token, expTimeMillis);
        }
    }

    public static String getCachedToken(String tokenKey, String expiryKey) {
        if (tokenKey == null) return null;
        PortalCache<String, Serializable> gotoCache = MultiVMPoolUtil.getPortalCache("deltares", true);
        String token = (String) gotoCache.get(tokenKey);
        if (token != null) {
            if (expiryKey == null) return token;
            Long expiryTime = (Long) gotoCache.get(expiryKey);
            if (expiryTime != null && expiryTime > System.currentTimeMillis()) {
                return token;
            }
        }
        return null;
    }

    public static void removeCachedToken(String key) {
        PortalCache<String, Serializable> keycloakCache = MultiVMPoolUtil.getPortalCache("deltares", true);
        keycloakCache.remove(key);
    }

    public static boolean setCachedToken(String tokenKey, String expiryKey, String token, long expiryTimeMillis) {
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

    public static Map<String, String> parseAccessToken(String accessToken) throws JSONException {
        if (accessToken == null) return Collections.emptyMap();

        String[] chunks = accessToken.split("\\.");

        final Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        final Map<String, String> map;
        if (chunks.length > 0) {
            final String header = new String(urlDecoder.decode(chunks[0]));
            map = JsonContentUtils.parseJsonToMap(header);
            if (chunks.length >1) {
                final String payload = new String(urlDecoder.decode(chunks[1]));
                map.putAll(JsonContentUtils.parseJsonToMap(payload));
            }
            return map;
        } else {
            return Collections.emptyMap();
        }

    }


}
