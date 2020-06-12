package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Location;
import nl.deltares.portal.model.impl.Registration;

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
}
