package org.keyf;

import java.util.HashMap;
import java.util.List;

public class Location {

    enum Direction {
        North, East, South, West,
    }

    private Direction direction;
    private String name;
    private String description;
    private List<Item> items;
    private List<Character> npcs;
    private boolean isSafeHouse;
    public HashMap<Direction, Location> Entries;
    public HashMap<Direction, Location> Exits;

    public void storeItem(Item item) {
        if (!isSafeHouse) {
            System.out.println("It's not safe to store an item here");
            return;
        }
        items.add(item);
        System.out.println("You stored " + item.getName());
    }

}
