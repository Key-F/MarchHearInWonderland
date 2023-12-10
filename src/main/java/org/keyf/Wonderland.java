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

    public Wonderland() {
        locations = new ArrayList<>();
        createLocations();
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
                exits.put(Location.Direction.North, getLocation((String) (LocObj.get("North"))));
            }
            if (!(LocObj.get("West")).equals("")) {
                exits.put(Location.Direction.West, getLocation((String) (LocObj.get("West"))));
            }
            if (!(LocObj.get("East")).equals("")) {
                exits.put(Location.Direction.East, getLocation((String) (LocObj.get("East"))));
            }
            if (!(LocObj.get("South")).equals("")) {
                exits.put(Location.Direction.South, getLocation((String) (LocObj.get("South"))));
            }
            loc.setExits(exits);
        } catch (Exception e) {
        }
        ;
    }

    public static Location getLocation(String name) {
        for (Location l : locations) {
            if (l.getName().equals(name)) {
                return l;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        Util.printInstructions();
        Wonderland GameSession = new Wonderland();
        System.out.println("kk");

        while (true) {

            if (player.hasWon()) {
                // String win = Setup.win();
                // System.out.println("\n" + win);
                break;
            }
        }


    }
}
