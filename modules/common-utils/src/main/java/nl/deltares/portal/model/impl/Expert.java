package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

public class Expert extends AbsDsdArticle {
    private boolean storeInParentSite;

    public Expert(JournalArticle dsdArticle) throws PortalException {
        super(dsdArticle);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String storeInParentSite = XmlContentParserUtils.getDynamicContentByName(document, "storeInParentSite", true);
            this.storeInParentSite = Boolean.parseBoolean(storeInParentSite);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }
    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Expert.name();
    }

    @Override
    public boolean storeInParentSite() {
        return storeInParentSite;
    }
}
