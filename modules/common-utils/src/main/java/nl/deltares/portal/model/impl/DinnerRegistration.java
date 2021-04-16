package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.Locale;

public class DinnerRegistration extends Registration {

    private static final Log LOG = LogFactoryUtil.getLog(DinnerRegistration.class);
    private Location restaurant;

    public DinnerRegistration(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, locale);
        init();
    }

    private void init() throws PortalException {

        try {
            Document document = getDocument();
            initDates(document);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public void validate() throws PortalException {
        parserRestaurant();
        super.validate();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Dinner.name();
    }

    public Location getRestaurant() {
        if (restaurant == null){
            try {
                parserRestaurant();
            } catch (PortalException e) {
                LOG.error(String.format("Error parsing restaurant for dinner %s: %s", getTitle(), e.getMessage()));
            }
        }
        return restaurant;
    }

    private void parserRestaurant() throws PortalException {
        String json = XmlContentUtils.getDynamicContentByName(getDocument(), "restaurant", false);
        JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(json);
        AbsDsdArticle location = dsdParserUtils.toDsdArticle(article, getLocale());
        if (!(location instanceof Location)) throw new PortalException(String.format("Article %s not instance of Location", article.getTitle()));
        restaurant = (Location) location;
    }

}
