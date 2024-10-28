package DimConsole.commands;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class find {
    private static ExecutorService executor;

    public static void main(String someWeirdArg) {
        if (someWeirdArg == null || someWeirdArg.isBlank()) {
            printHelp();
            return;
        }

        // Argument Parsing
        String[] args = someWeirdArg.trim().split(" ");
        String directory = null, itemName = null, prefix = null, suffix = null;
        boolean findFiles = true, findDirectories = false;
        int maxDepth = Integer.MAX_VALUE;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "help":
                    printHelp();
                    return;
                case "-d":
                case "--directory":
                    if (i + 1 < args.length) {
                        directory = args[i + 1].replaceAll("\"", ""); // Remove quotes
                        i++;
                    }
                    break;
                case "-i":
                case "--item":
                    if (i + 1 < args.length) {
                        itemName = args[i + 1].replaceAll("\"", "");
                        i++;
                    }
                    break;
                case "-p":
                case "--prefix":
                    if (i + 1 < args.length) {
                        prefix = args[i + 1].replaceAll("\"", "");
                        i++;
                    }
                    break;
                case "-s":
                case "--suffix":
                    if (i + 1 < args.length) {
                        suffix = args[i + 1].replaceAll("\"", "");
                        i++;
                    }
                    break;
                case "--max-depth":
                    if (i + 1 < args.length) {
                        try {
                            maxDepth = Integer.parseInt(args[i + 1]);
                            if (maxDepth < 1 || maxDepth > 1024) {
                                System.out.println("Invalid max-depth value. Please specify a value between 1 and 1024.");
                                return;
                            }
                            i++;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid max-depth value. Please specify a numeric value between 1 and 1024.");
                            return;
                        }
                    }
                    break;
                case "-fd":
                case "--finddirectory":
                    findFiles = false;
                    findDirectories = true;
                    break;
                case "-ff":
                case "--findfiles":
                    findFiles = true;
                    findDirectories = false;
                    break;
                case "-fb":
                case "--findboth":
                    findFiles = true;
                    findDirectories = true;
                    break;
            }
        }

        if (directory == null) {
            System.out.println("Please specify a directory to search in using -d or --directory, surrounded by double quotes (e.g., \"path/to/dir\").");
            return;
        }

        // Perform the search
        File dir = new File(directory);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Invalid directory specified: " + directory);
            return;
        }

        // Setup executor and start search with threading
        executor = Executors.newCachedThreadPool();
        searchDirectory(dir, findFiles, findDirectories, itemName, prefix, suffix, maxDepth, 0);

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all tasks to finish
        }
    }

    private static void searchDirectory(File directory, boolean findFiles, boolean findDirectories, String itemName, String prefix, String suffix, int maxDepth, int currentDepth) {
        if (currentDepth >= maxDepth) return;

        executor.execute(() -> {
            File[] files = directory.listFiles();
            if (files == null) return;

            for (File file : files) {
                boolean matches = true;
                if (itemName != null && !file.getName().equals(itemName)) {
                    matches = false;
                }
                if (prefix != null && !file.getName().startsWith(prefix)) {
                    matches = false;
                }
                if (suffix != null && !file.getName().endsWith(suffix)) {
                    matches = false;
                }
                if ((file.isFile() && !findFiles) || (file.isDirectory() && !findDirectories)) {
                    matches = false;
                }

                if (matches) {
                    System.out.println("Found " + (file.isDirectory() ? "directory" : "file") + " '" + file.getName() + "' at " + file.getAbsolutePath());
                }

                if (file.isDirectory() && currentDepth + 1 < maxDepth) {
                    searchDirectory(file, findFiles, findDirectories, itemName, prefix, suffix, maxDepth, currentDepth + 1);
                }
            }
        });
    }

    private static void printHelp() {
        System.out.println("""
        find command usage:
        find -d "<directory>" [-i "<item_name>"] [-p "<prefix>"] [-s "<suffix>"] [-fd | -ff | -fb] [--max-depth <1-1024>]
        Options:
          -d, --directory       Specify the directory to search in. Must be surrounded by double quotes.
          -i, --item            Name of the file or directory to search for.
          -p, --prefix          Look for files or directories with this prefix.
          -s, --suffix          Look for files or directories with this suffix.
          -fd, --finddirectory  Search for directories only.
          -ff, --findfiles      Search for files only.
          -fb, --findboth       Search for both files and directories.
          --max-depth           Set the maximum depth for the search. (value from 1 to 1024)
        Examples:
          find -d "/path/to/dir" -i "example.txt"            : Search for 'example.txt'.
          find -d "/path/to/dir" -p "test" -fd               : Search for directories that start with 'test'.
          find -d "/path/to/dir" -s ".log" -ff               : Search for files that end with '.log'.
          find -d "/path/to/dir" -fb --max-depth 3            : Recursively search for both files and directories up to 3 levels deep.
        """);
    }
}

// why did I make so many methods