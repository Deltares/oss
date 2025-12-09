package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

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
        if (user == null || user.isGuestUser()) return Collections.emptyMap();

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
        if (user == null || user.isGuestUser()) return Collections.emptyMap();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Bearer " + getAccessToken());

        String queryParameters = String.format("first_name=%s&last_name=%s&email=%s",
                URLEncoder.encode(user.getFirstName(), StandardCharsets.UTF_8),
                URLEncoder.encode(user.getLastName(), StandardCharsets.UTF_8),
                URLEncoder.encode(user.getEmailAddress(), StandardCharsets.UTF_8));
        HttpURLConnection connection = getConnection(getBasePath() + licenseType + "?" + queryParameters, "GET", headers);
        checkResponse(connection);

        final String response = readAll(connection);
        return JsonContentUtils.parseJsonToMap(response);

    }

    @Override
    public JSONArray getCustomerLicenses(User user, String state) throws IOException, JSONException {

        if (!isActive()) {
            LOG.warn("Unable to retrieve license files as the LicenseManager is not active!");
            return null;
        }
        if (user == null || user.isGuestUser()) return null;

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());

        String queryParameters = String.format("?email=%s",URLEncoder.encode(user.getEmailAddress(), StandardCharsets.UTF_8));
        HttpURLConnection connection = getConnection(getBasePath() + "clm/customers/contact/search" + queryParameters, "GET", headers);
        checkResponse(connection);

        String customerContactsViewResponse = readAll(connection);
        JSONArray customerContactsArray = JsonContentUtils.parseContentArray(customerContactsViewResponse);
        Long[] customerIds = parseCustomerIds(customerContactsArray);

        if (customerIds.length == 0){
            LOG.warn(String.format("Found no customer ID for CLM user %s!", user.getEmailAddress()));
            return null;
        }
        if (customerIds.length > 1){
            LOG.warn(String.format("Found more than one customer ID for CLM user %s!", user.getEmailAddress()));
        }

        if (state==null) {
            state = "Active";
        }
        queryParameters = String.format("?state=%s", state);

        connection = getConnection(getBasePath() + "clm/customers/" + customerIds[0] + "/subscriptions/suites" + queryParameters, "GET", headers);
        checkResponse(connection);

        return JsonContentUtils.parseContentArray(readAll(connection));

    }

    private Long[] parseCustomerIds(JSONArray customerContactsArray) {

        List<Long> ids = new ArrayList<>(customerContactsArray.length());
        for (int i = 0; i < customerContactsArray.length(); i++) {
            JSONObject customerContactView = customerContactsArray.getJSONObject(i);
            long customerContactCustomerId = customerContactView.getLong("customerContactCustomerId");
            if (!ids.contains(customerContactCustomerId)) {ids.add(customerContactCustomerId);}
        }
        return ids.toArray(new Long[0]);
    }

    private String replaceTags(LicenseFile licenseFile, User user) {
        String licenseFileTemplateContent = licenseFile.getTemplateContent();
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("@FIRSTNAME@", user.getFirstName());
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("@LASTNAME@", user.getLastName());
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("@EMAIL@", user.getEmailAddress());

        final SimpleDateFormat dateFormat = new SimpleDateFormat(licenseFile.getDateFormat());
        final String expirationDate = dateFormat.format(licenseFile.getExpirationDate());
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
