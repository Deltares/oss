package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public abstract class Registration extends AbsDsdArticle {

    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    private int capacity;
    private float price;
    private boolean open;
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
            String capacity = XmlContentParserUtils.getDynamicContentByName(document, "capacity", false);
            this.capacity =  Integer.parseInt(capacity);
            String price = XmlContentParserUtils.getDynamicContentByName(document, "price", false);
            this.price =  Float.parseFloat(price);
            String open = XmlContentParserUtils.getDynamicContentByName(document, "open", false);
            this.open = Boolean.parseBoolean(open);
            String parentJson = XmlContentParserUtils.getDynamicContentByName(document, "parent", true);
            if (parentJson != null) {
                parentRegistration = parseParentRegistration(parentJson);
                String overlap = XmlContentParserUtils.getDynamicContentByName(document, "overlaps", true);
                overlapWithParent = Boolean.parseBoolean(overlap);
            }
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
            Node dateNode = XmlContentParserUtils.getDynamicElementByName(document, date, false);
            String dateValue = XmlContentParserUtils.getDynamicContentForNode(dateNode);
            String timeValue = XmlContentParserUtils.getDynamicContentByName(dateNode, time, false);
            return dateTimeFormatter.parse(dateValue + 'T' + timeValue);
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

    public Registration getParentRegistration() {
        return parentRegistration;
    }

    public boolean isOverlapWithParent() {
        return overlapWithParent;
    }

    public void setOverlapWithParent(boolean overlapWithParent) {
        this.overlapWithParent = overlapWithParent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
