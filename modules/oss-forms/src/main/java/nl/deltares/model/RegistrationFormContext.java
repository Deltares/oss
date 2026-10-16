package nl.deltares.model;

import nl.deltares.portal.model.subscriptions.SubscriptionSelection;

import java.io.Serializable;
import java.util.List;

public class RegistrationFormContext implements Serializable {

    private AccountInfo accountInfo;
    private BillingInfo billingInfo;
    private BadgeInfo badgeInfo;
    private RegistrationsInfo registrationsInfo;
    private List<SubscriptionSelection> subscriptions;

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public BadgeInfo getBadgeInfo() {
        return badgeInfo;
    }

    public void setBadgeInfo(BadgeInfo badgeInfo) {
        this.badgeInfo = badgeInfo;
    }

    public RegistrationsInfo getRegistrationsInfo() {
        return registrationsInfo;
    }

    public void setRegistrationsInfo(RegistrationsInfo registrationsInfo) {
        this.registrationsInfo = registrationsInfo;
    }

    public List<SubscriptionSelection> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionSelection> subscriptions) {
        this.subscriptions = subscriptions;
    }
}