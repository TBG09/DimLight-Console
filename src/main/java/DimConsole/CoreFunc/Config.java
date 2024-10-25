package DimConsole.CoreFunc;

import DimConsole.System.PublicVariables;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import DimConsole.Logger;

public class Config {
    public static String MainConfig;
    public static String PathVariables;
    public static String EnvVariables;
    public static String LogDir;
    public static String StartupMsgFile;
    public static String DefaultDir; // Variable for DefaultDir

    public static void readConfig() {
        MainConfig = "config" + PublicVariables.SeperatorOSType + "DimConsole.json";

        // Use Jackson to read the configuration file
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON configuration file
            JsonNode configJson = objectMapper.readTree(new File(MainConfig));

            // Extract values from the JSON object
            if (configJson.has("LogDir")) {
                LogDir = configJson.get("LogDir").asText();
            } else {
                LogDir = "logs"; // Default value if LogDir is not specified
            }

            // Handle DefaultDir
            if (configJson.has("DefaultDir")) {
                DefaultDir = configJson.get("DefaultDir").asText();
            } else {
                DefaultDir = ""; // Set DefaultDir to an empty string if not specified
            }

            // Set currentDirectory based on DefaultDir
            if (DefaultDir.isEmpty()) {
                PublicVariables.currentDirectory = PublicVariables.HomeDir; // Set to home directory if DefaultDir is empty
            } else {
                File dirFile = new File(DefaultDir);
                if (dirFile.exists() && dirFile.isDirectory()) {
                    // Set currentDirectory to DefaultDir if it exists and is a directory
                    PublicVariables.currentDirectory = DefaultDir;
                } else {
                    // If the path is invalid, do not change currentDirectory
                    Logger.warn("Config", "Invalid path specified for DefaultDir: " + DefaultDir);
                }
            }

            // Handle StartupMsgFile
            if (configJson.has("StartupMessageFile")) {
                StartupMsgFile = configJson.get("StartupMessageFile").asText().replace("-", PublicVariables.SeperatorOSType);
            } else {
                StartupMsgFile = ""; // Default or handle accordingly if not specified
            }

        } catch (IOException e) {
            Logger.error("Config", "Failed to read configuration: " + e.getMessage());
        }
    }
}
