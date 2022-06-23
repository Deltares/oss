package nl.deltares.model;

import java.util.concurrent.locks.Lock;

public class LicenseInfo {

    public enum LOCKTYPES { new_usb_dongle, existing_usb_dongle, mac_address, not_provided }
    public enum LICENSETYPES { network, standalone, not_provided }
    public enum ATTRIBUTES {lock_address}

    LOCKTYPES lockType = LOCKTYPES.not_provided;
    LICENSETYPES licenseType = LICENSETYPES.not_provided;
    String dongleNumber = null;

    public LOCKTYPES getLockType() {
        return lockType;
    }

    public void setLockType(LOCKTYPES lockType) {
        if (lockType != null) {
            this.lockType = lockType;
        } else {
            this.lockType = LOCKTYPES.not_provided;
        }
    }

    public LICENSETYPES getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LICENSETYPES licenseType) {
        if (licenseType != null) {
            this.licenseType = licenseType;
        } else {
            this.licenseType = LICENSETYPES.not_provided;
        }
    }

    public String getDongleNumber() {
        return dongleNumber;
    }

    public void setDongleNumber(String dongleNumber) {
        this.dongleNumber = dongleNumber;
    }
}
