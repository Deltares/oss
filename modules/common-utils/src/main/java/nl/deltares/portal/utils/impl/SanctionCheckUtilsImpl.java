package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import nl.deltares.portal.configuration.SanctionSystemConfiguration;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.portal.utils.SanctionCheckUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component(
        immediate = true,
        service = SanctionCheckUtils.class
)
public class SanctionCheckUtilsImpl implements SanctionCheckUtils {

    private static final Log LOG = LogFactoryUtil.getLog(SanctionCheckUtilsImpl.class);
    private final List<String> countryCodes = new ArrayList<>();

    @Reference
    protected GeoIpUtils geoIpUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private void loadCountryCodes(ConfigurationProvider configurationProvider) {
        if (configurationProvider == null) return;

        try {
            SanctionSystemConfiguration configuration = configurationProvider.getSystemConfiguration(
                    SanctionSystemConfiguration.class);

            final String codes = configuration.sanctionCountryIsoCodes();

            final String[] codesList = codes.split(";");
            countryCodes.clear();
            Arrays.stream(codesList).forEach(code -> countryCodes.add(code.toUpperCase()));
            
        } catch (ConfigurationException e) {
            LOG.error("Error getting SanctionSystemConfiguration: " + e.getMessage());
        }
    }

    @Override
    public boolean isSanctionCountry(String ipAddress) {
        loadCountryCodes(_configurationProvider);
        return countryCodes.contains(geoIpUtils.getCountryIso2Code(ipAddress));
    }


}
