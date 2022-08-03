package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fos = new FileOutputStream(
                     Paths.get(new URL(url).getPath()).getFileName().toString())) {
            byte[] dataBuffer = new byte[1024];
            int byteRead;
            int download = 0;
            long start = System.currentTimeMillis();
            while ((byteRead = in.read(dataBuffer, 0, 1024)) != -1) {
                download += byteRead;
                if (download >= speed) {
                    long duration = System.currentTimeMillis() - start;
                    if (duration < 1000) {
                        Thread.sleep(1000 - duration);
                    }
                    download = 0;
                    start = System.currentTimeMillis();
                }
                fos.write(dataBuffer, 0, byteRead);
            }
        } catch (IOException | InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Enter url and speed");
        }
        if (!args[0].startsWith("https")) {
            throw new IllegalArgumentException("URL must starts with \"https\"");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Should be positive number");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
