package DimConsole.commands;

import DimConsole.System.NetworkHandler;

import java.io.IOException;
import java.net.URL;
import java.net.InetAddress;


public class net {
    public static void main(String ArgumentThing) {
        // Process the command
        processCommand(ArgumentThing);
    }

    private static void processCommand(String input) {
        // Split the input into the command and arguments
        String[] args = input.split(" ", 2);
        String command = args[0].toLowerCase();
        String argument = args.length > 1 ? args[1] : "";

        switch (command) {
            case "help":
                displayHelp();
                break;
            case "ping":
                ping(argument);
                break;
            case "download":
                download(argument);
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }

    private static void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("help - Displays this help message");
        System.out.println("ping <ip/url> - Pings the specified IP address or URL");
        System.out.println("download <url> <path> - Downloads the file from the URL to the specified path");
    }

    private static void ping(String argument) {
        try {
            // Check if the argument is provided
            if (argument.isEmpty()) {
                System.out.println("Usage: ping <ip/url>");
                return;
            }
            // Attempt to ping the IP address or URL
            InetAddress address = InetAddress.getByName(argument);
            boolean reachable = address.isReachable(5000);
            if (reachable) {
                System.out.println("Successful Ping towards: " + argument);
            } else {
                System.out.println("Ping failed towards: " + argument);
            }
        } catch (IOException e) {
            System.out.println("Error pinging: " + e.getMessage());
        }
    }

    private static void download(String argument) {
        String[] args = argument.split(" ");
        if (args.length < 2) {
            System.out.println("Usage: download <url> <path>");
            return;
        }

        String urlStr = args[0];
        String path = args[1];

        if (!isValidUrl(urlStr)) {
            System.out.println("Invalid URL: " + urlStr);
            return;
        }

        // Create an instance of NetworkHandler to manage downloads
        NetworkHandler networkHandler = new NetworkHandler();
        try {
            long fileSize = networkHandler.getFileSize(urlStr);
            System.out.println("Download size: " + fileSize + " bytes");
            networkHandler.downloadFile(urlStr, path);
        } catch (IOException e) {
            System.out.println("Download failed: " + e.getMessage());
        }
    }

    private static boolean isValidUrl(String urlStr) {
        try {
            new URL(urlStr).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
