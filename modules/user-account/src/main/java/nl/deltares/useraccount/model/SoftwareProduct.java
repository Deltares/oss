package nl.deltares.useraccount.model;

public class SoftwareProduct {


    private String softwareSuiteName;
    private long softwareSuiteId;
    private String softwareProductName;
    private long softwareProductId;

    public SoftwareProduct() {

    }

    public String getSoftwareProductName() {
        return softwareProductName;
    }

    public void setSoftwareProductName(String softwareProductName) {
        this.softwareProductName = softwareProductName;
    }


}
