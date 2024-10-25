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
        } catch (IOException e) {
            Logger.error("Config", "Failed to read configuration: " + e.getMessage());
        }
    }
}
