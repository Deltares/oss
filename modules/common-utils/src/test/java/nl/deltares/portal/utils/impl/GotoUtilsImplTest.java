package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.mock.MockJournalArticle;
import nl.deltares.mock.MockJournalArticleLocalServiceUtil;
import nl.deltares.mock.MockProps;
import nl.deltares.mock.MockUser;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.JsonContentParserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Requires a webinar to be configured in future. Not possible to remove users from past webinars.
 */
public class GotoUtilsImplTest {

    private GotoUtilsImpl gotoUtils;
    private MockJournalArticleLocalServiceUtil serviceUtil;

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

        gotoUtils = new GotoUtilsImpl();

        serviceUtil = new MockJournalArticleLocalServiceUtil();
        JsonContentParserUtils.setServiceUtils(serviceUtil);

        URL resourceDir = this.getClass().getResource("/goto/");
        File[] resources = new File(resourceDir.getFile()).listFiles(pathname -> pathname.getName().endsWith(".xml"));
        Assert.assertNotNull(resources);
        for (File resource : resources) {
            serviceUtil.addTestResource(MockJournalArticle.getInstance(resource));
        }
    }

    @Test
    public void testIsGotoMeeting() throws PortalException {

        if (!gotoUtils.isActive()) return;
        JournalArticle course = serviceUtil.getLatestArticle(107688);
        Registration registration = (Registration) AbsDsdArticle.getInstance(course);
        Assert.assertTrue(gotoUtils.isGotoMeeting(registration));

    }

    @Test
    public void testRegisterUser() throws Exception {
        if (!gotoUtils.isActive()) return;

        JournalArticle course = serviceUtil.getLatestArticle(107688);
        SessionRegistration registration = (SessionRegistration) AbsDsdArticle.getInstance(course);
        User user = new MockUser();
        user.setEmailAddress("test@liferay.com");
        user.setFirstName("Test");
        user.setLastName("User");

        Map<String, String> info = gotoUtils.getRegistration(user, registration.getWebinarKey());
        if (info == null) {
            info = gotoUtils.registerUser(user, registration.getWebinarKey(), "testRegisterUser");
        }
        Assert.assertNotNull(info.get("registrantKey"));
        Assert.assertEquals("https://global.gotowebinar.com/join/2590473991435429392/102396762",  info.get("joinUrl"));
    }

    @Test
    public void testUnregisterUser() throws Exception {
        if (!gotoUtils.isActive()) return;
        JournalArticle course = serviceUtil.getLatestArticle(107688);
        SessionRegistration registration = (SessionRegistration) AbsDsdArticle.getInstance(course);
        User user = new MockUser();
        user.setEmailAddress("test@liferay.com");
        user.setFirstName("Test");
        user.setLastName("User");

        Map<String, String> info = gotoUtils.getRegistration(user, registration.getWebinarKey());
        if (info != null) {
            long status = gotoUtils.unregisterUser(info.get("registrantKey"), registration.getWebinarKey());
            Assert.assertEquals(204 , status);
        }

    }

}
