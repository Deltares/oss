package nl.deltares.portal.utils;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import nl.deltares.portal.utils.impl.DsdJournalArticleUtilsImpl;

import java.util.*;

public class JsonContentUtils {

    /**
     * Turned into variable to allow replacement in unit tests
     **/
    private static final DsdJournalArticleUtils serviceUtils = new DsdJournalArticleUtilsImpl();

    public static JSONObject parseContent(String jsonContent) throws JSONException {
        if (jsonContent == null) return JSONFactoryUtil.createJSONObject();
        return JSONFactoryUtil.createJSONObject(jsonContent);
    }

    public static JSONArray parseContentArray(String jsonContent) throws JSONException {
        if (jsonContent == null) return JSONFactoryUtil.createJSONArray();
        return JSONFactoryUtil.createJSONArray(jsonContent);
    }

    /**
     * Find the corresponding JournalArticle for the json reference object
     *
     * @param jsonReference JSON reference extracted form JournalArticle document
     * @return JournalArticle instance of the reference object
     * @throws PortalException when JSON object is not valid.
     */
    public static JournalArticle jsonReferenceToJournalArticle(String jsonReference) throws PortalException {
        JSONObject roomObject = parseContent(jsonReference);
        long classPK = roomObject.getLong("classPK");
        return serviceUtils.getLatestArticle(classPK);

    }

    public static List<Map<String, String>> parseJsonArrayToMap(String jsonContent) throws JSONException {
        ArrayList<Map<String, String>> mapList = new ArrayList<>();
        if (jsonContent == null) {
            return mapList;
        }
        if (jsonContent.startsWith("[")) {
            JSONArray jsonArray = JsonContentUtils.parseContentArray(jsonContent);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Iterator<String> keys = jsonObject.keys();
                HashMap<String, String> map = new HashMap<>();
                keys.forEachRemaining(key -> map.put(key, jsonObject.getString(key)));
                mapList.add(map);
            }
        } else {
            mapList.add(parseJsonToMap(jsonContent));
        }
        return mapList;
    }

    public static Map<String, String> parseJsonToMap(String jsonContent) throws JSONException {
        if (jsonContent == null) return new HashMap<>();
        JSONObject jsonObject = parseContent(jsonContent);
        Iterator<String> keys = jsonObject.keys();
        HashMap<String, String> map = new HashMap<>();
        keys.forEachRemaining(key -> map.put(key, jsonObject.getString(key)));
        return map;
    }

    public static String formatMapToJson(Map<String, String> properties) {
        boolean empty = true;
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
        for (String key : properties.keySet()) {
            empty = false;
            jsonObject.put(key, properties.get(key));
        }
        if (empty) return null;
        return jsonObject.toJSONString();
    }

    /**
     * Remove leading and trailing  json array chars [ and ]
     */
    public static String parseJsonArrayToValue(String jsonArray) throws JSONException {
        if (!jsonArray.startsWith("[")) return jsonArray;
        JSONArray values = JSONFactoryUtil.createJSONArray(jsonArray);
        if (values.length() == 0) return null;
        StringBuilder arrayValue = new StringBuilder();
        for (int i = 0; i < values.length(); i++) {
            if (i > 0) arrayValue.append(',');
            arrayValue.append(values.getString(i));
        }
        return arrayValue.toString();
    }

    public static String parseImageJson(String jsonData) {
        try {
            JSONObject jsonObject = JsonContentUtils.parseContent(jsonData);
            if (jsonObject.isNull("fileEntryId")) return "";
            return parseImageJson(jsonObject.getLong("fileEntryId"));
        } catch (Exception e) {
            return "";
        }
    }

    public static String parseImageJson(long fileEntryId){
        try {
            FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
            if (fileEntry == null) return "";
            return DLURLHelperUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), null, "", false, true);
        } catch (Exception e) {
            return "";
        }
    }

    public static String parseDocumentJson(String jsonData) {
        try {
            JSONObject jsonObject = JsonContentUtils.parseContent(jsonData);
            if (jsonObject.isNull("classPK")) return "";
            FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(jsonObject.getLong("classPK"));
            if (fileEntry == null) return null;
            return DLURLHelperUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), null, "", false, true);
        } catch (Exception e) {
            return "";
        }
    }

    public static Map<String, String> parseSessionColorConfig(String json) {
        Map<String, String> colorMap;
        try {
            colorMap = JsonContentUtils.parseJsonToMap(json);
        } catch (JSONException e) {
            colorMap = new HashMap<>();
        }
        return colorMap;
    }

    public static boolean isEmpty(String response) {
        if (response == null || response.isEmpty()) return true;

        try {
            if (response.startsWith("[")){
                final JSONArray jsonArray = JSONFactoryUtil.createJSONArray(response);
                if (jsonArray.length() == 0) return true;
                for (int i = 0; i < jsonArray.length(); i++) {
                     if (jsonArray.getJSONObject(i).keys().hasNext()) return false;
                }
                return true;
            } else if (response.startsWith("{")){
                final JSONObject jsonObject = JsonContentUtils.parseContent(response);
                return !jsonObject.keys().hasNext();
            }
        } catch (JSONException ignored) {}
        return true;
    }

}
