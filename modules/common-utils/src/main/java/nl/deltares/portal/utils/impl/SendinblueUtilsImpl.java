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

import static nl.deltares.portal.utils.HttpClientUtils.getConnection;
import static nl.deltares.portal.utils.HttpClientUtils.readAll;

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
        if (PropsUtil.contains(DEFAULT_KEY)) {
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
        final ArrayList<Integer> intList = new ArrayList<>();
        subscriptionIds.forEach(s -> intList.add(Integer.parseInt(s)));
        return isSubscriptionInList(intList, contactInfo.getJSONArray("listIds"));

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

    private boolean isSubscriptionInList(List<Integer> subscriptionIds, JSONArray listIds) {
        boolean[] subscribed = {false};

        if (listIds == null) return false;
        for (int i = 0; i < listIds.length(); i++) {
            if (subscriptionIds.contains(listIds.getInt(i))) {
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
        subscribeAll(user, Collections.singletonList(subscriptionId));
    }

    @Override
    public void subscribeAll(User user, List<String> subscriptionIds) throws Exception {

        final JSONObject contactInfo = getContactInfo(user.getEmailAddress());
        JSONObject jsonUser = convertToContact(user, contactInfo);
        if (addSubscriptions(jsonUser, subscriptionIds)) {
            if (contactInfo == null) {
                addContact(jsonUser);
            } else {
                updateContact(jsonUser);
            }
            LOG.info(String.format("User %s is subscribed for subscriptions %s", user.getEmailAddress(), subscriptionIds));
        }
    }


    @Override
    public void unsubscribe(String email, String subscriptionId) throws Exception {
        unsubscribeAll(email, Collections.singletonList(subscriptionId));
    }

    @Override
    public void unsubscribeAll(String email, List<String> subscriptionIds) throws Exception {

        final JSONObject contactInfo = getContactInfo(email);
        if (contactInfo == null) return; // user doesn't exist

        JSONObject jsonUser = convertToContact(null, contactInfo);
        if (removeSubscriptions(jsonUser, subscriptionIds)) {
            updateContact(jsonUser);
            LOG.info(String.format("User %s is un-subscribed for subscription %s", email, subscriptionIds));
        }
    }

    @Override
    public void deleteUser(String emailAddress) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));

        //open connection
        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/" + URLEncoder.encode(emailAddress, StandardCharsets.UTF_8),
                "DELETE", headers);

        final int response = checkResponse(connection);
        if (response == 404) {
            LOG.info(String.format("User %s is not an existing Sendinblue contact", emailAddress));
        } else {
            LOG.info(String.format("User %s is removed from Sendinblue contacts", emailAddress));
        }
    }

    @Override
    public List<SubscriptionSelection> getSubscriptions(String emailAddress) throws IOException {
        final int[] folderIds = getFolderIds();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(API_NAME, PropsUtil.get(API_KEY));

        //open connection
        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/lists",
                "GET", headers);
        checkResponse(connection);

        String jsonResponse = readAll(connection);
        List<SubscriptionSelection> subscriptions = new ArrayList<>();
        try {
            final JSONObject jsonObject = JsonContentUtils.parseContent(jsonResponse);
            final JSONArray lists = jsonObject.getJSONArray("lists");
            for (int i = 0; i < lists.length(); i++) {
                final JSONObject list = lists.getJSONObject(i);
                final int folderId = list.getInt("folderId");
                if (folderIds.length > 0 && Arrays.stream(folderIds).noneMatch(id -> id == folderId)) continue;
                subscriptions.add(new SubscriptionSelection(list.getString("id"), list.getString("name")));
            }
        } catch (JSONException e) {
            LOG.warn(String.format("Failed to parse lists from response for folder  %s : %s", Arrays.toString(folderIds), e.getMessage()));
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
        final String email = jsonUser.getString("email");
        //open connection
        HttpURLConnection connection = getConnection(getBaseApiPath() + "contacts/" + email, "PUT", headers);
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

    private boolean addSubscriptions(JSONObject jsonUser, List<String> newIds) {
        final JSONArray listIds = jsonUser.getJSONArray("listIds");
        final JSONArray newArray = JSONFactoryUtil.createJSONArray();
        for (String newId : newIds) {
            final int intId = Integer.parseInt(newId);
            if (!isSubscriptionInList(Collections.singletonList(intId), listIds)) {
                newArray.put(intId);
            }
        }
        jsonUser.put("listIds", newArray); //put only the values that need to be added
        return newArray.length() > 0;
    }

    private boolean removeSubscriptions(JSONObject jsonUser, List<String> removeIds) {
        final JSONArray listIds = jsonUser.getJSONArray("listIds");
        jsonUser.remove("listIds");
        final JSONArray removeArray = JSONFactoryUtil.createJSONArray();
        for (int i = 0; i < listIds.length(); i++) {
            final String id = listIds.getString(i);
            if (removeIds.contains(id)) {
                removeArray.put(listIds.getInt(i));
            }
        }
        jsonUser.put("unlinkListIds", removeArray);
        return removeArray.length() > 0;
    }

    private JSONObject convertToContact(User user, JSONObject contactInfo) {

        String firstName;
        String lastName;
        String email;
        JSONArray listIds;
        if (contactInfo != null) {
            firstName = contactInfo.getJSONObject("attributes").getString("FIRSTNAME");
            lastName = contactInfo.getJSONObject("attributes").getString("LASTNAME");
            email = contactInfo.getString("email");
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
