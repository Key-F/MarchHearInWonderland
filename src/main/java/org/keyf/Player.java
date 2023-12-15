package org.keyf;

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
    public void move(Location.Direction direction) {

        switch (direction) {
            case north:
                if (CurrentLocation.getExits().containsKey(Location.Direction.north))
                    CurrentLocation = CurrentLocation.getExits().get(Location.Direction.north);
                else System.out.println(ANSI_RED + "There is nothing in the North");
                break;
            case south:
                if (CurrentLocation.getExits().containsKey(Location.Direction.south))
                    CurrentLocation = CurrentLocation.getExits().get(Location.Direction.south);
                else System.out.println("There is nothing in the South");
                break;
            case east:
                if (CurrentLocation.getExits().containsKey(Location.Direction.east))
                    CurrentLocation = CurrentLocation.getExits().get(Location.Direction.east);
                else System.out.println("There is nothing in the South");
                break;
            case west:
                if (CurrentLocation.getExits().containsKey(Location.Direction.west))
                    CurrentLocation = CurrentLocation.getExits().get(Location.Direction.west);
                else System.out.println("There is nothing in the West");
                break;
        }
        CurrentLocation.printInfo();
    }

    public void getStatus() {
        // check if all ch are invited
        for (Location l : Wonderland.locations)
            for (Character ch : l.getNpcs()) {
                if (!ch.isGoingToParty()) System.out.println(ch.getName() + " is still not invited");
            }

        for (Item i : Wonderland.player.getCharacter().getItemNeeded()) {
            if (!Wonderland.safeHouse.hasItem(i.getName())
                    && !Wonderland.player.getInventory().hasItem(i.getName(), Wonderland.player.getCharacter()))
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
//            else if (Wonderland.player.getInventory().getItemByName(i.getName()) != null)
//                if (Wonderland.player.getInventory().getItemByName(i.getName()).getNameFullName().contains("Broken"))
//                    System.out.println(i.getNameFullName() + " is broken");
//            if (Wonderland.safeHouse.hasItem(i.getName()))
//                if (Wonderland.safeHouse.hasItem(i.getName()).getNameFullName().contains("Broken"))
//                    System.out.println(i.getNameFullName() + " is broken");
        }

        // check that everything is found and fixed
        // check teacup count
    }

    public void fixItem(String item) {
        if (!getCurrentLocation().isSafeHouse()) System.out.println("It is not your house, you can't fix items here");
        else inventory.fixItem(item);
    }

    public boolean checkItem(Item item) {
        if (!Wonderland.safeHouse.hasItem(item.getNameFullName()) &&
                Wonderland.player.getInventory().hasItem(item.getNameFullName(), Wonderland.player.getCharacter())) {
            System.out.println("You dont have " + item.getNameFullName());
            return false;
        }
        return true;
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
        if (inventory.hasItem(item, character)) {
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
//                    if (!Wonderland.safeHouse.hasItem(i.getName()) &&
//                            !Wonderland.player.getInventory().hasItem(i.getName(), Wonderland.player.getCharacter())) {
//                        System.out.println("You dont have " + i.getName() + " And I need it before visiting your party");
//                        itemsNeeded = true;
//                    }
//                }
                for (Item i : ch.getItemWanted()) {
                    if (!Wonderland.safeHouse.hasItem(i.getNameFullName()) &&
                            !Wonderland.player.getInventory().hasItem(i.getNameFullName(), Wonderland.player.getCharacter())) {
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

//    public boolean obtains(Item item) {
//        return inventory.hasItem(item, character);
//    }


    public boolean hasWon() {
//        int npcCount = 0;
//        for (Location l : Wonderland.locations)
//            for (Character ch : l.getNpcs()) {
//                npcCount++;
//                hasWon &= ch.isGoingToParty();
//            }
//
//        //Wonderland.itemForParty.isEmpty();
//
//        for (Item i : Wonderland.player.getCharacter().getItemNeeded()) {
//            hasWon = (Wonderland.safeHouse.hasItem(i.getNameFullName()) ||
//                    Wonderland.player.getInventory().hasItem(i.getNameFullName(), Wonderland.player.getCharacter()))
//                    && !i.getNameFullName().contains("Broken");
//        }
        for (Location l : Wonderland.locations)
            for (Character ch : l.getNpcs()) {
                if (!ch.isGoingToParty()) return false;
            }

        for (Item i : Wonderland.player.getCharacter().getItemNeeded()) {
            if (!Wonderland.safeHouse.hasItem(i.getName())
                    && !Wonderland.player.getInventory().hasItem(i.getName(), Wonderland.player.getCharacter()))
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
//        for (Item i : Wonderland.itemForParty)
//            if (!Wonderland.safeHouse.getItems().contains(i) && !(Wonderland.player.getInventory().items.contains(i))) {
//                hasWon = false;
//                break;
//            }
        // System.out.println("You don't have ");
        //  hasWon &= ch.isGoingToParty();
        return true;
    }

    public Location getCurrentLocation() {
        return CurrentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        CurrentLocation = currentLocation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
