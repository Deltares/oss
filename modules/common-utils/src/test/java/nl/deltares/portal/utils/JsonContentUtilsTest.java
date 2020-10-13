package nl.deltares.portal.utils;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class JsonContentUtilsTest {

    @Before
    public void setUp() throws Exception {
        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
    }

    @Test
    public void parseMap() throws JSONException {

        String json = "{" +
                "\"Blog\":\"Blogs\", " +
                "\"New idea\":\"New ideas\"," +
                "\"Best practice\":\"Best practices\", " +
                "\"Location\":\"Locations\", " +
                "\"Eventlocation\":\"Locations\", " +
                "\"Building\":\"Locations\"," +
                " \"Room\":\"Locations\"," +
                "\"Expert\":\"Experts\"," +
                "\"Registration\":\"Registrations\"," +
                "\"Session\":\"Registrations\"," +
                "\"Dinner\":\"Registrations\"," +
                "\"Bustransfer\":\"Bustransfers\",\"Dsdevent\":\"Events\"}";
        Map<String, String> map = JsonContentUtils.parseJsonToMap(json);
        Assert.assertEquals(13, map.size());
    }
}
