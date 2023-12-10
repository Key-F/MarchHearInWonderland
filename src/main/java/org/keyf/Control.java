package org.keyf;

public class Control {

    public static void printInstructions() {
        System.out.println(Util.getResourceFileAsString("texts/instructions.txt"));
    }
}
