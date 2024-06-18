package nl.deltares.portal.utils;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.model.impl.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface DsdParserUtils {

    String STRUCTURE_KEY_REGEX = "([A-Z])+-(\\d+\\.)(\\d+\\.)(\\d+)";

    Event getEvent(long siteId, String eventId, Locale locale) throws PortalException;

    List<String> getRelatedArticles(long siteId, String articleId) throws PortalException;

    @Deprecated
    Event getEvent(long siteId, String eventId) throws PortalException;

    Registration getRegistration(long siteId, String articleId) throws PortalException;

    List<Registration> getRegistrations(long companyId, long siteId, Date startTime, Date endTime,
                                        String[] structureKeys, String dateFieldName, Locale locale) throws PortalException;

    List<Registration> getRegistrations(long companyId, long siteId, String eventId, String[] structureKeys, Locale locale) throws PortalException;

    Registration getRegistration(JournalArticle article) throws PortalException;

    Location getLocation(JournalArticle article) throws PortalException;

    Expert getExpert(JournalArticle article) throws PortalException;

    RegistrationDisplayContext getDisplayContextInstance(String articleId, ThemeDisplay themeDisplay);

    AbsDsdArticle toDsdArticle(long siteId, String articleId) throws PortalException;

    AbsDsdArticle toDsdArticle(JournalArticle article, Locale locale) throws PortalException;

    AbsDsdArticle toDsdArticle(JournalArticle article) throws PortalException;

    static String parseStructureKey(JournalArticle dsdArticle) {
        final DDMStructure ddmStructure = dsdArticle.getDDMStructure();
        if (ddmStructure == null) return  null;

        String structureKey = ddmStructure.getStructureKey();
        if (structureKey.matches(STRUCTURE_KEY_REGEX)) {
            structureKey = structureKey.substring(0, 1).toUpperCase()
                    + structureKey.substring(1, structureKey.lastIndexOf("-")).toLowerCase();
        }
        return structureKey;
    }

}
