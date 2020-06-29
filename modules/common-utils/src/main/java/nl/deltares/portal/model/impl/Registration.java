package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Document;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public abstract class Registration extends AbsDsdArticle {

    private long eventId;
    private int capacity;
    private float price;
    private boolean open;
    private String currency = "&#8364"; //euro sign
    private DsdArticle.DSD_SESSION_KEYS type;
    private Registration parentRegistration;
    private boolean overlapWithParent;
    private Date startTime;
    private Date endTime;

    public Registration(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String eventId = XmlContentParserUtils.getDynamicContentByName(document, "eventId", false);
            this.eventId =  Long.parseLong(eventId);
            String capacity = XmlContentParserUtils.getDynamicContentByName(document, "capacity", false);
            this.capacity =  Integer.parseInt(capacity);
            String price = XmlContentParserUtils.getDynamicContentByName(document, "price", false);
            this.price =  Float.parseFloat(price);
            String currency = XmlContentParserUtils.getDynamicContentByName(document, "currency", true);
            if (currency != null) this.currency = StringEscapeUtils.escapeHtml4(currency);
            String open = XmlContentParserUtils.getDynamicContentByName(document, "open", true);
            this.open = Boolean.parseBoolean(open);
            String type = XmlContentParserUtils.getDynamicContentByName(document, "type", false);
            this.type = DsdArticle.DSD_SESSION_KEYS.valueOf(type);
            String parentJson = XmlContentParserUtils.getDynamicContentByName(document, "parent", true);
            if (parentJson != null) {
                parentRegistration = parseParentRegistration(parentJson);
                String overlap = XmlContentParserUtils.getDynamicContentByName(document, "overlaps", true);
                overlapWithParent = Boolean.parseBoolean(overlap);
            }
            startTime = XmlContentParserUtils.parseDateTimeFields(document,"start", "starttime", false);
            endTime = XmlContentParserUtils.parseDateTimeFields(document,"end", "endtime", false);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Registration parseParentRegistration(String parentJson) throws PortalException {
        if (parentJson == null){
            throw new NullPointerException("parentJson");
        }
        AbsDsdArticle dsdArticle = parseJsonReference(parentJson);
        if (dsdArticle instanceof Registration) return (Registration) dsdArticle;
        throw new PortalException("Unsupported parent registration type " + dsdArticle.getClass().getName());
    }


    AbsDsdArticle parseJsonReference(String jsonReference) throws PortalException {
        JournalArticle journalArticle = JsonContentParserUtils.jsonReferenceToJournalArticle(jsonReference);
        return AbsDsdArticle.getInstance(journalArticle);
    }

    public boolean isOpen() {
        return open;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceText(Locale locale){
        if (price == 0) return LanguageUtil.get(locale, "price.free");
        return String.valueOf(price);
    }

    public String getCurrency() {
        return currency;
    }

    public Registration getParentRegistration() {
        return parentRegistration;
    }

    public boolean isOverlapWithParent() {
        return overlapWithParent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getType() {
        return type.name();
    }

    public long getEventId() {
        return eventId;
    }

    public boolean isEventInPast(){
        return System.currentTimeMillis() > startTime.getTime();
    }

    public boolean isMultiDayEvent(){
        long duration = endTime.getTime() - startTime.getTime();
        return TimeUnit.MILLISECONDS.toHours(duration) > TimeUnit.DAYS.toMillis(1);
    }

}
