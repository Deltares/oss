package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;

import java.util.Locale;
import java.util.Map;

public class Location extends AbsDsdArticle {
    private String city = "";
    private String country = "";
    private String address = "";
    private String postalCode = "";
    private String website = null;
    private double longitude = -1;
    private double latitude = -1;
    private String locationType = "";

    public Location(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, locale);
        init();
    }

    private void init() throws PortalException {
        try {
            this.city = getFormFieldValue( "city", false);
            this.country = getFormFieldValue( "country", false);
            this.address = getFormFieldValue( "address", false);
            this.postalCode = getFormFieldValue( "postalcode", false);
            this.website = getFormFieldValue( "website", true);
            if (this.website != null && !this.website.toLowerCase().startsWith("http")){
                this.website = "http://" + this.website; //we need to do this otherwise Liferay makes href relative.
            }
            this.locationType = JsonContentUtils.parseJsonArrayToValue(getFormFieldValue( "locationType", false));
            String geoLocation = getFormFieldValue( "location", false);
            Map<String, String> coords = JsonContentUtils.parseJsonToMap(geoLocation);

            this.longitude = Double.parseDouble(coords.containsKey("longitude") ? coords.get("longitude") : coords.get("lng"));
            this.latitude =  Double.parseDouble(coords.containsKey("latitude")? coords.get("latitude") : coords.get("lat"));
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Location.name();
    }

    public String getCity(){
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress(){
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getWebsite() {
        return website;
    }

    public boolean hasCoordinates(){
        return latitude != -1 && longitude != -1;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getLocationType() {
        return locationType;
    }

    public boolean isOnline() {
        return locationType.equals("online");
    }
}
