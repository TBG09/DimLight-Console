package DimConsole.Core;

import DimConsole.Logger;
import DimConsole.System.PublicVariables;

import java.io.IOException;

public class ConsoleManagement {

    public static void clearConsole(String meepThingThatsNotGonnaBeUsedEither) {
        String os = PublicVariables.osType.toLowerCase();

        try {
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                // Unix-like (Linux, macOS)
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                // Unsupported OS
                Logger.warn("ConsoleManagement","Unable to determine OS. Cannot clear console.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}