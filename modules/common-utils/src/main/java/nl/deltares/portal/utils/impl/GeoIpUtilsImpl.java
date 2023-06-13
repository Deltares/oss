package nl.deltares.portal.utils.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PortalInstances;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.service.GeoLocationLocalServiceUtil;
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
    public Map<String, String> getClientIpInfo(String ipAddress) {
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
            LOG.info(String.format("Parsed geolocation %s, %s from IP %s", city.getCity().getName(), city.getCountry().getName(), ipAddress));

            registerGeolocationIfMissing(info);
        } catch (Exception e) {
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
    public String getCityName(Map<String, String> clientIpInfo) {
        return clientIpInfo.get("city");
    }

    @Override
    public String getPostalCode(Map<String, String> clientIpInfo) {
        return clientIpInfo.get("postal");
    }

    @Override
    public double getLatitude(Map<String, String> clientIpInfo) {

        final String latitude = clientIpInfo.get("latitude");
        if (latitude != null) return Double.parseDouble(latitude);
        return Double.NaN;
    }

    @Override
    public double getLongitude(Map<String, String> clientIpInfo) {

        final String longitude = clientIpInfo.get("longitude");
        if (longitude != null) return Double.parseDouble(longitude);
        return Double.NaN;
    }

    @Override
    public long getGeoLocationId(Map<String, String> clientIpInfo) {

        final String locationId = clientIpInfo.get("geoLocationId");
        if (locationId == null) return -1;
        return Long.parseLong(locationId);

    }

    private void registerGeolocationIfMissing(Map<String, String> clientIpInfo) throws Exception {
        if (clientIpInfo.isEmpty()) throw new IOException("ClientIP info is empty! Cannot register country");
        final long defaultCompanyId = PortalInstances.getDefaultCompanyId();
        Country country = extractCountry(defaultCompanyId, clientIpInfo);

        final String city = getCityName(clientIpInfo);
        final GeoLocation geoLocation = GeoLocationLocalServiceUtil.fetchByCity(country.getCountryId(), city);
        if (geoLocation != null) {
            LOG.info(String.format("Geolocation %d already exists for country '%s (%s)' and city '%s'",
                    geoLocation.getLocationId(), country.getName(), country.getA2(), city));
            clientIpInfo.put("geoLocationId",String.valueOf(geoLocation.getLocationId()));
            return ;
        }

        final GeoLocation newGeoLocation = GeoLocationLocalServiceUtil.createGeoLocation(CounterLocalServiceUtil.increment(
                GeoLocation.class.getName()));
        newGeoLocation.setCompanyId(defaultCompanyId);
        newGeoLocation.setCountryId(country.getCountryId());
        newGeoLocation.setCityName(city);
        newGeoLocation.setLatitude(getLatitude(clientIpInfo));
        newGeoLocation.setLongitude(getLongitude(clientIpInfo));
        GeoLocationLocalServiceUtil.updateGeoLocation(newGeoLocation);
        clientIpInfo.put("geoLocationId", String.valueOf(newGeoLocation.getLocationId())) ;
    }

    private Country extractCountry(long defaultCompanyId, Map<String, String> clientIpInfo) throws Exception {
        final String countryIso2Code = getCountryIso2Code(clientIpInfo);

        final Country country = CountryServiceUtil.getCountryByA2(defaultCompanyId, countryIso2Code);
        if (country == null) throw new IOException("Cannot find valid country for ISO code " + countryIso2Code);
        return country;
    }

}
