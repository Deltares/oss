package nl.deltares.portal.utils.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.KeycloakUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class AbsDownloadUtilsImpl extends HttpClientUtils implements DownloadUtils {

    protected static final String BASEURL_KEY = "download.baseurl";
    protected static final String APP_NAME_KEY = "download.app.name";
    protected static final String APP_USER_KEY = "download.app.user";
    protected static final String APP_PW_KEY = "download.app.password";

    protected String AUTH_TOKEN;
    protected HashMap<String, String> AUTH_TOKEN_MAP = new HashMap<>();
    protected String API_PATH;
    protected HashMap<String, String> API_PATH_MAP = new HashMap<>();

    protected boolean active;

    @Override
    public boolean isActive() {
        return active;
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

        incrementDownloadCount(user.getCompanyId(), groupId, downloadId);
        if (user.isGuestUser()){
            return; //don't register anonymous downloads
        }

        nl.deltares.oss.download.model.Download userDownload = DownloadLocalServiceUtil.fetchUserDownload(groupId, user.getUserId(), downloadId);
        if (userDownload == null){
            userDownload = DownloadLocalServiceUtil.createDownload(CounterLocalServiceUtil.increment(
                    nl.deltares.oss.download.model.Download.class.getName()));

            userDownload.setCompanyId(user.getCompanyId());
            userDownload.setGroupId(groupId);
            userDownload.setUserId(user.getUserId());
            userDownload.setDownloadId(downloadId);
            userDownload.setCreateDate(new Date(System.currentTimeMillis()));
            if (userAttributes.containsKey("geoLocationId")) {
                userDownload.setGeoLocationId(Long.parseLong(userAttributes.get("geoLocationId")));
            }
        }

        userDownload.setFileName(fileName);
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
        if (download == null) return 0;
        final DownloadCount downloadCount = DownloadCountLocalServiceUtil.getDownloadCountByGroupId(download.getGroupId(), Long.parseLong(download.getArticleId()));
        if (downloadCount == null) return 0;

        return downloadCount.getCount();
    }

    protected String getDownloadBasePath() {
        if (!PropsUtil.contains(AbsDownloadUtilsImpl.BASEURL_KEY)) {
            return null;
        }
        String baseApiPath = PropsUtil.get(AbsDownloadUtilsImpl.BASEURL_KEY);

        if (baseApiPath.endsWith("/")) {
            return baseApiPath;
        }
        baseApiPath += '/';
        return baseApiPath;
    }

    @Override
    public boolean hasMultipleDownloadUrls(){
        final Properties properties = PropsUtil.getProperties(BASEURL_KEY, false);
        return properties.size() > 1;
    }

    @Override
    public List<String> getDownloadServerCountryCodes() {
        return new ArrayList<>(API_PATH_MAP.keySet());
    }

    @Override
    public String getDownloadServerCountryName(String countryCode) {
        final Country country = CountryLocalServiceUtil.fetchCountryByA2(PortalUtil.getDefaultCompanyId(), countryCode);
        return country != null ? country.getName() : "";
    }
}
