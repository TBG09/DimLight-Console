package DimConsole.commands;

import DimConsole.Core.CommandList;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class polarfetch {
    public static void main(String arguementThatIsntLoved) {
        // Sorry for this very inefficient code

        SystemInfo systemInfo = new SystemInfo();

        CentralProcessor cpu = systemInfo.getHardware().getProcessor();
        String cpuModel = cpu.getProcessorIdentifier().getName();
        int physicalCores = cpu.getPhysicalProcessorCount();
        int logicalCores = cpu.getLogicalProcessorCount();
        long cpuFrequency = cpu.getProcessorIdentifier().getVendorFreq() / 1_000_000;

        CommandList commandList = new CommandList();
        commandList.executeCommand2("echo", "CPU Model: " + cpuModel);
        commandList.executeCommand2("echo", "Physical Cores: " + physicalCores);
        commandList.executeCommand2("echo", "Logical Cores: " + logicalCores);
        commandList.executeCommand2("echo", "CPU Frequency: " + cpuFrequency);

        commandList.executeCommand2("echo", "End (temp msg)");
    }
}
