package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;

import java.util.Locale;

public class GenericArticle extends AbsDsdArticle {

    private final String structureKey;

    public GenericArticle(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, locale);
        this.structureKey = DsdParserUtils.parseStructureKey(article);
    }

    @Override
    public String getStructureKey() {
        return structureKey;
    }

}
