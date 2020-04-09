package nl.deltares.services.rest.fullcalendar.models;

import java.io.Serializable;

public class Event implements Serializable {
    private String resourceId;
    private String id;
    private String start;
    private String end;
    private String url;

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
