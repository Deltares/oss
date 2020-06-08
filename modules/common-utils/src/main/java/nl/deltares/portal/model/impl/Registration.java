package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public abstract class Registration extends AbsDsdArticle {

    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    private int capacity;
    private double price;
    private boolean open;
    private Registration parentRegistration;
    private Date startTime;
    private Date endTime;

    public Registration(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            Object capacity = XmlContentParserUtils.getNodeValue(document, "capacity", false);
            this.capacity =  capacity == null ? Integer.MAX_VALUE : (int) capacity;
            Object price = XmlContentParserUtils.getNodeValue(document, "price", false);
            this.price =  capacity == null ? 0 : (double) price;
            Object open = XmlContentParserUtils.getNodeValue(document, "open", false);
            this.open = open == null || (boolean) open;
            Object parentJson = XmlContentParserUtils.getNodeValue(document, "parent", true);
            parentRegistration = parentJson == null ? null : parseParentRegistration((String) parentJson);
            dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            startTime = parseDateTime("start", "starttime");
            endTime = parseDateTime("end", "endtime");
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Date parseDateTime(String date, String time) throws PortalException {
        try {
            Document document = getDocument();
            String dateValue = (String) XmlContentParserUtils.getNodeValue(document, date, false);
            String timeValue = (String) XmlContentParserUtils.getNodeValue(document, time, false);
            return dateTimeFormatter.parse(dateValue + 'T' + timeValue);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Registration parseParentRegistration(String parentJson) throws PortalException {
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


    public Registration getParentRegistration() {
        return parentRegistration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
