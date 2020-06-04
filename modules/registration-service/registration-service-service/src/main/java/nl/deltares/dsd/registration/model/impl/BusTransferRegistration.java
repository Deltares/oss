package nl.deltares.dsd.registration.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

public class BusTransferRegistration extends AbstractRegistration {
    public BusTransferRegistration(JournalArticle article) throws PortalException {
        super(article);
    }

    @Override
    public boolean hasParentRegistration() {
        return false;
    }

    @Override
    public long getParentRegistrationId() {
        return 0;
    }
}
