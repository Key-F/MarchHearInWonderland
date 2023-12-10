package org.keyf;

public class Player {
    private Location CurrentLocation;
    private Inventory inventory;
    private Character character;

    public Player(Location currentLocation, Inventory inventory, Character character) {
        CurrentLocation = currentLocation;
        this.inventory = inventory;
        this.character = character;
    }

    /** Public method that moves the player to a new location */
    public void move(Location.Direction direction) {

        switch (direction) {
            case north:
                if (CurrentLocation.getExits().containsKey(Location.Direction.north))
                    CurrentLocation = CurrentLocation.getExits().get(Location.Direction.north);
                else System.out.println("There is nothing in the North");
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
    }

    public void talkTo(String npc) {
       for (Character ch : getCurrentLocation().getNpcs()) {
           if (ch.getName().equalsIgnoreCase(npc))
               ch.say();
            }
    }

//    public boolean obtains(Item item) {
//        return inventory.hasItem(item, character);
//    }


    public boolean hasWon() {
        boolean hasWon = false;
        for (Character ch : Wonderland.characterList)
            hasWon &= ch.isGoingToParty();
       // for (Item i : Wonderland.itemList)
          //  hasWon &= ch.isGoingToParty();
        return hasWon;
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
