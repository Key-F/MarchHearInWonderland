package org.keyf;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Location {

    enum Direction {
        north, east, south, west,
    }

    private String name;
    private List<Item> items;
    private List<Character> npcs;
    private boolean isSafeHouse;
    public HashMap<Direction, Location> exits;
    private List<Item> itemsNeededForAVisit;

    public void storeItem(Item item) {
        if (!isSafeHouse) {
            System.out.println("It's not safe to store an item here");
            return;
        }
        items.add(item);
        System.out.println("You stored " + item.getName());
    }

    Location(File file) {
        try {
            InputStream is = new FileInputStream(file);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject LocObj = new JSONObject(jsonTxt);
            this.name = (String) LocObj.get("Name");
            this.isSafeHouse = (boolean) LocObj.get("SafeHouse");
            this.items = new ArrayList<>();
        }
        catch(Exception e) {};
    }

    public void generateConnections(File file) {
        try {
            InputStream is = new FileInputStream(file);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject LocObj = new JSONObject(jsonTxt);
            this.exits = new HashMap<>();
            if (!(LocObj.get("North")).equals("")) {
                exits.put(Direction.north, Wonderland.getLocation((String) (LocObj.get("North"))));
            }
            if (!(LocObj.get("West")).equals("")) {
                exits.put(Direction.west, Wonderland.getLocation((String) (LocObj.get("West"))));
            }
            if (!(LocObj.get("East")).equals("")) {
                exits.put(Direction.east, Wonderland.getLocation((String) (LocObj.get("East"))));
            }
            if (!(LocObj.get("South")).equals("")) {
                exits.put(Direction.south, Wonderland.getLocation((String) (LocObj.get("South"))));
            }
        }
        catch(Exception e) {};
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Character> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<Character> npcs) {
        this.npcs = npcs;
    }

    public boolean isSafeHouse() {
        return isSafeHouse;
    }

    public void setSafeHouse(boolean safeHouse) {
        isSafeHouse = safeHouse;
    }

    public HashMap<Direction, Location> getExits() {
        return exits;
    }

    public void setExits(HashMap<Direction, Location> exits) {
        this.exits = exits;
    }

    public List<Item> getItemsNeededForAVisit() {
        return itemsNeededForAVisit;
    }

    public void setItemsNeededForAVisit(List<Item> itemsNeededForAVisit) {
        this.itemsNeededForAVisit = itemsNeededForAVisit;
    }

    public Item getItem(String name) {
        for (Item i : this.items) {
            if (i.getName().equals(name)) {
                items.remove(i);
                return i;
            }
        }
        return null;
    }


}
