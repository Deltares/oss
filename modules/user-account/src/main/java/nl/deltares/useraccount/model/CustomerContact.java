package nl.deltares.useraccount.model;

public class CustomerContact {

    private String contactName;
    private String contactEmail;
    private String contactSalutation;

    public CustomerContact() {

    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactSalutation() {
        return contactSalutation;
    }

    public void setContactSalutation(String contactSalutation) {
        this.contactSalutation = contactSalutation;
    }
}
