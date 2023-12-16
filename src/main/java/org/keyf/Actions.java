package org.keyf;

import java.util.HashMap;
import java.util.Scanner;

public class Actions {

    /**
     * Instance variable to keep track of valid actions
     */
    private String[] actions;

    /**
     * Constructor for Actions class accepting a String array
     */
    public Actions(String[] actions) {
        // Initializes instance variable
        this.actions = actions;
    }

    /**
     * Public getter method to return the valid actions
     */
    public String[] getActions() {
        return actions;
    }

    /**
     * If the user wants their current location
     * @param player
     */
    public static void printLocation(Player player) {
        System.out.println("You are at the " + player.getCurrentLocation().getName());
    }


    /**
     * If the user wants to talk to an NPC
     *
     * @param userCommand
     * @param player
     */
    public static void talk(String[] userCommand, Player player) {
        String name = getArg(userCommand);
        if (name == null) {
            System.out.println("You must specify a character to talk to. Try 'talk <character name>'.");
            return;
        }
        player.talkTo(name);
    }

    /**
     * Method to get second arg
     */
    public static String getArg(String[] arg) {
        if (arg.length < 2)
            return null;
        String arg1 = "";
        for (int i = 1; i < arg.length; i++) {
            arg1 += arg[i] + " ";
        }
        return arg1.trim().toLowerCase();
    }


    /**
     * If the user wants to fix an item
     *
     * @param userCommand
     * @param player
     */
    public static void fix(String[] userCommand, Player player) {
        String item  = getArg(userCommand);
        if (item == null) {
            System.out.println("Please specify an item to use. Try 'use <item name>'");
            return;
        }
        player.getInventory().hasItem(item);
        player.fixItem(item);
    }

    /**
     * Check if the user specified an item, if not display an error message
     *
     * @param userCommand
     * @param player
     */
    public static void invite(String[] userCommand, Player player) {
        String name = getArg(userCommand);
        if (name == null) {
            System.out.println("Please specify an item to use. Try 'use <item name>'");
            return;
        }
        player.invite(name);
    }

    /**
     * If the user wants to move to a different room
     *
     * @param userCommand
     * @param player
     * @param actions
     */
    public static void move(String[] userCommand, Player player, HashMap<String, Actions> actions) {
        // Check if the user specified a direction
        if (userCommand.length == 2) {
            // Parse direction from user input and check if it's valid using Control class
            String direction = Control.checkInput(userCommand[1], actions);
            userCommand[1] = direction;

            // If direction is valid, call the Player move method to move to the new room
            if (userCommand[1] == null) {
                System.out.println("Invalid direction.");
            } else {
                player.move(Location.Direction.valueOf(direction));
            }

        } else if (userCommand.length > 2) {
            // If the user specified more than one direction, display an error message
            System.out.println(
                    "\n You can only move in one direction at a time. Try 'move <direction>'.");
        } else {
            // If the user didn't specify a direction, display an error message
            System.out.println("\nYou must specify a direction to move. Try 'move <direction>'.");
        }
    }

    public static void look(Player player) {
        player.getCurrentLocation().printInfo();
    }

    public static void inventory(Player player) {
        player.getInventory().print();
    }


    public static boolean give(String[] userCommand, Player player, Scanner in) {
        if (userCommand.length >= 2) {
            String npc = "";

            // Parse the NPC name from the user input
            for (int i = 1; i < userCommand.length; i++) {
                npc += userCommand[i] + " ";
            }

            // Call the Player talkTo method to display the NPC's dialogue
            Character ch = player.startGivingAction(npc.trim().toLowerCase());
            if (ch == null) return false;
            String itemName = in.nextLine();
            player.give(ch, itemName);

        } else {
            System.out.println("\nYou must specify a character to talk to. Try 'talk <character name>'.");
        }
        return true;
    }

    public static void pickUp(String[] userCommand, Player player) {
        String item = getArg(userCommand);
        if (item == null) {
            System.out.println("You must specify an item to get. Try 'get <item name>'.");
            return;
        }
        player.getInventory().addItem(player.getCurrentLocation().pickUpItem(item));
    }

    public static void drop(String[] userCommand, Player player) {
        String item = getArg(userCommand);
        if (item == null) {
            System.out.println("You must specify an item to drop. Try 'drop <item name>'.");
            return;
        }
        if (player.getInventory().hasItem(item.trim())) {
            player.getCurrentLocation().addItem(player.getInventory().getItem(item.trim()));
            System.out.println("You dropped: " + item.trim());
            player.getInventory().dropItem(item.trim());
        }
    }

    /**
     * If the user wants to quit the game
     */
    public static void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

}

