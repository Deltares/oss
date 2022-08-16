package nl.deltares.portal.model.keycloak;

import java.util.Arrays;
import java.util.List;

public class KeycloakMailing {

    public static List<String> frequencies;
    public static List<String> deliveryTypes;

    static {
        frequencies = Arrays.asList("weekly", "monthly", "quarterly", "annually", "varying");
        deliveryTypes = Arrays.asList("e-mail", "post", "both");
    }
    private final String id;
    private final String name;
    private final String description;
    private final String[] languages;
    private final int frequency;
    private final String[] deliveries;

    private boolean selected;
    private String selectedLanguage = "en";
    private String selectedDelivery = "e-mail";

    public KeycloakMailing(String id, String name, String description, String[] languages, int frequency, int delivery) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.languages = languages;
        this.frequency = frequency;

        if (delivery == 2){
            this.deliveries = deliveryTypes.toArray(new String[0]);
        } else if (delivery == 0 || delivery == 1) {
            this.deliveries = new String[]{deliveryTypes.get(delivery)};
        } else {
            this.deliveries = new String[]{deliveryTypes.get(0)};
        }

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

    public String[] getLanguages() {
        return languages;
    }

    public String getFrequency(){
        return frequencies.get(frequency);
    }
    public String[] getDeliveries() {
        return deliveries;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        if (selectedLanguage == null) return;
        this.selectedLanguage = selectedLanguage;
    }

    public String getSelectedDelivery() {
        return selectedDelivery;
    }

    public void setSelectedDelivery(String selectedDelivery) {
        if (selectedDelivery == null) return;
        this.selectedDelivery = selectedDelivery;
    }

    public void setSelectedDeliveryType(int selectedDelivery) {
        if (selectedDelivery < 0 || selectedDelivery >= deliveries.length ) return;
        this.selectedDelivery = deliveryTypes.get(selectedDelivery);
    }
}
