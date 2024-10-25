package DimConsole.CoreFunc;

import DimConsole.System.PublicVariables;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
        MainConfig = "config" + PublicVariables.SeperatorOSType + "DimConsole.json";
        PathVariables = new ArrayList<>();
        EnvVariables = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode configJson = objectMapper.readTree(new File(MainConfig));

            // Extract LogDir
            if (configJson.has("LogDir")) {
                LogDir = configJson.get("LogDir").asText();
            } else {
                LogDir = "logs";
            }

            // Extract DefaultDir and set currentDirectory
            if (configJson.has("DefaultDir")) {
                DefaultDir = configJson.get("DefaultDir").asText();
            } else {
                DefaultDir = "";
            }

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

            // Extract StartupMsgFile
            if (configJson.has("StartupMessageFile")) {
                StartupMsgFile = configJson.get("StartupMessageFile").asText().replace("-", PublicVariables.SeperatorOSType);
            } else {
                StartupMsgFile = "";
            }

            // Extract PathVariables if present and non-empty
            if (configJson.has("PathVariables")) {
                ArrayNode pathVariablesNode = (ArrayNode) configJson.get("PathVariables");
                if (pathVariablesNode.size() > 0) {
                    for (JsonNode pathVar : pathVariablesNode) {
                        PathVariables.add(pathVar.asText());
                    }
                }
            }

            // Extract EnvVariables if present and non-empty
            if (configJson.has("EnvVariables")) {
                ArrayNode envVariablesNode = (ArrayNode) configJson.get("EnvVariables");
                if (envVariablesNode.size() > 0) {
                    for (JsonNode envVar : envVariablesNode) {
                        EnvVariables.add(envVar.asText());
                    }
                }
            }

        } catch (IOException e) {
            Logger.error("Config", "Failed to read configuration: " + e.getMessage());
        }
    }
}
