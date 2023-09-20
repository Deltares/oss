package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.portal.utils.SanctionCheckUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

@Component(
        immediate = true,
        service = SanctionCheckUtils.class
)
public class SanctionCheckUtilsImpl implements SanctionCheckUtils {

    @Reference
    protected GeoIpUtils geoIpUtils;

    @Override
    public boolean isSanctionedByCountyCode(String isoCountryCode) {
        final long companyId = PortalUtil.getDefaultCompanyId();
        isSanctionedByCountyCode(companyId, isoCountryCode);
        return false;
    }

    @Override
    public boolean isSanctionedByCountyCode(long companyId, String isoCountryCode) {

        final Country countryByA2 = CountryLocalServiceUtil.fetchCountryByA2(companyId, isoCountryCode);
        if (countryByA2 != null) return !countryByA2.isActive();
        return false;
    }

    @Override
    public boolean isSanctionedByIp(String remoteAddress) {
        if (!geoIpUtils.isActive()) return false;
        final Map<String, String> clientIpInfo = geoIpUtils.getClientIpInfo(remoteAddress);
        if (clientIpInfo.isEmpty()) return false;
        return isSanctionedByCountyCode(geoIpUtils.getCountryIso2Code(clientIpInfo));
    }
}
