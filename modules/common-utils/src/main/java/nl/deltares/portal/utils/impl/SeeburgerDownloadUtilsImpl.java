package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.PasswordUtils;
import org.osgi.service.component.annotations.Component;

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
public class SeeburgerDownloadUtilsImpl extends AbsDownloadUtilsImpl  {

    protected static final String URL_PREFIX_KEY = "download.url.prefix";
    protected String FILE_PATH_PREFIX = "/My Subscriptions";

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

    public SeeburgerDownloadUtilsImpl() {
        String APP_NAME = PropsUtil.get(APP_NAME_KEY);
        String APP_USER = PropsUtil.get(APP_USER_KEY);
        String APP_PW = PropsUtil.get(APP_PW_KEY);
        String URL_PREFIX = PropsUtil.get(URL_PREFIX_KEY);
        if (URL_PREFIX != null){
            FILE_PATH_PREFIX = URL_PREFIX;
        }

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
    public Map<String, String> createShareLink(String filePath, String email, boolean passwordProtect) throws Exception {
        String directDownloadPath = API_PATH + "portal-seefx/ws/FileTransferService3";
        HttpURLConnection connection = getConnection(directDownloadPath, "POST", getDefaultHeaders());
        connection.setDoOutput(true);

        final HashMap<String, String> params = new HashMap<>();
        params.put("FullFilePath", FILE_PATH_PREFIX + filePath);
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

    @Override
    public Map<String, String> createShareLink(String countryCode, String filePath, String email, boolean password) throws Exception {
        throw new UnsupportedOperationException("Seeburger download does not support multiple download servers.");
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

    private HashMap<String, String> getDefaultHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/xml");
        headers.put("Authorization", "Basic " + AUTH_TOKEN);
        return headers;
    }

}
