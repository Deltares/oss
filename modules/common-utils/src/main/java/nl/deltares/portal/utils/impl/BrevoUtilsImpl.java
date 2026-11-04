package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.model.subscriptions.ConsentGroup;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.EmailSubscriptionUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static nl.deltares.portal.utils.HttpClientUtils.getConnection;
import static nl.deltares.portal.utils.HttpClientUtils.readAll;

@Component(
        immediate = true,
        service = EmailSubscriptionUtils.class
)
public class BrevoUtilsImpl implements EmailSubscriptionUtils {

    private static final Log LOG = LogFactoryUtil.getLog(BrevoUtilsImpl.class);

    private static final String API_KEY = "sendinblue.apikey";
    private static final String API_NAME = "api-key";
    private static final String BASEURL_KEY = "sendinblue.baseurl";
    private static final String FOLDER_ID_KEY = "sendinblue.folderid";
    private String baseApiPath;

    @Override
    public boolean isActive() {
        return PropsUtil.contains(API_KEY);
    }

    private boolean isSubscriptionInList(int subscriptionId, JSONArray listIds) {
        boolean[] subscribed = {false};

        if (listIds == null) return false;
        for (int i = 0; i < listIds.length(); i++) {
            if (subscriptionId == listIds.getInt(i)) {
                subscribed[0] = true;
                break;
            }
        }
        return subscribed[0];
    }

    @Override
    public List<ConsentGroup> getConsentGroups(String emailAddress) {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));

        List<ConsentGroup> consentGroups = new ArrayList<>();

        //open connection
        String jsonResponse;
        try {
            HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/" + emailAddress,
                    "GET", headers);
            checkResponse(connection);
            jsonResponse = readAll(connection);
        } catch (Exception e) {
            LOG.warn(String.format("Failed to get consent groups for %s: %s", emailAddress, e.getMessage()));
            return Collections.emptyList();
        }

        try {
            final JSONObject jsonObject = JsonContentUtils.parseContent(jsonResponse);
            final JSONArray groups = jsonObject.getJSONArray("consentGroups");
            for (int i = 0; i < groups.length(); i++) {
                final JSONObject group = groups.getJSONObject(i);
                ConsentGroup consentGroup = getConsentGroup(group.getString("id"));
                if (consentGroup != null) {
                    consentGroup.setSelected(group.getString("status").equals("subscribed"));
                    consentGroups.add(consentGroup);
                }
            }
        } catch (JSONException e) {
            LOG.warn(String.format("Failed to parse consent groups: %s", e.getMessage()));
        }
        return consentGroups;
    }

    private ConsentGroup getConsentGroup(String id) {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));

        String jsonResponse;
        try {
            HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/consent-groups/" + id,
                    "GET", headers);
            checkResponse(connection);
            jsonResponse = readAll(connection);
        } catch (Exception e) {
            LOG.warn(String.format("Failed to get consent group %s: %s", id, e.getMessage()));
            return null;
        }

        try {
            final JSONObject jsonObject = JsonContentUtils.parseContent(jsonResponse);
            final String name = jsonObject.getString("name");
            final String description = jsonObject.getString("description");
            return new ConsentGroup(id, name, description);
        } catch (JSONException e) {
            LOG.warn(String.format("Failed to parse consent groups: %s", e.getMessage()));
        }

        return null;
    }

    @Override
    public void updateConsentGroups(User user, List<ConsentGroup> consentGroups) throws Exception {
        JSONObject contactInfo = getContactInfo(user.getEmailAddress());
        if (contactInfo == null) {
            contactInfo = createContact(user);
            addContact(contactInfo);
        }
        updateConsentGroups(consentGroups, contactInfo);
    }

    private void updateConsentGroups(List<ConsentGroup> consentGroups, JSONObject contactInfo) throws IOException {
        JSONObject unlinkGroupObject = JSONFactoryUtil.createJSONObject();
        JSONArray unlinkGroupIds = JSONFactoryUtil.createJSONArray();
        unlinkGroupObject.put("email", unlinkGroupIds);
        JSONObject lingGroupObject = JSONFactoryUtil.createJSONObject();
        JSONArray lingGroupIds = JSONFactoryUtil.createJSONArray();
        lingGroupObject.put("email", lingGroupIds);
        contactInfo.put("unlinkConsentGroupIds", unlinkGroupObject);
        contactInfo.put("consentGroupIds", lingGroupObject);

        for (ConsentGroup consentGroup : consentGroups) {
            int id = Integer.parseInt(consentGroup.getId());
            if (consentGroup.isSelected()){
                lingGroupIds.put(id);
            } else {
                unlinkGroupIds.put(id);
            }
        }
        updateContact(contactInfo);
    }

    @Override
    public void updateSubscriptions(User user, List<SubscriptionSelection> subscriptions) throws Exception {
        JSONObject contactInfo = getContactInfo(user.getEmailAddress());
        if (contactInfo == null) {
            contactInfo = createContact(user);
            addContact(contactInfo);
        }
        updateSubscriptions(contactInfo, subscriptions);
    }

    private void updateSubscriptions(JSONObject contactInfo, List<SubscriptionSelection> subscriptions) throws IOException {

        JSONArray linkListIds = JSONFactoryUtil.createJSONArray();
        contactInfo.put("listIds", linkListIds);
        JSONArray unlinkListIds = JSONFactoryUtil.createJSONArray();
        contactInfo.put("unlinkListIds", unlinkListIds);

        for (SubscriptionSelection subscription : subscriptions) {
            int id;
            try {
                id = Integer.parseInt(subscription.getId());
            } catch (NumberFormatException e) {
                LOG.warn(String.format("Invalid subscription id %s: %s", subscription.getId(), e.getMessage()));
                continue;
            }
            if (subscription.isSelected()){
                linkListIds.put(id);
            } else {
                unlinkListIds.put(id);
            }
        }
        updateContact(contactInfo);
    }

    @Override
    public List<SubscriptionSelection> getSubscriptions(String emailAddress) throws IOException {
        final int[] folderIds = getFolderIds();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));

        List<SubscriptionSelection> subscriptions = new ArrayList<>();
        for (int folderId : folderIds) {
            //open connection
            HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/folders/" + folderId + "/lists?limit=50",
                    "GET", headers);
            checkResponse(connection);
            String jsonResponse = readAll(connection);
            try {
                final JSONObject jsonObject = JsonContentUtils.parseContent(jsonResponse);
                final JSONArray lists = jsonObject.getJSONArray("lists");
                for (int i = 0; i < lists.length(); i++) {
                    final JSONObject list = lists.getJSONObject(i);
                    SubscriptionSelection subscription = new SubscriptionSelection(list.getString("id"), list.getString("name"));
                    subscription.setSelected(false);
                    subscriptions.add(subscription);
                }
            } catch (JSONException e) {
                LOG.warn(String.format("Failed to parse lists from response for folder  %s : %s", Arrays.toString(folderIds), e.getMessage()));
            }

        }
        if (emailAddress != null) setUserSubscriptionSelection(emailAddress, subscriptions);
        return subscriptions;
    }

    private int[] getFolderIds() {
        final String configuredFolderIds;
        if (PropsUtil.contains(FOLDER_ID_KEY)) {
            configuredFolderIds = PropsUtil.get(FOLDER_ID_KEY);
            if (configuredFolderIds.isEmpty()) return new int[0];
        } else {
            return new int[0];
        }
        final String[] ids = configuredFolderIds.split(";");
        final int[] folderIds = new int[ids.length];
        for (int i = 0; i < ids.length; i++) {
            folderIds[i] = Integer.parseInt(ids[i]);
        }
        return folderIds;
    }

    private void setUserSubscriptionSelection(String email, List<SubscriptionSelection> allSubscriptions) throws IOException {

        final JSONObject contactInfo = getContactInfo(email);
        if (contactInfo == null) return;

        final JSONArray listIds = contactInfo.getJSONArray("listIds");
        if (listIds == null || listIds.length() == 0) return;

        allSubscriptions.forEach(subscription -> {
            if (isSubscriptionInList(Integer.parseInt(subscription.getId()), listIds)) {
                subscription.setSelected(true);
            }
        });
    }

    private void updateContact(JSONObject jsonUser) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));
        final String id = jsonUser.getString("id");
        //open connection
        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/" + id, "PUT", headers);
        connection.setDoOutput(true);
        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            w.write(jsonUser.toJSONString());
        }
        checkResponse(connection);
    }

    private void addContact(JSONObject jsonUser) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));
        //open connection
        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts", "POST", headers);
        connection.setDoOutput(true);
        try (Writer w = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            w.write(jsonUser.toJSONString());
        }
        checkResponse(connection);
    }

    private JSONObject createContact(User user) {

       JSONObject contactInfo = JSONFactoryUtil.createJSONObject();
       final JSONObject jsonAttributes = JSONFactoryUtil.createJSONObject();
       jsonAttributes.put("FIRSTNAME", user.getFirstName());
       jsonAttributes.put("LASTNAME", user.getLastName());
       contactInfo.put("attributes", jsonAttributes);
       contactInfo.put("email", user.getEmailAddress());
       contactInfo.put("emailBlacklisted", false);
       contactInfo.put("smsBlacklisted", false);
       contactInfo.put("updateEnabled", false);
       return contactInfo;
    }

    public static int checkResponse(HttpURLConnection urlConnection) throws IOException {
        int responseCode = urlConnection.getResponseCode();
        if (responseCode > 299) {

            if (responseCode == 404 && userNotFound(urlConnection.getResponseMessage())) {
                return responseCode;
            }
            throw new IOException("Error " + responseCode + ": " + urlConnection.getResponseMessage());
        }
        return responseCode;
    }

    public JSONObject getContactInfo(String emailAddress) throws IOException {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));

        //open connection
        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/" + URLEncoder.encode(emailAddress, StandardCharsets.UTF_8),
                "GET", headers);

        //get response
        int responseCode = checkResponse(connection);
        if (responseCode == 404) return null; //user not found

        String jsonResponse = readAll(connection);
        //Keycloak wraps all attributes in a json array. we need to remove this
        try {
            return JsonContentUtils.parseContent(jsonResponse);
        } catch (JSONException e) {
            LOG.warn(String.format("Failed to parse contact details response for user %s : %s", emailAddress, e.getMessage()));
        }
        return null;
    }

    private static boolean userNotFound(String responseMessage) {
        try {
            final Map<String, String> jsonToMap = JsonContentUtils.parseJsonToMap(responseMessage);
            return jsonToMap.containsKey("code") && jsonToMap.get("code").equals("document_not_found");
        } catch (JSONException e) {
            return true;
        }

    }

    private String getBaseApiPath() {
        if (baseApiPath != null) return baseApiPath;
        if (!PropsUtil.contains(BASEURL_KEY)) {
            LOG.info(String.format("Missing property %s in portal-ext.properties file", BASEURL_KEY));
            return null;
        }
        baseApiPath = PropsUtil.get(BASEURL_KEY);

        if (baseApiPath.endsWith("/")) {
            return baseApiPath;
        }
        baseApiPath += '/';
        return baseApiPath;
    }

}
