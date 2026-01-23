package nl.deltares.portal.utils;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.LicenseFile;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

public interface LicenseManagerUtils {

    boolean isActive();

    Map<String, String> encryptLicense(LicenseFile licenseFile, User user) throws IOException, JSONException;

    Map<String, String> encryptLicense(String licenseType, User user) throws IOException, JSONException;

    JSONArray getCustomerLicenses(User user, String status, Long customerId)  throws IOException, JSONException;

    JSONArray getCustomerContacts(User user)  throws IOException, JSONException;

    static AbstractMap.SimpleEntry<Long, String> parseCustomerIdAndName(JSONArray customerContactsArray) {

        if (customerContactsArray == null || customerContactsArray.length() == 0) {
            return new AbstractMap.SimpleEntry<>(null, null);
        }
        JSONObject customerContactView = customerContactsArray.getJSONObject(0);
        JSONObject customerContactCustomer = customerContactView.getJSONObject("customerContactCustomer");
        long customerId = customerContactCustomer.getLong("customerId");
        String customerName = customerContactCustomer.getString("customerName");
        return new AbstractMap.SimpleEntry<>(customerId, customerName);

    }

    JSONObject generateCustomerLicenseFiles(Long customerId, String filterEmail) throws IOException, JSONException;

    JSONObject getProgress(String requestId) throws IOException, JSONException;

    void download(String requestId, File downloadFile) throws IOException, JSONException;

}
