package nl.deltares.portal.utils.impl;

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

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Component(
        immediate = true,
        service = DownloadUtils.class
)
public class NextcloudDownloadUtilsImpl extends AbsDownloadUtilsImpl {

    @SuppressWarnings("FieldCanBeLocal")
    private final int passwordLength = 10;

    private static final Log LOG = LogFactoryUtil.getLog(NextcloudDownloadUtilsImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private String SHARE_PATH;


    public NextcloudDownloadUtilsImpl() {
        String APP_NAME = PropsUtil.get(APP_NAME_KEY);
        String APP_USER = PropsUtil.get(APP_USER_KEY);
        String APP_PW = PropsUtil.get(APP_PW_KEY);

        if (!DownloadUtils.APP_NAME.nextcloud.name().equals(APP_NAME)){
            active = false;
            LOG.info("NextcloudDownloadUtils is not configured.");
            return;
        }

        API_PATH = getDownloadBasePath();
        SHARE_PATH = getSharePath();
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
    public Map<String, String> createShareLink(String filePath, String email, boolean passwordProtect) throws Exception {
        String directDownloadPath = API_PATH + "files_sharing/api/v1/shares";
        HttpURLConnection connection = getConnection(directDownloadPath, "POST", getDefaultHeaders());
        connection.setDoOutput(true);

        final HashMap<String, String> params = new HashMap<>();
        params.put("path", filePath);
        params.put("shareType", String.valueOf(3)); //3 - public, 4 - share by email
        String password = null;
        if (passwordProtect) {
            password = PasswordUtils.getPassword(passwordLength);
            params.put("password", password);
        }
//        params.put("shareWith", email); not required for type 3
        params.put("permissions", String.valueOf(1));

        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            //noinspection ConstantConditions
            w.write(JsonContentUtils.formatMapToJson(params));
        }
        checkResponse(connection);

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
            shareInfo.put("expiration", String.valueOf(dateFormat.parse(expDate).getTime()));
        }
        final NodeList tokenNode = document.getElementsByTagName(("token"));
        if (tokenNode.getLength() > 0) {
            final String token = tokenNode.item(0).getTextContent();
            shareInfo.put("url", tokenToShareLinkUrl(token));
            if (passwordProtect) {
                shareInfo.put("password", password);
            }
        }

        return shareInfo;
    }

    private HashMap<String, String> getDefaultHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("OCS-APIRequest", "true");
        headers.put("Content-Type", "application/json");
//        headers.put("Content-Type", " application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic " + AUTH_TOKEN);
        return headers;
    }


    private String getSharePath() {
        if (!PropsUtil.contains(BASEURL_KEY)) {
            return null;
        }
        String baseApiPath = PropsUtil.get(BASEURL_KEY);

        final int startTrim = baseApiPath.indexOf("ocs");
        if (startTrim > 0) {
            final String rootUrl = baseApiPath.substring(0, startTrim);
            return rootUrl.concat("s/");

        }
        return null;
    }

    private String tokenToShareLinkUrl(String token) {
        if (SHARE_PATH == null) return token;
        return SHARE_PATH.concat(token);
    }
}
