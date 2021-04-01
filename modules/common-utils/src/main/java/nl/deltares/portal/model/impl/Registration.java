package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.Period;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Registration extends AbsDsdArticle {
    private static final Log LOG = LogFactoryUtil.getLog(Registration.class);
    private long eventId;
    private int capacity;
    private float price;
    private boolean open;
    private String currency = "&#8364"; //euro sign
    private String type = "unknown";
    private String topic = "unknown";
    private Registration parentRegistration = null;
    private boolean overlapWithParent = true;
    private boolean hasParent = true;
    Date startTime = new Date();
    Date endTime = new Date();
    final List<Period> dayPeriods = new ArrayList<>();
    boolean daily = true;
    private String timeZoneId = "CET";
    private float vat = 21;

    private final long dayMillis = TimeUnit.DAYS.toMillis(1);
    final SimpleDateFormat dayf = new SimpleDateFormat("yyyy-MM-dd");
    final SimpleDateFormat timef = new SimpleDateFormat("HH:mm");
    private final Calendar calendar = Calendar.getInstance();

    public Registration(JournalArticle article, DsdParserUtils dsdParserUtils) throws PortalException {
        super(article, dsdParserUtils);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String eventId = XmlContentUtils.getDynamicContentByName(document, "eventId", false);
            this.eventId =  Long.parseLong(eventId);
            String capacity = XmlContentUtils.getDynamicContentByName(document, "capacity", false);
            this.capacity =  Integer.parseInt(capacity);
            String price = XmlContentUtils.getDynamicContentByName(document, "price", false);
            this.price =  Float.parseFloat(price);
            String currency = XmlContentUtils.getDynamicContentByName(document, "currency", true);
            if (currency != null) this.currency = HtmlUtil.escape(currency);
            String open = XmlContentUtils.getDynamicContentByName(document, "open", true);
            this.open = Boolean.parseBoolean(open);
            String type = XmlContentUtils.getDynamicContentByName(document, "type", false);
            if (type != null) this.type = type;
            String topic = XmlContentUtils.getDynamicContentByName(document, "topic", false);
            if (topic != null) this.topic = topic;
            String parentJson = XmlContentUtils.getDynamicContentByName(document, "parent", true);
            if (parentJson != null) {
                String overlap = XmlContentUtils.getDynamicContentByName(document, "overlaps", true);
                overlapWithParent = Boolean.parseBoolean(overlap);
                hasParent = true;
            }
            timeZoneId = XmlContentUtils.getDynamicContentByName(document, "timeZone", true);
            String vatTxt = XmlContentUtils.getDynamicContentByName(document, "vat", true);
            if (vatTxt != null) this.vat = Long.parseLong(vatTxt);

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing Registration %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    void initDates(Document document) throws PortalException, ParseException {

        String datesOption = XmlContentUtils.getDynamicContentByName(document, "multipleDatesOption", true);
        daily = "daily".equals(datesOption);
        NodeList registrationDates = XmlContentUtils.getDynamicElementsByName(document, "registrationDate");
        ArrayList<Period> dayPeriods = new ArrayList<>();
        for (int i = 0; i < registrationDates.getLength(); i++) {
            Node registrationDate = registrationDates.item(i);
            Date startOfDay = XmlContentUtils.parseDateTimeFields(registrationDate, "startTime");
            Date endOfDay = XmlContentUtils.parseDateTimeFields(registrationDate, "endTime");
            dayPeriods.add(new Period(startOfDay, endOfDay));
        }

        if (daily && dayPeriods.size() == 2){
            this.dayPeriods.addAll(toDayPeriods(dayPeriods.get(0).getStartDate(), dayPeriods.get(1).getEndDate()));
        } else {
            this.dayPeriods.addAll(dayPeriods);
        }

        startTime = dayPeriods.get(0).getStartDate();
        endTime = dayPeriods.get(dayPeriods.size() - 1).getEndDate();

    }

    Collection<? extends Period> toDayPeriods(Date startTime, Date endTime) throws ParseException {

        String startDayString = dayf.format(startTime); // remove time
        String startTimeString = timef.format(startTime);
        String endTimeString = timef.format(endTime);
        long startTimeMillis = timef.parse(startTimeString).getTime();
        long endTimeMillis = timef.parse(endTimeString).getTime();
        Date day = dayf.parse(startDayString);
        ArrayList<Period> dayPeriods = new ArrayList<>();
        while (day.before(endTime)){
            if (isWeekDay(day)) {
                dayPeriods.add(new Period(day.getTime() + startTimeMillis, day.getTime() + endTimeMillis ));
            }
            day = new Date(day.getTime() + dayMillis);
        }
        return dayPeriods;
    }

    private boolean isWeekDay(Date day) {
        calendar.setTime(day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }

    @Override
    public void validate() throws PortalException {
        parseParentRegistration();
        super.validate();
    }

    private void parseParentRegistration() throws PortalException {
        String parentJson = XmlContentUtils.getDynamicContentByName(getDocument(), "parent", true);
        if (parentJson == null){
            return;
        }
        JournalArticle journalArticle = JsonContentUtils.jsonReferenceToJournalArticle(parentJson);
        parentRegistration = dsdParserUtils.getRegistration(journalArticle);

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

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public float getVAT(){
        return vat;
    }

    public String getCurrency() {
        return currency;
    }

    public Registration getParentRegistration() {
        loadParentRegistration();
        return parentRegistration;
    }

    private void loadParentRegistration() {
        if (!hasParent || parentRegistration != null) return;
        try {
            parseParentRegistration();
            hasParent = parentRegistration != null;
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing parent registration for registration %s: %s", getTitle(), e.getMessage()));
            hasParent = false;
        }
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
        return type;
    }

    public String getTopic() {
        return topic;
    }

    public long getEventId() {
        return eventId;
    }

    public boolean isEventInPast(){
        return System.currentTimeMillis() > startTime.getTime();
    }

    public boolean isMultiDayEvent(){
        long duration = endTime.getTime() - startTime.getTime();
        return duration > TimeUnit.DAYS.toMillis(1);
    }

    public boolean isDaily(){
        return daily;
    }

    public List<Period> getStartAndEndTimesPerDay(){
        return Collections.unmodifiableList(dayPeriods);
    }
}
