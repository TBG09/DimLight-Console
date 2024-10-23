package DimConsole.commands;

import DimConsole.Core.CommandList;
import DimConsole.System.PublicVariables;


public class polarfetch {
    public static void main(String arguementThatIsntLoved) {


        CommandList commandList = new CommandList();
        // Sorry if you needed the code i deleted :(
        if (PublicVariables.LinuxDistro == "Unknown Linux Distro" && PublicVariables.osType.toLowerCase().contains("win")) {
            System.out.println("OS: " + PublicVariables.osVersion);
        } else {
            System.out.println("OS: " + PublicVariables.LinuxDistro);
        }
    }
}
