package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.impl.JournalArticleServiceUtilsImpl;

import java.util.*;

public class JsonContentParserUtils {

    /** Turned into variable to allow replacement in unit tests **/
    private static JournalArticleServiceUtils serviceUtils = new JournalArticleServiceUtilsImpl();

    public static void setServiceUtils(JournalArticleServiceUtils serviceUtils){
        JsonContentParserUtils.serviceUtils = serviceUtils;
    }

    public static JSONObject parseContent(String jsonContent) throws JSONException{
        return JSONFactoryUtil.createJSONObject(jsonContent);
    }

    public static JSONArray parseContentArray(String jsonContent) throws JSONException{
        return JSONFactoryUtil.createJSONArray(jsonContent);
    }
    /**
     * Find the corresponding JournalArticle for the json reference object
     * @param jsonReference JSON reference extracted form JournalArticle document
     * @return JournalArticle instance of the reference object
     * @throws PortalException when JSON object is not valid.
     */
    public static JournalArticle jsonReferenceToJournalArticle(String jsonReference) throws PortalException {

        JSONObject roomObject = parseContent(jsonReference);
        long classPK = roomObject.getLong("classPK");
        return serviceUtils.getLatestArticle(classPK);

    }

    public static Location parseLocationJson(String json) throws PortalException {

        JournalArticle journalArticle = jsonReferenceToJournalArticle(json);
        AbsDsdArticle instance = AbsDsdArticle.getInstance(journalArticle);
        if ( ! (instance instanceof Location)){
            throw new PortalException(String.format("Article %s not instance of Location", journalArticle.getTitle()));
        }

        return (Location) instance;
    }

    public static Registration parseRegistrationJson(String json) throws PortalException {

        JournalArticle journalArticle = jsonReferenceToJournalArticle(json);
        AbsDsdArticle instance = AbsDsdArticle.getInstance(journalArticle);
        if ( ! (instance instanceof Registration)){
            throw new PortalException(String.format("Article %s not instance of Registration", journalArticle.getTitle()));
        }

        return (Registration) instance;
    }

    public static Building parseBuildingJson(String json) throws PortalException {

        JournalArticle journalArticle = jsonReferenceToJournalArticle(json);
        AbsDsdArticle instance = AbsDsdArticle.getInstance(journalArticle);
        if ( ! (instance instanceof Building)){
            throw new PortalException(String.format("Article %s not instance of Building", journalArticle.getTitle()));
        }

        return (Building) instance;
    }

    public static Room parseRoomJson(String json) throws PortalException {

        JournalArticle journalArticle = jsonReferenceToJournalArticle(json);
        AbsDsdArticle instance = AbsDsdArticle.getInstance(journalArticle);
        if ( ! (instance instanceof Room)){
            throw new PortalException(String.format("Article %s not instance of Room", journalArticle.getTitle()));
        }

        return (Room) instance;
    }

    public static List<Map<String, String>> parseJsonArrayToMap(String jsonContent) throws JSONException {
        ArrayList<Map<String, String>> mapList = new ArrayList<>();
        if (jsonContent == null) {
            return mapList;
        }
        if (jsonContent.startsWith("[")){
            JSONArray jsonArray = JsonContentParserUtils.parseContentArray(jsonContent);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Iterator<String> keys = jsonObject.keys();
                HashMap<String, String> map = new HashMap<>();
                keys.forEachRemaining(key -> map.put(key, jsonObject.getString(key)));
                mapList.add(map);
            }
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

    public static String formatMapToJson(Map<String, String> colorMap) {
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
        for (String key : colorMap.keySet()) {
            jsonObject.put(key, colorMap.get(key));
        }
        return jsonObject.toJSONString();
    }

    public static String formatTextToJson(String field, String message) {
        return String.format("{ \"%s\" : \"%s\" }", field, message);
    }

    /**
     * Remove leading and trailing  json array chars [ and ]
     * @param jsonArray
     * @return
     */
    public static String parseJsonArrayToValue(String jsonArray) throws JSONException {
        JSONArray values = JSONFactoryUtil.createJSONArray(jsonArray);
        if (values.length() == 0) return null;
        return values.getString(0);
    }
}
