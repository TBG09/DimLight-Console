package DimConsole.Core;

import DimConsole.CoreFunc.FileFormat;
import DimConsole.System.PublicVariables;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        StartupJobs.main();

        System.out.println("DimLight Console - " + PublicVariables.VersionNum);
        if (PublicVariables.LinuxDistro == "Unknown Linux Distro" && PublicVariables.osType.toLowerCase().contains("win")) {
            System.out.println("Running on " + PublicVariables.osType);
        } else if (PublicVariables.runningOnTermux){
           System.out.println("Running on termux with Linux " + PublicVariables.osVersion);
        } else {
            System.out.println("Running on " + PublicVariables.LinuxDistro);
        }
        System.out.println("Welcome!");



        UserInput();



    }

    public static void UserInput() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print(PublicVariables.currentDirectory + " >");
        String[] inputParts = scanner.nextLine().split(" ", 2);
        String command = inputParts[0];


        if (command.toLowerCase().contains(".rsbdp".toLowerCase())) {
            FileFormat.main(new String[]{command});
        } else {
            String arguments = inputParts.length > 1 ? inputParts[1] : "";
            CommandList commandList = new CommandList();
            commandList.executeCommand(command, arguments);
        }
    }

}
