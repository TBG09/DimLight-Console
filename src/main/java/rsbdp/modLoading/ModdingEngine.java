package rsbdp.modLoading;

import rsbdp.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class ModdingEngine {

    public static void setupModding() {
        File modsDir = new File("mods");
        if (modsDir.exists() && modsDir.isDirectory()) {
            for (File mod : modsDir.listFiles()) {
                if (mod.getName().endsWith(".jar")) {
                    try {
                        loadMod(mod);
                    } catch (IOException e) {
                        Logger.error("ModdingEngine", "Failed to load mod: " + mod.getName());
                    }
                }
            }
        } else {
            Logger.warn("ModdingEngine", "No mods directory found.");
        }
    }

    private static void loadMod(File modFile) throws IOException {
        URL modUrl = modFile.toURI().toURL();
        try (JarFile jarFile = new JarFile(modFile)) {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{modUrl}, ModdingEngine.class.getClassLoader());
            // Load the main class defined in the manifest
            String mainClass = jarFile.getManifest().getMainAttributes().getValue("Main-Class");
            if (mainClass != null) {
                Class<?> cls = classLoader.loadClass(mainClass);

                // Check if the loaded class implements the Mod interface
                if (Mod.class.isAssignableFrom(cls)) {
                    Mod modInstance = (Mod) cls.getDeclaredConstructor().newInstance();
                    modInstance.apply(); // Call the apply method to execute mod logic
                    Logger.info("ModdingEngine", "Loaded mod: " + modFile.getName() + " (Main-Class: " + mainClass + ")");
                } else {
                    Logger.warn("ModdingEngine", "Class " + mainClass + " in " + modFile.getName() + " does not implement Mod interface.");
                }
            } else {
                Logger.warn("ModdingEngine", "Main class not found in " + modFile.getName());
            }
        } catch (Exception e) {
            Logger.error("ModdingEngine", "Error loading mod: " + e.toString());
            e.printStackTrace();  // Print the full stack trace to the console for debugging
        }
    }
}
