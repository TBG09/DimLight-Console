package rsbdp.modLoading;

public interface Mod {
    void apply(); // Method for mods to apply their changes
    String getTargetClass(); // Optional method to specify the class being targeted
}
