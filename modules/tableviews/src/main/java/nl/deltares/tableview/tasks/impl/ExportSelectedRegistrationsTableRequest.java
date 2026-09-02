package nl.deltares.tableview.tasks.impl;

import com.liferay.journal.model.JournalArticle;
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
import nl.deltares.tableview.model.DisplayRegistration;
import nl.deltares.tableview.utils.RegistrationUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class ExportSelectedRegistrationsTableRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(ExportSelectedRegistrationsTableRequest.class);

    private final Group group;
    private final boolean findByUser;
    private final String filterEmail;
    private final long filterEventId;
    private final long filterRegistrationId;

    private final DsdJournalArticleUtils dsdJournalArticleUtils;

    public ExportSelectedRegistrationsTableRequest(String id, String filterEmailValue, long filterEventValue,
                                                   long filterRegistrationValue, ThemeDisplay themeDisplay,
                                                   DsdJournalArticleUtils dsdJournalArticleUtils) throws IOException {
        super(id, themeDisplay.getUserId());
        this.group = themeDisplay.getSiteGroup();
        this.filterEmail = filterEmailValue;
        this.filterEventId = filterEventValue;
        this.filterRegistrationId = filterRegistrationValue;
        this.findByUser = Validator.isEmailAddress(filterEmailValue);
        this.dsdJournalArticleUtils = dsdJournalArticleUtils;
    }

    @Override
    public STATUS call() {
        if (getStatus() == AVAILABLE) return status;

        if (!findByUser && filterEventId == 0 && filterRegistrationId == 0) {
            status = NODATA;
            return status;
        }
        statusMessage = "starting exporting registrations for filter";

        status = RUNNING;
        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                exportSelectedRecords(writer);
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
        } finally {
            if (status == RUNNING || status == PENDING) {
                status = TERMINATED;
            }
        }
        fireStateChanged();

        return status;
    }

    private void exportSelectedRecords(PrintWriter writer) {

        long companyId = group.getCompanyId();
        long groupId = group.getGroupId();

        User filterUser = null;
        Map<Long, JournalArticle> articleCache = new HashMap<>();
        if (findByUser) {
            filterUser = UserLocalServiceUtil.fetchUserByEmailAddress(companyId, filterEmail);
            if (filterUser == null) {
                totalCount = 0;
            } else {
                totalCount = RegistrationLocalServiceUtil.getUserRegistrationsCount(groupId, filterUser.getUserId());
            }
        } else {
            totalCount = RegistrationLocalServiceUtil.getEventRegistrationsCount(groupId, filterEventId);
        }

        if (totalCount == 0) {
            status = NODATA;
            setProcessCount(0);
            return;
        }

        writer.println("event,registration,email,start,end");

        int start = 0;
        int end = 100;

        for (int i = 0; i < totalCount; ) {
            if (status == TERMINATED) return;
            final List<Registration> registrations;

            if (filterUser != null) {
                registrations = RegistrationLocalServiceUtil.getUserRegistrations(groupId, filterUser.getUserId(), start, end);
            } else if (filterRegistrationId > 0) {
                registrations = RegistrationLocalServiceUtil.getArticleRegistrations(groupId, filterRegistrationId, start, end);
            } else if (filterEventId > 0) {
                registrations = RegistrationLocalServiceUtil.getEventRegistrations(groupId, filterEventId, start, end);
            } else {
                registrations = Collections.emptyList();
            }
            List<DisplayRegistration> displayRegistrations = RegistrationUtils.convertToDisplayValues(registrations,
                    articleCache, dsdJournalArticleUtils);

            displayRegistrations.forEach(registration -> {
                if (status == TERMINATED) return;
                incrementProcessCount(1);

                writer.println(String.format("%s,%s,%s,%s,%s",
                        registration.getEventName(), registration.getRegistrationName(), registration.getEmail(),
                        registration.getStartTime(), registration.getEndTime()));

                if (Thread.interrupted()) {
                    status = TERMINATED;
                    errorMessage = String.format("Thread 'ExportSelectedRegistrationsRequest' with id %s is interrupted!", id);
                }
            });
            i += registrations.size();
            start = end;
            end += 100;
        }

    }

}
