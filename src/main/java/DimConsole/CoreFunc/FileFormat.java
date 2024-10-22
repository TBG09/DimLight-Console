package DimConsole.CoreFunc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import DimConsole.Core.CommandList;
import DimConsole.Core.Main;

public class FileFormat {
    private static final Map<String, Object> variables = new HashMap<>(); // To store variables

    public static void main(String[] args) throws Exception {
        String fileLocation = args[0];
        try {
            executeCommandsFromFile(fileLocation);
        } catch (Exception e) {
            System.out.println("Script execution halted due to error: " + e.getMessage());
            Main.UserInput(); // Call UserInput method in case of error
        }

        // Ensure UserInput is called after executing commands
        Main.UserInput(); // Call UserInput after script execution
    }

    // Method to read commands from a file and execute them
    private static void executeCommandsFromFile(String fileLocation) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            CommandList commandList = new CommandList(); // Moved declaration outside the loop for efficiency
            while ((line = br.readLine()) != null) {
                // Skip empty lines or lines that are comments (e.g., starting with #)
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Split the command and its argument
                String[] parts = line.split(" ", 2); // Split only on the first space
                String command = parts[0]; // Command is the first part
                String argument = parts.length > 1 ? parts[1] : ""; // Argument is the rest

                // Process the command directly
                if (!processCommand(command, argument)) {
                    commandList.executeCommand2(command, argument); // Fallback to the command list if processing failed
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            throw new Exception("Failed to read the command file.");
        }
    }

    // Method to process commands and check for variable assignments, conditions, and echo
    private static boolean processCommand(String command, String argument) {
        if (command.startsWith("set ")) { // Example: set variableName = value
            String[] assignment = argument.split("=", 2);
            if (assignment.length == 2) {
                String varName = assignment[0].trim();
                String value = assignment[1].trim();
                if (value.matches("\\d+")) { // Integer
                    variables.put(varName, Integer.parseInt(value));
                } else if (value.matches("\\d+L")) { // Long
                    variables.put(varName, Long.parseLong(value.replace("L", "")));
                } else if (value.startsWith("[") && value.endsWith("]")) { // List
                    String listContent = value.substring(1, value.length() - 1);
                    String[] listItems = listContent.split(",");
                    variables.put(varName, listItems);
                } else { // String
                    variables.put(varName, value.replace("\"", "")); // Remove quotes
                }
                return true; // Indicate that the command was processed
            }
        } else if (command.startsWith("if ")) { // Example: if variableName == value
            String[] conditionParts = argument.split("==", 2);
            if (conditionParts.length == 2) {
                String varName = conditionParts[0].trim();
                String value = conditionParts[1].trim();
                if (variables.containsKey(varName) && variables.get(varName).toString().equals(value)) {
                    return true; // Condition met
                } else {
                    return false; // Condition not met
                }
            }
        } else if (command.equals("echo")) { // Implementing echo command
            // Check if the argument is a variable or a string to print
            if (variables.containsKey(argument.trim())) {
                System.out.println(variables.get(argument.trim())); // Print the variable value
            } else {
                System.out.println(argument.trim()); // Print the argument as is
            }
            return true; // Command processed successfully
        }

        return false; // Default to false for unrecognized commands
    }
}
