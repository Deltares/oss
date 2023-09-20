package nl.deltares.portal.utils;


public interface SanctionCheckUtils {

    boolean isSanctionedByCountyCode(String isoCountryCode);

    boolean isSanctionedByCountyCode(long companyId, String isoCountryCode);

    boolean isSanctionedByIp(String remoteAddress);
}
