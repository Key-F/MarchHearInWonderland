package org.keyf;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    public List<Item> items;

    public void addItem(Item item) {
        items.add(item);
    }

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public boolean hasItem(String item) {
        for (Item i : items) {
            if (i.getNameFullName().toLowerCase().contains(item.toLowerCase().trim())) {
                return true;
            }
        }
        return false;
    }

    public Item getItemByName(String item) {
        for (Item i : items) {
            if (i.getNameFullName().toLowerCase().contains(item.toLowerCase())) {
                return i;
            }
        }
        return null;
    }

    public void dropItem(String item) {
        for (Item i : items) {
            if (i.getNameFullName().toLowerCase().contains(item)) {
                items.remove(i);
                System.out.println("You dont have " + i.getNameFullName() + " in your inventory anymore");
                return;
            }
        }
    }

    public Item getItem(String item) {
        for (Item i : items) {
            if (i.getNameFullName().toLowerCase().contains(item)) {
                return i;
            }
        }
        return null;
    }

    public void print() {
        for (Item i : items)
            System.out.println(i.getNameFullName());
    }

    public void fixItem(String item) {
        for (Item i : items) {
            if (i.getNameFullName().toLowerCase().contains(item)) {
                i.fixItem();
                return;
            }
        }
        System.out.println("You don't have " + item + " in your inventory");
    }
}
