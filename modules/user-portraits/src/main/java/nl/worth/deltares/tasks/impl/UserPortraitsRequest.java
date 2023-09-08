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

        //Retrieve only 1000 users to search for portraits. Start position is random.
        int allUserCount = UserLocalServiceUtil.getUsersCount();

        JSONArray portraitUrls = JSONFactoryUtil.createJSONArray();

        int maxIterations = 10;
        int currentIteration = 0;
        while (portraitUrls.length() < UserPortraitsRequest.DEFAULT_PORTRAITS && currentIteration < maxIterations) {
            currentIteration++;
            try {
                Set<User> usersWithPortraits = getRandomUsersWithPortrait(allUserCount);

                for (User user : usersWithPortraits) {
                    String portraitURL = user.getPortraitURL(themeDisplay);
                    portraitUrls.put(addUserId(portraitURL, user.getUserId()));
                    incrementProcessCount(1);
                }

                if (Thread.interrupted()) {
                    status = terminated;
                    errorMessage = String.format("Thread 'UserPortraitsRequest' with id %s is interrupted!", id);
                    break;
                }
            } catch (PortalException e) {
                LOG.error("Error retrieving portrait URLs", e);
            }
        }

        return portraitUrls;
    }

    private String addUserId(String portraitURL, long userId) {
        return portraitURL + "&user_id=" + userId;
    }

    private Set<User> getRandomUsersWithPortrait(int allUserCount) {

        Set<User> usersWithPortrait = new HashSet<>();

        int countUsersWithPortrait = 0;

        int startIndex;
        int endIndex;
        if (allUserCount < 1000) {
            startIndex = 0;
            endIndex = allUserCount;
        } else {
            startIndex = random.nextInt(allUserCount - 1000);
            endIndex = startIndex + 1000;
        }

        List<User> allUsers = UserLocalServiceUtil.getUsers(startIndex, endIndex);

        for (User user : allUsers) {
            if (countUsersWithPortrait > UserPortraitsRequest.DEFAULT_PORTRAITS) break; // we have enough portraits
            if (user.getPortraitId() != 0L) {

                try {
                    Image image = ImageLocalServiceUtil.getImage(user.getPortraitId());
                    if (image == null || image.getTextObj() == null) {
                        //This portraitId is not valid so remove it.
                        LOG.warn(String.format("Un-setting portrait %d for user %s", user.getPortraitId(), user.getScreenName()));
                        user.setPortraitId(0);
                        UserLocalServiceUtil.updateUser(user);
                    } else {
                        //Found a portrait so add it.
                        usersWithPortrait.add(user);
                        countUsersWithPortrait++;
                    }
                } catch (Exception e) {
                    LOG.warn(String.format("Error getting portrait %d for user %s: %s", user.getPortraitId(), user.getScreenName(), e.getMessage()));
                }
            }
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
