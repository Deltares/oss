package nl.deltares.oss.portlet;


import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.DownloadCountLocalServiceUtil;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.service.GeoLocationLocalServiceUtil;
import nl.deltares.oss.portlet.constants.ActivityMapPortletKeys;
import nl.deltares.portal.utils.DsdParserUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.instanceable=true",
                "com.liferay.portlet.header-portlet-javascript=/lib/markerclusterer.js",
                "javax.portlet.display-name=ActivityMap Portlet",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + ActivityMapPortletKeys.ACTIVITY_MAP,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class ActivityMapPortlet extends MVCPortlet {

    @Reference
    DsdParserUtils dsdParserUtils;

    private static final Log LOG = LogFactoryUtil.getLog(ActivityMapPortlet.class);

    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        String downloadsJson = getDownloadsJson(themeDisplay.getSiteGroupId()).toJSONString();
        request.setAttribute("downloadsJson", downloadsJson);
        super.render(request, response);
    }

    private JSONArray getDownloadsJson(long siteGroupId) {

        JSONArray downloadLocations = JSONFactoryUtil.createJSONArray();
        final List<GeoLocation> geoLocations = GeoLocationLocalServiceUtil.getGeoLocations(0, GeoLocationLocalServiceUtil.getGeoLocationsCount());

        for (GeoLocation geoLocation : geoLocations) {
            final JSONObject downloadLocation = JSONFactoryUtil.createJSONObject();
            downloadLocation.put("city", geoLocation.getCityName());
            final JSONObject position = JSONFactoryUtil.createJSONObject();
            position.put("lat", geoLocation.getLatitude());
            position.put("lng", geoLocation.getLongitude());
            downloadLocation.put("position", position);

            final JSONArray products = JSONFactoryUtil.createJSONArray();
            final List<Long> downloadsByGeoLocations = DownloadLocalServiceUtil.findDistinctDownloadIdsByGeoLocation(geoLocation.getLocationId());

            downloadsByGeoLocations.forEach(downloadId -> {
                nl.deltares.portal.model.impl.Download dsdDownload = getDownloadArticle(siteGroupId, downloadId);
                if (dsdDownload == null) return;

                final DownloadCount downloadCount = DownloadCountLocalServiceUtil.getDownloadCount(dsdDownload.getGroupId(), downloadId);
                if (downloadCount == null) return;

                JSONObject jsonProduct = JSONFactoryUtil.createJSONObject();
                jsonProduct.put("downloadId", downloadId);
                jsonProduct.put("downloadCount", downloadCount.getCount());
                jsonProduct.put("software", dsdDownload.getFileTopic());
                jsonProduct.put("downloadName", dsdDownload.getFileName());
                products.put(jsonProduct);

            });
            downloadLocation.put("products", products);
            downloadLocations.put(downloadLocation);
        }


        return downloadLocations;
    }

    private nl.deltares.portal.model.impl.Download getDownloadArticle(long siteGroupId, long downloadId) {
        try {
            return (nl.deltares.portal.model.impl.Download) dsdParserUtils.toDsdArticle(siteGroupId, String.valueOf(downloadId));
        } catch (PortalException e) {
            LOG.warn(String.format("Error parsing article %d : %s", downloadId, e.getMessage()));
            return null;
        }
    }

}
