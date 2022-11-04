package nl.deltares.portal.model.impl;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.Period;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BusTransfer extends Registration {

    private static final Log LOG = LogFactoryUtil.getLog(BusTransfer.class);
    private BusRoute busRoute = null;
    private final List<Date> days = new ArrayList<>();
    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    private final Calendar calendar = Calendar.getInstance();
    public BusTransfer(JournalArticle article, DsdParserUtils parserUtils, Locale locale) throws PortalException {
        super(article, parserUtils, locale);
        init();
    }

    private void init() throws PortalException {

        final TimeZone timeZone;
        if (busRoute != null){
            timeZone = TimeZone.getTimeZone(getTimeZoneId());
        } else {
            timeZone = TimeZone.getTimeZone("CET");
        }
        dateTimeFormatter.setTimeZone(timeZone);
//        calendar.setTimeZone(timeZone);
        try {
            initDates(null);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    void initDates(Document document) throws PortalException, ParseException {

        BusRoute busRoute = getBusRoute();
        List<String> times = busRoute.getTimes();

        String startTimevalue = "00:00";
        String endTimevalue = "00:00";
        if (times.size() > 0) {
            startTimevalue = times.get(0);
            endTimevalue = times.get(times.size() - 1);
        }

        String datesOption = getFormFieldValue( "multipleDatesOption", true);
        daily = "daily".equals(datesOption);
        List<DDMFormFieldValue> formFieldValues = getDdmFormFieldValues( "registrationDate", false);
        final List<String> registrationDates = extractStringValues(formFieldValues);
        ArrayList<Period> dayPeriods = new ArrayList<>();
        for (String registrationDate : registrationDates) {
            dayPeriods.add(new Period(
                    dateTimeFormatter.parse(String.format("%sT%s", registrationDate, startTimevalue)),
                    dateTimeFormatter.parse(String.format("%sT%s", registrationDate, endTimevalue))
            ));
        }

        if (daily && dayPeriods.size() == 2){
            this.dayPeriods.addAll(toDayPeriods(dayPeriods.get(0).getStartDate(), dayPeriods.get(1).getEndDate()));
        } else {
            this.dayPeriods.addAll(dayPeriods);
        }
        //convert to day values
        for (Period dayPeriod : this.dayPeriods) {
            days.add(atStartOfDay(dayPeriod.getStartDate()));
        }

        startTime = dayPeriods.get(0).getStartDate();
        endTime = dayPeriods.get(dayPeriods.size() - 1).getEndDate();
    }

    @Override
    public void validate() throws PortalException {
        parseBusRoute();
        super.validate();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Bustransfer.name();
    }

    public BusRoute getBusRoute(){
        if (busRoute != null) {
            return busRoute;
        }
        try {
            busRoute = parseBusRoute();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing bus route for transfer %s: %s", getTitle(), e.getMessage()));
        }
        return busRoute;
    }

    private BusRoute parseBusRoute() throws PortalException {

        String busRouteJson = getFormFieldValue("busRoute", false);
        JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(busRouteJson);
        AbsDsdArticle busRoute = dsdParserUtils.toDsdArticle(article, getLocale());
        if (! (busRoute instanceof BusRoute)){
            throw new PortalException("Article not instance of BusRoute: " + busRoute.getTitle());
        }
        return (BusRoute) busRoute;
    }

    public boolean isValidDate(Date transferDate) {
        return days.contains(transferDate);
    }

    public List<Date> getTransferDays(){
        return days;
    }

//    public Date atEndOfDay(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//        calendar.set(Calendar.MILLISECOND, 999);
//        return calendar.getTime();
//    }

    private Date atStartOfDay(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
