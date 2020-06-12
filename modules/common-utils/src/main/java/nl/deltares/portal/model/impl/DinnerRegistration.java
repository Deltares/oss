package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public class DinnerRegistration extends Registration {

    private Location restaurant;

    public DinnerRegistration(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {
        Document document = getDocument();
        Object json = XmlContentParserUtils.getNodeValue(document, "restaurant", false);
        restaurant = JsonContentParserUtils.parseLocationJson((String) json);
    }


    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Dinner.name();
    }

    public Location getRestaurant() {
        return restaurant;
    }
}
