package rsbdp.Core;

import java.io.File;

public class PublicVariables {

    public static String VersionNum = "1.0.0.0002";
    public static String osType = System.getProperty("os.name").toLowerCase();
    public static String javaVer = System.getProperty("java.version");
    public static String deviceArch = System.getProperty("os.arch");
    public static String osVersion = System.getProperty("os.version");
    public static boolean isDevVer = true;
    boolean runningOnTermux = isRunningOnTermux();
    public static String ResourcePath = "resources";
    public static String SeperatorOSType = SeperatorType();

    private static boolean isRunningOnTermux() {
        File termuxFile = new File("/data/data/com.termux/files/home/.termux");
        return termuxFile.exists() && termuxFile.isDirectory();
    }

    private static String SeperatorType() {
        if (osType.contains("win")) {
            return "\\";
        } else {
            return "/";
        }
    }
}
