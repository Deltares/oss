package nl.deltares.portal.utils;

import java.util.Map;

public interface GeoIpUtils {
    Map<String, Object> getLocationInfo(String ipAddress);
}
