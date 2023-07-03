package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.DateUtil;
import nl.deltares.portal.utils.DsdParserUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LicenseFile extends AbsDsdArticle {

    private String content;
    private String name;
    private String dateFormat;
    private Date expirationDate = null;
    private long expirationTimeInMillis = 0;

    public LicenseFile(JournalArticle journalArticle, DsdParserUtils articleParserUtils, Locale locale) throws PortalException {
        super(journalArticle, articleParserUtils, locale);
        init();
    }

    private void init() throws PortalException {
        try {
            content = getFormFieldValue( "TemplateContent", false);
            name = getFormFieldValue("Name", false);
            dateFormat = getFormFieldValue( "DateFormat", false);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
        String expirationTimeInDays = getFormFieldValue("ExpirationDays", true);
        if (expirationTimeInDays != null && !expirationTimeInDays.isEmpty()){
            expirationTimeInMillis = TimeUnit.DAYS.toMillis(Long.parseLong(expirationTimeInDays));
        }
        String expirationDate = getFormFieldValue("ExpirationDate", true);
        if (expirationDate != null && !expirationDate.isEmpty()){
            try {
                this.expirationDate = DateUtil.parseDate("yyyy-MM-dd", expirationDate, getLocale());
            } catch (ParseException e) {
                throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), String.format("Invalid ExpirationDate %s: %s", expirationDate, e.getMessage())));
            }
        }
        if (expirationTimeInMillis == 0 && expirationDate == null){
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), "Expecting either ExpirationDays or ExpirationDate"));
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

    public Date getExpirationDate(){
        if (expirationDate != null) return expirationDate;
        return new Date(System.currentTimeMillis() + expirationTimeInMillis);
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
