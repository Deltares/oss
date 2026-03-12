package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.TaxCalculator;
import org.osgi.service.component.annotations.Component;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component(
        immediate = true,
        service = {TaxCalculator.class}
)
/*
 * This class calculates the tax for a Deltares course based on the rules described here:
 *
 * https://publicwiki.deltares.nl/spaces/SAL/pages/129022586/BTW+curssussen
 *
 * One important assumption is that all courses are of type 'open registration'.
 */
public class TaxCalculatorImpl extends HttpClientUtils implements TaxCalculator {

    private static final Log LOG = LogFactoryUtil.getLog(TaxCalculatorImpl.class);

    private static final String euTaxValidatorAPI = "https://ec.europa.eu/taxation_customs/vies/rest-api/ms/%s/vat/%s";
//    private static final String euTaxValidatorAPI = "https://viesapi.eu/taxation_customs/vies/rest-api/ms/%s/vat/%s";
    private static final String[] euCountryCodes = {
            "AT", // Austria
            "BE", // Belgium
            "BG", // Bulgaria
            "HR", // Croatia
            "CY", // Cyprus
            "CZ", // Czech Republic
            "DK", // Denmark
            "EE", // Estonia
            "FI", // Finland
            "FR", // France
            "DE", // Germany
            "GR", // Greece
            "HU", // Hungary
            "IE", // Ireland
            "IT", // Italy
            "LV", // Latvia
            "LT", // Lithuania
            "LU", // Luxembourg
            "MT", // Malta
            "NL", // Netherlands
            "PL", // Poland
            "PT", // Portugal
            "RO", // Romania
            "SK", // Slovakia
            "SI", // Slovenia
            "ES", // Spain
            "SE"  // Sweden
    };

    @Override
    public boolean isEuCountryCode(String countryCode) {
        return Arrays.stream(euCountryCodes).anyMatch(code -> code.equalsIgnoreCase(countryCode));
    }

    @Override
    public boolean isValidVAT(String countryCode, String vatCode) {

        Arrays.stream(euCountryCodes).filter(code -> code.equalsIgnoreCase(countryCode)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Country code '%s' is not a valid EU country code", countryCode)));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String uri = String.format(euTaxValidatorAPI, countryCode, vatCode);
        try {
            //open connection
            HttpURLConnection connection = getConnection(uri, "GET", headers, 30000);

            //get response
            checkResponse(connection);

            String response = readAll(connection);
            Map<String, String> map = JsonContentUtils.parseJsonToMap(response);
            return "true".equals(map.get("isValid"));
        } catch (Exception e) {
            LOG.warn(String.format("Failed to validate VAT - country combination '%s - %s': %s", vatCode, countryCode, e.getMessage()));
        }
        return false;
    }

    @Override
    public float defineTaxPercentage(boolean onlineCourse, float courseVat, String userTaxId, String userCountry) {

        if (onlineCourse){
            return getOnlineTaxPercentage(courseVat, userTaxId, userCountry);
        } else {
            return courseVat;
        }

    }

    private float getOnlineTaxPercentage(float courseVat, String userTaxId, String userCountry) {

        if("NL".equalsIgnoreCase(userCountry)){ return courseVat; }
        if (isEuCountryCode(userCountry)){
            if (isValidVAT(userCountry, userTaxId)){
                return 0f;
            } else {
                return courseVat;
            }
        }
        return 0f;
    }
}
