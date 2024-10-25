package DimConsole.System;

import DimConsole.Core.IOHandler;
import DimConsole.CoreFunc.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class  PublicVariables {

    public static String VersionNum = "1.2.0.0077";
    public static String osType = System.getProperty("os.name").toLowerCase();
    public static String javaVer = System.getProperty("java.version");
    public static String deviceArch = System.getProperty("os.arch");
    public static String osVersion = System.getProperty("os.version");
    public static boolean isDevVer = true;
    public static boolean runningOnTermux = isRunningOnTermux();
    public static String ResourceDir = "config/resources/";
    public static String SeperatorOSType = SeperatorType();
    public static String totalMemoryGB = String.valueOf((long) Runtime.getRuntime().totalMemory() / (1024 * 1024 * 1024));
    public static String totalMemoryMB = String.valueOf((long) Runtime.getRuntime().totalMemory() / (1024 * 1024));
    public static String totalMemoryKB = String.valueOf((long) Runtime.getRuntime().totalMemory());
    public static String currentDirectory = System.getProperty("user.dir");
    public static String LinuxDistro = getLinuxDistro();
    public static String Username = System.getProperty("user.name");
    public static String RootType = RootType();
    public static String HomeDir = System.getProperty("user.home");
    public static String StartupMsgURL = ("https://raw.githubusercontent.com/TBG09/DimLight-Console/refs/heads/main/build/libs/config/resources/StartupMsg.txt");
    public static boolean FirstTimeStart = FirstTimeSetup();


    private static boolean isRunningOnTermux() {
        File termuxFile = new File("/data/data/com.termux/files/home/.termux");
        return termuxFile.exists() && termuxFile.isDirectory();
    }

    private static boolean FirstTimeSetup() {
        IOHandler.IO io = new IOHandler.IO();
        boolean ReadJSONReturnString = false;
        String FirstReadJSON = Config.getConfigValue("FirstTimeSetup", "Stop");
        if (FirstReadJSON.toLowerCase().contains(FirstReadJSON)) {
            ReadJSONReturnString = true;
        } else {
            ReadJSONReturnString = false;
        }
        return ReadJSONReturnString;
    }

    private static String RootType() {
        String root;
        if (osType.toLowerCase().contains("win")) {
            root = "C:";

        } else {
            root = "/";
        }
        return root;
    }

    private static String SeperatorType() {
        if (osType.contains("win")) {
            return "\\";
        } else {
            return "/";
        }
    }

    private static String getLinuxDistro() {
        String[] filesToCheck = {"/etc/os-release", "/etc/lsb-release", "/etc/debian_version", "/etc/redhat-release"};

        for (String filePath : filesToCheck) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("NAME=")) {
                        return line.split("=")[1].replaceAll("\"", "").trim();
                    }
                }
            } catch (IOException e) {
                // Ignore the exception and continue checking the next file
            }
        }

        return "Unknown Linux Distro"; // Default value if no known distro was found
    }
}
