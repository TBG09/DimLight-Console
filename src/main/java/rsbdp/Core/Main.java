package rsbdp.Core;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        StartupJobs.main();

        System.out.println("rsbdp console - " + PublicVariables.VersionNum);
        if (PublicVariables.LinuxDistro == "Unknown Linux Distro" && PublicVariables.osType.toLowerCase().contains("win")) {
            System.out.println("Running on " + PublicVariables.osType);
        } else {
            System.out.println("Running on " + PublicVariables.LinuxDistro);
        }
        System.out.println("Welcome!");


        UserInput();



    }

    public static void UserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(PublicVariables.currentDirectory + " >");
        String[] inputParts = scanner.nextLine().split(" ", 2);
        String command = inputParts[0];


        if (command.toLowerCase().contains(".rsbdp".toLowerCase())) {

        } else {
            String arguments = inputParts.length > 1 ? inputParts[1] : "";
            CommandList commandList = new CommandList();
            commandList.executeCommand(command, arguments);
        }
    }

}
