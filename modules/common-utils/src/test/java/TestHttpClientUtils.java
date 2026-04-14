import nl.deltares.portal.utils.HttpClientUtils;
import org.junit.Test;

public class TestHttpClientUtils {

    @Test
    public void testGetHttpClient()  {
        String httpClient = HttpClientUtils.getBasicAuthorization("download-admin", "qFyxG-ii67E-YPNwH-xTMot-kQWoR");
    }
}
