package DimConsole;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static PrintStream logStream;

    // Static block to initialize the log file
    static {
        try {
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            String logFileName = "log_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".log";
            File logFile = new File(logDir, logFileName);
            logStream = new PrintStream(new FileOutputStream(logFile, true), true);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

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

    // Method to log hidden messages directly to the file
    public static void hidden(String source, String message) {
        String logEntry = String.format("[%s] [HIDDEN] [%s] %s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), source, message);
        logStream.println(logEntry);
        logStream.flush(); // Ensure the message is written to the file immediately
    }

    // Centralized logging method
    private static void log(String source, String level, String message) {
        String logEntry = String.format("[%s] [%s] [%s] %s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), source, level, message);
        System.out.println(logEntry); // Print to console
        logStream.println(logEntry); // Write to log file
        logStream.flush(); // Ensure the message is written to the file immediately
    }
}
