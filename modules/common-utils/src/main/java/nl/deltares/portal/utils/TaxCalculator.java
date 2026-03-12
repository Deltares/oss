package nl.deltares.portal.utils;

public interface TaxCalculator {

    boolean isEuCountryCode(String countryCode);

    boolean isValidVAT(String countryCode, String vatCode);

    float defineTaxPercentage(boolean onlineCourse, float courseVat, String userTaxId, String userCountry);
}
