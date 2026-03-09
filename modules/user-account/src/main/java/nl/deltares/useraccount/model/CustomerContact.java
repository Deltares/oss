package nl.deltares.useraccount.model;

public class CustomerContact {

    private long customerId;
    private long contactId;
    private String contactName;
    private String contactEmail;
    private String contactSalutation;
    public boolean contactManageLicenses;

    public CustomerContact() {

    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
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

    public boolean isContactManageLicenses() {
        return contactManageLicenses;
    }

    public void setContactManageLicenses(boolean contactManageLicenses) {
        this.contactManageLicenses = contactManageLicenses;
    }

    public void setContactSalutation(String contactSalutation) {
        this.contactSalutation = contactSalutation;
    }
}
