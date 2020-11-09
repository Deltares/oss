package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.WebinarUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;

public class ANewSpringUtils extends HttpClientUtils implements WebinarUtils {
    private static final Log LOG = LogFactoryUtil.getLog(ANewSpringUtils.class);

    private static final String API_KEY = "anewspring.apikey";
    private final String BASEURL_KEY =  "anewspring.baseurl";
    private String basePath;
    private String apiKey;


    @Override
    public boolean isActive() {
        return PropsUtil.contains(BASEURL_KEY);
    }

    @Override
    public int registerUser(User user, String webinarKey, String callerId, Map<String, String> properties) throws Exception {

        if (!userExists(user)){
            addUser(user, callerId);
        }
//        DO not subscribe user as this allocates a license.
//        if (!isUserSubscribed(user, webinarKey)){
//            return subscribe(user, webinarKey);
//        }
        return 0;
    }

    @Override
    public Map<String, String> getRegistration(User user, String courseId) throws Exception {

        String isSubscribedPath = StringBundler.concat(getBasePath(), "getSubscriptions/", user.getScreenName());
        HttpURLConnection connection = getConnection(isSubscribedPath, "GET", getHeader("application/json"));
        //get response
        checkResponse(connection);
        String jsonResponse = readAll(connection.getInputStream());

        JSONArray courses = (JSONArray) JsonContentUtils.parseContent(jsonResponse).get("courses");
        for (int i = 0; i < courses.length(); i++) {
            JSONObject course = courses.getJSONObject(i);
            if (course.get("id").equals(courseId)) {
                Iterator<String> keys = course.keys();
                HashMap<String, String> map = new HashMap<>();
                keys.forEachRemaining(key -> map.put(key, course.getString(key)));
                return map;
            }
        }
        return Collections.emptyMap();
    }

    @Override
    public int unregisterUser(User user , String webinarKey, Map<String, String> properties) throws Exception {

        if (userExists(user) && isUserSubscribed(user, webinarKey)){
            return unSubscribe(user, webinarKey);
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
        headers.put("X-API-Key", getApiKey());
        return headers;
    }

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

    protected String getBasePath() {
        if (basePath != null) return basePath;
        basePath =  getProperty(BASEURL_KEY);
        if (basePath == null) return null;
        if(basePath.endsWith("/")){
            return basePath;
        }
        basePath += '/';
        return basePath;
    }

    private String getApiKey(){
        if ( apiKey != null) return apiKey;
        apiKey = getProperty(API_KEY);
        return apiKey;
    }

    public static String getProperty(String propertyKey) {
        if (!PropsUtil.contains(propertyKey)) {
            LOG.warn(String.format("Missing property %s in portal-ext.properties file", propertyKey));
            return null;
        }
        return PropsUtil.get(propertyKey);
    }
}
