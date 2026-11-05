package nl.deltares.portal.model.subscriptions;

import java.util.Objects;

public class SubscriptionSelection {

    private final String id;
    private final String name;
    private final String description;

    private boolean selected;
    private boolean consentGroup;

    public SubscriptionSelection(String id, String name) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.consentGroup = false;
    }

    public SubscriptionSelection(String id, String name, String description, boolean consentGroup) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.consentGroup = consentGroup;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isConsentGroup() {
        return consentGroup;
    }

    public void setConsentGroup(boolean consentGroup) {
        this.consentGroup = consentGroup;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionSelection that = (SubscriptionSelection) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
