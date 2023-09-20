package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;
import org.w3c.dom.Document;

import java.util.Locale;

public class Subscription extends AbsDsdArticle {

    private String id;
    private String name;

    public Subscription(JournalArticle journalArticle, DsdParserUtils articleParserUtils, Locale locale) throws PortalException {
        super(journalArticle, articleParserUtils, locale);
        init();
    }

    public Subscription(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            id = getFormFieldValue("Id", false);
            name = getFormFieldValue("Name", false);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public String getStructureKey() {
        return "subscription";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
