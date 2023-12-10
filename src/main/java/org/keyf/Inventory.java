package org.keyf;

import java.util.List;

public class Inventory {
    public List<Item> items;

    public void addItem(Item item) {
        items.add(item);
    }
    public  boolean hasItem(String item, Character owner) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(item)) {
                System.out.println("Yes, there is " + item + " in " + owner.getName() + " inventory");
                return true;
            }
        }
        System.out.println("Yes, there is NO " + item + " in " + owner.getName() + " inventory");
        return false;
    }

    public void fixItem(String item) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(item)) {
               i.fixItem();
            }
        }
    }
}
