package DimConsole.System;

import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.platform.win32.Kernel32;
public class Hardware {

    public static long AvailableMemoryMB;
    public static void main() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        WinBase.MEMORYSTATUSEX memoryStatus = new WinBase.MEMORYSTATUSEX();
        AvailableMemoryMB = memoryStatus.ullAvailPhys.longValue();
    }
}
