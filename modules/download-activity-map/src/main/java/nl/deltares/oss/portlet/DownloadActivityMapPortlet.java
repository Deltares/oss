package nl.deltares.oss.portlet;


import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.oss.portlet.constants.ActivityMapPortletKeys;
import nl.deltares.portal.configuration.SiteMapConfiguration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.DownloadActivityMapRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.instanceable=false",
                "com.liferay.portlet.footer-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/activitymap.js",
                "javax.portlet.display-name=Download ActivityMap Portlet",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view-leaflet.jsp",
                "javax.portlet.name=" + ActivityMapPortletKeys.ACTIVITY_MAP,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DownloadActivityMapPortlet extends MVCPortlet {

    @Reference
    DsdParserUtils dsdParserUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(DownloadActivityMapPortlet.class);

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        final String id = getId(themeDisplay);
        final String cachedDownloads = getCachedDownloads(id);
        if (cachedDownloads != null){
            renderRequest.setAttribute("mapdata", cachedDownloads);
        }
        super.render(renderRequest, renderResponse);
    }

    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException {
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        String action = ParamUtil.getString(resourceRequest, "action");

        final String id = getId(themeDisplay);

        if ("start".equals(action)) {
            final String cachedDownloads = getCachedDownloads(id);
            if (cachedDownloads == null) {
                getDownloadRequest(id, themeDisplay);
            }
            writeToResponse(resourceResponse, cachedDownloads);
        } else if ("download".equals(action)) {
            final String cachedDownloads = getCachedDownloads(id);
            if (cachedDownloads != null) {
                writeToResponse(resourceResponse, cachedDownloads);
            } else {
                final DataRequest downloadRequest = DataRequestManager.getInstance().getDataRequest(id);
                if (Objects.requireNonNull(downloadRequest.getStatus()) == DataRequest.STATUS.available) {
                    final File dataFile = downloadRequest.getDataFile();
                    try {
                        if (dataFile != null && dataFile.exists()) {
                            final byte[] content = Files.readAllBytes(dataFile.toPath());
                            final String data = new String(content, StandardCharsets.UTF_8);
                            cacheDownloads(id, data);
                            writeToResponse(resourceResponse, data);
                        }
                    } catch (Exception e) {
                        DataRequestManager.getInstance().writeError(e.getMessage(), resourceResponse);
                    } finally {
                        DataRequestManager.getInstance().removeDataRequest(downloadRequest);
                    }
                } else {
                    resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }

        } else if ("updateStatus".equals(action)){
            DataRequestManager.getInstance().updateStatus(id, resourceResponse);
        } else {
            DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, resourceResponse);
        }
    }

    private void writeToResponse(ResourceResponse resourceResponse, String jsonDownload) {
        resourceResponse.setContentType("application/json");
        if (jsonDownload == null){
           resourceResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resourceResponse.setStatus(HttpServletResponse.SC_OK);
            resourceResponse.setContentLength(jsonDownload.getBytes(StandardCharsets.UTF_8).length);
            try (Writer writer = resourceResponse.getWriter()) {
                writer.write(jsonDownload);
            } catch (IOException e) {
                resourceResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

    }

    private static String getId(ThemeDisplay themeDisplay) {
        return DownloadActivityMapRequest.class.getName().concat("_").concat(String.valueOf(themeDisplay.getCompanyId()))
                .concat("_").concat(String.valueOf(themeDisplay.getSiteGroupId()));
    }

    @SuppressWarnings("UnusedReturnValue")
    private DataRequest getDownloadRequest(String id, ThemeDisplay themeDisplay) throws IOException {

        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(id);
        if (dataRequest == null) {

            long downloadSiteId;
            try {
                SiteMapConfiguration configuration = _configurationProvider.getSystemConfiguration(
                        SiteMapConfiguration.class);
                downloadSiteId = Long.parseLong(configuration.downloadPortalSiteId());
            } catch (Exception e) {
                LOG.error("Error retrieving ID for download portal from SiteMapConfiguration: " + e.getMessage());
                downloadSiteId = themeDisplay.getSiteGroupId();
            }
            dataRequest = new DownloadActivityMapRequest(id, themeDisplay.getUserId(), downloadSiteId, dsdParserUtils);
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
            instance.removeDataRequest(dataRequest);
        }
        return dataRequest;
    }

    private void cacheDownloads(String id, String data) {
        long expTimeMillis = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1);
        String expiryKey = id + "_expirytime";
        PortalCache<String, Serializable> portalCache =
                PortalCacheHelperUtil.getPortalCache(PortalCacheManagerNames.SINGLE_VM, "deltares");
        portalCache.put(id, data);
        portalCache.put(expiryKey, expTimeMillis);
    }

    private String getCachedDownloads(String id) {
        if (id == null) return null;
        PortalCache<String, Serializable> cache = PortalCacheHelperUtil.getPortalCache(PortalCacheManagerNames.SINGLE_VM, "deltares");
        String data = (String) cache.get(id);
        if (data != null) {
            final String expiryKey = id + "_expirytime";
            Long expiryTime = (Long) cache.get(expiryKey);
            if (expiryTime != null && expiryTime > System.currentTimeMillis()) {
                return data;
            } else {
                cache.remove(id);
                cache.remove(expiryKey);
            }
        }
        return null;
    }
}
