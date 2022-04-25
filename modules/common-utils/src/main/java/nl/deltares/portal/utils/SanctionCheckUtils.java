package nl.deltares.portal.utils;


import java.util.Map;

public interface SanctionCheckUtils {

    /**
     * Check if the download portal has been configured in portal-ext.properties
     * @return True if configuration is present. Else false.
     */
    boolean isActive();

    boolean isSanctioned(String isoCountryCode);

    Map<String, String> getClientIpInfo();
}
