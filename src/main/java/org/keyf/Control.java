package org.keyf;

import java.util.HashMap;

public class Control {

    /** Public static method to check whether or not an action is valid */
    public static String checkInput(String command, HashMap<String, Actions> validActions) {

        // Iterate over each valid action in the HashMap
        for (String action : validActions.keySet()) {

            // Iterate over each valid action in the array
            for (String actionAlias : validActions.get(action).getActions()) {

                // If the user input matches a valid action, return the action
                if (actionAlias.equals(command)) {
                    return action;
                }
            }
        }

        // If the user input does not match a valid action, return null
        return null;
    }

}
