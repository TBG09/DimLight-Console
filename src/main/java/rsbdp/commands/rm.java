package rsbdp.commands;

import rsbdp.Core.IOHandler;
import rsbdp.Logger;

public class rm {
    public static void main(String ObjectRemove) {
        IOHandler.IO io = new IOHandler.IO();
        if (io.FileExists(ObjectRemove)) {
            io.delete(ObjectRemove);
            System.out.println("Removed " + ObjectRemove);
        } else {
            System.out.println("Folder/File " + " Doesn't exist.");
        }

    }
}
