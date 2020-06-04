package nl.deltares.dsd.registration.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

public class SessionRegistration extends AbstractRegistration {
    public SessionRegistration(JournalArticle article) throws PortalException {
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
