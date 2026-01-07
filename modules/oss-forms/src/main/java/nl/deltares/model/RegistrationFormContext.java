package nl.deltares.model;

public class RegistrationFormContext {

    private AccountInfo accountInfo;
    private BillingInfo billingInfo;
    private BadgeInfo badgeInfo;
    private RegistrationsInfo registrationsInfo;

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
}