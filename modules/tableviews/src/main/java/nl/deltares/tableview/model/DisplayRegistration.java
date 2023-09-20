package nl.deltares.tableview.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DisplayRegistration implements Comparable<DisplayRegistration> {

    final private long recordId;
    final private String email;
    final private String eventName;
    final private String registrationName;
    private final String preferences;
    private final Date startTime;
    private final Date endTime;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final long eventResourceId;
    private final long resourceId;

    public DisplayRegistration(long recordId, long resourceId, long eventResourceId, String email, String eventName, String registrationName, String preferences,
                               Date startTime, Date endTime) {
        this.recordId = recordId;
        this.resourceId = resourceId;
        this.eventResourceId = eventResourceId;
        this.email = email;
        this.eventName = eventName;
        this.registrationName = registrationName;
        this.preferences = preferences;
        this.startTime = startTime;
        this.endTime = endTime;

        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public long getResourceId() {
        return resourceId;
    }

    public String getEmail() {
        return email;
    }

    public String getPreferences() {
        return preferences;
    }

    public String getEventName() {
        return eventName;
    }

    public String getRegistrationName() {
        return registrationName;
    }

    public String getStartTime() {
        return format.format(startTime);
    }

    public String getEndTime() {
        return format.format(endTime);
    }

    public long getEventResourceId() {
        return eventResourceId;
    }

    public long getRecordId() {
        return recordId;
    }

    @Override
    public int compareTo(DisplayRegistration o) {
        return Long.compare(recordId, o.recordId);
    }
}
