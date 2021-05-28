package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringBundler;
import nl.deltares.portal.configuration.WebinarSiteConfiguration;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.WebinarUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ANewSpringUtils extends HttpClientUtils implements WebinarUtils {
    private final String API_KEY;
    private String basePath;
    private final WebinarSiteConfiguration configuration;

    public ANewSpringUtils(WebinarSiteConfiguration configuration) {
        this.configuration = configuration;
        this.API_KEY = configuration.aNewSpringApiKey();
    }

    @Override
    public boolean isActive() {
        return !API_KEY.isEmpty();
    }

    @Override
    public int registerUser(User user, Map<String, String> userAttributes, String courseId, String callerId, Map<String, String> properties) throws Exception {

        if (!userExists(user)){
            addUser(user, callerId);
        }
//        DO not subscribe user as this allocates a license.
//        if (!isUserSubscribed(user, courseId)){
//            return subscribe(user, courseId);
//        }
        return 0;
    }

    @Override
    public boolean isUserRegistered(User user, String courseId, Map<String, String> properties) throws Exception {
        return isUserSubscribed(user, courseId);
    }

    @Override
    public List<String> getAllCourseRegistrations(String courseId) throws Exception {
        String subscriptionsPath = StringBundler.concat(getBasePath(), "getStudentSubscriptions/", courseId);
        HttpURLConnection connection = getConnection(subscriptionsPath, "GET", getHeader("application/json"));
        //get response
        checkResponse(connection);
        String jsonResponse = readAll(connection.getInputStream());

        JSONObject jsonObject = JsonContentUtils.parseContent(jsonResponse);
        JSONArray students = jsonObject.getJSONArray("students");
        ArrayList<String> studentIds = new ArrayList<>();
        for (int i = 0; i < students.length(); i++) {
            JSONObject student = students.getJSONObject(i);
            String id = student.getString("id");
            if (!id.isEmpty()) studentIds.add(id);
        }
        return studentIds;
    }

    @Override
    public boolean isUserInCourseRegistrationsList(List<String> courseRegistrations, User user){
        return courseRegistrations.contains(user.getScreenName());
    }
    @Override
    public int unregisterUser(User user , String courseId, Map<String, String> properties) throws Exception {

        if (userExists(user) && isUserSubscribed(user, courseId)){
            return unSubscribe(user, courseId);
        }
        return 0;
    }

    private void addUser(User user, String callerId) throws IOException {
        String addUserPath = getBasePath() + "addUser/" + user.getScreenName();

        HashMap<String, String> headers = getHeader("application/x-www-form-urlencoded");
        HttpURLConnection connection = getConnection(addUserPath, "POST", headers);

        connection.setDoOutput(true);
        Map<String,String> params = new HashMap<>();
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("login", user.getEmailAddress());
        params.put("custom1", callerId);
        params.put("active", String.valueOf(true));
        writePostParameters(connection, params);

        checkResponse(connection);

    }

    private boolean userExists(User user) throws IOException, JSONException {

        String userExistsPath = getBasePath() + "userExists/" + user.getScreenName();
        HttpURLConnection connection = getConnection(userExistsPath, "GET", getHeader("application/json"));

        //get response
        checkResponse(connection);
        String jsonResponse = readAll(connection.getInputStream());

        Map<String, String> response = JsonContentUtils.parseJsonToMap(jsonResponse);
        String result = response.get("result");
        return Boolean.parseBoolean(result);
    }

    private boolean isUserSubscribed(User user, String courseId) throws IOException, JSONException {

        String isSubscribedPath = StringBundler.concat(getBasePath(), "isSubscribed/", user.getScreenName(), "/", courseId);
        HttpURLConnection connection = getConnection(isSubscribedPath, "GET", getHeader("application/json"));
        //get response
        checkResponse(connection);
        String jsonResponse = readAll(connection.getInputStream());

        Map<String, String> response = JsonContentUtils.parseJsonToMap(jsonResponse);
        String result = response.get("result");
        return Boolean.parseBoolean(result);

    }

    private HashMap<String, String> getHeader(String s) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", s);
        headers.put("X-API-Key", API_KEY);
        return headers;
    }

    @SuppressWarnings("unused")
    private int subscribe(User user, String courseId) throws IOException {

        String subscribePath = StringBundler.concat(getBasePath(), "subscribe/", user.getScreenName(), "/",  courseId);
        HttpURLConnection connection = getConnection(subscribePath, "POST", getHeader("application/x-www-form-urlencoded"));

        return checkResponse(connection);
    }

    private int unSubscribe(User user, String courseId) throws IOException {

        String subscribePath = StringBundler.concat(getBasePath(), "unsubscribe/", user.getScreenName(), "/", courseId);
        HttpURLConnection connection = getConnection(subscribePath, "POST", getHeader("application/x-www-form-urlencoded"));
        return checkResponse(connection);
    }

    private String getBasePath() {
        if (basePath != null) return basePath;
        basePath =  configuration.aNewSpringURL();
        if (basePath == null) return null;
        if(basePath.endsWith("/")){
            return basePath;
        }
        basePath += '/';
        return basePath;
    }

}
