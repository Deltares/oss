package nl.deltares.dsd.model;

public class Person {

    private String firstName;
    private String lastName;
    private String email;
    private String academicTitle;
    private String jobTitle;
    private String organization;

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

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String accademicTitle) {
        this.academicTitle = accademicTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
