package DimConsole.System;

import DimConsole.LogFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteSysCommands {

    // Method to execute the command in a new window (as provided earlier)
    public static void executeCommand(String command) {
        String os = PublicVariables.osType;
        ProcessBuilder processBuilder;
        LogFile LogFile = new LogFile();

        try {
            // Determine if the OS is Windows
            if (os.contains("win")) {
                // Open a new command prompt window and execute the command
                LogFile.Hinfo("ExecuteSysCommands", "Executing command " + command + " using cmd because this is on windows lol.");
                processBuilder = new ProcessBuilder("cmd", "/c", "start", "cmd", "/k", command);
            } else {
                // Assume it's a Unix/Linux-based OS and use bash to execute
                LogFile.Hinfo("ExecuteSysCommands", "Executing command " + command + " using bash because this is on linux lol.");
                processBuilder = new ProcessBuilder("bash", "-c", command);
            }

            // Start the process
            Process process = processBuilder.start();

            // Read and output the command output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Wait for the process to finish and check the exit code
            int exitCode = process.waitFor();
            System.out.println("Command exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to silently verify if a command can be executed successfully
    public static boolean verifyCommand(String command) {
        String os = PublicVariables.osType;
        ProcessBuilder processBuilder;

        try {
            // Determine if the OS is Windows
            if (os.contains("win")) {
                // Run the command silently without showing a new window
                processBuilder = new ProcessBuilder("cmd", "/c", command);
                processBuilder.redirectErrorStream(true);
            } else {
                // Assume it's a Unix/Linux-based OS and use bash to execute silently
                processBuilder = new ProcessBuilder("bash", "-c", command);
                processBuilder.redirectErrorStream(true);
            }

            // Start the process and wait for the result in the background
            Process process = processBuilder.start();

            // Read output but do not display it
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while (reader.readLine() != null) {
                    // No output display
                }
            }

            // Wait for the process to finish and return the exit code (0 = success)
            int exitCode = process.waitFor();
            return exitCode == 0; // Return true if command executed successfully

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
