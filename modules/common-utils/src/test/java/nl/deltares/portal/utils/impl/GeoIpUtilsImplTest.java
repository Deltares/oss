package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.mock.MockProps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class GeoIpUtilsImplTest {

    private GeoIpUtilsImpl geoIpUtils;

    @Before
    public void setup() throws IOException {

        MockProps props = new MockProps();
        Properties properties = new Properties();
        properties.put(GeoIpUtilsImpl.PROP_GEOIP_DB_DIR, getClass().getResource("/geoip/").getFile());
        properties.put(GeoIpUtilsImpl.PROP_GEOIP_DB_NAME, "GeoLite2-City.mmdb");
        props.setProperties(properties);
        PropsUtil.setProps(props);
        geoIpUtils = new GeoIpUtilsImpl();

    }

    @Test
    public void testLocationInfo() {

        String ipAddress = "77.166.13.240";

        Map<String, String> locationInfo = geoIpUtils.getClientIpInfo(ipAddress);
        Assert.assertEquals("Delft", locationInfo.get("city"));
        Assert.assertEquals("Netherlands", locationInfo.get("country"));
        Assert.assertEquals( "NL", locationInfo.get("iso_code"));
        Assert.assertEquals("2611", locationInfo.get("postal"));

    }

}
