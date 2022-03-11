package nl.deltares.portal.utils.impl;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.mock.MockProps;
import nl.deltares.portal.utils.HttpClientUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Properties;

/**
 * In order to run these tests, it is necessary to start the Donwload portal docker container.
 */
public class DownloadUtilsTest {

    @Before
    public void setup()  {

        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

        MockProps props = new MockProps();
        Properties properties = new Properties();

        properties.put("download.baseurl", "http://localhost:8180/ocs/v2.php/apps/");
        properties.put("download.app.name", "admin");
        properties.put("download.app.password", "pTccE-5b4Mo-Xcj8R-BppK2-sydqc");
        props.setProperties(properties);
        PropsUtil.setProps(props);

    }

    @Test
    public void testDirectDownload() throws Exception {

        long fileId = 8841375;

        final DownloadUtilsImpl downloadUtils = new DownloadUtilsImpl();

        final String directDownloadLink = downloadUtils.getDirectDownloadLink(fileId);

        final HttpURLConnection file = HttpClientUtils.getConnection(directDownloadLink, "GET", Collections.emptyMap());
        final String content = HttpClientUtils.readAll(file);
        Assert.assertEquals("Test direct download endpoint", content);

    }

    @Test
    public void testInvalidDirectDownload() {

        long fileId = 999;

        final DownloadUtilsImpl downloadUtils = new DownloadUtilsImpl();

        try {
            downloadUtils.getDirectDownloadLink(fileId);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().startsWith("Error 404"));
        }

    }

    @Test
    public void testSendShareLink() throws Exception {

        final DownloadUtilsImpl downloadUtils = new DownloadUtilsImpl();
        int shareId = downloadUtils.sendShareLink("/OSS/Nextcloud Manual.pdf", "teset@lifer.com");
        System.out.println(String.format("created share %d for user %s", shareId, "erik.derooij@deltares.nl"));
        Assert.assertTrue(shareId > 0);
    }

    @Test
    public void testRevokeShareLink() throws Exception {

        final DownloadUtilsImpl downloadUtils = new DownloadUtilsImpl();

        int shareId = 65;
        downloadUtils.deleteShareLink(shareId);
        System.out.println(String.format("revoked share %d for user %s: %d", shareId, "erik.derooij@deltares.nl"));
    }

    @Test
    public void testShareLinkExists() throws Exception {

        final DownloadUtilsImpl downloadUtils = new DownloadUtilsImpl();

        int shareId = downloadUtils.shareLinkExists("/unittests/test-direct.txt", "erik.derooij@deltares.nl");
        Assert.assertEquals(-1, shareId);

        shareId = downloadUtils.sendShareLink("/unittests/test-direct.txt", "erik.derooij@deltares.nl");

        int shareIdChecked = downloadUtils.shareLinkExists("/unittests/test-direct.txt", "erik.derooij@deltares.nl");
        Assert.assertEquals(shareId, shareIdChecked);

    }

    @Test
    public void testResendShareLink() throws Exception {

        final DownloadUtilsImpl downloadUtils = new DownloadUtilsImpl();

        int shareId = downloadUtils.shareLinkExists("/unittests/test-direct.txt", "erik.derooij@deltares.nl");
        if (shareId == -1) {
           shareId = downloadUtils.sendShareLink("/unittests/test-direct.txt", "erik.derooij@deltares.nl");
        }

        final int newShareId = downloadUtils.resendShareLink(shareId);


    }
}
