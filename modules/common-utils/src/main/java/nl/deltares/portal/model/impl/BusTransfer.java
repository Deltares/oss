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
import java.util.*;

public class BusTransfer extends Registration {

    private static final Log LOG = LogFactoryUtil.getLog(BusTransfer.class);
    private BusRoute busRoute = null;
    private final List<Date> transferDays = new ArrayList<>();

    public BusTransfer(JournalArticle article, DsdParserUtils parserUtils) throws PortalException {
        super(article, parserUtils);
        init();
    }

    private void init() throws PortalException {

        try {
            Document document = getDocument();

            initDates(document);

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing bus route %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private void initDates(Document document) throws PortalException, ParseException {
        String pickupOption = XmlContentUtils.getDynamicContentByName(document, "pickupDates", false);
        daily = pickupOption.equals("daily");

        if (daily){
            Event event = dsdParserUtils.getEvent(getGroupId(), String.valueOf(getEventId()));
            if (event == null) return;
            transferDays.addAll(toDayValues(event.getStartTime(), event.getEndTime()));
        } else {
            String[] pickupDates = XmlContentUtils.getDynamicContentsByName(document, "date");
            for (String pickupDate : pickupDates) {
                Date day = dayf.parse(pickupDate);
                transferDays.add(day);
            }
        }

        BusRoute busRoute = getBusRoute();
        List<String> times = busRoute.getTimes();
        long startTimeMillis = 0;
        long endTimeMillis = 0;
        if (times.size() > 0) {
            startTimeMillis = timef.parse(times.get(0)).getTime();
            endTimeMillis = timef.parse(times.get(times.size() - 1)).getTime();
        }

        for (Date transferDay : transferDays) {
            dayPeriods.add(new Period(transferDay.getTime() + startTimeMillis, transferDay.getTime() + endTimeMillis));
        }
        if (dayPeriods.size() > 0){
            startTime = dayPeriods.get(0).getStartDate();
            endTime = dayPeriods.get(dayPeriods.size() -1).getEndDate();
        }
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
        AbsDsdArticle busRoute = dsdParserUtils.toDsdArticle(article);
        if (! (busRoute instanceof BusRoute)){
            throw new PortalException("Article not instance of BusRoute: " + busRoute.getTitle());
        }
        return (BusRoute) busRoute;
    }

    public List<Date> getTransferDates() {
        return Collections.unmodifiableList(transferDays);
    }

}
