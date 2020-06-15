package nl.deltares.services.rest.fullcalendar.models;

import java.io.Serializable;
import java.util.Objects;

public class Resource implements Serializable {
    private String id;
    private String title;
    private String eventColor;
    private BusinessHours businessHours = new BusinessHours();

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

    public BusinessHours getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(BusinessHours businessHours) {
        this.businessHours = businessHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return id.equals(resource.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
