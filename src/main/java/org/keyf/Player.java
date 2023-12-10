package org.keyf;

public class Player {
    private Location CurrentLocation;
    private Inventory inventory;
    private Character character;




    public boolean obtains(Item item) {
        return inventory.hasItem(item, character);
    }


    public boolean hasWon() {
        boolean hasWon = false;
        for (Character ch : Wonderland.characterList)
            hasWon &= ch.isGoingToParty();
       // for (Item i : Wonderland.itemList)
          //  hasWon &= ch.isGoingToParty();
        return hasWon;
    }
}
