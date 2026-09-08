package nl.deltares.tableview.tasks.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.tableview.utils.RegistrationUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DeletedSelectedRegistrationsRequest extends AbstractDataRequest {


    private static final Log logger = LogFactoryUtil.getLog(DeletedSelectedRegistrationsRequest.class);

    private final List<String> selectedRecords;
    private final Group group;
    private final boolean findByUser;
    private final String filterEmail;
    private final long filterEventId;
    private final long filterRegistrationId;
    private final DsdJournalArticleUtils dsdJournalArticleUtils;

    public DeletedSelectedRegistrationsRequest(String id, List<String> recordIds, long userId, DsdJournalArticleUtils dsdJournalArticleUtils) throws IOException {
        super(id, userId);
        this.selectedRecords = recordIds;
        this.dsdJournalArticleUtils = dsdJournalArticleUtils;
        this.filterEmail = null;
        this.filterEventId = 0;
        this.filterRegistrationId = 0;
        this.findByUser = false;
        this.group = null;
    }

    public DeletedSelectedRegistrationsRequest(String id, String filterEmailValue, long filterEventValue,
                                               long filterRegistrationValue, ThemeDisplay themeDisplay,
                                               DsdJournalArticleUtils dsdJournalArticleUtils) throws IOException {
        super(id, themeDisplay.getUserId());
        this.selectedRecords = Collections.emptyList();
        this.dsdJournalArticleUtils = dsdJournalArticleUtils;
        this.filterEmail = filterEmailValue;
        this.filterEventId = filterEventValue;
        this.filterRegistrationId = filterRegistrationValue;
        this.findByUser = Validator.isEmailAddress(filterEmailValue);
        this.group = themeDisplay.getSiteGroup();
    }

    @Override
    public STATUS call() {
        if (getStatus() == AVAILABLE) return status;
        status = RUNNING;
        statusMessage = "start deleting...";
        init();
        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                deleteSelectedRecords(writer);
                if (status != TERMINATED) {
                    status = AVAILABLE;
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = TERMINATED;
            }
            if (status == AVAILABLE) {
                this.dataFile = new File(getExportDir(), id + ".csv");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = TERMINATED;
        }
        fireStateChanged();

        return status;
    }

    private void deleteSelectedRecords(PrintWriter writer) {

        writer.println("event,registration,email,start,end");
        if (!selectedRecords.isEmpty()) {
            deleteBySelectedIds(writer);
        } else  {
            deleteByFilterSelection(writer);
        }
    }

    private void deleteByFilterSelection(PrintWriter writer) {

        long[] filterUerId = new long[1];
        if (findByUser) {
            User filterUser = UserLocalServiceUtil.fetchUserByEmailAddress(group.getCompanyId(), filterEmail);
            if (filterUser == null) {
                status = NODATA;
                errorMessage = String.format("User not found for email %s", filterEmail);
                setProcessCount(totalCount);
                return;
            }
            filterUerId[0] = filterUser.getUserId();
        }

        totalCount = RegistrationUtils.getTotalRegistrationsCountForFilterSelection(group, filterUerId[0], filterEventId, filterRegistrationId);
        if (totalCount == 0) {
            status = NODATA;
            setProcessCount(0);
            return;
        }

        long groupId = group.getGroupId();
        HashMap<Long, JournalArticle> cachedArticles = new HashMap<>();
        for (int i = 0; i < totalCount; ) {
            if (status == TERMINATED) return;
            final List<Registration> registrations = RegistrationUtils.getRegistrationsForFilterSelection(groupId,
                    filterUerId[0], filterEventId, filterRegistrationId, 0, 100);
            registrations.forEach(registration -> {
                try {
                    RegistrationLocalServiceUtil.deleteRegistration(registration);
                    writeToLogMessage(writer, registration, cachedArticles);
                } finally {
                    incrementProcessCount(1);
                }

                if (Thread.interrupted()) {
                    status = TERMINATED;
                    errorMessage = String.format("Thread 'ExportSelectedRegistrationsRequest' with id %s is interrupted!", id);
                }
            });
            i += registrations.size();
        }

    }

    private void deleteBySelectedIds(PrintWriter writer) {

        totalCount = selectedRecords.size();

        HashMap<Long, JournalArticle> cachedArticles = new HashMap<>();
        selectedRecords.forEach(id -> {
            if (status == TERMINATED) return;
            try {
                final Registration registration = RegistrationLocalServiceUtil.deleteRegistration(Long.parseLong(id));
                writeToLogMessage(writer, registration, cachedArticles);
            } catch (PortalException e) {
                writer.println(String.format("Failed to delete record %s: %s", id, e.getMessage()));
            } finally {
                incrementProcessCount(1);
            }
            if (Thread.interrupted()) {
                status = TERMINATED;
                errorMessage = String.format("Thread 'DeletedSelectedDownloadsRequest' with id %s is interrupted!", id);
            }
        });
    }

    private void writeToLogMessage(PrintWriter writer, Registration registration, HashMap<Long, JournalArticle> cachedArticles) {

        final User user = UserLocalServiceUtil.fetchUser(registration.getUserId());
        String email;
        if (user == null) {
            email = String.valueOf(registration.getUserId());
        } else {
            email = user.getEmailAddress();
        }
        JournalArticle eventArticle = RegistrationUtils.getArticleByResourcePrimaryKey(registration.getEventResourcePrimaryKey(), cachedArticles, dsdJournalArticleUtils);
        JournalArticle registrationArticle = RegistrationUtils.getArticleByResourcePrimaryKey(registration.getResourcePrimaryKey(), cachedArticles, dsdJournalArticleUtils);

        final Date startDate = registration.getStartTime();
        final Date endDate = registration.getEndTime();
        writer.println(String.format("%s,%s,%s,%s,%s",
                eventArticle == null ? registration.getEventResourcePrimaryKey() : eventArticle.getTitle(),
                registrationArticle == null ? registration.getResourcePrimaryKey() : registrationArticle.getTitle(),
                email, startDate, endDate));
    }
}
