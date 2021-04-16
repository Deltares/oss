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

import java.util.*;

public class BusRoute extends AbsDsdArticle{

    private static final Log LOG = LogFactoryUtil.getLog(BusRoute.class);

    private boolean storeInParentSite;
    private List<Location> stops = null;
    private List<String> times = null;

    public BusRoute(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, locale);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String storeInParentSite = XmlContentUtils.getDynamicContentByName(document, "storeInParentSite", true);
            this.storeInParentSite = Boolean.parseBoolean(storeInParentSite);

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public void validate() throws PortalException {
        parseStops();
        super.validate();
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
            AbsDsdArticle location = dsdParserUtils.toDsdArticle(article, this.getLocale());
            if (!(location instanceof Location)) throw new PortalException(String.format("Article %s not instance of Location", article.getTitle()));
            this.stops.add((Location) location);
            this.times.add(XmlContentUtils.getDynamicContentByName(stopNode, "time", false));
        }
    }
    public List<Location> getStops() {
        if (stops == null) loadStops();
        return Collections.unmodifiableList(stops);

    }

    public List<String> getTimes()  {
        if (stops == null) loadStops();
        return Collections.unmodifiableList(times);}

    public Location getLocation(String time) {
        if (stops == null) loadStops();
        int i = times.indexOf(time);
        if (i > -1) return stops.get(i);
        return null;
    }

    public String getTime(Location stop) {
        if (stops == null) loadStops();

        int i = stops.indexOf(stop);
        if (i > -1) return times.get(i);
        return null;
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Busroute.name();
    }

    @Override
    public boolean storeInParentSite() {
        return storeInParentSite;
    }
}
