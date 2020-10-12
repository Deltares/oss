package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BusTransfer extends Registration {

    private static final Log LOG = LogFactoryUtil.getLog(BusTransfer.class);

    private final long dayMillis = TimeUnit.DAYS.toMillis(1);
    private final SimpleDateFormat dayf = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timef = new SimpleDateFormat("HH:mm");
    private BusRoute busRoute = null;
    private Date startTime = new Date();
    private Date endTime = new Date();
    private final List<Date> transferDates = new ArrayList<>();
    private final Calendar calendar = Calendar.getInstance();

    public BusTransfer(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {

        try {
            Document document = getDocument();
            Event event = getEvent(String.valueOf(getEventId()));

            String pickupOption = XmlContentUtils.getDynamicContentByName(document, "pickupDates", false);
            if (pickupOption.equals("daily")){
                transferDates.addAll(getTransferDates(event.getStartTime(), event.getEndTime()));
            } else {
                String[] pickupDates = XmlContentUtils.getDynamicContentsByName(document, "date");
                for (String pickupDate : pickupDates) {
                    transferDates.add(dayf.parse(pickupDate));
                }

            }
            if (transferDates.size() > 0) {
                BusRoute busRoute = getBusRoute();
                List<String> times = busRoute.getTimes();
                Date startTime = timef.parse(times.get(0));
                Date endTime = timef.parse(times.get(times.size() - 1));
                this.startTime = new Date(transferDates.get(0).getTime() + startTime.getTime());
                this.endTime = new Date(transferDates.get(transferDates.size() - 1).getTime() + endTime.getTime());
            }

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing bus route %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Collection<? extends Date> getTransferDates(Date startTime, Date endTime) throws ParseException {

        String startDayString = dayf.format(startTime); // remove time
        Date day = dayf.parse(startDayString);
        ArrayList<Date> days = new ArrayList<>();
        while (day.before(endTime)){
            if (!isWeekend(day)) {
                days.add(day);
            }
            day = new Date(day.getTime() + dayMillis);
        }
        return days;
    }

    private boolean isWeekend(Date day) {
        calendar.setTime(day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
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
        AbsDsdArticle instance = AbsDsdArticle.getInstance(article);

        if (! (instance instanceof BusRoute)){
            throw new PortalException("Article not instance of BusRoute: " + instance.getTitle());
        }
        return (BusRoute) instance;
    }

    public List<Date> getTransferDates() {
        return new ArrayList<>(transferDates);
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

}
