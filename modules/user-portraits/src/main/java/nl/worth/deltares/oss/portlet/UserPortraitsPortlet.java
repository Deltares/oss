package nl.worth.deltares.oss.portlet;


import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.worth.deltares.oss.portlet.constants.UserPortraitsPortletKeys;
import nl.worth.deltares.tasks.impl.UserPortraitsRequest;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
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
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.instanceable=false",
                "com.liferay.portlet.footer-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/userportraits.js",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + UserPortraitsPortletKeys.USER_PORTRAITS,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class UserPortraitsPortlet extends MVCPortlet {
    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        final String id = getId(themeDisplay);
        final String cachedPortraits = getCachedPortraits(id);
        if (cachedPortraits != null) {
            renderRequest.setAttribute("userportraitdata", cachedPortraits);
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
            final String cachedPortraits = getCachedPortraits(id);
            if (cachedPortraits == null) {
                getUserPortraitRequest(id, themeDisplay);
            }
            writeToResponse(resourceResponse, cachedPortraits);
        } else if ("download".equals(action)) {
            final String cachedPortraits = getCachedPortraits(id);
            if (cachedPortraits != null) {
                writeToResponse(resourceResponse, cachedPortraits);
            } else {
                final DataRequest downloadRequest = DataRequestManager.getInstance().getDataRequest(id);
                if (Objects.requireNonNull(downloadRequest.getStatus()) == DataRequest.STATUS.available) {
                    final File dataFile = downloadRequest.getDataFile();
                    try {
                        if (dataFile != null && dataFile.exists()) {
                            final byte[] content = Files.readAllBytes(dataFile.toPath());
                            final String data = new String(content, StandardCharsets.UTF_8);
                            cachePortraits(id, data);
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

        } else if ("updateStatus".equals(action)) {
            DataRequestManager.getInstance().updateStatus(id, resourceResponse);
        } else {
            DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, resourceResponse);
        }
    }

    private void writeToResponse(ResourceResponse resourceResponse, String jsonDownload) {
        resourceResponse.setContentType("application/json");
        if (jsonDownload == null) {
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

    @SuppressWarnings("UnusedReturnValue")
    private DataRequest getUserPortraitRequest(String id, ThemeDisplay themeDisplay) throws IOException {

        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(id);
        if (dataRequest == null) {

            dataRequest = new UserPortraitsRequest(id, themeDisplay.getUserId(), themeDisplay);
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
            instance.removeDataRequest(dataRequest);
        }
        return dataRequest;
    }

    private static String getId(ThemeDisplay themeDisplay) {
        return UserPortraitsRequest.class.getName().concat("_").concat(String.valueOf(themeDisplay.getCompanyId()))
                .concat("_").concat(String.valueOf(themeDisplay.getSiteGroupId()));
    }

    private void cachePortraits(String id, String data) {
        long expTimeMillis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);
        String expiryKey = id + "_expirytime";
        PortalCache<String, Serializable> portalCache =
                PortalCacheHelperUtil.getPortalCache(PortalCacheManagerNames.SINGLE_VM, "deltares");
        portalCache.put(id, data);
        portalCache.put(expiryKey, expTimeMillis);
    }

    private String getCachedPortraits(String id) {
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