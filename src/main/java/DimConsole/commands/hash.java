package DimConsole.commands;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;
import java.util.List;

public class hash {
    private static final List<String> SUPPORTED_ALGORITHMS = Arrays.asList(
            "MD5", "SHA-256", "Base64", "SHA-1", "SHA-512", "SHA-384", "BLAKE2b",
            "SHA3-224", "SHA3-256", "SHA3-384", "SHA3-512", "RIPEMD160"
    );

    public static void main(String ArgsThing) {
        Security.addProvider(new BouncyCastleProvider());

        if (ArgsThing == null || ArgsThing.isBlank()) {
            System.out.println("Usage: hash <subcommand> [options]");
            return;
        }

        // Split the input arguments by spaces for parsing
        String[] args = ArgsThing.trim().split(" ", 3);
        String subcommand = args[0];

        try {
            switch (subcommand.toLowerCase()) {
                case "help":
                    printHelp();
                    break;
                case "file":
                    if (args.length != 3) {
                        System.out.println("Usage: hash file \"<file_name/path>\" <algorithm>");
                        return;
                    }
                    handleFileSubcommand(args[1], args[2]);
                    break;
                case "text":
                    if (args.length != 3) {
                        System.out.println("Usage: hash text \"<plain_text>\" <algorithm>");
                        return;
                    }
                    handleTextSubcommand(args[1], args[2]);
                    break;
                default:
                    System.out.println("Unknown subcommand: " + subcommand);
                    printHelp();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void printHelp() {
        System.out.println("Supported commands:");
        System.out.println("1. help - Show this help message.");
        System.out.println("2. file \"<file_name/path>\" <algorithm> - Hash the contents of the specified file.");
        System.out.println("3. text \"<plain_text>\" <algorithm> - Hash the provided plain text.");
        System.out.println("Supported algorithms: " + SUPPORTED_ALGORITHMS);
    }

    private static void handleFileSubcommand(String filePath, String algorithm) {
        if (!new File(filePath).exists()) {
            System.out.println("File does not exist: " + filePath);
            return;
        }

        if (!SUPPORTED_ALGORITHMS.contains(algorithm)) {
            System.out.println("Invalid algorithm. Supported algorithms: " + SUPPORTED_ALGORITHMS);
            return;
        }

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            String hashValue = computeHash(fileBytes, algorithm);
            System.out.println("Hash of file (" + filePath + ") using " + algorithm + ": " + hashValue);
        } catch (Exception e) {
            System.out.println("Error reading file or computing hash: " + e.getMessage());
        }
    }

    private static void handleTextSubcommand(String plainText, String algorithm) {
        if (!SUPPORTED_ALGORITHMS.contains(algorithm)) {
            System.out.println("Invalid algorithm. Supported algorithms: " + SUPPORTED_ALGORITHMS);
            return;
        }

        try {
            String hashValue = computeHash(plainText.getBytes(), algorithm);
            System.out.println("Hash of text using " + algorithm + ": " + hashValue);
        } catch (Exception e) {
            System.out.println("Error computing hash: " + e.getMessage());
        }
    }

    private static String computeHash(byte[] input, String algorithm) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hash = digest.digest(input);
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
