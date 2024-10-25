package DimConsole.commands;

import javax.swing.*;

public class MessageBox {
    public static void main(String argumentsWHYYY) {
        // Check if the input is null or empty
        if (argumentsWHYYY == null || argumentsWHYYY.isEmpty()) {
            System.out.println("No arguments provided.");
            return;
        }

        String title = "Default Title";  // Fallback title
        String message = "Default Message";  // Fallback message

        // Split the input string into arguments
        String[] args = argumentsWHYYY.split("(?<!\\\\) "); // Split on spaces not preceded by a backslash

        // Check for the 'help' argument
        if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
            printHelpMessage();
            return;
        }

        // Parse the arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-t") && i + 1 < args.length) {
                // Extract title
                title = extractQuotedArgument(args, i + 1);
                i += countQuotedParts(args, i + 1); // Move index past the title
            } else if (args[i].equalsIgnoreCase("-m") && i + 1 < args.length) {
                // Extract message
                message = extractQuotedArgument(args, i + 1);
                i += countQuotedParts(args, i + 1); // Move index past the message
            }
        }

        // Create and display the message box
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private static String extractQuotedArgument(String[] args, int startIndex) {
        StringBuilder result = new StringBuilder();

        if (startIndex < args.length && args[startIndex].startsWith("\"")) {
            // Handle quoted argument
            for (int i = startIndex; i < args.length; i++) {
                result.append(args[i]);
                if (args[i].endsWith("\"")) {
                    break; // End of quoted argument
                }
                result.append(" "); // Append space if not the last part of the quote
            }
            // Remove quotes
            String value = result.toString().replaceAll("^\"|\"$", "");
            return value;
        }

        // If no quotes, return the next argument
        return args[startIndex];
    }

    private static int countQuotedParts(String[] args, int startIndex) {
        int count = 0;

        if (startIndex < args.length && args[startIndex].startsWith("\"")) {
            // Count the number of parts in the quoted argument
            for (int i = startIndex; i < args.length; i++) {
                count++;
                if (args[i].endsWith("\"")) {
                    break; // End of quoted argument
                }
            }
        } else {
            // If no quotes, count one
            count = 1;
        }

        return count;
    }

    private static void printHelpMessage() {
        String helpMessage = "Usage:\n" +
                "MessageBox -t \"Title\" -m \"Message\"\n" +
                "Options:\n" +
                "-t: Specify the title of the message box.\n" +
                "-m: Specify the message to display in the message box.\n" +
                "help: Show this help message.";
        JOptionPane.showMessageDialog(null, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
}
