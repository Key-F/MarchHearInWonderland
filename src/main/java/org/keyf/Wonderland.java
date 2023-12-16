package org.keyf;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Wonderland {

    public static Player player;
    public static Location safeHouse;
    public static List<Location> locations;

    private HashMap<String, Actions> actions;

    public Wonderland() {
        locations = new ArrayList<>();
        createLocations();
        actions = TextUtil.createActions();
        createPlayer();
        player.getCurrentLocation().printInfo();
    }

    public static void main(String[] args) {
        TextUtil.printFromFile("texts/instructions.txt");
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
                    case "location":
                        Actions.printLocation(player);
                        break;
                    case "get":
                        Actions.pickUp(userCommand, player);
                        break;
                    case "drop":
                        Actions.drop(userCommand, player);
                        break;
                    case "give":
                        if (Actions.give(userCommand, player, in)) continue;
                        break;
                    case "inventory":
                        Actions.inventory(player);
                        break;
                    case "look":
                        Actions.look(player);
                        break;
                    case "move":
                        Actions.move(userCommand, player, game.actions);
                        break;
                    case "talk":
                        Actions.talk(userCommand, player);
                        break;
                    case "fix":
                        Actions.fix(userCommand, player);
                        break;
                    case "invite":
                        Actions.invite(userCommand, player);
                        break;
                    case "exit":
                        Actions.exit();
                        break;
                    case "help":
                        TextUtil.printFromFile("texts/help.txt");
                        break;
                    case "status":
                        player.getStatus();
                        break;
                }

            }
        }
        in.close();

    }

    public void createPlayer() {
        Item item = new Item("Key");
        player = new Player(getLocation("March Hare's house"),
                new Inventory(), new Character("March Hare"));
        player.getInventory().addItem(item);
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
            e.printStackTrace();
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

}
