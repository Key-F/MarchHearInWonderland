package org.keyf;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class Wonderland {

    public static Player player;
    public static List<Character> characterList;
    public static List<Item> itemList;
    public static Location safeHouse;
    public static List<Location> locations;

    private HashMap<String, Actions> actions;

    public Wonderland() {
        locations = new ArrayList<>();
        createLocations();
        characterList = new ArrayList<>();
        createCharacters();
        actions = TextUtil.createActions();
        TextUtil.printHelp();
        createPlayer();
    }

    public static void main(String[] args) {
        TextUtil.printInstructions();
        Wonderland game = new Wonderland();
        Scanner in = new Scanner(System.in);

        while (true) {
            if (player.hasWon()) {
                System.out.println("You won");
                break;
            }

            // Split the user input into an array of strings
            String userInput = in.nextLine();
            userInput = userInput.toLowerCase();
            String[] userCommand = userInput.split(" ");

            // Using the Control class, ensure the user input is valid
            String command = Control.checkInput(userCommand[0], game.actions);
            userCommand[0] = command;

            // If the user input is invalid, display an error message, otherwise continue
            if (userCommand[0] == null) {
                System.out.println("\nInvalid command.");
            } else {

                // Based on the first command, execute the appropriate action
                switch (userCommand[0]) {
                    // If the user wants their current location
                    case "location":
                        System.out.println("\nYou are at the " + game.player.getCurrentLocation().getName());
                        break;

                    // If the user wants to get an item from the room
                    case "get":
                        // Check if the user specified an item
                        if (userCommand.length >= 2) {
                            String item = "";

                            // Parse the item name from the user input
                            for (int i = 1; i < userCommand.length; i++) {
                                item += userCommand[i] + " ";
                            }

                            game.player.getInventory().addItem(player.getCurrentLocation().getItem(item));

                        } else {
                            System.out.println("\nYou must specify an item to get. Try 'get <item name>'.");
                        }
                        break;

                    // If the user wants to see their inventory
                    case "inventory":
                        // Call the Player getInventory method to display the inventory
                        game.player.getInventory().print();
                        break;

                    // If the user wants to move to a different room
                    case "move":
                        // Check if the user specified a direction
                        if (userCommand.length == 2) {
                            // Parse direction from user input and check if it's valid using Control class
                            String direction = Control.checkInput(userCommand[1], game.actions);
                            userCommand[1] = direction;

                            // If direction is valid, call the Player move method to move to the new room
                            if (userCommand[1] == null) {
                                System.out.println("\nInvalid direction.");
                            } else {
                                game.player.move(Location.Direction.valueOf(direction));
                            }

                        } else if (userCommand.length > 2) {
                            // If the user specified more than one direction, display an error message
                            System.out.println(
                                    "\n You can only move in one direction at a time. Try 'move <direction>'.");
                        } else {
                            // If the user didn't specify a direction, display an error message
                            System.out.println("\nYou must specify a direction to move. Try 'move <direction>'.");
                        }
                        break;

                    // If the user wants to talk to an NPC
                    case "talk":
                        // Check if the user specified an NPC, if not display an error message
                        if (userCommand.length >= 2) {
                            String npc = "";

                            // Parse the NPC name from the user input
                            for (int i = 1; i < userCommand.length; i++) {
                                npc += userCommand[i] + " ";
                            }

                            // Call the Player talkTo method to display the NPC's dialogue
                            game.player.talkTo(npc.trim().toLowerCase());
                        } else {
                            System.out.println("\nYou must specify a character to talk to. Try 'talk <character name>'.");
                        }
                        break;

                    // If the user wants to use an item
                    case "fix":
                        // Check if the user specified an item, if not display an error message
                        if (userCommand.length >= 2) {
                            String item = "";

                            // Parse the item name from the user input
                            for (int i = 1; i < userCommand.length; i++) {
                                item += userCommand[i] + " ";
                            }

                            game.player.getInventory().hasItem(item.trim(), player.getCharacter());
                            game.player.getInventory().fixItem(item.trim());
                        } else {
                            System.out.println("\nPlease specify an item to use. Try 'use <item name>'");
                        }
                        break;

                    // If the user wants to quit the game
                    case "exit":
                        // Break out of the loop and end the game
                        System.out.println("\nSorry to see you go. Goodbye!");
                        System.exit(0);
                        break;

                    // If the user wants to see the help menu
                    case "help":
                        TextUtil.printHelp();
                        break;
                }

            }
        }

        // Close the scanner object
        in.close();

    }

    public void createPlayer() {
        Item item = new Item("Key");
        player = new Player(getLocation("March Hare's house"),
                new Inventory(), getCharacter("March Hare house"));
        player.getInventory().addItem(item);
    }

    public void createCharacters() {
        final File folder = new File("characters/");
        for (File fileEntry : folder.listFiles()) {
            characterList.add(new Character(fileEntry));
        }
    }

    public void createLocations() {
        final File folder = new File("locations/");
        for (File fileEntry : folder.listFiles()) {
            locations.add(new Location(fileEntry));
        }
        for (File fileEntry : folder.listFiles()) {
            generateConnections(fileEntry);
        }
    }


    public void generateConnections(File file) {
        try {
            InputStream is = new FileInputStream(file);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject LocObj = new JSONObject(jsonTxt);
            Location loc = getLocation((String) LocObj.get("Name"));
            HashMap<Location.Direction, Location> exits = new HashMap<>();
            if (!(LocObj.get("North")).equals("")) {
                exits.put(Location.Direction.north, getLocation((String) (LocObj.get("North"))));
            }
            if (!(LocObj.get("West")).equals("")) {
                exits.put(Location.Direction.west, getLocation((String) (LocObj.get("West"))));
            }
            if (!(LocObj.get("East")).equals("")) {
                exits.put(Location.Direction.east, getLocation((String) (LocObj.get("East"))));
            }
            if (!(LocObj.get("South")).equals("")) {
                exits.put(Location.Direction.south, getLocation((String) (LocObj.get("South"))));
            }
            loc.setExits(exits);
        } catch (Exception e) {
        }
    }

    public static Location getLocation(String name) {
        for (Location l : locations) {
            if (l.getName().equals(name)) {
                return l;
            }
        }
        return null;
    }

    public static Character getCharacter(String name) {
        for (Character character : characterList) {
            if (character.getName().equals(name)) {
                return character;
            }
        }
        return null;
    }

}
