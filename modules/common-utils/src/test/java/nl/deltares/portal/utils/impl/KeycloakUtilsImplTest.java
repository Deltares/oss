package nl.deltares.portal.utils.impl;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.mock.MockProps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Requires local keycloak running
 */
@Ignore
public class KeycloakUtilsImplTest {

    private KeycloakUtilsImpl keycloakUtils;

    @Before
    public void setup() throws IOException {
        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

        MockProps props = new MockProps();
        Properties properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/keycloak/portal-ext.properties")) {
            if (is != null) {
                properties.load(is);
            }
        }
        props.setProperties(properties);
        PropsUtil.setProps(props);

        keycloakUtils = new KeycloakUtilsImpl();
    }

    @Test
    public void testGetUserAttributes() throws Exception {

        if (!keycloakUtils.isActive()) return;
        Map<String, String> userAttributes = keycloakUtils.getUserAttributes("test@liferay.com");

        Assert.assertTrue(userAttributes.size() > 0);
        Assert.assertEquals("Deltares", userAttributes.get("org_name"));
    }

    @Test
    public void testSetUserAttributes() throws Exception {

        if (!keycloakUtils.isActive()) return;

        Map<String, String> userAttributes = keycloakUtils.getUserAttributes("test@liferay.com");

        String org_postal = userAttributes.get("org_postal");
        String new_org_postal = String.valueOf(System.currentTimeMillis());
        Assert.assertNotEquals(org_postal, new_org_postal);

        userAttributes.put("org_postal", new_org_postal);
        keycloakUtils.updateUserAttributes("test@liferay.com", userAttributes);

        Map<String, String> newUserAttributes = keycloakUtils.getUserAttributes("test@liferay.com");

        org_postal = newUserAttributes.get("org_postal");
        Assert.assertEquals(org_postal, new_org_postal);

    }

    @Test
    public void getUserAvatar() throws Exception {

        if (!keycloakUtils.isActive()) return;
        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

        byte[] userAvatar = keycloakUtils.getUserAvatar("test@liferay.com");

        File expectedFile = new File(getClass().getResource("/keycloak/expected.jpg").getFile());
        FileInputStream is = new FileInputStream(expectedFile);
        byte[] bytes = is.readAllBytes();
        Assert.assertArrayEquals(bytes, userAvatar);

    }
}
