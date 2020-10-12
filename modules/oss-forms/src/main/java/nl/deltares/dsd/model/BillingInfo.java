package nl.deltares.dsd.model;

import nl.deltares.portal.utils.KeycloakUtils;

public class BillingInfo {

    String name = null;
    String email = null;
    String address = null;
    String postal = null;
    String city = null;
    String country = null;
    String vat = null;
    String reference = null;

    public static KeycloakUtils.ATTRIBUTES getCorrespondingUserAttributeKey(KeycloakUtils.BILLING_ATTRIBUTES billingKey){
        switch (billingKey){
            case billing_city:
                return KeycloakUtils.ATTRIBUTES.org_city;
            case billing_name:
                return KeycloakUtils.ATTRIBUTES.org_name;
            case billing_email:
                return KeycloakUtils.ATTRIBUTES.email;
            case billing_postal:
                return KeycloakUtils.ATTRIBUTES.org_postal;
            case billing_address:
                return KeycloakUtils.ATTRIBUTES.org_address;
            case billing_country:
                return KeycloakUtils.ATTRIBUTES.org_country;
            case billing_reference:
                return KeycloakUtils.ATTRIBUTES.pay_reference;
            case billing_vat:
                return KeycloakUtils.ATTRIBUTES.org_vat;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + billingKey);
        }
    }

    public String getAttribute(KeycloakUtils.BILLING_ATTRIBUTES key){
        switch (key){
            case billing_city:
                return city;
            case billing_name:
                return name;
            case billing_email:
                return email;
            case billing_postal:
                return postal;
            case billing_address:
                return address;
            case billing_country:
                return country;
            case billing_reference:
                return reference;
            case billing_vat:
                return vat;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }
    }

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
            case billing_reference:
                reference = value;
                break;
            case billing_vat:
                vat = value;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }

    }

    public boolean isUseOrganization() {
        return email != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
