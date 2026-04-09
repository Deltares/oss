package nl.deltares.portal.utils;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.LicenseFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface LicenseManagerUtils {

    boolean isActive();

    Map<String, String> encryptLicense(LicenseFile licenseFile, User user) throws IOException, JSONException;

    Map<String, String> encryptLicense(String licenseType, User user) throws IOException, JSONException;

    JSONArray getCustomerContactsForUser(User user)  throws IOException, JSONException;

    JSONArray getCustomerContactsForCustomerAndFilter(long customerId, boolean filterBetaTesters, boolean filterLicenseManagers)  throws IOException, JSONException;

    static Map<Long, String> parseCustomerIdAndName(JSONArray customerContactsArray) {

        if (customerContactsArray == null || customerContactsArray.length() == 0) {
            return Collections.emptyMap();
        }
        HashMap<Long, String> map = new HashMap<>();
        for (int i = 0; i < customerContactsArray.length(); i++) {
            JSONObject customerContactView = customerContactsArray.getJSONObject(i);
            JSONObject customerContactCustomer = customerContactView.getJSONObject("customerContactCustomer");
            long customerId = customerContactCustomer.getLong("customerId");
            String customerName = customerContactCustomer.getString("customerName");
            map.put(customerId, customerName);
        }
        return map;

    }

    static Map<String, Object> parseCustomerContact(JSONArray customerContactsArray, Long searchCustomerId) {
        if (customerContactsArray == null || customerContactsArray.length() == 0) {
            return Collections.emptyMap();
        }
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < customerContactsArray.length(); i++) {
            JSONObject customerContactView = customerContactsArray.getJSONObject(i);
            JSONObject customerContactCustomer = customerContactView.getJSONObject("customerContactCustomer");
            long customerId = customerContactCustomer.getLong("customerId");
            if (!searchCustomerId.equals(customerId)) continue;
            long customerContactId = customerContactView.getLong("customerContactId");
            map.put("customerContactId", customerContactId);
            boolean customerContactManageLicenses = customerContactView.getBoolean("customerContactManageLicenses");
            map.put("customerContactManageLicenses", customerContactManageLicenses);
            String maconomyId = customerContactCustomer.getString("customerMaconomyId");
            map.put("customerMaconomyId", maconomyId);
        }
        return map;
    }

    JSONArray getCustomerLicenses(User user, String state, Long customerId, Long customerContactId, boolean customerContactManageLicenses) throws IOException, JSONException;

    JSONObject generateCustomerLicenseFiles(Long customerId, String filterEmail) throws IOException, JSONException;

    JSONObject getProgress(String requestId) throws IOException, JSONException;

    void download(String requestId, File downloadFile) throws IOException, JSONException;

}
