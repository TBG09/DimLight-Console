package DimConsole.CoreFunc;

import DimConsole.System.PublicVariables;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import DimConsole.Logger;

public class Config {
    public static String MainConfig;
    public static List<String> PathVariables;
    public static List<String> EnvVariables;
    public static String LogDir;
    public static String StartupMsgFile;
    public static String DefaultDir;

    public static void readConfig() {
        MainConfig = "config/DimConsole.json";
        PathVariables = new ArrayList<>();
        EnvVariables = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode configJson = objectMapper.readTree(new File(MainConfig));

            // Use getConfigValue method to get each value
            LogDir = getConfigValue("LogDir", "logs");
            DefaultDir = getConfigValue("DefaultDir", "");

            // Set currentDirectory based on DefaultDir
            if (DefaultDir.isEmpty()) {
                PublicVariables.currentDirectory = PublicVariables.HomeDir;
            } else {
                File dirFile = new File(DefaultDir);
                if (dirFile.exists() && dirFile.isDirectory()) {
                    PublicVariables.currentDirectory = DefaultDir;
                } else {
                    Logger.warn("Config", "Invalid path specified for DefaultDir: " + DefaultDir);
                }
            }

            StartupMsgFile = getConfigValue("StartupMessageFile", "").replace("-", "/");

            // Load PathVariables
            JsonNode pathVariablesNode = configJson.get("PathVariables");
            if (pathVariablesNode instanceof ArrayNode) {
                for (JsonNode pathVar : (ArrayNode) pathVariablesNode) {
                    PathVariables.add(pathVar.asText());
                }
            }

            // Load EnvVariables
            JsonNode envVariablesNode = configJson.get("EnvVariables");
            if (envVariablesNode instanceof ArrayNode) {
                for (JsonNode envVar : (ArrayNode) envVariablesNode) {
                    EnvVariables.add(envVar.asText());
                }
            }

        } catch (IOException e) {
            Logger.error("Config", "Failed to read configuration: " + e.getMessage());
        }
    }

    // Method to retrieve a configuration value with a default value if key is not found
    public static String getConfigValue(String key, String defaultValue) {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new File(MainConfig);

        try {
            JsonNode configJson = objectMapper.readTree(configFile);
            JsonNode valueNode = configJson.get(key);

            if (valueNode != null) {
                return valueNode.asText();
            } else {
                Logger.warn("Config", "Key not found in config: " + key + " - using default: " + defaultValue);
            }
        } catch (IOException e) {
            Logger.error("Config", "Failed to retrieve config value: " + e.getMessage());
        }

        return defaultValue;
    }

    // Method to set a configuration value
    public static void setConfigValue(String key, String newValue) {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new File(MainConfig);

        try {
            JsonNode configJson = objectMapper.readTree(configFile);
            if (configJson instanceof ObjectNode) {
                ((ObjectNode) configJson).put(key, newValue);

                // Write the updated JSON back to the file
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(configFile, configJson);
                Logger.info("Config", "Updated config key: " + key + " to value: " + newValue);
            } else {
                Logger.error("Config", "Failed to update configuration. Root node is not an object.");
            }
        } catch (IOException e) {
            Logger.error("Config", "Failed to update configuration: " + e.getMessage());
        }
    }
}
