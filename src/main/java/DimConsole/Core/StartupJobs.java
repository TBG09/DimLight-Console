package DimConsole.Core;

import DimConsole.CoreFunc.Config;
import DimConsole.LogFile;
import DimConsole.System.PublicVariables;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StartupJobs {

    public static void main() throws IOException {
        createNecessaryDirectories();
        setupConfigFile();
        loadConfiguration();
        setupLogging();
        synchronizeStartupMessage();
    }

    // Stage 1: Create necessary directories
    public static void createNecessaryDirectories() throws IOException {
        IOHandler.IO io = new IOHandler.IO();
        io.CreateDirectory("logs");
        io.CreateDirectory("config");
        io.CreateDirectory("config/resources");

    }

    // Stage 2: Setup config file if it doesn’t exist
    public static void setupConfigFile() throws IOException {
        IOHandler.IO io = new IOHandler.IO();
        Path configPath = Paths.get("config/DimConsole.json");

        // Check if the config file exists and is not empty
        if (!Files.exists(configPath) || Files.size(configPath) == 0) {
            // If the file doesn't exist or is empty, write the default config
            io.createFile(configPath.toString());
            io.write(configPath.toString(), """
                {
                    "PathVariables": [],
                    "EnvVariables": [],
                    "LogDir": "logs"
                    ,"StartupMessageFile": "config-resources-StartupMsg.txt"
                    ,"DefaultDir": ""
                    ,"Username": ""
                    ,"FirstTimeSetup": false
                }
                """);
        }
    }

    // Stage 3: Load configuration settings
    public static void loadConfiguration() {
        Config.readConfig();
    }

    // Stage 4: Setup logging
    public static void setupLogging() {
        LogFile.setupLogging();
    }

    // Stage 5: Synchronize the startup message file
    public static void synchronizeStartupMessage() throws IOException {
        Path localFilePath = Paths.get(PublicVariables.ResourceDir + "StartupMsg.txt");
        URL remoteFileURL = new URL(PublicVariables.StartupMsgURL);

        // Check for internet access and download file if accessible
        try {
            HttpURLConnection connection = (HttpURLConnection) remoteFileURL.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000); // Timeout in milliseconds
            connection.connect();

            // If successful, proceed with file comparison
            if (connection.getResponseCode() == 200) {
                String localContent = Files.exists(localFilePath) ? Files.readString(localFilePath) : "";
                String remoteContent = new String(remoteFileURL.openStream().readAllBytes());

                if (!localContent.equals(remoteContent)) {
                    // Write the remote content to the local file if they differ
                    Files.writeString(localFilePath, remoteContent);
                    LogFile.Hinfo("StartupJobs", "Wrote new StartupMsg.txt");
                }
            } else {
                LogFile.Hwarn("StartupJobs", "No internet access, skipping StartupMsg check.");
            }
        } catch (IOException e) {
            // Log the warning if unable to reach the internet
            LogFile.Hwarn("StartupJobs", "No internet access, skipping StartupMsg check.");
        }
    }
}
