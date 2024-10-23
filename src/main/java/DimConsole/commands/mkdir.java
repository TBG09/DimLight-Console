package DimConsole.commands;

import DimConsole.Core.IOHandler;

public class mkdir {
   public static void main(String FolderName) {
       IOHandler.IO io = new IOHandler.IO();
        try {
            io.CreateDirectory(FolderName);
            System.out.println("Directory " + FolderName + "Created.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




   }
}
