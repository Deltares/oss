package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component(
        immediate = true,
        service = GeoIpUtils.class
)
/*
 * The idea of this class was to allow retrieval of user location info from request. However it is not
 * that easy to get IP address from the request and therefore this class is not being used.
 */
public class IpGeolocationUtils implements GeoIpUtils {

    private static final Log LOG = LogFactoryUtil.getLog(IpGeolocationUtils.class);

    private final String API_URL;
    private final String API_KEY;
    private final boolean active;


    public IpGeolocationUtils() {

        API_URL = PropsUtil.get("ipgeolocation.api.url");
        API_KEY = PropsUtil.get("ipgeolocation.api.key");

        active = API_URL != null && API_KEY != null;
        LOG.info("Initialised SanctionCheckUtils with properties: " + API_URL + API_KEY);

    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public Map<String, String> getClientIpInfo(String ipAddress) {
        if (!active) return Collections.emptyMap();

        try {
            HashMap<String, String> headers = new HashMap<>();
            final String requestPath;
            if (ipAddress == null) {
                requestPath = API_URL + API_KEY + "&fields=country_code2,country_name,latitude,longitude";
            } else {
                requestPath = API_URL + API_KEY + "&ip=" + ipAddress + "&fields=country_code2,country_name,latitude,longitude";
            }

            final HttpURLConnection get = HttpClientUtils.getConnection(requestPath, "GET", headers);
            final String ipResponse = HttpClientUtils.readAll(get);
            return JsonContentUtils.parseJsonToMap(ipResponse);

        } catch (IOException | JSONException e) {
            LOG.error("Error getting client info from API: " + e.getMessage());
        }
        return Collections.emptyMap();
    }

    @Override
    public String getCountryIso2Code(Map<String, String> clientIpInfo) {
        return clientIpInfo.get("country_code2");
    }

    @Override
    public String getCountryName(Map<String, String> clientIpInfo) {
        return clientIpInfo.get("country_name");
    }

    @Override
    public double[] getLatitudeLongitude(Map<String, String> clientIpInfo) {
        final double[] latlon = new double[]{Double.NaN, Double.NaN};

        final String latitude = clientIpInfo.get("latitude");
        if (latitude != null) latlon[0] = Double.parseDouble(latitude);

        final String longitude = clientIpInfo.get("longitude");
        if (longitude != null) latlon[1] = Double.parseDouble(longitude);

        return latlon;
    }
}
