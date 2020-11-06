package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.Period;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.*;

public class SessionRegistration extends Registration {
    private static final Log LOG = LogFactoryUtil.getLog(SessionRegistration.class);
    private Room room = null;
    private final List<Expert> presenters = new ArrayList<>();
    private String imageUrl = "";
    private String webinarKey;

    public SessionRegistration(JournalArticle article, DsdParserUtils dsdParserUtils) throws PortalException {
        super(article, dsdParserUtils);
        init();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Session.name();
    }

    private void init() throws PortalException {

        try {
            Document document = getDocument();
            String jsonImage = XmlContentUtils.getDynamicContentByName(document, "eventImage", true);
            if (jsonImage != null) {
                imageUrl = JsonContentUtils.parseImageJson(jsonImage);
            }
            webinarKey = XmlContentUtils.getDynamicContentByName(document, "webinarKey", true);
            //todo: Add provider currently only GOTO

            initDates(document);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private void initDates(Document document) throws PortalException, ParseException {

        String sessionOption = XmlContentUtils.getDynamicContentByName(document, "sessionDates", true);
        daily = "daily".equals(sessionOption);
        if (daily){
            startTime = XmlContentUtils.parseDateTimeFields(document,"dailyStartDay", "dailyStartTime");
            endTime = XmlContentUtils.parseDateTimeFields(document,"dailyEndDay", "dailyEndTime");
            if (endTime == null) endTime = startTime;
            dayPeriods.addAll(toDayPeriods(startTime, endTime));
        } else {
            NodeList specificDates = XmlContentUtils.getDynamicElementsByName(document, "specificDate");
            for (int i = 0; i < specificDates.getLength(); i++) {
                Node item = specificDates.item(i);
                Date startOfDay = XmlContentUtils.parseDateTimeFields(item, "specificStartTime");
                Date endOfDay = XmlContentUtils.parseDateTimeFields(item, "specificEndTime");
                dayPeriods.add(new Period(startOfDay, endOfDay));
            }
            if (dayPeriods.size() > 0) {
                startTime = dayPeriods.get(0).getStartDate();
                endTime = dayPeriods.get(dayPeriods.size() - 1).getEndDate();
            }
        }
    }

    @Override
    public void validate() throws PortalException {
        parseRoom();
        parsePresenter();
        validateRoomCapacity();
        super.validate();
    }

    private void validateRoomCapacity() throws PortalException {
        int sessionCapacity = getCapacity();
        int roomCapacity = getRoom().getCapacity();
        if (roomCapacity < sessionCapacity) {
            throw new ValidationException(String.format("Room capacity %d is smaller than session capacity %s !", roomCapacity, sessionCapacity));
        }
    }

    private void parseRoom() throws PortalException {
        String roomJson = XmlContentUtils.getDynamicContentByName(getDocument(), "room", false);
        JournalArticle journalArticle = JsonContentUtils.jsonReferenceToJournalArticle(roomJson);
        AbsDsdArticle dsdArticle = dsdParserUtils.toDsdArticle(journalArticle);
        if (!(dsdArticle instanceof Room)){
            throw new PortalException("Unsupported registration type! Expected Room but found: " + dsdArticle.getClass().getName());
        }
        room = (Room) dsdArticle;
    }

    private void parsePresenter() throws PortalException {

        String[] presenters = XmlContentUtils.getDynamicContentsByName(getDocument(), "presenters");
        for (String presenterJson : presenters) {
            JournalArticle journalArticle = JsonContentUtils.jsonReferenceToJournalArticle(presenterJson);
            this.presenters.add( dsdParserUtils.getExpert(journalArticle) );
        }
    }

    public Room getRoom() {
        loadRoom();
        return room;
    }

    private void loadRoom() {
        if (room != null) return;
        try {
            parseRoom();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing room for session %s: %s", getTitle(), e.getMessage()));
        }
    }

    public List<Expert> getPresenters() {
        loadPresenter();
        return Collections.unmodifiableList(presenters);
    }

    private void loadPresenter() {
        if (presenters.size() > 0) return;
        try {
            parsePresenter();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing presenter for session %s: %s", getTitle(), e.getMessage()));
        }
    }

    public String getWebinarKey(){ return  webinarKey; }

    @Override
    public String getSmallImageURL(ThemeDisplay themeDisplay) {
        String smallImageURL = super.getSmallImageURL(themeDisplay);
        if (smallImageURL != null && !smallImageURL.trim().isEmpty()) {
            return smallImageURL;
        }
        return imageUrl;
    }
}
