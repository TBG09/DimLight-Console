package DimConsole.commands;

import DimConsole.CoreFunc.Config;
import DimConsole.Logger;

public class reload {
    public static void main(String IdcShutupArgument) {
        Logger.info("Reload", "Reloading config");
        Config.readConfig();
        Logger.info("Reload", "Successfully reloaded config!");
    }
}
