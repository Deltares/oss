package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.display.context.RegistrationDisplayContext;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Location;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

@Component(
        immediate = true,
        service = DsdParserUtils.class
)
public class DsdParserUtilsImpl implements DsdParserUtils {

    private static final Log LOG = LogFactoryUtil.getLog(DsdParserUtilsImpl.class);

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;

    @Override
    public Event getEvent(long siteId, String articleId) throws PortalException {
        if ("0".equals(articleId)) {
            return null; //show all events
        }
        JournalArticle eventResource = dsdJournalArticleUtils.getJournalArticle(siteId, articleId);
        AbsDsdArticle eventArticle = AbsDsdArticle.getInstance(eventResource);
        if (!(eventArticle instanceof Event)) {
            throw new PortalException(String.format("EventId %s is not the articleId of a valid DSD Event", articleId));
        }
        return (Event) eventArticle;
    }

    @Override
    public Registration getRegistration(long siteId, String articleId) throws PortalException {
        JournalArticle article =  dsdJournalArticleUtils.getJournalArticle(siteId, articleId);
        return getRegistration(article);
    }

    @Override
    public Registration getRegistration(JournalArticle article) throws PortalException {
        AbsDsdArticle dsdArticle = AbsDsdArticle.getInstance(article);
        if (!(dsdArticle instanceof Registration)) {
            throw new PortalException(String.format("Id %s is not the articleId of a valid DSD Registration", article.getTitle()));
        }
        return (Registration) dsdArticle;
    }

    @Override
    public Location getLocation(JournalArticle article) throws PortalException {
        AbsDsdArticle dsdArticle = AbsDsdArticle.getInstance(article);
        if (!(dsdArticle instanceof Location)) {
            throw new PortalException(String.format("Id %s is not the articleId of a valid DSD Location", article.getTitle()));
        }
        return (Location) dsdArticle;
    }

    @Override
    public Map<String, String> parseSessionColorConfig(String json) {
        Map<String, String> colorMap;
        try {
            colorMap = JsonContentUtils.parseJsonToMap(json);
        } catch (JSONException e) {
            LOG.error(String.format("Error parsing session color config '%s' :  %s", json, e.getMessage()));
            colorMap = new HashMap<>();
        }
        for (DsdArticle.DSD_REGISTRATION_KEYS session_keys : DsdArticle.DSD_REGISTRATION_KEYS.values()) {
            String sessionKey = session_keys.name();
            colorMap.putIfAbsent(sessionKey, "#17a2b8");
        }
        return colorMap;
    }

    @Override
    public RegistrationDisplayContext getDisplayContextInstance(String articleId, ThemeDisplay themeDisplay) {
        return new RegistrationDisplayContext(articleId, themeDisplay, _configurationProvider);
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

}
