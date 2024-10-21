package rsbdp.commands;

import rsbdp.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class wait {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // This method will be called to wait for the specified time
    public static void main(String time) {
        try {
            System.out.println("Waiting for " + time + " seconds...");
            TimeUnit.SECONDS.sleep(Long.parseLong(time));
        } catch (InterruptedException e) {
            Logger.warn("wait", "Sleep was interrupted.");
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
