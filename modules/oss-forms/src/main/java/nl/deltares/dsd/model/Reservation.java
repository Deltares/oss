package nl.deltares.dsd.model;

import java.util.ArrayList;
import java.util.List;

public class Reservation {

    private List<Reservation> childReservations = new ArrayList<>();
    private String eventName;
    private String name;
    private long startTime;
    private long endTime;
    private String type;
    private double price;
    private int capacity;
    private String location;
    private String bannerUrl;
    private String articleUrl;

    public List<Reservation> getChildReservations() {
        return childReservations;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public void setBannerUrl(String bannerURL) {
        this.bannerUrl = bannerURL;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }
}
