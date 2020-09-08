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
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private BusRoute busRoute = null;
    private final List<Date> transferDates = new ArrayList<>();

    public BusTransfer(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {

        try {
            Document document = getDocument();
            String pickupOption = XmlContentUtils.getDynamicContentByName(document, "pickupDates", false);
            if (pickupOption.equals("daily")){
                transferDates.addAll(getTransferDates(getStartTime(), getEndTime()));
            } else {
                String[] pickupDates = XmlContentUtils.getDynamicContentsByName(document, "date");
                for (String pickupDate : pickupDates) {
                    transferDates.add(df.parse(pickupDate));
                }
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing bus route %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private Collection<? extends Date> getTransferDates(Date startTime, Date endTime) throws ParseException {

        String startDayString = df.format(startTime); // remove time
        Date day = df.parse(startDayString);
        ArrayList<Date> days = new ArrayList<>();
        while (day.before(endTime)){
            days.add(day);
            day = new Date(day.getTime() + dayMillis);
        }
        return days;
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
        if (transferDates.size() == 0) {
            if (getEvent().isPresent()) {
                return getEvent().get().getStartTime();
            }
        }
        return transferDates.get(0);
    }

    @Override
    public Date getEndTime() {
        if (transferDates.size() == 0) {
            if (getEvent().isPresent()) {
                return getEvent().get().getEndTime();
            }
        }
        return transferDates.get(transferDates.size() -1);
    }

    private Optional<Event> getEvent() {
        return Optional.ofNullable(getCachedEventArticle(String.valueOf(this.getEventId())));
    }
}
