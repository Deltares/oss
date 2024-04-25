package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.PasswordUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component(
        immediate = true,
        service = DownloadUtils.class
)
public class NextcloudDownloadUtilsImpl extends AbsDownloadUtilsImpl {

    @SuppressWarnings("FieldCanBeLocal")
    private final int passwordLength = 10;
    private final long THREE_DAYS_MILLIS = TimeUnit.DAYS.toMillis(3);

    private static final Log LOG = LogFactoryUtil.getLog(NextcloudDownloadUtilsImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final SimpleDateFormat expireDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private String SHARE_PATH;

    private final HashMap<String, String> SHARE_PATH_MAP = new HashMap<>();


    public NextcloudDownloadUtilsImpl() {

        if (hasMultipleDownloadUrls()){
            initMultipleUrls();
        } else {
            initSingleUrl();
        }

    }

    private void initMultipleUrls() {
        final Properties urlMap = PropsUtil.getProperties(BASEURL_KEY + '.', true);
        for (Object key : urlMap.keySet()) {
            String countryCode = (String) key;
            String url = urlMap.getProperty(countryCode);
            if (!url.endsWith("/")) {
                url += '/';
            }
            API_PATH_MAP.put(countryCode, url);

            final String sharePath = getSharePath(url);
            SHARE_PATH_MAP.put(countryCode, sharePath);

            final String APP_NAME = PropsUtil.get(APP_NAME_KEY + '.' + countryCode);
            final String APP_USER = PropsUtil.get(APP_USER_KEY + '.' + countryCode);
            final String APP_PW = PropsUtil.get(APP_PW_KEY + '.' + countryCode);

            if (!DownloadUtils.APP_NAME.nextcloud.name().equals(APP_NAME)){
                active = false;
                LOG.info("NextcloudDownloadUtils is not configured.");
                return;
            }
            final String token = getBasicAuthorization(APP_USER, APP_PW);
            AUTH_TOKEN_MAP.put(countryCode, token);
            LOG.info(String.format("DownloadUtils has been initialized for APP_NAME '%s' and API_PATH '%s'.", APP_NAME, url));
        }

    }

    private void initSingleUrl() {

        String APP_NAME = PropsUtil.get(APP_NAME_KEY);
        String APP_USER = PropsUtil.get(APP_USER_KEY);
        String APP_PW = PropsUtil.get(APP_PW_KEY);

        if (!DownloadUtils.APP_NAME.nextcloud.name().equals(APP_NAME)){
            active = false;
            LOG.info("NextcloudDownloadUtils is not configured.");
            return;
        }

        API_PATH = getDownloadBasePath();
        SHARE_PATH = getSharePath(BASEURL_KEY);
        active = APP_USER != null && APP_PW != null && API_PATH != null;
        if (active) {
            AUTH_TOKEN = getBasicAuthorization(APP_USER, APP_PW);
            LOG.info(String.format("DownloadUtils has been initialized for APP_NAME '%s' and API_PATH '%s'.", APP_NAME, API_PATH));
        } else {
            AUTH_TOKEN = null;
            LOG.info("DownloadUtils has not been initialized.");
        }

    }

    @Override
    public Map<String, String> createShareLink(String countryCode, String filePath, String email, boolean passwordProtect) throws Exception {

        if (countryCode == null || countryCode.isEmpty())
            throw new IllegalArgumentException("CountryCode is missing");

        if (!hasMultipleDownloadUrls())
            throw new UnsupportedOperationException("Multiple download servers not supported for this download instance!");

        String directDownloadPath = API_PATH_MAP.get(countryCode) + "files_sharing/api/v1/shares";
        final String authToken = AUTH_TOKEN_MAP.get(countryCode);
        final String sharePath = SHARE_PATH_MAP.get(countryCode);
        return callShareLinkApi(filePath, email, passwordProtect, directDownloadPath, authToken, sharePath);
    }

    @Override
    public Map<String, String> createShareLink(String filePath, String email, boolean passwordProtect) throws Exception {

        String directDownloadPath = API_PATH + "files_sharing/api/v1/shares";
        final String authToken = AUTH_TOKEN;
        final String sharePath = SHARE_PATH;
        return callShareLinkApi(filePath, email, passwordProtect, directDownloadPath, authToken, sharePath);
    }

    private Map<String, String> callShareLinkApi(String filePath, String email, boolean passwordProtect, String directDownloadPath,
                                                 String authToken, String sharePath) throws IOException, PortalException, ParseException {

        HttpURLConnection connection = getConnection(directDownloadPath, "POST", getDefaultHeaders(authToken));
        connection.setDoOutput(true);

        final HashMap<String, String> params = new HashMap<>();
        params.put("path", filePath);
        params.put("shareType", String.valueOf(3)); //3 - public, 4 - share by email
        String password = null;
        if (passwordProtect) {
            password = PasswordUtils.getPassword(passwordLength);
            params.put("password", password);
        }
        params.put("expireDate", expireDateFormat.format(new Date(System.currentTimeMillis() + THREE_DAYS_MILLIS)));
//        params.put("shareWith", email); not required for type 3
        params.put("permissions", String.valueOf(1));

        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            //noinspection ConstantConditions
            w.write(JsonContentUtils.formatMapToJson(params));
        }
        try {
            checkResponse(connection);
        } catch (IOException e) {
            throw new IOException(getErrorMessage(connection));
        }

        final String xmlResponse = readAll(connection);
        final Document document = XmlContentUtils.parseContent(NextcloudDownloadUtilsImpl.class.getName(), xmlResponse);

        final NodeList idNode = document.getElementsByTagName("id");
        final HashMap<String, String> shareInfo = new HashMap<>();
        if (idNode.getLength() == 0) {
            LOG.error("Failed to create a share for file " + filePath);
            return Collections.emptyMap();
        } else {
            final int shareId = Integer.parseInt(idNode.item(0).getTextContent());
            shareInfo.put("id", String.valueOf(shareId));
            LOG.info(String.format("Created share for user '%s' on file '%s'.", email, filePath));
        }
        final NodeList expNode = document.getElementsByTagName("expiration");
        if (expNode.getLength() > 0) {
            final String expDate = expNode.item(0).getTextContent();
            if (!expDate.isEmpty())
                shareInfo.put("expiration", String.valueOf(dateFormat.parse(expDate).getTime()));
        }
        final NodeList tokenNode = document.getElementsByTagName(("token"));
        if (tokenNode.getLength() > 0) {
            final String token = tokenNode.item(0).getTextContent();
            shareInfo.put("url", tokenToShareLinkUrl(sharePath, token));
            if (passwordProtect) {
                shareInfo.put("password", password);
            }
        }

        return shareInfo;
    }

    private static String getErrorMessage(HttpURLConnection connection) throws IOException, JSONException {
        final String s = new String(connection.getErrorStream().readAllBytes());
        final JSONObject jsonObject = JsonContentUtils.parseContent(s);
        final JSONObject ocs = jsonObject.getJSONObject("ocs");
        final JSONObject meta = ocs.getJSONObject("meta");

        return meta.getString("statuscode") + ": " + meta.getString("message");
    }

    private HashMap<String, String> getDefaultHeaders(String authToken) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("OCS-APIRequest", "true");
        headers.put("Content-Type", "application/json");
//        headers.put("Content-Type", " application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic " + authToken);
        return headers;
    }


    private String getSharePath(String baseUrlKey) {
        final int startTrim = baseUrlKey.indexOf("ocs");
        if (startTrim > 0) {
            final String rootUrl = baseUrlKey.substring(0, startTrim);
            return rootUrl.concat("s/");
        }
        return null;
    }
    private String tokenToShareLinkUrl(String sharePath, String token) {
        if (sharePath == null) return token;
        return sharePath.concat(token);
    }
}
