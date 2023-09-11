package nl.worth.deltares.tasks.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static nl.deltares.tasks.DataRequest.STATUS.*;

public class UserPortraitsRequest extends AbstractDataRequest {

    private static final int DEFAULT_PORTRAITS = 12;

    private final SecureRandom random = new SecureRandom();

    private static final Log LOG = LogFactoryUtil.getLog(UserPortraitsRequest.class);
    private final ThemeDisplay themeDisplay;

    public UserPortraitsRequest(String id, long currentUserId, ThemeDisplay themeDisplay) throws IOException {
        super(id, currentUserId);
        this.themeDisplay = themeDisplay;
    }

    @Override
    public STATUS call() {
        if (getStatus() == available) return status;
        status = running;
        statusMessage = "Start UserPortraitRequest";
        init();

        totalCount = DEFAULT_PORTRAITS;

        try {
            JSONArray userPortraits = getRandomPortraits(themeDisplay);

            if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());

            if (userPortraits.length() > 0)
                writeResultsToFile(userPortraits, dataFile);

            status = available;

            statusMessage = String.format("%d user portraits have been processed.", getProcessedCount());
            LOG.info(statusMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            status = terminated;
        } finally {
            fireStateChanged();
        }
        return status;
    }

    private JSONArray getRandomPortraits(ThemeDisplay themeDisplay) {

        JSONArray portraitUrls = JSONFactoryUtil.createJSONArray();

        Set<User> usersWithPortraits = getRandomUsersWithPortrait();

        for (User user : usersWithPortraits) {
            try {
                String portraitURL = user.getPortraitURL(themeDisplay);
                portraitUrls.put(addUserId(portraitURL, user.getUserId()));
            } catch (PortalException e) {
                LOG.warn(String.format("Error getting portraitURL for user %s: %s", user.getEmailAddress(), e.getMessage()));
            }
        }
        return portraitUrls;
    }

    private String addUserId(String portraitURL, long userId) {
        return portraitURL + "&user_id=" + userId;
    }

    private Set<User> getRandomUsersWithPortrait() {

        //Retrieve only 1000 users to search for portraits. Start position is random.
        int allUserCount = UserLocalServiceUtil.getUsersCount();

        Set<User> usersWithPortrait = new HashSet<>();

        int iterations = 0;
        while (usersWithPortrait.size() < DEFAULT_PORTRAITS && iterations < 1000) {
            int startIndex = random.nextInt(allUserCount - 100);
            int endIndex = startIndex + 100;
            List<User> checkUsers = UserLocalServiceUtil.getUsers(startIndex, endIndex);

            for (User checkUser : checkUsers) {
                if (checkUser.getPortraitId() == 0 || usersWithPortrait.contains(checkUser)) continue;

                try {
                    Image image = ImageLocalServiceUtil.getImage(checkUser.getPortraitId());
                    if (image == null || image.getTextObj() == null) {
                        //This portraitId is not valid so remove it.
                        LOG.warn(String.format("Un-setting portrait %d for user %s", checkUser.getPortraitId(), checkUser.getScreenName()));
                        checkUser.setPortraitId(0);
                        UserLocalServiceUtil.updateUser(checkUser);
                    } else {
                        //Found a portrait so add it.
                        usersWithPortrait.add(checkUser);
                        incrementProcessCount(1);
                    }
                } catch (Exception e) {
                    LOG.warn(String.format("Error getting portrait %d for user %s: %s", checkUser.getPortraitId(), checkUser.getScreenName(), e.getMessage()));
                }

            }
            if (Thread.interrupted()) {
                status = terminated;
                errorMessage = String.format("Thread 'UserPortraitsRequest' with id %s is interrupted!", id);
                break;
            }
            iterations++;
        }
        return usersWithPortrait;
    }

    private void writeResultsToFile(JSONArray downloadLocations, File tempFile) throws IOException, JSONException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
            downloadLocations.write(writer);
            writer.flush();
        }
    }
}
