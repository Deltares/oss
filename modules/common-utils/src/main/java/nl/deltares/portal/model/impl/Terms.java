package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;

import java.util.Locale;

public class Terms extends AbsDsdArticle {

    private String name;
    private String termsURL;

    public Terms(JournalArticle journalArticle, DsdParserUtils articleParserUtils, DsdJournalArticleUtils dsdJournalArticleUtils, Locale locale) throws PortalException {
        super(journalArticle, articleParserUtils, dsdJournalArticleUtils, locale);
        init();
    }

    @Override
    void init() throws PortalException {

        super.init();

        try {
            termsURL = getFormFieldValue("TermsURL", false);
            name = getFormFieldValue("Name", false);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public String getStructureKey() {
        return "terms";
    }

    public String getName() {
        return name;
    }

    public String getTermsURL() {
        return termsURL;
    }
}
