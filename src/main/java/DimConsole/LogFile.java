package DimConsole;

import DimConsole.CoreFunc.Config;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogFile {
    public static final String LOG_DIRECTORY = Config.LogDir;
    public static final String LOG_FILE_PREFIX = "log_";
    public static final String LOG_FILE_SUFFIX = ".log";
    public static final long UPDATE_INTERVAL_MS = 300; // 0.3 seconds

    public static PrintStream originalOut;
    public static PrintStream originalErr;
    public static StringBuilder currentInput = new StringBuilder();
    public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private static PrintStream logStream; // To keep track of the log stream

    public static void setupLogging() {
        try {
            // Ensure the logs directory exists
            File logDir = new File(LOG_DIRECTORY);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Create a log file with the current date and time
            String logFileName = LOG_FILE_PREFIX + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS").format(new Date()) + LOG_FILE_SUFFIX;
            File logFile = new File(logDir, logFileName);

            // Save the original System.out and System.err
            originalOut = System.out;
            originalErr = System.err;

            // Redirect System.out and System.err to the log file
            logStream = new PrintStream(new FileOutputStream(logFile, true), true);
            System.setOut(new PrintStream(new MultiOutputStream(System.out, logStream), true));
            System.setErr(new PrintStream(new MultiOutputStream(System.err, logStream), true));

            Logger.info("LogFile", "Logging started. Output will be written to " + logFile.getAbsolutePath());

            // Start the periodic update task
            startPeriodicUpdate(logStream);
        } catch (IOException e) {
            Logger.error("LogFile", "Failed to set up logging: " + e.getMessage());
        }
    }

    public static void startPeriodicUpdate(PrintStream logStream) {
        executor.scheduleAtFixedRate(() -> {
            synchronized (currentInput) {
                if (currentInput.length() > 0) {
                    logStream.println(currentInput.toString());
                    logStream.flush();
                    currentInput.setLength(0); // Clear the input after logging
                }
            }
        }, 0, UPDATE_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    // Logging methods with hidden log support, now accepting a source parameter
    public static void Hinfo(String source, String message) {
        log(source, "INFO", message);
    }

    public static void Hwarn(String source, String message) {
        log(source, "WARN", message);
    }

    public static void Herror(String source, String message) {
        log(source, "ERROR", message);
    }

    public static void Hfatal(String source, String message) {
        log(source, "FATAL", message);
    }

    public static void Hdebug(String source, String message) {
        log(source, "DEBUG", message);
    }

    // Centralized logging method with source parameter
    private static void log(String source, String level, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        String logEntry = String.format("[%s] [%s] [%s] %s", timestamp, level, source, message);
        logStream.println(logEntry);
        logStream.flush();
    }



    public static class MultiOutputStream extends OutputStream {
        public final OutputStream[] outputStreams;

        public MultiOutputStream(OutputStream... outputStreams) {
            this.outputStreams = outputStreams;
        }

        @Override
        public void write(int b) throws IOException {
            for (OutputStream os : outputStreams) {
                os.write(b);
            }
        }

        @Override
        public void flush() throws IOException {
            for (OutputStream os : outputStreams) {
                os.flush();
            }
        }

        @Override
        public void close() throws IOException {
            for (OutputStream os : outputStreams) {
                os.close();
            }
        }
    }
}
