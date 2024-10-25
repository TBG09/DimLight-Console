package DimConsole.CoreFunc;

import DimConsole.Logger;

import static java.lang.System.exit;

public class Halt {
    public static void main(String Type,String Source, String Message, Integer ExitCode) {
        if (Type == "error") {
            Logger.error(Source, Message);
            exit(ExitCode);
        } else {
            Logger.fatal(Source, Message);
            exit(ExitCode);
        }

    }
}
