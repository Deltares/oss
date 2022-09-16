package nl.deltares.portal.model.impl;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;

import java.util.*;

public class BusRoute extends AbsDsdArticle{

    private static final Log LOG = LogFactoryUtil.getLog(BusRoute.class);

    private List<Location> stops = null;
    private List<String> times = null;

    public BusRoute(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, locale);
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
        final List<DDMFormFieldValue> stopNodes = getDdmFormFieldValues("location", false);
        for (DDMFormFieldValue stopNode : stopNodes) {
            String locationJson = extractStringValue(stopNode);
            JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(locationJson);
            AbsDsdArticle location = dsdParserUtils.toDsdArticle(article, this.getLocale());
            if (!(location instanceof Location)) throw new PortalException(String.format("Article %s not instance of Location", article.getTitle()));
            this.stops.add((Location) location);
            this.times.add(getFormFieldValue(stopNode.getNestedDDMFormFieldValues(), "time", false));
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

}
