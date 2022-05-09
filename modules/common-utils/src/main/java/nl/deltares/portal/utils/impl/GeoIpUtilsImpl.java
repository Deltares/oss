package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import nl.deltares.portal.utils.GeoIpUtils;
import org.osgi.service.component.annotations.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
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
public class GeoIpUtilsImpl implements GeoIpUtils {

    private static final Log LOG = LogFactoryUtil.getLog(GeoIpUtilsImpl.class);

    public final static String PROP_GEOIP_DB_DIR = "maxmind.geoip.database.dir";
    public final static String PROP_GEOIP_DB_NAME = "maxmind.geoip.database.name";

    private final DatabaseReader reader;


    public GeoIpUtilsImpl() throws IOException {

        String dbDir = PropsUtil.get(PROP_GEOIP_DB_DIR);
        String dbName = PropsUtil.get(PROP_GEOIP_DB_NAME);

        File database = new File(dbDir + "/" + dbName);
        if (database.exists()) {
            reader = new DatabaseReader.Builder(database).build();
        } else {
            LOG.warn("GeoIP file does not exist: " + database.getAbsolutePath());
            reader = null;
        }

    }

    @Override
    public boolean isActive() {
        return reader != null;
    }

    @Override
    public Map<String, String> getClientIpInfo(String ipAddress){
        HashMap<String, String> info = new HashMap<>();
        if (reader == null) return info;
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            CityResponse city = reader.city(inetAddress);
            info.put("city", city.getCity().getName());
            info.put("postal", city.getPostal().getCode());
            info.put("country", city.getCountry().getName());
            info.put("iso_code", city.getCountry().getIsoCode());
            info.put("latitude", String.valueOf(city.getLocation().getLatitude()));
            info.put("longitude", String.valueOf(city.getLocation().getLongitude()));
        } catch (GeoIp2Exception | IOException e) {
            LOG.warn("Error creating location info response: " + e.getMessage());
        }
        return info;
    }

    @Override
    public String getCountryIso2Code(Map<String, String> clientIpInfo) {
        return clientIpInfo.get("iso_code");
    }

    @Override
    public String getCountryName(Map<String, String> clientIpInfo) {
        return clientIpInfo.get("country");
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
