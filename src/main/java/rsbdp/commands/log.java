package rsbdp.commands;

import rsbdp.Logger;

public class log {
    public static void main(String arguments) {
        if (arguments.toLowerCase().contains("print")) {
            String[] parts = arguments.split("print", 2);
            String Subcommand = "print";
            String textToLog = parts.length > 1 ? parts[1].trim() : "";
            Logger.info("User", textToLog);


        }
        if (arguments.toLowerCase().contains("help")) {
            System.out.println("""
                    Subcommands for log'
                    
                    print - will log a message.""");
        }
    }
}
