package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DinnerRegistration extends Registration {

    private static final Log LOG = LogFactoryUtil.getLog(DinnerRegistration.class);
    private Location restaurant;

    public DinnerRegistration(JournalArticle article, DsdParserUtils dsdParserUtils) throws PortalException {
        super(article, dsdParserUtils);
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
        AbsDsdArticle location = dsdParserUtils.toDsdArticle(article);
        if (!(location instanceof Location)) throw new PortalException(String.format("Article %s not instance of Location", article.getTitle()));
        restaurant = (Location) location;
    }

    @Override
    public Date getEndTime() {
        return new Date(super.getStartTime().getTime() + TimeUnit.HOURS.toMillis(3));
    }
}
