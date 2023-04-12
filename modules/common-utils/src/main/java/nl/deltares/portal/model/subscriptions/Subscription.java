package nl.deltares.portal.model.subscriptions;

public class Subscription {

    private final String id;
    private final String name;

    private boolean selected;

    public Subscription(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
