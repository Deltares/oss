package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component(
        immediate = true,
        service = DownloadUtils.class
)
public class DownloadUtilsImpl extends HttpClientUtils implements DownloadUtils {

    @SuppressWarnings("FieldCanBeLocal")
    private final int passwordLength = 10;

    private static final Log LOG = LogFactoryUtil.getLog(DownloadUtilsImpl.class);

    private static final String BASEURL_KEY = "download.baseurl";
    private static final String APPNAME_KEY = "download.app.name";
    private static final String APPPW_KEY = "download.app.password";
    private final String AUTH_TOKEN;
    private final String API_PATH;
    private final boolean active;

    public DownloadUtilsImpl() {
        String APP_NAME = PropsUtil.get(APPNAME_KEY);
        String APP_PW = PropsUtil.get(APPPW_KEY);
        API_PATH = getDownloadBasePath();
        active = APP_NAME != null && APP_PW != null && API_PATH != null;
        if (active) {
            AUTH_TOKEN = getBasicAuthorization(APP_NAME, APP_PW);
            LOG.info(String.format("DownloadUtils has been initialized for APP_NAME '%s' and API_PATH '%s'.", APP_NAME, API_PATH));
        } else {
            AUTH_TOKEN = null;
            LOG.info("DownloadUtils has not been initialized.");
        }

    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getDirectDownloadLink(long fileId) throws Exception {

        HashMap<String, String> headers = getDefaultHeaders();

        String directDownloadPath = API_PATH + "dav/api/v1/direct";
        HttpURLConnection connection;
        try {
            connection = getConnection(directDownloadPath, "POST", headers);
        } catch (IOException e) {
            throw new Exception("Failed to connect to download server: " + e.getMessage());
        }
        connection.setDoOutput(true);
        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            w.write(String.format("{\"fileId\":%d}", fileId));
//            w.write("fileId=" + fileId);
        }
        checkResponse(connection);

        final String xmlResponse = readAll(connection);
        final Document document = XmlContentUtils.parseContent(DownloadUtilsImpl.class.getName(), xmlResponse);

        final NodeList urlNode = document.getElementsByTagName("url");
        if (urlNode.getLength() == 0) {
            LOG.error("Failed to return URL link for fileId " + fileId);
            return null;
        }
        return urlNode.item(0).getTextContent();
    }

    @Override
    public int sendShareLink(String filePath, String email) throws Exception {
        String directDownloadPath = API_PATH + "files_sharing/api/v1/shares";
        HttpURLConnection connection = getConnection(directDownloadPath, "POST", getDefaultHeaders());
        connection.setDoOutput(true);

        final HashMap<String, String> params = new HashMap<>();
        params.put("path", filePath);
        params.put("shareType", String.valueOf(4));
        params.put("password", PasswordUtils.getPassword(passwordLength));
        params.put("shareWith", email);
        params.put("permissions", String.valueOf(1));

        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            //noinspection ConstantConditions
            w.write(JsonContentUtils.formatMapToJson(params));
        }
        checkResponse(connection);

        final String xmlResponse = readAll(connection);
        final Document document = XmlContentUtils.parseContent(DownloadUtilsImpl.class.getName(), xmlResponse);

        final NodeList idNode = document.getElementsByTagName("id");
        if (idNode.getLength() == 0) {
            LOG.error("Failed to create a share for file " + filePath);
            return -1;
        } else {
            LOG.info(String.format("Created share for user '%s' on file '%s'.", email, filePath));
        }
        return Integer.parseInt(idNode.item(0).getTextContent());
    }

    @Override
    public void deleteShareLink(int shareId) throws Exception {
        String path = API_PATH + "files_sharing/api/v1/shares/" + shareId;
        HttpURLConnection connection = getConnection(path, "DELETE", getDefaultHeaders());

        checkResponse(connection);
    }

    @Override
    public int shareLinkExists(String filePath, String email) throws Exception {
        String path = API_PATH + "files_sharing/api/v1/shares?path=" + filePath;
        HttpURLConnection connection = getConnection(path, "GET",  getDefaultHeaders());

        if (connection.getResponseCode() == 400) return -1;
        checkResponse(connection);

        final String xmlResponse = readAll(connection);
        final Document document = XmlContentUtils.parseContent(DownloadUtilsImpl.class.getName(), xmlResponse);

        final NodeList emailNodes = document.getElementsByTagName("share_with");
        if (emailNodes.getLength() == 0) {
            return -1;
        }

        int shareIndex = -1;
        for (int i = 0; i < emailNodes.getLength(); i++) {
            if (!emailNodes.item(i).getTextContent().equals(email)) continue;
            shareIndex = i;
            break;
        }
        if (shareIndex == -1) return shareIndex;
        final NodeList shareIdNodes = document.getElementsByTagName("id");
        return Integer.parseInt(shareIdNodes.item(shareIndex).getTextContent());
    }

    @Override
    public int resendShareLink(int shareId) throws Exception{

        //Get info from existing share
        final Map<String, String> existingShare = getShareLinkInfo(shareId);
        //Delete the old share, to force resending emails
        deleteShareLink(Integer.parseInt(existingShare.get("id")));
        //Create a new share
        return sendShareLink(existingShare.get("path"), existingShare.get("email"));
    }

    @Override
    public Map<String, String> getShareLinkInfo(int shareId) throws Exception {
        String path = API_PATH + "files_sharing/api/v1/shares/" + shareId;
        HttpURLConnection connection = getConnection(path, "GET", getDefaultHeaders());

        checkResponse(connection);

        final String xmlResponse = readAll(connection);
        final Document document = XmlContentUtils.parseContent(DownloadUtilsImpl.class.getName(), xmlResponse);

        final HashMap<String, String> shareInfo = new HashMap<>();
        //Response should always have an element otherwise exception would have been thrown.
        shareInfo.put("email", document.getElementsByTagName("share_with").item(0).getTextContent());
        shareInfo.put("id", document.getElementsByTagName("id").item(0).getTextContent());
        shareInfo.put("path", document.getElementsByTagName("path").item(0).getTextContent());
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

    private String getDownloadBasePath() {
        if (!PropsUtil.contains(BASEURL_KEY)) {
            return null;
        }
        String baseApiPath = PropsUtil.get(BASEURL_KEY);

        if (baseApiPath.endsWith("/")) {
            return baseApiPath;
        }
        baseApiPath += '/';
        return baseApiPath;
    }
}
