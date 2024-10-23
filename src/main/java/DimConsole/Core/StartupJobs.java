package DimConsole.Core;

import DimConsole.LogFile;
import DimConsole.Logger;
import DimConsole.System.PublicVariables;

import java.io.IOException;

public class StartupJobs {

    public static void main() throws IOException {
        IOHandler.IO io = new IOHandler.IO();

        io.FileExists("." + PublicVariables.SeperatorOSType + "logs");
        if (io.FileExists("logs")) {
        } else {
            Logger.info("StartupJobs", "logs folder doesn't exist, creating.");
            io.CreateDirectory("logs");
            io.CreateDirectory("mods");
        }


        LogFile.setupLogging();
    }
}
