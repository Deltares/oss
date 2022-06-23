package nl.deltares.portal.utils.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component(
        immediate = true,
        service = DownloadUtils.class
)
public class DownloadUtilsImpl extends HttpClientUtils implements DownloadUtils {

    private final long maxProcessingTime = TimeUnit.MINUTES.toMillis(10);

    public enum DOWNLOAD_STATUS {payment_pending, available, expired, unknown, invalid, processing}

    private final Document DUMMY;
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

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document dummy = null;
        try {
            dummy = factory.newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            LOG.warn("Error creating dummy document: " + e.getMessage());
        }
        this.DUMMY = dummy;

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
        params.put("shareType", String.valueOf(3)); //3 - public, 4 - share by email
        final String password = PasswordUtils.getPassword(passwordLength);
        params.put("password", password);
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
        if (expNode.getLength() > 0) {
            final String expDate = expNode.item(0).getTextContent();
            shareInfo.put("expiration", dateFormat.parse(expDate));
        }
        final NodeList tokenNode = document.getElementsByTagName(("token"));
        if (tokenNode.getLength() > 0){
            final String token = tokenNode.item(0).getTextContent();
            shareInfo.put("url", tokenToShareLinkUrl(token));
            shareInfo.put("password", password);
        }

        return shareInfo;
    }

    @Override
    public void deleteShareLink(int shareId) throws Exception {
        String path = API_PATH + "files_sharing/api/v1/shares/" + shareId;
        HttpURLConnection connection = getConnection(path, "DELETE", getDefaultHeaders());

        checkResponse(connection);
    }

    public String directDownloadExists(long downloadId, long userId, long groupId) {
        final nl.deltares.oss.download.model.Download download = DownloadLocalServiceUtil.fetchUserDownload(groupId, userId, downloadId);
        if (download == null) return null;

        //check if expired
        final Date expiryDate = download.getExpiryDate();
        if (expiryDate != null && expiryDate.getTime() < System.currentTimeMillis()) return null;
        final String directDownloadUrl = download.getDirectDownloadUrl();
        return directDownloadUrl != null && directDownloadUrl.isEmpty() ? null : directDownloadUrl;

    }

    @Override
    public Document getFileShares(String filePath) throws Exception {
        String path = API_PATH + "files_sharing/api/v1/shares?reshares=true&path=" + URLEncoder.encode(filePath, StandardCharsets.UTF_8.name());
        HttpURLConnection connection = getConnection(path, "GET", getDefaultHeaders());

        if (connection.getResponseCode() == 400) {
            return null;
        }
        checkResponse(connection);

        final String xmlResponse = readAll(connection);
        return XmlContentUtils.parseContent(DownloadUtilsImpl.class.getName(), xmlResponse);
    }

    @Override
    public Map<String, Object> shareLinkExists(String filePath, String email) throws Exception {

        final Document document = getFileShares(filePath);
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
    public Map<String, Object> resendShareLink(int shareId) throws Exception {

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
        shareInfo.put("url", tokenToShareLinkUrl(document.getElementsByTagName("token").item(0).getTextContent()));
        return shareInfo;

    }

    @Override
    public void registerDownload(User user, long groupId, long downloadId, String filePath, String directDownloadUrl, Map<String, String> userAttributes) {

        final HashMap<String, Object> shareInfo = new HashMap<>();
        shareInfo.put("url", directDownloadUrl);
        shareInfo.put("expiration", new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(8)));
        shareInfo.put("id", -8);
        registerDownload(user, groupId, downloadId, filePath, shareInfo, userAttributes);

    }

    @Override
    public void registerDownload(User user,long groupId, long downloadId, String filePath, Map<String, Object> shareInfo, Map<String, String> userAttributes) {
        nl.deltares.oss.download.model.Download userDownload = DownloadLocalServiceUtil.fetchUserDownload(groupId, user.getUserId(), downloadId);
        if (userDownload == null) {
            userDownload = DownloadLocalServiceUtil.createDownload(CounterLocalServiceUtil.increment(
                    nl.deltares.oss.download.model.Download.class.getName()));

            userDownload.setCompanyId(user.getCompanyId());
            userDownload.setGroupId(groupId);
            userDownload.setUserId(user.getUserId());
            userDownload.setDownloadId(downloadId);

            userDownload.setCreateDate(new Date(System.currentTimeMillis()));
            userDownload.setFilePath(filePath);

        }
        userDownload.setModifiedDate(new Date(System.currentTimeMillis()));
        if (!userAttributes.isEmpty()) {
            userDownload.setOrganization(userAttributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()));
            userDownload.setCity(userAttributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()));
            final String country = userAttributes.get(KeycloakUtils.ATTRIBUTES.org_country.name());
            try {
                if (country != null) userDownload.setCountryCode(CountryServiceUtil.getCountryByName(country).getA2());
            } catch (PortalException e) {
                LOG.warn("Invalid country name " + country);
            }
        }
        if (!shareInfo.isEmpty()) {
            //Share info can be missing when user has not yet paid.
            final Object expiration = shareInfo.get("expiration");
            if (expiration != null) {
                userDownload.setExpiryDate(((Date) expiration));
            }
            final Object shareId = shareInfo.get("id");
            if (shareId != null) {
                userDownload.setShareId((int) shareId);
            } else {
                userDownload.setShareId(-8);
            }
            final Object url = shareInfo.get("url");
            if (url != null) {
                userDownload.setDirectDownloadUrl((String) url);
            }
        }
        DownloadLocalServiceUtil.updateDownload(userDownload);

        if (userDownload.getShareId() >= -1) { //request completed
            incrementDownloadCount(user.getCompanyId(), groupId, downloadId);
        }

    }

    @Override
    public void incrementDownloadCount(long companyId, long groupId, long downloadId) {
        //Update statistics

        DownloadCount downloadCount = DownloadCountLocalServiceUtil.getDownloadCount(groupId, downloadId);
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
    public void updatePendingShares(User user, long groupId) {
        final List<nl.deltares.oss.download.model.Download> byPendingUserDownloads = getDownloadRecords(user, groupId, -1);

        HashMap<String, Document> cache = new HashMap<>();
        byPendingUserDownloads.forEach(download -> {
            try {

                Document document = cache.get(download.getFilePath());
                if (document == null) {
                    document = getFileShares(download.getFilePath());
                    cache.put(download.getFilePath(), document == null ? DUMMY : document);

                }
                String email = getEmailForDownload(user, download);
                if (email == null) return;

                if (document == null) return;
                if (extractShareInformation(download, document, email)) {
                    DownloadLocalServiceUtil.updateDownload(download);
                    LOG.info(String.format("Updated pending download %s for user %s.", download.getFilePath(), email));
                }

            } catch (Exception e) {
                LOG.warn(String.format("Error checking for shares of %s: %s", download.getFilePath(),  e.getMessage()));
            }
        });
    }

    @Override
    public void updateProcessingShares(User user, long groupId) {
        final List<nl.deltares.oss.download.model.Download> processing = getDownloadRecords(user, groupId, -9);

        HashMap<String, Document> cache = new HashMap<>();
        processing.forEach(download -> {
            try {
                if (!isTimedOut(download)) return; //still have time to complete

                Document document = cache.get(download.getFilePath());
                if (document == null) {
                    document = getFileShares(download.getFilePath());
                    cache.put(download.getFilePath(), document == null ? DUMMY : document);
                }
                String email = getEmailForDownload(user, download);
                if (document == null || email == null) {
                    download.setShareId(0); //start by setting to 0
                } else if (extractShareInformation(download, document, email)) { //try to get info from the server
                    LOG.info(String.format("Updated pending download %s for user %s.", download.getFilePath(), email));
                } else {
                    download.setShareId(0); //start by setting to 0
                }
                DownloadLocalServiceUtil.updateDownload(download);
            } catch (Exception e) {
                LOG.warn(String.format("Error checking for shares of %s: %s", download.getFilePath(),  e.getMessage()));
            }
        });
    }

    private boolean extractShareInformation(nl.deltares.oss.download.model.Download download, Document document, String email) throws ParseException {
        final NodeList emailNodes = document.getElementsByTagName("share_with");
        if (emailNodes.getLength() == 0) return false;

        int shareIndex = -1;
        for (int i = 0; i < emailNodes.getLength(); i++) {
            if (!emailNodes.item(i).getTextContent().equals(email)) continue;
            shareIndex = i;
            break;
        }
        if (shareIndex == -1) return false;
        final NodeList shareIdNodes = document.getElementsByTagName("id");
        final int shareId = Integer.parseInt(shareIdNodes.item(shareIndex).getTextContent());
        final NodeList expNodes = document.getElementsByTagName("expiration");
        final Date expiration = dateFormat.parse(expNodes.item(shareIndex).getTextContent());

        download.setShareId(shareId);
        download.setExpiryDate(expiration);
        return true;
    }

    private String getEmailForDownload(User user, nl.deltares.oss.download.model.Download download) {
        String email = null;
        if (user != null){
            email = user.getEmailAddress();
        } else {
            final User userOfDownload = UserLocalServiceUtil.fetchUser(download.getUserId());
            if (userOfDownload != null) email = userOfDownload.getEmailAddress();
        }
        return email;
    }

    private List<nl.deltares.oss.download.model.Download> getDownloadRecords(User user, long groupId, int shareId) {
        final List<nl.deltares.oss.download.model.Download> byPendingUserDownloads;
        if (user == null){
            byPendingUserDownloads = DownloadLocalServiceUtil.findDownloadsByShareId(groupId, shareId);
        } else {
            byPendingUserDownloads = DownloadLocalServiceUtil.findUserDownloadsByShareId(groupId, user.getUserId(), shareId);
        }
        return byPendingUserDownloads;
    }

    @Override
    public int getDownloadCount(Download download) {
        final DownloadCount downloadCount = DownloadCountLocalServiceUtil.getDownloadCount(download.getGroupId(), Long.parseLong(download.getArticleId()));
        if (downloadCount == null) return 0;

        return downloadCount.getCount();
    }

    @Override
    public String getDownloadStatus(Download download, User user) {
        if (user == null || user.isDefaultUser()) return DOWNLOAD_STATUS.unknown.name();

        nl.deltares.oss.download.model.Download dbDownload = DownloadLocalServiceUtil.fetchUserDownload(
                download.getGroupId(), user.getUserId(), Long.parseLong(download.getArticleId()));
        if (dbDownload == null) return DOWNLOAD_STATUS.unknown.name();

        if (dbDownload.getShareId() == -9) return DOWNLOAD_STATUS.processing.name();
        if (dbDownload.getShareId() == -1 && download.isBillingRequired()) return DOWNLOAD_STATUS.payment_pending.name();
        if (dbDownload.getShareId() > 0) {
            if (isExpired(dbDownload)) return DOWNLOAD_STATUS.expired.name();
            return DOWNLOAD_STATUS.available.name();
        }
        if (dbDownload.getDirectDownloadUrl() != null && !dbDownload.getDirectDownloadUrl().isEmpty()) {
            if (isExpired(dbDownload)) return DOWNLOAD_STATUS.expired.name();
            return DOWNLOAD_STATUS.available.name();
        }
        return DOWNLOAD_STATUS.invalid.name();
    }

    private boolean isTimedOut(nl.deltares.oss.download.model.Download dbDownload){
        final long timeNow = System.currentTimeMillis();
        final Date startProcessing = dbDownload.getModifiedDate();
        return startProcessing != null && (startProcessing.getTime() + maxProcessingTime) < timeNow;
    }

    private boolean isExpired(nl.deltares.oss.download.model.Download dbDownload) {
        final long timeNow = System.currentTimeMillis();
        final Date expiryDate = dbDownload.getExpiryDate();
        return expiryDate != null && expiryDate.getTime() < timeNow;
    }

    @Override
    public boolean isPaymentPending(Download download, User user) {
        if (!download.isBillingRequired()) return false;
        if (user == null || user.isDefaultUser()) return false;

        final nl.deltares.oss.download.model.Download dbDownload = DownloadLocalServiceUtil.fetchUserDownload(
                download.getGroupId(), user.getUserId(), Long.parseLong(download.getArticleId()));
        return dbDownload != null && dbDownload.getShareId() == -1;
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

    private String tokenToShareLinkUrl(String token){
        if (SHARE_PATH == null) return token;
        return SHARE_PATH.concat(token);
    }
}
