package nl.deltares.portal.utils;

import javax.servlet.http.HttpServletRequest;

public interface SanctionCheckUtils {

    boolean isSanctionedByCountyCode(String isoCountryCode);

    boolean isSanctionedByIp(String remoteAddress);
}
