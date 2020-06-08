package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

public class EventLocation extends Location {
    public EventLocation(JournalArticle article) throws PortalException {
        super(article);
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Eventlocation.name();
    }
}
