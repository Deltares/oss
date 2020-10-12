package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Location;
import nl.deltares.portal.model.impl.Registration;

import java.util.Map;

public interface DsdParserUtils {

    /**
     * Parse color configuration to map.
     * @param json Json configuration containing sessionid to color mapping
     * @return Map with sessionId as key and color hashcode as value
     */
    Map<String, String> parseSessionColorConfig(String json);

    Event getEvent(long siteId, String eventId) throws PortalException;

    Registration getRegistration(long siteId, String articleId) throws PortalException;

    Registration getRegistration(JournalArticle article) throws PortalException;

    Location getLocation(JournalArticle article) throws PortalException;

    RegistrationDisplayContext getDisplayContextInstance(String articleId, ThemeDisplay themeDisplay);
}
