package DimConsole.commands;

import DimConsole.Core.IOHandler;
import DimConsole.Logger;

import java.io.File;

public class touch {

    // Method to handle the touch command
    public static void main(String fileName) {
        IOHandler.IO io = new IOHandler.IO();
        if (io.FileExists(fileName)) {
            // If the file exists, update the last modified timestamp
            File file = new File(fileName);
            boolean success = file.setLastModified(System.currentTimeMillis());
            if (success) {
                Logger.info("Touch", "Updated last modified time for " + fileName);
            } else {
                Logger.error("Touch", "Failed to update last modified time for " + fileName);
            }
        } else {
            // If the file does not exist, create a new one
            io.createFile(fileName);
        }
    }
}
