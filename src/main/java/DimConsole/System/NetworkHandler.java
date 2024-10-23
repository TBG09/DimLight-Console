package DimConsole.System;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkHandler {

    public long getFileSize(String urlStr) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
        connection.setRequestMethod("HEAD");
        return connection.getContentLengthLong();
    }

    public void downloadFile(String urlStr, String path) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        try (InputStream in = new BufferedInputStream(connection.getInputStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long totalBytesRead = 0;

            long startTime = System.currentTimeMillis();
            long lastTime = startTime;
            long lastBytesRead = 0;

            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                if (System.currentTimeMillis() - lastTime >= 1000) {
                    double downloadSpeed = (totalBytesRead - lastBytesRead) / 1024.0; // KB/s
                    System.out.printf("Downloaded: %d bytes at %.2f KB/s%n", totalBytesRead, downloadSpeed);
                    lastBytesRead = totalBytesRead;
                    lastTime = System.currentTimeMillis();
                }
            }
            System.out.println("Download complete!");
        } finally {
            connection.disconnect();
        }
    }
}
