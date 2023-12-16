package org.keyf;

import org.keyf.Location.Direction;

import static org.keyf.Colors.ANSI_RED;

public class Player {
    private Location CurrentLocation;
    private Inventory inventory;
    private Character character;

    public Player(Location currentLocation, Inventory inventory, Character character) {
        CurrentLocation = currentLocation;
        this.inventory = inventory;
        this.character = character;
    }

    /**
     * Public method that moves the player to a new location
     */
    public void move(Direction direction) {
        switch (direction) {
            case north:
                if (CurrentLocation.getExits().containsKey(Direction.north))
                    CurrentLocation = CurrentLocation.getExits().get(Direction.north);
                else System.out.println(ANSI_RED + "There is nothing in the North");
                break;
            case south:
                if (CurrentLocation.getExits().containsKey(Direction.south))
                    CurrentLocation = CurrentLocation.getExits().get(Direction.south);
                else System.out.println("There is nothing in the South");
                break;
            case east:
                if (CurrentLocation.getExits().containsKey(Direction.east))
                    CurrentLocation = CurrentLocation.getExits().get(Direction.east);
                else System.out.println("There is nothing in the South");
                break;
            case west:
                if (CurrentLocation.getExits().containsKey(Direction.west))
                    CurrentLocation = CurrentLocation.getExits().get(Direction.west);
                else System.out.println("There is nothing in the West");
                break;
        }
        CurrentLocation.printInfo();
    }

    /**
     * Print info about current game progress
     */
    public void getStatus() {
        // check if all characters are invited
        for (Location l : Wonderland.locations)
            for (Character ch : l.getNpcs()) {
                if (!ch.isGoingToParty()) System.out.println(ch.getName() + " is still not invited");
            }

        // check if all items are collected
        for (Item i : Wonderland.player.getCharacter().getItemNeeded()) {
            if (!Wonderland.safeHouse.hasItem(i.getName())
                    && !Wonderland.player.getInventory().hasItem(i.getName()))
                System.out.println("You dont have " + i.getName());
            Item ii = Wonderland.player.getInventory().getItemByName(i.getName().replace("(Broken)", ""));
            if (ii != null)
                if (ii.getNameFullName().contains("Broken")) {
                    System.out.println(i.getNameFullName() + " is broken");
                    continue;
                }
            ii = Wonderland.safeHouse.getItem(i.getName());
            if (ii != null)
                if (ii.getNameFullName().contains("Broken")) {
                    System.out.println(i.getNameFullName() + " is broken");
                }
        }

    }

    public void fixItem(String item) {
        if (!getCurrentLocation().isSafeHouse()) System.out.println("It is not your house, you can't fix items here");
        else inventory.fixItem(item);
    }

    public void talkTo(String npc) {
        for (Character ch : getCurrentLocation().getNpcs()) {
            if (ch.getName().toLowerCase().contains(npc)) {
                ch.say();
                return;
            }
        }
        System.out.println("There is no " + npc + " in this location");
    }

    public Character startGivingAction(String npc) {
        for (Character ch : getCurrentLocation().getNpcs()) {
            if (ch.getName().toLowerCase().contains(npc)) {
                System.out.println("Specify item, you would like to give to " + ch.getName());
                inventory.print();
                return ch;
            }
        }
        System.out.println("There is no " + npc + " in this location");
        return null;
    }

    public void give(Character npc, String item) {
        if (inventory.hasItem(item)) {
            if (npc.getItem(inventory.getItemByName(item)))
                inventory.dropItem(item);
        } else System.out.println("You don't have " + item + " in your inventory");
    }

    public void invite(String npc) {
        for (Character ch : getCurrentLocation().getNpcs()) {
            System.out.println(ch.getName() + " responds to player: ");
            if (ch.getName().toLowerCase().contains(npc)) {
                if (ch.isGoingToParty()) {
                    System.out.println(ch.getName() + ": i'm already going to your party");
                    return;
                }
                boolean itemsNeeded = false;
                if (!ch.getItemNeeded().isEmpty()) {
                    for (Item i : ch.getItemNeeded()) {
                        System.out.println("I need to find " + i.getNameFullName().replace(" (Broken)", "") + " before visiting your party");
                    }
                    itemsNeeded = true;
                }

                for (Item i : ch.getItemWanted()) {
                    if (!Wonderland.safeHouse.hasItem(i.getNameFullName()) &&
                            !Wonderland.player.getInventory().hasItem(i.getNameFullName())) {
                        System.out.println("You dont have " + i.getNameFullName() + " And I like it for a tea party");
                        itemsNeeded = true;
                    }
                }
                if (!itemsNeeded) ch.setGoingToParty(true);
                return;
            }
        }
        System.out.println("There is no " + npc + " in this location");
    }

    /**
     * Method to check win condition
     * @return
     */
    public boolean hasWon() {
        for (Location l : Wonderland.locations)
            for (Character ch : l.getNpcs()) {
                if (!ch.isGoingToParty()) return false;
            }
        for (Item i : Wonderland.player.getCharacter().getItemNeeded()) {
            if (!Wonderland.safeHouse.hasItem(i.getName())
                    && !Wonderland.player.getInventory().hasItem(i.getName()))
                return false;
            Item ii = Wonderland.player.getInventory().getItemByName(i.getName().replace("(Broken)", ""));
            if (ii != null)
                if (ii.getNameFullName().contains("Broken")) {
                    return false;
                }
            ii = Wonderland.safeHouse.getItem(i.getName());
            if (ii != null)
                if (ii.getNameFullName().contains("Broken")) {
                    return false;
                }
        }
        return true;
    }

    public Location getCurrentLocation() {
        return CurrentLocation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Character getCharacter() {
        return character;
    }

}
