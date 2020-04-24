package nl.deltares.dsd.model;

public class Room {

    private final String name;
    private int capacity;
    private final Building building;

    public Room(String name, Building building) {
        this.name = name;
        this.building = building;
        this.building.addRoom(this);
    }

    public Building getBuilding(){
        return building;
    }

    public Location getLocation() {
        return building.getLocation();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
