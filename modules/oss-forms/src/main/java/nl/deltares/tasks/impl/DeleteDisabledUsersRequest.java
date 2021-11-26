package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class DeleteDisabledUsersRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DeleteDisabledUsersRequest.class);
    private final AdminUtils adminUtils;
    private final long companyId;
    private final long disableTimeAfter;

    public DeleteDisabledUsersRequest(String id, long companyId, long disableTimeAfter, long currentUserId, AdminUtils adminUtils) throws IOException {
        super(id, currentUserId);
        this.companyId = companyId;
        this.adminUtils = adminUtils;
        this.disableTimeAfter = disableTimeAfter;
    }

    @Override
    public STATUS call() {

        if (status == available || status == nodata) return status;

        init();
        status = running;

        try {
            File tempFile = new File(getExportDir(), id + ".tmp");
            if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

            //Create local session because the servlet session will close after call to endpoint is completed
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))){

                final List<Group> sites = GroupLocalServiceUtil.getGroups(companyId, -1, true);

                int start = 0;
                final int max = 100;
                List<User> disabledUsers = adminUtils.getDisabledUsers(companyId, start, max, disableTimeAfter, writer);
                if (disabledUsers.isEmpty()){
                    writer.println("No users found for deletion in Liferay database.");
                }
                while (disabledUsers.size() > 0) {
                    start += max;

                    for (User user : disabledUsers) {
                        if (user.getUserId() == currentUserId) {
                            writer.printf("Error: Not allowed to delete yourself %s", user.getEmailAddress());
                            continue;
                        }
                        sites.forEach(site ->
                                adminUtils.deleteUserAndRelatedContent(site.getGroupId(), user, writer, false));

                        processedCount++;
                        if (Thread.interrupted()) {
                            status = terminated;
                            errorMessage = "Thread interrupted";
                            break;
                        }
                    }
                    if (status == terminated) break;
                    disabledUsers = adminUtils.getDisabledUsers(companyId, start, max, disableTimeAfter, writer);
                }
                if (status != terminated) {
                    status = available;
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
                logger.warn("Error serializing csv content: %s", e);
                status = terminated;
            }
            if (status == available) {
                this.dataFile = new File(getExportDir(), id + ".data");
                if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                Files.move(tempFile.toPath(), dataFile.toPath());
            }
        } catch (IOException e) {
            errorMessage = e.getMessage();
            status = terminated;
        }
        fireStateChanged();

        return status;

    }

}
