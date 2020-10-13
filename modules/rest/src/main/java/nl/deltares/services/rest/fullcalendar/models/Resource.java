package nl.deltares.services.rest.fullcalendar.models;

import java.io.Serializable;
import java.util.Objects;

public class Resource implements Serializable {
    private String id;
    private String title;
    private String eventColor;
    private String building;
    private BusinessHours businessHours = new BusinessHours();
    private int capacity;

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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }


    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
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
