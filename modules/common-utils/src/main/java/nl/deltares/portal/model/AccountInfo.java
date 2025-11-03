package nl.deltares.portal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountInfo implements Serializable {

    String companyName = null;
    String companyIdentifier = null;
    List<String> emailDomains = new ArrayList<>();
    String vat = null;
    String type = "business";
    String website = null;
    AddressInfo addressInfo = null;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    public String[] getEmailDomains() {
        return emailDomains.toArray(new String[0]);
    }

    public void setEmailDomains(String[] emailDomains) {
        this.emailDomains.addAll(Arrays.asList(emailDomains));
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }
}
