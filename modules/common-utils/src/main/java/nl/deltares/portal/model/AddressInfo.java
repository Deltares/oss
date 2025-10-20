package nl.deltares.portal.model;

import java.io.Serializable;

public class AddressInfo implements Serializable {

    String addressIdentifier = null;
    String addressName = null;
    String street = null;
    String postal = null;
    String city = null;
    String countryA2Code = null;
    String phone = null;
    String website = null;
    boolean isDefaultBillingAddress = true;

    public String getAddressIdentifier() {
        return addressIdentifier;
    }

    public void setAddressIdentifier(String addressIdentifier) {
        this.addressIdentifier = addressIdentifier;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getCountryA2Code() {
        return countryA2Code;
    }

    public void setCountryA2Code(String countryA2Code) {
        this.countryA2Code = countryA2Code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isDefaultBillingAddress() {
        return isDefaultBillingAddress;
    }

    public void setDefaultBillingAddress(boolean defaultBillingAddress) {
        isDefaultBillingAddress = defaultBillingAddress;
    }
}
