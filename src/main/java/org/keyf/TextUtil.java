package org.keyf;

import java.io.*;
import java.util.HashMap;

public class TextUtil {

    public static void printInstructions() {


        // Create FileReader and BufferedReader objects to read the instructions from a
        // file
        try (FileReader file = new FileReader("texts/instructions.txt");
             BufferedReader br = new BufferedReader(file)) {

            // Create a String to store the instructions
            String line = br.readLine();
            String instructions = "";

            // While there are still lines to read in the file, append them to the
            // instructions
            while (line != null) {
                instructions = instructions + line + '\n';
                line = br.readLine();
            }

            // Return the instructions
            System.out.println(instructions);

        } catch (IOException e) {
            // If the file is not found, display a error message
            System.out.println("Error reading file. Please check the filename and try again.");
        }
    }

    public static void printHelp() {

        // Create FileReader and BufferedReader objects to read the instructions from a
        // file
        try (FileReader file = new FileReader("texts/help.txt");
             BufferedReader br = new BufferedReader(file)) {

            // Create a String to store the instructions
            String line = br.readLine();
            String instructions = "";

            // While there are still lines to read in the file, append them to the
            // instructions
            while (line != null) {
                instructions = instructions + line + '\n';
                line = br.readLine();
            }

            // Return the instructions
            System.out.println(instructions);

        } catch (IOException e) {
            // If the file is not found, display a error message
            System.out.println("Error reading file. Please check the filename and try again.");
        }
    }

    /** Public static method to create the game actions */
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
                actions.put(mainAction, new Actions(actionsList));

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
