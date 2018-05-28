package com.worth.deltares.subversion.model;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
public class Activity {

    private String latitude;
    private String longitude;
    private String location;
    private String message;
    private String type;

    public Activity(String type) { this.type = type; }

    public String getLatitude() { return this.latitude; }
    public String getLongitude() { return this.longitude; }
    public String getLocation() { return this.location; }
    public String getMessage() { return this.message; }
    public String getType() { return this.type; }

    public void setLatitude(String latitude) { this.latitude = latitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
    public void setLocation(String location) { this.location = location; }
    public void setMessage(String message) { this.message = message; }
    public void setType(String type) { this.type = type; }
}