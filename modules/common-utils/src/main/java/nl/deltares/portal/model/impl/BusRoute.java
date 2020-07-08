package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.JsonContentParserUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public class BusRoute extends AbsDsdArticle{
    private boolean storeInParentSite;
    private List<Location> stops = new ArrayList<>();
    private List<String> times = new ArrayList<>();

    BusRoute(JournalArticle article) throws PortalException {
        super(article);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String storeInParentSite = XmlContentParserUtils.getDynamicContentByName(document, "storeInParentSite", true);
            this.storeInParentSite = Boolean.parseBoolean(storeInParentSite);
            NodeList stopNodes = XmlContentParserUtils.getDynamicElementsByName(document, "location");
            for (int i = 0; i < stopNodes.getLength(); i++) {
                Node stopNode = stopNodes.item(i);
                String locationJson = XmlContentParserUtils.getDynamicContentForNode(stopNode);
                this.stops.add(JsonContentParserUtils.parseLocationJson(locationJson));
                this.times.add(XmlContentParserUtils.getDynamicContentByName(stopNode, "time", false));
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    public List<Location> getStops(){
        return new ArrayList<>(stops);
    }

    public List<String> getTimes() {return new ArrayList<>(times);}

    public Location getLocation(String time){
        int i = times.indexOf(time);
        if (i > -1) return stops.get(i);
        return null;
    }

    public String getTime(Location stop){
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
