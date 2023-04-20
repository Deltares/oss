package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
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

import static nl.deltares.portal.utils.HttpClientUtils.*;

@Component(
        immediate = true,
        service = EmailSubscriptionUtils.class
)
public class SendinblueUtilsImpl implements EmailSubscriptionUtils {

    private static final Log LOG = LogFactoryUtil.getLog(SendinblueUtilsImpl.class);

    private static final String API_KEY = "sendinblue.apikey";
    private static final String API_NAME = "api-key";
    private static final String BASEURL_KEY = "sendinblue.baseurl";
    private static final String FOLDER_ID_KEY = "sendinblue.folderid";
    private static final String DEFAULT_KEY = "sendinblue.subscriptions.default";
    private String baseApiPath;
    private Boolean defaultSubscriptionsUtil = null;

    @Override
    public boolean isDefault() {
        if (defaultSubscriptionsUtil != null) return defaultSubscriptionsUtil;
        if (PropsUtil.contains(DEFAULT_KEY)){
            defaultSubscriptionsUtil = Boolean.parseBoolean(PropsUtil.get(DEFAULT_KEY));
        } else {
            defaultSubscriptionsUtil = false;
        }
        return defaultSubscriptionsUtil;
    }

    @Override
    public boolean isActive() {
        return PropsUtil.contains(API_KEY);
    }

    @Override
    public boolean isSubscribed(String emailAddress, final List<String> subscriptionIds) throws IOException {

        final JSONObject contactInfo = getContactInfo(emailAddress);
        if (contactInfo == null) return false;

        //Keycloak wraps all attributes in a json array. we need to remove this
        return isSubscriptionInList(subscriptionIds, contactInfo.getJSONArray("listIds"));

    }

    private boolean isSubscriptionInList(String subscriptionId, JSONArray listIds) {
        boolean[] subscribed = {false};

        if (listIds == null) return false;
        for (int i = 0; i < listIds.length(); i++) {
            if (subscriptionId.equals(listIds.getString(i))) {
                subscribed[0] = true;
                break;
            }
        }
        return subscribed[0];
    }
    private boolean isSubscriptionInList(List<String> subscriptionIds, JSONArray listIds) {
        boolean[] subscribed = {false};

        if (listIds == null) return false;
        for (int i = 0; i < listIds.length(); i++) {
            if (subscriptionIds.contains(listIds.getString(i))) {
                subscribed[0] = true;
                break;
            }
        }
        return subscribed[0];
    }

    @Override
    public boolean isSubscribed(String email, String subscriptionId) throws IOException {
        return isSubscribed(email, Collections.singletonList(subscriptionId));
    }

    @Override
    public void subscribe(User user, String subscriptionId) throws Exception {

        final JSONObject contactInfo = getContactInfo(user.getEmailAddress());
        JSONObject jsonUser = convertToContact(user, contactInfo);
        addSubscription(jsonUser, subscriptionId);
        updateContact(jsonUser);

        LOG.info(String.format("User %s is subscribed for subscription %s", user.getEmailAddress(), subscriptionId ));
    }

    @Override
    public void unsubscribe(String email, String subscriptionId) throws Exception {

        final JSONObject contactInfo = getContactInfo(email);
        if (contactInfo == null) return; // user doesn't exist

        JSONObject jsonUser = convertToContact(null, contactInfo);
        removeSubscription(jsonUser, subscriptionId);
        updateContact(jsonUser);

        LOG.info(String.format("User %s is un-subscribed for subscription %s", email, subscriptionId ));
    }

    @Override
    public void deleteUser(String emailAddress) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));

        //open connection
        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/" + URLEncoder.encode(emailAddress, StandardCharsets.UTF_8.toString()),
                "DELETE", headers);

        final int response = checkResponse(connection);
        if (response == 404){
            LOG.info(String.format("User %s is not an existing Sendinblue contact", emailAddress));
        } else {
            LOG.info(String.format("User %s is removed from Sendinblue contacts", emailAddress));
        }
    }

    @Override
    public List<SubscriptionSelection> getSubscriptions(String emailAddress) throws IOException {
        if (!PropsUtil.contains(FOLDER_ID_KEY)){
            LOG.error("No 'sendinblue.folderid' configured in portal-ext.properties!");
            return Collections.emptyList();
        }

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));

        final String folderId = PropsUtil.get(FOLDER_ID_KEY);
        //open connection

        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/folders/" + folderId + "/lists?limit=50&offset=0&sort=desc",
                "GET", headers);
        final int response = checkResponse(connection);
        if (response == 404){
            LOG.info(String.format("FolderId %s is not an existing Sendinblue folder", folderId));
            return Collections.emptyList();
        }

        String jsonResponse = readAll(connection);
        List<SubscriptionSelection> subscriptions = new ArrayList<>();
        try {
            final JSONObject jsonObject = JsonContentUtils.parseContent(jsonResponse);
            final JSONArray lists = jsonObject.getJSONArray("lists");
            for (int i = 0; i < lists.length(); i++) {
                final JSONObject list = lists.getJSONObject(i);
                subscriptions.add(new SubscriptionSelection(list.getString("id"), list.getString("name")));
            }
        } catch (JSONException e) {
            LOG.warn(String.format("Failed to parse lists from response for folder  %s : %s", folderId, e.getMessage()));
        }

        if (emailAddress != null) setUserSubscriptionSelection(emailAddress, subscriptions);
        return subscriptions;
    }

    private void setUserSubscriptionSelection(String email, List<SubscriptionSelection> allSubscriptions) throws IOException {

        final JSONObject contactInfo = getContactInfo(email);
        if ( contactInfo == null) return;

        final JSONArray listIds = contactInfo.getJSONArray("listIds");
        if (listIds == null || listIds.length() == 0) return;

        allSubscriptions.forEach(subscription -> {
            if (isSubscriptionInList(subscription.getId(), listIds)){
                subscription.setSelected(true);
            }
        });
    }

    private void updateContact(JSONObject jsonUser) throws IOException {
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

    private void addSubscription(JSONObject jsonUser, String listId) {
        final JSONArray listIds = jsonUser.getJSONArray("listIds");
        if (!isSubscriptionInList(Collections.singletonList(listId), listIds)){
            listIds.put(listId);
        }
    }

    private void removeSubscription(JSONObject jsonUser, String listId) {
        final JSONArray listIds = jsonUser.getJSONArray("listIds");
        final JSONArray newArray = JSONFactoryUtil.createJSONArray();
        for (int i = 0; i < listIds.length(); i++) {
            final String id = listIds.getString(i);
            if (id.equals(listId)) continue;
            newArray.put(id);
        }
        jsonUser.put("listIds", newArray);
    }

    private JSONObject convertToContact(User user, JSONObject contactInfo)  {

        String firstName;
        String lastName;
        String email;
        JSONArray listIds;
        if (contactInfo != null) {
            firstName = contactInfo.getJSONObject("attributes").getString("FIRSTNAME");
            lastName =  contactInfo.getJSONObject("attributes").getString("LASTNAME");
            email =  contactInfo.getString("email");
            listIds = contactInfo.getJSONArray("listIds");
        } else {
            firstName = user.getFirstName();
            lastName = user.getLastName();
            email = user.getEmailAddress();
            listIds = JSONFactoryUtil.createJSONArray();
        }
        final JSONObject jsonUser = JSONFactoryUtil.createJSONObject();
        final JSONObject jsonAttributes = JSONFactoryUtil.createJSONObject();
        jsonAttributes.put("FIRSTNAME", firstName);
        jsonAttributes.put("LASTNAME", lastName);
        jsonUser.put("attributes", jsonAttributes);
        jsonUser.put("email", email);
        jsonUser.put("emailBlacklisted", false);
        jsonUser.put("smsBlacklisted", false);
        jsonUser.put("updateEnabled", contactInfo != null);
        jsonUser.put("listIds", listIds);
        return jsonUser;
    }

    public static int checkResponse(HttpURLConnection urlConnection) throws IOException {
        int responseCode = urlConnection.getResponseCode();
        if (responseCode > 299) {

            if (responseCode == 404 && userNotFound(urlConnection.getResponseMessage())){
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
        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/" + URLEncoder.encode(emailAddress, StandardCharsets.UTF_8.toString()),
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
        baseApiPath =  PropsUtil.get(BASEURL_KEY);

        if(baseApiPath.endsWith("/")){
            return baseApiPath;
        }
        baseApiPath += '/';
        return baseApiPath;
    }

}
