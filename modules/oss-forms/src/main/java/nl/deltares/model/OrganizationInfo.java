package nl.deltares.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

public class OrganizationInfo {

    public enum ATTRIBUTES {
        org_external_reference,
        org_registration_id,
        org_address,
        org_city,
        org_country,
        org_name,
        org_postal,
        org_vat,
        org_website,
        org_phone
    }

    String registrationId = null;
    String externalReference = null;
    String name = null;
    String address = null;
    String postal = null;
    String city = null;
    String country = null;
    String vat = null;
    String phone = null;
    String website = null;


    public String getAttribute(ATTRIBUTES key){
        switch (key){
            case org_registration_id:
                return registrationId;
            case org_external_reference:
                return externalReference;
            case org_name:
                return name;
            case org_address:
                return address;
            case org_postal:
                return postal;
            case org_city:
                return city;
            case org_country:
                return country;
            case org_vat:
                return vat;
            case org_phone:
                return phone;
            case org_website:
                return website;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }
    }

    public void setAttribute(ATTRIBUTES key, String value){

        if (value == null || value.isEmpty()) return;

        switch (key){
            case org_registration_id:
                registrationId = value;
                break;
            case org_external_reference:
                externalReference = value;
                break;
            case org_name:
                name = value;
                break;
            case org_address:
                address = value;
                break;
            case org_postal:
                postal = value;
                break;
            case org_city:
                city = value;
                break;
            case org_country:
                country = value;
                break;
            case org_vat:
                vat = value;
                break;
            case org_phone:
                phone = value;
                break;
            case org_website:
                website = value;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
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

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }
}
