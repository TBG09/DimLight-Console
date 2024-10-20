package rsbdp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {


    // Method to log INFO messages
    public static void info(String source, String message) {
        log(source, "INFO", message);
    }

    // Method to log WARN messages
    public static void warn(String source, String message) {
        log(source, "WARN", message);
    }

    // Method to log ERROR messages
    public static void error(String source, String message) {
        log(source, "ERROR", message);
    }

    // Method to log FATAL messages
    public static void fatal(String source, String message) {
        log(source, "FATAL", message);
    }

    // Method to log DEBUG messages
    public static void debug(String source, String message) {
        log(source, "DEBUG", message);
    }

    // Private method to format and print the log message
    private static void log(String source, String level, String message) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = formatter.format(new Date());
        String output = "[" + dateString + "] [" + source + "/" + level + "] " + message;
        System.out.println(output);
    }
}
