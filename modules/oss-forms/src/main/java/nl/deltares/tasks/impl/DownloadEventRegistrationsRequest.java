package nl.deltares.tasks.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import nl.deltares.model.BadgeInfo;
import nl.deltares.model.BillingInfo;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DownloadEventRegistrationsRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DownloadEventRegistrationsRequest.class);
    private final String articleId;
    private final DsdParserUtils dsdParserUtils;
    private final DsdSessionUtils dsdSessionUtils;
    private final Group group;
    private final DsdJournalArticleUtils dsdJournalArticleUtils;
    private final boolean deleteOnCompletion;
    private final WebinarUtilsFactory webinarUtilsFactory;
    private final Locale locale;
    private final boolean removeMissing;
    private final boolean useResourcePrimKey;
    private final boolean reproVersion;
    private boolean disableCallWebinar;

    public DownloadEventRegistrationsRequest(String id, long currentUser, String articleId, Group siteGroup,
                                             DsdParserUtils dsdParserUtils, DsdSessionUtils dsdSessionUtils,
                                             DsdJournalArticleUtils dsdJournalArticleUtils,
                                             WebinarUtilsFactory webinarUtilsFactory, boolean primKey,
                                             boolean delete, boolean removeMissing, boolean reproVersion) throws IOException {
        super(id, currentUser);
        this.articleId = articleId;
        this.group = siteGroup;

        this.dsdParserUtils = dsdParserUtils;
        this.dsdSessionUtils = dsdSessionUtils;
        this.dsdJournalArticleUtils = dsdJournalArticleUtils;
        this.webinarUtilsFactory = webinarUtilsFactory;
        this.deleteOnCompletion = delete;
        this.removeMissing = removeMissing;
        this.useResourcePrimKey = primKey;
        this.reproVersion = reproVersion;
        this.locale = LocaleUtil.fromLanguageId(siteGroup.getDefaultLanguageId());

    }

    @Override
    public STATUS call() {

        if (getStatus() == available) return status;
        status = running;
        statusMessage = "starting download";
        init();

        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                writeHeader(writer);

                if (articleId.equals("all")) {
                    //we are potentially downloading a lot so no webinar requests
                    disableCallWebinar = true;
                    downloadAllRegistrations(writer);
                } else if (useResourcePrimKey) {
                    //we are deleting so no webinar info requests
                    disableCallWebinar = true;
                    final long resourceId = Long.parseLong(articleId);
                    downloadRequestedResourceIdRegistrations(resourceId, writer);
                } else {
                    //If we are deleting then do not request webinar info
                    disableCallWebinar = deleteOnCompletion;
                    downloadRequestedArticleRegistrations(articleId, writer);
                }

            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = terminated;
                statusMessage = errorMessage;
            }

            if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
            Files.move(tempFile.toPath(), dataFile.toPath());

            if (deleteOnCompletion){
                if (useResourcePrimKey) {
                    deleteRegistrationsForRecourseId( Long.parseLong(articleId));
                } else {
                    deleteRegistrationsForArticle(articleId);
                }
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
        fireStateChanged();

        return status;
    }

    private void deleteRegistrationsForRecourseId(long  resourceId) {

        statusMessage = "deleting registrations for resourcePrimKey" + resourceId;

        try {
            logger.info(String.format("Deleting registration records for  groupId %d and eventResourcePrimKey %d", group.getGroupId(), resourceId) );
            dsdSessionUtils.deleteEventRegistrations(group.getGroupId(), resourceId);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting event registrations for groupId %d and eventResourcePrimKey %d: %s", group.getGroupId(), resourceId, e.getMessage()));
        }

        try {
            logger.info(String.format("Deleting registration records for groupId %d and resourcePrimKey %d", group.getGroupId(), resourceId) );
            dsdSessionUtils.deleteRegistrationsFor(group.getGroupId(), resourceId);
        } catch (PortalException e) {
            logger.warn(String.format("Error deleting registrations for groupId %d and resourcePrimKey %d: %s", group.getGroupId(), resourceId, e.getMessage()));
        }
    }

    private void deleteRegistrationsForArticle(String articleId) throws PortalException {

        DsdArticle dsdArticle = getJournalArticle(articleId);
        statusMessage = "deleting registrations for " + dsdArticle.getTitle();
        if (dsdArticle instanceof Event) {
            logger.info(String.format("Deleting registration records for Event %s (%s)", dsdArticle.getTitle(), dsdArticle.getArticleId()) );
            dsdSessionUtils.deleteEventRegistrations(dsdArticle.getGroupId(), dsdArticle.getResourceId());
        } else if (dsdArticle instanceof Registration){
            logger.info(String.format("Deleting registration records for Registration %s (%s)", dsdArticle.getTitle(), dsdArticle.getArticleId()) );
            dsdSessionUtils.deleteRegistrationsFor((Registration) dsdArticle);
        }
    }

    private DsdArticle getDsdArticle(Long resourcePrimaryKey, Map<Long, DsdArticle> cache) {

        if (cache.containsKey(resourcePrimaryKey)) return cache.get(resourcePrimaryKey);

        try {
            final JournalArticle latestArticle = dsdJournalArticleUtils.getLatestArticle(resourcePrimaryKey);
            final AbsDsdArticle article = dsdParserUtils.toDsdArticle(latestArticle);
            cache.put(resourcePrimaryKey, article);
            return article;

        } catch (PortalException e) {
            //do not keep trying to retrieve article if we know it does not exist
            cache.put(resourcePrimaryKey, null);
        }
        return null;
    }

    private void downloadAllRegistrations(PrintWriter writer) {

        totalCount = dsdSessionUtils.getRegistrationCount();
        if (totalCount == 0){
            status = nodata;
            setProcessCount(0);
            return;
        }

        //Load the dsdArticles into a cache for retrieval later, so we can access title and other stuff.
        Map<Long, DsdArticle> cache = new HashMap<>();
        for (int i = 0; i < totalCount; ) {
            int start = i;
            int end = i + 500;
            final List<Map<String, Object>> registrationRecordsToProcess = dsdSessionUtils.getRegistrations(start, end);
            registrationRecordsToProcess.forEach(recordObjects -> {
                if (status == terminated) return;
                incrementProcessCount(1);
                Long eventResourcePrimaryKey = (Long) recordObjects.get("eventResourcePrimaryKey");
                Event event = (Event) getDsdArticle(eventResourcePrimaryKey, cache);
                Long resourcePrimaryKey = (Long) recordObjects.get("resourcePrimaryKey");
                Registration registration = (Registration) getDsdArticle(resourcePrimaryKey, cache);

                statusMessage = String.format("procession eventResourcePrimaryKey=%d and resourcePrimaryKey=%d",
                        eventResourcePrimaryKey, resourcePrimaryKey);

                User matchingUser = null;
                try {
                    matchingUser = UserLocalServiceUtil.getUser((Long) recordObjects.get("userId"));
                } catch (PortalException e) {
                    //
                }
                writeRecord(writer, recordObjects, event, registration, matchingUser, null, locale);
                if (Thread.interrupted()) {
                    throw new RuntimeException("Thread interrupted");
                }

                if (Thread.interrupted()) {
                    status = terminated;
                    errorMessage = String.format("Thread 'DownloadEventRegistrationsRequest' with id %s is interrupted!", id);
                }
            });

            i = end;
        }
        if (status != terminated) {
            status = available;
        }


    }

    private void downloadRequestedResourceIdRegistrations(long resourceId, PrintWriter writer) {

        List<Map<String, Object>> registrationRecordsToProcess = dsdSessionUtils.getRegistrations(group.getGroupId(), resourceId);
        registrationRecordsToProcess.addAll(dsdSessionUtils.getEventRegistrations(group.getGroupId(), resourceId));

        if (registrationRecordsToProcess.size() == 0) {
            status = nodata;
            setProcessCount(0);
            return;
        }
        totalCount = registrationRecordsToProcess.size();

        registrationRecordsToProcess.forEach(recordObjects -> {
            if (status == terminated) return;
            incrementProcessCount(1);
            Long resourcePrimaryKey = (Long) recordObjects.get("resourcePrimaryKey");
            statusMessage = "procession resourcePrimaryKey=" + resourcePrimaryKey;

            User matchingUser = null;
            try {
                matchingUser = UserLocalServiceUtil.getUser((Long) recordObjects.get("userId"));
            } catch (PortalException e) {
                //
            }
            writeRecord(writer, recordObjects, null, null, matchingUser,
                    null, locale);
            if (Thread.interrupted()) {
                status = terminated;
                errorMessage = String.format("Thread 'DownloadEventRegistrationsRequest' with id %s is interrupted!", id);
            }

        });
        if (status != terminated) {
            status = available;
        }


    }

    private void downloadRequestedArticleRegistrations(String articleId, PrintWriter writer) {
        try {

            //Get article for requested articleId.
            DsdArticle article = getJournalArticle(articleId);
            //Load the registrations into a cache for retrieval later, so we can access title and other stuff.
            Map<Long, Registration> registrationCache = new HashMap<>();
            Map<String, List<String>> webinarKeyCache = new HashMap<>();

            List<Map<String, Object>> registrationRecordsToProcess = getRegistrationRecordsLinkedToSelectedArticle(
                    article, registrationCache);

            if (registrationRecordsToProcess.size() == 0) {
                status = nodata;
                setProcessCount(0);
                return;
            }
            totalCount = registrationRecordsToProcess.size();

            Event event = getEvent(article);
            registrationRecordsToProcess.forEach(recordObjects -> {
                if (status == terminated) return;
                incrementProcessCount(1);
                Long resourcePrimaryKey = (Long) recordObjects.get("resourcePrimaryKey");
                statusMessage = "procession resourcePrimaryKey=" + resourcePrimaryKey;
                Registration matchingRegistration = registrationCache.get(resourcePrimaryKey);

                User matchingUser = null;
                try {
                    matchingUser = UserLocalServiceUtil.getUser((Long) recordObjects.get("userId"));
                } catch (PortalException e) {
                    //
                }
                if (reproVersion) {
                    //write short output for printing badges
                    writeReproRecord(writer, recordObjects, event, matchingRegistration, matchingUser);
                } else {
                    writeRecord(writer, recordObjects, event, matchingRegistration, matchingUser,
                            webinarKeyCache, locale);
                }
                if (Thread.interrupted()) {
                    status = terminated;
                    errorMessage = String.format("Thread 'DownloadEventRegistrationsRequest' with id %s is interrupted!", id);
                }


            });
            if (status != terminated) {
                status = available;
            }

        } catch (PortalException e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
    }

    private void writeHeader(PrintWriter writer) {

        StringBuilder header;
        if (reproVersion){
            header = new StringBuilder("eventTitle,registrationTitle,email,badgeName,organization");
        } else {
            header = new StringBuilder("eventId, eventTitle, projectNumber, registrationId, registrationTitle,start date,topic,type,email,firstName,lastName,webinarProvider,registrationStatus,remarks");

            for (BillingInfo.ATTRIBUTES value : BillingInfo.ATTRIBUTES.values()) {
                header.append(',');
                header.append(value.name());
            }
            header.append(",registration time");
            header.append(",organization");
        }
        writer.println(header);
    }

    private List<Map<String, Object>> getRegistrationRecordsLinkedToSelectedArticle(DsdArticle selectedArticle,
                                                                                    Map<Long, Registration> registrationCache) throws PortalException {

        if (selectedArticle instanceof Event){
            List<Registration> eventRegistrations = ((Event)selectedArticle).getRegistrations(locale);
            eventRegistrations.forEach(registration -> registrationCache.put(registration.getResourceId(), registration));
            return dsdSessionUtils.getRegistrations((Event) selectedArticle);
        } else if (selectedArticle instanceof Registration) {
            final Registration registration = (Registration) selectedArticle;
            registrationCache.put(selectedArticle.getResourceId(), registration);
            return dsdSessionUtils.getRegistrations(registration.getGroupId(), registration.getResourceId());
        } else {
            return Collections.emptyList();
        }
    }

    private void deleteBrokenRegistration(long registrationId){

        logger.info(String.format("Deleting registration record %d", registrationId) );
        try {
            dsdSessionUtils.deleteRegistrationRecord(registrationId);
        } catch (PortalException e) {
            logger.warn(String.format("Could not find registration record for deletion: %d", registrationId));
        }
    }

    private DsdArticle getJournalArticle(String articleId) throws PortalException {

        JournalArticle article = null;
        try {
            article =  dsdJournalArticleUtils.getJournalArticle(group.getGroupId(), articleId);
        } catch (PortalException e) {
            List<Group> children = group.getChildren(true);
            for (Group child : children) {
                try {
                    article = dsdJournalArticleUtils.getJournalArticle(child.getGroupId(), articleId);
                    break;
                } catch (PortalException portalException) {
                    //
                }
            }
        }
        if (article != null) return dsdParserUtils.toDsdArticle(article);
        throw new PortalException("Could not find dsd article for id " + articleId);

    }

    private Event getEvent(DsdArticle dsdArticle) throws PortalException {

        if (dsdArticle instanceof Event) return (Event) dsdArticle;
        if (dsdArticle instanceof Registration){
            return (Event) getJournalArticle(String.valueOf(((Registration) dsdArticle).getEventId()));
        }
        logger.warn("Could not find event for id " + articleId);
        return null;
    }

    private void writeReproRecord(PrintWriter writer, Map<String, Object> record, Event event,
                             Registration dsdRegistration, User user) {

        StringBuilder line = new StringBuilder();
        if (event == null) {
            return;
        } else {
            writeField(line, event.getTitle());
        }
        if (dsdRegistration == null) {
            return;
        } else {
            writeField(line, dsdRegistration.getTitle());
        }
        if (user == null){
            return;
        } else {
            writeField(line, user.getEmailAddress());
        }
        String userPreferencesValue = (String) record.get("userPreferences");
        Map<String, String> userPreferences = Collections.emptyMap();
        try {
            userPreferences = JsonContentUtils.parseJsonToMap(userPreferencesValue);
        } catch (JSONException e) {
            logger.error(String.format("Invalid userPreferences '%s': %s", record.get("userPreferences"), e.getMessage()));
        }
        writeBadgeInfo(line, userPreferences, user);
        writeField(line, userPreferences.get(KeycloakUtils.ATTRIBUTES.org_name.name()));
        writer.println(line);
    }

    private void writeRecord(PrintWriter writer, Map<String, Object> record, Event event,
                             Registration dsdRegistration, User user, Map<String, List<String>> courseRegistrationsCache, Locale locale) {

        StringBuilder line = new StringBuilder();
        if (event == null) {
            writeField(line, record.get("eventResourcePrimaryKey").toString());
            writeField(line, null);
        } else {
            writeField(line, String.valueOf(event.getArticleId()));
            writeField(line, event.getTitle());
        }
        if (dsdRegistration == null) {
            writeField(line, null);
            writeField(line, record.get("resourcePrimaryKey").toString());
            writeField(line, null);
        } else {
            writeField(line, dsdRegistration.getProjectNumber());
            writeField(line, dsdRegistration.getArticleId());
            writeField(line, dsdRegistration.getTitle());
        }
        writeField(line, DateUtil.getDate((Date) record.get("startTime"),"yyyy-MM-dd", locale));
        if (dsdRegistration == null){
            writeField(line, null);
            writeField(line, null);
        } else {
            writeField(line, dsdRegistration.getTopic());
            writeField(line, dsdRegistration.getType());
        }
        if (user == null){
            writeField(line, record.get("userId").toString());
            writeField(line,null);
            writeField(line,null);
        } else {
            writeField(line, user.getEmailAddress());
            writeField(line, user.getFirstName());
            writeField(line, user.getLastName());
        }
        String userPreferencesValue = (String) record.get("userPreferences");
        Map<String, String> userPreferences = Collections.emptyMap();
        try {
            userPreferences = JsonContentUtils.parseJsonToMap(userPreferencesValue);
        } catch (JSONException e) {
            logger.error(String.format("Invalid userPreferences '%s': %s", record.get("userPreferences"), e.getMessage()));
        }
        writeWebinarInfo(line, user, dsdRegistration, courseRegistrationsCache, userPreferences);
        writeField(line, userPreferences.get("remarks"));
        writeBillingInfo(line, userPreferences);
        writeField(line, userPreferences.get("registration_time"));
        writeField(line, userPreferences.get(KeycloakUtils.ATTRIBUTES.org_name.name()));
        if (removeMissing && dsdRegistration == null){
            deleteBrokenRegistration((Long)record.get("registrationId"));
            writeField(line, "deleted record");
        }

        writer.println(line);
    }

    private void writeField(StringBuilder line, String value) {
        if (value != null) {
            if (value.contains("\n")) {
                value = value.replaceAll("\n", " ");
            }
            //Change ; -> , to avoid problems with regional settings.
            if (value.contains(";")) {
                value = value.replaceAll(";", ",");
            }
            boolean addQuotes = value.contains(",");
            if (addQuotes){
                line.append("\"");
            }
            line.append(value);
            if (addQuotes){
                line.append("\"");
            }
        }
        line.append(',');
    }

    private void writeWebinarInfo(StringBuilder line, User user, Registration registration, Map<String,
            List<String>> courseRegistrationsCache, Map<String, String> userPreferences) {

        if (disableCallWebinar || !webinarUtilsFactory.isWebinarSupported(registration)) {
            line.append(','); //webinarProvider
            line.append(','); //registrationStatus
            return;
        }

        SessionRegistration sessionRegistration = (SessionRegistration) registration;
        writeField(line, sessionRegistration.getWebinarProvider());

        WebinarUtils webinarUtils;
        try {
            webinarUtils = webinarUtilsFactory.newInstance(registration);
        } catch (Exception e) {
            logger.error(String.format("Failed to get utils for webinar provider %s: %s", ((SessionRegistration) registration).getWebinarProvider(), e.getMessage()));
            line.append(','); //registrationStatus
            return;
        }
        String webinarKey = sessionRegistration.getWebinarKey();

        String status;
        if (deleteOnCompletion){
            try {
                webinarUtils.unregisterUser(user, webinarKey, userPreferences);
                status = "unregistered";
            } catch (Exception e) {
                logger.error(String.format("Failed to unregister user for webinar %s: %s", webinarKey, e.getMessage()));
                status = "failed to unregister";
            }
        } else {
            List<String> courseRegistrations = courseRegistrationsCache.get(webinarKey);
            if (courseRegistrations == null) {
                try {
                    courseRegistrations = webinarUtils.getAllCourseRegistrations(webinarKey);
                } catch (Exception e) {
                    logger.error(String.format("Failed to get course registrations for webinar %s: %s", webinarKey, e.getMessage()));
                    courseRegistrations = Collections.emptyList();
                }
                courseRegistrationsCache.put(webinarKey, courseRegistrations);
            }
            status = webinarUtils.isUserInCourseRegistrationsList(courseRegistrations, user) ? "registered" : "not in registration list";
        }
        writeField(line, status);
    }

    private void writeBillingInfo(StringBuilder line, Map<String, String> billingInfo){

        //Write billing information.
        for (BillingInfo.ATTRIBUTES key : BillingInfo.ATTRIBUTES.values()) {
            String billingAttribute = billingInfo.get(key.name());
            writeField(line, billingAttribute);
        }
    }

    private void writeBadgeInfo(StringBuilder line, Map<String, String> badgeInfo, User user){

        final BadgeInfo instance = BadgeInfo.getInstance(badgeInfo);
        final String badgeName = instance.formatBadgeName(user.getFirstName(), user.getLastName());
        writeField(line, badgeName);

    }
}
