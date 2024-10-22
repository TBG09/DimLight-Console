package rsbdp.commands;

import rsbdp.Core.IOHandler;

public class rename {
    public static void main(String ObjectRename) {
        IOHandler.IO io = new IOHandler.IO();

        // Split the ObjectRename into parts
        String[] parts = ObjectRename.split(" ", 2);
        String firstWord = parts[0];
        String ObjectName2 = parts.length > 1 ? parts[1].trim() : "";


        if (ObjectName2.isEmpty()) {
            System.out.println("Expected new name of file, but got none. :(");
            return; // Exit early if no new name
        }

        // Check if the file exists
        if (io.FileExists(firstWord)) {
            io.renameFile(firstWord, ObjectName2);
            System.out.println("File " + firstWord + " has been renamed to " + ObjectName2);
        } else {
            System.out.println("File " + firstWord + " doesn't exist.");
        }
    }
}
