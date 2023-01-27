package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LicenseFile extends AbsDsdArticle {

    private String content;
    private String name;
    private File generatedLicenseFile = null;
    private String dateFormat;
    private long expirationTimeInMillis;

    public LicenseFile(JournalArticle journalArticle, DsdParserUtils articleParserUtils, Locale locale) throws PortalException {
        super(journalArticle, articleParserUtils, locale);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            content = XmlContentUtils.getDynamicContentByName(document, "TemplateContent", false);
            name = XmlContentUtils.getDynamicContentByName(document, "Name", false);
            String expirationTimeInDays = XmlContentUtils.getDynamicContentByName(document, "ExpirationDays", false);
            expirationTimeInMillis = TimeUnit.DAYS.toMillis(Long.parseLong(expirationTimeInDays));
            dateFormat = XmlContentUtils.getDynamicContentByName(document, "DateFormat", false);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public String getStructureKey() {
        return "license";
    }

    public String getTemplateContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public File getGeneratedFile() {
        return generatedLicenseFile;
    }

    public void setGeneratedFile(File generatedLicenseFile) {
        this.generatedLicenseFile = generatedLicenseFile;
    }

    public long getExpirationPeriodInMillis() {
        return expirationTimeInMillis;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
