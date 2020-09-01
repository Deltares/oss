package nl.deltares.dsd.model;

import nl.deltares.portal.utils.KeycloakUtils;

public class BillingInfo {

    boolean useOrganization = true;
    String name;
    String email;
    String address;
    String postal;
    String city;
    String country;

    public void setAttribute(KeycloakUtils.BILLING_ATTRIBUTES key, String value){
        switch (key){
            case billing_city:
                city = value;
                break;
            case billing_name:
                name = value;
                break;
            case billing_email:
                email = value;
                break;
            case billing_postal:
                postal = value;
                break;
            case billing_address:
                address = value;
                break;
            case billing_country:
                country = value;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }

        useOrganization = false;

    }

    public boolean isUseOrganization() {
        return useOrganization;
    }

    public void setUseOrganization(boolean useOrganization) {
        this.useOrganization = useOrganization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        useOrganization = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        useOrganization = false;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        useOrganization = false;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
        useOrganization = false;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        useOrganization = false;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        useOrganization = false;
    }
}
