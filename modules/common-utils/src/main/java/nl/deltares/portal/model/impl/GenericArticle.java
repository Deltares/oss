package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

public class GenericArticle extends AbsDsdArticle {

    private final String structureKey;

    GenericArticle(JournalArticle article, String structureKey) throws PortalException {
        super(article);
        this.structureKey = structureKey;
    }

    @Override
    public String getStructureKey() {
        return structureKey;
    }

}
