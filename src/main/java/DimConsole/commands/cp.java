package DimConsole.commands;

import DimConsole.Core.IOHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class cp {
    public static void main(String MainArgs) {
        IOHandler.IO io = new IOHandler.IO();

        // Null check for MainArgs
        if (MainArgs == null || MainArgs.trim().isEmpty()) {
            System.out.println("No arguments provided.");
            return;
        }

        // Split the input arguments by spaces
        String[] args = MainArgs.split(" ");

        // Check if there are enough arguments
        if (args.length < 2) {
            System.out.println("Usage: cp <source> <destination> [-d]");
            return;
        }

        String source = args[0];        // First argument as source
        String destination = args[1];   // Second argument as destination
        boolean isDirectory = args.length > 2 && args[2].equals("-d"); // Check for the -d flag

        // Check if both source and destination are provided
        if (source.isEmpty() || destination.isEmpty()) {
            System.out.println("Source and destination must be specified.");
            return;
        }

        File srcFile = new File(source);
        File destFile = new File(destination);

        // Check if the source exists
        if (!srcFile.exists()) {
            System.out.println("Source does not exist: " + source);
            return;
        }

        // If it's a directory copy, we need to check for -d
        if (isDirectory && srcFile.isFile()) {
            System.out.println("Expected a directory to copy, but found a file: " + source);
            return;
        }

        // Invoke the copy method with parsed parameters
        try {
            if (isDirectory) {
                // Handle directory copying
                copyDirectory(srcFile.toPath(), destFile.toPath());
                System.out.println("Directory copied from " + source + " to " + destination + " successfully!");
            } else {
                // Handle file copying
                io.copy(source, destination);
                System.out.println("Copied file from " + source + " to " + destination + " successfully!");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while copying: " + e.getMessage());
        }
    }

    private static void copyDirectory(Path source, Path destination) throws IOException {
        // Create destination directory if it does not exist
        if (!Files.exists(destination)) {
            Files.createDirectories(destination);
        }

        // Copy all files and directories
        Files.walk(source).forEach(srcPath -> {
            Path destPath = destination.resolve(source.relativize(srcPath));
            try {
                if (Files.isDirectory(srcPath)) {
                    if (!Files.exists(destPath)) {
                        Files.createDirectory(destPath);
                    }
                } else {
                    Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                System.out.println("Failed to copy: " + srcPath + " to " + destPath + ": " + e.getMessage());
            }
        });
    }
}
