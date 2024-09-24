package nl.deltares.portal.constants;

public class BillingConstants{

    //billing attributes
    public static final String EMAIL="billing_email";
    public static final String FIRST_NAME="billing_first_name";
    public static final String LAST_NAME="billing_last_name";
    public static final String PAYMENT_REFERENCE="billing_payment_reference";
    public static final String PAYMENT_METHOD="billing_payment_method";

    public static final String ORG_NAME= "billing_org_name";
    public static final String ORG_STREET="billing_org_address";
    public static final String ORG_POSTAL= "billing_org_postal";
    public static final String ORG_CITY="billing_org_city";
    public static final String ORG_COUNTRY_CODE ="billing_org_country";
    public static final String ORG_PHONE="billing_org_phone";
    public static final String ORG_EXTERNAL_REFERENCE_CODE="billing_org_external_reference_code";
    public static final String ORG_VAT="billing_org_vat";

    public static final String[] ORG_KEYS = {ORG_NAME, ORG_STREET, ORG_POSTAL, ORG_CITY, ORG_COUNTRY_CODE,
            ORG_PHONE, ORG_EXTERNAL_REFERENCE_CODE, ORG_VAT};

    public static final String[] BILLING_KEY = {EMAIL, FIRST_NAME, LAST_NAME, PAYMENT_REFERENCE, PAYMENT_METHOD};


}
