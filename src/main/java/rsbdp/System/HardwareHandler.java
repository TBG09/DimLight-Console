package rsbdp.System;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.ArrayList;
import java.util.List;

public class HardwareHandler {

    // Declare class fields to store the data
    public int GPUCount;
    public List<String> GPUList;
    public String CPUName;

    // Constructor
    public HardwareHandler() {
        // Initialize class fields in the constructor
        this.GPUList = new ArrayList<>();
    }

    // Method to get hardware information
    public void GetInfo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        // Get the list of GraphicsCards
        List<GraphicsCard> graphicsCards = hal.getGraphicsCards();

        // Set the GPU count based on the number of GraphicsCards
        this.GPUCount = graphicsCards.size();

        // Store the CPU name
        this.CPUName = hal.getProcessor().getProcessorIdentifier().getName();

        // Loop through the graphics cards and add their names to the GPU list
        for (GraphicsCard card : graphicsCards) {
            GPUList.add(card.getName());
        }
    }

    // Getter method for GPU count
    public int getGPUCount() {
        return this.GPUCount;
    }

    // Getter method for GPU list
    public List<String> getGPUList() {
        return this.GPUList;
    }

    // Getter method for CPU name
    public String getCPUName() {
        return this.CPUName;
    }
}
