package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;

public class DinnerRegistration extends Registration {

    private Location restaurant;

    public DinnerRegistration(JournalArticle article) throws PortalException {
        super(article);
    }


    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Dinner.name();
    }

    public Location getRestaurant() throws PortalException {
        if (restaurant == null){
            String json = XmlContentParserUtils.getDynamicContentByName(getDocument(), "restaurant", false);
            restaurant = JsonContentParserUtils.parseLocationJson(json);
        }
        return restaurant;
    }
}
