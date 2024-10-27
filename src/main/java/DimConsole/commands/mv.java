package DimConsole.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class mv {
    public static void main(String MainArgs) {
        // Null check for MainArgs
        if (MainArgs == null || MainArgs.trim().isEmpty()) {
            System.out.println("No arguments provided.");
            return;
        }

        // Split the input arguments by spaces
        String[] args = MainArgs.split(" ");

        // Check if there are enough arguments
        if (args.length < 2) {
            System.out.println("Usage: mv <source> <destination>");
            return;
        }

        String source = args[0];       // First argument as source
        String destination = args[1];  // Second argument as destination

        // Check if both source and destination are provided
        if (source.isEmpty() || destination.isEmpty()) {
            System.out.println("Source and destination must be specified.");
            return;
        }

        File srcFile = new File(source);
        File destFile = new File(destination);

        // Check if the source file/directory exists
        if (!srcFile.exists()) {
            System.out.println("Source file or directory does not exist: " + source);
            return;
        }

        // Invoke the move method with parsed parameters
        try {
            moveFileOrDirectory(srcFile, destFile);
            System.out.println("Moved " + (srcFile.isDirectory() ? "directory" : "file") + ": "
                    + srcFile.getAbsolutePath() + " to " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("An error occurred while moving: " + e.getMessage());
        }
    }

    private static void moveFileOrDirectory(File source, File destination) throws IOException {
        // Attempt to move the file or directory
        Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
