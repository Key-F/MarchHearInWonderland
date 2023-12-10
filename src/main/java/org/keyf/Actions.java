package org.keyf;

public class Actions {

    /** Instance variable to keep track of valid actions */
    private String[] actions;

    /** Constructor for Actions class accepting a String array */
    public Actions(String[] actions) {
        // Initializes instance variable
        this.actions = actions;
    }

    /** Public getter method to return the valid actions */
    public String[] getActions() {
        return actions;
    }

    public void speak(Character character) {
    }


    // location
    public void move(Location.Direction dir) {

    }

    public void examine(Location location) {

    }

    // items
    public void consume(Item item) {

    }

    public void pickUp(Item item) {

    }

    public void drop(Item item) {

    }

    public void store(Item item) {

    }

}

