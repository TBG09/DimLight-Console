package DimConsole.commands;

import DimConsole.Core.IOHandler;
import DimConsole.System.PublicVariables;

public class dir {
    public static void main(String Directory) {
        IOHandler.IO io = new IOHandler.IO();


        if (Directory == null || Directory.isEmpty()) {

            Directory = PublicVariables.currentDirectory;
        }

        io.listDirContents(Directory);
    }


    
}
