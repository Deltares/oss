package nl.deltares.tableview.model;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.oss.download.model.Download;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.SessionRegistration;

import java.util.Date;


public class DisplayRegistration implements Comparable<DisplayRegistration> {

    final private long id;
    final private String email;
    final private String eventName;
    final private String registrationName;
    private final String preferences;

    public DisplayRegistration(long registrationId, String email, String eventName, String registrationName, String preferences) {
        this.id = registrationId;
        this.email = email;
        this.eventName = eventName;
        this.registrationName = registrationName;
        this.preferences = preferences;
    }

    public long getId(){
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPreferences() {return preferences; }

    public String getEventName() {
        return eventName;
    }

    public String getRegistrationName() {
        return registrationName;
    }

    @Override
    public int compareTo(DisplayRegistration o) {
        return Long.compare(id, o.id);
    }
}
