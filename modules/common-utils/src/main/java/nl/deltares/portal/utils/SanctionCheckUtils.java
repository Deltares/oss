package nl.deltares.portal.utils;


public interface SanctionCheckUtils {

    boolean isSanctionedByCountyCode(String isoCountryCode);

    boolean isSanctionedByIp(String remoteAddress);
}
