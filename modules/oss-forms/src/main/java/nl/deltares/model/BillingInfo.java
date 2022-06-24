package nl.deltares.model;

import com.liferay.portal.kernel.util.Validator;
import nl.deltares.portal.utils.KeycloakUtils;

import java.util.HashMap;
import java.util.Map;

public class BillingInfo {

    public enum ATTRIBUTES {
        billing_company,
        billing_email,
        billing_firstname,
        billing_lastname,
        billing_address,
        billing_postal,
        billing_city,
        billing_country,
        billing_reference,
        billing_vat,
        billing_preference,
        billing_phone,
        billing_website
    }

    String companyName = null;
    String firstName = null;
    String lastName = null;
    String email = null;
    String address = null;
    String postal = null;
    String city = null;
    String country = null;
    String vat = "";
    String reference = "";
    String preference = "payLink";
    String phone = "";
    String website = "";

    public static KeycloakUtils.ATTRIBUTES getCorrespondingUserAttributeKey(BillingInfo.ATTRIBUTES billingKey){
        switch (billingKey){
            case billing_city:
                return KeycloakUtils.ATTRIBUTES.org_city;
            case billing_firstname:
                return KeycloakUtils.ATTRIBUTES.first_name;
            case billing_lastname:
                return KeycloakUtils.ATTRIBUTES.last_name;
            case billing_email:
                return KeycloakUtils.ATTRIBUTES.email;
            case billing_postal:
                return KeycloakUtils.ATTRIBUTES.org_postal;
            case billing_address:
                return KeycloakUtils.ATTRIBUTES.org_address;
            case billing_country:
                return KeycloakUtils.ATTRIBUTES.org_country;
            case billing_vat:
                return KeycloakUtils.ATTRIBUTES.org_vat;
            case billing_phone:
                return KeycloakUtils.ATTRIBUTES.org_phone;
            case billing_website:
                return KeycloakUtils.ATTRIBUTES.org_website;
            case billing_company:
                return  KeycloakUtils.ATTRIBUTES.org_name;
            default:
                return null;
        }
    }

    public String getAttribute(ATTRIBUTES key){
        switch (key){
            case billing_city:
                return city;
            case billing_firstname:
                return firstName;
            case billing_lastname:
                return lastName;
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
            case billing_preference:
                return preference;
            case billing_phone:
                return phone;
            case billing_website:
                return website;
            case billing_company:
                return companyName;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }
    }

    public void setAttribute(ATTRIBUTES key, String value){
        switch (key){
            case billing_city:
                city = value;
                break;
            case billing_firstname:
                firstName = value;
                break;
            case billing_lastname:
                lastName = value;
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
            case billing_preference:
                preference = value;
                break;
            case billing_phone:
                phone = value;
                break;
            case billing_website:
                website = value;
                break;
            case billing_company:
                companyName = value;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }

    }

    public Map<String, String> toMap(){
        final HashMap<String, String> map = new HashMap<>();
        for (ATTRIBUTES key : ATTRIBUTES.values()) {
            final String value = getAttribute(key);
            if (Validator.isNotNull(value)) map.put(key.name(), value);

        }
        return map;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
