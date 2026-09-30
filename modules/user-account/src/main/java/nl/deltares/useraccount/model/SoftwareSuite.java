package nl.deltares.useraccount.model;

import java.util.*;

public class SoftwareSuite {


    private String suiteName;
    private long suiteId;
    private String suiteCode;
    private final List<SoftwareSuiteSubscription> subscriptionList = new ArrayList<>();
    private final List<SoftwareGroup> groups = new ArrayList<>();

    public SoftwareSuite() {

    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getSuiteCode() {
        return suiteCode;
    }

    public void setSuiteCode(String suiteCode) {
        this.suiteCode = suiteCode;
    }

    public long getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(long suiteId) {
        this.suiteId = suiteId;
    }

    public List<SoftwareSuiteSubscription> getSubscriptionList() {
        return Collections.unmodifiableList(subscriptionList);
    }

    public void addSubscription(SoftwareSuiteSubscription subscription) {
        subscriptionList.add(subscription);
    }

    public void addGroup(SoftwareGroup softwareGroup) {
        if (groups.contains(softwareGroup)) {return;}
        groups.add(softwareGroup);
    }

    public List<SoftwareGroup> getGroups() {
        return new ArrayList<>(groups);
    }
}
