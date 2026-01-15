package nl.deltares.useraccount.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoftwareSuite {


    private String suiteName;
    private long suiteId;
    private String suiteCode;
    private final List<SoftwareSuiteSubscription> subscriptionList = new ArrayList<>();

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
}
