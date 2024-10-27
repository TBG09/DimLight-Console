package DimConsole.commands;

import DimConsole.Core.IOHandler;
import java.io.IOException;

public class rm {
    public static void main(String ObjectRemove) {
        IOHandler.IO io = new IOHandler.IO();

        // Validate input
        if (ObjectRemove == null || ObjectRemove.trim().isEmpty()) {
            System.out.println("Please specify a file or directory to remove.");
            return;
        }

        if (io.FileExists(ObjectRemove)) {
            io.delete(ObjectRemove);
            System.out.println("Removed " + ObjectRemove);
        } else {
            System.out.println("File or folder does not exist: " + ObjectRemove);
        }
    }
}
