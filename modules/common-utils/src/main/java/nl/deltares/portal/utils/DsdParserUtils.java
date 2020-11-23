package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface DsdParserUtils {

    Event getEvent(long siteId, String eventId) throws PortalException;

    Registration getRegistration(long siteId, String articleId) throws PortalException;

    List<Registration> getRegistrations(long siteId, Date startTime, Date endTime, Locale locale) throws PortalException;

    List<Registration> getRegistrations(long siteId, String eventId, Locale locale) throws PortalException;

    Registration getRegistration(JournalArticle article) throws PortalException;

    Location getLocation(JournalArticle article) throws PortalException;

    Expert getExpert(JournalArticle article) throws PortalException;

    RegistrationDisplayContext getDisplayContextInstance(String articleId, ThemeDisplay themeDisplay);

    AbsDsdArticle toDsdArticle(JournalArticle article) throws PortalException;

    static String parseStructureKey(JournalArticle dsdArticle) {
        String structureKey = dsdArticle.getDDMStructureKey();

        if (structureKey.matches("([A-Z])+-(\\d+\\.)(\\d+\\.)(\\d+)")) {
            structureKey = structureKey.substring(0, 1).toUpperCase()
                    + structureKey.substring(1, structureKey.lastIndexOf("-")).toLowerCase();
        }
        return structureKey;
    }

    static boolean isDsdArticle(JournalArticle article) {
        try {
            getDsdStructureKey(parseStructureKey(article));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static DsdArticle.DSD_STRUCTURE_KEYS getDsdStructureKey(String structureKey) {
        DsdArticle.DSD_STRUCTURE_KEYS dsd_structure_key;
        try {
            dsd_structure_key = DsdArticle.DSD_STRUCTURE_KEYS.valueOf(structureKey);
        } catch (IllegalArgumentException e) {
            return DsdArticle.DSD_STRUCTURE_KEYS.Generic;
        }
        return dsd_structure_key;
    }
}
