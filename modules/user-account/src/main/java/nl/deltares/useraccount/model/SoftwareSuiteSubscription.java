package nl.deltares.useraccount.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SoftwareSuiteSubscription {

    private String softwareProductName;
    private String softwareVersion;
    private String subscriptionState;
    private long subscriptionId;
    private String contractType;
    private Date startDate;
    private Date endDate;
    private int licenseCount;
    private int licenseUsed;
    private String supportLevelName;
    private int supportLevelValue;
    private int supportHours;

    private final List<CustomerContact> customerContactList = new ArrayList<>();
    private final List<Asset> assetList = new ArrayList<>();

    public SoftwareSuiteSubscription() {

    }

    public String getSoftwareProductName() {
        return softwareProductName;
    }

    public void setSoftwareProductName(String softwareProductName) {
        this.softwareProductName = softwareProductName;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getSubscriptionState() {
        return subscriptionState;
    }

    public void setSubscriptionState(String subscriptionState) {
        this.subscriptionState = subscriptionState;
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getLicenseCount() {
        return licenseCount;
    }

    public void setLicenseCount(int licenseCount) {
        this.licenseCount = licenseCount;
    }

    public int getLicenseUsed() {
        return licenseUsed;
    }

    public void setLicenseUsed(int licenseUsed) {
        this.licenseUsed = licenseUsed;
    }

    public String getSupportLevelName() {
        return supportLevelName;
    }

    public void setSupportLevelName(String supportLevelName) {
        this.supportLevelName = supportLevelName;
    }

    public int getSupportLevelValue() {
        return supportLevelValue;
    }

    public void setSupportLevelValue(int supportLevelValue) {
        this.supportLevelValue = supportLevelValue;
    }

    public int getSupportHours() {
        return supportHours;
    }

    public void setSupportHours(int supportHours) {
        this.supportHours = supportHours;
    }

    public List<CustomerContact> getCustomerContactList() {
        return Collections.unmodifiableList(customerContactList);
    }
    public void addCustomerContact(CustomerContact customerContact) {
        customerContactList.add(customerContact);
    }

    public List<Asset> getAssetList() {
        return Collections.unmodifiableList(assetList);
    }
    public void addAsset(Asset asset) {
        assetList.add(asset);
    }
}
