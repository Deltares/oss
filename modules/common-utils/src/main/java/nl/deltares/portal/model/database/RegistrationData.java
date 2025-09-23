package nl.deltares.portal.model.database;

import nl.deltares.portal.utils.Period;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationData {

    long registrationRecordId;
    long companyId;
    long groupId;
    long resourceId;
    long parentResourceId;
    long eventResourceId;
    long userId;
    long authorId;
    long registrationTime;
    List<Period> periods = new ArrayList<Period>();
    Map<String, String> attributes = new HashMap<String, String>();

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public long getParentResourceId() {
        return parentResourceId;
    }

    public long getRegistrationRecordId() {
        return registrationRecordId;
    }

    public void setRegistrationRecordId(long registrationRecordId) {
        this.registrationRecordId = registrationRecordId;
    }

    public void setParentResourceId(long parentResourceId) {
        this.parentResourceId = parentResourceId;
    }

    public long getEventResourceId() {
        return eventResourceId;
    }

    public void setEventResourceId(long eventResourceId) {
        this.eventResourceId = eventResourceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(long registrationTime) {
        this.registrationTime = registrationTime;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public boolean addPeriod(Period period) {
        return this.periods.add(period);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String putAttribute(String key, String value) {
        return this.attributes.put(key, value);
    }
}
