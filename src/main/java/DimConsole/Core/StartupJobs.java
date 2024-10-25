package DimConsole.Core;

import DimConsole.CoreFunc.Config;
import DimConsole.LogFile;
import DimConsole.Logger;
import DimConsole.System.PublicVariables;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StartupJobs {

    public static void main() throws IOException {
        IOHandler.IO io = new IOHandler.IO();

        // Check and create necessary directories
        io.CreateDirectory("logs");
        io.CreateDirectory("mods");
        io.CreateDirectory("config");

        // Define the path for the config file
        Path configPath = Paths.get("config" + PublicVariables.SeperatorOSType + "DimConsole.json");

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
                }
                """);
        }

        // Read the config before setting up logging (idk why)
        Config.readConfig();

        LogFile.setupLogging();
    }
}
