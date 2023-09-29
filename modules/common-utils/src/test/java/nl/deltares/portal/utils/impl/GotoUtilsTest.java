package nl.deltares.portal.utils.impl;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import nl.deltares.mock.MockUser;
import nl.deltares.mock.MockWebinarSiteConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Ignore
public class GotoUtilsTest {

    private GotoUtils gotoUtils;

    @Before
    public void setup() throws IOException {

        final MockWebinarSiteConfiguration config = new MockWebinarSiteConfiguration();

        Properties properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/goto/portal-ext.properties")) {
            if (is != null) {
                properties.load(is);
            }
        }

        config.setGotoUrl(properties.getProperty("goto.baseurl"));
        config.setClientId(properties.getProperty("goto.clientid"));
        config.setClientSecret(properties.getProperty("goto.clientsecret"));
        config.setPassword(properties.getProperty("goto.password"));
        config.setUserName(properties.getProperty("goto.username"));
        gotoUtils = new GotoUtils(config);

        final JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
    }


//    @Test
//    public void testGetAccessToken(){
//
//        String organizerKey = "3013550679235154576";
//        final String accessToken = gotoUtils.getAccessToken();
//        System.out.println(accessToken);
//        Assert.assertNotNull(accessToken);
//
//        final String orgKey = gotoUtils.getOrganizerKey();
//        System.out.println(organizerKey);
//        Assert.assertEquals(organizerKey, orgKey);
//    }

    @Test
    public void testGetAllRegistrations() throws Exception {

        String webinarKey = "1489648458266936589" ;
        final List<String> allCourseRegistrations = gotoUtils.getAllCourseRegistrations(webinarKey);

        for (String registration : allCourseRegistrations) {
            System.out.println(registration);
        }
    }

    @Test
    public void testRegisterUser() throws Exception {
        final MockUser mockUser = new MockUser();
        mockUser.setEmailAddress("erik.derooij@deltares.nl");
        mockUser.setUserId(1000);
        mockUser.setFirstName("Erik");
        mockUser.setLastName("de Rooij");
        final String webinarKey = "1489648458266936589";
        final HashMap<String, String> registrationProperties = new HashMap<>();
        gotoUtils.registerUser(mockUser, Collections.emptyMap(), webinarKey, "testRegisterUser", registrationProperties);

        final String registrantKey = registrationProperties.get("registrantKey");
        final String joinUrl = registrationProperties.get("joinUrl");

        System.out.println(registrantKey);
        System.out.println(joinUrl);
        Assert.assertNotNull(registrantKey);
        Assert.assertNotNull(joinUrl);


    }
}
