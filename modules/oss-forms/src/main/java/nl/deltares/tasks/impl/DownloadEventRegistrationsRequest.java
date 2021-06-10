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
import nl.deltares.dsd.model.BillingInfo;
import nl.deltares.portal.model.DsdArticle;
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
    private final KeycloakUtils keycloakUtils;
    private final Group group;
    private final Locale locale;
    private final DsdJournalArticleUtils dsdJournalArticleUtils;
    private final boolean deleteOnCompletion;
    private final WebinarUtilsFactory webinarUtilsFactory;

    public DownloadEventRegistrationsRequest(String id, long currentUser, String articleId, Group siteGroup, Locale locale,
                                             DsdParserUtils dsdParserUtils, DsdSessionUtils dsdSessionUtils,
                                             KeycloakUtils keycloakUtils, DsdJournalArticleUtils dsdJournalArticleUtils,
                                             WebinarUtilsFactory webinarUtilsFactory, boolean delete) throws IOException {
        super(id, currentUser);
        this.articleId = articleId;
        this.group = siteGroup;
        this.locale = locale;
        this.dsdParserUtils = dsdParserUtils;
        this.dsdSessionUtils = dsdSessionUtils;
        this.keycloakUtils = keycloakUtils;
        this.dsdJournalArticleUtils = dsdJournalArticleUtils;
        this.webinarUtilsFactory = webinarUtilsFactory;
        this.deleteOnCompletion = delete;

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
            //Get article for requested articleId.
            DsdArticle selectedArticle = getJournalArticle(articleId);

            Map<Long, Registration> registrationCache = new HashMap<>();
            Event event = getEvent(selectedArticle);
            List<Map<String, Object>> registrationRecordsToProcess = getRegistrationRecordsLinkedToSelectedArticle(
                    selectedArticle, event, registrationCache);

            totalCount = registrationRecordsToProcess.size();
            if (registrationRecordsToProcess.size() == 0) {
                status = nodata;
                processedCount = totalCount;
                fireStateChanged();
                return status;
            }
            Map<String, List<String>> webinarKeyCache = new HashMap<>();
            Map<Long, Map<String, String>> userAttributeCache = new HashMap<>();

            //Create local session because the servlet session will close after call to endpoint is completed
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))){

                StringBuilder header = new StringBuilder("event,registration,start date,topic,type,email,firstName,lastName,webinarProvider,registrationStatus,remarks");
                for (KeycloakUtils.BILLING_ATTRIBUTES value : KeycloakUtils.BILLING_ATTRIBUTES.values()) {
                    header.append(',');
                    header.append(value.name());
                }
                writer.println(header);
                String finalEventTitle = event.getTitle();
                registrationRecordsToProcess.forEach(recordObjects ->{

                    ++processedCount;
                    Long resourcePrimaryKey = (Long) recordObjects.get("resourcePrimaryKey");
                    statusMessage = "procession resourcePrimaryKey=" + resourcePrimaryKey;
                    Registration matchingRegistration = registrationCache.get(resourcePrimaryKey);

                    User matchingUser = null;
                    try {
                        matchingUser = UserLocalServiceUtil.getUser((Long) recordObjects.get("userId"));
                    } catch (PortalException e) {
                        //
                    }
                    writeRecord(writer, recordObjects, finalEventTitle, matchingRegistration, matchingUser,
                            userAttributeCache, webinarKeyCache, locale);
                    if (Thread.interrupted()){
                        throw new RuntimeException("Thread interrupted");
                    }

                });

                status = available;

            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = terminated;
                statusMessage = errorMessage;
            }

            if (status == available) {
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }

            if (deleteOnCompletion) {
                deleteSelectedArticle(selectedArticle);
            }
        } catch (IOException | PortalException e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
        fireStateChanged();

        return status;
    }

    private List<Map<String, Object>> getRegistrationRecordsLinkedToSelectedArticle(DsdArticle selectedArticle, Event event,
                                                                                    Map<Long, Registration> registrationCache) throws PortalException {

        if (selectedArticle instanceof Event){
            List<Registration> eventRegistrations = event.getRegistrations(locale);
            eventRegistrations.forEach(registration -> registrationCache.put(registration.getResourceId(), registration));
        } else if (selectedArticle instanceof Registration) {
            registrationCache.put(selectedArticle.getResourceId(), (Registration) selectedArticle);
        } else {
            return Collections.emptyList();
        }
        List<Map<String, Object>> registrations = dsdSessionUtils.getRegistrations(event);
        if (selectedArticle == event) return registrations; //no need to filter

        //For single registration filter our unwanted records
        long resourceId = selectedArticle.getResourceId();
        List<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> record : registrations) {
            if ((Long)record.get("resourcePrimaryKey") != resourceId) continue;
            filtered.add(record);
        }
        return filtered;
    }

    private void deleteSelectedArticle(DsdArticle dsdArticle) {

        statusMessage = "deleting registrations for " + dsdArticle.getTitle();
        if (dsdArticle instanceof Event) {
            logger.info(String.format("Deleting registration records for Event %s (%s)", dsdArticle.getTitle(), dsdArticle.getArticleId()) );
            dsdSessionUtils.deleteEventRegistrations(dsdArticle.getGroupId(), dsdArticle.getResourceId());
        } else if (dsdArticle instanceof Registration){
            logger.info(String.format("Deleting registration records for Registration %s (%s)", dsdArticle.getTitle(), dsdArticle.getArticleId()) );
            dsdSessionUtils.deleteRegistrationsFor((Registration) dsdArticle);
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
        throw new PortalException("Could not find event for id " + articleId);
    }

    private void writeRecord(PrintWriter writer, Map<String, Object> record, String eventTitle,
                             Registration dsdRegistration, User user,
                             Map<Long, Map<String, String>> userAttributeCache, Map<String, List<String>> courseRegistrationsCache, Locale locale) {

        StringBuilder line = new StringBuilder();
        writeField(line, eventTitle);
        if (dsdRegistration == null) {
            writeField(line, (String) record.get("resourcePrimaryKey"));
        } else {
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
            writeField(line, (String) record.get("userId"));
            writeField(line,null);
            writeField(line,null);
        } else {
            writeField(line, user.getEmailAddress());
            writeField(line, user.getFirstName());
            writeField(line, user.getLastName());
        }
        String userPreferencesValue = (String) record.get("userPreferences");
        if (user == null){
            writeField(line, userPreferencesValue);
        } else {
            Map<String, String> userPreferences = Collections.emptyMap();
            try {
                userPreferences = JsonContentUtils.parseJsonToMap(userPreferencesValue);
            } catch (JSONException e) {
                logger.error(String.format("Invalid userPreferences '%s': %s", record.get("userPreferences"), e.getMessage()));
                line.append(','); //empty remarks
                Arrays.stream(KeycloakUtils.BILLING_ATTRIBUTES.values()).iterator().forEachRemaining(billing_attributes -> line.append(',')); //empty billing info
            }
            writeWebinarInfo(line, user, dsdRegistration, courseRegistrationsCache, userPreferences);
            writeField(line, userPreferences.get("remarks"));
            BillingInfo billingInfo = getBillingInfo(userPreferences);
            writeBillingInfo(line, billingInfo, user, userAttributeCache);
        }
        writer.println(line);
    }

    private void writeField(StringBuilder line, String value) {
        if (value != null) {
            if (value.contains("\n")) {
                value = value.replaceAll("\n", " ");
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

        if (!webinarUtilsFactory.isWebinarSupported(registration)) {
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
            line.append(','); //webinarProvider
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

    private BillingInfo getBillingInfo(Map<String, String> propertyMap) {

        BillingInfo billingInfo = new BillingInfo();
        //Write billing information.
        for (KeycloakUtils.BILLING_ATTRIBUTES key : KeycloakUtils.BILLING_ATTRIBUTES.values()) {
            String value = propertyMap.get(key.name());
            if (value != null) billingInfo.setAttribute(key, value);
        }
        return billingInfo;
    }

    private void writeBillingInfo(StringBuilder line, BillingInfo billingInfo, User user, Map<Long, Map<String, String>> userAttributeCache){

        Map<String, String> userAttributes = userAttributeCache.get(user.getUserId());
        if (billingInfo.isUseOrganization() && userAttributes == null){
            try {
                userAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
            } catch (Exception e) {
                logger.error(String.format("Cannot find attributes for DSD user %d: %s", user.getUserId(), e.getMessage()));
                userAttributes = new HashMap<>();
            }
            userAttributeCache.put(user.getUserId(), userAttributes);
        }
        //Write billing information. If no billing info then get values from user attributes
        for (KeycloakUtils.BILLING_ATTRIBUTES key : KeycloakUtils.BILLING_ATTRIBUTES.values()) {
            String billingAttribute = billingInfo.getAttribute(key);
            if (billingAttribute == null) {
                billingAttribute = userAttributes.get(BillingInfo.getCorrespondingUserAttributeKey(key).name());
            }
            writeField(line, billingAttribute);
        }
    }

}
