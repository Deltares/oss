package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

public class DinnerRegistration extends Registration {

    public DinnerRegistration(JournalArticle article) throws PortalException {
        super(article);
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Dinner.name();
    }

    @Override
    public boolean storeInParentSite() {
        return false;
    }
}
