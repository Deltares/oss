package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public class Location extends AbsDsdArticle {
    private boolean storeInParentSite;
    private String city = "";
    private String country = "";
    private String address = "";
    private String postalCode = "";
    private String website = null;

    public Location(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String storeInParentSite = XmlContentParserUtils.getDynamicContentByName(document, "storeInParentSite", true);
            this.storeInParentSite = Boolean.parseBoolean(storeInParentSite);
            this.city = XmlContentParserUtils.getDynamicContentByName(document, "city", false);
            this.country = XmlContentParserUtils.getDynamicContentByName(document, "country", false);
            this.address = XmlContentParserUtils.getDynamicContentByName(document, "address", false);
            this.postalCode = XmlContentParserUtils.getDynamicContentByName(document, "postalcode", false);
            this.website = XmlContentParserUtils.getDynamicContentByName(document, "website", true);
            if (this.website != null && !this.website.toLowerCase().startsWith("http")){
                this.website = "http://" + this.website; //we need to do this otherwise Liferay makes href relative.
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Location.name();
    }

    @Override
    public boolean storeInParentSite() {
        return storeInParentSite;
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
}
