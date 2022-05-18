package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.Locale;

public class Terms extends AbsDsdArticle {

    private String name;
    private String termsURL;
    public Terms(JournalArticle journalArticle, DsdParserUtils articleParserUtils, Locale locale) throws PortalException {
        super(journalArticle, articleParserUtils, locale);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            termsURL = XmlContentUtils.getDynamicContentByName(document, "TermsURL", false);
            name = XmlContentUtils.getDynamicContentByName(document, "Name", false);
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
