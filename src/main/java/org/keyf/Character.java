package org.keyf;

import java.util.List;

public class Character {

    private String name;
    private Location location;
    private List<Item> itemNeeded;
    private List<Item> itemWanted;

    public boolean isGoingToParty() {
        return isGoingToParty;
    }

    public void setGoingToParty(boolean goingToParty) {
        isGoingToParty = goingToParty;
    }

    private boolean isGoingToParty;


    public void say() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Item> getItemNeeded() {
        return itemNeeded;
    }

    public void setItemNeeded(List<Item> itemNeeded) {
        this.itemNeeded = itemNeeded;
    }
}
