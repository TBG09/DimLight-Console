package rsbdp.Core;

import rsbdp.LogFile;
import rsbdp.Logger;

import java.io.IOException;

public class StartupJobs {

    public static void main() throws IOException {
        IOHandler.IO io = new IOHandler.IO();

        io.FileExists("." + PublicVariables.SeperatorOSType + "logs");
        if (io.FileExists("logs")) {
            // Nothing
        } else {
            Logger.info("StartupJobs", "logs folder doesn't exist, creating.");
            io.CreateDirectory("logs");
        }


        LogFile.setupLogging();
    }
}
