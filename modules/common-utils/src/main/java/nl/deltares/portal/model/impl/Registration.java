package nl.deltares.portal.model.impl;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Registration extends AbsDsdArticle {
    private static final Log LOG = LogFactoryUtil.getLog(Registration.class);
    private long defaultUserId = -1;
    private long eventId;
    private int capacity;
    private float price;
    private boolean open;
    private boolean hidden;
    private String projectNumber = null;
    private String requiredTeam;
    private String currency = "&#8364"; //euro sign
    private String type = "unknown";
    private String topic = "unknown";
    private final List<Registration> relatedRegistrations = new ArrayList<>();
    private boolean overlapWithParent = false;
    private boolean hasParent = false;
    Date startTime = new Date(0);
    Date endTime = new Date(0);
    final List<Period> dayPeriods = new ArrayList<>();
    boolean daily = true;
    boolean toBeDetermined = false;
    private String timeZoneId = "CET";

    private long cancellationPeriodMillis = 0;
    private float vat = 21;

    private final long dayMillis = TimeUnit.DAYS.toMillis(1);
    final SimpleDateFormat dayf = new SimpleDateFormat("yyyy-MM-dd");
    final SimpleDateFormat timef = new SimpleDateFormat("HH:mm");
    private final Calendar calendar = Calendar.getInstance();

    public Registration(JournalArticle article, DsdParserUtils dsdParserUtils, DsdJournalArticleUtils dsdJournalArticleUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, dsdJournalArticleUtils, locale);
    }

    @Override
    void init() throws PortalException {

        super.init();

        try {
            this.eventId =  Long.parseLong(getFormFieldValue("eventId", false));
            this.capacity =  Integer.parseInt(getFormFieldValue("capacity", false));
            this.price =  getFormFieldFloatValue("price", false);
            String currency = getFormFieldValue("currency", true);
            if (currency != null) this.currency = HtmlUtil.escape(currency);
            this.open = Boolean.parseBoolean(getFormFieldValue("open", true));
            this.hidden = Boolean.parseBoolean(getFormFieldValue("hidden", true));
            this.type = getFormFieldValue("registrationType", false);
            this.topic = getFormFieldValue("topic", false);
            String parentJson = getFormFieldValue("parent", true);
            String relationType = getFormFieldValue("relationType", true);
            if (parentJson != null) {
                overlapWithParent = Boolean.parseBoolean(getFormFieldValue( "overlaps", true));
                hasParent = true;
            } else if (relationType != null) {
                overlapWithParent = true;
                hasParent = relationType.equals("child");
            }
            requiredTeam = getFormFieldValue( "requiredTeam", true);
            projectNumber = getFormFieldValue("ProjectNumber", true);
            timeZoneId = getFormFieldValue("timeZone", true);
            timeZoneId = correctTimeZone(timeZoneId);
            String vatTxt = getFormFieldValue( "vat", true);
            if (vatTxt != null) this.vat = Long.parseLong(vatTxt);
            defaultUserId = UserLocalServiceUtil.getGuestUserId(getCompanyId());

            final String cancellationPeriodTxt = getFormFieldValue("CancellationPeriodDays", true);
            if (cancellationPeriodTxt != null) {
                this.cancellationPeriodMillis = TimeUnit.DAYS.toMillis(Integer.parseInt(cancellationPeriodTxt));
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing Registration %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    //correct for entering unsupported timezones.
    private String correctTimeZone(String timeZoneId) {
        if (timeZoneId == null) return "GMT";
        if (timeZoneId.equalsIgnoreCase("CEST")) return "CET";
        return timeZoneId;
    }

    void initDates() throws PortalException, ParseException {

        String datesOption = getFormFieldValue( "multipleDatesOption", true);
        daily = "daily".equals(datesOption);
        toBeDetermined = "undetermined".equals(datesOption);
        ArrayList<Period> dayPeriods = new ArrayList<>();
        final TimeZone timeZone;
        timeZone = TimeZone.getTimeZone(Objects.requireNonNullElse(timeZoneId, "CET"));
        if (!toBeDetermined) {
            List<DDMFormFieldValue> registrationDatesFieldSet = getDdmFormFieldValues("registrationDateFieldSet", true);
            for (DDMFormFieldValue fieldSet : registrationDatesFieldSet) {
                final String dateValue = getFormFieldValue(fieldSet.getNestedDDMFormFieldValues(), "registrationDate", false);
                final String startTimeString = getFormFieldValue(fieldSet.getNestedDDMFormFieldValues(), "startTime", false);
                Date startOfDay = parseDateTimeFields(dateValue, startTimeString, timeZone);
                final String endTimeString = getFormFieldValue(fieldSet.getNestedDDMFormFieldValues(), "endTime", false);
                Date endOfDay = parseDateTimeFields(dateValue, endTimeString, timeZone);
                dayPeriods.add(new Period(startOfDay, endOfDay));
            }
            //Add a sort to assure that the returned periods are ordered correctly.
            dayPeriods.sort(Comparator.comparing(Period::getStartDate));
        }

        if (daily && dayPeriods.size() == 2){
            this.dayPeriods.addAll(toDayPeriods(dayPeriods.get(0).getStartDate(), dayPeriods.get(1).getEndDate()));
        } else {
            this.dayPeriods.addAll(dayPeriods);
        }

        if (!dayPeriods.isEmpty()){
            startTime = dayPeriods.get(0).getStartDate();
            endTime = dayPeriods.get(dayPeriods.size()-1).getEndDate();
        }


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
//            if (isWeekDay(day)) {
                dayPeriods.add(new Period(day.getTime() + startTimeMillis, day.getTime() + endTimeMillis ));
//            }
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
        String parentJson = getFormFieldValue( "parent", true);
        if (parentJson == null){
            parseRelatedAssets();
            return;
        }
        JournalArticle journalArticle = JsonContentUtils.jsonReferenceToJournalArticle(parentJson);
        relatedRegistrations.add(_dsdParserUtils.getRegistration(journalArticle));

    }

    private void parseRelatedAssets() throws PortalException {
        List<JournalArticle> relatedArticles = _dsdJournalArticleUtils.getRelatedArticles(getGroupId(), new String[]{getJournalArticle().getArticleId()});

        for (JournalArticle relatedArticle : relatedArticles) {
            Registration registration = _dsdParserUtils.getRegistration(relatedArticle);
            if (registration.canUserRegister(getGroupId())) {
                relatedRegistrations.add(registration);
            }
        }
    }

    public boolean canUserRegister(long userId){

        if (!open) return false;
        if (isEventInPast()) return false;
        if (userId == defaultUserId) return false; //not logged in
        if (requiredTeam == null) return true;

        try {
            Team team = TeamLocalServiceUtil.getTeam(getGroupId(), requiredTeam);
            return TeamServiceUtil.hasUserTeam(userId, team.getTeamId());
        } catch (PortalException e) {
            LOG.error(String.format("Error retrieving SiteTeam %s : %s", requiredTeam, e.getMessage()));
        }
        return false;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isHidden(){
        return hidden;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public boolean isCourse(){
        return type != null && (type.equals("course") || type.equals("onlinecourse"));
    }

    public boolean isCancellationPeriodExceeded(){
        if (cancellationPeriodMillis == 0) return false;

        final long millisBeforeStart = getStartTime().getTime() - System.currentTimeMillis();
        return millisBeforeStart < cancellationPeriodMillis;

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
        if (!relatedRegistrations.isEmpty()) {
            return relatedRegistrations.get(0);
        }
        return null;
    }

    public boolean hasParent() {
        return hasParent;
    }

    private void loadParentRegistration() {
        if (!hasParent || !relatedRegistrations.isEmpty()) return;
        try {
            parseParentRegistration();
            hasParent = !relatedRegistrations.isEmpty();
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
        return !toBeDetermined && System.currentTimeMillis() > endTime.getTime();
    }

    public boolean isToBeDetermined(){
        return toBeDetermined;
    }

    public boolean isMultiDayEvent(){
        long duration = endTime.getTime() - startTime.getTime();
        return duration > TimeUnit.DAYS.toMillis(1);
    }

    public boolean isShowMultipleDaysAsSingleDate(){
        return false;
    }

    public boolean isDaily(){
        return daily;
    }

    public List<Period> getStartAndEndTimesPerDay(){
        return Collections.unmodifiableList(dayPeriods);
    }
}
