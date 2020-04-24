package nl.deltares.dsd.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Building {

    private final String name;
    private final Location location;
    private List<Room> rooms = new ArrayList<>();

    public Building(String name, Location location) {
        this.name = name;
        this.location = location;
        this.location.addBuilding(this);
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public List<Room> getRooms(){
        return Collections.unmodifiableList(rooms);
    }

}
