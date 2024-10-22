package DimConsole.Core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import DimConsole.commands.*;

public class CommandList {

    private Map<String, Consumer<String>> commandMap;

    public CommandList() {
        commandMap = new HashMap<>();

        // Register commands here
        commandMap.put("echo", echo::main);
        commandMap.put("getvar", getvar::main);
        commandMap.put("help", help::main);
        commandMap.put("hlp", help::main);
        commandMap.put("log", log::main);
        commandMap.put("cls", ConsoleManagement::clearConsole);
        commandMap.put("clear", ConsoleManagement::clearConsole);
        commandMap.put("polarfetch", polarfetch::main);
        commandMap.put("wait", wait::main);
        commandMap.put("cd", cd::main);
        commandMap.put("dir", dir::main);
        commandMap.put("mkdir", mkdir::main);
        commandMap.put("pwd", pwd::main);
        commandMap.put("rename", rename::main);
        commandMap.put("ren", rename::main);
        commandMap.put("rm", rm::main);
        commandMap.put("remove", rm::main);
        commandMap.put("ls", dir::main);
    }

    // Method to execute a command based on input
    public void executeCommand(String command, String argument) throws Exception {
        Consumer<String> action = commandMap.get(command.toLowerCase());
        if (action != null) {
            action.accept(argument);  // Pass the argument to the command
            Main.UserInput();
        } else {
            System.out.println("Unknown command: " + command);
            Main.UserInput();
        }
    }
    // Some method to handle commands in a different way
    public void executeCommand2(String command, String argument) {
        Consumer<String> action = commandMap.get(command.toLowerCase());
        if (action != null) {
            action.accept(argument);  // Pass the argument to the command

        } else {
            System.out.println("Unknown command: " + command);

        }
    }
}
