package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.model.impl.LicenseFile;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.LicenseManagerUtils;
import org.osgi.service.component.annotations.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component(
        immediate = true,
        service = LicenseManagerUtils.class
)
/*
 * The idea of this class was to allow retrieval of user location info from request. However it is not
 * that easy to get IP address from the request and therefore this class is not being used.
 */
public class LicenseManagerUtilsImpl extends HttpClientUtils implements LicenseManagerUtils {

    private static final Log LOG = LogFactoryUtil.getLog(LicenseManagerUtilsImpl.class);

    private static final String BASEURL_KEY = "license.baseurl";
    private String basePath;
    private final boolean CACHE_TOKEN;

    public LicenseManagerUtilsImpl() {
        CACHE_TOKEN = Boolean.parseBoolean(PropsUtil.get("license.cache.token"));
    }

    @Override
    public boolean isActive() {
        return PropsUtil.contains(BASEURL_KEY);
    }

    private String getTokenPath() {
        String basePath = getBasePath();
        return basePath + "login";
    }

    @Override
    public Map<String, String> encryptLicense(LicenseFile licenseFile, User user) throws IOException, JSONException {
        if (!isActive()) {
            LOG.warn("Unable to generate license files as the LicenseManager is not active!");
            return Collections.emptyMap();
        }
        if (user == null || user.isDefaultUser()) return Collections.emptyMap();

        String boundaryString = "----SignLicense";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data; boundary=" + boundaryString );
        headers.put("Authorization", "Bearer " + getAccessToken());

        HttpURLConnection connection = getConnection(getBasePath() + "/sign-lic", "POST", headers);
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        BufferedWriter httpRequestBodyWriter =
                new BufferedWriter(new OutputStreamWriter(outputStream));

        // Include the section to describe the file
        httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
        httpRequestBodyWriter.write("Content-Disposition: form-data;"
                + "name=\"licenseFile\";"
                + "filename=\"" + licenseFile.getName() + "\""
                + "\nContent-Type: text/plain\n\n");
        httpRequestBodyWriter.flush();

        String licenseFileTemplateContent = replaceTags(licenseFile, user);
        httpRequestBodyWriter.write(licenseFileTemplateContent);

        // Mark the end of the multipart http request
        httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
        httpRequestBodyWriter.flush();

        // Close the streams
        outputStream.close();
        httpRequestBodyWriter.close();

        //get response
        checkResponse(connection);

        final String response = readAll(connection);
        return JsonContentUtils.parseJsonToMap(response);
    }

    @Override
    public Map<String, String> encryptLicense(String licenseType, User user) throws IOException, JSONException {

        if (!isActive()) {
            LOG.warn("Unable to generate license files as the LicenseManager is not active!");
            return Collections.emptyMap();
        }
        if (user == null || user.isDefaultUser()) return Collections.emptyMap();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Bearer " + getAccessToken());

        String queryParameters = String.format("first_name=%s&last_name=%s&email=%s",
                URLEncoder.encode(user.getFirstName(), StandardCharsets.UTF_8.name()),
                URLEncoder.encode(user.getLastName(), StandardCharsets.UTF_8.name()),
                URLEncoder.encode(user.getEmailAddress(), StandardCharsets.UTF_8.name()));
        HttpURLConnection connection = getConnection(getBasePath() + licenseType + "?" + queryParameters, "GET", headers);
        checkResponse(connection);

        final String response = readAll(connection);
        return JsonContentUtils.parseJsonToMap(response);

    }

    private String replaceTags(LicenseFile licenseFile, User user) {
        String licenseFileTemplateContent = licenseFile.getTemplateContent();
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("@FIRSTNAME@", user.getFirstName());
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("@LASTNAME@", user.getLastName());
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("@EMAIL@", user.getEmailAddress());

        final SimpleDateFormat dateFormat = new SimpleDateFormat(licenseFile.getDateFormat());
        final String expirationDate = dateFormat.format(new Date(System.currentTimeMillis() + licenseFile.getExpirationPeriodInMillis()));
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("@EXPIRATIONDATE@", expirationDate);

        return licenseFileTemplateContent;
    }

    private String getAccessToken() {

        String CACHED_TOKEN_KEY = "license.token";
        String CACHED_EXPIRY_KEY = "license.expirytime";
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
                cacheAccessTokens("license", parsedToken);
            }
            return parsedToken.get("access_token");
        } catch (IOException | JSONException e) {
            clearAccessTokens("license");
            LOG.error("Failed to get access token: " + e.getMessage());
        }

        return null;
    }

    private String getBasePath() {
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

    private Map<String, String> getOAuthParameters() {
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("client_id", getProperty("license.clientid"));
        pathParameters.put("client_secret", getProperty("license.clientsecret"));

        final String refreshToken = getCachedToken("license.refresh.token", "license.refresh.expirytime");
        if (refreshToken != null) {
            pathParameters.put("grant_type", "refresh_token"); // use refresh token to close previous session
            pathParameters.put("refresh_token", refreshToken); // use refresh token to close previous session
        } else {
            pathParameters.put("grant_type", "client_credentials");
        }
        return pathParameters;
    }
}
