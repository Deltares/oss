package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.Period;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BusTransfer extends Registration {

    private static final Log LOG = LogFactoryUtil.getLog(BusTransfer.class);
    private BusRoute busRoute = null;
    private final List<Date> days = new ArrayList<>();

    public BusTransfer(JournalArticle article, DsdParserUtils parserUtils, Locale locale) throws PortalException {
        super(article, parserUtils, locale);
        init();
    }

    private void init() throws PortalException {

        try {
            Document document = getDocument();
            initDates(document);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    void initDates(Document document) throws PortalException, ParseException {

        BusRoute busRoute = getBusRoute();
        List<String> times = busRoute.getTimes();
        long startTimeMillis = 0;
        long endTimeMillis = 0;
        if (times.size() > 0) {
            startTimeMillis = timef.parse(times.get(0)).getTime();
            endTimeMillis = timef.parse(times.get(times.size() - 1)).getTime();
        }

        String datesOption = XmlContentUtils.getDynamicContentByName(document, "multipleDatesOption", true);
        daily = "daily".equals(datesOption);
        String[] registrationDates = XmlContentUtils.getDynamicContentsByName(document, "registrationDate");
        ArrayList<Period> dayPeriods = new ArrayList<>();
        for (String registrationDate : registrationDates) {
            long dayValueMillis = dayf.parse(registrationDate).getTime();
            dayPeriods.add(new Period(dayValueMillis + startTimeMillis, dayValueMillis + endTimeMillis));
        }

        if (daily && dayPeriods.size() == 2){
            this.dayPeriods.addAll(toDayPeriods(dayPeriods.get(0).getStartDate(), dayPeriods.get(1).getEndDate()));
        } else {
            this.dayPeriods.addAll(dayPeriods);
        }

        for (Period dayPeriod : this.dayPeriods) {
            days.add(new Date(dayPeriod.getStartTime() - startTimeMillis));
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

        String busRouteJson = XmlContentUtils.getDynamicContentByName(getDocument(), "busRoute", false);
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
}
