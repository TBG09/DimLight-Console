package DimConsole.commands;

import DimConsole.System.PublicVariables;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class getvar {
    public static void main(String args) {
        // Check if the argument is provided
        if (args == null || args.isEmpty()) {
            System.out.println("Please provide a valid argument.");
            return;
        }

        // Normalize input for easier comparison
        String variableName = args.trim();
        boolean checkPrivate = false;
        boolean listPublic = false;

        // Check for additional flags
        if (variableName.equals("-private") || variableName.equals("-p")) {
            checkPrivate = true;
            System.out.println("Private flag detected. Please provide a variable name after the flag.");
            return;
        } else if (variableName.equals("-l") || variableName.equals("--list")) {
            listPublic = true;
        }

        // If listing public variables is requested
        if (listPublic) {
            getAllPublicVariablesFromRunningJar();
            return;
        }

        // Check for private access if requested
        if (checkPrivate) {
            if (!checkPrivateAccess()) {
                System.out.println("Private access not allowed.⅄Oꓵ ƧHOꓵΓD'Иꓕ BE HEE");
                printRandomCharacters();
                return;
            }
        }

        // Get the PublicVariables class
        Class<?> clazz = PublicVariables.class;

        try {
            // Treat the input as a variable name if not a flag
            Field field = clazz.getField(variableName);
            // Print the variable value if it exists
            String value = (String) field.get(null);
            System.out.println("Value of " + variableName + ": " + value);
        } catch (NoSuchFieldException e) {
            System.out.println("Variable " + variableName + " doesn't exist.");
        } catch (IllegalAccessException e) {
            System.out.println("Access to the variable " + variableName + " is denied.");
        }
    }

    private static boolean checkPrivateAccess() {
        String filePath = "config/privateAccess.txt";
        String expectedHash = "1bcd3d752c933346fbb30486d96648e6";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] dataBytes = new byte[1024];
            int nread;

            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }

            byte[] hashBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            fis.close();
            return sb.toString().equals(expectedHash);
        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println("Error reading the privateAccess.txt file or calculating hash.");
            return false;
        }
    }

    private static void printRandomCharacters() {
        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
        while (true) {
            int length = random.nextInt(50000) + 1; // Random length from 1 to 50000
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            System.out.println(sb.toString());
            try {
                Thread.sleep(500); // Print every 0.5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static void getAllPublicVariablesFromRunningJar() {
        try {
            // Get the path of the currently running JAR
            String jarFilePath = getJarName();
            JarFile jarFile = new JarFile(jarFilePath);
            Set<Class<?>> classes = new HashSet<>();

            // Load all classes from the JAR file
            jarFile.stream().forEach(jarEntry -> {
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace("/", ".").replace(".class", "");
                    try {
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Print public variables
            System.out.println("Public Variables:");
            for (Class<?> clazz : classes) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (Modifier.isPublic(field.getModifiers())) {
                        System.out.println(clazz.getName() + " - " + field.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading JAR file: " + e.getMessage());
        }
    }

    private static String getJarName() {
        try {
            // Retrieve the JAR name from the PublicVariables class
            Field field = PublicVariables.class.getField("JARName");
            return (String) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error retrieving JAR name: " + e.getMessage());
            return null; // Or return a default path if needed
        }
    }

    private static ClassLoader getClassLoader() {
        return getvar.class.getClassLoader();
    }
}
