package nl.deltares.portal.utils.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component(
        immediate = true,
        service = DownloadUtils.class
)
public class DownloadUtilsImpl extends HttpClientUtils implements DownloadUtils {

    @SuppressWarnings("FieldCanBeLocal")
    private final int passwordLength = 10;

    private static final Log LOG = LogFactoryUtil.getLog(DownloadUtilsImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private static final String BASEURL_KEY = "download.baseurl";
    private static final String APP_NAME_KEY = "download.app.name";
    private static final String APP_PW_KEY = "download.app.password";
    private final String AUTH_TOKEN;
    private final String API_PATH;
    private final String SHARE_PATH;
    private final boolean active;

    public DownloadUtilsImpl() {
        String APP_NAME = PropsUtil.get(APP_NAME_KEY);
        String APP_PW = PropsUtil.get(APP_PW_KEY);
        API_PATH = getDownloadBasePath();
        SHARE_PATH = getSharePath();
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
        final Document document = XmlContentUtils.parseContent(DownloadUtilsImpl.class.getName(), xmlResponse);

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

    @Override
    public void registerDownload(User user, long groupId, long downloadId, String fileName, String fileShare, Map<String, String> userAttributes) {

        final HashMap<String, String> shareInfo = new HashMap<>();
        shareInfo.put("url", fileShare);
        shareInfo.put("expiration", String.valueOf(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(8)));
        shareInfo.put("id", "-1");
        registerDownload(user, groupId, downloadId, fileName, shareInfo, userAttributes);

    }

    @Override
    public void registerDownload(User user, long groupId, long downloadId, String fileName, Map<String, String> shareInfo, Map<String, String> userAttributes) {
        nl.deltares.oss.download.model.Download userDownload = DownloadLocalServiceUtil.fetchUserDownload(groupId, user.getUserId(), downloadId);
        if (userDownload == null) {
            userDownload = DownloadLocalServiceUtil.createDownload(CounterLocalServiceUtil.increment(
                    nl.deltares.oss.download.model.Download.class.getName()));

            userDownload.setCompanyId(user.getCompanyId());
            userDownload.setGroupId(groupId);
            userDownload.setUserId(user.getUserId());
            userDownload.setDownloadId(downloadId);

            userDownload.setCreateDate(new Date(System.currentTimeMillis()));
            userDownload.setFileName(fileName);

            if (userAttributes.containsKey("geoLocationId")) {
                userDownload.setGeoLocationId(Long.parseLong(userAttributes.get("geoLocationId")));
            }
        }
        userDownload.setModifiedDate(new Date(System.currentTimeMillis()));
        if (!userAttributes.isEmpty()) {
            userDownload.setOrganization(userAttributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()));
        }
        if (!shareInfo.isEmpty()) {
            //Share info can be missing when user has not yet paid.
            final String expiration = shareInfo.get("expiration");
            if (expiration != null) {
                userDownload.setExpiryDate(new Date(Long.parseLong(expiration)));
            }

            String url = shareInfo.get("url");
            if (url != null) {
                final String password = shareInfo.get("password");
                if (password != null) url = url.concat(" ( ").concat(password).concat(" )");
                userDownload.setFileShareUrl(url);
            }
            String licUrl = shareInfo.get("licUrl");
            if (licUrl != null) {
                userDownload.setLicenseDownloadUrl(licUrl);
            }
        }
        DownloadLocalServiceUtil.updateDownload(userDownload);

        if (userDownload.getFileShareUrl() != null) { //request completed
            incrementDownloadCount(user.getCompanyId(), groupId, downloadId);
        }

    }

    @Override
    public void incrementDownloadCount(long companyId, long groupId, long downloadId) {
        //Update statistics

        DownloadCount downloadCount = DownloadCountLocalServiceUtil.getDownloadCountByGroupId(groupId, downloadId);
        if (downloadCount == null) {
            downloadCount = DownloadCountLocalServiceUtil.createDownloadCount(CounterLocalServiceUtil.increment(
                    nl.deltares.oss.download.model.DownloadCount.class.getName()));
            downloadCount.setDownloadId(downloadId);
            downloadCount.setGroupId(groupId);
            downloadCount.setCompanyId(companyId);
        }
        int count = downloadCount.getCount();
        downloadCount.setCount(++count);
        DownloadCountLocalServiceUtil.updateDownloadCount(downloadCount);
    }

    @Override
    public int getDownloadCount(Download download) {
        final DownloadCount downloadCount = DownloadCountLocalServiceUtil.getDownloadCountByGroupId(download.getGroupId(), Long.parseLong(download.getArticleId()));
        if (downloadCount == null) return 0;

        return downloadCount.getCount();
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
