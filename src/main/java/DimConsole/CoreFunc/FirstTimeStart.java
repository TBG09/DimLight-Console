package DimConsole.CoreFunc;

import DimConsole.Core.ConsoleManagement;
import DimConsole.Core.IOHandler;
import DimConsole.Core.Main;
import DimConsole.Core.StartupJobs;
import DimConsole.System.PublicVariables;
import com.sun.jna.platform.win32.Netapi32Util;

import java.util.Scanner;
public class FirstTimeStart {

    public static String UserUsername;
    public static void main() throws Exception {
        Scanner scanner = new Scanner(System.in);
        IOHandler io = new IOHandler();

            System.out.println("Hello and welcome to DimConsole!");
            Thread.sleep(2500);
            System.out.println("Since this is your first time being here, this setup will ride you through getting your experience ready!");
            Thread.sleep(2500);
            System.out.println("First, your username for this console, this will only be used in here.");
            System.out.print("Your username: ");
            String UserSetupInput = scanner.nextLine();
            UserUsername = UserSetupInput;
            Config.setConfigValue("Username", UserUsername);
            System.out.println("K, that's out of the way now, lets move on to passwor-... wait, what do you mean whats the point of passwords... *sigh*, fine no password.");
            Thread.sleep(2500);
            System.out.println("What shall be the default path for you when you open this console?");
            System.out.print("Default Path: ");
            UserSetupInput = scanner.nextLine();
            Config.setConfigValue("DefaultDir", UserSetupInput);
            System.out.println("You, should be ready to go, just note these can be changed in the config at config/DimConsole.json or config\\DimConsole.json");
            System.out.println("Good luck!");
            Thread.sleep(5000);
            ConsoleManagement.clearConsole("I dont want to");

    }
}
