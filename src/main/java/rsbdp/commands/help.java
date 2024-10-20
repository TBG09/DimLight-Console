package rsbdp.commands;

public class help {
    public static void main(String argumentThingThatIsNeverUsed) {
        System.out.println("""
                commands are not case sensitive!
                help or hlp - shows this help message
                echo <string> - prints back whatever is given
                getvar <Variable> - Gets whatever variable is given.
                cls or clear - clears the console""");
    }

}
