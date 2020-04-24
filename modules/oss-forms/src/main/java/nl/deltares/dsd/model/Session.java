package nl.deltares.dsd.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Session extends Reservation {

    private List<Person> presenters = new ArrayList<>();
    private Room room;
    private List<Reservation> children = new ArrayList<>();

    public Room getRoom(){
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Person> getPresenters(){
        return Collections.unmodifiableList(presenters);
    }

    public void addPresenter(Person person){
        presenters.add(person);
    }

    public boolean hasChildren(){
        return children.size() > 0;
    }

    public List<Reservation> getChildren(){
        return Collections.unmodifiableList(children);
    }

    public void addChild(Reservation reservation){
        if (reservation == this){
            throw new  IllegalArgumentException("Child reservation cannot be same as parrent!" );
        }
        children.add(reservation);
    }
}
