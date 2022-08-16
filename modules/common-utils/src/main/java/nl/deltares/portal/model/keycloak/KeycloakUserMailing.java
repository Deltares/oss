package nl.deltares.portal.model.keycloak;

public class KeycloakUserMailing {

    private final boolean selected;
    private final String id;
    private final String email;
    private final String mailingId;
    private final String language;
    private final int delivery;

    public KeycloakUserMailing(boolean selected, String id, String email, String mailingId, String language, int delivery) {
        this.selected = selected;
        this.id = id;
        this.email = email;
        this.mailingId = mailingId;
        this.language = language;
        this.delivery = delivery;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMailingId() {
        return mailingId;
    }

    public String getLanguage() {
        return language;
    }

    public int getDelivery() {
        return delivery;
    }
}
