import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.json.JSONException;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.impl.TaxCalculatorImpl;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    @Test
    public void testTaxCalculatorValidId() {
        TaxCalculatorImpl taxCalculator = new TaxCalculatorImpl();
        assertTrue(taxCalculator.isValidVAT("DE", "DE137528175"));
    }

    @Test
    public void testParseJsonResponse() throws IOException, JSONException {
        // Load from src/test/resources via classpath; this works regardless of working directory.
        try (InputStream in = TestUtils.class.getResourceAsStream("/ec.europe.eu.response.DE.json")) {
            assertNotNull("Missing test resource ec.europe.eu.response.DE.json on classpath", in);

            String content = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            Map obj = new ObjectMapper().readValue(content, Map.class);
            assertTrue((Boolean) obj.get("isValid"));

        }
    }
}
