package DimConsole.CoreFunc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import DimConsole.System.PublicVariables;

public class StartupMessage {
    public static void main() {
        // Specify the path to the Startup.txt file
        String path = "config/resources/StartupMsg.txt";

        // Read lines from the file
        List<String> lines = readLinesFromFile(path);
        if (lines.isEmpty()) {
            System.out.println("No startup message:(");
            return;
        }

        // Generate a random index to select a line
        Random random = new Random();
        int randomIndex = random.nextInt(lines.size()); // Get a random number between 0 and <number of lines> (im going insane, help)

        // Get the random message from the selected line
        String randomMessage = lines.get(randomIndex).trim(); // Get the message and trim whitespace

        // Print the random message
        System.out.println("Message for this startup: " + randomMessage);
    }

    private static List<String> readLinesFromFile(String path) {
        List<String> validLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Check for non-empty lines
                    validLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return validLines;
    }
}
