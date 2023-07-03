package nl.deltares.portal.utils.impl;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.mock.MockProps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

/**
 * In order to run these tests, it is necessary to start the Donwload portal docker container.
 */
@Ignore
public class DownloadUtilsTest {

    @Before
    public void setup() {

        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

        MockProps props = new MockProps();
        Properties properties = new Properties();

        properties.put("download.baseurl", "http://172.23.64.1:8180/ocs/v2.php/apps/");
        properties.put("download.app.name", "ossadmin");
        properties.put("download.app.password", "doF4S-nrLMg-FBzs4-NjAxB-2dSMH");
        props.setProperties(properties);
        PropsUtil.setProps(props);

    }

    @Test
    public void testSendShareLink() throws Exception {

        final DownloadUtilsImpl downloadUtils = new DownloadUtilsImpl();
        final Map<String, String> info = downloadUtils.createShareLink("/OSS/Nextcloud Manual.pdf", "teset@lifer.com", false);
        final Integer shareId = Integer.parseInt(info.get("id"));
        System.out.printf("created share %d for user %s%n", shareId, "erik.derooij@deltares.nl");
        Assert.assertTrue(shareId > 0);
    }


}
