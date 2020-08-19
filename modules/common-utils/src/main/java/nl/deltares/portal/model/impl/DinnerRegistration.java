package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DinnerRegistration extends Registration {

    private static final Log LOG = LogFactoryUtil.getLog(DinnerRegistration.class);
    private Location restaurant;

    public DinnerRegistration(JournalArticle article) throws PortalException {
        super(article);
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
        String json = XmlContentParserUtils.getDynamicContentByName(getDocument(), "restaurant", false);
        restaurant = JsonContentParserUtils.parseLocationJson(json);
    }

    @Override
    public Date getEndTime() {
        return new Date(super.getStartTime().getTime() + TimeUnit.HOURS.toMillis(3));
    }
}
