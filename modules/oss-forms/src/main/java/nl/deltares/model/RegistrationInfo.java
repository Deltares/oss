package nl.deltares.model;

public class RegistrationInfo {

    private String salutation = null;
    private String firstName = null;
    private String lastName = null;
    private String email = null;
    private String remarks = null;
    private String articleId = null;
    private String initials = null;
    private float price = 0;
    private String title;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setRegistrationName(String title) {
        this.title = title;
    }

    public String getRegistrationName() {
        return this.title;
    }

    public String getArticleId() {
        return this.articleId;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getInitials() {
        return initials;
    }
}
