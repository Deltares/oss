package nl.deltares.tableview.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DisplayRegistration implements Comparable<DisplayRegistration> {

    final private long id;
    final private String email;
    final private String eventName;
    final private String registrationName;
    private final String preferences;
    private final Date startTime;
    private final Date endTime;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public DisplayRegistration(long registrationId, String email, String eventName, String registrationName, String preferences,
                               Date startTime, Date endTime) {
        this.id = registrationId;
        this.email = email;
        this.eventName = eventName;
        this.registrationName = registrationName;
        this.preferences = preferences;
        this.startTime = startTime;
        this.endTime = endTime;

        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public long getId() {
        return id;
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

    @Override
    public int compareTo(DisplayRegistration o) {
        return Long.compare(id, o.id);
    }
}
