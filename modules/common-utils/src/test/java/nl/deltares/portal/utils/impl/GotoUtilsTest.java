package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.mock.MockDsdJournalArticleUtils;
import nl.deltares.mock.MockJournalArticle;
import nl.deltares.mock.MockProps;
import nl.deltares.mock.MockUser;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Requires a webinar to be configured in future. Not possible to remove users from past webinars.
 */
@Ignore
public class GotoUtilsTest {

    private GotoUtils gotoUtils;

    @Before
    public void setup() throws IOException {
        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

        MockProps props = new MockProps();
        Properties properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/goto/portal-ext.properties")) {
            if (is != null) {
                properties.load(is);
            }
        }
        props.setProperties(properties);
        PropsUtil.setProps(props);

        gotoUtils = new GotoUtils();
    }

    @Test
    public void testRegisterUser() throws Exception {
        if (!gotoUtils.isActive()) return;

        User user = new MockUser();
        user.setEmailAddress("test@liferay.com");
        user.setFirstName("Test");
        user.setLastName("User");

        Map<String, String> info = new HashMap<>();
        String webinarKey = "5555006932186073868";
        if (gotoUtils.isUserRegistered(user, webinarKey, info)) {
            gotoUtils.unregisterUser(user, webinarKey, info);
            Assert.assertFalse(gotoUtils.isUserRegistered(user, webinarKey, info));
        }
        gotoUtils.registerUser(user, webinarKey, "testRegisterUser", info);
        Assert.assertTrue(gotoUtils.isUserRegistered(user, webinarKey, info));
    }

    @Test
    public void testUnregisterUser() throws Exception {
        if (!gotoUtils.isActive()) return;
        User user = new MockUser();
        user.setEmailAddress("test@liferay.com");
        user.setFirstName("Test");
        user.setLastName("User");

        Map<String, String> info = new HashMap<>();
        String webinarKey = "5555006932186073868";
        if (!gotoUtils.isUserRegistered(user, webinarKey, info)) {
            gotoUtils.registerUser(user, webinarKey, "testRegisterUser", info);
            Assert.assertTrue(gotoUtils.isUserRegistered(user, webinarKey, info));
        }
        gotoUtils.unregisterUser(user, webinarKey, info);
        Assert.assertFalse(gotoUtils.isUserRegistered(user, webinarKey, info));

    }

    @Test
    public void testGetAllRegistrations() throws Exception {
        if (!gotoUtils.isActive()) return;

        User user = new MockUser();
        user.setEmailAddress("test@liferay.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setScreenName("test-dsd");

        String webinarKey = "5555006932186073868";
        Map<String, String> info = new HashMap<>();
        if (!gotoUtils.isUserRegistered(user, webinarKey, info)) {
            gotoUtils.registerUser(user, webinarKey, "testRegisterUser", info);
        }
        List<String> allCourseRegistrations = gotoUtils.getAllCourseRegistrations(webinarKey);
        Assert.assertTrue(gotoUtils.isUserInCourseRegistrationsList(allCourseRegistrations, user));

    }
}
