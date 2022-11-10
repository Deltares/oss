package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BusTransfer extends Registration {

    private static final Log LOG = LogFactoryUtil.getLog(BusTransfer.class);
    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    private List<JournalArticle> stops = null;
    private List<String> times = null;
    private String transferDay;
    private String name;

    public BusTransfer(JournalArticle article, DsdParserUtils parserUtils, Locale locale) throws PortalException {
        super(article, parserUtils, locale);
        init();
    }

    private void init() throws PortalException {

        dateTimeFormatter.setTimeZone(TimeZone.getTimeZone(getTimeZoneId()));

        try {
            Document document = getDocument();
            final String name = XmlContentUtils.getDynamicContentByName(document, "Name", true);
            this.name = name == null? getTitle() : name;
            initDates(document);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    void initDates(Document document) throws PortalException, ParseException {

        List<String> times = getTimes();

        String startTimevalue = "00:00";
        String endTimevalue = "00:00";
        if (times.size() > 0) {
            startTimevalue = times.get(0);
            endTimevalue = times.get(times.size() - 1);
        }
        transferDay = XmlContentUtils.getDynamicContentByName(document, "registrationDate", false);
        startTime = dateTimeFormatter.parse(String.format("%sT%s", transferDay, startTimevalue));
        endTime = dateTimeFormatter.parse(String.format("%sT%s", transferDay, endTimevalue));
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Bustransfer.name();
    }

    private void loadStops(){
        try {
            parseStops();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing stops for route %s: %s", getTitle(), e.getMessage()));
        }
    }


    private void parseStops() throws PortalException {
        this.stops = new ArrayList<>();
        this.times = new ArrayList<>();
        NodeList stopNodes = XmlContentUtils.getDynamicElementsByName(getDocument(), "location");
        for (int i = 0; i < Objects.requireNonNull(stopNodes).getLength(); i++) {
            Node stopNode = stopNodes.item(i);
            String locationJson = XmlContentUtils.getDynamicContentForNode(stopNode);
            JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(locationJson);
            this.stops.add(article);
            this.times.add(XmlContentUtils.getDynamicContentByName(stopNode, "time", false));
        }
    }
    public List<JournalArticle> getStops() {
        if (stops == null) loadStops();
        return Collections.unmodifiableList(stops);
    }

    public List<String> getTimes()  {
        if (stops == null) loadStops();
        return Collections.unmodifiableList(times);}

    public JournalArticle getLocation(String time) {
        if (stops == null) loadStops();
        int i = times.indexOf(time);
        if (i > -1) return stops.get(i);
        return null;
    }

    public String getTime(JournalArticle stop) {
        if (stops == null) loadStops();

        int i = stops.indexOf(stop);
        if (i > -1) return times.get(i);
        return null;
    }

    public String getTransferDay(){
        return transferDay;
    }

    public String getName(){
        return name;
    }
}
