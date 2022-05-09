package nl.deltares.portal.utils;

import java.util.Map;

public interface GeoIpUtils {

    boolean isActive();
    Map<String, String> getClientIpInfo(String ipAddress);

    String getCountryIso2Code(Map<String, String> clientIpInfo);

    String getCountryName(Map<String, String> clientIpInfo);

    double[] getLatitudeLongitude(Map<String, String> clientIpInfo);
}
