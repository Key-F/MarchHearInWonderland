package org.keyf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Service {

    public static HashMap<String, Actions> createActions() {

        // Create FileReader and BufferedReader objects to read the actions from a file
        try (FileReader file = new FileReader("texts/actions.txt");
             BufferedReader br = new BufferedReader(file)) {

            // Create a HashMap to store the actions
            HashMap<String, Actions> actions = new HashMap<String, Actions>();
            String line = br.readLine();

            // While there are still lines to read in the file
            while (line != null) {

                // Split the line into the main action name and the action aliases
                String[] actionsList = line.split(",");
                String mainAction = actionsList[0];

                // Put the main action name and the Actions object in the HashMap
               // actions.put(mainAction, new Actions(actionsList));

                // Read the next line
                line = br.readLine();
            }

            // Return the HashMap
            return actions;

        } catch (IOException e) {
            // If the file is not found, display a error message
            System.out.println("Error reading file. Please check the filename and try again.");
            return null;
        }
    }
}
