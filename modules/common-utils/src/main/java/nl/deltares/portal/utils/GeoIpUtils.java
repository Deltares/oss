package nl.deltares.portal.utils;

import java.util.Map;

public interface GeoIpUtils {

    enum ATTRIBUTES {
        country_id,
        county_iso2code,
        city_name,
        latitude,
        longitude
    }
    boolean isActive();
    Map<String, String> getClientIpInfo(String ipAddress);

    String getCountryIso2Code(Map<String, String> clientIpInfo);

    String getCountryName(Map<String, String> clientIpInfo);

    String getCityName(Map<String, String> clientIpInfo);

    String getPostalCode(Map<String, String> clientIpInfo);

    double getLatitude(Map<String, String> clientIpInfo);

    double getLongitude(Map<String, String> clientIpInfo);

    long getGeoLocationId(Map<String, String> clientIpInfo) throws Exception;

}
