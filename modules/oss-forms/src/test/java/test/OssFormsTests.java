package test;

import nl.deltares.model.BillingInfo;
import org.junit.Assert;

import java.util.Map;

public class OssFormsTests {

    @org.junit.Test
    public void testBillingInfoToMap() {
        BillingInfo billingInfo = new BillingInfo();
        try {
            Map<String, String> map = billingInfo.toMap();
        } catch (Exception e){
            Assert.fail("toMap should not throw an exception");
        }


    }
}
