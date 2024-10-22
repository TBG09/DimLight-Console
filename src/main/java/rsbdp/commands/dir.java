package rsbdp.commands;

import rsbdp.Core.IOHandler;
import rsbdp.Core.PublicVariables;

public class dir {
    public static void main(String Directory) {
        IOHandler.IO io = new IOHandler.IO();


        if (Directory == null || Directory.isEmpty()) {

            Directory = PublicVariables.currentDirectory;
        }

        io.listDirContents(Directory);
    }


    
}
