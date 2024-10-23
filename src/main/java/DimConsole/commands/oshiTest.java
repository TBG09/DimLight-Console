package DimConsole.commands;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class oshiTest {
    public static void main(String ArgThingIDK) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        System.out.println("Processor: " + cpu);
    }
}
