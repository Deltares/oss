package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public class Room extends AbsDsdArticle {
    private int capacity;
    private boolean storeInParentSite;

    public Room(JournalArticle dsdArticle) throws PortalException {
        super(dsdArticle);
        init();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Room.name();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String capacity = XmlContentParserUtils.getDynamicContentByName(document, "capacity", false);
            this.capacity = Integer.parseInt(capacity);
            String storeInParentSite = XmlContentParserUtils.getDynamicContentByName(document, "storeInParentSite", true);
            this.storeInParentSite = Boolean.parseBoolean(storeInParentSite);

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean storeInParentSite() {
        return storeInParentSite;
    }

}
