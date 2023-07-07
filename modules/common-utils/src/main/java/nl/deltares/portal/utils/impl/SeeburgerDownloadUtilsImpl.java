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

import java.io.*;
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
public class SeeburgerDownloadUtilsImpl extends HttpClientUtils implements DownloadUtils {

    @SuppressWarnings("FieldCanBeLocal")
    private final int passwordLength = 10;
    @SuppressWarnings("FieldCanBeLocal")
    private final int maxDownloads = 5;
    private final long validPeriodMillis = TimeUnit.DAYS.toMillis(5);
    private static final Log LOG = LogFactoryUtil.getLog(SeeburgerDownloadUtilsImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private static final String BASEURL_KEY = "download.baseurl";
    private static final String APP_NAME_KEY = "download.app.name";
    private static final String APP_USER_KEY = "download.app.user";
    private static final String APP_PW_KEY = "download.app.password";
    private String AUTH_TOKEN;
    private String API_PATH;
    private final boolean active;

    public SeeburgerDownloadUtilsImpl() {
        String APP_NAME = PropsUtil.get(APP_NAME_KEY);
        String APP_USER = PropsUtil.get(APP_USER_KEY);
        String APP_PW = PropsUtil.get(APP_PW_KEY);

        if (!DownloadUtils.APP_NAME.seeburger.name().equals(APP_NAME)){
            active = false;
            LOG.info("SeeburgerDownloadUtils is not configured.");
            return;
        }
        API_PATH = getDownloadBasePath();
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
    public boolean isActive() {
        return active;
    }

    @Override
    public Map<String, String> createShareLink(String filePath, String email, boolean passwordProtect) throws Exception {
        String directDownloadPath = API_PATH + "portal-seefx/ws/FileTransferService3";
        HttpURLConnection connection = getConnection(directDownloadPath, "POST", getDefaultHeaders());
        connection.setDoOutput(true);

        final HashMap<String, String> params = new HashMap<>();
        params.put("FullFilePath", filePath);
        params.put("Recipient", email);
        if (passwordProtect) {
            String password = PasswordUtils.getPassword(passwordLength);
            params.put("TransferPassword", password);
        }
        params.put("MaxNumberOfDownloads", String.valueOf(maxDownloads));
        params.put("ValidUntil", dateFormat.format(new Date(System.currentTimeMillis() + validPeriodMillis)));

        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            w.write(getDownloadPortal(params));
        }

        checkResponse(connection);

        final String xmlResponse = readAll(connection);

        final String urlString = extractXmlTagValue(xmlResponse, "UrlString");
        final HashMap<String, String> shareInfo = new HashMap<>();
        if (urlString.isEmpty()) {
            LOG.error("Failed to create a share for file " + filePath);
            return Collections.emptyMap();
        } else {
            shareInfo.put("url", urlString);
            LOG.info(String.format("Created share for user '%s' on file '%s'.", email, filePath));
            shareInfo.put("expiration", params.get("ValidUntil"));
            if (passwordProtect){
                shareInfo.put("password", params.get("TransferPassword"));
            }
        }

        return shareInfo;
    }

    private String extractXmlTagValue(String xmlResponse, String tagName) {

        String startTag = String.format("<%s>", tagName);
        String endTag = String.format("</%s>", tagName);
        final int startIndex = xmlResponse.indexOf(startTag) + startTag.length();
        final int endIndex = xmlResponse.indexOf(endTag);
        return xmlResponse.substring(startIndex, endIndex);

    }

    private String getDownloadPortal(HashMap<String, String> params) {

        final StringBuilder sb = new StringBuilder();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:shar=\"http://uri.seeburger.com/seefx/fileTransfer/3\">\n");
        sb.append("   <soapenv:Header/>\n");
        sb.append("   <soapenv:Body>\n");
        sb.append("      <shar:CreateDownloadPermit3>\n");
        sb.append(String.format("    <shar:FullFilePath>%s</shar:FullFilePath>\n", params.get("FullFilePath")));
        sb.append("         <shar:PermitRecipients>\n");
        sb.append(String.format("    <shar:Recipient>%s</shar:Recipient>\n", params.get("Recipient")));
        sb.append("         </shar:PermitRecipients>\n");
        sb.append(String.format("    <shar:MaxNumberOfDownloads>%s</shar:MaxNumberOfDownloads>\n", params.get("MaxNumberOfDownloads")));
        if (params.containsKey("TransferPassword\n")) {
            sb.append("     <shar:PasswordProtected>true</shar:PasswordProtected>\n");
            sb.append(String.format("<shar:TransferPassword>%s</shar:TransferPassword>\n", params.get("TransferPassword")));
        }
        sb.append(String.format("<shar:ValidUntil>%s</shar:ValidUntil>\n", params.get("ValidUntil")));
        sb.append("      </shar:CreateDownloadPermit3>\n");
        sb.append("   </soapenv:Body>\n");
        sb.append("</soapenv:Envelope>");
        return sb.toString();
    }

    @Override
    public void registerDownload(User user, long groupId, long downloadId, String fileName, String fileShare, Map<String, String> userAttributes) {

        final HashMap<String, String> shareInfo = new HashMap<>();
        shareInfo.put("url", fileShare);
        shareInfo.put("expiration", String.valueOf(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(8)));
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
                try {
                    userDownload.setExpiryDate(dateFormat.parse(expiration));
                } catch (ParseException e) {
                    LOG.warn(String.format("Error parsing expiration date %s : %s", expiration, e.getMessage()));
                }
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
                    DownloadCount.class.getName()));
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
        headers.put("Content-Type", "application/xml");
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
