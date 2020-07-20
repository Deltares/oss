package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BusTransfer extends Registration {

    private final long dayMillis = TimeUnit.DAYS.toMillis(1);
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private BusRoute busRoute = null;
    private List<Date> transferDates = new ArrayList<>();

    public BusTransfer(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {

        try {
            Document document = getDocument();
            String pickupOption = XmlContentParserUtils.getDynamicContentByName(document, "pickupDates", false);
            if (pickupOption.equals("daily")){
                transferDates.addAll(getTransferDates(getStartTime(), getEndTime()));
            } else {
                String[] pickupDates = XmlContentParserUtils.getDynamicContentsByName(document, "date");
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
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Bustransfer.name();
    }

    public BusRoute getBusRoute() throws PortalException {
        if (busRoute != null) return busRoute;
        String busRouteJson = XmlContentParserUtils.getDynamicContentByName(getDocument(), "busRoute", false);
        this.busRoute = parseBusRoute(busRouteJson);
        return busRoute;
    }

    private BusRoute parseBusRoute(String busRoute) throws PortalException {
        JournalArticle article = JsonContentParserUtils.jsonReferenceToJournalArticle(busRoute);
        AbsDsdArticle instance = AbsDsdArticle.getInstance(article);

        if (! (instance instanceof BusRoute)){
            throw new PortalException("Article not instance of BusRoute: " + instance.getTitle());
        }
        return (BusRoute) instance;
    }

    public List<Date> getTransferDates() {
        return new ArrayList<>(transferDates);
    }
}
