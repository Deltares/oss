package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

public class JsonContentParserUtils {

    public static JSONObject parseContent(String jsonContent) throws JSONException{
        return JSONFactoryUtil.createJSONObject(jsonContent);
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
        return JournalArticleLocalServiceUtil.getLatestArticle(classPK);

    }

}
