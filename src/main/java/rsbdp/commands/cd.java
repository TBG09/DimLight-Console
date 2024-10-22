package rsbdp.commands;

import rsbdp.Core.IOHandler;
import rsbdp.Core.PublicVariables;

import java.io.File;

public class cd {
    public static void main(String Directory) {
        IOHandler.IO io = new IOHandler.IO();

        if (Directory == null || Directory.isEmpty()) {
            System.out.println("Directory doesn't exist.");
            return; // Exit early if the directory is null or empty
        }

        // Normalize the current directory path for the OS
        String currentDir = PublicVariables.currentDirectory;
        String newDirectory = Directory.trim(); // Trim any extra spaces

        if (newDirectory.equals("..")) {
            // Go up one directory
            File currentFile = new File(currentDir);
            String parentDir = currentFile.getParent(); // Get the parent directory
            if (parentDir != null) {
                PublicVariables.currentDirectory = parentDir;
                System.out.println("Changed directory to: " + parentDir);
            } else {
                System.out.println("Already at the root directory.");
            }
        } else if (newDirectory.equals(".")) {
            // Stay in the same directory
            System.out.println("Staying in the current directory: " + currentDir);
        } else {
            // Check for other directories or files
            File targetDir = new File(currentDir, newDirectory);
            if (targetDir.exists()) {
                if (targetDir.isDirectory()) {
                    // It's a directory; update the current directory
                    PublicVariables.currentDirectory = targetDir.getAbsolutePath();
                    System.out.println("Changed directory to: " + targetDir.getAbsolutePath());
                } else {
                    // It's a file
                    System.out.println("That's not a folder; that's a file :/");
                }
            } else {
                System.out.println("Directory doesn't exist.");
            }
        }
    }
}
// What are the purpose of these comments again? Explaining? Oh, Right.