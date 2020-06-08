package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public class Location extends AbsDsdArticle {
    private boolean storeInParentSite;

    public Location(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            Object storeInParentSite = XmlContentParserUtils.getNodeValue(document, "storeInParentSite", true);
            this.storeInParentSite = storeInParentSite != null && (boolean) storeInParentSite;
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Location.name();
    }

    @Override
    public boolean storeInParentSite() {
        return storeInParentSite;
    }
}
