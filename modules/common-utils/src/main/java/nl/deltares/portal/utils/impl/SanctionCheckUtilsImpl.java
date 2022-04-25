package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.configuration.SanctionSystemConfiguration;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.SanctionCheckUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component(
        immediate = true,
        service = SanctionCheckUtils.class
)
public class SanctionCheckUtilsImpl implements SanctionCheckUtils {

    private final String API_URL;
    private final String API_KEY;
    private final boolean active;
    private static final Log LOG = LogFactoryUtil.getLog(SanctionCheckUtilsImpl.class);


    private ConfigurationProvider _configurationProvider;

    public SanctionCheckUtilsImpl() {

        API_URL = PropsUtil.get("download.api.url");
        API_KEY = PropsUtil.get("download.api.key");

        active = API_URL != null && API_KEY != null;
    }

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private String[] getSanctionedCountryCodes(ConfigurationProvider configurationProvider) {
        if (configurationProvider == null) return new String[0];

        try {
            SanctionSystemConfiguration configuration = configurationProvider.getSystemConfiguration(
                    SanctionSystemConfiguration.class);

            final String codes = configuration.sanctionCountryIsoCodes();
            return codes.split(";");

        } catch (ConfigurationException e) {
            LOG.error("Error getting SanctionSystemConfiguration: " + e.getMessage());
        }
        return new String[0];
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isSanctioned(String isoCountryCode){
        final String[] sanctionList = getSanctionedCountryCodes(_configurationProvider);
        return Arrays.stream(sanctionList).anyMatch(sanctionCode -> sanctionCode.toUpperCase().equals(isoCountryCode));
    }

    @Override
    public Map<String, String> getClientIpInfo() {
        if (!active) return Collections.emptyMap();

        try {
            HashMap<String, String> headers = new HashMap<>();
            final HttpURLConnection get = HttpClientUtils.getConnection(API_URL + API_KEY, "GET", headers);
            final String ipResponse = HttpClientUtils.readAll(get);
            return JsonContentUtils.parseJsonToMap(ipResponse);

        } catch (IOException | JSONException e) {
            LOG.error("Error getting client info from API: " + e.getMessage());
        }
        return Collections.emptyMap();
    }
}
