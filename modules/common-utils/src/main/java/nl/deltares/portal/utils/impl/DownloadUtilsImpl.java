package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.persistence.CountryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.oss.download.service.persistence.DownloadPK;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
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
    private final boolean active;

    public DownloadUtilsImpl() {
        String APP_NAME = PropsUtil.get(APP_NAME_KEY);
        String APP_PW = PropsUtil.get(APP_PW_KEY);
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
    public Map<String, Object> sendShareLink(String filePath, String email) throws Exception {
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
        final HashMap<String, Object> shareInfo = new HashMap<>();
        if (idNode.getLength() == 0) {
            LOG.error("Failed to create a share for file " + filePath);
            return Collections.emptyMap();
        } else {
            final int shareId = Integer.parseInt(idNode.item(0).getTextContent());
            shareInfo.put("id", shareId);
            LOG.info(String.format("Created share for user '%s' on file '%s'.", email, filePath));
        }
        final NodeList expNode = document.getElementsByTagName("expiration");
        if (expNode.getLength() > 0){
            final String expDate = expNode.item(0).getTextContent();
            shareInfo.put("expiration", dateFormat.parse(expDate));
        }
        return shareInfo;
    }

    @Override
    public void deleteShareLink(int shareId) throws Exception {
        String path = API_PATH + "files_sharing/api/v1/shares/" + shareId;
        HttpURLConnection connection = getConnection(path, "DELETE", getDefaultHeaders());

        checkResponse(connection);
    }

    public String directDownloadExists(long downloadId, long userId) {
        final nl.deltares.oss.download.model.Download download = DownloadLocalServiceUtil.fetchDownload(new DownloadPK(downloadId, userId));
        if (download == null) return null;

        //check if expired
        if (download.getExpiryDate().before(new Date(System.currentTimeMillis()))) return null;
        return download.getDirectDownloadUrl();

    }

    @Override
    public Map<String, Object> shareLinkExists(String filePath, String email) throws Exception {
        String path = API_PATH + "files_sharing/api/v1/shares?path=" + URLEncoder.encode(filePath, StandardCharsets.UTF_8.name());
        HttpURLConnection connection = getConnection(path, "GET",  getDefaultHeaders());

        if (connection.getResponseCode() == 400) return Collections.emptyMap();
        checkResponse(connection);

        final String xmlResponse = readAll(connection);
        final Document document = XmlContentUtils.parseContent(DownloadUtilsImpl.class.getName(), xmlResponse);

        final NodeList emailNodes = document.getElementsByTagName("share_with");
        if (emailNodes.getLength() == 0) {
            return Collections.emptyMap();
        }

        int shareIndex = -1;
        for (int i = 0; i < emailNodes.getLength(); i++) {
            if (!emailNodes.item(i).getTextContent().equals(email)) continue;
            shareIndex = i;
            break;
        }
        if (shareIndex == -1) return Collections.emptyMap();
        final NodeList shareIdNodes = document.getElementsByTagName("id");

        final HashMap<String, Object> shareInfo = new HashMap<>();
        final int shareId = Integer.parseInt(shareIdNodes.item(shareIndex).getTextContent());
        shareInfo.put("id", shareId);
        final NodeList expNodes = document.getElementsByTagName("expiration");
        final Date expiration = dateFormat.parse(expNodes.item(shareIndex).getTextContent());
        shareInfo.put("expiration", expiration);
        return shareInfo;
    }

    @Override
    public Map<String, Object> resendShareLink(int shareId) throws Exception{

        //Get info from existing share
        final Map<String, Object> existingShare = getShareLinkInfo(shareId);
        //Delete the old share, to force resending emails
        deleteShareLink((Integer) existingShare.get("id"));
        //Create a new share
        return sendShareLink(existingShare.get("path").toString(), existingShare.get("email").toString());
    }

    @Override
    public Map<String, Object> getShareLinkInfo(int shareId) throws Exception {
        String path = API_PATH + "files_sharing/api/v1/shares/" + shareId;
        HttpURLConnection connection = getConnection(path, "GET", getDefaultHeaders());

        checkResponse(connection);

        final String xmlResponse = readAll(connection);
        final Document document = XmlContentUtils.parseContent(DownloadUtilsImpl.class.getName(), xmlResponse);

        final HashMap<String, Object> shareInfo = new HashMap<>();
        //Response should always have an element otherwise exception would have been thrown.
        shareInfo.put("email", document.getElementsByTagName("share_with").item(0).getTextContent());
        shareInfo.put("id", Integer.parseInt(document.getElementsByTagName("id").item(0).getTextContent()));
        shareInfo.put("path", document.getElementsByTagName("path").item(0).getTextContent());
        return shareInfo;

    }

    @Override
    public void registerDownload(User user, long downloadId, String directDownloadUrl, Map<String, String> userAttributes) {

        final HashMap<String, Object> shareInfo = new HashMap<>();
        shareInfo.put("url", directDownloadUrl);
        shareInfo.put("expiration", new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(8)));

        registerDownload(user, downloadId, shareInfo, userAttributes);

    }

    @Override
    public void registerDownload(User user, long downloadId, Map<String, Object> shareInfo, Map<String, String> userAttributes) {
        nl.deltares.oss.download.model.Download userDownload = DownloadLocalServiceUtil.fetchDownload(new DownloadPK(downloadId, user.getUserId()));
        if (userDownload == null){
            userDownload = DownloadLocalServiceUtil.createDownload(new DownloadPK(downloadId, user.getUserId()));
            userDownload.setCreateDate(new Date(System.currentTimeMillis()));
        }
        userDownload.setCompanyId(user.getCompanyId());
        userDownload.setGroupId(user.getGroupId());
        userDownload.setOrganization(userAttributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()));
        userDownload.setCity(userAttributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()));
        final String country = userAttributes.get(KeycloakUtils.ATTRIBUTES.org_country.name());
        userDownload.setCountryCode(CountryUtil.fetchByName(country).getA2());
        userDownload.setModifiedDate(new Date(System.currentTimeMillis()));

        //Share info can be missing when user has not yet paid.
        final Object expiration = shareInfo.get("expiration");
        if (expiration != null) {
            userDownload.setExpiryDate(((Date) expiration));
        }
        final Object shareId = shareInfo.get("id");
        if(shareId != null) {
            userDownload.setShareId( (long) shareId);
        }
        final Object url = shareInfo.get("url");
        if(url != null) {
            userDownload.setDirectDownloadUrl((String) url);
        }
        DownloadLocalServiceUtil.updateDownload(userDownload);

        incrementDownloadCount(downloadId);

    }

    @Override
    public void incrementDownloadCount(long downloadId) {
        //Update statistics
        DownloadCount downloadCount;
        try {
            downloadCount = DownloadCountLocalServiceUtil.getDownloadCount(downloadId);
        } catch (PortalException e) {
            LOG.error("Error getting download count: " + e.getMessage());
            return;
        }
        if (downloadCount == null){
            downloadCount = DownloadCountLocalServiceUtil.createDownloadCount(downloadId);
        }
        int count = downloadCount.getCount();
        downloadCount.setCount(++count);
        DownloadCountLocalServiceUtil.updateDownloadCount(downloadCount);
    }

    @Override
    public void updatePendingShares(User user) {
        final List<nl.deltares.oss.download.model.Download> byPendingUserDownloads = DownloadLocalServiceUtil.getPendingUserDownloads(user.getUserId());

        byPendingUserDownloads.forEach(download -> {
            try {
                final Map<String, Object> existingShare = shareLinkExists(download.getPath(), user.getEmailAddress());
                if (existingShare.isEmpty()) return;

                download.setShareId((Long) existingShare.get("id"));
                download.setExpiryDate((Date) existingShare.get("expiration"));
                DownloadLocalServiceUtil.updateDownload(download);
                LOG.info(String.format("Updated pending download %s for user %s.", download.getPath(), user.getEmailAddress()));
            } catch (Exception e) {
                LOG.warn("Error checking share: " + e.getMessage());
            }
        });
    }

    @Override
    public int getDownloadCount(Download download) {
        final DownloadCount downloadCount = DownloadCountLocalServiceUtil.fetchDownloadCount(Long.parseLong(download.getArticleId()));
        if (downloadCount == null) return 0;

        return downloadCount.getCount();
    }

    @Override
    public boolean isPaymentPending(Download download, ThemeDisplay themeDisplay) {
        if (!download.isBillingRequired()) return false;
        final User user = themeDisplay.getUser();
        if (user.isDefaultUser() || !themeDisplay.isSignedIn()) return false;

        final nl.deltares.oss.download.model.Download dbDownload = DownloadLocalServiceUtil.fetchDownload(new DownloadPK(Long.parseLong(download.getArticleId()), user.getUserId()));
        return dbDownload != null && dbDownload.getShareId() < 1;
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
