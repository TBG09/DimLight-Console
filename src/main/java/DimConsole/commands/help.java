package DimConsole.commands;

public class help {
    public static void main(String argumentThingThatIsNeverUsed) {
        System.out.println("""
                commands are not case sensitive!
                help or hlp - shows this help message
                echo <string> - prints back whatever is given
                getvar <Variable> - Gets whatever variable is given.
                cls or clear - clears the console
                cd <directory> - changes directory
                dir <directory> - lists the files and folders in the current for or specified folder.
                mkdir <Directory> - Creates a directory.
                pwd - Displays the current directory path.
                rename or ren - renames the specified file.
                rm or remove - removes the folder or file.
                wait <seconds> - waits the amount of seconds given.
                net <sub command> - do net help if you want help lol.
                reload - reloads the config
                messagebox - allows you to create your own message boxes, do help as an argument if you dont know how to use it.""");
    }

}
