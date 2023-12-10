package org.keyf;

import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

public class Util {

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


}
