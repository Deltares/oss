package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.DsdParserUtils;

public class GenericArticle extends AbsDsdArticle {

    private final String structureKey;

    public GenericArticle(JournalArticle article, DsdParserUtils dsdParserUtils) throws PortalException {
        super(article, dsdParserUtils);
        this.structureKey = DsdParserUtils.parseStructureKey(article);
    }

    @Override
    public String getStructureKey() {
        return structureKey;
    }

}
