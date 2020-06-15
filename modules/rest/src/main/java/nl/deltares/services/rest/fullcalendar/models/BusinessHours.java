package nl.deltares.services.rest.fullcalendar.models;

import java.io.Serializable;

public class BusinessHours implements Serializable {

    private String startTime = "08:00";
    private String endTime = "18:00";
    private int[] daysOfWeek = new int[]{1, 2 ,3, 4, 5};

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(int[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}
