package nl.deltares.dsd.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Location {

    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String website;
    private List<Building> buildings = new ArrayList<>();

    public List<Building> getBuildings(){
        return Collections.unmodifiableList(buildings);
    }

    public void addBuilding(Building building){
        buildings.add(building);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
