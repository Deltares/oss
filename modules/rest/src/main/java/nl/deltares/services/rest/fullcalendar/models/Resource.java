package nl.deltares.services.rest.fullcalendar.models;

import java.io.Serializable;

public class Resource implements Serializable {
    private String id;
    private String title;
    private String eventColor;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setEventColor(String eventColor) {
        this.eventColor = eventColor;
    }

    public String getEventColor() {
        return eventColor;
    }
}
