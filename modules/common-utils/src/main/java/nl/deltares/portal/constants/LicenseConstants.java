package nl.deltares.portal.constants;

public class LicenseConstants {

    //license request keys
    /*
    Expected values: "new_usb_dongle", "existing_usb_dongle", "mac_address", "not_provided"
     */
    public static final String LOCK_TYPE="licenseinfo.locktypes";
    public static final String DONGLE_NUMBER="lock_address";
    /*
    Expected values network, standalone, not_provided
     */
    public static final String LICENSE_TYPE="licenseinfo.licensetypes";

    public static final String[] KEYS = {LOCK_TYPE, LICENSE_TYPE, DONGLE_NUMBER};


    public static final String LOCK_TYPES_NEW_USB_DONGLE="lock_type_new_usb_dongle";
    public static final String LOCK_TYPES_EXISTING_USB_DONGLE="lock_type_existing_usb_dongle";
    public static final String LOCK_TYPES_MAC_ADDRESS="lock_type_mac_address";
    public static final String LOCK_TYPES_NOT_PROVIDED="lock_type_not_provided";

    public static final String LICENSE_TYPES_NETWORK="license_type_network";
    public static final String LICENSE_TYPES_STANDALONE="license_type_standalone";
    public static final String LICENSE_TYPES_NOT_PROVIDED="license_type_not_provided";

}
