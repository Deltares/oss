package nl.deltares.services.rest.registration.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationDetails implements Serializable {

    private String articleId;
    private String title;
    private long groupId;
    private long userId;
    private String preferences;
    private String eventArticleId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public void setEventArticleId(String eventArticleId) {
        this.eventArticleId = eventArticleId;
    }

    public String getEventArticleId() {
        return eventArticleId;
    }
}
