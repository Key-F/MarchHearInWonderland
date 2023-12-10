package org.keyf;

import java.util.List;

public class Inventory {
    public List<Item> items;

    public  boolean hasItem(Item item, Character owner) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(item.getName())) {
                System.out.println("Yes, there is " + item.getName() + " in " + owner.getName() + " inventory");
                return true;
            }
        }
        System.out.println("Yes, there is NO " + item.getName() + " in " + owner.getName() + " inventory");
        return false;
    }
}
