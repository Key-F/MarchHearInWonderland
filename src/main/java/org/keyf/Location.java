package org.keyf;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.keyf.Colors.*;

public class Location {

    public enum Direction {
        north, east, south, west,
    }

    private String name;
    private List<Item> items;
    private List<Character> npcs;
    private boolean isSafeHouse;
    public HashMap<Direction, Location> exits;
    private List<Item> itemsNeededForAVisit;


    Location(File file) {
        try {
            InputStream is = new FileInputStream(file);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject LocObj = new JSONObject(jsonTxt);
            this.name = (String) LocObj.get("Name");
            this.isSafeHouse = (boolean) LocObj.get("SafeHouse");
            if (this.isSafeHouse) Wonderland.safeHouse = this;
            this.items = new ArrayList<>();
            this.npcs = new ArrayList<>();

            for (Object obj : (JSONArray) LocObj.get("Characters")) {
                if (obj.equals("")) continue;
                this.npcs.add(new Character((String) obj));
            }
            for (Object obj : (JSONArray) LocObj.get("Items")) {
                if (obj.equals("")) continue;
                this.items.add(new Item((String) obj));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printLocation() {
        System.out.println("You are in the: " + name);
    }

    public void printInfo() {
        // info about location
        printLocation();
        // info about characters
        if (npcs.isEmpty()) System.out.println("There're no npcs in this location");
        else System.out.println("NPC's in this location:");
        for (Character c : npcs) {
            System.out.println("Here you see " + c.getName());
        }
        // info about items
        if (items.isEmpty()) System.out.println("There're no items in this location");
        else System.out.println("Items in this location:");
        for (Item item : items) {
            System.out.println(item.getNameFullName());
        }
        // info about directions
        for (Map.Entry<Direction, Location> entry :
                exits.entrySet()) {
            System.out.println("To the " + ANSI_RED + entry.getKey().toString() +
                    ANSI_GREEN +
                    " " + entry.getValue().getName() +
                    ANSI_RESET + " is located");
        }
        System.out.println();
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

    public boolean hasItem(String item) {
        for (Item i : items) {
            if (i.getNameFullName().toLowerCase().contains(item.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public Item pickUpItem(String name) {
        for (Item i : this.items) {
            if (i.getNameFullName().toLowerCase().contains(name)) {
                items.remove(i);
                System.out.println("You picked up " + i.getNameFullName());
                return i;
            }
        }
        System.out.println("Item not found " + name);
        return null;
    }

    public Item getItem(String name) {
        for (Item i : items) {
            if (i.getNameFullName().toLowerCase().contains(name.toLowerCase())) {
                return i;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        items.add(item);
    }

}
