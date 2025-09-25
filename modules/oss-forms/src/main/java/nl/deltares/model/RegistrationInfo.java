package nl.deltares.model;

public class RegistrationInfo {

    private String salutation = null;
    private String firstName = null;
    private String lastName = null;
    private String email = null;
    private String remarks = null;
    private String articleId = null;
    private long resourceId = 0;
    private float price = 0;
    private String title;
    private long parentResourceId = 0;
    private String parentTitle = null;

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

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public long getParentResourceId() {
        return parentResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public void setParentResourceId(long parentResourceId) {
        this.parentResourceId = parentResourceId;
    }

    public boolean isChildRelation(){
        return this.parentResourceId > 0;
    }

    public String getArticleId() {
        return this.articleId;
    }
}
