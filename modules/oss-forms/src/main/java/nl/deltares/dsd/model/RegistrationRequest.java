package nl.deltares.dsd.model;

import nl.deltares.portal.model.impl.*;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRequest {

    private final List<Registration> childRegistrations = new ArrayList<>();
    private Registration registration;
    private String eventName;
    private BillingInfo billingInfo;
    private String baseUrl;

    public void setRegistration(Registration registration){
        this.registration = registration;
    }

    public Registration getRegistration() {
        return registration;
    }

    public List<Registration> getChildRegistrations() {
        return childRegistrations;
    }

    public String getLocation() {
        if (registration instanceof SessionRegistration){
            Room room = ((SessionRegistration) registration).getRoom();
            return room.getTitle();
        } else if (registration instanceof DinnerRegistration){
            Location location = ((DinnerRegistration) registration).getRestaurant();
            location.getTitle();
        }
        return null;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
    }
    public String getArticleUrl() {
        return baseUrl + registration.getJournalArticle().getUrlTitle();
    }

    public void setBillingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }
}
