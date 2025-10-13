import com.liferay.portal.util.PropsUtil;
import nl.deltares.portal.utils.impl.KeycloakUtilsImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestKeycloakUtils {

    @Test
    public void testGettingToken() throws Exception {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String path = "https://accounts.deltares.nl/auth/realms/liferay-portal/protocol/openid-connect/token";
        HttpURLConnection post = getConnection(path, "POST", headers);


        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("client_id", "oss-accounts");
        pathParameters.put("client_secret", "<copy this from Keycloak>");
        pathParameters.put("grant_type", "client_credentials");

        writePostParameters(post, pathParameters);
        checkResponse(post);

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
                w.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
                w.append('=');
                w.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
                w.append('&');
            }
        }
    }

    public static int checkResponse(HttpURLConnection urlConnection) throws IOException {
        int responseCode = urlConnection.getResponseCode();
        if (responseCode == 409){
            return 409; //already registered
        }
        if (responseCode > 299) {
            throw new IOException("Error " + responseCode + ": " + urlConnection.getResponseMessage());
        }
        return responseCode;
    }

}
